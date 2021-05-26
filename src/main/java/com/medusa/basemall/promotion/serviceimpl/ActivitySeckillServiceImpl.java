package com.medusa.basemall.promotion.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.google.common.base.Joiner;
import com.medusa.basemall.constant.ActiviMqQueueName;
import com.medusa.basemall.constant.ActivityState;
import com.medusa.basemall.constant.ActivityType;
import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.core.ServiceException;
import com.medusa.basemall.integral.vo.FindProductVo;
import com.medusa.basemall.jobhandler.ActiveDelaySendJobHandler;
import com.medusa.basemall.order.service.OrderService;
import com.medusa.basemall.product.dao.ProductMapper;
import com.medusa.basemall.product.entity.ProductSpecClass;
import com.medusa.basemall.product.entity.ProductSpecItem;
import com.medusa.basemall.product.service.ProductSpecClassService;
import com.medusa.basemall.product.service.ProductSpecItemService;
import com.medusa.basemall.promotion.common.ActivityUtils;
import com.medusa.basemall.promotion.dao.ActivitySeckillMapper;
import com.medusa.basemall.promotion.entity.ActivityProduct;
import com.medusa.basemall.promotion.entity.ActivityProductStock;
import com.medusa.basemall.promotion.entity.ActivitySeckill;
import com.medusa.basemall.promotion.service.ActivityProductService;
import com.medusa.basemall.promotion.service.ActivityProductStockService;
import com.medusa.basemall.promotion.service.ActivitySeckillService;
import com.medusa.basemall.promotion.vo.ActivitySeckillDetailVo;
import com.medusa.basemall.promotion.vo.ActivitySeckillVo;
import com.medusa.basemall.promotion.vo.JoinActityProductVO;
import com.medusa.basemall.promotion.vo.OptionalProductItems;
import com.medusa.basemall.utils.IdGenerateUtils;
import com.medusa.basemall.utils.TimeUtil;
import com.vip.vjtools.vjkit.collection.ListUtil;
import com.vip.vjtools.vjkit.mapper.BeanMapper;
import com.vip.vjtools.vjkit.time.ClockUtil;
import com.vip.vjtools.vjkit.time.DateFormatUtil;
import com.vip.vjtools.vjkit.time.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author Created by psy on 2018/05/30.
 */
@Service
public class ActivitySeckillServiceImpl extends AbstractService<ActivitySeckill> implements ActivitySeckillService {

	@Resource
	private ActivitySeckillMapper tActivitySeckillMapper;
	@Resource
	private ProductMapper productMapper;
	@Autowired
	private ActivityUtils activityUtils;
	@Resource
	private ActiveDelaySendJobHandler activeDelaySendJobHandler;
	@Resource
	private ActivityProductService activityProductService;
	@Autowired
	private ProductSpecItemService productSpecItemService;
	@Autowired
	private ProductSpecClassService productSpecClassService;
	@Autowired
	private ActivityProductStockService activityProductStockService;
	@Autowired
	private OrderService orderService;


	@Override
	public List<OptionalProductItems> findProductForSeckill(FindProductVo findProductVo) {
		if (findProductVo.getStartDate() == null || findProductVo.getEndDate() == null) {
			throw new ServiceException("活动时间不能为空");
		}
		//结束时间减一秒
		findProductVo.setEndDate(ActivityUtils.setEndDate(findProductVo.getEndDate(), 2));
		//查询是否存在冲突的秒杀活动
		if (findConflictingSeckill(findProductVo.getStartDate(), findProductVo.getEndDate(),
				findProductVo.getAppmodelId()).size() > 0 && findProductVo.getInsertIf().equals(true)) {
			throw new ServiceException("活动时间存在冲突,无法创建");
		}
		//查询创建的时间段内是否存在其他活动,如果存在其他活动则排除其他活动中的商品
		List<Long> productIds = activityUtils
				.findRestsActivityConflicting(findProductVo.getAppmodelId(), ActivityType.SECONDKILL,
						findProductVo.getStartDate(), findProductVo.getEndDate());
		PageHelper.startPage(findProductVo.getPageNum(), findProductVo.getPageSize());
		List<OptionalProductItems> productWxVos = getOptionalProductItems(findProductVo.getAppmodelId(), productIds, 1);
		productIds = productWxVos.stream().map(obj->obj.getProductId()).collect(Collectors.toList());
		//查询商品是否在参加其他未开始或进行中的活动
		List<ActivityProduct> activityProducts = activityProductService
				.findProductJoinActivityNotEnds(productIds, ActivityType.SECONDKILL);
		if (activityProducts != null && activityProducts.size() > 0) {
			//按商品id进行分组
			Map<Long, List<ActivityProduct>> activityProductMap = activityProducts.stream()
					.collect(Collectors.groupingBy(ActivityProduct::getProductId));
			for (OptionalProductItems productWxVo : productWxVos) {
				List<ActivityProduct> activityProductList = activityProductMap.get(productWxVo.getProductId());
				if (ListUtil.isNotEmpty(activityProductList)) {
					for (ActivityProduct activityProduct : activityProductList) {
						int stock = 0;
						if (activityProduct.getSpecType().equals(false)) {
							ActivityProductStock productStock = activityProductStockService
									.findByActivityIdAndActivityTypeAndActivityProductId(
											activityProduct.getActivityId(), activityProduct.getActivityType(),
											activityProduct.getActivityProductId());
							stock =  productWxVo.getSort() - productStock.getActivityStock();
						}
						if (activityProduct.getSpecType().equals(true)) {
							stock =  productWxVo.getStock() - activityProduct.getActivityStock();
						}
							productWxVo.setStock(stock);
					}
				}
			}
		}
		return productWxVos;
	}

	private List<OptionalProductItems> getOptionalProductItems(String appmodelId, List<Long> productIds, Integer type) {
		List<OptionalProductItems> productWxVos = productMapper.selectNotinOrInIdsSpec(productIds, appmodelId, type);
		if (productWxVos != null && productWxVos.size() > 0) {
			for (OptionalProductItems productWxVo : productWxVos) {
				if (productWxVo.getProductSpecItemList() != null && productWxVo.getProductSpecItemList().size() > 0) {
					//商品选中的规格分类
					List<ProductSpecClass> productSpecClassList = productSpecClassService
							.findByProductId(productWxVo.getProductId(), productWxVo.getAppmodelId());
					productWxVo.setProductSpecClassList(productSpecClassList);
				}
			}
		}
		return productWxVos;
	}

	/**
	 * 查询指定时间段之内是否存在冲突的秒杀活动
	 * @param startDate  开始时间
	 * @param endDate   结束时间
	 * @param appmodelId  商家appmodelId
	 */
	@Override
	public List<ActivitySeckill> findConflictingSeckill(String startDate, String endDate, String appmodelId) {
		//查询已开始,未开始,进行中的秒杀活动判断是否存在时间冲突
		if (TimeUtil.startdateGreaterthanEnddate(startDate, endDate)) {
			throw new ServiceException("日期出错,开始时间必须小于结束时间");
		}
		return tActivitySeckillMapper.findConflictingSeckill(startDate, endDate, appmodelId);
	}

	@Override
	public ActivitySeckillDetailVo findseckillDetail(Integer activitySeckillId, String appmodelId) {
		if (activitySeckillId == null || StringUtils.isEmpty(appmodelId)) {
			throw new ServiceException("参数不能为null");
		}
		ActivitySeckill activitySeckill = tActivitySeckillMapper.selectByPrimaryKey(activitySeckillId);
		if (activitySeckill == null) {
			throw new ServiceException("活动不存在");
		}
		ActivitySeckillDetailVo seckillDetailVo = BeanMapper.map(activitySeckill, ActivitySeckillDetailVo.class);
		//封装商品信息
		List<ActivityProduct> activityProducts = activityProductService
				.findActivityProduct(activitySeckillId.toString(), ActivityType.SECONDKILL, appmodelId);
		if (activityProducts.isEmpty()) {
			throw new ServiceException("活动无任何商品");
		}
		Map<Long, ActivityProduct> activityProductMap = activityProducts.stream()
				.collect(Collectors.toMap(k -> k.getProductId(), v -> v));
		//获取指定商品
		List<Long> productIds = activityProducts.stream().map(obj -> obj.getProductId()).collect(Collectors.toList());
		List<OptionalProductItems> optionalProductItems = getOptionalProductItems(appmodelId, productIds, 2);
		for (OptionalProductItems optionalProductItem : optionalProductItems) {
			ActivityProduct activityProduct = activityProductMap.get(Long.valueOf(optionalProductItem.getProductId()));
			optionalProductItem.setActivityStock(activityProduct.getActivityStock());
			optionalProductItem.setMaxQuantity(activityProduct.getMaxQuantity());
			optionalProductItem.setActivityDiscount(activityProduct.getActivityDiscount());
			optionalProductItem.setHomeViewStat(activityProduct.getHomeViewStat());
			optionalProductItem.setSort(activityProduct.getSort());
			if (activityProduct.getSpecType().equals(false)) {
				List<ActivityProductStock> activityProductStocks = activityProductStockService
						.findActivityProductSpec(activityProduct.getActivityProductId());
				Map<Long, ActivityProductStock> activityProductStockMap = activityProductStocks.stream()
						.collect(Collectors.toMap(k -> k.getProductSpecItemId(), v -> v));
				for (ProductSpecItem specItem : optionalProductItem.getProductSpecItemList()) {
					ActivityProductStock productStock = activityProductStockMap.get(specItem.getProductSpecItemId());
					specItem.setActivityStock(productStock.getActivityStock());
				}
			}
		}
		optionalProductItems.sort(Comparator.comparing(obj -> obj.getSort()));
		seckillDetailVo.setOptionalProductItems(optionalProductItems);
		seckillDetailVo.setEndDate(ActivityUtils.setEndDate(activitySeckill.getEndDate(), 1));
		return seckillDetailVo;
	}

	@Override
	public List<ActivitySeckill> selectThatVeryDaySeckillActivity(String appmodelId) {
		Date currentDate = ClockUtil.currentDate();
		String start = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.format(DateUtil.beginOfDate(currentDate));
		String end = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.format(DateUtil.endOfDate(currentDate));
		return tActivitySeckillMapper.selectThatVeryDaySeckillActivity(start, end, appmodelId);
	}

	@Override
	public ActivitySeckill findPreheatActivitySeckill(String appmodelId) {
		Condition condition = new Condition(ActivitySeckill.class);
		condition.createCriteria().andEqualTo("nowState", 1).andEqualTo("appmodelId", appmodelId)
				.andEqualTo("deleteState");
		condition.orderBy("startDate").asc();
		List<ActivitySeckill> activitySeckills = tActivitySeckillMapper.selectByCondition(condition);
		if (activitySeckills == null || activitySeckills.size() == 0) {
			throw new ServiceException("秒杀数据异常");
		}
		return activitySeckills.get(0);
	}


	/**
	 * 创建活动、活动商品
	 * 创建失败，请检查选择的商品是否与其他活动时间冲突
	 * @param activitySeckillVo
	 * @return
	 */

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int saveActivitySeckill(ActivitySeckillVo activitySeckillVo) {
		if (activitySeckillVo == null) {
			throw new ServiceException("数据错误");
		}
		List<JoinActityProductVO> productList = activitySeckillVo.getJoinActityProductVO();
		if (productList.isEmpty()) {
			throw new ServiceException("商品不能为空");
		}
		//查询是否存在冲突的秒杀活动(再次查询保证数据安全性)
		if (findConflictingSeckill(activitySeckillVo.getStartDate(), activitySeckillVo.getEndDate(),
				activitySeckillVo.getAppmodelId()).size() > 0 && activitySeckillVo.getActivitySeckillId() == null) {
			throw new ServiceException("活动时间存在冲突,无法创建");
		}
		//查询判断预热时间是否正常
		if (StringUtils.isNotBlank(activitySeckillVo.getPreheatTime()) && TimeUtil
				.startdateGreaterthanEnddate(activitySeckillVo.getPreheatTime(), activitySeckillVo.getStartDate())) {
			throw new ServiceException("预热时间出错,预热时间必须小于开始时间");
		}
		//结束时间减一秒
		activitySeckillVo.setEndDate(ActivityUtils.setEndDate(activitySeckillVo.getEndDate(), 2));
		activitySeckillVo.setActivitySeckillId(null);
		activitySeckillVo.setDeleteState(false);
		activitySeckillVo.setNowState(ActivityState.READY);
		activitySeckillVo.setCreateTime(TimeUtil.getNowTime());
		tActivitySeckillMapper.insertSelective(activitySeckillVo);
		//新增活动商品
		int i = 0;
		for (JoinActityProductVO joinActityProductVO : productList) {
			ActivityProduct ap = BeanMapper.map(joinActityProductVO, ActivityProduct.class);
			ap.setActivityType(ActivityType.SECONDKILL);
			ap.setActivityProductId(IdGenerateUtils.getItemId());
			ap.setActivityId(activitySeckillVo.getActivitySeckillId());
			ap.setAppmodelId(activitySeckillVo.getAppmodelId());
			ap.setSoldQuantity(0);
			ap.setPreheatState(0);
			ap.setSort(i);
			if (StringUtils.isNotBlank(activitySeckillVo.getPreheatTime())) {
				ap.setPreheatState(1);
			}
			//多规格活动商品
			List<ActivityProductStock> activityProductStocks = joinActityProductVO.getActivityProductStocks();
			//商品总库存抽取在活动开始时抽取,创建活动时只做预扣,活动结束归还
			if (activityProductStocks != null && !activityProductStocks.isEmpty()) {
				ap.setSpecType(false);
				//查询所有多规格信息
				List<Long> productSpecItemIds = activityProductStocks.stream()
						.map(ActivityProductStock::getProductSpecItemId).collect(Collectors.toList());
				List<ProductSpecItem> productSpecItems = productSpecItemService
						.findByIds(Joiner.on(",").join(productSpecItemIds));
				Map<Long, ActivityProductStock> stockMap = activityProductStocks.stream()
						.collect(Collectors.toMap(key -> key.getProductSpecItemId(), value -> value));
				//商品原库存预减抽出至活动商品库存(活动结束有剩余库存归还)
				for (ProductSpecItem productSpecItem : productSpecItems) {
					productSpecItem
							.setActivityStock(stockMap.get(productSpecItem.getProductSpecItemId()).getActivityStock());
					productSpecItem
							.setActivityPrice(stockMap.get(productSpecItem.getProductSpecItemId()).getActivityPrice());
					productSpecItemService.update(productSpecItem);
				}
				ap.setActivityStock(
						activityProductStocks.stream().mapToInt(ActivityProductStock::getActivityStock).sum());
				activityProductStocks.stream().forEach(obj -> {
					obj.setActivityProductId(ap.getActivityProductId());
					obj.setActivityType(ActivityType.SECONDKILL);
					obj.setActivityId(activitySeckillVo.getActivitySeckillId());
					obj.setAppmodelId(activitySeckillVo.getAppmodelId());
				});
				activityProductStockService.save(activityProductStocks);
			}
			activityProductService.saveActivityProduct(ap);
			i++;
		}
		//发送活动开始时间(如有预热时间则发送预热开始时间)
		Long sendTime = 0L;
		if (StringUtils.isNotBlank(activitySeckillVo.getPreheatTime())) {
			sendTime = TimeUtil.endTiemSubtractStart(activitySeckillVo.getPreheatTime());
		} else {
			sendTime = TimeUtil.endTiemSubtractStart(activitySeckillVo.getStartDate());
		}
		//如果有预热时间需要提前锁定商品,使其不可购买
		activeDelaySendJobHandler
				.savaTask(activitySeckillVo.getActivitySeckillId().toString(), ActiviMqQueueName.ACTIVITY_SECKILL_START,
						sendTime, activitySeckillVo.getAppmodelId());
		return 1;
	}

	/**
	 * 后端查询秒杀活动
	 * @param appmodelId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Override
	public List<ActivitySeckillVo> findByAppmodelId(String appmodelId, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ActivitySeckill> activitySeckills = tActivitySeckillMapper.findByAppmodelId(appmodelId);
		if (activitySeckills != null) {
			List<ActivitySeckillVo> activitySeckillVos = BeanMapper.mapList(activitySeckills, ActivitySeckillVo.class);
			activitySeckillVos.parallelStream().forEach(activitySeckill->{
				activitySeckill.setNumberOfPeople(orderService
						.findJoinActivityNumberOfPeople(activitySeckill.getActivitySeckillId(), ActivityType.SECONDKILL,
								appmodelId));
				activitySeckill.setOrderSum(orderService
						.findActivityOrderPayOkSum(activitySeckill.getActivitySeckillId(), ActivityType.SECONDKILL,
								appmodelId));
				activitySeckill.setPayFeeTotle(orderService
						.findActivityOrdertotleFee(activitySeckill.getActivitySeckillId(), ActivityType.SECONDKILL,
								appmodelId));
				activitySeckill.setProductSum(activityProductService
						.selectActivityProductSum(activitySeckill.getActivitySeckillId(), ActivityType.SECONDKILL,
								appmodelId));
				//结束时间加一秒
				activitySeckill.setEndDate(ActivityUtils.setEndDate(activitySeckill.getEndDate(), 1));
			});
			return activitySeckillVos;
		}
		return new ArrayList<>();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int batchDelete(String activitySpecialIds, String appmodelId) {
		if (!StringUtils.isNotBlank(activitySpecialIds)) {
			throw new ServiceException("id不能传空");
		}
		if (!StringUtils.isNotBlank(appmodelId)) {
			throw new ServiceException("appmodelId不能传空");
		}
		List<ActivitySeckill> activitySeckill = tActivitySeckillMapper.selectByIds(activitySpecialIds);
		activitySeckill.forEach(seckill -> {
			//如果是开始或进行时的活动需要归还库存
			if (seckill.getNowState().equals(ActivityState.READY) || seckill.getNowState()
					.equals(ActivityState.STARE)) {
				activityUtils.activityProductStockReturn(seckill.getActivitySeckillId().toString(), appmodelId);
			}
		});
		//删除活动标签
		activityProductService.deleteByActivityId(activitySpecialIds, ActivityType.SECONDKILL, appmodelId);
		return tActivitySeckillMapper.batchDelete(activitySpecialIds.split(","), appmodelId);
	}

	@Override
	public int updateActivitySeckill(ActivitySeckillVo activitySeckillVo) {
		int rs = 0;
		Integer activitySeckillId = activitySeckillVo.getActivitySeckillId();
		if (activitySeckillVo.getType().equals(1)) {
			ActivitySeckill activitySeckill = new ActivitySeckill();
			activitySeckill.setActivitySeckillId(activitySeckillId);
			activitySeckill.setActivitySeckillName(activitySeckillVo.getActivitySeckillName());
			rs = update(activitySeckill);
		}
		if (activitySeckillVo.getType().equals(2)) {
			//删除活动商品
			activityProductService.deleteByActivityId(activitySeckillId.toString(), ActivityType.SECONDKILL,
					activitySeckillVo.getAppmodelId());
			//重新创建活动
			activitySeckillVo.setNowState(ActivityState.READY);
			deleteById(activitySeckillVo.getActivitySeckillId());
			rs = saveActivitySeckill(activitySeckillVo);

		}
		return rs;
	}

	@Override
	public ActivitySeckill findByActivitySeckillIdStart(Integer activitySeckillId) {
		return tActivitySeckillMapper.findByActivitySeckillIdStart(activitySeckillId);
	}

	@Override
	public ActivitySeckill findByActivitySeckillIdEnd(Integer activitySeckillId) {
		return tActivitySeckillMapper.findByActivitySeckillIdEnd(activitySeckillId);
	}

	@Override
	public List<ActivitySeckill> selectNotEnd(String appmodelId) {
		return tActivitySeckillMapper.selectNotEnd(appmodelId);
	}

	@Override
	public void updateEndDate(Map<String, Object> map) {
		tActivitySeckillMapper.updateEndDate(map);
	}

	@Override
	public ActivitySeckill findUnderwayActivitySeckill(String appmodelId) {
		ActivitySeckill activitySeckill = new ActivitySeckill();
		activitySeckill.setNowState(2);
		activitySeckill.setDeleteState(false);
		activitySeckill.setAppmodelId(appmodelId);

		return tActivitySeckillMapper.selectOne(activitySeckill);
	}

}
