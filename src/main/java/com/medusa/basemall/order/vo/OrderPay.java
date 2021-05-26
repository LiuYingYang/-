package com.medusa.basemall.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @author whh
 */
@Data
public class OrderPay {

	@ApiModelProperty(value = "订单id")
	private Long orderId;

	@ApiModelProperty(value = "支付方式   101 微信支付  102 余额支付")
	private Integer payType;

	@ApiModelProperty(value = "实际支付人的用户id")
	private Long wxuserId;

}
