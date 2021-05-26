package com.medusa.basemall.promotion.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Created by psy on 2018/05/30.
 */
@Table(name = "t_activity_product")
@Data
public class ActivityProduct {

	@ApiModelProperty(value = "活动商品编号")
	@Id
	@Column(name = "activity_product_id")
	@GeneratedValue(generator = "JDBC")
	private Long activityProductId;

	@ApiModelProperty(value = "商品ID")
	@Column(name = "product_id")
	private Long productId;

	@ApiModelProperty(value = "活动ID")
	@Column(name = "activity_id")
	private Integer activityId;

	@ApiModelProperty(value = "活动价")
	@Column(name = "activity_price")
	private Double activityPrice;

	@ApiModelProperty(value = "活动折扣")
	@Column(name = "activity_discount")
	private Double activityDiscount;

	@ApiModelProperty(value = "活动库存")
	@Column(name = "activity_stock")
	private Integer activityStock;

	@ApiModelProperty(value = "已售数量")
	@Column(name = "sold_quantity")
	private Integer soldQuantity;

	@ApiModelProperty(value = "限购")
	@Column(name = "max_quantity")
	private Integer maxQuantity;

	@ApiModelProperty(value = "预热状态  0-无预热,1-带预热商品")
	@Column(name = "preheat_state")
	private Integer preheatState;

	@ApiModelProperty(value = "首页排序 数值越小越大 but required >= 0")
	@Column(name = "sort")
	private Integer sort;

	@ApiModelProperty(value = "是否显示在首页 0-不显示 1-显示")
	@Column(name = "home_view_stat")
	private Integer homeViewStat;

	@ApiModelProperty(value = "活动类型（优惠券活动1001，特价活动2001，拼团3001，秒杀4001）")
	@Column(name = "activity_type")
	private Integer activityType;

	@ApiModelProperty(value = "用户模板ID")
	@Column(name = "appmodel_id")
	private String appmodelId;

	@ApiModelProperty(value = "删除标志")
	@Column(name = "delete_state")
	private Boolean deleteState;

	@Column(name = "spec_type")
	@ApiModelProperty(value = "是否统一规格  是否统一规格 0多规格,1-统一规格")
	private Boolean specType;

}