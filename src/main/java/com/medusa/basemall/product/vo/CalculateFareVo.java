package com.medusa.basemall.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author whh
 */
@Data
public class CalculateFareVo {

	@ApiModelProperty(value = "商品可用的物流配送方式名称")
	private String typeName;
	@ApiModelProperty(value = "运费价格")
	private Double price;
	@ApiModelProperty(value = "是否可用 1-可用 2-不可用")
	private Integer state;
	@ApiModelProperty(value = "不可使用原因")
	private String reason;

	public CalculateFareVo() {
	}

	public CalculateFareVo(String typeName, Double price, Integer state, String reason) {
		this.typeName = typeName;
		this.price = price;
		this.state = state;
		this.reason = reason;
	}
}
