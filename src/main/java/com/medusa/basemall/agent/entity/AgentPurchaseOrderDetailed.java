package com.medusa.basemall.agent.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "t_agent_purchase_order_detailed")
public class AgentPurchaseOrderDetailed {
    /**
     * 采购订单表id
     */
    @Id
    @Column(name = "purchase_order_detailed_id")
	@ApiModelProperty(value ="采购订单表id")
    private Long purchaseOrderDetailedId;

    /**
     * 商品ID
     */
    @Column(name = "product_id")
	@ApiModelProperty(value ="商品ID")
    private Long productId;

    /**
     * 商品名称
     */
    @Column(name = "product_name")
	@ApiModelProperty(value ="商品名称")
    private String productName;

    /**
     * 商品价格
     */
	@ApiModelProperty(value ="商品价格")
    private BigDecimal price;

    /**
     * 商品主图
     */
    @Column(name = "product_img")
	@ApiModelProperty(value ="商品主图")
    private String productImg;

    /**
     * 商品数量
     */
	@ApiModelProperty(value ="商品数量")
    private Integer quantity;

    /**
     * 商品-规格详情编号
     */
    @Column(name = "product_spec_info")
	@ApiModelProperty(value ="商品-规格详情编号")
    private String productSpecInfo;

    /**
     * 活动详情编号
     */
    @Column(name = "activity_info")
	@ApiModelProperty(value ="活动详情编号")
    private String activityInfo;

    /**
     * 采购订单ID
     */
    @Column(name = "purchase_order_id")
	@ApiModelProperty(value ="采购订单ID")
    private Long purchaseOrderId;

    /**
     * 添加时间
     */
    @Column(name = "create_time")
	@ApiModelProperty(value ="添加时间")
    private String createTime;

    /**
     * 模板ID
     */
    @Column(name = "appmodel_id")
	@ApiModelProperty(value ="模板ID")
    private String appmodelId;

    /**
     * 删除标志
     */
    @Column(name = "delete_state")
	@ApiModelProperty(value ="删除标志")
    private Boolean deleteState;

    /**
     * 获取采购订单表id
     *
     * @return purchase_order_detailed_id - 采购订单表id
     */
    public Long getPurchaseOrderDetailedId() {
        return purchaseOrderDetailedId;
    }

    /**
     * 设置采购订单表id
     *
     * @param purchaseOrderDetailedId 采购订单表id
     */
    public void setPurchaseOrderDetailedId(Long purchaseOrderDetailedId) {
        this.purchaseOrderDetailedId = purchaseOrderDetailedId;
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
     * 获取商品名称
     *
     * @return product_name - 商品名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设置商品名称
     *
     * @param productName 商品名称
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 获取商品价格
     *
     * @return price - 商品价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置商品价格
     *
     * @param price 商品价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取商品主图
     *
     * @return product_img - 商品主图
     */
    public String getProductImg() {
        return productImg;
    }

    /**
     * 设置商品主图
     *
     * @param productImg 商品主图
     */
    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    /**
     * 获取商品数量
     *
     * @return quantity - 商品数量
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * 设置商品数量
     *
     * @param quantity 商品数量
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * 获取商品-规格详情编号
     *
     * @return product_spec_info - 商品-规格详情编号
     */
    public String getProductSpecInfo() {
        return productSpecInfo;
    }

    /**
     * 设置商品-规格详情编号
     *
     * @param productSpecInfo 商品-规格详情编号
     */
    public void setProductSpecInfo(String productSpecInfo) {
        this.productSpecInfo = productSpecInfo;
    }

    /**
     * 获取活动详情编号
     *
     * @return activity_info - 活动详情编号
     */
    public String getActivityInfo() {
        return activityInfo;
    }

    /**
     * 设置活动详情编号
     *
     * @param activityInfo 活动详情编号
     */
    public void setActivityInfo(String activityInfo) {
        this.activityInfo = activityInfo;
    }

	public Long getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(Long purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	/**
     * 获取添加时间
     *
     * @return create_time - 添加时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置添加时间
     *
     * @param createTime 添加时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取模板ID
     *
     * @return appmodel_id - 模板ID
     */
    public String getAppmodelId() {
        return appmodelId;
    }

    /**
     * 设置模板ID
     *
     * @param appmodelId 模板ID
     */
    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }

    /**
     * 获取删除标志
     *
     * @return delete_state - 删除标志
     */
    public Boolean getDeleteState() {
        return deleteState;
    }

    /**
     * 设置删除标志
     *
     * @param deleteState 删除标志
     */
    public void setDeleteState(Boolean deleteState) {
        this.deleteState = deleteState;
    }
}