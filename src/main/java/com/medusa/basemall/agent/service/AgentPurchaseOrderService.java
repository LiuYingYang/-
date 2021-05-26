package com.medusa.basemall.agent.service;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.agent.entity.AgentPurchaseOrder;
import com.medusa.basemall.agent.vo.*;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 * @author medusa
 * @date 2018/06/02
 */
public interface AgentPurchaseOrderService extends Service<AgentPurchaseOrder> {

	Result saveOrder(PayAgentOrderVo saveOrderVo);

	String notify(String xmlResult);

	Result paymentOrder(PaymentAgentOrderVo payAgentOrderVo, HttpServletRequest request);

	List<MiniOrderListVo> miniList(Long wxuserId, Integer payFlag, String shopName);

	MiniOrderListVo miniListDetail(Long purchaseOrderId);

	List<BackstageOrderListVo> backstageList(Integer payFlag, String appmodelId, String shopName, String nikeName, String user, String phone, String outTrade_no, String wlNum,
			String payTime);

	Result deliverGoods(List<AgentDeliverGoods> deliverGoods);

	Result closeOrder(String purchaseOrderIds, Integer operatiPerson);

	Result batchBackRemrk(JSONObject jsonObject);

	void updateOrderAddres(Long purchaseOrderId, String addres);

	BackstageOrderListVo backstageDetail(Long purchaseOrderId);
}
