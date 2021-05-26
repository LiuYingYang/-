package com.medusa.basemall.activemq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.agent.dao.AgentPurchaseOrderMapper;
import com.medusa.basemall.agent.entity.AgentPurchaseOrder;
import com.medusa.basemall.constant.ActiviMqQueueName;
import com.medusa.basemall.constant.AgentOrderPayFlag;
import com.medusa.basemall.jobhandler.ActiveDelaySendJobHandler;
import com.medusa.basemall.utils.TimeUtil;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class AgentOrderListerner {

	@Resource
	private AgentPurchaseOrderMapper tAgentPurchaseOrderMapper;
	@Resource
	private ActiveDelaySendJobHandler activeDelaySendJobHandler;

	/**
	 * 生成订单48小时内未支付,则关闭订单
	 * purchaseOrderId
	 */
	@JmsListener(destination = ActiviMqQueueName.AGENT_PURCHASE_ORDER)
	public void deliveryRefundOrder(String jsonData) {
		JSONObject jsonObject = JSON.parseObject(jsonData);
		AgentPurchaseOrder purchaseOrder = tAgentPurchaseOrderMapper.selectByPrimaryKey(jsonObject.getLong("id"));
		if (purchaseOrder.getPayFlag().equals(AgentOrderPayFlag.WAIT_PAY)) {
			String nowTime = TimeUtil.getNowTime();
			purchaseOrder.setCloseTime(nowTime);
			purchaseOrder.setPayFlag(AgentOrderPayFlag.USER_OVER_CLOASE);
			tAgentPurchaseOrderMapper.updateByPrimaryKeySelective(purchaseOrder);
		}
		activeDelaySendJobHandler.updateState(jsonObject.getString("activeMqTaskTOId"));
	}

	/**
	 * 发货之后15天内未确认收货则自动确认货
	 *  purchaseOrderId
	 */
	@JmsListener(destination = ActiviMqQueueName.AGENT_PURCHASE_ORDER_CONFIRM_RECEIPT)
	public void AgentPurchaseOrderConfirmReceipt(String jsonData) {
		JSONObject jsonObject = JSON.parseObject(jsonData);
		AgentPurchaseOrder purchaseOrder = tAgentPurchaseOrderMapper.selectByPrimaryKey(jsonObject.getLong("id"));
		if (purchaseOrder.getPayFlag().equals(AgentOrderPayFlag.DELIVERY)) {
			String nowTime = TimeUtil.getNowTime();
			purchaseOrder.setRecordTime(nowTime);
			purchaseOrder.setUpdateTime(nowTime);
			purchaseOrder.setPayFlag(AgentOrderPayFlag.USER_OVER_CLOASE);
			tAgentPurchaseOrderMapper.updateByPrimaryKeySelective(purchaseOrder);
		}
		activeDelaySendJobHandler.updateState(jsonObject.getString("activeMqTaskTOId"));
	}
}
