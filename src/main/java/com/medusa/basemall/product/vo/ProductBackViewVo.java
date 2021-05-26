package com.medusa.basemall.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品信息，用在PC后端查询所有商品时
 */
@Data
public class ProductBackViewVo extends ProductWxViewVo  {

	@ApiModelProperty(value = "商品分类")
	private List<CategoryProductVo> categoryList;

	@ApiModelProperty(value = "商品所属首页模块")
	private List<PlateVo> plateVos;

	@ApiModelProperty(value = "限购")
	private Integer maxQuantity;


}
