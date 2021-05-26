package com.medusa.basemall.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SpecItemUpdateVo {

	@ApiModelProperty(value ="商品规格项Id")
	private Long productSpecItemId;

	@ApiModelProperty(value ="商品id")
	private Long productId;

	@ApiModelProperty(value ="价格")
	private Double price;

	@ApiModelProperty(value ="库存")
	private Integer stock;


}
