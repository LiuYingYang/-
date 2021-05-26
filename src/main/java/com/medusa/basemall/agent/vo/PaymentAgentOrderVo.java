package com.medusa.basemall.agent.vo;

import io.swagger.annotations.ApiModelProperty;

public class PaymentAgentOrderVo {

	@ApiModelProperty(value = "代理订单id")
	private Long purchaseOrderId;

	@ApiModelProperty(value = "微信openId")
	private String openId;

	@ApiModelProperty(value = "支付方式  1-微信支付,2-余额支付")
	private Integer payType;


	public Long getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(Long purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}
}
