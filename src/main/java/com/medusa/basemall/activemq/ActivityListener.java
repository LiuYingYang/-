package com.medusa.basemall.activemq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.medusa.basemall.configurer.WxServiceUtils;
import com.medusa.basemall.constant.ActiviMqQueueName;
import com.medusa.basemall.constant.ActivityState;
import com.medusa.basemall.constant.ActivityType;
import com.medusa.basemall.core.ServiceException;
import com.medusa.basemall.integral.dao.PrizeOrderMapper;
import com.medusa.basemall.integral.entity.PrizeOrder;
import com.medusa.basemall.jobhandler.ActiveDelaySendJobHandler;
import com.medusa.basemall.order.entity.Order;
import com.medusa.basemall.order.entity.OrderDetail;
import com.medusa.basemall.order.service.BuycarService;
import com.medusa.basemall.order.service.OrderDetailService;
import com.medusa.basemall.order.service.OrderService;
import com.medusa.basemall.product.dao.ProductMapper;
import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.product.entity.ProductSpecItem;
import com.medusa.basemall.product.service.ProductService;
import com.medusa.basemall.product.service.ProductSpecItemService;
import com.medusa.basemall.promotion.common.ActivityUtils;
import com.medusa.basemall.promotion.dao.CouponWxuserMapper;
import com.medusa.basemall.promotion.dao.GroupMemberMapper;
import com.medusa.basemall.promotion.entity.*;
import com.medusa.basemall.promotion.service.*;
import com.medusa.basemall.user.service.CollectService;
import com.medusa.basemall.user.service.FootMarkService;
import com.medusa.basemall.user.service.WxuserService;
import com.medusa.basemall.utils.TimeUtil;
import com.vip.vjtools.vjkit.collection.ListUtil;
import com.vip.vjtools.vjkit.time.ClockUtil;
import com.vip.vjtools.vjkit.time.DateFormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 活动消息接收类
 *
 * @author whh
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class ActivityListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityListener.class);

	@Autowired
	public WxuserService wxuserService;

	@Autowired
	public OrderService orderService;

	@Autowired
	private ActivityGroupService activityGroupService;

	@Autowired
	private ActivitySpecialService activitySpecialService;

	@Autowired
	private ActivitySeckillService activitySeckillService;

	@Resource
	private ActiveDelaySendJobHandler activeDelaySendJobHandler;

	@Autowired
	private ActivityCouponService activityCouponService;

	@Autowired
	private ActivityProductService activityProductService;

	@Autowired
	private GroupService groupService;

	@Resource
	private GroupMemberMapper groupMemberMapper;

	@Autowired
	private GroupMemberService groupMemberService;

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private ProductService productService;
	@Autowired
	private CollectService collectService;
	@Autowired
	private BuycarService buycarService;
	@Autowired
	private FootMarkService footMarkService;
	@Autowired
	private ProductSpecItemService productSpecItemService;
	@Resource
	private CouponWxuserMapper couponWxuserMapper;
	@Resource
	private CouponService couponService;
	@Resource
	private PrizeOrderMapper prizeOrderMapper;
	@Resource
	private ProductMapper productMapper;
	@Resource
	private WxServiceUtils wxServiceUtils;
	@Resource
	private ActivityProductStockService activityProductStockService;
	@Autowired
	private ActivityUtils activityUtils;


	/**
	 * 团购活动时间限制
	 */

	@JmsListener(destination = ActiviMqQueueName.ACTIVITY_GROUP_START)
	public void activiGroupStart(String jsonDate) {
		JSONObject jsonObject = JSONObject.parseObject(jsonDate);
		ActivityGroup activityGroup = activityGroupService.selectByActivityGroupIdStart(jsonObject.getInteger("id"));
		if (activityGroup != null) {
			if (System.currentTimeMillis() >= TimeUtil.str2Timestamp(activityGroup.getStartDate())
					&& System.currentTimeMillis() <= TimeUtil.str2Timestamp(activityGroup.getStartDate()) + 10000) {
				if (findActiviGroup(activityGroup) > 0) {
					LOGGER.info("活动开始");
					activeDelaySendJobHandler.savaTask(activityGroup.getActivityGroupId().toString(),
							ActiviMqQueueName.ACTIVITY_GROUP_START,
							TimeUtil.str2Timestamp(activityGroup.getEndDate()) - TimeUtil
									.str2Timestamp(activityGroup.getCreateTime()), activityGroup.getAppmodelId());
				}
			}
		}
		activeDelaySendJobHandler.updateState(jsonObject.getString("activeMqTaskTOId"));
	}

	@JmsListener(destination = ActiviMqQueueName.ACTIVITY_GROUP_END)
	public void activiGroupEnd(String jsonDate) {
		JSONObject jsonObject = JSONObject.parseObject(jsonDate);
		ActivityGroup activityGroup = activityGroupService.selectByActivityGroupIdEnd(jsonObject.getInteger("id"));
		if (activityGroup != null) {
			if (System.currentTimeMillis() >= TimeUtil.str2Timestamp(activityGroup.getEndDate())
					&& System.currentTimeMillis() <= TimeUtil.str2Timestamp(activityGroup.getEndDate()) + 10000) {
				if (findActiviGroup(activityGroup) > 0) {
					LOGGER.info("活动结束");
					// 活动结束删除活动商品
					List<ActivityProduct> activityProducts = activityProductService
							.findActivityProduct(jsonObject.getString("id"), ActivityType.SPECIAL,
									activityGroup.getAppmodelId());
					/*if (activityProducts.size() > 0) {
						for (ActivityProduct activityProduct : activityProducts) {
							Product product = productMapper.selectByPrimaryKey(activityProduct.getProductId());
							List<ActivityProductStock> activityProductStocks = activityProductStockService
									.findByAcitivityProductId(activityProduct.getActivityProductId());
							if (activityProductStocks.size() > 0) {
								for (ActivityProductStock activityProductStock : activityProductStocks) {
									// 多规格库存返回
									ProductSpecItem productSpecItem = productSpecItemService
											.findById(activityProductStock.getProductSpecItemId());
									productSpecItem.setStock(
											productSpecItem.getStock() + activityProductStock.getActivityStock());
									productSpecItemService.update(productSpecItem);
								}
							}
							// 总库存返回
							product.setStock(product.getStock() + activityProduct.getActivityStock());
							productMapper.updateByPrimaryKeySelective(product);
							activityProductService.deleteByActivityId(jsonObject.getString("id"), ActivityType.GROUP,
									activityGroup.getAppmodelId());
						}
					}*/
				}
			}
		}
		activeDelaySendJobHandler.updateState(jsonObject.getString("activeMqTaskTOId"));
	}

	private int findActiviGroup(ActivityGroup activityGroup) {
		if (activityGroup.getNowState().equals(ActivityState.READY)) {
			activityGroup.setNowState(ActivityState.STARE);
			return activityGroupService.update(activityGroup);
		}
		if (activityGroup.getNowState().equals(ActivityState.STARE)) {
			activityGroup.setNowState(ActivityState.OVER);
			return activityGroupService.update(activityGroup);
		}
		return 0;
	}

	/**
	 * 特价活动时间限制
	 */

	@JmsListener(destination = ActiviMqQueueName.ACTIVITY_SPECIAL_START)
	public void activiSpecialStart(String jsonDate) {
		JSONObject jsonObject = JSONObject.parseObject(jsonDate);
		ActivitySpecial activitySpecial = activitySpecialService
				.findByActivitySpecialIdStart(jsonObject.getInteger("id"));
		if (activitySpecial != null) {
			if (System.currentTimeMillis() >= TimeUtil.str2Timestamp(activitySpecial.getStartDate())
					&& System.currentTimeMillis() <= TimeUtil.str2Timestamp(activitySpecial.getStartDate()) + 10000) {
				if (findActiviSpecial(activitySpecial) > 0) {
					LOGGER.info("活动开始");
					activeDelaySendJobHandler.savaTask(activitySpecial.getActivitySpecialId().toString(),
							ActiviMqQueueName.ACTIVITY_SPECIAL_END,
							TimeUtil.str2Timestamp(activitySpecial.getEndDate()) - TimeUtil
									.str2Timestamp(activitySpecial.getCreateTime()), activitySpecial.getAppmodelId());
				}
			}
		}
		activeDelaySendJobHandler.updateState(jsonObject.getString("activeMqTaskTOId"));
	}

	@JmsListener(destination = ActiviMqQueueName.ACTIVITY_SPECIAL_END)
	public void activiSpecialEnd(String jsonDate) {
		JSONObject jsonObject = JSONObject.parseObject(jsonDate);
		ActivitySpecial activitySpecial = activitySpecialService
				.findByActivitySpecialIdEnd(jsonObject.getInteger("id"));
		if (activitySpecial != null) {
			if (System.currentTimeMillis() >= TimeUtil.str2Timestamp(activitySpecial.getEndDate())
					&& System.currentTimeMillis() <= TimeUtil.str2Timestamp(activitySpecial.getEndDate()) + 10000) {
				if (findActiviSpecial(activitySpecial) > 0) {
					LOGGER.info("活动结束");
					// 活动结束删除活动商品
					List<ActivityProduct> activityProducts = activityProductService
							.findActivityProduct(jsonObject.getString("id"), ActivityType.SPECIAL,
									activitySpecial.getAppmodelId());
					if (activityProducts.size() > 0) {
						for (ActivityProduct activityProduct : activityProducts) {
							activityProductService.deleteByActivityId(jsonObject.getString("id"), ActivityType.SPECIAL,
									activitySpecial.getAppmodelId());
						}
					}
				}
			}
		}
		activeDelaySendJobHandler.updateState(jsonObject.getString("activeMqTaskTOId"));
	}

	private int findActiviSpecial(ActivitySpecial activitySpecial) {
		if (activitySpecial.getNowState().equals(ActivityState.READY)) {
			activitySpecial.setNowState(ActivityState.STARE);
			return activitySpecialService.update(activitySpecial);
		}
		if (activitySpecial.getNowState().equals(ActivityState.STARE)) {
			activitySpecial.setNowState(ActivityState.OVER);
			return activitySpecialService.update(activitySpecial);
		}
		return 0;
	}

	/**
	 * 查询预热时间段到开始时间段内是否有进行中的活动,存在则判断商品是否在活动中,在则不为此商品添加标签
	 * @param preheatTime
	 * @param activityType
	 * @param activityId
	 * @param appmodelId
	 */
	private void activityTimeIsConflict(String preheatTime, Integer activityType, Integer activityId,
			String appmodelId) {
		//活动商品
		List<ActivityProduct> activityProduct = activityProductService
				.findActivityProduct(activityId.toString(), activityType, appmodelId);
		//时间段之内正在才加活动的商品
		List<ActivityProduct> activityProducts = activityProductService
				.findPreheatTimetoStartDateActivityProduct(preheatTime, appmodelId, activityType, activityId);

		List<ActivityProduct> addMark = new ArrayList<>();
		if (ListUtil.isNotEmpty(activityProducts)) {
			Map<Long, ActivityProduct> activityProductMap = activityProducts.stream()
					.collect(Collectors.toMap(key -> key.getActivityProductId(), v -> v));
			for (ActivityProduct product : activityProduct) {
				ActivityProduct a = activityProductMap.get(product.getActivityProductId());
				if (a == null) {
					addMark.add(product);
				}
			}
		} else {
			addMark.addAll(activityProduct);
		}
		String productIds = addMark.stream().map(obj -> obj.getProductId().toString()).collect(Collectors.joining(","));
		productAddMark(productIds, activityType, activityId, appmodelId);
	}

	/**
	 * 为指定的活动商品添加商品标示
	 * 如果商品正在进行某项
	 * @param activityType
	 * @param activityId
	 * @param activityId
	 * @param appmodelId
	 */
	private List<Product> productAddMark(String productIds, Integer activityType, Integer activityId,
			String appmodelId) {
		Condition condition = new Condition(Product.class);
		condition.createCriteria().andIn("productId",  Arrays.asList(productIds.split(",")))
				.andEqualTo("deleteState", false).andEqualTo("shelfState",0);
		List<Product> products = productService.findByCondition(condition);
		products.forEach(obj -> {
			if (obj.getProductTypeList() != null && !obj.getProductTypeList().contains(activityType.toString())) {
				String productTypeList = ActivityUtils.addProductTypeList(obj.getProductTypeList(), activityType);
				obj.setProductTypeList(productTypeList);
				productService.update(obj);
				//收藏,足迹添加标记
				collectService.updateActivityMark(obj, appmodelId);
				footMarkService.updateActivityMark(obj, appmodelId);
			}
		});
		return products;
	}

	/**
	 * 秒杀活动时间限制
	 */
	@JmsListener(destination = ActiviMqQueueName.ACTIVITY_SECKILL_START)
	public void activiScekilStart(String jsonDate) {
		JSONObject jsonObject = JSONObject.parseObject(jsonDate);
		ActivitySeckill activitySeckill = activitySeckillService
				.findByActivitySeckillIdStart(jsonObject.getInteger("id"));
		if (activitySeckill != null) {
			//当前接收到的是预热时间,锁定商品不可购买,重新计算时间并发送开始时间
			boolean isPreheat = activitySeckill.getPreheatTime().length() > 0;
			if (isPreheat && !TimeUtil
					.startdateGreaterthanEnddate(activitySeckill.getPreheatTime(), activitySeckill.getStartDate())
					&& !TimeUtil.startdateGreaterthanEnddate(
					DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.format(ClockUtil.currentDate()),
					activitySeckill.getStartDate())) {
				//查询预热时间段到开始时间段内是否有进行中的活动,存在则判断商品是否在活动中,在则不为此商品添加标签
				activityTimeIsConflict(activitySeckill.getPreheatTime(), ActivityType.SECONDKILL,
						activitySeckill.getActivitySeckillId(), activitySeckill.getAppmodelId());
				activeDelaySendJobHandler.savaTask(activitySeckill.getActivitySeckillId().toString(),
						ActiviMqQueueName.ACTIVITY_SECKILL_START,
						TimeUtil.endTiemSubtractStart(activitySeckill.getStartDate()), activitySeckill.getAppmodelId());
			} else if (findActiviSeckil(activitySeckill) > 0) {
				LOGGER.info("秒杀活动开始:" + JSON.toJSONString(activitySeckill));
				if (isPreheat) {
					activityProductService
							.updatePreheatState(activitySeckill.getActivitySeckillId(), ActivityType.SECONDKILL,
									activitySeckill.getAppmodelId(), 0);
				}
				//为未添加标签的商品添加标签(商品,收藏,足迹) && 活动开始活动商品对应商品库存减少
				List<ActivityProduct> activityProducts = activityProductService
						.findActivityProduct(activitySeckill.getActivitySeckillId().toString(), ActivityType.SECONDKILL,
								activitySeckill.getAppmodelId());
				String productIds = activityProducts.stream().map(obj -> obj.getProductId().toString())
						.collect(Collectors.joining(","));
				List<Product> products = productAddMark(productIds, ActivityType.SECONDKILL,
						activitySeckill.getActivitySeckillId(), activitySeckill.getAppmodelId());
				Map<Long, Product> productMap = products.stream()
						.collect(Collectors.toMap(k -> k.getProductId(), v -> v));
				for (ActivityProduct activityProduct : activityProducts) {
					Product product = productMap.get(activityProduct.getProductId());
					product.setStock(product.getStock() - activityProduct.getActivityStock());
					if (activityProduct.getSpecType().equals(0)) {
						List<ProductSpecItem> specItems = productSpecItemService
								.findByProductId(product.getProductId());
						for (ProductSpecItem specItem : specItems) {
							specItem.setStock(specItem.getStock() - specItem.getActivityStock());
							productSpecItemService.update(specItem);
						}
					}
					productService.update(product);
				}
				activeDelaySendJobHandler.savaTask(activitySeckill.getActivitySeckillId().toString(),
						ActiviMqQueueName.ACTIVITY_SECKILL_END,
						TimeUtil.endTiemSubtractStart(activitySeckill.getEndDate()), activitySeckill.getAppmodelId());
			}
		}
		activeDelaySendJobHandler.updateState(jsonObject.getString("activeMqTaskTOId"));
	}


	@JmsListener(destination = ActiviMqQueueName.ACTIVITY_SECKILL_END)
	public void activiScekilEnd(String jsonDate) {
		JSONObject jsonObject = JSONObject.parseObject(jsonDate);
		ActivitySeckill activitySeckill = activitySeckillService
				.findByActivitySeckillIdEnd(jsonObject.getInteger("id"));
		if (activitySeckill != null) {
			if (findActiviSeckil(activitySeckill) > 0) {
				LOGGER.info("秒杀活动结束" + JSON.toJSONString(activitySeckill));
				// 活动结束删除活动商品
				activityProductService.deleteByActivityId(jsonObject.getString("id"), ActivityType.SECONDKILL,
						activitySeckill.getAppmodelId());
			}
		}
		activeDelaySendJobHandler.updateState(jsonObject.getString("activeMqTaskTOId"));
	}

	private int findActiviSeckil(ActivitySeckill activitySeckill) {
		activitySeckill.setUpdateTime(TimeUtil.getNowTime());
		if (activitySeckill.getNowState().equals(ActivityState.READY)) {
			activitySeckill.setNowState(ActivityState.STARE);
			return activitySeckillService.update(activitySeckill);
		}
		if (activitySeckill.getNowState().equals(ActivityState.STARE)) {
			activitySeckill.setNowState(ActivityState.OVER);
			activitySeckillService.update(activitySeckill);
			//库存归还
			activityUtils.activityProductStockReturn(activitySeckill.getActivitySeckillId().toString(),
					activitySeckill.getAppmodelId());
			return 1;
		}
		return 0;
	}

	/**
	 * 优惠券活动时间限制
	 */
	@JmsListener(destination = ActiviMqQueueName.ACTIVITY_COUPON_START)
	public void activiCouponStart(String jsonDate) {
		JSONObject jsonObject = JSONObject.parseObject(jsonDate);
		ActivityCoupon activityCoupon = activityCouponService
				.findByActivityCouponIdToStart(jsonObject.getInteger("id"));
		if (activityCoupon != null) {
			if (findActiviCoupon(activityCoupon) > 0) {
				LOGGER.info("优惠券活动开始" + JSON.toJSONString(activityCoupon));
				activeDelaySendJobHandler.savaTask(activityCoupon.getActivityCouponId().toString(),
						ActiviMqQueueName.ACTIVITY_COUPON_END,
						TimeUtil.endTiemSubtractStart(activityCoupon.getEndDate()), activityCoupon.getAppmodelId());
			}
		}
		activeDelaySendJobHandler.updateState(jsonObject.getString("activeMqTaskTOId"));
	}

	@JmsListener(destination = ActiviMqQueueName.ACTIVITY_COUPON_END)
	public void activiCouponEnd(String jsonDate) {
		JSONObject jsonObject = JSONObject.parseObject(jsonDate);
		ActivityCoupon activityCoupon = activityCouponService.findByActivityCouponIdToEnd(jsonObject.getInteger("id"));
		if (activityCoupon != null) {
			if (findActiviCoupon(activityCoupon) > 0) {
				LOGGER.info("优惠券活动结束" + JSON.toJSONString(activityCoupon));
			}
		}
		activeDelaySendJobHandler.updateState(jsonObject.getString("activeMqTaskTOId"));
	}

	/**
	 * activity coupon start or end
	 * setting activity coupon current state
	 *         start - add product join activity coupon label
	 *         end -r emove product join activity coupon label
	 * @param activityCoupon
	 * @return
	 */
	private int findActiviCoupon(ActivityCoupon activityCoupon) {
		if (activityCoupon.getNowState().equals(ActivityState.READY)) {
			activityCoupon.setNowState(ActivityState.STARE);
			//指定商品参加优惠券活动
			if (activityCoupon.getFullState()) {
				List<Product> products = productMapper.selectByAppmodelId(activityCoupon.getAppmodelId());
				if (products.size() > 0) {
					for (Product product : products) {
						String productTypeList = ActivityUtils
								.addProductTypeList(product.getProductTypeList(), ActivityType.COUPON);
						product.setProductTypeList(productTypeList);
						productMapper.updateByPrimaryKeySelective(product);
					}
				}
			} else {
				List<ActivityProduct> activityProducts = activityProductService
						.findActivityProduct(activityCoupon.getActivityCouponId().toString(), ActivityType.COUPON,
								activityCoupon.getAppmodelId());
				if (activityProducts.size() > 0) {
					for (ActivityProduct activityProduct : activityProducts) {
						Product product = productMapper.selectByPrimaryKey(activityProduct.getProductId());
						String productTypeList = ActivityUtils
								.addProductTypeList(product.getProductTypeList(), ActivityType.COUPON);
						product.setProductTypeList(productTypeList);
						productMapper.updateByPrimaryKeySelective(product);
					}
				}
			}
			return activityCouponService.update(activityCoupon);
		}
		if (activityCoupon.getNowState().equals(ActivityState.STARE)) {
			activityCoupon.setNowState(ActivityState.OVER);
			activityCoupon.setDeleteState(false);
			couponWxuserMapper.updateFlag(activityCoupon.getActivityCouponId());
			couponService.deleteByActivityCouponId(activityCoupon.getActivityCouponId().toString());
			activityProductService
					.deleteByActivityId(activityCoupon.getActivityCouponId().toString(), ActivityType.COUPON,
							activityCoupon.getAppmodelId());
			if (activityCoupon.getFullState() == true) {
				List<Product> products = productMapper.selectByAppmodelId(activityCoupon.getAppmodelId());
				if (products.size() > 0) {
					for (Product product : products) {
						product.setProductTypeList(
								ActivityUtils.removeProductTypeList(product.getProductTypeList(), ActivityType.COUPON));
						productMapper.updateByPrimaryKeySelective(product);
					}
				}
			} else {
				List<ActivityProduct> activityProducts = activityProductService
						.findActivityProduct(activityCoupon.getActivityCouponId().toString(), ActivityType.COUPON,
								activityCoupon.getAppmodelId());
				if (activityProducts.size() > 0) {
					for (ActivityProduct activityProduct : activityProducts) {
						Product product = productMapper.selectByPrimaryKey(activityProduct.getProductId());
						product.setProductTypeList(
								ActivityUtils.removeProductTypeList(product.getProductTypeList(), ActivityType.COUPON));
						productMapper.updateByPrimaryKeySelective(product);
					}
				}
			}
			return activityCouponService.update(activityCoupon);
		}
		return 0;
	}

	/**
	 * 成团失败
	 */
	@JmsListener(destination = ActiviMqQueueName.GROUP_END)
	public void group(String jsonDate) {
		JSONObject jsonObject = JSONObject.parseObject(jsonDate);
		// 团长信息
		GroupMember groupMember = groupMemberService.findById(jsonObject.getInteger("id"));
		// 团信息
		Group group = groupService.findByGroupId(groupMember.getGroupId());
		List<GroupMember> groupMembers = groupMemberMapper.findByGroupId(group.getGroupId());
		for (GroupMember groupMemberNew : groupMembers) {
			Order order = orderService.findById(groupMember.getOrderId());
			String outTradeNo = order.getOutTradeNo();
			if (null != order.getOutTradeNoExt() && !"".equals(order.getOutTradeNoExt())) {
				outTradeNo = order.getOutTradeNoExt();
			}
			Integer payFee = BaseWxPayRequest.yuanToFen(order.getPayFee() + "");
			try {
				wxServiceUtils.wechatRefund(outTradeNo, outTradeNo, payFee, payFee, order.getAppmodelId());
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("退款失败");
			}
			// todo 模板消息拼团失败
		}
		// 订单详情
		List<OrderDetail> orderDetails = orderDetailService.selectByOrderId(groupMember.getOrderId());
		OrderDetail orderDetail = orderDetails.get(0);
		// 活动商品对应商品
		Product product = productService.findById(orderDetail.getProductId());
		// 查询团活动对应活动商品
		ActivityProduct activityProduct = activityProductService.selectByProductId(orderDetail.getProductId());
		if (System.currentTimeMillis() >= TimeUtil.str2Timestamp(group.getEndTime())) {
			group.setGroupState(2);
			int result = groupService.update(group);
			if (result > 0) {
				LOGGER.info("成团失败");
			}
			activityProduct.setActivityStock(activityProduct.getActivityStock() + orderDetail.getQuantity());
			int res2 = activityProductService.update(activityProduct);
			if (res2 > 0) {
				LOGGER.info("成团失败活动商品库存数量返回");
			}
			product.setStock(product.getStock() + orderDetail.getQuantity());
			int res3 = productService.update(product);
			if (res3 > 0) {
				LOGGER.info("成团失败商品库存数量返回");
			}
			if (orderDetail.getProductSpecInfo() != null && !"".equals(orderDetail.getProductSpecInfo())) {
				ProductSpecItem productSpecItem = JSON
						.parseObject(orderDetail.getProductSpecInfo(), ProductSpecItem.class);
				productSpecItem.setActivityStock(productSpecItem.getActivityStock() + orderDetail.getQuantity());
				int res4 = productSpecItemService.update(productSpecItem);
				if (res4 > 0) {
					LOGGER.info("成团失败商品对应规格库存数量返回");
				}
			}
		}
		activeDelaySendJobHandler.updateState(jsonObject.getString("activeMqTaskTOId"));
	}


	@JmsListener(destination = ActiviMqQueueName.PRIZE_ORDER)
	public void prizeOrder(String jsonDate) {
		JSONObject jsonObject = JSONObject.parseObject(jsonDate);
		PrizeOrder prizeOrder = prizeOrderMapper.selectByPrimaryKey(jsonObject.getInteger("id"));
		prizeOrder.setOrderState(3);
		prizeOrder.setRecordTime(TimeUtil.getNowTime());
		if (prizeOrderMapper.updateByPrimaryKey(prizeOrder) > 0) {
			LOGGER.info("交易成功");
		}
		activeDelaySendJobHandler.updateState(jsonObject.getString("activeMqTaskTOId"));
	}

}

