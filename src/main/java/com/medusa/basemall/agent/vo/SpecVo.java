package com.medusa.basemall.agent.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SpecVo {

	@ApiModelProperty(value = "商品id")
	private Long productId;
	@ApiModelProperty(value = "规格id")
	private List<Integer> specId;
	@ApiModelProperty(value = "规格值")
	private String specValue;
	@ApiModelProperty(value = "商品价格")
	private BigDecimal price;
	@ApiModelProperty(value = "代理价格")
	private List<BigDecimal> agentPrice;
	@ApiModelProperty(value = "商品库存")
	private Integer stock;
	@ApiModelProperty(value = "代理商品库存")
	private Integer agentStock;
	@ApiModelProperty(value = "规格项id")
	private Long specItemId;

	public SpecVo() {
	}

	public SpecVo(Long productId, List<Integer> specId, Long specItemId) {
		this.productId = productId;
		this.specId = specId;
		this.specItemId = specItemId;
	}
}
