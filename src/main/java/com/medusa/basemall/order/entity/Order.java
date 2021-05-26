package com.medusa.basemall.order.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "t_order")
@Data
public class Order {
	/**
	 * 编号
	 */
	@Id
	@Column(name = "order_id")
	@ApiModelProperty(value = "订单id")
	private Long orderId;

	@Column(name = "tel_phone")
	@ApiModelProperty(value = "联系电话")
	private String telPhone;

	@Column(name = "pay_flag")
	@ApiModelProperty(value = "支付状态", notes = "0等待买家付款,1买家已付款 ,2卖家已发货,3交易成功,4买家取消付款,5订单超时订单关闭,6商家操作订单关闭,7售后全部退款订单关闭")
	private Integer payFlag;

	@Column(name = "out_trade_no")
	@ApiModelProperty(value = "订单号")
	private String outTradeNo;


	@Column(name = "pay_fee")
	@ApiModelProperty(value = "支付价")
	private BigDecimal payFee;

	@Column(name = "total_fee")
	@ApiModelProperty(value = "总价")
	private BigDecimal totalFee;

	@Column(name = "wxuser_id")
	@ApiModelProperty(value = "用户ID")
	private Long wxuserId;

	@Column(name = "create_time")
	@ApiModelProperty(value = "创建时间")
	private String createTime;

	@Column(name = "pay_time")
	@ApiModelProperty(value = "支付时间")
	private String payTime;

	@Column(name = "send_time")
	@ApiModelProperty(value = "发货时间")
	private String sendTime;

	@Column(name = "record_time")
	@ApiModelProperty(value = "收货时间")
	private String recordTime;

	@ApiModelProperty(value = "备注")
	private String remark;

	@Column(name = "wl_name")
	@ApiModelProperty(value = "物流公司名称")
	private String wlName;

	@Column(name = "wl_num")
	@ApiModelProperty(value = "物流单号")
	private String wlNum;

	@Column(name = "wl_code")
	@ApiModelProperty(value = "物流公司代码")
	private String wlCode;

	@Column(name = "user_address")
	@ApiModelProperty(value = "收货地址")
	private String userAddress;

	@Column(name = "user_name")
	@ApiModelProperty(value = "收货人")
	private String userName;

	@Column(name = "wl_price")
	@ApiModelProperty(value = "邮费")
	private BigDecimal wlPrice;

	@Column(name = "out_trade_no_ext")
	@ApiModelProperty(value = "再次支付订单号")
	private String outTradeNoExt;

	@Column(name = "order_type")
	@ApiModelProperty(value = "订单类型")
	private String orderType;

	@Column(name = "activity_id")
	@ApiModelProperty(value = "活动id  只有秒杀/拼团/特价订单才会有")
	private Integer activityId;

	@Column(name = "back_remark")
	@ApiModelProperty(value = "商家备注")
	private String backRemark;

	@Column(name = "delete_state")
	@ApiModelProperty(value = "删除标识 ", notes = "0正常  1逻辑删除", dataType = "boolean")
	private Boolean deleteState;

	@Column(name = "appmodel_id")
	@ApiModelProperty(value = "模板ID")
	private String appmodelId;


	@Column(name = "update_time")
	@ApiModelProperty(value = "更新时间" )
	private Date updateTime;


	@Column(name = "distribute_mode")
	@ApiModelProperty(value = "配送方式", notes = "商家配送，物流配送，到店自取")
	private String distributeMode;

	@Column(name = "delivery_staff")
	@ApiModelProperty(value = "配送信息")
	private String deliveryStaff;


	@Column(name = "group_state")
	@ApiModelProperty(value = "成团状态")
	private Integer groupState;


	@Column(name = "group_member_id")
	@ApiModelProperty(value = "团成员ID")
	private Integer groupMemberId;


	@Column(name = "factPay_wxuser_id")
	@ApiModelProperty(value = "实际支付人id")
	private Long factpayWxuserId;



	@Column(name = "pay_type")
	@ApiModelProperty(value = "支付方式", notes = "微信支付,余额支付,好友代付")
	private String payType;

	@Column(name = "member_discount")
	@ApiModelProperty(value = "会员折扣")
	private Double memberDiscount;

	@Column(name = "wxuser_coupon_id")
	@ApiModelProperty(value = "用户优惠券id")
	private Integer wxuserCouponId;


}