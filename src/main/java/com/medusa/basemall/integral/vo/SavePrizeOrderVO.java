package com.medusa.basemall.integral.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SavePrizeOrderVO {
	@ApiModelProperty(value = "商家appmodelId", required = true)
	private String appmodelId;
	@ApiModelProperty(value = "用户id", required = true)
	private Long wxuserId;
	@ApiModelProperty(value = "积分商品id", required = true)
	private Integer prizeId;
	@ApiModelProperty(value = "订单类型 1商品订单,2优惠券订单", required = true)
	private Integer orderType;
	@ApiModelProperty(value = "买家昵称", required = true)
	private String wxuserName;
	@ApiModelProperty(value = "数量", required = true)
	private Integer sum;
	@ApiModelProperty(value = "付款积分", required = true)
	private Integer expendIntegral;
	@ApiModelProperty(value = "收货人(订单类型为商品时传)")
	private String name;
	@ApiModelProperty(value = "收货地址(订单类型为商品时传)")
	private String address;
	@ApiModelProperty(value = "邮费(订单类型为商品时传)")
	private Double wlPrice;
	@ApiModelProperty(value = "支付方式 type 1-微信支付 2-余额支付")
	private Integer type;
	@ApiModelProperty(value = "备注(订单类型为商品时传)")
	private String remark;
	@ApiModelProperty(value = "联系电话(订单类型为商品时传)")
	private String telPhone;
	@ApiModelProperty(value = "配送方式(商家配送，物流配送)(订单类型为商品时传)", required = true)
	private String distriMode;
}
