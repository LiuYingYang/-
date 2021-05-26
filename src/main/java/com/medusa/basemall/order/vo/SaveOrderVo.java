package com.medusa.basemall.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author whh
 */
@Data
public class SaveOrderVo {


	@ApiModelProperty(value = "商品信息")
	private List<ProductOrderVo> productList;

	@ApiModelProperty(value = "模板消息发送用的formId")
	private String formIds;

	@ApiModelProperty(value = "订单信息")
	private OrderInfo orderInfo;

	@ApiModelProperty(value = "团Id")
	private Integer groupId;

}
