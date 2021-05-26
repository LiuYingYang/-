package com.medusa.basemall.user.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "collect")
public class Collect {


	/**
	 * 收藏id
	 */
	@Id
	@Field("collect_id")
	@ApiModelProperty(value = "收藏id")
	private String collectId;

	/**
	 * 用户id
	 */
	@Field("wxuser_id")
	@ApiModelProperty(value = "用户id")
	private Long wxuserId;

	/**
	 * 商品图片img
	 */
	@Field("img_url")
	@ApiModelProperty(value = "商品图片img")
	private String imgUrl;

	/**
	 * 商品id
	 */
	@Field("product_id")
	@ApiModelProperty(value = "商品id")
	private Long productId;

	/**
	 * 商品名
	 */
	@Field("product_name")
	@ApiModelProperty(value = "商品名")
	private String productName;


	/**
	 * 备注
	 */
	@Field("remark")
	@ApiModelProperty(value = "备注")
	private String remark;


	/**
	 * 商品实际价格
	 */
	@Field("max_price")
	@ApiModelProperty(value = "商品实际价格")
	private double maxPrice;


	/**
	 * 商品当前价格
	 */
	@Field("min_price")
	@ApiModelProperty(value = "商品当前价格")
	private double minPrice;

	/**
	 * 商品状态是否失效  false 失效 true有效
	 */
	@Field("state")
	@ApiModelProperty(value = "商品状态  0-上架 1-下架 2-售罄")
	private Integer state;

	/**
	 * 模板id
	 */
	@Field("appmodel_id")
	@ApiModelProperty(value = "模板id")
	private String appmodelId;

	@Field("product_type_list")
	@ApiModelProperty(value = "活动信息")
	private String productTypeList;


	@Field("create_time")
	@ApiModelProperty(value = "create_time")
	private String createTime;

}
