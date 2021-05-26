package com.medusa.basemall.product.vo;

import com.medusa.basemall.product.entity.ProductSpec;
import com.medusa.basemall.product.entity.ProductSpecClass;
import com.medusa.basemall.product.entity.ProductSpecItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品展示信息，小程序端
 */
@Data
public class ProductWxVo extends ProductSimpleVo {

	@ApiModelProperty(value = "商品正在参加的活动类型")
	private String productTypeList;

	private Double activityPrice;

	private Double activityDiscount;

	private Integer activityStock;

	private Integer maxQuantity;

	@ApiModelProperty(value = "活动商品表id")
	private Long activityProductId;


	/**
	 * 规格分类
	 */
	private List<ProductSpecClass> productSpecClassList;

	/**
	 * 规格
	 */
	private List<ProductSpec> productSpecList;

	/**
	 * 规格详情
	 */
	private List<ProductSpecItem> productSpecItemList;




}
