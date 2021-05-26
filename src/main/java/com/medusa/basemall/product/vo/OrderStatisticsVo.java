package com.medusa.basemall.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderStatisticsVo {

	@ApiModelProperty(value = "代付款订单")
	private Integer payment;
	@ApiModelProperty(value = "待发货订单")
	private Integer waitSendProduct;
	@ApiModelProperty(value = "待收货订单")
	private Integer waitCollectProduct;
	@ApiModelProperty(value = "已完成订单")
	private Integer changeHands;
	@ApiModelProperty(value = "售后订单")
	private Integer afterSale;

}
