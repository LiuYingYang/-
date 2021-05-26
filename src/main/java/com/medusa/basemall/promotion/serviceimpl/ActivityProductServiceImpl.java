package com.medusa.basemall.promotion.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.constant.ActivityState;
import com.medusa.basemall.constant.ActivityType;
import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.order.service.BuycarService;
import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.product.entity.ProductSpecItem;
import com.medusa.basemall.product.service.ProductService;
import com.medusa.basemall.product.service.ProductSpecItemService;
import com.medusa.basemall.product.vo.DeleteProduct;
import com.medusa.basemall.product.vo.JoinActiveInfo;
import com.medusa.basemall.promotion.common.ActivityUtils;
import com.medusa.basemall.promotion.dao.ActivityProductMapper;
import com.medusa.basemall.promotion.entity.*;
import com.medusa.basemall.promotion.service.*;
import com.medusa.basemall.user.service.CollectService;
import com.medusa.basemall.user.service.FootMarkService;
import com.vip.vjtools.vjkit.time.ClockUtil;
import com.vip.vjtools.vjkit.time.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;


/**
 *
 * @author psy
 * @date 2018/05/30
 * 需要事物时添加  @Transactional
 * @author Created by psy on 2018/05/30.
 */
@Service

public class ActivityProductServiceImpl extends AbstractService<ActivityProduct> implements ActivityProductService {

	@Resource
	private ActivityProductMapper tActivityProductMapper;

	@Resource
	private ActivityProductStockService activityProductStockService;

	@Resource
	private ActivityCouponService activityCouponService;

	@Resource
	private ActivityGroupService activityGroupService;

	@Resource
	private ActivitySeckillService activitySeckillService;

	@Resource
	private ActivitySpecialService activitySpecialService;

	@Resource
	private ProductService productService;
	@Resource
	private ProductSpecItemService productSpecItemService;

	@Resource
	private BuycarService buycarService;

	@Autowired
	private FootMarkService footMarkService;
	@Autowired
	private CollectService collectService;


	/**
	 * 查询出所有与当前活动时间冲突的活动产品
	 * @param appmodelId 模板ID
	 * @param startDate  活动开始时间
	 * @param endDate    结束时间
	 * @param Overlay    是否叠加
	 * @param isCoupon   是否是优惠券活动
	 * @param activityId 活动id
	 * @param activityType 活动类型
	 * @return
	 */
	@Override
	public List<Long> selectTimeConflictProduct(String appmodelId, String startDate, String endDate, Boolean Overlay,
			boolean isCoupon, Integer activityId, Integer activityType) {
		List<ActivityProduct> activityProducts = new ArrayList<>();
		try {
			Date createStartTime = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.parse(startDate);
			Date createEndTime = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.parse(endDate);
			//团购活动
			Map<String, Object> mapForGroup = new HashMap<>();
			mapForGroup.put("appmodelId", appmodelId);
			if (activityId != null && !activityId.equals("") && activityType.equals(ActivityType.GROUP)) {
				mapForGroup.put("activityGroupId", activityId);
			}
			List<ActivityGroup> activityGroups = activityGroupService.selectNotEnd(mapForGroup);
			if (!activityGroups.isEmpty()) {
				activityGroups = activityGroups.stream().filter(activityProduct -> ActivityUtils
						.verifyStartTimeAndEndTimeIfClash(createStartTime, createEndTime,
								activityProduct.getStartDate(), activityProduct.getEndDate()))
						.collect(Collectors.toList());
				if (!activityGroups.isEmpty()) {
					activityGroups.forEach(ag -> {
						boolean isOverlay = isCoupon && Overlay && ag.getOverlayState();
						if (!isOverlay) {
							List<ActivityProduct> activityProductList = findActivityProduct(ag.getActivityGroupId(),
									ActivityType.GROUP);
							activityProducts.addAll(activityProductList);
						}
					});

				}
			}
			//秒杀活动
			Map<String, Object> mapForSeckill = new HashMap<>();
			mapForSeckill.put("appmodelId", appmodelId);
			if (activityId != null && !activityId.equals("") && activityType.equals(ActivityType.SECONDKILL)) {
				mapForSeckill.put("activitySeckill", activityId);
			}
			List<ActivitySeckill> activitySeckills = activitySeckillService.selectNotEnd(null);
			if (!activitySeckills.isEmpty()) {
				activitySeckills = activitySeckills.stream().filter(activityProduct -> ActivityUtils
						.verifyStartTimeAndEndTimeIfClash(createStartTime, createEndTime,
								activityProduct.getStartDate(), activityProduct.getEndDate()))
						.collect(Collectors.toList());
				if (!activitySeckills.isEmpty()) {
					activitySeckills.forEach(as -> {
						boolean isOverlay = isCoupon && Overlay && as.getOverlayState();
						if (!isOverlay) {
							List<ActivityProduct> activityProductList = findActivityProduct(as.getActivitySeckillId(),
									ActivityType.SECONDKILL);
							activityProducts.addAll(activityProductList);
						}
					});
				}
			}
			//特价活动
			Map<String, Object> mapForSpecial = new HashMap<>();
			mapForSpecial.put("appmodelId", appmodelId);
			if (activityId != null && !activityId.equals("") && activityType.equals(ActivityType.SPECIAL)) {
				mapForSpecial.put("activitySeckill", activityId);
			}
			List<ActivitySpecial> activitySpecials = activitySpecialService.selectNotEnd(mapForSpecial);
			if (!activitySpecials.isEmpty()) {
				activitySpecials = activitySpecials.stream().filter(activityProduct -> ActivityUtils
						.verifyStartTimeAndEndTimeIfClash(createStartTime, createEndTime,
								activityProduct.getStartDate(), activityProduct.getEndDate()))
						.collect(Collectors.toList());
				if (!activitySpecials.isEmpty()) {
					activitySpecials.forEach(as -> {
						boolean isOverlay = isCoupon && Overlay && as.getOverlayState();
						if (!isOverlay) {
							List<ActivityProduct> activityProductList = findActivityProduct(as.getActivitySpecialId(),
									ActivityType.SPECIAL);
							activityProducts.addAll(activityProductList);
						}
					});
				}
			}
			List<Long> ids = new ArrayList<>();
			if (!activityProducts.isEmpty()) {
				ids = activityProducts.stream().map(ActivityProduct::getProductId).collect(Collectors.toList());
			}
			return ids;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new ArrayList<>(0);
	}

	/**
	 * 根据活动ID和类型，删除活动商品
	 *
	 * @param activityIds
	 * @param activityType
	 * @param appmodelId
	 * @return
	 */
	@Override
	public int deleteByActivityId(String activityIds, Integer activityType, String appmodelId) {
		String[] ids = activityIds.split(",");
		List<ActivityProduct> list = tActivityProductMapper.selectByActivityIdsProduct(ids, activityType, appmodelId);
		//更新商品标签
		if (!list.isEmpty()) {
			//优惠券活动没有活动商品,只有活动标签
			if (ActivityType.COUPON.equals(activityType)) {
				List<ActivityCoupon> activityCouponList = activityCouponService.findByIds(activityIds);
				for (ActivityCoupon activityCoupon : activityCouponList) {
					//如果是进行中的活动需要删除标签
					if (activityCoupon.getNowState().equals(ActivityState.STARE)) {
						List<Product> productList = null;
						if (activityCoupon.getFullState().equals(false)) {
							String productIds = list.stream()
									.filter(obj -> obj.getActivityId().equals(activityCoupon.getActivityCouponId()))
									.map(obj -> obj.getProductId().toString()).collect(Collectors.joining(","));
							productList = productService.findByIds(productIds);
						}
						if (activityCoupon.getFullState().equals(true)) {
							productList = productService.selectByAppmodelId(activityCoupon.getAppmodelId());
						}
						removeMark(activityType, appmodelId, productList);
					}
				}
				return 1;
			} else if (ActivityType.SECONDKILL.equals(activityType) || ActivityType.GROUP.equals(activityType)
					|| ActivityType.SPECIAL.equals(activityType)) {
				String productIds = list.stream().map(obj -> obj.getProductId().toString())
						.collect(Collectors.joining(","));
				List<Product> productList = productService.findByIds(productIds);
				removeMark(activityType, appmodelId, productList);
				return 1;
			}
		}

		return 0;
	}

	private void removeMark(Integer activityType, String appmodelId, List<Product> productList) {
		productList.forEach(product -> {
			if (product.getProductTypeList().contains(activityType.toString())) {
				product.setProductTypeList(
						ActivityUtils.removeProductTypeList(product.getProductTypeList(), activityType));
				productService.update(product);
				collectService.updateActivityMark(product, appmodelId);
				footMarkService.updateActivityMark(product, appmodelId);
			}
		});
	}

	/**
	 * 根据活动ID和类型查找活动商品，并返回商品的ID数组
	 *
	 * @param activityId
	 * @param activityType
	 * @return
	 */
	@Override
	public List<Long> findProductIds(Integer activityId, Integer activityType, Integer limit) {

		List<Long> productIds = new ArrayList<>();
		ActivityProduct activityProduct = new ActivityProduct();
		activityProduct.setActivityId(activityId);
		activityProduct.setActivityType(activityType);
		List<ActivityProduct> list = tActivityProductMapper.select(activityProduct);

		if (!list.isEmpty()) {
			productIds = list.stream().sorted(Comparator.comparing(ActivityProduct::getSoldQuantity).reversed())
					.limit(limit).map(ActivityProduct::getProductId).collect(Collectors.toList());
		}

		return productIds;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveActivityProduct(ActivityProduct ap) {
		tActivityProductMapper.insertSelective(ap);
	}


	@Override
	public ActivityProduct selectByProductId(Long productId) {
		return tActivityProductMapper.selectByProductId(productId);
	}

	@Override
	public Map<String, Object> findProductActivityForOrder(Integer activityType, Long activityProductId,
			Integer activityId) {
		Map<String, Object> map = new HashMap<>(3);
		ActivityProduct ap = tActivityProductMapper.selectByPrimaryKey(activityProductId);
		map.put("ActivityProductInfo", ap);
		switch (activityType) {
			case 2001:
				map.put("ActivitySpecialInfo", activitySpecialService.findById(ap.getActivityId()));
				break;
			case 3001:
				map.put("ActivityGroupInfo", activityGroupService.findById(ap.getActivityId()));
				break;
			case 4001:
				map.put("ActivitySeckillInfo", activitySeckillService.findById(ap.getActivityId()));

				break;
			default:
				break;
		}
		return map;
	}

	@Override
	public void deleteActivityProduct(List<DeleteProduct> deleteProduct) {
		List<Map<String, Object>> list = new ArrayList<>();
		List<Long> productIds = deleteProduct.stream().map(obj -> obj.getProductId()).collect(Collectors.toList());
		//查询所有进心中和未开始的活动中包含的商品
		List<ActivityProduct> haveActivity = tActivityProductMapper.findProductIfJoninActivity(productIds);
		if (haveActivity != null && haveActivity.size() > 0) {
			//如果是活动商品,活动商品中需要删除
			Map<Long, DeleteProduct> deleteProductMap = deleteProduct.stream()
					.collect(Collectors.toMap(k -> k.getProductId(), v -> v));
			Map<Long, List<ActivityProduct>> listMap = haveActivity.stream()
					.collect(Collectors.groupingBy(ActivityProduct::getProductId));
			deleteProductMap.forEach((k, v) -> {
				JoinActiveInfo joinActiveInfo = v.getJoinActiveInfo();
				//正在进行中的活动
				if (joinActiveInfo != null) {
					//处理团购/秒杀/特价活动商品
					JSONObject activityInfo = JSONObject.parseObject(joinActiveInfo.getActiveInfo());
					Map<String, Object> map = new HashMap<>();
					map.put("productId", v.getProductId());
					if (activityInfo != null) {
						Long activityProductId = activityInfo.getLong("activityProductId");
						ActivityProduct activityProduct = new ActivityProduct();
						activityProduct.setActivityProductId(activityProductId);
						activityProduct.setDeleteState(true);
						tActivityProductMapper.updateByPrimaryKeySelective(activityProduct);
						Integer activityId = activityInfo.getInteger("activityId");
						Integer activityType = activityInfo.getInteger("activityType");
						activityEnd(activityId, activityType);
						map.put("otherId", activityId);
						ActivityProduct ap = tActivityProductMapper.selectByPrimaryKey(activityProductId);
						if (ap.getSpecType().equals(false)) {
							List<ProductSpecItem> ps = productSpecItemService.findByProductId(ap.getProductId());
							List<ActivityProductStock> aps = activityProductStockService
									.findActivityProductSpec(ap.getActivityProductId());
							Map<Long, ActivityProductStock> activityProductStockMap = aps.stream()
									.collect(Collectors.toMap(j -> j.getProductSpecItemId(), b -> b));
							ps.forEach(obj -> {
								ActivityProductStock productStock = activityProductStockMap
										.get(obj.getProductSpecItemId());
								obj.setStock(obj.getStock() + productStock.getActivityStock());
								productSpecItemService.update(obj);
							});
						}
						Product p = productService.findById(ap.getProductId());
						p.setStock(p.getStock() + ap.getActivityStock());
						productService.update(p);
					}
					//处理优惠券活动商品
					JSONObject couponInfo = JSONObject.parseObject(joinActiveInfo.getCouponInfo());
					if (couponInfo != null) {
						Integer activityId = couponInfo.getInteger("activityId");
						Integer activityType = couponInfo.getInteger("activityType");
						ActivityProduct activityProduct = new ActivityProduct();
						activityProduct.setActivityType(activityType);
						activityProduct.setActivityId(activityId);
						activityProduct.setProductId(v.getProductId());
						activityProduct.setDeleteState(true);
						tActivityProductMapper.updateByPrimaryKeySelective(activityProduct);
						activityEnd(activityId, activityType);
						map.put("couponId", activityId);
					}
					list.add(map);
				}
				List<ActivityProduct> activityProducts = listMap.get(v.getProductId());
				if (activityProducts.size() > 0) {
					//先按活动分组
					List<Long> activityPorudctIds = new ArrayList<>();
					for (ActivityProduct activityProduct : activityProducts) {
						switch (activityProduct.getActivityType()) {
							case 2001:
								break;
							case 3001:
								break;
							case 4001:
								ActivitySeckill as = activitySeckillService.findById(activityProduct.getActivityId());
								if (as != null) {
									if (as.getNowState().equals(1) || as.getNowState().equals(2)) {
										activityPorudctIds.add(activityProduct.getProductId());
									}
								}
								break;
							default:
								break;
						}
					}
					updateDelete(activityPorudctIds, true);
					//判断是否是最后一个商品
					Map<Integer, Integer> map = activityProducts.stream()
							.collect(Collectors.toMap(ke -> ke.getActivityId(), va -> va.getActivityType()));
					map.forEach((id, type) -> {
						ActivityProduct activityProduct = new ActivityProduct();
						activityProduct.setActivityId(id);
						activityProduct.setActivityType(type);
						activityProduct.setDeleteState(false);
						int count = tActivityProductMapper.selectCount(activityProduct);
						if (count == 0) {
							switch (type) {
								case 2001:
									break;
								case 3001:
									break;
								case 4001:
									ActivitySeckill activitySeckill = new ActivitySeckill();
									activitySeckill.setDeleteState(true);
									activitySeckill.setActivitySeckillId(id);
									activitySeckillService.update(activitySeckill);
									break;
								default:
									break;
							}
						}
					});
				}
			});

			//活动商品删除,需要处理购物车中商品的状态为已下架
			buycarService.updateActiveInfo(list);
		}

	}

	private void updateDelete(List<Long> activityPorudctIds, Boolean deleteState) {
		tActivityProductMapper.updateDelete(activityPorudctIds, deleteState);
	}

	@Override
	public List<ActivityProduct> findActivityProduct(String activityIds, Integer activeType, String appmodelId) {
		String[] activityId = activityIds.split(",");
		List<ActivityProduct> activityProducts = tActivityProductMapper
				.selectByActivityIdsProduct(activityId, activeType, appmodelId);
		return activityProducts;
	}

	@Override
	public ActivityProduct findActivityProduct(Integer activityId, Long productId, String appmodelId) {
		ActivityProduct activityProduct = new ActivityProduct();
		activityProduct.setActivityId(activityId);
		activityProduct.setProductId(productId);
		activityProduct.setAppmodelId(appmodelId);
		return tActivityProductMapper.selectOne(activityProduct);
	}

	@Override
	public void updatePreheatState(Integer activityId, Integer activityType, String appmodelId, Integer state) {
		tActivityProductMapper.updatePreheatState(activityId, activityType, appmodelId, state);
	}

	@Override

	public void updateSoldQuantity(Long activityProductId, Integer quantity) {
		tActivityProductMapper.updateSoldQuantity(activityProductId, quantity);
	}

	@Override
	public List<ActivityProduct> findPreheatTimetoStartDateActivityProduct(String preheatTime, String appmodelId,
			Integer activityType, Integer activityId) {
		return tActivityProductMapper
				.findPreheatTimetoStartDateActivityProduct(preheatTime, appmodelId, activityType, activityId);
	}

	@Override
	public Integer selectActivityProductSum(Integer activityId, Integer activityType, String appmodelId) {
		ActivityProduct activityProduct = new ActivityProduct();
		activityProduct.setAppmodelId(appmodelId);
		activityProduct.setActivityType(activityType);
		activityProduct.setActivityId(activityId);
		activityProduct.setDeleteState(false);
		return tActivityProductMapper.selectCount(activityProduct);
	}

	@Override
	public ActivityProduct findProductJoinActivityNotEnd(Long productId, Integer activityType) {
		return tActivityProductMapper.selectProductJoinActivityNotEnd(productId, activityType);
	}

	@Override
	public List<ActivityProduct> findProductJoinActivityNotEnds(List<Long> productIds, Integer activityType) {
		return tActivityProductMapper.selectProductJoinActivityNotEnds(productIds, activityType);
	}


	private void activityEnd(Integer activityId, Integer activityType) {
		ActivityProduct condition = new ActivityProduct();
		condition.setActivityId(activityId);
		condition.setActivityType(activityType);
		condition.setDeleteState(false);
		//活动中已经没有商品,则活动结束
		if (tActivityProductMapper.selectCount(condition) == 0) {
			String endDate = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.format(ClockUtil.currentDate());
			Map<String, Object> map = new HashMap<>();
			map.put("activityId", activityId);
			map.put("endDate", endDate);
			map.put("newState", 2);
			switch (activityType) {
				case 1001:
					activityCouponService.updateEndDate(map);
					break;
				case 2001:
					activitySpecialService.updateEndDate(map);
					break;
				case 3001:
					activityGroupService.updateEndDate(map);
					break;
				case 4001:
					activitySeckillService.updateEndDate(map);
					break;
				default:
					break;
			}
		}
	}


	/**
	 * 根据活动ID和类型查找活动商品
	 *
	 * @param activityId
	 * @param activityType
	 * @return
	 */
	public List<ActivityProduct> findActivityProduct(Integer activityId, int activityType) {
		ActivityProduct activityProduct = new ActivityProduct();
		activityProduct.setActivityId(activityId);
		activityProduct.setActivityType(activityType);
		return tActivityProductMapper.select(activityProduct);
	}

}
