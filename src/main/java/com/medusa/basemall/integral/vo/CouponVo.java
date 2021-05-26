package com.medusa.basemall.integral.vo;

import com.medusa.basemall.promotion.entity.Coupon;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Created by wx on 2018/06/06.
 */
public class CouponVo {

    @ApiModelProperty(value = "优惠券对象")
    private Coupon coupon;

    @ApiModelProperty(value = "当前页数")
    private Integer pageNum;

    @ApiModelProperty(value = "每页数量")
    private Integer pageSize;

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "CouponVo{" +
                "coupon=" + coupon +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
