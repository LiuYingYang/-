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
@Table(name = "t_activity_product_stock")
@Data
public class ActivityProductStock {

    @ApiModelProperty(value="活动商品规格库存id")
    @Id
    @Column(name = "activity_product_stock_id")
    @GeneratedValue(generator = "JDBC")
    private Integer activityProductStockId;

    @ApiModelProperty(value="活动商品id")
    @Column(name = "activity_product_id")
    private Long activityProductId;

    @ApiModelProperty(value="规格id   添加活动的时候必须")
    @Column(name = "product_spec_item_id")
    private Long productSpecItemId;

    @ApiModelProperty(value="活动库存  添加活动的时候必须")
	@Column(name = "activity_stock")
	private Integer activityStock;

	@ApiModelProperty(value="多规格商品活动价格")
	@Column(name = "activity_price")
	private Double activityPrice;

	@ApiModelProperty(value="活动id")
	@Column(name = "activity_id")
	private Integer activityId;

	@ApiModelProperty(value="商家id")
	@Column(name = "appmodel_id")
	private String appmodelId;

	@ApiModelProperty(value="活动类型")
	@Column(name = "activity_type")
	private Integer activityType;

	@ApiModelProperty(value="是否删除  0false  1true")
	@Column(name = "delete_state")
	private Boolean deleteState;


}