package com.medusa.basemall.product.entity;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Table(name = "t_product")
public class Product {


	@Id
	@Column(name = "product_id")
	@ApiModelProperty(value = "商品编号")
	private Long productId;

	@Column(name = "product_name")
	@ApiModelProperty(value = "商品名称")
	private String productName;

	@ApiModelProperty(value = "描述")
	private String remark;


	@Column(name = "shelf_state")
	@ApiModelProperty(value = "上下状态(默认上架，0--上架，1--下架（仓库中），2--已售完)")
	private Integer shelfState;

	@Column(name = "send_place")
	@ApiModelProperty(value = "发货地")
	private String sendPlace;

	@Column(name = "send_date")
	@ApiModelProperty(value = "发货日期")
	private String sendDate;


	@ApiModelProperty(value = " 销售价（购买价）")
	private Double price;

	@Column(name = "market_price")
	@ApiModelProperty(value = "市场价（划掉的价格）")
	private Double marketPrice;


	@ApiModelProperty(value = "库存")
	private Integer stock;


	@Column(name = "sales_volume")
	@ApiModelProperty(value = "销量")
	private Integer salesVolume;

	@Column(name = "spec_type")
	@ApiModelProperty(value = "是否统一规格")
	private Boolean specType;

	@Deprecated
	@Column(name = "delivery_type")
	@ApiModelProperty(value = "是否统一邮费  true | flase")
	private Boolean deliveryType;


	@Column(name = "delivery_fees")
	@ApiModelProperty(value = "统一邮费 价格")
	private Double deliveryFees;


	@Column(name = "logistic_model_id")
	@ApiModelProperty(value = "邮费模板id")
	private Integer logisticModelId;

	@Column(name = "distribute_type")
	@Deprecated
	@ApiModelProperty(value = "是否支持商家配送")
	private Boolean distributeType;

	@Column(name = "service_assurance")
	@ApiModelProperty(value = "服务保障")
	private String serviceAssurance;


	@Column(name = "delete_state")
	@ApiModelProperty(value = "1删除，0不删除")
	private Boolean deleteState;

	@Column(name = "appmodel_id")
	@ApiModelProperty(value = "模板ID")
	private String appmodelId;

	@Column(name = "product_img")
	@ApiModelProperty(value = "主图")
	private String productImg;


	@Column(name = "create_time")
	@ApiModelProperty(value = "创建时间")
	private String createTime;

	@Column(name = "upate_time")
	@ApiModelProperty(value = "更新时间")
	private String upateTime;

	@Column(name = "rimg_url")
	@ApiModelProperty(value = "轮播图")
	private String rimgUrl;

	@Column(name = "text_img")
	@ApiModelProperty(value = "图文详情")
	private String textImg;

	@Column(name = "param_value")
	@ApiModelProperty(value = "规格参数")
	private String paramValue;

	@Column(name = "product_type_list")
	@ApiModelProperty(value = "活动类型")
	private String productTypeList;

	@Column(name = "product_bulk")
	@ApiModelProperty(value = "商品的体积或重量或重量,  计算方式根据运费模板的类型计算")
	private Double productBulk;

	@Transient
	private Double needPayPrice;

}