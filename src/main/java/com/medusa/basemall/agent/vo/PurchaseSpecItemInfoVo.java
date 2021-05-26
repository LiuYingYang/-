package com.medusa.basemall.agent.vo;

import io.swagger.annotations.ApiModelProperty;



public class PurchaseSpecItemInfoVo {

	@ApiModelProperty(value ="规格项id  如无规格则不传入id")
	private Long productSpecItemId;

	/**
	 * 规格id
	 */
	@ApiModelProperty(value ="规格id")
	private String specificationValue;

	/**
	 * 规格名称
	 */
	@ApiModelProperty(value ="规格名称")
	private String specificationName;

	/**
	 * sku图
	 */
	@ApiModelProperty(value ="sku图")
	private String skuImg;

	/**
	 * 价格
	 */
	@ApiModelProperty(value ="价格")
	private Double price;

	/**
	 * 代理商品库存
	 */
	@ApiModelProperty(value ="代理商品库存")
	private Integer agentStock;

	public Long getProductSpecItemId() {
		return productSpecItemId;
	}

	public void setProductSpecItemId(Long productSpecItemId) {
		this.productSpecItemId = productSpecItemId;
	}

	public String getSpecificationValue() {
		return specificationValue;
	}

	public void setSpecificationValue(String specificationValue) {
		this.specificationValue = specificationValue;
	}

	public String getSpecificationName() {
		return specificationName;
	}

	public void setSpecificationName(String specificationName) {
		this.specificationName = specificationName;
	}

	public String getSkuImg() {
		return skuImg;
	}

	public void setSkuImg(String skuImg) {
		this.skuImg = skuImg;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getAgentStock() {
		return agentStock;
	}

	public void setAgentStock(Integer agentStock) {
		this.agentStock = agentStock;
	}
}
