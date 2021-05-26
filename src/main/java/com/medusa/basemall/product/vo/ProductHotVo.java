package com.medusa.basemall.product.vo;

import com.medusa.basemall.product.entity.Product;
import io.swagger.annotations.ApiModelProperty;

public class ProductHotVo extends Product {

    @ApiModelProperty(value = "销售额")
    private Double saleNumber;

    public Double getSaleNumber() {
        return saleNumber;
    }

    public void setSaleNumber(Double saleNumber) {
        this.saleNumber = saleNumber;
    }
}
