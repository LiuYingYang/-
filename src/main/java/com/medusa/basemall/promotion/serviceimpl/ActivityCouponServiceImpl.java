package com.medusa.basemall.promotion.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medusa.basemall.constant.ActiviMqQueueName;
import com.medusa.basemall.constant.ActivityState;
import com.medusa.basemall.constant.ActivityType;
import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.integral.vo.FindProductVo;
import com.medusa.basemall.jobhandler.ActiveDelaySendJobHandler;
import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.product.service.ProductService;
import com.medusa.basemall.product.vo.ProductSimpleVo;
import com.medusa.basemall.promotion.common.ActivityUtils;
import com.medusa.basemall.promotion.dao.ActivityCouponMapper;
import com.medusa.basemall.promotion.entity.ActivityCoupon;
import com.medusa.basemall.promotion.entity.ActivityProduct;
import com.medusa.basemall.promotion.entity.Coupon;
import com.medusa.basemall.promotion.service.ActivityCouponService;
import com.medusa.basemall.promotion.service.ActivityProductService;
import com.medusa.basemall.promotion.service.CouponService;
import com.medusa.basemall.promotion.vo.ActivityCouponVo;
import com.medusa.basemall.utils.IdGenerateUtils;
import com.medusa.basemall.utils.TimeUtil;
import com.vip.vjtools.vjkit.time.DateFormatUtil;
import com.vip.vjtools.vjkit.time.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author Created by psy on 2018/05/30.
 */
@Service
public class ActivityCouponServiceImpl extends AbstractService<ActivityCoupon> implements ActivityCouponService {

	@Resource
	private ActivityCouponMapper tActivityCouponMapper;
	@Resource
	private ActiveDelaySendJobHandler activeDelaySendJobHandler;
	@Resource
	private CouponService couponService;

	@Resource
	private ProductService productService;

	@Resource
	private ActivityProductService activityProductService;


	/**
	 * 查找优惠券活动可用的商品
	 */
	@Override
	public Result findProductForCoupon(FindProductVo findProductVo) {

		//判断是否时间冲突
		try {
			Date createStartTime = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.parse(findProductVo.getStartDate());
			Date createEndTime = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.parse(findProductVo.getEndDate());
			if (DateUtil.isSameTime(createStartTime, createEndTime)) {
				return ResultGenerator.genFailResult("无法创建开始时间和结束时间相同的活动");
			}
			//查询所有未开始或进行中的优惠券活动
			List<ActivityCoupon> activityCoupons = tActivityCouponMapper
					.findGoingAndNotStarted(findProductVo.getAppmodelId());
			for (ActivityCoupon activityCoupon : activityCoupons) {
				if (findProductVo.getActivityId() != null && findProductVo.getActivityId() > 0 && findProductVo
						.getActivityId().equals(activityCoupon.getActivityCouponId())) {
					continue;
				}
				boolean flag = ActivityUtils
						.verifyStartTimeAndEndTimeIfClash(createStartTime, createEndTime, activityCoupon.getStartDate(),
								activityCoupon.getEndDate());
				if (flag == true) {
					return ResultGenerator.genFailResult("活动时间冲突,无法创建");
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		PageHelper.startPage(findProductVo.getPageNum(), findProductVo.getPageSize());
		List<ProductSimpleVo> list = productService.selectProductSimpleVo(findProductVo.getAppmodelId());
		PageInfo pageInfo = new PageInfo(list);
		return ResultGenerator.genSuccessResult(pageInfo);
	}

	/**
	 * 创建优惠券活动
	 *
	 * @param ac
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result saveActivityCoupon(ActivityCouponVo ac) {

		ac.setCreateTime(TimeUtil.getNowTime());
		//是否适用于所有商品
		if (ac.getFullState()) {
			Map<String, Object> newMap = new HashMap<>();
			newMap.put("appmodelId", ac.getAppmodelId());
			if (ac.getActivityCouponId() != null && !"".equals(ac.getActivityCouponId())) {
				newMap.put("activityCouponId", ac.getActivityCouponId());
			}
			List<ActivityCoupon> activityCoupons = tActivityCouponMapper.selectSpecialTwo(newMap);
			if (activityCoupons.size() > 0) {
				try {
					Date createStartDate = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.parse(ac.getStartDate());
					Date createEndDate = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.parse(ac.getEndDate());
					for (ActivityCoupon activityCoupon : activityCoupons) {
						if (ActivityUtils.verifyStartTimeAndEndTimeIfClash(createStartDate, createEndDate,
								activityCoupon.getStartDate(), activityCoupon.getEndDate()) == true) {
							return ResultGenerator.genFailResult("活动时间商品存在冲突,无法创建");
						}
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			ac.setNowState(ActivityState.READY);
			ac.setDeleteState(false);
			if (tActivityCouponMapper.insertSelective(ac) > 0) {
				activeDelaySendJobHandler
						.savaTask(ac.getActivityCouponId().toString(), ActiviMqQueueName.ACTIVITY_COUPON_START,
								TimeUtil.str2Timestamp(ac.getStartDate()) - TimeUtil
										.str2Timestamp(ac.getCreateTime()), ac.getAppmodelId());
				//创建活动和优惠券
				List<Coupon> coupons = ac.getCouponList();
				coupons.forEach(coupon -> {
					coupon.setActivityCouponId(ac.getActivityCouponId());
					coupon.setAppmodelId(ac.getAppmodelId());
					coupon.setUsedQuantity(0);
					coupon.setObtainQuantity(0);
					coupon.setDeleteState(false);
					coupon.setSourceType(1);
				});
				couponService.save(coupons);
				return ResultGenerator.genSuccessResult("创建成功");
			}
			return ResultGenerator.genFailResult("创建失败");
		} else {
			//创建活动、优惠券、活动商品
			List<Long> productIds = ac.getProductIds();
			if (!productIds.isEmpty()) {
				int result = 0;
				ac.setNowState(ActivityState.READY);
				ac.setDeleteState(false);
				if (ac.getActivityCouponId() != null && !"".equals(ac.getActivityCouponId())) {
					result = tActivityCouponMapper.updateByPrimaryKeySelective(ac);
				} else {
					result = tActivityCouponMapper.insertSelective(ac);
				}
				if (result > 0) {
					activeDelaySendJobHandler.savaTask(ac.getActivityCouponId().toString(),
							ActiviMqQueueName.ACTIVITY_COUPON_START,
							TimeUtil.str2Timestamp(ac.getStartDate()) - TimeUtil
									.str2Timestamp(ac.getCreateTime()), ac.getAppmodelId());
				}
				for (Long productId : productIds) {
					ActivityProduct ap = new ActivityProduct();
					ap.setActivityType(ActivityType.COUPON);
					ap.setActivityId(ac.getActivityCouponId());
					ap.setAppmodelId(ac.getAppmodelId());
					ap.setProductId(productId);
					ap.setActivityProductId(IdGenerateUtils.getItemId());
					ap.setSoldQuantity(0);
					activityProductService.saveActivityProduct(ap);
				}
				List<Coupon> coupons = ac.getCouponList();
				coupons.forEach(coupon -> {
					coupon.setActivityCouponId(ac.getActivityCouponId());
					coupon.setAppmodelId(ac.getAppmodelId());
					coupon.setObtainQuantity(0);
					coupon.setUsedQuantity(0);
					coupon.setDeleteState(false);
					coupon.setSourceType(1);
				});
				couponService.save(coupons);
				return ResultGenerator.genSuccessResult("创建成功");
			} else {
				return ResultGenerator.genFailResult("针对部分商品必须有商品,无法创建");
			}
		}
	}

	@Override
	public List<ActivityCouponVo> findByAppmodelId(String appmodelId) {
		List<ActivityCouponVo> activityCouponVos = tActivityCouponMapper.findByAppmodelId(appmodelId);
		if (activityCouponVos.size() > 0) {
			List<ActivityCouponVo> list = new ArrayList<>();
			Map<Boolean, List<ActivityCouponVo>> collect = activityCouponVos.stream()
					.collect(Collectors.groupingBy(o -> o.getFullState(), Collectors.toList()));
			List<ActivityCouponVo> assign = collect.get(false);
			if (assign != null) {
				String activityIds = assign.stream().map(obj -> obj.getActivityCouponId().toString())
						.collect(Collectors.joining(","));
				if (activityIds.length() > 0) {
					List<ActivityProduct> activityProduct = activityProductService
							.findActivityProduct(activityIds, ActivityType.COUPON, appmodelId);
					assign.forEach(obj -> {
						List<ActivityProduct> activityProducts = activityProduct.stream()
								.filter(ap -> ap.getActivityId().equals(obj.getActivityCouponId()))
								.collect(Collectors.toList());
						String productIds = activityProducts.stream()
								.filter(ap -> ap.getDeleteState().equals(Boolean.FALSE))
								.map(ap -> ap.getProductId().toString()).distinct().collect(Collectors.joining(","));
						if (productIds != null && productIds.length() > 0) {
							obj.setProducts(productService.findByIds(productIds));
						}
					});
				}
				list.addAll(assign);
			}
			List<ActivityCouponVo> over = activityCouponVos.stream()
					.filter(obj -> obj.getNowState().equals(ActivityState.OVER)).collect(Collectors.toList());
			if (over.size() > 0) {
				String activeCouponId = over.stream().map(obj -> obj.getActivityCouponId().toString())
						.collect(Collectors.joining(","));
				List<Coupon> coupons = couponService.findByList("activityCouponId", activeCouponId);
				over.forEach(obj -> {
					List<Coupon> couponList = coupons.stream()
							.filter(coupon -> coupon.getActivityCouponId().equals(obj.getActivityCouponId()))
							.collect(Collectors.toList());
					obj.setCouponList(couponList);
				});
			}
			if (collect.get(true) != null) {
				list.addAll(collect.get(true));
			}
			list.stream().collect(Collectors.collectingAndThen(
					Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getActivityCouponId()))),
					ArrayList::new));
			list.sort(Comparator.comparing(ActivityCouponVo::getActivityCouponId).reversed());
			return list;
		}
		return activityCouponVos;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int batchDelete(String activityCouponIds, String appmodelId) {
		int rs = 0;
		List<ActivityCoupon> activityCoupons = tActivityCouponMapper.selectByIds(activityCouponIds);
		List<ActivityCoupon> underwayActivity = activityCoupons.stream()
				.filter(obj -> obj.getNowState().equals(ActivityState.STARE)).collect(Collectors.toList());
		if (underwayActivity.size() > 0) {
			ActivityCoupon activityCoupon = underwayActivity.get(0);
			//全场商品去除标志
			if (activityCoupon.getFullState() == true) {
				List<Product> products = productService.selectByAppmodelId(activityCoupon.getAppmodelId());
				for (Product product : products) {
					String label = ActivityUtils
							.removeProductTypeList(product.getProductTypeList(), ActivityType.COUPON);
					productService.updateActivityLabel(product.getProductId(), label);
				}
			} else {
				List<ActivityProduct> activityProduct = activityProductService
						.findActivityProduct(activityCoupon.getActivityCouponId().toString(), ActivityType.COUPON,
								activityCoupon.getAppmodelId());
				String productIds = activityProduct.stream().map(obj -> obj.getProductId().toString())
						.collect(Collectors.joining(","));
				List<Product> products = productService.findByIds(productIds);
				for (Product product : products) {
					String label = ActivityUtils
							.removeProductTypeList(product.getProductTypeList(), ActivityType.COUPON);
					productService.updateActivityLabel(product.getProductId(), label);
				}
			}

		}
		couponService.deleteByActivityCouponId(activityCouponIds);
		activityProductService.deleteByActivityId(activityCouponIds, ActivityType.COUPON, appmodelId);
		return tActivityCouponMapper.batchDelete(activityCouponIds.split(","));
	}

	/**
	 * 更新优惠券活动
	 * @param activityCouponVo
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result updateActivityCoupon(ActivityCouponVo activityCouponVo) {
		Integer activityCouponId = activityCouponVo.getActivityCouponId();
		//删除活动商品
		activityProductService
				.deleteByActivityId(activityCouponId.toString(), ActivityType.COUPON, activityCouponVo.getAppmodelId());
		//删除优惠券
		couponService.deleteByActivityCouponId(activityCouponId.toString());
		tActivityCouponMapper.deleteByPrimaryKey(activityCouponVo.getActivityCouponId());
		activityCouponVo.setActivityCouponId(null);
		//重新构建活动
		return saveActivityCoupon(activityCouponVo);
	}

	@Override
	public ActivityCoupon findByActivityCouponIdToStart(Integer activityCouponId) {
		return tActivityCouponMapper.findByActivityCouponIdToStart(activityCouponId);
	}

	@Override
	public ActivityCoupon findByActivityCouponIdToEnd(Integer activityCouponId) {
		return tActivityCouponMapper.findByActivityCouponIdToEnd(activityCouponId);
	}

	@Override
	public List<ActivityCoupon> selectNotEnd(Map<String, Object> mapForCoupon) {
		return tActivityCouponMapper.selectNotEnd(mapForCoupon);
	}

	@Override
	public ActivityCouponVo selectByActivityCouponVo(Integer activityId) {
		return tActivityCouponMapper.selectByActivityCouponVo(activityId);
	}

	@Override
	public void updateEndDate(Map<String, Object> map) {
		tActivityCouponMapper.updateEndDate(map);
	}

	@Override
	public ActivityCoupon findUnderwayActivityCoupon(String appmodelId) {
		return tActivityCouponMapper.selectStart(appmodelId);
	}

}
