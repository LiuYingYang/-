package com.medusa.basemall.order.vo;

import com.medusa.basemall.product.vo.JoinActiveInfo;
import com.medusa.basemall.product.vo.ProductSimpleVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductOrderVo extends ProductSimpleVo {

	@ApiModelProperty(value = "商品购买数量")
    private Integer buyQuantity;

	@ApiModelProperty(value = "商品购买价格")
    private Double buyPrice;

	@ApiModelProperty(value = "规格信息")
    private String productSpecItemInfo;

	@ApiModelProperty(value = "参加的活动信息")
	private JoinActiveInfo joinActiveInfo;

}
