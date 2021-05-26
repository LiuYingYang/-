package com.medusa.basemall.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductInfoVo {
	@ApiModelProperty("商品id")
	private Long productId;
	@ApiModelProperty("购买的商品数量")
	private Integer num;
	@ApiModelProperty(required = false,hidden = true,value = "商品的体积或重量或重量,  计算方式根据运费模板的类型计算")
	private double productBulk;
	@ApiModelProperty(required = false,hidden = true,value = "邮费模板")
	private Integer logisticModelId;
}
