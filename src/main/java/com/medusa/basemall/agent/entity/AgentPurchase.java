package com.medusa.basemall.agent.entity;

import com.medusa.basemall.product.entity.ProductSpecItem;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Agent_purchase")
public class AgentPurchase {
    /**
     * 采购表ID
     */
    @Id
	@ApiModelProperty(value ="采购单ID")
    private String purchaseId;

    /**
     * 商品ID
     */
	@ApiModelProperty(value ="商品ID")
    private Long productId;

    /**
     * 商品名称
     */
	@ApiModelProperty(value ="商品名称")
    private String productName;
    /**
     * 商品图片
     */
	@ApiModelProperty(value ="商品图片")
    private String productImg;
    /**
     * 商品数量
     */
	@ApiModelProperty(value ="商品数量")
    private int quantity;
    /**
     * 商品价格
     */
	@ApiModelProperty(value ="商品价格")
    private double countPrice;
    /**
     * 是否下架
     */
	@ApiModelProperty(value ="是否下架")
    private int shelfState;
    /**
     * 商品选中的规格信息
     */
	@ApiModelProperty(value ="商品选中的规格信息")
    private ProductSpecItem productSpecItemInfo;
    /**
     * 用户ID
     */
	@ApiModelProperty(value ="用户ID")
    private Long wxuserId;
    /**
     * 添加时间
     */
	@ApiModelProperty(value ="添加时间")
    private String createTime;
    /**
     * 模板ID
     */
	@ApiModelProperty(value ="商家wxAppId")
    private String appmodelId;

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getCountPrice() {
        return countPrice;
    }

    public void setCountPrice(double countPrice) {
        this.countPrice = countPrice;
    }

    public int getShelfState() {
        return shelfState;
    }

    public void setShelfState(int shelfState) {
        this.shelfState = shelfState;
    }

    public ProductSpecItem getProductSpecItemInfo() {
        return productSpecItemInfo;
    }

    public void setProductSpecItemInfo(ProductSpecItem productSpecItemInfo) {
        this.productSpecItemInfo = productSpecItemInfo;
    }

    public Long getWxuserId() {
        return wxuserId;
    }

    public void setWxuserId(Long wxuserId) {
        this.wxuserId = wxuserId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAppmodelId() {
        return appmodelId;
    }

    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }
}