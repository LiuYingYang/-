package com.medusa.basemall.agent.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "t_agent_product")
public class AgentProduct {

    /**
     * 编号
     */
    @Id
    @Column(name = "agent_product_id")
	@ApiModelProperty(value ="编号")
    private Long agentProductId;

    /**
     * 商品ID
     */
    @Column(name = "product_id")
	@ApiModelProperty(value ="商品ID")
    private Long productId;

    /**
     * 分销商品库存
     */
    @Column(name = "agent_stock")
	@ApiModelProperty(value ="分销商品库存")
    private Integer agentStock;

    /**
     * 分销商品销量
     */
    @Column(name = "agent_sales_volume")
	@ApiModelProperty(value ="分销商品销量")
    private Integer agentSalesVolume;

    /**
     * 分销商品上下架状态（默认上架，0------上架，1---------下架（仓库中），2---------已售完）
     */
    @Column(name = "agent_shelf_state")
	@ApiModelProperty(value ="分销商品上下架状态（默认上架，0------上架，1---------下架（仓库中），2---------已售完）")
    private Integer agentShelfState;

    /**
     * 原价
     */
	@ApiModelProperty(value ="原价")
    private Double price;

    /**
     * 删除标志
     */
    @Column(name = "delete_state")
	@ApiModelProperty(value ="删除标志")
    private Integer deleteState;

    /**
     * 模板id
     */
    @Column(name = "appmodel_id")
	@ApiModelProperty(value ="商家wxAppId")
    private String appmodelId;

    /**
     * 发布时间
     */
    @Column(name = "release_time")
	@ApiModelProperty(value ="发布时间")
    private String releaseTime;

    /**
     * 下架时间
     */
    @Column(name = "down_shelves_time")
	@ApiModelProperty(value ="下架时间")
    private String downShelvesTime;

    /**
     * 是否有规格  0false 1true
     */
    @Column(name = "spec_type")
	@ApiModelProperty(value ="是否有规格 0false 1true")
    private Boolean specType;

    @Column(name = "product_name")
	@ApiModelProperty(value ="商品名称")
    private String productName;

    @Transient
	@ApiModelProperty(value ="最小价格")
    private Double minPrice;

    @Transient
	@ApiModelProperty(value ="最大价格")
    private Double maxPrice;


    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 获取编号
     *
     * @return agent_product_id - 编号
     */
    public Long getAgentProductId() {
        return agentProductId;
    }

    /**
     * 设置编号
     *
     * @param agentProductId 编号
     */
    public void setAgentProductId(Long agentProductId) {
        this.agentProductId = agentProductId;
    }

    /**
     * 获取商品ID
     *
     * @return product_id - 商品ID
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * 设置商品ID
     *
     * @param productId 商品ID
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * 获取分销商品库存
     *
     * @return agent_stock - 分销商品库存
     */
    public Integer getAgentStock() {
        return agentStock;
    }

    /**
     * 设置分销商品库存
     *
     * @param agentStock 分销商品库存
     */
    public void setAgentStock(Integer agentStock) {
        this.agentStock = agentStock;
    }

    /**
     * 获取分销商品销量
     *
     * @return agent_sales_volume - 分销商品销量
     */
    public Integer getAgentSalesVolume() {
        return agentSalesVolume;
    }

    /**
     * 设置分销商品销量
     *
     * @param agentSalesVolume 分销商品销量
     */
    public void setAgentSalesVolume(Integer agentSalesVolume) {
        this.agentSalesVolume = agentSalesVolume;
    }

    /**
     * 获取分销商品上下架状态（默认上架，0------上架，1---------下架（仓库中），2---------已售完）
     *
     * @return agent_shelf_state - 分销商品上下架状态（默认上架，0------上架，1---------下架（仓库中），2---------已售完）
     */
    public Integer getAgentShelfState() {
        return agentShelfState;
    }

    /**
     * 设置分销商品上下架状态（默认上架，0------上架，1---------下架（仓库中），2---------已售完）
     *
     * @param agentShelfState 分销商品上下架状态（默认上架，0------上架，1---------下架（仓库中），2---------已售完）
     */
    public void setAgentShelfState(Integer agentShelfState) {
        this.agentShelfState = agentShelfState;
    }

    /**
     * 获取原价
     *
     * @return price - 原价
     */
    public Double getPrice() {
        return price;
    }

    /**
     * 设置原价
     *
     * @param price 原价
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * 获取删除标志
     *
     * @return delete_state - 删除标志
     */
    public Integer getDeleteState() {
        return deleteState;
    }

    /**
     * 设置删除标志
     *
     * @param deleteState 删除标志
     */
    public void setDeleteState(Integer deleteState) {
        this.deleteState = deleteState;
    }

    /**
     * 获取模板id
     *
     * @return appmodel_id - 模板id
     */
    public String getAppmodelId() {
        return appmodelId;
    }

    /**
     * 设置模板id
     *
     * @param appmodelId 模板id
     */
    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }

    /**
     * 获取发布时间
     *
     * @return release_time - 发布时间
     */
    public String getReleaseTime() {
        return releaseTime;
    }

    /**
     * 设置发布时间
     *
     * @param releaseTime 发布时间
     */
    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    /**
     * 获取下架时间
     *
     * @return down_shelves_time - 下架时间
     */
    public String getDownShelvesTime() {
        return downShelvesTime;
    }

    /**
     * 设置下架时间
     *
     * @param downShelvesTime 下架时间
     */
    public void setDownShelvesTime(String downShelvesTime) {
        this.downShelvesTime = downShelvesTime;
    }

    /**
     * 获取是否统一规格  0false 1true
     *
     * @return spec_type - 是否统一规格  0false 1true
     */
    public Boolean getSpecType() {
        return specType;
    }

    /**
     * 设置是否统一规格  0false 1true
     *
     * @param specType 是否统一规格  0false 1true
     */
    public void setSpecType(Boolean specType) {
        this.specType = specType;
    }
}