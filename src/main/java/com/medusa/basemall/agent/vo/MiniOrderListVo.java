package com.medusa.basemall.agent.vo;

import com.medusa.basemall.agent.entity.AgentPurchaseOrder;
import com.medusa.basemall.agent.entity.AgentPurchaseOrderDetailed;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class MiniOrderListVo extends AgentPurchaseOrder {

	@ApiModelProperty(value = "订单详情信息")
	private List<AgentPurchaseOrderDetailed> detaileds;

	public List<AgentPurchaseOrderDetailed> getDetaileds() {
		return detaileds;
	}

	public void setDetaileds(List<AgentPurchaseOrderDetailed> detaileds) {
		this.detaileds = detaileds;
	}
}
