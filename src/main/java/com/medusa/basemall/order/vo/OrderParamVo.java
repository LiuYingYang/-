package com.medusa.basemall.order.vo;

import com.medusa.basemall.order.entity.Order;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * @author whh
 */
public class OrderParamVo extends Order {


	@ApiModelProperty(value = "退款原因"  )
	private String reason;

	@ApiModelProperty(value = "收货人手机号"  )
	private String phone;

	@ApiModelProperty(value = "退款订单id"  )
	private Long orderRefoundId;

	@ApiModelProperty(value = "验证码"  )
	private String smsCode;


	@ApiModelProperty(value = "拒绝原因"  )
	private String refuseReason;


	@ApiModelProperty(value = "货物状态"  )
	private String productState;

	@ApiModelProperty(value = "退款价格" )
	private BigDecimal refoundFee;


	public BigDecimal getRefoundFee() {
		return refoundFee;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setRefoundFee(BigDecimal refoundFee) {
		this.refoundFee = refoundFee;
	}

	public String getProductState() {
		return productState;
	}

	public void setProductState(String productState) {
		this.productState = productState;
	}


	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}


	public Long getOrderRefoundId() {
		return orderRefoundId;
	}

	public void setOrderRefoundId(Long orderRefoundId) {
		this.orderRefoundId = orderRefoundId;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


}
