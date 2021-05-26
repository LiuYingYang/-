package com.medusa.basemall.agent.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author whh
 */
@Data
public class PitchonProduct {

	@ApiModelProperty(value = "商品appmodelId")
    private String appmodelId;
	@ApiModelProperty(value = "商品id")
    private Long productId;
	@ApiModelProperty(value = "商品名称")
    private String productName;
	@ApiModelProperty(value = "商品图片")
    private String productImage;
	@ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "代理价格")
    private List<BigDecimal> agentPrice;

    @ApiModelProperty(value = "商品库存")
    private Integer stock;

    @ApiModelProperty(value = "是否统一规格   false 0  多规格  true 统一规格 ")
    private Boolean specType;

	@ApiModelProperty(value = "多规格商品规格属性")
    private List<SpecVo> specVos;

    @ApiModelProperty(value = "代理商品库存")
    private Integer agentStock;


}
