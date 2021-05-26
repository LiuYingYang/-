package com.medusa.basemall.order.vo;

import io.swagger.annotations.ApiModelProperty;

public class OrderRefundVo {
	@ApiModelProperty(value = "订单id"  )
	private Long orderId;
	@ApiModelProperty(value = "订单详情id"  )
	private Long orderDetailId;
	@ApiModelProperty(value = "用户ID"  )
	private Long wxuserId;
	@ApiModelProperty(value = "退款原因"  )
	private String reason;
	@ApiModelProperty(value = "退款备注"  )
	private String remark;
	@ApiModelProperty(value = "退款金额" )
	private Double refoundFee;
	@ApiModelProperty(value = "货物状态"  )
	private String productState;
	@ApiModelProperty(value = "退款类型 0--只退款，1--退货退款" )
	private Integer refoundType;
	@ApiModelProperty(value = "模板ID"  )
	private String appmodelId;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(Long orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public Long getWxuserId() {
		return wxuserId;
	}

	public void setWxuserId(Long wxuserId) {
		this.wxuserId = wxuserId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getRefoundFee() {
		return refoundFee;
	}

	public void setRefoundFee(Double refoundFee) {
		this.refoundFee = refoundFee;
	}

	public String getProductState() {
		return productState;
	}

	public void setProductState(String productState) {
		this.productState = productState;
	}

	public Integer getRefoundType() {
		return refoundType;
	}

	public void setRefoundType(Integer refoundType) {
		this.refoundType = refoundType;
	}

	public String getAppmodelId() {
		return appmodelId;
	}

	public void setAppmodelId(String appmodelId) {
		this.appmodelId = appmodelId;
	}
}
