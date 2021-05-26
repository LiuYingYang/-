package com.medusa.basemall.agent.service;

import com.medusa.basemall.agent.entity.AgentPurchaseOrder;
import com.medusa.basemall.agent.entity.AgentPurchaseOrderDetailed;
import com.medusa.basemall.agent.vo.AgentProductOrderVo;
import com.medusa.basemall.core.Service;

import java.util.List;

/**
 * Created by medusa on 2018/06/02.
 */
public interface AgentPurchaseOrderDetailedService extends Service<AgentPurchaseOrderDetailed> {

	void saveOrderDetail(List<AgentProductOrderVo> productList, AgentPurchaseOrder order);
}
