package com.medusa.basemall.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商品的基本信息
 */
@Data
public class ProductSimpleVo {

	@ApiModelProperty(value = "商品id")
    private Long productId;
	@ApiModelProperty(value = "商品名称")
    private String productName;
	@ApiModelProperty(value = "备注")
    private String remark;
	@ApiModelProperty(value = "商品图片")
    private String productImg;
	@ApiModelProperty(value = "商品价格")
	private Double price;
	@ApiModelProperty(value = "市场价（划掉的价格）")
	private Double marketPrice;
	@ApiModelProperty(value = "商品库存")
    private Integer stock;
	@ApiModelProperty(value = "商品销量")
    private Integer salesVolume;
	@ApiModelProperty(value = "创建时间")
    private String createTime;
	@ApiModelProperty(value = "是否有规格  false带规格 true不带规格")
    private Boolean specType;
	@ApiModelProperty(value = "上下状态(默认上架，0--上架，1--下架（仓库中），2--已售完)")
    private Integer shelfState;
	@ApiModelProperty(value = "商家wxAppId")
    private String appmodelId;
   
}
