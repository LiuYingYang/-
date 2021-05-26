package com.medusa.basemall.activemq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.medusa.basemall.configurer.WxServiceUtils;
import com.medusa.basemall.constant.*;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.core.ServiceException;
import com.medusa.basemall.integral.entity.PrizeRule;
import com.medusa.basemall.integral.service.PrizeRuleService;
import com.medusa.basemall.jobhandler.ActiveDelaySendJobHandler;
import com.medusa.basemall.order.entity.Order;
import com.medusa.basemall.order.entity.OrderDetail;
import com.medusa.basemall.order.entity.OrderRefound;
import com.medusa.basemall.order.service.BalanceDetaiService;
import com.medusa.basemall.order.service.OrderDetailService;
import com.medusa.basemall.order.service.OrderRefoundService;
import com.medusa.basemall.order.service.OrderService;
import com.medusa.basemall.order.vo.RefoundRecordVo;
import com.medusa.basemall.product.entity.LogisticCancel;
import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.product.service.LogisticCancelService;
import com.medusa.basemall.product.service.ProductService;
import com.medusa.basemall.shop.entity.ColumnFlag;
import com.medusa.basemall.shop.service.ColumnFlagService;
import com.medusa.basemall.user.entity.Member;
import com.medusa.basemall.user.entity.MemberRankRule;
import com.medusa.basemall.user.entity.Wxuser;
import com.medusa.basemall.user.service.MemberRankRuleService;
import com.medusa.basemall.user.service.MemberService;
import com.medusa.basemall.user.service.WxuserService;
import com.medusa.basemall.user.vo.MiniWxuserVo;
import com.medusa.basemall.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author whh
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class CustomerServiceListener {


	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceListener.class);

	@Resource
	private WxServiceUtils wxServiceUtils;

	@Resource
	private MemberService memberService;
	@Resource
	private ActiveMqClient activeMqClient;
	@Resource
	private ColumnFlagService columnFlagService;
	@Resource
	private PrizeRuleService prizeRuleService;
	@Resource
	private MemberRankRuleService memberRankRuleService;
	@Resource
	public OrderService orderService;
	@Resource
	public OrderRefoundService refoundService;
	@Resource
	private OrderDetailService orderDetailService;
	@Resource
	private ActiveDelaySendJobHandler activeDelaySendJobHandler;
	@Resource
	public WxuserService wxuserService;
	@Resource
	private LogisticCancelService logisticCancelService;
	@Resource
	private BalanceDetaiService balanceDetaiService;
	@Resource
	private ProductService productService;

	/**
	 * 退款订单
	 * 申请类型为
	 * 0-仅退款 5天商家无任何操作自动退款,变成退款成功的售后订单
	 * 1-退货退款 5天商家无任何操作自动同意退货退款
	 * 退款订单id
	 */
	@JmsListener(destination = ActiviMqQueueName.REFUND_ORDER)
	public void deliveryRefundOrder(String jsonData) {
		JSONObject jsonObject = JSON.parseObject(jsonData);
		OrderRefound orderRefound = refoundService.findById(jsonObject.getLong("id"));
		if (null != orderRefound) {
			OrderDetail detail = orderDetailService.findById(orderRefound.getOrderDetailId());
			Order order = orderService.findBy("orderId", orderRefound.getOrderId());
			String newtiem = TimeUtil.getNowTime();
			//5天商家无任何操作,
			if (orderRefound.getFlowPath().equals(FlowPathState.APPLYFOR_IN)) {
				//仅退款类型订单 自动退款,自动同意退款,直接退款给用户
				if (orderRefound.getRefoundType().equals(0)) {
					//更新商品为已成功退款
					detail.setApplyState(ApplyState.REFUND_OK);
					detail.setUpdateTime(newtiem);
					List<RefoundRecordVo> recordVos = JSON.parseArray(detail.getRecord(), RefoundRecordVo.class);
					RefoundRecordVo recordVo = new RefoundRecordVo();
					recordVo.setName("系统");
					recordVo.setTime(newtiem);
					recordVo.setContent("因商家超时未处理，系统自动退款成功。");
					recordVos.add(recordVo);
					detail.setRecord(JSON.toJSONString(recordVos));
					orderDetailService.update(detail);
					//判断订单中商品是否全部退款成功
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
						//在订单属于待发货时同意退款,同时还是最后一个退货商品,需要退还邮费
						if (order.getPayFlag().equals(PayFlag.PAY_OK)) {
							//添加邮费价格
							orderRefound.setRefoundFee(orderRefound.getRefoundFee().add(order.getWlPrice()));
						}
						order.setPayFlag(PayFlag.BUSINESS_CLOASE);
						order.setUpdateTime(TimeUtil.str2Date(newtiem));
						orderService.update(order);
					}
					//设置退款
					orderRefound.setFlowPath(FlowPathState.OVER_TIME);
					orderRefound.setBusinessState(BusinessState.REFUND_OK);
					orderRefound.setRefoundTime(newtiem);
					orderRefound.setUpdateTime(newtiem);
					if (refoundService.update(orderRefound) > 0) {
						try {
							Integer payFee = BaseWxPayRequest.yuanToFen(order.getPayFee() + "");
							Integer refundFee = BaseWxPayRequest.yuanToFen(orderRefound.getRefoundFee() + "");
							Result result = null;
							MiniWxuserVo miniWxuserVo = wxuserService.selectByWxuserId(order.getFactpayWxuserId());
							Member member = miniWxuserVo.getMemberInfo();
							ColumnFlag columnFlag = columnFlagService.findByAppmodelId(order.getAppmodelId());
							//判断是否开启积分商城
							if (columnFlag.getShopFlag().equals(true)) {
								PrizeRule prizeRule = prizeRuleService.findByAppmodelId(order.getAppmodelId());
								BigDecimal integral = orderRefound.getRefoundFee()
										.multiply(new BigDecimal(prizeRule.getTypeThreeGet()));
								if (integral.doubleValue() > 0) {
									member.setIntegralTotal(0);
								} else {
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
									balanceDetaiService.orderRefoundUpdate(order.getOrderId(),
											orderRefound.getRefoundFee().toString(), detail.getQuantity(),
											member.getMemberId());
									result = ResultGenerator.genSuccessResult();
								}
							}
							if ("微信支付".equals(order.getPayType())) {
								if (refundFee == 0) {
									result = ResultGenerator.genSuccessResult();
								} else {
									result = wxServiceUtils.wechatRefund(order.getOutTradeNoExt(),
											orderRefound.getOrderRefoundId() + "", payFee, refundFee,
											order.getAppmodelId());
								}
							}
							Product product = productService.findById(detail.getProductId());
							product.setSalesVolume(product.getSalesVolume() - detail.getQuantity());
							productService.update(product);
							memberService.update(member);
							activeDelaySendJobHandler.savaTask(String.valueOf(order.getOrderId()),
									ActiviMqQueueName.ORDER_CLOSE_STOCK_RETURN, 0L, "");
							JSONObject json = new JSONObject();
							json.put("orderRefoundId", orderRefound.getOrderRefoundId());
							json.put("messageType", SendTemplatMessageType.REFUND_SUCCESS);
							activeMqClient
									.send(json.toJSONString(), ActiviMqQueueName.ORDER_MINIPROGRAM_TEMPLATE_MESSAGE);
							if (result.getCode() == 100) {
								activeDelaySendJobHandler.updateState(jsonObject.getString("activeMqTaskTOId"));
								LOGGER.info("退款订单5天商家无任何操作自动退款成功,退款订单号:  " + orderRefound.getOrderRefoundId());
								return;
							} else {
								LOGGER.info("退款失败,退款订单号:  " + orderRefound.getOrderRefoundId());
								LOGGER.info("错误信息 : " + result.getMessage());
								throw new ServiceException("退款失败,退款订单号:" + orderRefound.getOrderRefoundId());
							}
						} catch (Exception e) {
							e.printStackTrace();
							throw new ServiceException("退款失败,退款订单号:" + orderRefound.getOrderRefoundId());
						}
					}
					throw new ServiceException("退款失败,退款订单号:" + orderRefound.getOrderRefoundId());
				}
				//退货退款的申请订单,自动填写买家默认收货地址
				if (orderRefound.getRefoundType().equals(1)) {
					Condition condition = new Condition(LogisticCancel.class);
					condition.createCriteria().andEqualTo("defaultState", 1)
							.andEqualTo("appmodelId", orderRefound.getAppmodelId());
					List<LogisticCancel> logisticCancels = logisticCancelService.findByCondition(condition);
					String[] addresses = JSON.parseObject(logisticCancels.get(0).getLocationJson())
							.getJSONArray("address").toArray(new String[0]);
					StringBuffer buffer = new StringBuffer();
					for (String s : addresses) {
						buffer.append(s);
					}
					detail.setRefuseState(RefuseState.AGREE_PRICE_PRODUCT);
					detail.setApplyState(ApplyState.APPLYFOR_IN);
					List<RefoundRecordVo> recordVos = JSON.parseArray(detail.getRecord(), RefoundRecordVo.class);
					RefoundRecordVo recordVo = new RefoundRecordVo();
					recordVo.setName("系统");
					recordVo.setTime(newtiem);
					recordVo.setContent("因商家超时未处理,系统自动选择默认收货地址 : " + buffer.toString());
					recordVos.add(recordVo);
					detail.setRecord(JSON.toJSONString(recordVos));
					orderDetailService.update(detail);
					orderRefound.setBusinessState(BusinessState.AGREE_PRICE_PRODUCT);
					orderRefound.setFlowPath(FlowPathState.USER_WRITE_EXPRESS);
					orderRefound.setShopAddress(JSON.toJSONString(logisticCancels.get(0)));
					orderRefound.setUpdateTime(newtiem);
					//自动同意退货之后用户5天之内未填写地址,退款订单自动关闭
					activeDelaySendJobHandler.savaTask(orderRefound.getOrderRefoundId().toString(),
							ActiviMqQueueName.AGREE_REFOUND_PRODUCT, TimeType.FIVEDAY,
							orderRefound.getAppmodelId());
					refoundService.update(orderRefound);
					activeDelaySendJobHandler.updateState(jsonObject.getString("activeMqTaskTOId"));
				}
				return;
			}
		}
		LOGGER.info("退款订单5天商家无任何操作自动退款失败,退款订单号:  " + jsonObject.getLong("id"));
	}

	/**
	 * 同意退货之后用户5天之内未填写地址,退款订单自动关闭
	 *
	 * orderRefoundId
	 */
	@JmsListener(destination = ActiviMqQueueName.AGREE_REFOUND_PRODUCT)
	public void agreeRefoundProduct(String jsonData) {
		JSONObject jsonObject = JSON.parseObject(jsonData);
		OrderRefound orderRefound = refoundService.findById(jsonObject.getLong("id"));
		if (null != orderRefound) {
			if (orderRefound.getFlowPath().equals(FlowPathState.SELLER_OVER_TIME) || orderRefound.getFlowPath()
					.equals(FlowPathState.USER_WRITE_EXPRESS)) {
				orderRefound.setFlowPath(FlowPathState.WAIT_OVER_TIME);
				orderRefound.setBusinessState(BusinessState.REFUND_CLOSE);
				String nowTime = TimeUtil.getNowTime();
				orderRefound.setUpdateTime(nowTime);
				if (refoundService.update(orderRefound) > 0) {
					OrderDetail detail = orderDetailService.findById(orderRefound.getOrderDetailId());
					detail.setUpdateTime(nowTime);
					detail.setApplyState(ApplyState.REGULAR);
					List<RefoundRecordVo> recordVos = JSON.parseArray(detail.getRecord(), RefoundRecordVo.class);
					RefoundRecordVo recordVo = new RefoundRecordVo();
					recordVo.setName("系统");
					recordVo.setTime(nowTime);
					recordVo.setContent("因您超时未填写物流,本次售后关闭!!!");
					recordVos.add(recordVo);
					detail.setRecord(JSON.toJSONString(recordVos));
					orderDetailService.update(detail);
					if (orderDetailService.update(detail) == 0) {
						LOGGER.info("退款单号:  " + jsonObject.getLong("id") + "退款失败,订单更新操作失败!!!");
						throw new RuntimeException("退款单号:  " + jsonObject.getLong("id") + "退款失败,订单更新操作失败");
					}
					activeDelaySendJobHandler.updateState(jsonObject.getString("activeMqTaskTOId"));
					return;
				}
				LOGGER.info("退款单号:  " + jsonObject.getLong("id") + "退款失败,订单更新操作失败!!!");
			}
			return;
		}
		LOGGER.info("同意退货之后用户5天之内未填写地址,退款订单自动关闭,查无此订单号: " + jsonObject.getLong("id"));
	}

	/**
	 * 卖家发货之后买家20天内未确认收货自动变为交易成功订单
	 */
	@JmsListener(destination = ActiviMqQueueName.CONFIRM_RECEIPT)
	public void confirmReceipt(String jsonData) {
		JSONObject jsonObject = JSON.parseObject(jsonData);
		Order order = orderService.findById(jsonObject.getLong("id"));
		if (null != order) {
			if (order.getPayFlag().equals(PayFlag.DELIVERY)) {
				order.setPayFlag(PayFlag.BUSINESS_OK);
				order.setUpdateTime(TimeUtil.str2Date(TimeUtil.getNowTime()));
				if (orderService.update(order) > 0) {
					activeDelaySendJobHandler.updateState(jsonObject.getString("activeMqTaskTOId"));
					LOGGER.info("订单号 " + jsonObject.getLong("id") + " 买家确认收20天内未操作,自动变更成功");
					return;
				}
			}
			return;
		}
		LOGGER.info("家发货之后买家15天内未确认收货自动变为交易成功订单 ,查无此订单号: " + jsonObject.getLong("id"));
	}

	/**
	 * 退款订单(待收货订单退款)
	 * 5天商家无任何操作自动退款,变成退款成功的售后订单
	 * 两种退款形式
	 * 仅退款和退货退款    两种形式5天未操作都直接退款 变成退款成功的售后订单
	 * 仅退款形式:
	 * 同意:
	 * 直接退款(发送模板消息)
	 * 拒绝:
	 * 第一次卖家拒绝买家在5天内还能再次发起申请,没有再次申请直接关闭订单
	 * 第二次卖家拒绝直接变成 交易成功订单 和 退款关闭的售后订单
	 * 退货退款 :
	 * 同意:
	 * 情况1.
	 * 等待买家发货,5天买家没发货就变成退款关闭的订单;
	 * 情况2.
	 * 买家发货,15天内卖家无任何操作,自动退款给买家;
	 * 15内卖家收到货之后,可以确认收货并退款或拒绝;
	 * 确认收货并退款,变成退款成功的售后订单;
	 * 拒绝退款,买家订单可退款次数-1,如果是最后一次直接变成退款关闭的售后订单;
	 * 拒绝:
	 * 第一次卖家拒绝买家在5天内还能再次发起申请
	 * 第二次卖家拒绝直接变成 交易成功订单 和 退款关闭的售后订单
	 *//*


	 */

	/**
	 * 拒绝退款/退货
	 * 第一次卖家拒绝买家在5天内还能再次发起申请,没有再次申请直接关闭订单退款,订单变为已完成状态
	 *orderRefoundId
	 */
	@JmsListener(destination = ActiviMqQueueName.CLOSE_ORDER_REFUND)
	public void closeOrderRefund(String jsonData) {
		JSONObject jsonObject = JSON.parseObject(jsonData);
		OrderRefound orderRefound = refoundService.findById(jsonObject.getLong("id"));
		activeDelaySendJobHandler.updateState(jsonObject.getString("activeMqTaskTOId"));
		if (null != orderRefound) {
			if (orderRefound.getBusinessState().equals(BusinessState.REFUSE)) {
				orderRefound.setBusinessState(BusinessState.REFUND_CLOSE);
				orderRefound.setFlowPath(FlowPathState.USER_OVER_TIEM);
				orderRefound.setUpdateTime(TimeUtil.getNowTime());
				refoundService.update(orderRefound);
			}
			return;
		}
		LOGGER.info("第一次卖家拒绝买家在5天内还能再次发起申请,没有再次申请直接关闭订单,查无此订单号: " + jsonObject.getLong("id"));
	}

	@JmsListener(destination = ActiviMqQueueName.BUYERS_DELIVERY_AUTO_REFUND_REMIND)
	public void buyersDeliveryAutoRefundRemind(String jsonData) {
		JSONObject jsonObject = JSON.parseObject(jsonData);
		activeDelaySendJobHandler.updateState(jsonObject.getString("activeMqTaskTOId"));
		OrderRefound orderRefound = refoundService.findById(jsonObject.getLong("id"));
		Order order = orderService.findById(orderRefound.getOrderId());
		OrderDetail orderDetail = orderDetailService.findById(orderRefound.getOrderDetailId());
		Wxuser wxuser = wxuserService.findById(order.getWxuserId());
		JSONObject date = new JSONObject();
		jsonObject.put("first", "即将超时退款提醒");
		List<String> keywords = new LinkedList<>();
		keywords.add(order.getOutTradeNo());
		keywords.add(String.valueOf(orderDetail.getPrice()));
		keywords.add(wxuser.getNikeName());
		keywords.add("24小时后");
		jsonObject.put("keywords", keywords);
		jsonObject.put("remark", "您有订单即将超时，系统将做退款处理，请及时登陆后台处理");
		jsonObject.put("appmodelId", order.getAppmodelId());
		jsonObject.put("type", 2);

		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("orderId", "");
		jsonObject2.put("module", 3);
		jsonObject2.put("type", 2);
		jsonObject2.put("orderRefundId", orderRefound.getOrderRefoundId());
		jsonObject2.put("wxuserId", orderRefound.getWxuserId());
		activeMqClient.send(jsonObject2.toJSONString(), ActiviMqQueueName.MESSAGE_NOTIFY);

		activeMqClient.send(jsonObject.toJSONString(), ActiviMqQueueName.ORDER_FWH_MESSAGE);

	}
	/**
	 * 买家物流填写完成后,20天内卖家无任何操作,自动退款给买家;
	 *
	 * orderRefoundId
	 */
	@JmsListener(destination = ActiviMqQueueName.BUYERS_DELIVERY)
	public void buyersDelivery(String jsonData) {
		JSONObject jsonObject = JSON.parseObject(jsonData);
		activeDelaySendJobHandler.updateState(jsonObject.getString("activeMqTaskTOId"));
		OrderRefound orderRefound = refoundService.findById(jsonObject.getLong("id"));
		if (null != orderRefound) {
			if (orderRefound.getFlowPath().equals(FlowPathState.SELLER_CONFIRM)) {
				String nowTime = TimeUtil.getNowTime();
				orderRefound.setFlowPath(FlowPathState.SELLER_OVER_AUTO_REFUND);
				orderRefound.setBusinessState(BusinessState.REFUND_OK);
				orderRefound.setUpdateTime(nowTime);
				if (refoundService.update(orderRefound) == 0) {
					LOGGER.info("更新退款订单失败,退款单号:" + orderRefound);
					return;
				}
				OrderDetail detail = orderDetailService.findById(orderRefound.getOrderDetailId());
				detail.setUpdateTime(nowTime);
				detail.setApplyState(ApplyState.REFUND_OK);
				if (orderDetailService.update(detail) == 0) {
					LOGGER.info("更新退款订单失败,退款单号:" + orderRefound);
					throw new RuntimeException("订单详情更新失败");
				}
				Order order = orderService.findById(orderRefound.getOrderId());
				List<OrderDetail> details = orderDetailService.selectByOrderId(orderRefound.getOrderId());
				//判断订单中商品是否全部退款成功
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
					if (orderService.update(order) == 0) {
						LOGGER.info("更新退款订单失败,退款单号:" + orderRefound);
						throw new RuntimeException("订单更新失败");
					}
				}
				try {
					Result result = null;
					Integer payFee = BaseWxPayRequest.yuanToFen(order.getPayFee() + "");
					Integer refundFee = BaseWxPayRequest.yuanToFen(orderRefound.getRefoundFee() + "");
					Member member = memberService.findBy("wxuserId", order.getWxuserId());
					ColumnFlag columnFlag = columnFlagService.findByAppmodelId(order.getAppmodelId());
					//判断是否开启积分商城
					if (columnFlag.getShopFlag().equals(true)) {
						PrizeRule prizeRule = prizeRuleService.findByAppmodelId(order.getAppmodelId());
						BigDecimal integral = orderRefound.getRefoundFee()
								.multiply(new BigDecimal(prizeRule.getTypeThreeGet()));
						if (integral.doubleValue() > 0)  {
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
							member.setSupplyBonus(member.getSupplyBonus() + orderRefound.getRefoundFee().doubleValue());
							balanceDetaiService
									.orderRefoundUpdate(order.getOrderId(), orderRefound.getRefoundFee().toString(),
											detail.getQuantity(), member.getMemberId());
							result = ResultGenerator.genSuccessResult();
						}
					}
					if ("微信支付".equals(order.getPayType())) {
						if (refundFee == 0) {
							result = ResultGenerator.genSuccessResult();
						} else {
							result = wxServiceUtils
									.wechatRefund(order.getOutTradeNoExt(), orderRefound.getOrderRefoundId() + "",
											payFee, refundFee, order.getAppmodelId());
						}
					}
					memberService.update(member);
					if (result.getCode() == 100) {

						LOGGER.info(
								"卖家同意退货之后,买家发货,15天内卖家无任何操作,自动退款给买家,退款成功,退款订单号:  " + orderRefound.getOrderRefoundId());
						return;
					} else {
						LOGGER.info("退款失败,退款订单号:  " + orderRefound.getOrderRefoundId());
						LOGGER.info("错误信息 : " + result.getMessage());
						throw new RuntimeException("退款失败,退款订单号:" + orderRefound.getOrderRefoundId());
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("退款失败,事物回滚");
				}
			}
			return;
		}
		LOGGER.info("买家发货,15天内卖家无任何操作,自动退款给买家,查无此订单号: " + orderRefound.getOrderRefoundId());
	}

}
