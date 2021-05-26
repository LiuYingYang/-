package com.medusa.basemall.agent.dao;

import com.medusa.basemall.agent.entity.AgentPurchaseOrderDetailed;
import com.medusa.basemall.core.Mapper;

import java.util.List;

public interface AgentPurchaseOrderDetailedMapper extends Mapper<AgentPurchaseOrderDetailed> {

	List<AgentPurchaseOrderDetailed>  selectByOrderId(Long purchaseOrderId);
}