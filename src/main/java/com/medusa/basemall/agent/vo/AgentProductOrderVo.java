package com.medusa.basemall.agent.vo;

import io.swagger.annotations.ApiModelProperty;

public class AgentProductOrderVo {

	@ApiModelProperty(value = "代理商品购买数量")
	private Integer quantity;
	@ApiModelProperty(value = "代理商品购买价格")
	private Double price;
	@ApiModelProperty(value = "商品id")
	private Long productId;
	@ApiModelProperty(value = "商品名称")
	private String productName;
	@ApiModelProperty(value = "商品图片")
	private String productImg;
	@ApiModelProperty(value = "是否有规格  false不带规格 true带规格")
	private Boolean specType;
	@ApiModelProperty(value = "规格信息")
	private String productSpecInfo;

	public String getProductImg() {
		return productImg;
	}

	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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

	public Boolean getSpecType() {
		return specType;
	}

	public void setSpecType(Boolean specType) {
		this.specType = specType;
	}

	public String getProductSpecInfo() {
		return productSpecInfo;
	}

	public void setProductSpecInfo(String productSpecInfo) {
		this.productSpecInfo = productSpecInfo;
	}
}
