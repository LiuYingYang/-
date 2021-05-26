package com.medusa.basemall.promotion.vo;

import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.promotion.entity.ActivityCoupon;
import com.medusa.basemall.promotion.entity.Coupon;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author Created by psy on 2018/05/30.
 */
public class ActivityCouponVo extends ActivityCoupon {

    @ApiModelProperty(value="优惠券对象集合")
    private List<Coupon> couponList;

    @ApiModelProperty(value="商品id对象集合")
    private List<Long> productIds;

    @ApiModelProperty(value="商品对象集合")
    private List<Product> products;

    public List<Coupon> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<Coupon> couponList) {
        this.couponList = couponList;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "ActivityCouponVo{" +
                "couponList=" + couponList +
                ", productIds=" + productIds +
                ", products=" + products +
                '}';
    }
}
