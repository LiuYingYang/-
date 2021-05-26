package com.medusa.basemall.promotion.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.medusa.basemall.activemq.ActiveMqClient;
import com.medusa.basemall.constant.ActiviMqQueueName;
import com.medusa.basemall.constant.ActivityState;
import com.medusa.basemall.constant.ActivityType;
import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.core.ServiceException;
import com.medusa.basemall.integral.vo.FindProductVo;
import com.medusa.basemall.jobhandler.ActiveDelaySendJobHandler;
import com.medusa.basemall.product.dao.ProductMapper;
import com.medusa.basemall.product.dao.ProductSpecItemMapper;
import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.product.entity.ProductSpecItem;
import com.medusa.basemall.product.vo.ProductWxVo;
import com.medusa.basemall.promotion.common.ActivityUtils;
import com.medusa.basemall.promotion.dao.ActivityGroupMapper;
import com.medusa.basemall.promotion.dao.ActivityProductMapper;
import com.medusa.basemall.promotion.dao.ActivityProductStockMapper;
import com.medusa.basemall.promotion.dao.GroupMapper;
import com.medusa.basemall.promotion.entity.ActivityGroup;
import com.medusa.basemall.promotion.entity.ActivityProduct;
import com.medusa.basemall.promotion.entity.ActivityProductStock;
import com.medusa.basemall.promotion.service.ActivityGroupService;
import com.medusa.basemall.promotion.service.ActivityProductService;
import com.medusa.basemall.promotion.vo.ActivityGroupVo;
import com.medusa.basemall.utils.IdGenerateUtils;
import com.medusa.basemall.utils.TimeUtil;
import com.vip.vjtools.vjkit.time.DateFormatUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;


/**
 * @author Created by psy on 2018/05/30.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ActivityGroupServiceImpl extends AbstractService<ActivityGroup> implements ActivityGroupService {
	@Resource
	private ActivityGroupMapper tActivityGroupMapper;
	@Resource
	private ActiveDelaySendJobHandler activeDelaySendJobHandler;
	@Resource
	private ActivityProductService activityProductService;

	@Resource
	private ProductMapper productMapper;

	@Resource
	private GroupMapper groupMapper;

	@Autowired
	private ActiveMqClient activeMqClient;

	@Resource
	private ActivityProductMapper activityProductMapper;

	@Resource
	private ActivityProductStockMapper activityProductStockMapper;

	@Resource
	private ActivityUtils activityUtils;

	@Resource
	private ProductSpecItemMapper productSpecItemMapper;

	@Override
	public Result findProductForGroup(FindProductVo findProductVo) {
		Boolean result = activityUtils.checkCoupon(findProductVo);
		if (!result) {
			return ResultGenerator.genFailResult("活动时间冲突,无法创建");
		}

		PageHelper.startPage(findProductVo.getPageNum(), findProductVo.getPageSize());

		/*List<Long> productIds = activityProductService
				.selectTimeConflictProduct(findProductVo.getAppmodelId(), findProductVo.getStartDate(),
						findProductVo.getEndDate(), findProductVo.getOverlayState(), false,
						findProductVo.getActivityId(), ActivityType.GROUP);*/
		//List<ProductWxVo> productWxVos = productMapper.selectNotinOrInIdsSpec(null, findProductVo.getAppmodelId());
		//return ResultGenerator.genSuccessResult(new PageInfo(productWxVos));
		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 创建活动、活动商品
	 *
	 * @param activityGroupVo
	 * @return
	 */

	@Override
	public int saveActivityGroup(ActivityGroupVo activityGroupVo) {
		List<ProductWxVo> productList = activityGroupVo.getProductList();
		if (!productList.isEmpty()) {
			activityGroupVo.setNowState(ActivityState.READY);
			activityGroupVo.setCreateTime(TimeUtil.getNowTime());
			activityGroupVo.setDeleteState(false);
			if (tActivityGroupMapper.insertSelective(activityGroupVo)==0) {
				throw new ServiceException("创建失败");
			}
			//新增活动商品
			for (ProductWxVo productWxVo : productList) {
				Product product = productMapper.selectByPrimaryKey(productWxVo.getProductId());
				ActivityProduct ap = new ActivityProduct();
				ap.setActivityType(ActivityType.GROUP);
				ap.setActivityId(activityGroupVo.getActivityGroupId());
				ap.setAppmodelId(activityGroupVo.getAppmodelId());
				ap.setProductId(productWxVo.getProductId());
				ap.setActivityProductId(IdGenerateUtils.getItemId());
				ap.setActivityPrice(productWxVo.getActivityPrice());
				ap.setActivityDiscount(productWxVo.getActivityDiscount());
				ap.setMaxQuantity(productWxVo.getMaxQuantity());
				ap.setActivityStock(productWxVo.getActivityStock());
				ap.setSoldQuantity(0);
				activityProductService.saveActivityProduct(ap);
				//活动商品不同规格库存
				List<ProductSpecItem> productSpecItemList = productWxVo.getProductSpecItemList();
				if (productSpecItemList != null) {
					int activityStock = 0;
					for (ProductSpecItem productSpecItem : productSpecItemList) {
						ActivityProductStock activityProductStock = new ActivityProductStock();
						activityProductStock.setActivityProductId(ap.getActivityProductId());
						activityProductStock.setProductSpecItemId(productSpecItem.getProductSpecItemId());
						activityProductStock.setActivityStock(productSpecItem.getActivityStock());
						activityProductStockMapper.insert(activityProductStock);
						ProductSpecItem productSpecItemNew = productSpecItemMapper
								.selectByPrimaryKey(productSpecItem.getProductSpecItemId());
						activityStock += productSpecItem.getActivityStock();
						// 多规格抽出活动库存
						productSpecItemNew.setStock(productSpecItemNew.getStock() - productSpecItem.getActivityStock());
						productSpecItemMapper.updateByPrimaryKey(productSpecItemNew);
					}
					ap.setActivityStock(activityStock);
				}
				// 总库存抽出活动库存
				product.setStock(product.getStock() - ap.getActivityStock());
				productMapper.updateByPrimaryKeySelective(product);
				activityProductService.update(ap);
			}
			activeDelaySendJobHandler
					.savaTask(activityGroupVo.getActivityGroupId().toString(), ActiviMqQueueName.ACTIVITY_GROUP_START,
							TimeUtil.str2Timestamp(activityGroupVo.getStartDate()) - TimeUtil
									.str2Timestamp(activityGroupVo.getCreateTime()),
							activityGroupVo.getAppmodelId());
		}
		return 0;
	}

	/**
	 * 后端查询团购活动
	 *
	 * @param appmodelId
	 * @return
	 */
	@Override
	public List<ActivityGroupVo> findByAppmodelId(String appmodelId) {

		List<ActivityGroupVo> list = new ArrayList<>();
		ActivityGroup newag = new ActivityGroup();
		newag.setAppmodelId(appmodelId);
		newag.setDeleteState(false);
		List<ActivityGroup> activityGroups = tActivityGroupMapper.select(newag);
		activityGroups.forEach(activityGroup -> {
			ActivityGroupVo activityGroupVo = new ActivityGroupVo();
			BeanUtils.copyProperties(activityGroupVo, activityGroup);
			List<Long> productIds = activityProductService
					.findProductIds(activityGroup.getActivityGroupId(), ActivityType.GROUP, 3);
			List<ProductWxVo> productWxVoList = productMapper.selectByProductIds(productIds);
			if (!productWxVoList.isEmpty()) {
				productWxVoList.forEach(productWxVo -> {
					productWxVo.setProductSpecClassList(null);
					productWxVo.setProductSpecItemList(null);
					productWxVo.setProductSpecList(null);
				});
				activityGroupVo.setProductList(productWxVoList);
			}
			list.add(activityGroupVo);
		});

		return list;
	}

	@Override
	public int batchDelete(String[] activityGroupId, String appmodelId) {
		for (int i = 0; i < activityGroupId.length; i++) {
			Map<String, Integer> map = new HashMap<>();
			map.put("activityId", Integer.valueOf(activityGroupId[i]));
			map.put("activityType", ActivityType.GROUP);
			activityProductMapper.deleteByActivityId(activityGroupId, ActivityType.GROUP, appmodelId);
			ActivityGroup activityGroup = tActivityGroupMapper.selectByPrimaryKey(Integer.valueOf(activityGroupId[i]));
			if (activityGroup.getNowState().equals(ActivityState.READY) || activityGroup.getNowState()
					.equals(ActivityState.STARE)) {
				List<ActivityProduct> activityProducts = activityProductMapper.selectByActivityId(map);
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
		return tActivityGroupMapper.batchDelete(activityGroupId);
	}


	@Override
	public int updateActivityGroup(ActivityGroupVo activityGroupVo) {

		int rs = 0;
		Integer activityGroupId = activityGroupVo.getActivityGroupId();
		//删除活动商品
		activityProductService
				.deleteByActivityId(activityGroupId.toString(), ActivityType.GROUP, activityGroupVo.getAppmodelId());

		//重新创建活动
		activityGroupVo.setNowState(ActivityState.READY);
		activityGroupVo.setActivityGroupId(null);
		rs += saveActivityGroup(activityGroupVo);

		return rs;
	}

	@Override
	public ActivityGroup selectByActivityGroupIdStart(Integer activityGroupId) {
		return tActivityGroupMapper.selectByActivityGroupIdStart(activityGroupId);
	}

	@Override
	public ActivityGroup selectByActivityGroupIdEnd(Integer activityGroupId) {
		return tActivityGroupMapper.selectByActivityGroupIdEnd(activityGroupId);
	}

	@Override
	public List<ActivityGroup> selectNotEnd(Map<String, Object> mapForGroup) {
		return tActivityGroupMapper.selectNotEnd(mapForGroup);
	}

	@Override
	public void updateEndDate(Map<String, Object> map) {
		tActivityGroupMapper.updateEndDate(map);
	}

	@Override
	public List<ActivityGroup> findConflictingGroups(String startTime, String endTime, String appmodelId) {
		try {
			//查询已开始,未开始,进行中的秒杀活动判断是否存在时间冲突
			Date createStartDate = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.parse(startTime);
			Date createEndDate = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.parse(endTime);
			return tActivityGroupMapper
					.findConflictingSeckill(createStartDate.getTime(), createEndDate.getTime(), appmodelId);
		} catch (ParseException e) {
			e.printStackTrace();
			return new ArrayList<>(0);
		}
	}

}
