package com.medusa.basemall.integral.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Created by wx on 2018/06/06.
 */
@Table(name = "t_prize_order")
@Data
public class PrizeOrder {

	@Id
	@Column(name = "prize_order_id")
	@ApiModelProperty(value = "订单编号(新增时不传,其余操作时传)")
	private Long prizeOrderId;

	@Column(name = "change_num")
	@ApiModelProperty(value = "兑换单号(不需要)")
	private String changeNum;

	@Column(name = "wxuser_id")
	@ApiModelProperty(value = "用户id", required = true)
	private Long wxuserId;

	@Column(name = "pay_type")
	@ApiModelProperty(value = "支付方式   1-微信支付  2-余额支付")
	private Integer payType;

	@Column(name = "prize_id")
	@ApiModelProperty(value = "积分商品id", required = true)
	private Integer prizeId;

	@ApiModelProperty(value = "实际价格(订单类型为商品时传)")
	private Double price;

	@ApiModelProperty(value = "数量", hidden = true)
	private Integer sum;

	@Column(name = "expend_integral")
	@ApiModelProperty(value = "付款积分", required = true)
	private Integer expendIntegral;

	@ApiModelProperty(value = "收货人(订单类型为商品时传)", required = true)
	private String name;

	@ApiModelProperty(value = "收货地址(订单类型为商品时传)", required = true)
	private String address;

	@Column(name = "wl_num")
	@ApiModelProperty(value = "物流单号(发货时传)")
	private String wlNum;

	@Column(name = "wl_code")
	@ApiModelProperty(value = "物流公司代码(发货时传)")
	private String wlCode;

	@Column(name = "wl_price")
	@ApiModelProperty(value = "邮费(订单类型为商品时传)")
	private Double wlPrice;

	@Column(name = "wl_name")
	@ApiModelProperty(value = "物流公司名称")
	private String wlName;

	@Column(name = "distri_mode")
	@ApiModelProperty(value = "配送方式(商家配送，物流配送)(订单类型为商品时传)", required = true)
	private String distriMode;

	@ApiModelProperty(value = "备注(订单类型为商品时传)")
	private String remark;

	@Column(name = "back_remark")
	@ApiModelProperty(value = "商家备注")
	private String backRemark;

	@Column(name = "order_state")
	@ApiModelProperty(value = "订单状态(0等待买家付款,1买家已付款,2卖家已发货,3交易成功)(不需要)")
	private Integer orderState;

	@Column(name = "pay_time")
	@ApiModelProperty(value = "支付时间(不需要)")
	private String payTime;

	@Column(name = "send_time")
	@ApiModelProperty(value = "发货时间(不需要)")
	private String sendTime;

	@Column(name = "record_time")
	@ApiModelProperty(value = "发货时间(不需要)", hidden = true)
	private String recordTime;

	@Column(name = "refound_time")
	@ApiModelProperty(value = "退款时间(不需要)", hidden = true)
	private String refoundTime;

	@Column(name = "delete_state")
	@ApiModelProperty(value = "删除状态0-删除,1-正常(不需要)", hidden = true)
	private Boolean deleteState;

	@Column(name = "create_time")
	@ApiModelProperty(value = "退款时间(不需要)")
	private String createTime;

	@Column(name = "update_time")
	@ApiModelProperty(value = "更新时间(不需要)")
	private String updateTime;

	@Column(name = "appmodel_id")
	@ApiModelProperty(value = "模板id", required = true)
	private String appmodelId;

	@Column(name = "pay_fee")
	@ApiModelProperty(value = "支付费用(订单类型为商品时传)")
	private Double payFee;

	@Column(name = "order_type")
	@ApiModelProperty(value = "订单类型 1商品订单,2优惠券订单", required = true)
	private Integer orderType;

	@Column(name = "tel_phone")
	@ApiModelProperty(value = "联系电话(订单类型为商品时传)", required = true)
	private String telPhone;

	@Column(name = "delivery_staff")
	@ApiModelProperty(value = "配送信息(订单类型为商品时传)")
	private String deliveryStaff;

	@Column(name = "wxuser_name")
	@ApiModelProperty(value = "买家昵称", required = true)
	private String wxuserName;

}