package com.medusa.basemall.agent.vo;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class SaveAgentOrderVo {


	/**
	 * 支付价
	 */
	@ApiModelProperty(value = "支付价")
	private BigDecimal payFee;

	/**
	 * 总价
	 */
	@ApiModelProperty(value = "总价")
	private BigDecimal totalFee;

	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户ID")
	private Long wxuserId;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	/**
	 * 收货地址
	 */
	@ApiModelProperty(value = "收货地址")
	private String address;

	/**
	 * 邮费
	 */
	@ApiModelProperty(value = "邮费")
	private Long wlPrice;

	/**
	 * 配送方式
	 */
	@ApiModelProperty(value = "配送方式")
	private String distriMode;


	/**
	 * 配送信息
	 */
	@ApiModelProperty(value = "配送信息")
	private String deliveryStaff;

	/**
	 * 模板ID
	 */
	@ApiModelProperty(value = "模板ID")
	private String appmodelId;


	public BigDecimal getPayFee() {
		return payFee;
	}

	public void setPayFee(BigDecimal payFee) {
		this.payFee = payFee;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public Long getWxuserId() {
		return wxuserId;
	}

	public void setWxuserId(Long wxuserId) {
		this.wxuserId = wxuserId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getWlPrice() {
		return wlPrice;
	}

	public void setWlPrice(Long wlPrice) {
		this.wlPrice = wlPrice;
	}

	public String getDistriMode() {
		return distriMode;
	}

	public void setDistriMode(String distriMode) {
		this.distriMode = distriMode;
	}

	public String getDeliveryStaff() {
		return deliveryStaff;
	}

	public void setDeliveryStaff(String deliveryStaff) {
		this.deliveryStaff = deliveryStaff;
	}

	public String getAppmodelId() {
		return appmodelId;
	}

	public void setAppmodelId(String appmodelId) {
		this.appmodelId = appmodelId;
	}
}
