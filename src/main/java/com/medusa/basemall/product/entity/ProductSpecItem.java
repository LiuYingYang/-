package com.medusa.basemall.product.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_product_spec_item")
@Data
public class ProductSpecItem {
    /**
     * 编号
     */
    @Id
    @Column(name = "product_spec_item_id")
	@ApiModelProperty(value ="编号")
    private Long productSpecItemId;

    /**
     * 规格id
     */
    @Column(name = "specification_value")
	@ApiModelProperty(value ="规格id")
    private String specificationValue;

    /**
     * 规格名称
     */
    @Column(name = "specification_name")
	@ApiModelProperty(value ="规格名称")
    private String specificationName;

    /**
     * 商品ID
     */
    @Column(name = "product_id")
	@ApiModelProperty(value ="商品ID")
    private Long productId;

    /**
     * sku图
     */
    @Column(name = "sku_img")
	@ApiModelProperty(value ="sku图")
    private String skuImg;

    /**
     * 价格
     */
	@ApiModelProperty(value ="价格")
    private Double price;


    /**
     * 库存
     */
	@ApiModelProperty(value ="库存")
    private Integer stock;

    /**
     * 活动库存
     */
	@ApiModelProperty(value ="活动库存")
    @Column(name = "activity_stock")
    private Integer activityStock;

	/**
	 * 活动价格
	 */
	@ApiModelProperty(value ="价格")
	@Column(name = "activity_stock")
	private Double activityPrice;


	/**
	 * 代理商品库存
	 */
    @Column(name = "agent_stock")
	@ApiModelProperty(value ="代理商品库存")
    private Integer agentStock;

    /**
     * 模板ID
     */
    @Column(name = "appmodel_id")
	@ApiModelProperty(value ="模板ID")
    private String appmodelId;


}