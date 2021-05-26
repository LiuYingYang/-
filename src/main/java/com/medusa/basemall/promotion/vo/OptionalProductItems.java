package com.medusa.basemall.promotion.vo;

import com.medusa.basemall.product.entity.ProductSpecClass;
import com.medusa.basemall.product.entity.ProductSpecItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author whh
 */
@Data
public class OptionalProductItems {

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
	@ApiModelProperty(value = "规格项")
	private List<ProductSpecItem> productSpecItemList;
	@ApiModelProperty(value = "规格分类")
	private List<ProductSpecClass> productSpecClassList;
	@ApiModelProperty(value = "活动库存  统一规格商品查询时才有")
	private Integer activityStock;
	@ApiModelProperty(value = "限购")
	private Integer maxQuantity;
	@ApiModelProperty(value = "活动折扣")
	private Double activityDiscount;
	@ApiModelProperty(value = "是否显示在首页 0-不显示 1-显示")
	private Integer homeViewStat;
	@ApiModelProperty(value = "首页排序  数值越小越大 but required >= 0")
	private Integer sort;
}
