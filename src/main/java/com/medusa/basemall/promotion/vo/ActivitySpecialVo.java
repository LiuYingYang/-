package com.medusa.basemall.promotion.vo;

import com.medusa.basemall.product.vo.ProductWxVo;
import com.medusa.basemall.promotion.entity.ActivitySpecial;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author Created by psy on 2018/05/30.
 */
public class ActivitySpecialVo extends ActivitySpecial {

    @ApiModelProperty(value="数量")
    private Integer joinNum;

    @ApiModelProperty(value="商品列表")
    private List<ProductWxVo> productList;

    public List<ProductWxVo> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductWxVo> productList) {
        this.productList = productList;
    }

    public Integer getJoinNum() {
        return joinNum;
    }

    public void setJoinNum(Integer joinNum) {
        this.joinNum = joinNum;
    }

    @Override
    public String toString() {
        return "ActivitySpecialVo{" +
                "joinNum=" + joinNum +
                ", productList=" + productList +
                '}';
    }
}
