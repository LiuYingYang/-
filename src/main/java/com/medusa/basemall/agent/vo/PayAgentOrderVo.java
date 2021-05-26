package com.medusa.basemall.agent.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class PayAgentOrderVo {

	@ApiModelProperty(value = "订单信息")
	private SaveAgentOrderVo order;

	@ApiModelProperty(value = "商品信息")
	private List<AgentProductOrderVo> productList;


	public SaveAgentOrderVo getOrder() {
		return order;
	}

	public void setOrder(SaveAgentOrderVo order) {
		this.order = order;
	}

	public List<AgentProductOrderVo> getProductList() {
		return productList;
	}

	public void setProductList(List<AgentProductOrderVo> productList) {
		this.productList = productList;
	}
}
