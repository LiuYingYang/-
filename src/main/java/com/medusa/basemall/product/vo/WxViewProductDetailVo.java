package com.medusa.basemall.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WxViewProductDetailVo {

	@ApiModelProperty(value = "商品id")
	private Long productId;

	@ApiModelProperty(value = "用户收藏对象")
	private Long wxuserId;



}
