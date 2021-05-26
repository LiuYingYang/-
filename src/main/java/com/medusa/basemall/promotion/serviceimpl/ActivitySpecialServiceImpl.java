package com.medusa.basemall.promotion.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.medusa.basemall.activemq.ActiveMqClient;
import com.medusa.basemall.constant.ActiviMqQueueName;
import com.medusa.basemall.constant.ActivityState;
import com.medusa.basemall.constant.ActivityType;
import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.integral.vo.FindProductVo;
import com.medusa.basemall.jobhandler.ActiveDelaySendJobHandler;
import com.medusa.basemall.product.dao.ProductMapper;
import com.medusa.basemall.product.dao.ProductSpecItemMapper;
import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.product.entity.ProductSpecItem;
import com.medusa.basemall.product.vo.ProductWxVo;
import com.medusa.basemall.promotion.common.ActivityUtils;
import com.medusa.basemall.promotion.dao.ActivityCouponMapper;
import com.medusa.basemall.promotion.dao.ActivityProductMapper;
import com.medusa.basemall.promotion.dao.ActivityProductStockMapper;
import com.medusa.basemall.promotion.dao.ActivitySpecialMapper;
import com.medusa.basemall.promotion.entity.ActivityProduct;
import com.medusa.basemall.promotion.entity.ActivityProductStock;
import com.medusa.basemall.promotion.entity.ActivitySpecial;
import com.medusa.basemall.promotion.service.ActivityProductService;
import com.medusa.basemall.promotion.service.ActivitySpecialService;
import com.medusa.basemall.promotion.vo.ActivitySpecialVo;
import com.medusa.basemall.utils.IdGenerateUtils;
import com.medusa.basemall.utils.TimeUtil;
import com.vip.vjtools.vjkit.time.DateFormatUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author Created by psy on 2018/05/30.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ActivitySpecialServiceImpl extends AbstractService<ActivitySpecial> implements ActivitySpecialService {

	@Resource
	private ActivitySpecialMapper tActivitySpecialMapper;
	@Resource
	private ActiveDelaySendJobHandler activeDelaySendJobHandler;

	@Resource
	private ActivityProductService activityProductService;

	@Resource
	private ProductMapper productMapper;

	@Resource
	private ProductSpecItemMapper specItemMapper;

	@Resource
	private ActiveMqClient activeMqClient;

	@Resource
	private ActivityCouponMapper activityCouponMapper;

	@Resource
	private ActivityProductMapper activityProductMapper;

	@Resource
	private ProductSpecItemMapper productSpecItemMapper;

	@Resource
	private ActivityProductStockMapper activityProductStockMapper;

	@Resource
	private ActivityUtils activityUtils;

	@Override
	public Result findProductForSpecial(FindProductVo findProductVo) {
		Boolean result = activityUtils.checkCoupon(findProductVo);
		if (!result) {
			return ResultGenerator.genFailResult("活动时间冲突,无法创建");
		}
		PageHelper.startPage(findProductVo.getPageNum(), findProductVo.getPageSize());
		List<Long> productIds = activityProductService
				.selectTimeConflictProduct(findProductVo.getAppmodelId(), findProductVo.getStartDate(),
						findProductVo.getEndDate(), findProductVo.getOverlayState(), false,
						findProductVo.getActivityId(), ActivityType.SPECIAL);
//		List<ProductWxVo> productWxVos = productMapper.selectNotinOrInIdsSpec(productIds, findProductVo.getAppmodelId());
//		return ResultGenerator.genSuccessResult(new PageInfo(productWxVos));
		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 创建活动、活动商品
	 *
	 * @param activitySpecialVo
	 * @return
	 */

	@Override
	public int saveActivitySpecial(ActivitySpecialVo activitySpecialVo) {
		int rs = 0;
		List<ProductWxVo> productList = activitySpecialVo.getProductList();
		if (!productList.isEmpty()) {
			activitySpecialVo.setDeleteState(false);
			activitySpecialVo.setNowState(ActivityState.READY);
			activitySpecialVo.setCreateTime(TimeUtil.getNowTime());
			rs += tActivitySpecialMapper.insertSelective(activitySpecialVo);
			if (rs > 0) {

				activeDelaySendJobHandler.savaTask(activitySpecialVo.getActivitySpecialId().toString(),
						ActiviMqQueueName.ACTIVITY_SPECIAL_START,
						TimeUtil.str2Timestamp(activitySpecialVo.getStartDate()) - TimeUtil
								.str2Timestamp(activitySpecialVo.getCreateTime()),
						activitySpecialVo.getAppmodelId());
			}
			//新增活动商品
			for (ProductWxVo productWxVo : productList) {
				Product product = productMapper.selectByPrimaryKey(productWxVo.getProductId());
				ActivityProduct ap = new ActivityProduct();
				ap.setActivityType(ActivityType.GROUP);
				ap.setActivityId(activitySpecialVo.getActivitySpecialId());
				ap.setAppmodelId(activitySpecialVo.getAppmodelId());
				ap.setProductId(productWxVo.getProductId());
				ap.setActivityProductId(IdGenerateUtils.getItemId());
				ap.setActivityPrice(productWxVo.getActivityPrice());
				ap.setActivityDiscount(productWxVo.getActivityDiscount());
				ap.setMaxQuantity(productWxVo.getMaxQuantity());
				ap.setActivityStock(productWxVo.getActivityStock());
				ap.setSoldQuantity(0);
				// 活动商品不同规格库存
				List<ProductSpecItem> productSpecItemList = productWxVo.getProductSpecItemList();
				if (productSpecItemList != null) {
					int activityStock = 0;
					for (ProductSpecItem productSpecItem : productSpecItemList) {
						specItemMapper.updateByPrimaryKeySelective(productSpecItem);
						activityStock = activityStock + productSpecItem.getActivityStock();
						ProductSpecItem productSpecItemNew = productSpecItemMapper
								.selectByPrimaryKey(productSpecItem.getProductSpecItemId());
						activityStock += productSpecItem.getActivityStock();
						// 多规格抽出活动库存
						productSpecItemNew.setStock(productSpecItemNew.getStock() - productSpecItem.getActivityStock());
						productSpecItemMapper.updateByPrimaryKey(productSpecItemNew);
					}
					ap.setActivityStock(activityStock);
				}
				product.setStock(product.getStock() - ap.getActivityStock());
				productMapper.updateByPrimaryKeySelective(product);
				activityProductService.saveActivityProduct(ap);
			}

		}

		return rs;
	}

	/**
	 * 后端查询特价活动
	 *
	 * @param appmodelId
	 * @return
	 */
	@Override
	public List<ActivitySpecialVo> findByAppmodelId(String appmodelId) {

		List<ActivitySpecialVo> list = new ArrayList<>();
		ActivitySpecial newag = new ActivitySpecial();
		newag.setAppmodelId(appmodelId);
		newag.setDeleteState(false);
		List<ActivitySpecial> activitySpecials = tActivitySpecialMapper.select(newag);
		activitySpecials.forEach(activitySpecial -> {
			ActivitySpecialVo activitySpecialVo = new ActivitySpecialVo();
			BeanUtils.copyProperties(activitySpecial, activitySpecialVo);
			list.add(activitySpecialVo);
		});

		return list;
	}

	@Override
	public int batchDelete(String[] activitySpecialId) {
		for (int i = 0; i < activitySpecialId.length; i++) {
			ActivitySpecial activitySpecial = tActivitySpecialMapper
					.selectByPrimaryKey(Integer.valueOf(activitySpecialId[i]));
			if (activitySpecial.getNowState().equals(ActivityState.READY) || activitySpecial.getNowState()
					.equals(ActivityState.STARE)) {
				List<ActivityProduct> activityProducts = activityProductMapper
						.selectByActivityIdsProduct(activitySpecialId[i].split(","), ActivityType.SECONDKILL,
								activitySpecial.getAppmodelId());
				for (ActivityProduct activityProduct : activityProducts) {
					Product product = productMapper.selectByPrimaryKey(activityProduct.getProductId());
					product.setStock(product.getStock() + activityProduct.getActivityStock());
					productMapper.updateByPrimaryKeySelective(product);
					List<ActivityProductStock> activityProductStocks = activityProductStockMapper
							.findByAcitivityProductId(activityProduct.getActivityProductId());
					if (activityProductStocks.size() > 0) {
						for (ActivityProductStock activityProductStock : activityProductStocks) {
							ProductSpecItem productSpecItem = productSpecItemMapper
									.selectByPrimaryKey(activityProductStock.getProductSpecItemId());
							productSpecItem
									.setStock(productSpecItem.getStock() + activityProductStock.getActivityStock());
							productSpecItemMapper.updateByPrimaryKey(productSpecItem);
						}
					}
				}
			}

		}
		return tActivitySpecialMapper.batchDelete(activitySpecialId);
	}

	@Override
	public int updateActivitySpecial(ActivitySpecialVo activitySpecialVo) {

		int rs = 0;
		Integer activitySpecialId = activitySpecialVo.getActivitySpecialId();
		//删除活动商品
		activityProductService.deleteByActivityId(activitySpecialId.toString(), ActivityType.GROUP,
				activitySpecialVo.getAppmodelId());

		//重新创建活动
		activitySpecialVo.setNowState(ActivityState.READY);
		activitySpecialVo.setActivitySpecialId(null);
		rs += saveActivitySpecial(activitySpecialVo);

		return rs;
	}


	@Override
	public ActivitySpecial findByActivitySpecialIdStart(Integer activitySpecialId) {
		return tActivitySpecialMapper.findByActivitySpecialIdStart(activitySpecialId);
	}

	@Override
	public ActivitySpecial findByActivitySpecialIdEnd(Integer activitySpecialId) {
		return tActivitySpecialMapper.findByActivitySpecialIdEnd(activitySpecialId);
	}

	@Override
	public List<ActivitySpecial> selectNotEnd(Map<String, Object> mapForSpecial) {
		return tActivitySpecialMapper.selectNotEnd(mapForSpecial);
	}

	@Override
	public void updateEndDate(Map<String, Object> map) {
		tActivitySpecialMapper.updateEndDate(map);
	}

	@Override
	public List<ActivitySpecial> findConflictingSpecial(String startTime, String endTime, String appmodelId) {
		try {
			//查询已开始,未开始,进行中的秒杀活动判断是否存在时间冲突
			Date createStartDate = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.parse(startTime);
			Date createEndDate = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.parse(endTime);
			return tActivitySpecialMapper
					.findConflictingSeckill(createStartDate.getTime(), createEndDate.getTime(), appmodelId);
		} catch (ParseException e) {
			e.printStackTrace();
			return new ArrayList<>(0);
		}
	}
}
