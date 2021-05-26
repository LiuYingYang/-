package com.medusa.basemall.order.vo;

import io.swagger.annotations.ApiModelProperty;

public class OrderWlVo {

	@ApiModelProperty(value = "退货订单ID", required = true  )
	private Long orderRefoundId;
	@ApiModelProperty(value = "订单详情ID", required = true  )
	private Long orderDetailId;
	@ApiModelProperty(value = "退款物流公司", required = true  )
	private String uWlName;
	@ApiModelProperty(value = "物流单号", required = true  )
	private String uWlNum;
	@ApiModelProperty(value = "联系电话", required = true  )
	private String uWlPhone;
	@ApiModelProperty(value = "退货说明"  )
	private String uWlRemark;
	@ApiModelProperty(value = "快递公司代码"  )
	private String uWlCode;
	@ApiModelProperty(value = "模板ID", required = true  )
	private String appmodelId;


	public String getuWlCode() {
		return uWlCode;
	}
	public void setuWlCode(String uWlCode) {
		this.uWlCode = uWlCode;
	}

	public Long getOrderRefoundId() {
		return orderRefoundId;
	}

	public void setOrderRefoundId(Long orderRefoundId) {
		this.orderRefoundId = orderRefoundId;
	}

	public Long getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(Long orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public String getuWlName() {
		return uWlName;
	}

	public void setuWlName(String uWlName) {
		this.uWlName = uWlName;
	}

	public String getuWlNum() {
		return uWlNum;
	}

	public void setuWlNum(String uWlNum) {
		this.uWlNum = uWlNum;
	}

	public String getuWlPhone() {
		return uWlPhone;
	}

	public void setuWlPhone(String uWlPhone) {
		this.uWlPhone = uWlPhone;
	}

	public String getuWlRemark() {
		return uWlRemark;
	}

	public void setuWlRemark(String uWlRemark) {
		this.uWlRemark = uWlRemark;
	}

	public String getAppmodelId() {
		return appmodelId;
	}

	public void setAppmodelId(String appmodelId) {
		this.appmodelId = appmodelId;
	}
}
