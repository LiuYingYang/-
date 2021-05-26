package com.medusa.basemall.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品分类信息，用在PC后端查询所有商品时（findProductForBack），商品的分类信息
 * @author whh
 */
@Data
public class CategoryProductVo {


	@ApiModelProperty(value = "分类id")
    private Long categoryId;

	@ApiModelProperty(value = "分类名称")
    private String categoryName;

	@ApiModelProperty(value = "商品id",hidden = true)
	private Long productId;

	@ApiModelProperty(value = "父类id",hidden = true)
	private Long fatherId;

	@ApiModelProperty(value = "分类子分类")
    private List<CategoryProductVo> subCategoryList;


}
