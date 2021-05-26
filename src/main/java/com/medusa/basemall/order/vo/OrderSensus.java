package com.medusa.basemall.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author whh
 */
@Data
public class OrderSensus {

	@ApiModelProperty(value = "待发货订单数量")
	private Integer waitSendProduct;
	@ApiModelProperty(value = "退款中订单数量")
	private Integer refundIn;
	@ApiModelProperty(value = "团购订单数量")
	private Integer groupSum;
	@ApiModelProperty(value = "积分订单数量")
	private Integer integralSum;
	@ApiModelProperty(value = "代理采购订单数量")
	private Integer agentSum;
	@ApiModelProperty(value = "所有订单数量")
	private Integer totleOrderSum;

}
