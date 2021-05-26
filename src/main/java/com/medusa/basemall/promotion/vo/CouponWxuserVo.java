package com.medusa.basemall.promotion.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Created by psy on 2018/05/30.
 */
public class CouponWxuserVo {

    @ApiModelProperty(value="用户id", required = true)
    private Long wxuserId;

    @ApiModelProperty(value="模板id", required = true)
    private String appmodelId;

    @ApiModelProperty(value="使用状态(查询用户优惠券时传)")
    private Integer useFlag;

    @ApiModelProperty(value="结算商品对象数组(结算时查询用户可用优惠券时传)")
    private String productVos;

    @ApiModelProperty(value="所有商品结算总价(结算时查询用户可用优惠券时传)")
    private Double allPrice;

    public Integer getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(Integer useFlag) {
        this.useFlag = useFlag;
    }

    public String getProductVos() {
        return productVos;
    }

    public void setProductVos(String productVos) {
        this.productVos = productVos;
    }

    public Double getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(Double allPrice) {
        this.allPrice = allPrice;
    }

    public Long getWxuserId() {
        return wxuserId;
    }

    public void setWxuserId(Long wxuserId) {
        this.wxuserId = wxuserId;
    }

    public String getAppmodelId() {
        return appmodelId;
    }

    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }

    @Override
    public String toString() {
        return "CouponWxuserVo{" +
                "wxuserId=" + wxuserId +
                ", appmodelId='" + appmodelId + '\'' +
                ", useFlag=" + useFlag +
                ", productVos='" + productVos + '\'' +
                ", allPrice=" + allPrice +
                '}';
    }
}
