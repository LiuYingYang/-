package com.medusa.basemall.product.vo;

import com.medusa.basemall.product.entity.*;

import java.util.List;

/**
 * 商品编辑时查询的数据
 */
public class ProductEditVo extends Product {

    /**
     * 服务保障
     */
    private List<String> serviceAssuranceList;

    /**
     * 轮播图
     */
    private List<String> rimgurlList;

    /**
     * 属性数组
     */
    private List<Parameter> paramValueList;

    /**
     * 规格分类
     */
    private List<ProductSpecClass> productSpecClassList;

    /**
     * 规格
     */
    private List<ProductSpec> productSpecList;

    /**
     * 规格详情
     */
    private List<ProductSpecItem> productSpecItemList;

    /**
     * 分类ID数组
     */
    private List<Long> categoryIds;

    public List<String> getServiceAssuranceList() {
        return serviceAssuranceList;
    }

    public void setServiceAssuranceList(List<String> serviceAssuranceList) {
        this.serviceAssuranceList = serviceAssuranceList;
    }

    public List<String> getRimgurlList() {
        return rimgurlList;
    }

    public void setRimgurlList(List<String> rimgurlList) {
        this.rimgurlList = rimgurlList;
    }

    public List<Parameter> getParamValueList() {
        return paramValueList;
    }

    public void setParamValueList(List<Parameter> paramValueList) {
        this.paramValueList = paramValueList;
    }

    public List<ProductSpecClass> getProductSpecClassList() {
        return productSpecClassList;
    }

    public void setProductSpecClassList(List<ProductSpecClass> productSpecClassList) {
        this.productSpecClassList = productSpecClassList;
    }

    public List<ProductSpec> getProductSpecList() {
        return productSpecList;
    }

    public void setProductSpecList(List<ProductSpec> productSpecList) {
        this.productSpecList = productSpecList;
    }

    public List<ProductSpecItem> getProductSpecItemList() {
        return productSpecItemList;
    }

    public void setProductSpecItemList(List<ProductSpecItem> productSpecItemList) {
        this.productSpecItemList = productSpecItemList;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Long> categoryIds) {
        this.categoryIds = categoryIds;
    }


}
