package com.medusa.basemall.activemq;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.constant.ActiviMqQueueName;
import com.medusa.basemall.messages.entity.Messages;
import com.medusa.basemall.messages.service.MessagesService;
import com.medusa.basemall.order.entity.Order;
import com.medusa.basemall.order.service.OrderService;
import com.medusa.basemall.promotion.service.GroupService;
import com.medusa.basemall.shop.entity.Manager;
import com.medusa.basemall.shop.service.ManagerService;
import com.medusa.basemall.user.entity.Wxuser;
import com.medusa.basemall.user.service.WxuserService;
import com.medusa.basemall.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author whh
 */
@Component
public class MessageListerner {

	@Autowired
	private OrderService orderService;

	@Autowired
	private WxuserService wxuserService;

	private GroupService groupService;

	@Autowired
	private MessagesService messagesService;
	@Autowired
	private ManagerService managerService;

	@JmsListener(destination = ActiviMqQueueName.MESSAGE_NOTIFY)
	public void addMessage(String jsonData) {
		JSONObject jsonObject = JSONObject.parseObject(jsonData);
		Messages messages = new Messages();
		messages.setModule(jsonObject.getInteger("module"));
		messages.setType(jsonObject.getInteger("type"));
		messages.setAppmodeId(jsonObject.getString("appmodelId"));
		messages.setCreateTime(TimeUtil.getNowDate());
		messages.setReadOrNot(false);
		switch (messages.getModule()) {
			//1订单 2营销 3售后 4系统 5平台
			case 1:
				orderMessage(jsonObject, messages);

				break;
			case 2:
				marketingActivityMessage(jsonObject, messages);
				break;
			case 3:
				RefundMessage(jsonObject, messages);
				break;
			case 4:
				//新版版通知需要发送给所有商家
				systemMessage(jsonObject, messages);
				messagesService.sava(messages);
				return;
			case 5:
				platfromMessage(jsonObject, messages);
				break;
			default:
				break;
		}
		messagesService.sava(messages);

	}

	private void platfromMessage(JSONObject jsonObject, Messages messages) {

	}

	private void RefundMessage(JSONObject jsonObject, Messages messages) {
		Long wxuserId = jsonObject.getLong("wxuserId");
		Wxuser wxuser = wxuserService.findById(wxuserId);
		//售后:1申请 2退款超时 3修改退款
		switch (messages.getType()) {
			case 1:
				messages.setMessageHead(
						"售后申请通知: 用户 \"NICENAME\" 发起售后申请(售后订单编号: ORDERID)请".replace("NICENAME", wxuser.getNikeName())
								.replace("ORDERID", jsonObject.getString("orderRefundId")));
				break;
			case 2:
				messages.setMessageHead(
						"售后超时通知: 用户 \"NICENAME\" 申请的售后退款即将超时请".replace("NICENAME", wxuser.getNikeName()));
				break;
			case 3:
				messages.setMessageHead("退款修改通知: 用户 \"NICENAME\" 修改了退款申请请前往".replace("NICENAME", wxuser.getNikeName()));
				break;
			default:
				break;
		}
	}

	private void marketingActivityMessage(JSONObject jsonObject, Messages messages) {
		Long wxuserId = jsonObject.getLong("wxuserId");
		Wxuser wxuser = wxuserService.findById(wxuserId);
		//1拼团 2会员 3代理申请 4代理绑定
		switch (messages.getType()) {
			case 1:
				//todo 未写
				Integer groupId = jsonObject.getInteger("groupId");
				//Group group = groupService.findByGroupId(groupId);
				messages.setMessageHead(
						"拼团活动通知: 团长 \"NICENAME\" 所在团拼团成功(团编号:ORDERNUMBER)前往".replace("NICENAME", wxuser.getNikeName())
								.replace("ORDERNUMBER", groupId.toString()));
				break;
			case 2:
				messages.setMessageHead("注册会员通知: 用户 \"NICENAME\" 成功注册会员请前往".replace("NICENAME", wxuser.getNikeName()));
				break;
			case 3:
				messages.setMessageHead("代理申请通知: 用户 \"NICENAME\" 申请成为代理商请".replace("NICENAME", wxuser.getNikeName()));
				break;
			case 4:
				messages.setMessageHead(
						"代理绑定成功通知: 用户 \"NICENAME\" 已成功绑定为代理商请前往".replace("NICENAME", wxuser.getNikeName()));
				break;
			default:
				break;
		}
	}

	private void systemMessage(JSONObject jsonObject, Messages messages) {
		//1续费提醒 2购买 3开通 4审核结果6绑定子账号 7禁用子账号 8解禁子账号
		Manager manager = managerService.findById(jsonObject.getInteger("managerId"));
		switch (messages.getType()) {
			case 1:
				//todo 未写
				messages.setMessageHead("标准商城VERSION小程序还有DAY即将到期".
						replace("VERSION", jsonObject.getString("version"))
						.replace("DAY", jsonObject.getString("DAY")));
				break;
			case 2:
				messages.setMessageHead("您已成功购买标准商城VERSION小程序模板".replace("VERSION", getVersion(manager)));
				break;
			case 3:
				messages.setMessageHead("您已成功开通标准商城VERSION小程序".replace("VERSION", getVersion(manager)));
				break;
			//4审核结果不需要
			case 4:
				break;
			//5提醒更新
			case 5:
				List<Manager> all = managerService.findAll();
				for (Manager m : all) {
					String version = getVersion(m);
					messages.setMessageHead("标准商城VERSION小程序有新版本(VERSIONNAME)发布请".replace("VERSION", version)
							.replace("VERSIONNAME", jsonObject.getString("versionName")));
					messages.setAppmodeId(m.getAppmodelId());
					messagesService.sava(messages);
				}
				break;
			case 6:
				messages.setMessageHead("NAME已成功绑定成为子账号管理员微信号".replace("NAME", jsonObject.getString("name")));
			case 7:
				messages.setMessageHead("NAME已被禁用子账号管理".replace("NAME", jsonObject.getString("name")));
				break;
			case 8:
				messages.setMessageHead("NAME已成功解除禁用子账号管理员".replace("NAME", jsonObject.getString("name")));
				break;
			default:
				break;
		}

	}

	private String getVersion(Manager manager) {
		switch (manager.getVersion()) {
			case 1:
				return "标准版";
			case 2:
				return "营销版";
			case 3:
				return "旗舰版";
			default:
				return "";
		}
	}

	/**
	 * type 1付款 2改价 3提醒发货 4订单收获 5订单关闭
	 * @param jsonObject
	 * @param messages
	 */
	private void orderMessage(JSONObject jsonObject, Messages messages) {
		Order order = orderService.findById(jsonObject.getLong("orderId"));
		Wxuser wxuser = wxuserService.findById(order.getWxuserId());
		switch (messages.getType()) {
			case 1:
				messages.setMessageHead(
						"付款通知: 用户 \"NIKENAME\"已下单付款(订单号号ORDERID)请前往".replace("ORDERID", order.getOutTradeNo())
								.replace("NIKENAME", wxuser.getNikeName()));
				break;
			case 2:
				messages.setMessageHead(
						"价格改价通知: 您已成功修改(订单号ORDERID)商品价格为￥PRICE请前往".replace("ORDERID", order.getOutTradeNo())
								.replace("PRICE", order.getPayFee().toString()));
				break;
			case 3:
				messages.setMessageHead(
						"提醒发货通知: 用户 \"USERNAME\" 发起发货提醒(订单号ORDERID)请".replace("USERNAME", wxuser.getNikeName())
								.replace("ORDERID", order.getOutTradeNo()));
				break;
			case 4:
				messages.setMessageHead(
						"收货通知: 用户 \"NIKENAME\" 已确认收货(订单号ORDERID)请前往".replace("NIKENAME", wxuser.getNikeName())
								.replace("ORDERID", order.getOutTradeNo()));
				break;
			case 5:
				messages.setMessageHead("订单关闭通知: 订单号(ORDERID)已关闭请前往".replace("ORDERID", order.getOutTradeNo()));
				break;
			default:
				break;
		}
	}


}
