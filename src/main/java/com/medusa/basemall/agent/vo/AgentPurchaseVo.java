package com.medusa.basemall.agent.vo;

import io.swagger.annotations.ApiModelProperty;

public class AgentPurchaseVo {

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
	 * 用户ID
	 */
	@ApiModelProperty(value ="用户ID")
	private Long wxuserId;
	/**
	 * 模板ID
	 */
	@ApiModelProperty(value ="商家wxAppId")
	private String appmodelId;


	/**
	 * 商品选中的规格信息
	 */
	@ApiModelProperty(value ="商品选中的规格信息")
	private PurchaseSpecItemInfoVo productSpecItemInfo;


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

	public Long getWxuserId() {
		return wxuserId;
	}

	public void setWxuserId(Long wxuserId) {
		this.wxuserId = wxuserId;
	}

	public String getAppmodelId() {
		return appmodelId;
	}

	public void setAppmodelId(String appmodelId) {
		this.appmodelId = appmodelId;
	}

	public PurchaseSpecItemInfoVo getProductSpecItemInfo() {
		return productSpecItemInfo;
	}

	public void setProductSpecItemInfo(PurchaseSpecItemInfoVo productSpecItemInfo) {
		this.productSpecItemInfo = productSpecItemInfo;
	}
}
