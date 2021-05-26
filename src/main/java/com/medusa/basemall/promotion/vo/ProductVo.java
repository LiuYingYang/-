package com.medusa.basemall.promotion.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Created by psy on 2018/05/30.
 */
@Data
public class ProductVo {

    @ApiModelProperty(value = "商品id")
    private Long productId;

    @ApiModelProperty(value = "结算价格")
    private Double allPrice;

}
