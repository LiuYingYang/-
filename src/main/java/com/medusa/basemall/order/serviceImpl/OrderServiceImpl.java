package com.medusa.basemall.order.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.medusa.basemall.activemq.ActiveMqClient;
import com.medusa.basemall.configurer.WxServiceUtils;
import com.medusa.basemall.constant.*;
import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.core.ServiceException;
import com.medusa.basemall.integral.entity.PrizeRule;
import com.medusa.basemall.integral.service.PrizeDetailService;
import com.medusa.basemall.integral.service.PrizeRuleService;
import com.medusa.basemall.jobhandler.ActiveDelaySendJobHandler;
import com.medusa.basemall.messages.msgs.OrderTemplateMsgs;
import com.medusa.basemall.messages.service.WxuserFormIdService;
import com.medusa.basemall.order.dao.OrderDetailMapper;
import com.medusa.basemall.order.dao.OrderMapper;
import com.medusa.basemall.order.dao.OrderRefoundMapper;
import com.medusa.basemall.order.entity.Order;
import com.medusa.basemall.order.entity.OrderDetail;
import com.medusa.basemall.order.entity.OrderExtend;
import com.medusa.basemall.order.entity.OrderRefound;
import com.medusa.basemall.order.service.BalanceDetaiService;
import com.medusa.basemall.order.service.BuycarService;
import com.medusa.basemall.order.service.OrderDetailService;
import com.medusa.basemall.order.service.OrderService;
import com.medusa.basemall.order.vo.*;
import com.medusa.basemall.product.dao.ProductMapper;
import com.medusa.basemall.product.dao.ProductSpecItemMapper;
import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.product.entity.ProductSpecItem;
import com.medusa.basemall.product.service.LogisticCancelService;
import com.medusa.basemall.product.service.ProductService;
import com.medusa.basemall.product.service.ProductSpecItemService;
import com.medusa.basemall.product.vo.JoinActiveInfo;
import com.medusa.basemall.product.vo.OrderStatisticsVo;
import com.medusa.basemall.promotion.dao.*;
import com.medusa.basemall.promotion.entity.*;
import com.medusa.basemall.promotion.service.*;
import com.medusa.basemall.shop.dao.ManagerMapper;
import com.medusa.basemall.shop.dao.ShopInfoDao;
import com.medusa.basemall.shop.entity.ColumnFlag;
import com.medusa.basemall.shop.entity.Manager;
import com.medusa.basemall.shop.service.ColumnFlagService;
import com.medusa.basemall.shop.service.SmsService;
import com.medusa.basemall.user.dao.WxuserMapper;
import com.medusa.basemall.user.entity.*;
import com.medusa.basemall.user.service.*;
import com.medusa.basemall.user.vo.MiniWxuserVo;
import com.medusa.basemall.utils.IdGenerateUtils;
import com.medusa.basemall.utils.TimeUtil;
import com.medusa.basemall.websocket.PlaceAnOrderNotify;
import com.vip.vjtools.vjkit.mapper.BeanMapper;
import com.vip.vjtools.vjkit.time.ClockUtil;
import com.vip.vjtools.vjkit.time.DateFormatUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


/**
 * @author medusa
 * @date 2018/06/02
 * 需要事物时添加  @Transactional
 */

@Service

public class OrderServiceImpl extends AbstractService<Order> implements OrderService {

	@Resource
	private SmsService smsService;
	@Resource
	private OrderMapper tOrderMapper;
	@Resource
	private OrderDetailService orderDetailService;
	@Resource
	private OrderDetailMapper tOrderDetailMapper;
	@Resource
	private WxServiceUtils wxServiceUtils;
	@Resource
	private WxuserMapper tWxuserMapper;
	@Resource
	private ActivityProductMapper tActivitytProductMapper;
	@Resource
	private ProductSpecItemMapper tProductSpecItemMapper;
	@Resource
	private ProductMapper tProductMapper;
	@Resource
	private CouponWxuserService couponWxuserService;
	@Resource
	private CouponService couponService;
	@Resource
	private OrderRefoundMapper tOrderRefoundMapper;
	@Resource
	private GroupMemberMapper groupMemberMapper;
	@Resource
	private GroupMapper groupMapper;
	@Resource
	private ActivityGroupMapper activityGroupMapper;
	@Resource
	private GroupMemberMapper tGroupMemberMapper;
	@Resource
	private ActiveMqClient activeMqClient;
	@Resource
	private ActiveDelaySendJobHandler activeDelaySendJobHandler;
	@Resource
	private ShopInfoDao shopInfoDao;
	@Resource
	private MemberRankRuleService memberRankRuleService;
	@Resource
	private ColumnFlagService columnFlagService;
	@Resource
	private MemberRankService memberRankService;
	@Resource
	private PrizeRuleService prizeRuleService;
	@Resource
	private MemberService memberService;
	@Resource
	private PrizeDetailService prizeDetailService;
	@Resource
	private ActivitySeckillMapper activitySeckillMapper;
	@Resource
	private CouponWxuserMapper couponWxuserMapper;
	@Resource
	private ActivitySpecialMapper activitySpecialMapper;
	@Resource
	private ActivityCouponService activityCouponService;
	@Resource
	private LogisticCancelService logisticCancelService;
	@Resource
	private BalanceDetaiService balanceDetaiService;
	@Resource
	private OrderTemplateMsgs orderTemplateMsgs;
	@Resource
	private ManagerMapper managerMapper;
	@Resource
	private WxuserFormIdService wxuserFormIdService;
	@Resource
	private BuycarService buycarService;
	@Resource
	private CollectService collectService;
	@Resource
	private FootMarkService footMarkService;
	@Resource
	private OrderMapper orderMapper;
	@Resource
	private WxuserService wxuserService;
	@Resource
	private ActivityProductService activityProductService;
	@Resource
	private ActivityProductStockService activityProductStockService;
	@Resource
	private ProductService productService;
	@Resource
	private ProductSpecItemService specItemService;
	@Resource
	private RedisTemplate redisTemplate;
	@Resource
	private MongoTemplate mongoTemplate;


	private static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);


	@Override

	public Result againBuyProduct(Long orderId) {
		Order order = tOrderMapper.selectByPrimaryKey(orderId);
		//判断是否属于活动商品   活动商品不能加入购物车,如果有库存,返回,活动类型,商品id
		if (null != order) {
			boolean flag = (order.getOrderType() != null && order.getOrderType().length() > 0) && (
					order.getOrderType().contains(String.valueOf(ActivityType.SECONDKILL)) || order.getOrderType()
							.contains(String.valueOf(ActivityType.GROUP)));
			if (flag == true) {
				return ResultGenerator.genFailResult("拼团/秒杀商品不可再次购买");
			}
			List<OrderDetail> oldDetails = orderDetailService.selectByOrderId(orderId);
			//查询订单中的商品是否下架或删除
			StringBuilder sb = new StringBuilder();
			oldDetails.forEach(obj -> {
				sb.append(obj.getProductId() + ",");
			});
			//筛选出可继续购买的商品
			List<Product> oldProduct = productService
					.findProductNotDeleteAndShelfstate(sb.substring(0, sb.length() - 1));
			List<OrderDetail> available = new ArrayList<>();
			oldDetails.forEach(detail -> {
				oldProduct.forEach(product -> {
					if (detail.getProductId().equals(product.getProductId())) {
						available.add(detail);
					}
				});
			});
			//算出已不可购买的数量商品
			//如果所有商品都已经下架或删除 返回提醒用户去逛一逛
			if (oldDetails.size() - available.size() == oldDetails.size()) {
				return ResultGenerator.genSuccessResult(-1);
			}
			//根据商品id封装到map后面使用
			Map<Long, Product> map = new HashMap<>(oldProduct.size());
			oldProduct.forEach(obj -> map.put(obj.getProductId(), obj));
			//判断存在的商品库存是否足够,
			for (OrderDetail orderDetail : available) {
				Integer quantity = orderDetail.getQuantity();
				Long wxuserId = order.getWxuserId();
				Product product = map.get(orderDetail.getProductId());
				//原本购买的商品与现有商品的规格状态是否相同
				//统一规格库存处理
				JoinActiveInfo joinActiveInfo = new JoinActiveInfo();
				//添加优惠券标示
				if (product.getProductTypeList() != null && product.getProductTypeList().contains("1001")) {
					//查看当前进行中的优惠券活动
					ActivityCoupon activityCoupon = activityCouponService
							.findUnderwayActivityCoupon(orderDetail.getAppmodelId());
					ActivityProduct activityProduct = activityProductService
							.findActivityProduct(activityCoupon.getActivityCouponId(), product.getProductId(),
									orderDetail.getAppmodelId());
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("activityType", ActivityType.COUPON);
					jsonObject.put("activityId", activityCoupon.getActivityCouponId());
					joinActiveInfo.setCouponInfo(jsonObject.toJSONString());
				}
				if (product.getSpecType().equals(true)) {
					//处理非活动商品
					if (orderDetail.getActivityInfo() == null && product.getStock() - orderDetail.getQuantity() >= 0) {
						ProductSpecItem specItem = new ProductSpecItem();
						specItem.setStock(product.getStock());
						specItem.setSkuImg(product.getProductImg());
						specItem.setPrice(product.getPrice());
						buycarService.addBuycar(product, wxuserId, quantity, specItem, null);
						continue;
					}
					if (orderDetail.getActivityInfo() != null) {
						//处理特价商品
						JSONObject jsonObject = JSON.parseObject(orderDetail.getActivityInfo());
						ActivitySpecial activitySpecial = jsonObject
								.getObject("ActivitySpecialInfo", ActivitySpecial.class);
						//先判断特价活动是否还存在
						if (activitySpecial != null && activitySpecial.getNowState().equals(1)) {
							//判断商品库存是否足够
							ActivityProduct activityProductInfo = jsonObject
									.getObject("ActivityProductInfo", ActivityProduct.class);
							ActivityProduct activityProduct = activityProductService
									.findById(activityProductInfo.getActivityId());
							//商品未删除并且库存足够
							if (activityProduct != null && activityProduct.getDeleteState().equals(false)
									&& activityProduct.getActivityStock() - orderDetail.getQuantity() >= 0) {
								JSONObject activityInfo = new JSONObject();
								activityInfo.put("activityId", activitySpecial.getActivitySpecialId());
								activityInfo.put("activityProductId", activityProductInfo.getActivityProductId());
								activityInfo.put("activityType", ActivityType.SPECIAL);
								joinActiveInfo.setActiveInfo(activityInfo.toJSONString());
								ProductSpecItem specItem = new ProductSpecItem();
								specItem.setStock(activityProduct.getActivityStock());
								specItem.setSkuImg(product.getProductImg());
								specItem.setPrice(activityProduct.getActivityPrice());
								buycarService.addBuycar(product, wxuserId, quantity, specItem, joinActiveInfo);
								continue;
							}
						}
					}
					//处理多规格库存处理
				} else if (product.getSpecType().equals(false)) {
					JSONObject jsonObject = JSON.parseObject(orderDetail.getProductSpecInfo());
					//如果是活动商品 则表示活动商品规格库存的id(优惠券活动除外)    todo 活动规格的id  活动结束会删除  ???
					Long productSpecItemId = jsonObject.getLong("productSpecItemId");
					//处理非活动商品
					if (orderDetail.getActivityInfo() == null) {
						List<ProductSpecItem> specItems = tProductSpecItemMapper
								.selectByProductId(product.getProductId());
						if (specItems != null) {
							//获取购买过的规格项
							ProductSpecItem specItem = specItems.stream()
									.filter(obj -> obj.getProductSpecItemId().equals(productSpecItemId)).findFirst()
									.get();
							//判断以前的购买的库存是否还存在
							if (specItem != null) {
								if (specItem.getStock() - orderDetail.getQuantity() >= 0) {
									buycarService.addBuycar(product, wxuserId, quantity, specItem, joinActiveInfo);
									continue;
								}
							}
							//筛选可用的规格库存
							List<ProductSpecItem> otherSpecItems = specItems.stream()
									.filter(obj -> obj.getStock() > 0 && obj.getStock() - quantity >= 0).sorted()
									.collect(Collectors.toList());
							if (otherSpecItems != null && otherSpecItems.size() > 0) {
								ProductSpecItem newProductSpecItem = otherSpecItems.get(0);
								buycarService.addBuycar(product, wxuserId, quantity, specItem, joinActiveInfo);
								continue;
							}

						}
					}
					if (orderDetail.getActivityInfo() != null) {
						//处理特价商品
						JSONObject jsonObject1 = JSON.parseObject(orderDetail.getActivityInfo());
						ActivitySpecial activitySpecial = jsonObject1
								.getObject("ActivitySpecialInfo", ActivitySpecial.class);
						//先判断特价活动是否还存在
						if (activitySpecial != null && activitySpecial.getNowState().equals(1)) {
							ActivityProduct activityProduct = jsonObject1
									.getObject("ActivityProductInfo", ActivityProduct.class);
							//判断商品规格库存是否足够
							List<ActivityProductStock> activityProductStocks = activityProductStockService
									.findByList("activityOroductId", activityProduct);
							if (activityProductStocks != null) {

								JSONObject activityInfo = new JSONObject();
								activityInfo.put("activityId", activitySpecial.getActivitySpecialId());
								activityInfo
										.put("activityProductId", activityProductStocks.get(0).getActivityProductId());
								activityInfo.put("activityType", ActivityType.SPECIAL);
								joinActiveInfo.setActiveInfo(activityInfo.toJSONString());
								ActivityProductStock productStock = activityProductStocks.stream()
										.filter(obj -> obj.getActivityProductStockId().equals(productSpecItemId))
										.findFirst().get();
								//以前的购买的库存还存在
								if (productStock != null) {
									if (productStock.getActivityStock() - orderDetail.getQuantity() >= 0) {
										ProductSpecItem specItem = specItemService
												.findById(productStock.getProductSpecItemId());
										buycarService.addBuycar(product, wxuserId, quantity, specItem, joinActiveInfo);
										continue;
									}
								}
								List<ActivityProductStock> otherSpecItems = activityProductStocks.stream()
										.filter(obj -> obj.getActivityStock() > 0
												&& obj.getActivityStock() - quantity >= 0).sorted()
										.collect(Collectors.toList());
								if (otherSpecItems != null && otherSpecItems.size() > 0) {
									ProductSpecItem specItem = specItemService
											.findById(otherSpecItems.get(0).getProductSpecItemId());
									buycarService.addBuycar(product, wxuserId, quantity, specItem, joinActiveInfo);
									continue;
								}
							}
						}
					}
				}
				//不相同则从封装的map中移除
				map.remove(product.getProductId());
			}
			if (oldDetails.size() == map.size()) {
				return ResultGenerator.genSuccessResult(0);
			} else if (oldDetails.size() > map.size() && map.size() > 0) {
				return ResultGenerator.genSuccessResult(1);
			} else {
				return ResultGenerator.genSuccessResult(-1);
			}
		}
		return ResultGenerator.genFailResult("查无此订单");
	}


	@Override
	public Result refoundHistory(Long detailId) {
		OrderDetail orderDetail = tOrderDetailMapper.selectByPrimaryKey(detailId);
		if (null != orderDetail.getRecord() && !orderDetail.getRecord().equals("")) {
			List<RefoundRecordVo> refoundRecordVos = JSON.parseArray(orderDetail.getRecord(), RefoundRecordVo.class);
			Collections.sort(refoundRecordVos, Comparator.comparing(RefoundRecordVo::getTime).reversed());
			Order order = tOrderMapper.selectByPrimaryKey(orderDetail.getOrderId());
			Wxuser wxuser = tWxuserMapper.selectByPrimaryKey(order.getWxuserId());
			refoundRecordVos.forEach(obj -> {
				if (obj.getName().equals("买家")) {
					obj.setUrl(wxuser.getAvatarUrl());
				}
			});
			return ResultGenerator.genSuccessResult(refoundRecordVos);
		}
		return ResultGenerator.genFailResult("无协商记录");
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Result updateRefund(OrderParamVo paramVo) {
		if (null != paramVo.getOrderRefoundId()) {
			OrderRefound orderRefound = tOrderRefoundMapper.selectByPrimaryKey(paramVo.getOrderRefoundId());
			if (null != orderRefound) {
				if (orderRefound.getBusinessState().equals(BusinessState.APPLYFOR_IN)) {
					orderRefound.setReason(paramVo.getReason());
					orderRefound.setProductState(paramVo.getProductState());
					String nowTime = TimeUtil.getNowTime();
					orderRefound.setUpdateTime(nowTime);
					orderRefound.setRefoundFee(paramVo.getRefoundFee());
					orderRefound.setRemark(paramVo.getRemark());
					OrderDetail orderDetail = tOrderDetailMapper.selectByPrimaryKey(orderRefound.getOrderDetailId());
					RefoundRecordVo record = setRecord(orderRefound, nowTime);
					//修改协商历史操作
					switch (orderDetail.getNumber()) {
						case 1:
							List<RefoundRecordVo> recordVos = new ArrayList<>();
							recordVos.add(record);
							orderDetail.setRecord(JSON.toJSONString(recordVos));
							break;
						case 2:
							List<RefoundRecordVo> refoundRecordVos = JSONObject
									.parseArray(orderDetail.getRecord(), RefoundRecordVo.class);
							Collections
									.sort(refoundRecordVos, Comparator.comparing(RefoundRecordVo::getTime).reversed());
							for (RefoundRecordVo refoundRecordVo : refoundRecordVos) {
								if (refoundRecordVo.getContent().contains("原因")) {
									refoundRecordVo.setTime(record.getTime());
									refoundRecordVo.setContent(record.getContent());
									break;
								}
							}
							orderDetail.setRecord(JSON.toJSONString(refoundRecordVos));
							break;
						default:
							break;
					}
					orderDetail.setUpdateTime(nowTime);
					tOrderDetailMapper.updateByPrimaryKeySelective(orderDetail);
					addMessage(0L, 3, 3, orderRefound.getOrderRefoundId(), orderRefound.getAppmodelId());
					return tOrderRefoundMapper.updateByPrimaryKeySelective(orderRefound) > 0 ?
							ResultGenerator.genSuccessResult() :
							ResultGenerator.genFailResult("修改失败");
				}
				return ResultGenerator.genFailResult("非法操作");
			}
			return ResultGenerator.genFailResult("参数出错");
		}
		return ResultGenerator.genFailResult("非法参数");
	}


	@Override
	public Result warnSellerSendProduct(Long orderId) {
		Order order = tOrderMapper.selectByPrimaryKey(orderId);
		if (order != null) {
			Wxuser wxuser = wxuserService.findById(order.getWxuserId());
			try {
				JSONObject jsonObject = new JSONObject();
				String pattern = "yyyy年MM月dd HH:mm";
				List<String> keywords = new LinkedList<>();
				keywords.add(order.getOutTradeNo());
				String payTime = DateFormatUtil
						.formatDate(pattern, DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.parse(order.getPayTime()));
				keywords.add(payTime);
				keywords.add(wxuser.getNikeName());
				String currentTime = DateFormatUtil.formatDate(pattern, ClockUtil.currentDate());
				keywords.add(currentTime);
				jsonObject.put("first", "用户催单通知");
				jsonObject.put("keywords", keywords);
				jsonObject.put("remark", "请及时安排发货，提高服务质量!");
				jsonObject.put("appmodelId", order.getAppmodelId());
				jsonObject.put("type", 1);
				activeMqClient.send(jsonObject.toJSONString(), ActiviMqQueueName.ORDER_FWH_MESSAGE);
				addMessage(order.getOrderId(), 1, 3, 0L, order.getAppmodelId());
				return ResultGenerator.genSuccessResult();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return ResultGenerator.genFailResult("订单不存在");
	}

	/**
	 * 增加会员成长值
	 *
	 * @param wxuser
	 * @param payFee 支付的价格
	 */
	private void addGrowthValue(MiniWxuserVo wxuser, Double payFee) {
		MemberRankRule memberRankRule = memberRankRuleService.findBy("appmodelId", wxuser.getAppmodelId());
		//商家已设置会员等级成长值
		if (null != memberRankRule) {
			Member memberInfo = wxuser.getMemberInfo();
			if (payFee < 1) {
				return;
			}
			int addGrowthValue = (int) Math.floor(payFee) * memberRankRule.getConsumeIntegral();
			memberInfo.setGrowthValue(memberInfo.getGrowthValue() + addGrowthValue);
			//查询商家店铺所有等级
			//判断会员等级是否可升级
			Condition condition = new Condition(MemberRank.class);
			condition.createCriteria().andEqualTo("appmodelId", wxuser.getAppmodelId());
			condition.orderBy("growthValue").asc();
			//当前等级
			List<MemberRank> memberRanks = memberRankService.findByCondition(condition);
			Integer rankId = memberInfo.getRankInfo().getRankId();
			//当前等级如果是最大的就不进行升级判断
			if (!rankId.equals(memberRanks.get(memberRanks.size() - 1).getRankId())) {
				//当前等级的下一等级
				for (int i = 0; i < memberRanks.size(); i++) {
					//进行等级晋升判断
					if (memberRanks.get(i).getRankId().equals(rankId)) {
						if (memberRanks.get(i + 1).getGrowthValue() <= memberInfo.getGrowthValue()) {
							memberInfo.setRankId(memberRanks.get(i + 1).getRankId());
							memberInfo.setUpgradeTime(TimeUtil.getNowTime());
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("memberId", memberInfo.getMemberId());
							jsonObject.put("oldRankId", memberInfo.getRankId());
							jsonObject.put("newRankId", memberRanks.get(i + 1));
							jsonObject.put("messageType", SendTemplatMessageType.UPDATE_NOTIFICATION);
							activeMqClient.send(jsonObject.toJSONString(),
									ActiviMqQueueName.ORDER_MINIPROGRAM_TEMPLATE_MESSAGE);
						}
						break;
					}
				}
			}
			memberService.update(memberInfo);
		} else {
			log.info("商家未设置会员获取成长值规则");
		}
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public String notify(WxPayOrderNotifyResult payOrderNotifyResult) {
		//1、更新商品库存(包含商品规格的库存，活动商品的库存)
		log.info("订单回调XmlResult --------->" + payOrderNotifyResult.toString());
		if ("SUCCESS".equalsIgnoreCase(payOrderNotifyResult.getResultCode())) {
			OrderExtend orderExtend = tOrderMapper.findByOutTradeNo(payOrderNotifyResult.getOutTradeNo());
			if (orderExtend.getPayFlag().equals(PayFlag.WAIT_PAY)) {
				log.info("开始处理订单回调: " + payOrderNotifyResult.getOutTradeNo() + " !!!");
				String result = paySuccessOrder(orderExtend.getOutTradeNoExt());
				if (result.equals("success")) {
					return WxPayNotifyResponse.success("处理成功");
				}
			}
			return WxPayNotifyResponse.success("已处理");
		}
		return WxPayNotifyResponse.fail("订单回调处理失败");
	}

	@Override
	public Result closeOrder(String orderIds, Integer operatiPerson, String appmodelId) {
		//todo 后期优化  减少数据库请求
		int result = 0;
		String[] split = orderIds.split(",");
		for (int i = 0; i < split.length; i++) {
			Order order = new Order();
			Long orderId = Long.valueOf(split[i]);
			order.setOrderId(orderId);
			if (operatiPerson.equals(1001)) {
				order.setPayFlag(PayFlag.BUSINESS_CLOASE);
			} else if (operatiPerson.equals(2001)) {
				order.setPayFlag(PayFlag.SELLER_CLOASE);
			} else {
				return ResultGenerator.genFailResult("非法操作");
			}

			activeDelaySendJobHandler
					.savaTask(String.valueOf(orderId), ActiviMqQueueName.ORDER_CLOSE_STOCK_RETURN, 0L, "");
			String nowTime = TimeUtil.getNowTime();
			order.setUpdateTime(TimeUtil.str2Date(nowTime));
			result = result + tOrderMapper.updateByPrimaryKeySelective(order);
		}
		if (result == split.length) {
			List<Order> orders = orderMapper.selectByIds(orderIds);
			for (Order order : orders) {
				addMessage(order.getOrderId(), 1, 5, 0L, appmodelId);
			}
			return ResultGenerator.genSuccessResult();
		}
		return ResultGenerator.genFailResult("关闭订单失败");
	}

	@Override
	public Result confirmReceipt(Order orderId) {
		Order order = new Order();
		order.setOrderId(orderId.getOrderId());
		order.setPayFlag(PayFlag.BUSINESS_OK);
		String nowTime = TimeUtil.getNowTime();
		order.setRecordTime(nowTime);
		order.setUpdateTime(TimeUtil.str2Date(nowTime));
		int i = tOrderMapper.updateByPrimaryKeySelective(order);
		if (i > 0) {
			Order o = tOrderMapper.selectByPrimaryKey(order.getOrderId());
			addMessage(o.getOrderId(), 1, 4, 0L, o.getAppmodelId());
			return ResultGenerator.genSuccessResult();
		}
		return ResultGenerator.genFailResult("确认收货失败");
	}

	@Override
	public List<OrderExtend> findOrderMini(Long wxuserId, String appmodelId, Integer orderState) {
		Order order = new Order();
		order.setWxuserId(wxuserId);
		order.setAppmodelId(appmodelId);
		order.setPayFlag(orderState);
		List<OrderExtend> select = tOrderMapper.findOrderMini(order);
		return select;
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Result refoundOrderPrice(OrderRefundVo orderRefundVo) {
		OrderRefound newRefound = BeanMapper.map(orderRefundVo, OrderRefound.class);
		OrderDetail orderDetail = tOrderDetailMapper.selectByPrimaryKey(newRefound.getOrderDetailId());
		//申请是否已经超过两次
		if (orderDetail == null) {
			return ResultGenerator.genFailResult("记录为空");
		}
		if (orderDetail.getNumber() < 2) {
			if (orderDetail.getApplyState().equals(ApplyState.APPLYFOR_IN)) {
				return ResultGenerator.genFailResult("订单已在申请退款状态");
			}
			if (orderDetail.getApplyState().equals(ApplyState.REFUND_OK)) {
				return ResultGenerator.genFailResult("订单已退款成功");
			}
			//商品申请状态处于正常订单
			if (orderDetail.getApplyState().equals(ApplyState.REGULAR)) {
				String nowTime = TimeUtil.getNowTime();
				orderDetail.setApplyState(ApplyState.APPLYFOR_IN);
				orderDetail.setNumber(orderDetail.getNumber() + 1);
				orderDetail.setRefuseState(RefuseState.APPLYFOR_IN);
				orderDetail.setWlprice(new BigDecimal(0));
				//记录申请操作记录
				if (null == orderDetail.getRecord()) {
					List<RefoundRecordVo> recordVos = new ArrayList<>();
					RefoundRecordVo record = setRecord(newRefound, nowTime);
					recordVos.add(record);
					orderDetail.setRecord(JSON.toJSONString(recordVos));
				} else {
					List<RefoundRecordVo> refoundRecordVos = JSONObject
							.parseArray(orderDetail.getRecord(), RefoundRecordVo.class);
					refoundRecordVos.add(setRecord(newRefound, nowTime));
					orderDetail.setRecord(JSON.toJSONString(refoundRecordVos));
				}
				//如果订单属于待发货状态,退款了所有购买的商品需要退其下单时的邮费
				Order order = tOrderMapper.selectByPrimaryKey(orderDetail.getOrderId());
				if (order.getPayType().equals(PayFlag.PAY_OK)) {
					Condition condition = new Condition(OrderDetail.class);
					condition.createCriteria().andEqualTo("orderId", orderDetail.getOrderId());
					List<OrderDetail> details = tOrderDetailMapper.selectByCondition(condition);
					long count = details.stream().filter(obj -> obj.getRefuseState().equals(RefuseState.REFUND_OK))
							.count();
					if (details.size() == count) {
						orderDetail.setWlprice(order.getWlPrice());
					}
				}
				tOrderDetailMapper.updateByPrimaryKeySelective(orderDetail);
				newRefound.setOrderRefoundId(Long.valueOf(IdGenerateUtils.getOrderNum()));
				newRefound.setCreateTime(nowTime);
				newRefound.setUpdateTime(nowTime);
				newRefound.setBusinessState(BusinessState.APPLYFOR_IN);
				newRefound.setFlowPath(FlowPathState.APPLYFOR_IN);
				if (tOrderRefoundMapper.insert(newRefound) > 0) {
					activeDelaySendJobHandler
							.savaTask(newRefound.getOrderRefoundId().toString(), ActiviMqQueueName.REFUND_ORDER,
									TimeType.FIVEDAY, order.getAppmodelId());
					String afterSaleType = orderRefundVo.getRefoundType().equals(0) ? "退款" : "退货退款";
					String orderType = order.getPayFlag().equals(PayFlag.PAY_OK) ? "已付款，待发货" : "已发货，待收货";
					List<String> keywords = new LinkedList<>();
					keywords.add(afterSaleType);
					keywords.add(orderType);
					String productName = tProductMapper.selectByPrimaryKey(orderDetail.getProductId()).getProductName();
					keywords.add(productName);
					keywords.add(orderRefundVo.getReason());
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("first", "售后申请通知");
					jsonObject.put("keywords", keywords);
					jsonObject.put("remark", "您有客户发起售后申请，请登陆后台及时处理!");
					jsonObject.put("appmodelId", order.getAppmodelId());
					jsonObject.put("type", 4);
					activeMqClient.send(jsonObject.toJSONString(), ActiviMqQueueName.ORDER_FWH_MESSAGE);
					addMessage(0L, 3, 1, newRefound.getOrderRefoundId(), newRefound.getAppmodelId());
					return ResultGenerator.genSuccessResult(newRefound.getOrderRefoundId());
				}
			}
			return ResultGenerator.genFailResult("申请退款失败");
		}
		return ResultGenerator.genFailResult("订单申请已关闭");
	}


	private RefoundRecordVo setRecord(OrderRefound newRefound, String nowTime) {
		StringBuffer content = new StringBuffer();
		content.append("发起");
		if (newRefound.getRefoundType().equals(0)) {
			content.append("仅退款申请,原因:");
		} else {
			content.append("退货退款申请,原因:");
		}
		content.append(newRefound.getReason() + ",金额:" + newRefound.getRefoundFee() + "元;");
		if (!newRefound.getRemark().equals("")) {
			content.append("说明:" + newRefound.getRemark());
		}
		RefoundRecordVo record = new RefoundRecordVo();
		record.setContent(content.toString());
		record.setTime(nowTime);
		record.setName("买家");
		return record;
	}


	@Override
	public List<OrderExtend> findOrderMiniGroup(Long wxuserId, String appmodelId, Integer orderState) {
		Map<String, Object> map = new HashMap<>(3);
		map.put("wxuserId", wxuserId);
		map.put("appmodelId", appmodelId);
		map.put("orderState", orderState);
		List<OrderExtend> orderExtends = tOrderMapper.findOrderMiniGroup(map);
		orderExtends.forEach(obj -> {
			GroupMember groupMember = new GroupMember();
			groupMember.setGroupId(obj.getGroup().getGroupId());
			List<GroupMember> select = tGroupMemberMapper.select(groupMember);
			obj.setGroupLimit(obj.getGroup().getLimitNum() - select.size());

			OrderDetail detail = new OrderDetail();
			detail.setOrderId(obj.getOrderId());
			obj.setOrderDetails(tOrderDetailMapper.select(detail));
		});

		return orderExtends;
	}


	@Override
	public Result batchBackRemrk(JSONObject paramVo) {
		String orderIds = paramVo.getString("orderIds");
		String backRemark = paramVo.getString("backRemark");
		Boolean coverType = paramVo.getBoolean("coverType");
		if (orderIds.length() > 0) {
			List<Order> orders = tOrderMapper.selectByIds(ArrayUtils.toString(orderIds));
			//todo 后期优化批量更新
			if (coverType) {
				orders.forEach(obj -> {
					obj.setBackRemark(backRemark);
					tOrderMapper.updateByPrimaryKey(obj);
				});
			} else {
				//不覆盖原有备注,过滤出非空备注的用户
				List<Order> collect = orders.stream()
						.filter(obj -> obj.getBackRemark() == null || "".equals(obj.getBackRemark()))
						.collect(Collectors.toList());
				collect.forEach(obj -> {
					obj.setBackRemark(backRemark);
					tOrderMapper.updateByPrimaryKeySelective(obj);
				});

			}
			return ResultGenerator.genSuccessResult();
		}
		return ResultGenerator.genFailResult("备注失败");
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Result updateRefoundAddress(OrderWlVo orderWlVo) {
		OrderRefound orderRefound = BeanMapper.map(orderWlVo, OrderRefound.class);
		if (null != orderRefound.getOrderRefoundId()) {
			orderRefound.setFlowPath(FlowPathState.SELLER_CONFIRM);
			String nowTime = TimeUtil.getNowTime();
			orderRefound.setUpdateTime(nowTime);
			OrderDetail detail = tOrderDetailMapper.findByDetailId(orderRefound.getOrderDetailId());
			List<RefoundRecordVo> recordVos = JSON.parseArray(detail.getRecord(), RefoundRecordVo.class);
			RefoundRecordVo recordVo = new RefoundRecordVo();
			recordVo.setName("买家");
			recordVo.setTime(nowTime);
			recordVo.setContent("买家退货。物流公司" + orderRefound.getuWlName() + "物流单号" + orderRefound.getuWlNum());
			recordVos.add(recordVo);
			detail.setRecord(JSONObject.toJSONString(recordVos));
			tOrderDetailMapper.updateByPrimaryKeySelective(detail);
			if (tOrderRefoundMapper.updateByPrimaryKeySelective(orderRefound) > 0) {
				//自动退款前24小时
				activeDelaySendJobHandler.savaTask(orderRefound.getOrderRefoundId().toString(),
						ActiviMqQueueName.BUYERS_DELIVERY_AUTO_REFUND_REMIND, TimeType.NINETEENDAYS,
						orderWlVo.getAppmodelId());
				//买家发货,20天内卖家无任何操作,自动退款给买家;
				activeDelaySendJobHandler
						.savaTask(orderRefound.getOrderRefoundId().toString(), ActiviMqQueueName.BUYERS_DELIVERY,
								TimeType.TWENTYDAY, orderWlVo.getAppmodelId());
				List<String> keywords = new LinkedList<>();
				Order order = tOrderMapper.selectByPrimaryKey(detail.getOrderId());
				keywords.add(order.getOutTradeNo());
				Wxuser wxuser = tWxuserMapper.selectByPrimaryKey(order.getWxuserId());
				keywords.add(wxuser.getNikeName());
				keywords.add(order.getTelPhone());
				keywords.add(detail.getProductName());
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("first", "退货提醒");
				jsonObject.put("keywords", keywords);
				jsonObject.put("remark", "您有售后订单客户已退货，如需查看请登陆后台!");
				jsonObject.put("appmodelId", order.getAppmodelId());
				jsonObject.put("type", 3);
				activeMqClient.send(jsonObject.toJSONString(), ActiviMqQueueName.ORDER_FWH_MESSAGE);
				return ResultGenerator.genSuccessResult(orderRefound.getOrderRefoundId());
			}
			return ResultGenerator.genFailResult("填写失败");
		}
		return ResultGenerator.genFailResult("退款id参数不能为空");
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Result agreeRefoundOrder(OrderParamVo paramVo) {
		Boolean inspection = smsService
				.inspection(paramVo.getAppmodelId(), true, paramVo.getPhone(), paramVo.getSmsCode(), 4);
		if (Boolean.logicalAnd(inspection, true)) {
			OrderRefound orderRefound = tOrderRefoundMapper.selectByPrimaryKey(paramVo.getOrderRefoundId());
			if (orderRefound != null) {
				if (orderRefound.getBusinessState().equals(BusinessState.REFUND_CLOSE)) {
					throw new ServiceException("售后订单已关闭");
				}
				//更新退款订单,订单详情,订单信息
				String currentTime = TimeUtil.getNowTime();
				orderRefound.setUpdateTime(currentTime);
				orderRefound.setRefoundTime(currentTime);
				orderRefound.setBusinessState(BusinessState.REFUND_OK);
				orderRefound.setFlowPath(FlowPathState.AGREE_PRICE);
				if (orderRefound.getRefoundType().equals(1)) {
					orderRefound.setFlowPath(FlowPathState.SELLER_CONFIRM);
				}
				if (tOrderRefoundMapper.updateByPrimaryKeySelective(orderRefound) > 0) {
					OrderDetail orderDetail = tOrderDetailMapper.selectByPrimaryKey(orderRefound.getOrderDetailId());
					orderDetail.setApplyState(ApplyState.REFUND_OK);
					orderDetail.setRefuseState(RefuseState.REFUND_OK);
					orderDetail.setUpdateTime(currentTime);
					//记录操作记录
					List<RefoundRecordVo> refoundRecordVos = JSONObject
							.parseArray(orderDetail.getRecord(), RefoundRecordVo.class);
					RefoundRecordVo record = new RefoundRecordVo();
					record.setName("卖家");
					record.setTime(currentTime);
					record.setContent("卖家同意退款申请，退款成功。");
					if (orderRefound.getRefoundType().equals(1)) {
						record.setContent("卖家同意退货退款申请,退款成功");
					}
					refoundRecordVos.add(record);
					orderDetail.setRecord(JSONObject.toJSONString(refoundRecordVos));
					tOrderDetailMapper.updateByPrimaryKeySelective(orderDetail);
					//判断订单中商品是否全部退款成功
					Order order = tOrderMapper.selectByPrimaryKey(orderRefound.getOrderId());
					List<OrderDetail> details = orderDetailService.selectByOrderId(orderRefound.getOrderId());
					AtomicInteger number = new AtomicInteger(1);
					details.forEach(obj -> {
						if (!obj.getOrderDetailId().equals(orderDetail.getOrderDetailId()) && obj.getApplyState()
								.equals(ApplyState.REFUND_OK)) {
							number.getAndIncrement();
						}
					});
					//全部退款成功修改订单为关闭订单
					if (details.size() == number.get()) {
						//在订单属于待发货时同意退款,同时还是最后一个退货商品,需要退还邮费
						if (order.getPayFlag().equals(PayFlag.PAY_OK)) {
							//添加邮费价格
							orderRefound.setRefoundFee(orderRefound.getRefoundFee().add(order.getWlPrice()));
						}
						order.setPayFlag(PayFlag.BUSINESS_CLOASE);
						order.setUpdateTime(TimeUtil.str2Date(currentTime));
						tOrderMapper.updateByPrimaryKeySelective(order);
					}
					//发起退款
					Integer payFee = BaseWxPayRequest.yuanToFen(order.getPayFee() + "");
					Integer refundFee = BaseWxPayRequest.yuanToFen(orderRefound.getRefoundFee() + "");
					try {
						Result result = null;
						MiniWxuserVo miniWxuserVo = wxuserService.selectByWxuserId(order.getFactpayWxuserId());
						Member member = miniWxuserVo.getMemberInfo();
						ColumnFlag columnFlag = columnFlagService.findByAppmodelId(order.getAppmodelId());
						//判断是否开启积分商城
						if (columnFlag.getShopFlag().equals(true)) {
							PrizeRule prizeRule = prizeRuleService.findByAppmodelId(order.getAppmodelId());
							Integer integral = getPrizeSum(
									orderRefound.getRefoundFee().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(),
									prizeRule.getTypeThreePay(), prizeRule.getTypeThreeGet());
							if (integral > 0) {
								member.setIntegralTotal(member.getIntegralTotal() - integral.intValue());
							}
						}
						//判断是否开启会员功能以及是否是会员
						if (columnFlag.getMemberFlag().equals(true) && member.getState().equals(1)) {
							MemberRankRule memberRankRule = memberRankRuleService
									.findBy("appmodelId", order.getAppmodelId());
							int addGrowthValue =
									orderRefound.getRefoundFee().intValue() * memberRankRule.getConsumeIntegral();
							member.setGrowthValue(member.getGrowthValue() - addGrowthValue);
							if ("余额支付".equals(order.getPayType())) {
								member.setSupplyBonus(
										member.getSupplyBonus() + orderRefound.getRefoundFee().doubleValue());
								balanceDetaiService
										.orderRefoundUpdate(order.getOrderId(), orderRefound.getRefoundFee().toString(),
												orderDetail.getQuantity(), member.getMemberId());
								result = ResultGenerator.genSuccessResult();
							}
						}
						if ("微信支付".equals(order.getPayType())) {
							if (refundFee == 0) {
								result = ResultGenerator.genSuccessResult();
							} else {
								result = wxServiceUtils
										.wechatRefund(order.getOutTradeNoExt(), orderRefound.getOrderRefoundId() + "",
												payFee, refundFee, paramVo.getAppmodelId());
							}
						}
						Product product = productService.findById(orderDetail.getProductId());
						product.setSalesVolume(product.getSalesVolume() - orderDetail.getQuantity());
						productService.update(product);
						memberService.update(member);
						activeMqClient
								.send(String.valueOf(order.getOrderId()), ActiviMqQueueName.ORDER_CLOSE_STOCK_RETURN);
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("orderRefoundId", paramVo.getOrderRefoundId());
						jsonObject.put("messageType", SendTemplatMessageType.REFUND_SUCCESS);
						activeMqClient
								.send(jsonObject.toJSONString(), ActiviMqQueueName.ORDER_MINIPROGRAM_TEMPLATE_MESSAGE);
						return result;
					} catch (Exception e) {
						e.printStackTrace();
						throw new ServiceException("退款失败");
					}
				}
				return ResultGenerator.genFailResult("退款订单更新失败");
			}
			return ResultGenerator.genFailResult("订单号有误");
		}
		return ResultGenerator.genFailResult("验证码不正确");
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Result refuseRefoundOrder(OrderParamVo orderRefound) {
		if (null != orderRefound && null != orderRefound.getOrderRefoundId()) {
			//查询退款订单
			OrderRefound refound = tOrderRefoundMapper.selectByPrimaryKey(orderRefound.getOrderRefoundId());
			if (refound != null) {
				OrderDetail detail = tOrderDetailMapper.selectByPrimaryKey(refound.getOrderDetailId());
				Order order = tOrderMapper.selectByPrimaryKey(refound.getOrderId());
				String nowTime = TimeUtil.getNowTime();
				Date date = TimeUtil.str2Date(nowTime);
                /*if (detail.getNumber() == 1 && order.getPayFlag().equals(AgentOrderPayFlag.PAY_OK) && orderRefound.getRefoundState().equals(2)) {
                //第一次申请,订单已支付状态,
                 *//**
				 * 订单属于已支付状态下,订单变为已发货订单,同时退货订单关闭,变为商家发货订单关闭状态
				 *//*
                    order.setPayFlag(AgentOrderPayFlag.DELIVERY);
                    order.setSendTime(nowTime);
                    order.setUpdateTime(date);
                    order.setWlName(orderRefound.getWlName());
                    order.setWlNum(orderRefound.getWlNum());
                    order.setWlCode(orderRefound.getWlCode());
                    order.setDistributeMode(orderRefound.getDistributeMode());
                    order.setDeliveryStaff(orderRefound.getDeliveryStaff());
                    if (tOrderMapper.updateByPrimaryKeySelective(order) > 0) {
                        detail.setApplyState(ApplyState.REGULAR);
                        detail.setRefuseState(RefuseState.NORMAL_ORDER);
                        detail.setUpdateTime(nowTime);
                        //记录操作
                        List<RefoundRecordVo> refoundRecordVos = JSON.parseArray(detail.getRecord(), RefoundRecordVo.class);
                        RefoundRecordVo record = new RefoundRecordVo();
                        record.setName("卖家");
                        record.setTime(nowTime);
                        record.setContent("卖家拒绝退款,退款失败");
                        refoundRecordVos.add(record);
                        detail.setRecord(JSONObject.toJSONString(refoundRecordVos));
                        if (tOrderDetailMapper.updateByPrimaryKeySelective(detail) == 0) {
                            throw new RuntimeException("订单更新操作失败");
                        }
                        refound.setBusinessState(BusinessState.REFUND_CLOSE);
                        refound.setFlowPath(FlowPathState.SEMD_PRODUCT);
                        refound.setUpdateTime(nowTime);
                        return updateRefund(refound, date.getTime());
                    }
                    return ResultGenerator.genFailResult("订单属于待发货的状态下,第一次被拒绝修改失败");
                }*/
				refound.setRefuseReason(orderRefound.getRefuseReason());
				//if (detail.getNumber() == 1 && order.getPayFlag().equals(AgentOrderPayFlag.DELIVERY) && orderRefound.getRefoundState().equals(1)) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("orderRefoundId", refound.getOrderRefoundId());
				jsonObject.put("messageType", SendTemplatMessageType.REFUSAL_OF_REFUND);
				if (detail.getNumber() == 1 && order.getPayFlag().equals(PayFlag.DELIVERY)) {
					/**
					 * 订单属于待收货的状态下并且是第一次被拒绝
					 * 第一次卖家拒绝买家在5天内还能再次发起申请,
					 * 没有再次申请直接关闭本次订单申请退款
					 */
					detail.setUpdateTime(nowTime);
					detail.setApplyState(ApplyState.REGULAR);
					detail.setRefuseState(RefuseState.NORMAL_ORDER);
					//记录操作
					List<RefoundRecordVo> refoundRecordVos = JSON.parseArray(detail.getRecord(), RefoundRecordVo.class);
					RefoundRecordVo record = new RefoundRecordVo();
					record.setName("卖家");
					record.setTime(nowTime);
					record.setContent("卖家拒绝退款,退款失败");
					refoundRecordVos.add(record);
					detail.setRecord(JSONObject.toJSONString(refoundRecordVos));
					if (tOrderDetailMapper.updateByPrimaryKeySelective(detail) == 0) {
						return ResultGenerator.genFailResult("更新订单详情失败");
					}
					refound.setBusinessState(BusinessState.REFUSE);
					refound.setFlowPath(FlowPathState.REFUSE_PRICE_PRODUCT);
					activeMqClient
							.send(jsonObject.toJSONString(), ActiviMqQueueName.ORDER_MINIPROGRAM_TEMPLATE_MESSAGE);
					return updateRefund(refound, date.getTime());
				}
				if (detail.getNumber() == 2) {
					detail.setApplyState(ApplyState.REFUND_FAIL);
					detail.setUpdateTime(nowTime);
					detail.setRefuseState(RefuseState.REFUND_CLOSE);
					if (tOrderDetailMapper.updateByPrimaryKeySelective(detail) == 0) {
						return ResultGenerator.genFailResult("更新订单详情失败");
					}
					refound.setBusinessState(BusinessState.REFUND_CLOSE);
					refound.setFlowPath(FlowPathState.REFUSE_PRICE_PRODUCT);
					refound.setUpdateTime(nowTime);
					//判断订单中商品是否全部退款成功,全部退款成功则关闭订单,则等待订单超时时间自动完成订单
					List<OrderDetail> details = orderDetailService.selectByOrderId(orderRefound.getOrderId());
					AtomicInteger number = new AtomicInteger(1);
					details.forEach(obj -> {
						if (!obj.getOrderDetailId().equals(detail.getOrderDetailId()) && obj.getApplyState()
								.equals(ApplyState.REFUND_OK)) {
							number.getAndIncrement();
						}
					});
					//全部退款成功修改订单为关闭订单
					if (details.size() == number.get()) {
						order.setPayFlag(PayFlag.BUSINESS_CLOASE);
						order.setUpdateTime(TimeUtil.str2Date(nowTime));
						tOrderMapper.updateByPrimaryKeySelective(order);
					}
					activeMqClient
							.send(jsonObject.toJSONString(), ActiviMqQueueName.ORDER_MINIPROGRAM_TEMPLATE_MESSAGE);
					return updateRefund(refound, date.getTime());
				}
				return ResultGenerator.genFailResult("订单条件出错");
			}
			return ResultGenerator.genFailResult("订单退款拒绝失败!");
		}
		return ResultGenerator.genFailResult("订单id错误");
	}

	private Result updateRefund(OrderRefound orderRefound, Long time) {
		orderRefound.setUpdateTime(TimeUtil.timestampToStr2(time));
		if (tOrderRefoundMapper.updateByPrimaryKeySelective(orderRefound) > 0) {
			//发送延时消息处理关闭订单
			if (orderRefound.getFlowPath().equals(FlowPathState.SEMD_PRODUCT)) {
				activeDelaySendJobHandler
						.savaTask(orderRefound.getOrderRefoundId().toString(), ActiviMqQueueName.CLOSE_ORDER_REFUND,
								TimeType.FIVEDAY, orderRefound.getAppmodelId());
			}
			return ResultGenerator.genSuccessResult();
		}
		throw new ServiceException("退款订单更新失败");
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Result agreeRefoundProduct(OrderRefound orderRefound) {
		if (null != orderRefound) {
			if (null == orderRefound.getShopAddress() || "".equals(orderRefound.getShopAddress())) {
				return ResultGenerator.genFailResult("退货地址为空");
			}
			orderRefound.setBusinessState(BusinessState.AGREE_PRICE_PRODUCT);
			orderRefound.setFlowPath(FlowPathState.USER_WRITE_EXPRESS);
			String nowTime = TimeUtil.getNowTime();
			orderRefound.setUpdateTime(nowTime);
			if (tOrderRefoundMapper.updateByPrimaryKeySelective(orderRefound) > 0) {
				OrderDetail detail = tOrderDetailMapper.findByDetailId(orderRefound.getOrderDetailId());
				List<RefoundRecordVo> recordVos = JSON.parseArray(detail.getRecord(), RefoundRecordVo.class);
				RefoundRecordVo recordVo = new RefoundRecordVo();
				recordVo.setName("卖家");
				recordVo.setTime(nowTime);
				JSONObject jsonObject = JSON.parseObject(orderRefound.getShopAddress());
				String[] addresses = jsonObject.getJSONObject("locationJson").getJSONArray("address")
						.toArray(new String[0]);
				StringBuffer buffer = new StringBuffer();
				for (String s : addresses) {
					buffer.append(s);
				}
				recordVo.setContent("卖家同意退货退款申请,请填写退货物流。商家收货地址:" + jsonObject.getString("userName") + " " + jsonObject
						.getString("phone") + " " + buffer.toString() + "" + jsonObject.getString("postCode"));
				recordVos.add(recordVo);
				detail.setRecord(JSONObject.toJSONString(recordVos));
				detail.setUpdateTime(nowTime);
				tOrderDetailMapper.updateByPrimaryKeySelective(detail);
				//同意退货之后用户5天之内未填写地址,退款订单自动关闭
				activeDelaySendJobHandler
						.savaTask(orderRefound.getOrderRefoundId().toString(), ActiviMqQueueName.AGREE_REFOUND_PRODUCT,
								TimeType.FIVEDAY, detail.getAppmodelId());
				JSONObject jsonData = new JSONObject();
				jsonData.put("orderRefoundId", orderRefound.getOrderRefoundId());
				jsonData.put("messageType", SendTemplatMessageType.AGREE_RETREAT_PRODUCT);
				activeMqClient.send(jsonData.toJSONString(), ActiviMqQueueName.ORDER_MINIPROGRAM_TEMPLATE_MESSAGE);
				return ResultGenerator.genSuccessResult();
			}
			throw new ServiceException("同意退货失败");
		}
		return ResultGenerator.genFailResult("参数有误");
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Result repealRefund(OrderRefound orderRefound) {
		if (null != orderRefound && null != orderRefound.getOrderRefoundId()) {
			orderRefound.setBusinessState(BusinessState.REFUND_CLOSE);
			String nowTime = TimeUtil.getNowTime();
			orderRefound.setUpdateTime(nowTime);
			orderRefound.setFlowPath(FlowPathState.USER_CANCEL_APPLYFOR);
			if (tOrderRefoundMapper.updateByPrimaryKeySelective(orderRefound) > 0) {
				OrderDetail orderDetail = tOrderDetailMapper.selectByPrimaryKey(orderRefound.getOrderDetailId());
				orderDetail.setApplyState(ApplyState.REGULAR);
				orderDetail.setUpdateTime(nowTime);
				List<RefoundRecordVo> recordVos = JSONObject.parseArray(orderDetail.getRecord(), RefoundRecordVo.class);
				RefoundRecordVo recordVo = new RefoundRecordVo();
				recordVo.setName("买家");
				recordVo.setContent("撤消了退款申请,退款申请已关闭");
				recordVo.setTime(nowTime);
				recordVos.add(recordVo);
				orderDetail.setRecord(JSON.toJSONString(recordVos));
				orderDetail.setRefuseState(RefuseState.NORMAL_ORDER);
				if (tOrderDetailMapper.updateByPrimaryKeySelective(orderDetail) > 0) {
					return ResultGenerator.genSuccessResult();
				}
				throw new ServiceException("订单更新失败");
			}
			return ResultGenerator.genFailResult("撤消失败");
		}
		return ResultGenerator.genFailResult("参数有误");
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Result deliverGoods(List<UpdateOrderVo> orderVos, String appmodelId) {
		List<Order> orders = BeanMapper.mapList(orderVos, Order.class);
		String nowTime = TimeUtil.getNowTime();
		if (orders.size() >= 1) {
			StringBuffer buffer = new StringBuffer();
			for (Order obj : orders) {
				String distributeMode = obj.getDistributeMode();
				JSONObject jsonDate = new JSONObject();
				jsonDate.put("orderId", obj.getOrderId());
				if ("物流配送".equals(distributeMode)) {
					jsonDate.put("messageType", SendTemplatMessageType.LOGISTICS_DISPATCHING);
				}
				if ("商家配送".equals(distributeMode)) {
					jsonDate.put("messageType", SendTemplatMessageType.MERCHANT_DISPATCHING);
				}
				activeMqClient.send(jsonDate.toJSONString(), ActiviMqQueueName.CONFIRM_RECEIPT);
				obj.setSendTime(nowTime);
				obj.setPayFlag(PayFlag.DELIVERY);
				obj.setUpdateTime(TimeUtil.str2Date(nowTime));

				if (tOrderMapper.updateByPrimaryKeySelective(obj) > 0) {
					activeDelaySendJobHandler.savaTask(obj.getOrderId().toString(), ActiviMqQueueName.CONFIRM_RECEIPT,
							TimeType.TWENTYDAY, appmodelId);
					buffer.append(obj.getOrderId() + ",");
				} else {
					throw new ServiceException("订单不存在");
				}
				List<OrderDetail> orderDetails = orderDetailService
						.findByList("orderId", buffer.substring(0, buffer.length()));
				buffer = buffer.delete(0, buffer.length());
				//修改订单详情的售后状态为正常订单
				for (OrderDetail orderDetail : orderDetails) {
					if (orderDetail.getApplyState().equals(ApplyState.APPLYFOR_IN)) {
						orderDetail.setApplyState(ApplyState.REGULAR);
						orderDetail.setRefuseState(RefuseState.NORMAL_ORDER);
						//记录操作
						List<RefoundRecordVo> refoundRecordVos = JSON
								.parseArray(orderDetail.getRecord(), RefoundRecordVo.class);
						RefoundRecordVo record = new RefoundRecordVo();
						record.setName("卖家");
						record.setTime(nowTime);
						record.setContent("卖家发货,本次退款关闭");
						refoundRecordVos.add(record);
						orderDetail.setRecord(JSONObject.toJSONString(refoundRecordVos));
						buffer.append(orderDetail.getOrderDetailId() + ",");
						orderDetailService.update(orderDetail);
					}
				}
				//把当前有申请售后的订单全部变为因为商家发货退款本次退款关闭
				if (buffer.length() > 0) {
					Condition condition = new Condition(OrderRefound.class);
					condition.createCriteria()
							.andIn("orderDetailId", Arrays.asList(buffer.substring(0, buffer.length() - 1).split(",")));
					List<OrderRefound> orderRefounds = tOrderRefoundMapper.selectByCondition(condition);
					for (OrderRefound orderRefound : orderRefounds) {
						orderRefound.setFlowPath(FlowPathState.SEMD_PRODUCT);
						orderRefound.setBusinessState(BusinessState.REFUND_CLOSE);
						updateRefund(orderRefound, TimeUtil.str2Timestamp(nowTime));
					}
				}
			}

			return ResultGenerator.genSuccessResult();
		}
		return ResultGenerator.genFailResult("修改参数出错");
	}

	@Override
	public Result findOrderDetail(String appmodelId, Long orderId) {
		Map<String, Object> map = new HashMap<>(2);
		map.put("appmodelId", appmodelId);
		map.put("orderId", orderId);
		OrderExtend orderExtend = tOrderMapper.findOrderDetail(map);
		Wxuser wxuser = tWxuserMapper.selectByPrimaryKey(orderExtend.getWxuserId());
		if (null != wxuser.getAvatarUrl()) {
			orderExtend.setAvatarUrl(wxuser.getAvatarUrl());
		}
		findCouponDiscount(orderExtend);
		orderExtend.setNickName(wxuser.getNikeName());
		return ResultGenerator.genSuccessResult(orderExtend);
	}

	private void findCouponDiscount(OrderExtend orderExtend) {
		if (orderExtend.getWxuserCouponId() != null && orderExtend.getWxuserCouponId() > 0) {
			Coupon coupon = couponService.findById(orderExtend.getWxuserCouponId());
			if (coupon != null) {
				if (coupon.getCouponType().equals(1) || coupon.getCouponType().equals(3)) {
					orderExtend.setCouponDiscount(coupon.getMinPrice().toString());
				}
				if (coupon.getCouponType().equals(2) || coupon.getCouponType().equals(4)) {
					List<OrderDetail> orderDetails = orderExtend.getOrderDetails();
					BigDecimal preferential = new BigDecimal(0);
					for (OrderDetail orderDetail : orderDetails) {
						preferential = preferential.add(orderDetail.getPreferential());
					}
					orderExtend.setCouponDiscount(preferential.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
					//如果有会员折扣则减去会员折扣优惠值
					if (orderExtend.getMemberDiscount() != null && orderExtend.getMemberDiscount() > 0) {
						BigDecimal notMemberDiscount = preferential.subtract(orderExtend.getTotalFee().subtract(
								orderExtend.getTotalFee()
										.multiply(new BigDecimal(orderExtend.getMemberDiscount() / 10))));
						orderExtend
								.setCouponDiscount(notMemberDiscount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
					}
				}
			}
		}
	}

	@Override
	public List<OrderDetail> findOrderRefund(Long wxuserId, Long detailId, String appmodelId) {
		Map<String, Object> param = new HashMap<>(3);
		param.put("wxuserId", wxuserId);
		param.put("appmodelId", appmodelId);
		if (null != detailId && detailId > 0) {
			param.put("detailId", detailId);
		}
		List<OrderDetail> refund = tOrderDetailMapper.findOrderRefund(param);
		if (refund.size() > 0) {
			StringBuffer buffer = new StringBuffer();
			refund.forEach(obj -> {
				buffer.append(obj.getOrderId() + ",");
			});
			List<Order> orders = tOrderMapper.selectByIds(buffer.substring(0, buffer.length() - 1));
			Map<Long, Integer> collect = orders.stream()
					.collect(Collectors.toMap(Order::getOrderId, Order::getPayFlag));
			refund.forEach(obj -> {
				obj.setPayFlag(collect.get(obj.getOrderId()));
			});
		}
		return refund;
	}

	@Override
	public List<OrderExtend> findOrderManager(OrderBtQueryVo paramVo) {
		Map<String, Object> param = getMapParam(paramVo);
		List<OrderExtend> orderExtends = tOrderMapper.findOrderManager(param);
		if (orderExtends.size() > 0) {
			StringBuffer buffer = new StringBuffer();
			orderExtends.forEach(obj -> {
				buffer.append(obj.getWxuserId() + ",");
				findCouponDiscount(obj);
				//如果订单详情中退款成功的商品,实付款价格需重新计算
				obj.getOrderDetails().forEach(detail -> {
					if (detail.getApplyState().equals(ApplyState.REFUND_OK)) {
						obj.setPayFee(obj.getPayFee().subtract(detail.getPrice().subtract(detail.getPreferential())));
					}
				});
			});
			//添加微信昵称
			List<Wxuser> wxusers = tWxuserMapper.selectByIds(buffer.substring(0, buffer.length() - 1));
			Map<Long, String> collect = wxusers.stream()
					.collect(Collectors.toMap(Wxuser::getWxuserId, Wxuser::getNikeName));
			orderExtends.forEach(obj -> {
				obj.setNickName(collect.get(obj.getWxuserId()));
			});
		}

		return orderExtends;
	}

	private Map<String, Object> getMapParam(OrderBtQueryVo paramVo) {
		Map<String, Object> param = new HashMap<>(16);
		param.put("appmodelId", paramVo.getAppmodelId());
		param.put("orderState", paramVo.getOrderState());
		param.put("productName", notNullParam(paramVo.getProductName()));
		param.put("nickName", notNullParam(paramVo.getNickName()));
		param.put("userName", notNullParam(paramVo.getUserName()));
		param.put("phone", notNullParam(paramVo.getPhone()));
		param.put("outTradeNum", notNullParam(paramVo.getOutTradeNum()));
		param.put("wlNum", notNullParam(paramVo.getWlNum()));
		if (null != paramVo.getPayTime() && !"".equals(paramVo.getPayTime())) {
			String[] split = paramVo.getPayTime().split(",");
			param.put("startTime", split[0]);
			param.put("endTime", split[1]);
		} else {
			param.put("startTime", null);
			param.put("endTime", null);
		}
		if (null != paramVo.getDetailId() && paramVo.getDetailId() > 0) {
			param.put("detailId", paramVo.getDetailId());
		}
		return param;
	}

	private String notNullParam(String str) {
		if (str != null && !str.equals("")) {
			return "%" + str + "%";
		}
		return null;
	}

	@Override
	public List<OrderDetail> findRefundOrderManager(OrderBtQueryVo queryVo) {
		Map<String, Object> param = getMapParam(queryVo);
		List<OrderDetail> refund = tOrderDetailMapper.findRefundIn(param);
		if (refund.size() > 0) {
			StringBuffer buffer = new StringBuffer();
			refund.forEach(obj -> {
				buffer.append(obj.getOrderId() + ",");
			});
			List<Order> orders = tOrderMapper.selectByIds(buffer.substring(0, buffer.length() - 1));
			String wxuserId = orders.stream().map(obj -> obj.getWxuserId().toString()).collect(Collectors.joining(","));
			//添加微信昵称
			if (wxuserId.length() > 0) {
				List<Wxuser> wxusers = tWxuserMapper.selectByIds(wxuserId);
				Map<String, Object> nickNmae = new HashMap<>();
				Map<String, Object> order = new HashMap<>();
				orders.forEach(obj -> {
					wxusers.forEach(obi -> {
						if (obj.getWxuserId().equals(obi.getWxuserId())) {
							nickNmae.put(obj.getOrderId() + "", obi.getNikeName());
						}
						if (order.get(obj.getOrderId() + "") == null) {
							order.put(obj.getOrderId() + "", obj);
						}
					});
				});
				refund.forEach(obj -> {
					obj.setNickName(nickNmae.get(obj.getOrderId() + "") + "");
					obj.setOrderInfo((Order) order.get(obj.getOrderId() + ""));
				});
			}
		}
		return refund;
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Result updateOrder(UpdateOrderVo paramVo) {
		Order order = BeanMapper.map(paramVo, Order.class);
		//商品改价
		boolean changePrice = order.getPayFee() != null && order.getPayFee().doubleValue() > 0 ? true : false;
		if (changePrice == true) {
			Order oldOrder = tOrderMapper.selectByPrimaryKey(order.getOrderId());
			List<OrderDetail> details = tOrderDetailMapper.selectByOrderId(oldOrder.getOrderId());
			int sum = details.stream().mapToInt(obj -> obj.getQuantity()).sum();
			//订单价格增加
			if (order.getPayFee().doubleValue() > oldOrder.getPayFee().doubleValue()) {
				return ResultGenerator.genFailResult("价格修改不能高于原价!!!");
			}
			//订单价格减少
			if (order.getPayFee().doubleValue() < oldOrder.getPayFee().doubleValue()) {
				for (OrderDetail detail : details) {
					//商品价格/原订单实际支付价格*修改的价格
					BigDecimal price = detail.getPrice().divide(oldOrder.getTotalFee(), 2, BigDecimal.ROUND_HALF_EVEN)
							.multiply(order.getPayFee());
					detail.setPrice(price.add(oldOrder.getWlPrice()));
					detail.setPreferential(new BigDecimal(0));
				}
				for (OrderDetail detail : details) {
					tOrderDetailMapper.updateByPrimaryKeySelective(detail);
				}
			}
			addMessage(order.getOrderId(), 1, 2, 0L, order.getAppmodelId());
		}
		if (tOrderMapper.updateByPrimaryKeySelective(order) > 0) {
			if (changePrice == true) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("orderId", order.getOrderId());
				jsonObject.put("messageType", SendTemplatMessageType.ORDER_CHANGE_PRICE);
				activeMqClient.send(jsonObject.toJSONString(), ActiviMqQueueName.ORDER_MINIPROGRAM_TEMPLATE_MESSAGE);
			}
			//如果是订单改价格
			return ResultGenerator.genSuccessResult();
		}
		return ResultGenerator.genFailResult("更新失败");
	}

	/**
	 *  获取商品名称
	 */
	private String productName(Long orderId) {
		StringBuffer productNameSb = new StringBuffer();
		List<OrderDetail> orderDetails = orderDetailService.selectByOrderId(orderId);
		if (orderDetails.size() > 0) {
			for (int i = 0; i < orderDetails.size(); i++) {
				if (i == orderDetails.size() - 1) {
					productNameSb = productNameSb.append(orderDetails.get(i).getProductName());
				} else {
					productNameSb = productNameSb.append(orderDetails.get(i).getProductName() + ",");
				}
			}
		}
		String productName = String.valueOf(productNameSb);
		return productName;
	}

	@Override
	public Result orderPayOkSum(String appmodelId) {
		OrderSensus orderSensus = new OrderSensus();
		Order order = new Order();
		//待发货订单数量
		order.setAppmodelId(appmodelId);
		orderSensus.setTotleOrderSum(tOrderMapper.selectCount(order));
		order.setPayFlag(1);
		orderSensus.setWaitSendProduct(tOrderMapper.selectCount(order));
		//退款中订单数量
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setAppmodelId(appmodelId);
		orderDetail.setApplyState(1);
		orderSensus.setRefundIn(tOrderDetailMapper.selectCount(orderDetail));
		//团购订单数量
		orderSensus.setGroupSum(0);
		//积分订单数量
		orderSensus.setIntegralSum(0);
		//代理采购订单数量
		orderSensus.setAgentSum(0);
		return ResultGenerator.genSuccessResult(orderSensus);
	}


	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Result generateOrder(SaveOrderVo saveOrderVo) {
		Order order = BeanMapper.map(saveOrderVo.getOrderInfo(), Order.class);
		if (order == null) {
			return ResultGenerator.genFailResult("订单错误");
		}
		Wxuser wxuser = tWxuserMapper.selectByPrimaryKey(order.getWxuserId());
		if (saveOrderVo.getFormIds() != null && saveOrderVo.getFormIds().length() > 0) {
			wxuserFormIdService.saveWxuserFoemId(saveOrderVo.getFormIds(), wxuser.getOpenId(), order.getAppmodelId());
		}
		order.setOrderId(IdGenerateUtils.getItemId());
		order.setOutTradeNo(IdGenerateUtils.getOrderNum());
		order.setCreateTime(TimeUtil.getNowTime());
		order.setPayFlag(0);
		order.setPayType("微信支付");
		//商品库存预减(秒杀库存,拼团库存,特价库存,商品总库存)
		//(指定时间内没有支付则关闭订单库存归还)
		//秒杀商品和拼团商品只能单独购买,特价可与普通商品同时购买
		synchronized (OrderServiceImpl.class) {
			List<ProductOrderVo> productOrderVos;
			if (order.getOrderType() != null && order.getOrderType().equals(ActivityType.GROUP.toString()) || order
					.getOrderType().equals(ActivityType.SECONDKILL.toString())) {
				productOrderVos = activityProductAdvanceReduction(saveOrderVo.getProductList().get(0));
			} else {
				//处理普通商品库
				productOrderVos = ProductAdvanceReduction(saveOrderVo.getProductList());
			}
			if (productOrderVos.size() > 0) {
				return ResultGenerator.genFailResult("商品库存不足", productOrderVos);
			}
			//商家版本是否达标
			String json = (String) redisTemplate.opsForValue().get(RedisPrefix.MANAGER_VERSION + order.getAppmodelId());
			Manager manager = JSONObject.parseObject(json, Manager.class);
			if (manager.getVersion() >= 2) {
				//商家是否开启会员功能,开启并且是会员就记录会员折扣
				ColumnFlag columnFlag = columnFlagService.findByAppmodelId(order.getAppmodelId());
				if (columnFlag.getMemberFlag()) {
					Member member = memberService.findMenberInfo(order.getWxuserId(), order.getAppmodelId());
					if (member.getState().equals(1)) {
						order.setMemberDiscount(member.getRankInfo().getDiscount());
					}
				}
			}
			//todo 参团和开团搁置
			if (orderMapper.insertSelective(order) > 0) {
				//优惠券状态处理
				if (order.getWxuserCouponId() != null && order.getWxuserCouponId() > 0) {
					//查询用户对应优惠券(取其中一张)
					Condition couponCondition = new Condition(CouponWxuser.class);
					couponCondition.createCriteria().andEqualTo("wxuserId", order.getWxuserId()).andEqualTo("flag", 0)
							.andEqualTo("couponId", order.getWxuserCouponId());
					List<CouponWxuser> couponWxusers = couponWxuserService.findByCondition(couponCondition);
					if (couponWxusers != null && couponWxusers.size() > 0) {
						CouponWxuser couponWxuser = couponWxusers.get(0);
						couponWxuser.setFlag(1);
						couponWxuserMapper.updateByPrimaryKeySelective(couponWxuser);
						//优惠券使用量增加
						Coupon coupon = couponService.findById(couponWxuser.getCouponId());
						coupon.setUsedQuantity(coupon.getUsedQuantity() + 1);
						couponService.update(coupon);
					}
				}
				if (order.getOrderType().equals(ActivityType.SECONDKILL.toString())) {
					activeDelaySendJobHandler
							.savaTask(order.getOrderId().toString(), ActiviMqQueueName.ORDER_CLOSE, TimeType.HALFHOUR,
									order.getAppmodelId());
				} else if (order.getOrderType().equals(ActivityType.GROUP.toString())) {

				} else {
					activeDelaySendJobHandler
							.savaTask(order.getOrderId().toString(), ActiviMqQueueName.ORDER_CLOSE, TimeType.NEXTDAY,
									order.getAppmodelId());
				}

				// 添加订单详情
				orderDetailService.saveOrderDetail(order, saveOrderVo.getProductList());
				return ResultGenerator.genSuccessResult(order.getOrderId());
			}
		}
		return ResultGenerator.genFailResult("下单错误");
	}

	@Override
	public OrderStatisticsVo findMiniOrderStatistics(Long wxuserId) {
		Query query = new Query(Criteria.where("wxuserId").is(wxuserId));
		UserOperation one = mongoTemplate.findOne(query, UserOperation.class);
		if (one == null) {
			one = new UserOperation();
			one.setWxuserId(wxuserId);
			mongoTemplate.save(one);
			return new OrderStatisticsVo();
		}

		OrderStatisticsVo orderStatisticsVo = new OrderStatisticsVo();
		Condition condition = new Condition(Order.class);
		condition.createCriteria().andEqualTo("wxuserId", wxuserId).andEqualTo("payFlag", 0);
		orderStatisticsVo.setPayment(tOrderMapper.selectCountByCondition(condition));

		condition = new Condition(Order.class);
		condition.createCriteria().andEqualTo("wxuserId", wxuserId).andEqualTo("payFlag", 1);
		orderStatisticsVo.setWaitSendProduct(tOrderMapper.selectCountByCondition(condition));

		condition = new Condition(Order.class);
		condition.createCriteria().andEqualTo("wxuserId", wxuserId).andEqualTo("payFlag", 2);
		orderStatisticsVo.setWaitCollectProduct(tOrderMapper.selectCountByCondition(condition));

		Map<String, Object> map4 = new HashMap<>(3);
		map4.put("wxuserId", wxuserId);
		map4.put("payFlag", 3);
		if (one.getLastLookOkOrderTime() != null) {
			map4.put("recordTime", one.getLastLookOkOrderTime());
		}
		orderStatisticsVo.setChangeHands(orderMapper.findMiniOrderStatistics(map4));

		//售后订单分两种  1-售后中一直显示  2-卖家已拒绝 退款关闭  退款完成
		Condition condition1 = new Condition(OrderRefound.class);
		List<Integer> businessState = new ArrayList<>();
		businessState.add(1);
		businessState.add(2);
		condition1.createCriteria().andEqualTo("wxuserId", wxuserId).andIn("businessState", businessState);
		int sum = tOrderRefoundMapper.selectCountByCondition(condition1);
		businessState.clear();
		businessState.add(3);
		businessState.add(4);
		businessState.add(5);
		if (one.getLastLookAfterSaleOrderTime() != null) {
			condition1.and().andGreaterThan("updateTime", one.getLastLookAfterSaleOrderTime());
		}
		orderStatisticsVo.setAfterSale(sum += tOrderRefoundMapper.selectCountByCondition(condition1));
		return orderStatisticsVo;
	}

	@Override
	public Integer findJoinActivityNumberOfPeople(Integer activityId, Integer activityType, String appmodelId) {
		return orderMapper.findJoinActivityNumberOfPeople(activityId, activityType, appmodelId);
	}

	@Override
	public Integer findActivityOrderPayOkSum(Integer activityId, Integer activityType, String appmodelId) {
		return orderMapper.findActivityOrderPayOkSum(activityId, activityType, appmodelId);
	}

	@Override
	public Double findActivityOrdertotleFee(Integer activityId, Integer activityType, String appmodelId) {
		return orderMapper.findActivityOrdertotleFee(activityId, activityType, appmodelId);
	}

	@Override
	public Boolean findIfBuyMax(Long wxuserId, Integer activityId, Integer activityType, Long productId,
			Integer maxQuantity) {
		List<OrderBuyMaxVO> orders = orderMapper.selectIfBuyMax(wxuserId, activityId, activityType);
		if (orders != null && orders.size() > 0) {
			AtomicInteger atomicInteger = new AtomicInteger(0);
			for (OrderBuyMaxVO order : orders) {
				List<OrderDetail> collect = order.getOrderDetails().stream()
						.filter(obj -> obj.getProductId().equals(productId)).collect(Collectors.toList());
				if (collect != null && collect.size() > 0) {
					for (OrderDetail orderDetail : collect) {
						atomicInteger.addAndGet(orderDetail.getQuantity());
					}
				}
			}
			if (atomicInteger.intValue() >= maxQuantity) {
				return false;
			}
		}
		return true;
	}


	/**
	 * 处理普通商品库存(如果其中有特价商品需要处理活动商品库存)
	 * @param productList
	 * @return
	 */
	private List<ProductOrderVo> ProductAdvanceReduction(List<ProductOrderVo> productList) {
		List<ProductOrderVo> productOrderVos = new ArrayList<>();
		List<Long> products = new ArrayList<>();
		for (ProductOrderVo productOrderVo : productList) {
			//商品是否特价商品
			if (productOrderVo.getJoinActiveInfo() != null && productOrderVo.getJoinActiveInfo().getActiveInfo() != null
					&& productOrderVo.getJoinActiveInfo().getActiveInfo().length() > 0) {
				List<ProductOrderVo> specialProduct = activityProductAdvanceReduction(productOrderVo);
				if (specialProduct.size() > 0) {
					productOrderVos.add(specialProduct.get(0));
				}
			} else {
				Product product = productService.findById(productOrderVo.getProductId());
				int surplus = product.getStock() - productOrderVo.getBuyQuantity();
				if (surplus < 0) {
					productOrderVos.add(productOrderVo);
					continue;
				}
				product.setStock(surplus);
				//处理普通商品多规格库存
				if (product.getSpecType() != null && product.getSpecType().equals(false)) {
					ProductSpecItem productSpecItem = JSONObject
							.parseObject(productOrderVo.getProductSpecItemInfo(), ProductSpecItem.class);
					ProductSpecItem specItem = specItemService.findById(productSpecItem.getProductSpecItemId());
					int specSurplus = specItem.getStock() - productOrderVo.getBuyQuantity();
					if (specSurplus < 0) {
						productOrderVos.add(productOrderVo);
						continue;
					}
					specItem.setStock(specSurplus);
					specItemService.update(specItem);
				}
				if (product.getStock().equals(0)) {
					//找出购买后库存为0的商品
					product.setShelfState(2);
					products.add(product.getProductId());
				}
				productService.update(product);
			}
		}
		//足迹,收藏,购物车,如果卖完需要更新商品状态为已售罄
		if (products.size() > 0) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("productIds", StringUtils.join(products, ","));
			jsonObject.put("shelfState", 2);
			activeMqClient.send(jsonObject.toJSONString(), ActiviMqQueueName.PRODUCT_SHELFSTATE_CHANGE);
		}
		return productOrderVos;
	}

	/**
	 * 秒杀商品 拼团商品 都只能单独下单 特价商品可与普通商品同时购买
	 * 活动商品库存预减,如果某个库存不足则返回库存不足的商品,不继续生成订单
	 * @param productOrderVo
	 * @return
	 */
	private List<ProductOrderVo> activityProductAdvanceReduction(ProductOrderVo productOrderVo) {
		List<ProductOrderVo> productOrderVos = new ArrayList<>();
		JSONObject activeInfo = JSON.parseObject(productOrderVo.getJoinActiveInfo().getActiveInfo());
		ActivityProduct activityProduct = activityProductService.findById(activeInfo.getLong("activityProductId"));

		Integer surplus = activityProduct.getActivityStock() - productOrderVo.getBuyQuantity();
		if (surplus < 0) {
			productOrderVos.add(productOrderVo);
			return productOrderVos;
		}
		//总规格商品库存处理(不管是否统一规格或多规格,都需要处理总规格)
		activityProduct.setActivityStock(surplus);
		//多规格活动商品的库存预减
		if (productOrderVo.getSpecType().equals(false)) {
			//因为是活动商品,规格的id是规格库存的id
			ProductSpecItem productSpecItem = JSON
					.parseObject(productOrderVo.getProductSpecItemInfo(), ProductSpecItem.class);
			ActivityProductStock productStocks = activityProductStockService
					.findById(productSpecItem.getProductSpecItemId());
			int specSurplus = productStocks.getActivityStock() - productOrderVo.getBuyQuantity();
			if (specSurplus < 0) {
				productOrderVos.add(productOrderVo);
				return productOrderVos;
			}
			productStocks.setActivityStock(specSurplus);
			if (productStocks.getActivityStock().equals(0)) {
			}
			activityProductStockService.update(productStocks);
		}
		activityProductService.update(activityProduct);
		return productOrderVos;
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Result payOrder(OrderPay orderPay) {
		Order order = orderMapper.selectByPrimaryKey(orderPay.getOrderId());
		if (order == null) {
			return ResultGenerator.genFailResult("订单不存在");
		}
		if (order.getPayFee().doubleValue() < 0.01) {
			return ResultGenerator.genFailResult("支付金额有误");
		}
		if (order.getPayFlag() == 1 || order.getPayFlag() == 2 || order.getPayFlag() == 3) {
			return ResultGenerator.genFailResult("订单已支付");
		}
		if (order.getPayFlag() == 4 || order.getPayFlag() == 5 || order.getPayFlag() == 6) {
			return ResultGenerator.genFailResult("订单已关闭");
		}
		Wxuser wxuser = wxuserService.findById(orderPay.getWxuserId());
		if (wxuser == null) {
			return ResultGenerator.genFailResult("支付失败");
		}
		if (!order.getWxuserId().equals(wxuser.getOpenId())) {
			order.setFactpayWxuserId(wxuser.getWxuserId());
		}
		order.setOutTradeNoExt(IdGenerateUtils.getOrderNum());

		switch (orderPay.getPayType()) {
			case 101:
				return WeChatPay(order, wxuser.getOpenId());
			case 102:
				//默认微信支付
				order.setPayType("余额支付");
				return balancePay(order, wxuser);
			default:
				break;
		}
		return ResultGenerator.genFailResult("订单出错");
	}

	private Result WeChatPay(Order order, String openId) {
		try {
			Map<String, String> map = wxServiceUtils
					.wxJsapiPayRequest(order.getPayType(), order.getOutTradeNoExt(), order.getPayFee().toString(),
							openId, Url.ORDER_NOTIFY, order.getAppmodelId());
			orderMapper.updateByPrimaryKeySelective(order);
			return ResultGenerator.genSuccessResult(map);
		} catch (Exception e) {
			log.error("发起微信支付请求失败");
			log.error("错误内容: ");
			log.error(e.getClass().toString());
			log.error(e.getMessage());
			throw new ServiceException("微信支付请求失败");
		}
	}

	private Result balancePay(Order order, Wxuser wxuser) {
		Member memberInfo = memberService.findById(wxuser.getMemberId());
		Double supplyBonus = memberInfo.getSupplyBonus() - order.getPayFee().doubleValue();
		if (supplyBonus < 0.0) {
			return ResultGenerator.genFailResult("余额不足");
		}
		memberInfo.setSupplyBonus(supplyBonus);
		memberService.update(memberInfo);
		//添加余额操作记录
		Long orderId = order.getOrderId();
		int countSize = orderDetailService.findCountSize(orderId);
		tOrderMapper.updateByPrimaryKeySelective(order);
		balanceDetaiService.addOrderRecord(order.getOrderId(), memberInfo.getMemberId(), countSize,
				order.getPayFee().doubleValue());
		//余额足够调起支付回调接口
		if (paySuccessOrder(order.getOutTradeNoExt()).equals("success")) {
			return ResultGenerator.genSuccessResult(order.getOrderId());
		}
		return ResultGenerator.genFailResult("支付失败");
	}

	/**
	 * 下单已经减库存,无需再减(如订单支付超时,则库存归还)
	 * @param outTradeNo
	 * @return
	 */
	private String paySuccessOrder(String outTradeNo) {
		Order condition = new Order();
		condition.setOutTradeNoExt(outTradeNo);
		Order order = tOrderMapper.selectOne(condition);
		if (order == null) {
			return "fail";
		}
		if (order.getPayFlag().equals(PayFlag.WAIT_PAY)) {
			PlaceAnOrderNotify.sendAll("订单号:" + order.getOrderId(), order.getAppmodelId());
			addMessage(order.getOrderId(), 1, 1, 0L, order.getAppmodelId());
			order.setPayFlag(PayFlag.PAY_OK);
			String nowTime = TimeUtil.getNowTime();
			order.setPayTime(nowTime);
			order.setUpdateTime(TimeUtil.str2Date(nowTime));
			//会员
			handleUserInfo(order);
			tOrderMapper.updateByPrimaryKeySelective(order);
			//商品销量处理
			List<OrderDetail> details = tOrderDetailMapper.selectByOrderId(order.getOrderId());
			String productIds = details.stream().map(obj -> obj.getProductId().toString())
					.collect(Collectors.joining(","));
			//商品总销量
			List<Product> products = tProductMapper.selectByIds(productIds);
			details.forEach(detail -> {
				products.forEach(product -> {
					if (detail.getProductId().equals(product.getProductId())) {
						product.setSalesVolume(product.getSalesVolume() + detail.getQuantity());
						tProductMapper.updateByPrimaryKeySelective(product);
					}
				});
			});
			//活动商品修改信息
			List<OrderDetail> activityProduct = details.stream()
					.filter(obj -> obj.getActivityInfo() != null && !obj.getActivityInfo().equals(""))
					.collect(Collectors.toList());
			if (activityProduct != null && activityProduct.size() > 0) {
				activityProduct.forEach(detail -> {
					JSONObject jsonObject = JSON.parseObject(detail.getActivityInfo());
					ActivityProduct activityProductInfo = jsonObject
							.getObject("ActivityProductInfo", ActivityProduct.class);
					if (activityProductInfo != null) {
						activityProductService
								.updateSoldQuantity(activityProductInfo.getActivityProductId(), detail.getQuantity());
					}
				});
			}
			return "success";
		}

		return "fail";
	}

	private void addMessage(Long orderId, Integer module, Integer type, Long orderRefundId, String appmodelId) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("orderId", orderId);
		jsonObject.put("module", module);
		jsonObject.put("type", type);
		jsonObject.put("orderRefundId", orderRefundId);
		jsonObject.put("appmodelId", appmodelId);
		if (orderRefundId != null && orderRefundId > 0) {
			OrderRefound orderRefound = tOrderRefoundMapper.selectByPrimaryKey(orderRefundId);
			jsonObject.put("wxuserId", orderRefound.getWxuserId());
		}
		activeMqClient.send(jsonObject.toJSONString(), ActiviMqQueueName.MESSAGE_NOTIFY);
	}

	/**
	 *
	 * 处理用户信息如优惠券,积分,会员等
	 * @param order
	 */
	private void handleUserInfo(Order order) {
		String json = (String) redisTemplate.opsForValue().get(RedisPrefix.MANAGER_VERSION + order.getAppmodelId());
		Manager manager = JSONObject.parseObject(json, Manager.class);
		//基础版以上的需要处理会员成长值,积分
		if (manager.getVersion() >= 2) {
			ColumnFlag flag = columnFlagService.findByAppmodelId(order.getAppmodelId());
			MiniWxuserVo wxuser = tWxuserMapper.selectByWxuserId(order.getWxuserId());
			//判断是否有开启会员功能
			if (flag.getMemberFlag().equals(true)) {
				//判断用户是否是会员
				if (wxuser.getMemberInfo().getState().equals(1)) {
					//添加会员成长
					addGrowthValue(wxuser, order.getPayFee().doubleValue());
				}
			}
			//判断是否开启积分商城
			if (flag.getShopFlag().equals(true)) {
				addIntegral(wxuser.getMemberInfo(), order.getPayFee().doubleValue());
			}
		}
	}

	/**
	 * 增加积分
	 * @param memberInfo
	 * @param price
	 */
	private void addIntegral(Member memberInfo, Double price) {
		PrizeRule prizeRule = prizeRuleService.findByAppmodelId(memberInfo.getAppmodelId());
		if (prizeRule != null) {
			int integrator = getPrizeSum(price, prizeRule.getTypeThreePay(), prizeRule.getTypeThreeGet());
			memberInfo.setIntegralTotal(memberInfo.getIntegralTotal() + integrator);
			memberService.update(memberInfo);
			prizeDetailService.addIntegral(2, memberInfo.getAppmodelId(), memberInfo.getWxuserId());
		}
	}

	/**
	 *计算价格计算后可得到的积分
	 * @param price
	 * @param maxPrize
	 * @param getIntegral
	 */
	private Integer getPrizeSum(Double price, Integer maxPrize, Integer getIntegral) {
		BigDecimal divide = new BigDecimal(price).divide(new BigDecimal(maxPrize));

		return divide.multiply(new BigDecimal(getIntegral)).setScale(2, BigDecimal.ROUND_HALF_UP).intValue();
	}

}
