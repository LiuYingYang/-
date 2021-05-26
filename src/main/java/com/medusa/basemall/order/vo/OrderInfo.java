package com.medusa.basemall.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;

@Data
public class OrderInfo {

	@ApiModelProperty(value = "联系电话")
	private String telPhone;

	@ApiModelProperty(value = "支付价")
	private BigDecimal payFee;

	@ApiModelProperty(value = "总价")
	private BigDecimal totalFee;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "收货地址")
	private String userAddress;

	@ApiModelProperty(value = "收货人")
	private String userName;

	@ApiModelProperty(value = "邮费")
	private BigDecimal wlPrice;

	@ApiModelProperty(value = "订单类型")
	private String orderType;

	@Column(name = "appmodel_id")
	@ApiModelProperty(value = "模板ID")
	private String appmodelId;

	@ApiModelProperty(value = "配送方式", notes = "商家配送，物流配送，到店自取")
	private String distributeMode;

	@Column(name = "delivery_staff")
	@ApiModelProperty(value = "配送信息")
	private String deliveryStaff;

	@ApiModelProperty(value = "实际支付人id")
	private Long factpayWxuserId;

	@ApiModelProperty(value = "用户id")
	private Long wxuserId;

	@ApiModelProperty(value = "使用的优惠券id")
	private Integer wxuserCouponId;

	@ApiModelProperty(value = "成团状态")
	private Integer groupState;

	@ApiModelProperty(value = "团成员ID")
	private Integer groupMemberId;

	@ApiModelProperty(value = "团Id")
	private Integer groupId;
}