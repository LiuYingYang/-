package com.medusa.basemall.activemq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;
import com.medusa.basemall.configurer.WxServiceUtils;
import com.medusa.basemall.constant.ActiviMqQueueName;
import com.medusa.basemall.constant.Url;
import com.medusa.basemall.messages.dao.WxuserFormIdMapper;
import com.medusa.basemall.messages.entity.WxuserFormId;
import com.medusa.basemall.order.entity.Order;
import com.medusa.basemall.order.entity.OrderDetail;
import com.medusa.basemall.order.entity.OrderRefound;
import com.medusa.basemall.order.service.OrderDetailService;
import com.medusa.basemall.order.service.OrderRefoundService;
import com.medusa.basemall.order.service.OrderService;
import com.medusa.basemall.product.entity.LogisticCancel;
import com.medusa.basemall.product.service.LogisticCancelService;
import com.medusa.basemall.user.entity.*;
import com.medusa.basemall.user.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class MiniTemplatMessageListerner {

	@Resource
	private WxServiceUtils wxServiceUtils;
	@Resource
	private OrderRefoundService refoundService;
	@Resource
	private LogisticCancelService logisticCancelService;
	@Resource
	private MemberService memberService;
	@Resource
	private MemberRankService memberRankService;
	@Resource
	private MemberRechargeRecordService memberRechargeRecordService;
	@Resource
	private MemberActiveRechargeService activeRechargeService;
	@Resource
	public OrderService orderService;
	@Resource
	public OrderDetailService orderDetailService;
	@Resource
	private WxuserService wxuserService;
	@Resource
	private WxuserFormIdMapper tWxuserFormIdMapper;


	private static Logger logger = LoggerFactory.getLogger(MiniTemplatMessageListerner.class);

	/**
	 * orderId messageType
	 *
	 * @param jsonData
	 */
	@JmsListener(destination = ActiviMqQueueName.ORDER_MINIPROGRAM_TEMPLATE_MESSAGE)
	public void orderMiniProgramTemplateMessage(String jsonData) {
		JSONObject jsonObject = JSON.parseObject(jsonData);
		Integer messageType = jsonObject.getInteger("messageType");
		WxuserFormId wxuserFormId = null;
		String templateId = "";
		try {
			if (messageType > 1000 && messageType < 3000) {
				Long orderId = jsonObject.getLong("orderId");
				Order order = orderService.findById(orderId);
				Wxuser wxuser = wxuserService.findById(order.getWxuserId());
				List<WxuserFormId> wxuserFormIds = tWxuserFormIdMapper.selectByOpenId(wxuser.getOpenId());
				if (wxuserFormIds.size() == 0) {
					return;
				}
				wxuserFormId = wxuserFormIds.get(0);
				String appid = order.getAppmodelId().substring(9, order.getAppmodelId().length());
				JSONObject params = new JSONObject();
				params.put("orderId", orderId);
				params.put("type", "order");
				String page = Url.MINI_TEMPLATE_MESSAGE_PAGE.replace("URL", "/pages/orderDetail/orderDetail")
						.replace("JSON", params.toJSONString());
				switch (messageType) {
					// 物流配送消息通知
					case 1001:
						templateId = getTemplateId(appid, "AT0007");
						OrderLogisticsDispatching(wxuserFormId.getFormValue(), wxuser.getOpenId(), order, templateId,
								appid,page);
						break;
					// 商家配送消息通知
					case 1002:
						templateId = getTemplateId(appid, "AT0177");
						MerchantDispatching(wxuserFormId.getFormValue(), wxuser.getOpenId(), order, templateId, appid,page);
						break;
					// 订单改价通知
					case 2001:
						templateId = getTemplateId(appid, "AT0693");
						orderChangePrice(wxuserFormId.getFormValue(), wxuser.getOpenId(), order, templateId, appid,page);
						break;
					case 2002:
						// 订单关闭通知
						templateId = getTemplateId(appid, "AT1410");
						orderClose(wxuserFormId.getFormValue(), wxuser.getOpenId(), order, templateId, appid,page);
						break;
					default:
						break;
				}
			} else if (messageType > 3000 && messageType < 4000) {
				Long orderRefoundId = jsonObject.getLong("orderRefoundId");
				OrderRefound orderRefound = refoundService.findById(orderRefoundId);
				Wxuser wxuser = wxuserService.findById(orderRefound.getWxuserId());
				wxuserFormId = tWxuserFormIdMapper.selectByOpenId(wxuser.getOpenId()).get(0);
				String appid = orderRefound.getAppmodelId().substring(9, orderRefound.getAppmodelId().length());
				JSONObject params = new JSONObject();
				params.put("orderId", orderRefound.getOrderId());
				params.put("type", "order");
				String page = Url.MINI_TEMPLATE_MESSAGE_PAGE.replace("URL", "/pages/orderDetail/orderDetail")
						.replace("JSON", params.toJSONString());
				switch (messageType) {
					case 3001:
						// 售后订单拒绝退款
						templateId = getTemplateId(appid, "AT1983");
						refusalOfRefund(wxuserFormId.getFormValue(), wxuser.getOpenId(), orderRefound, templateId,
								appid,page);
						break;
					case 3002:
						// 售后订单同意退货
						templateId = getTemplateId(appid, "AT0956");
						agreeToReturn(wxuserFormId.getFormValue(), wxuser.getOpenId(), orderRefound, templateId, appid,page);
						break;
					case 3003:
						// 售后订单退款成功
						templateId = getTemplateId(appid, "AT0787");
						refundSuccess(wxuserFormId.getFormValue(), wxuser.getOpenId(), orderRefound, templateId, appid,page);
						break;
					default:
						break;
				}
			} else if (messageType > 4000 && messageType < 5000) {
				String page = "";
				switch (messageType) {
					case 4001:
						page =Url.MINI_TEMPLATE_MESSAGE_PAGE.replace("URL", "/pages/memberCenter/memberCenter")
								.replace("JSON", "{}");
						//会员升级通知 AT0557
						wxuserFormId = updateNotification(jsonObject.getLong("memberId"),
								jsonObject.getInteger("oldRankId"), jsonObject.getInteger("newRankId"),page);
						break;
					case 4002:
						 page =Url.MINI_TEMPLATE_MESSAGE_PAGE.replace("URL", "/pages/memberCenter/memberCenter")
								.replace("JSON", "{}");
						// 充值成功通知 AT0016
						wxuserFormId = beRechargedSuccessfully(jsonObject.getInteger("rechargeRecordId"),page);
						break;
					case 4003:
						// 参团成功提醒 AT0933", new Integer[]{18, 3, 4, 26, 7});
						wxuserFormId = null;
						break;
					case 4004:
						// 拼团成功通知 AT0051", new Integer[]{1, 13, 6, 15, 39});
						wxuserFormId = null;
						break;
					case 4005:
						// 拼团失败通知AT0310", new Integer[]{2, 4, 15, 5, 11});
						wxuserFormId = null;
						break;
					default:
						break;
				}
			} else if (messageType > 5000 && messageType < 6000) {

				switch (messageType) {
					case 5001:
						// 积分兑换成功通知 AT1213", new Integer[]{1, 13, 6, 7, 4});
						wxuserFormId = null;
						break;
					case 5002:
						// 积分到期提醒 AT1223", new Integer[]{1, 8, 7, 4});
						wxuserFormId = IntegralDueRemind(jsonObject);
						break;
					case 5003:
						// 申请成功提醒 AT0197", new Integer[]{4, 1, 44, 25});
						wxuserFormId = null;
						break;
					case 5004:
						// 注册成功提醒 AT0208", new Integer[]{17, 41, 13, 7, 44});
						wxuserFormId = null;
						break;
					default:
						break;
				}
			}
			tWxuserFormIdMapper.delete(wxuserFormId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private WxuserFormId IntegralDueRemind(JSONObject jsonObject) {
		Wxuser wxuser = wxuserService.findById(jsonObject.getLong("wxuserId"));
		String authorizerAppid = wxuser.getAppmodelId().substring(9, wxuser.getAppmodelId().length());
		String templateId = getTemplateId(authorizerAppid, "AT1223");
		List<String> keywords = new LinkedList<>();
		keywords.add(wxuser.getNikeName());
		keywords.add(jsonObject.getString("integralDue"));
		keywords.add(jsonObject.getString("endTime"));
		keywords.add("可用积分将于" + jsonObject.getString("endTime") + "到期清零。");
		WxuserFormId wxuserFormId = tWxuserFormIdMapper.selectByOpenId(wxuser.getOpenId()).get(0);
		wxServiceUtils.platSendMiniProgramTemplateMessage(keywords, authorizerAppid, templateId, wxuser.getOpenId(),
				wxuserFormId.getFormValue(), templateId);
		return wxuserFormId;
	}

	private WxuserFormId beRechargedSuccessfully(Integer rechargeRecordId, String page) {
		MemberRechargeRecord record = memberRechargeRecordService.findById(rechargeRecordId);
		Member member = memberService.findById(record.getMemberId());
		String authorizerAppid = member.getAppmodelId().substring(9, member.getAppmodelId().length());
		String templateId = getTemplateId(authorizerAppid, "AT0016");
		Wxuser wxuser = wxuserService.findById(member.getWxuserId());
		WxuserFormId wxuserFormId = tWxuserFormIdMapper.selectByOpenId(wxuser.getOpenId()).get(0);
		List<String> keywords = new LinkedList<>();
		keywords.add(record.getPrice().toString());
		keywords.add(member.getPhone());
		String givePrice = "0";
		if (record.getActiveRechargeId() != null && record.getActiveRechargeId() > 0) {
			MemberActiveRecharge activeRecharge = activeRechargeService.findById(record.getActiveRechargeId());
			if (record.getPrice() > activeRecharge.getMaxPrice()) {
				givePrice = activeRecharge.getSendPrice().toString();
			}
		}
		keywords.add(givePrice);
		keywords.add(record.getPrice().toString());
		keywords.add(member.getSupplyBonus().toString());
		wxServiceUtils.platSendMiniProgramTemplateMessage(keywords, authorizerAppid, templateId, wxuser.getOpenId(),
				wxuserFormId.getFormValue(), templateId);
		return wxuserFormId;
	}

	private WxuserFormId updateNotification(Long memberId, Integer oldRankId, Integer newRankId, String page) {
		Member member = memberService.findById(memberId);
		String authorizerAppid = member.getAppmodelId().substring(9, member.getAppmodelId().length());
		String templateId = getTemplateId(authorizerAppid, "AT0557");
		MemberRank memberRankOld = memberRankService.findById(oldRankId);
		MemberRank memberRankNew = memberRankService.findById(newRankId);
		Wxuser wxuser = wxuserService.findById(member.getWxuserId());
		WxuserFormId wxuserFormId = tWxuserFormIdMapper.selectByOpenId(wxuser.getOpenId()).get(0);
		List<String> keywords = new LinkedList<>();
		keywords.add(memberRankOld.getRankName());
		keywords.add(memberRankNew.getRankName());
		keywords.add(member.getSupplyBonus().toString());
		keywords.add("会员服务规则");
		wxServiceUtils.platSendMiniProgramTemplateMessage(keywords, authorizerAppid, templateId, wxuser.getOpenId(),
				wxuserFormId.getFormValue(),  page);
		return wxuserFormId;
	}

	private void refundSuccess(String formId, String openId, OrderRefound orderRefound, String templateId,
			String authorizerAppid, String page) {
		Order order = orderService.findById(orderRefound.getOrderId());
		LogisticCancel logisticCancel = logisticCancelService.selectDefultTrue(orderRefound.getAppmodelId());
		List<String> keywords = new LinkedList<>();
		keywords.add(order.getOutTradeNo());
		keywords.add(logisticCancel.getUserName());
		keywords.add(orderRefound.getRefoundFee().toString());
		keywords.add("退回到零钱");
		keywords.add("退款已经原路返回");
		wxServiceUtils.platSendMiniProgramTemplateMessage(keywords, authorizerAppid, templateId, openId, formId,
				templateId);
	}

	private void agreeToReturn(String formId, String openId, OrderRefound orderRefound, String templateId,
			String authorizerAppid, String page) {
		LogisticCancel logisticCancel = logisticCancelService.selectDefultTrue(orderRefound.getAppmodelId());

		List<String> address = JSON
				.parseArray(JSON.parseObject(logisticCancel.getLocationJson()).getString("address"), String.class);
		StringBuilder sb = new StringBuilder();
		address.forEach(obj -> sb.append(obj));
		List<String> keywords = new LinkedList<>();
		keywords.add(logisticCancel.getUserName());
		keywords.add(logisticCancel.getPhone());
		keywords.add(sb.toString());
		keywords.add("温馨提示");
		wxServiceUtils.platSendMiniProgramTemplateMessage(keywords, authorizerAppid, templateId, openId, formId,
				templateId);
	}

	private void refusalOfRefund(String formId, String openId, OrderRefound orderRefound, String templateId,
			String authorizerAppid, String page) {
		String productName = orderDetailService.findById(orderRefound.getOrderDetailId()).getProductName();
		Order order = orderService.findById(orderRefound.getOrderId());
		List<String> keywords = new LinkedList<>();
		keywords.add(String.valueOf(orderRefound.getRefoundFee()));
		keywords.add(productName);
		keywords.add(order.getOutTradeNo());
		keywords.add("温馨提示");
		wxServiceUtils.platSendMiniProgramTemplateMessage(keywords, authorizerAppid, templateId, openId, formId,
				templateId);
	}

	private void orderClose(String formId, String openId, Order order, String templateId, String authorizerAppid, String page) {
		List<String> keywords = new LinkedList<>();
		keywords.add(order.getOutTradeNo());
		keywords.add(productName(order.getOrderId()));
		keywords.add(String.valueOf(order.getPayFee()));
		keywords.add(order.getCreateTime());
		keywords.add("订单关闭");
		wxServiceUtils.platSendMiniProgramTemplateMessage(keywords, authorizerAppid, templateId, openId, formId,
				page);
	}

	/** 订单改价通知 */
	private void orderChangePrice(String formId, String openId, Order order, String templateId,
			String authorizerAppid, String page) {
		List<String> keywords = new LinkedList<>();
		keywords.add(order.getOutTradeNo());
		keywords.add(String.valueOf(order.getOrderId()));
		keywords.add(String.valueOf(order.getPayFee()));
		keywords.add(String.valueOf(order.getPayFee()));
		wxServiceUtils.platSendMiniProgramTemplateMessage(keywords, authorizerAppid, templateId, openId, formId,
				page);
	}

	private String getTemplateId(String authorizerAppid, String titleId) {
		Map<String, Object> param = new HashMap<>(2);
		param.put("authorizerAppid", authorizerAppid);
		param.put("titleId", titleId);
		return HttpRequest.get(Url.GET_TEMPLATID_URL, param, false).body();
	}

	/**
	 * AT0693 物流配送消息通知
	 *  @param formId
	 * @param openId
	 * @param templateId
	 * @param page
	 */
	private void OrderLogisticsDispatching(String formId, String openId, Order order, String templateId,
			String authorizerAppid, String page) {
		JSONObject jsonObject = JSONObject.parseObject(order.getUserAddress());
		String user = jsonObject.getString("user");
		String tel = jsonObject.getString("tel");
		String address = jsonObject.getString("string");
		String productName = productName(order.getOrderId());
		List<String> keywords = new LinkedList<>();
		keywords.add(order.getOutTradeNo());
		keywords.add(productName);
		keywords.add(order.getOutTradeNo());
		keywords.add(user);
		keywords.add(tel);
		keywords.add(address);
		keywords.add(order.getWlName());
		keywords.add(order.getWlNum());
		wxServiceUtils.platSendMiniProgramTemplateMessage(keywords, templateId, openId, formId, authorizerAppid,page);
	}

	/** 商家配送消息通知 */
	private void MerchantDispatching(String formId, String openId, Order order, String templateId,
			String authorizerAppid, String page) {
		// 模板消息id
		JSONObject jsonObject = JSONObject.parseObject(order.getDeliveryStaff());
		String user = jsonObject.getString("Distributor");
		String tel = jsonObject.getString("phone");
		JSONObject jsonObjectTwo = JSONObject.parseObject(order.getUserAddress());
		String address = jsonObjectTwo.getString("string");
		String productName = productName(order.getOrderId());
		List<String> keywords = new LinkedList<>();
		keywords.add(order.getOutTradeNo());
		keywords.add(productName);
		keywords.add(address);
		keywords.add(user);
		keywords.add(tel);
		keywords.add("温馨提示");
		wxServiceUtils.platSendMiniProgramTemplateMessage(keywords, authorizerAppid, templateId, openId, formId,
				page);
	}

	/**
	 * 获取商品名称
	 *
	 * @param orderId
	 * @return
	 */
	private String productName(Long orderId) {
		StringBuffer productNameSb = new StringBuffer();
		List<OrderDetail> orderDetails = orderDetailService.selectByOrderId(orderId);
		if (orderDetails.size() > 0) {
			for (int i = 0; i < orderDetails.size(); i++) {
				productNameSb = productNameSb.append(orderDetails.get(i).getProductName() + ",");
			}
		}
		return productNameSb.substring(0, productNameSb.length() - 1);
	}
}
