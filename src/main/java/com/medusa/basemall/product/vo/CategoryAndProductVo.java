package com.medusa.basemall.product.vo;

import com.medusa.basemall.product.entity.Category;
import com.medusa.basemall.product.entity.Product;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Created by wx on 2018/09/08.
 */
public class CategoryAndProductVo {

    @ApiModelProperty(value = "一级分类对象")
    private Category firstCategory;

    @ApiModelProperty(value = "二级分类对象")
    private Category secondCategory;

    @ApiModelProperty(value = "商品对象")
    private Product product;

    public Category getFirstCategory() {
        return firstCategory;
    }

    public void setFirstCategory(Category firstCategory) {
        this.firstCategory = firstCategory;
    }

    public Category getSecondCategory() {
        return secondCategory;
    }

    public void setSecondCategory(Category secondCategory) {
        this.secondCategory = secondCategory;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
