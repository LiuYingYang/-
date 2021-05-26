package com.medusa.basemall.product.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_product_spec_class")
public class ProductSpecClass {
    /**
     * 编号
     */
    @Id
    @Column(name = "product_spec_class_id")
    private Long productSpecClassId;

    /**
     * 规格分类编号
     */
    @Column(name = "specification_class_id")
    private Integer specificationClassId;

    /**
     * 商品编号
     */
    @Column(name = "product_id")
    private Long productId;

    /**
     * 模板ID
     */
    @Column(name = "appmodel_id")
    private String appmodelId;

    /**
     * 获取编号
     *
     * @return product_spec_class_id - 编号
     */
    public Long getProductSpecClassId() {
        return productSpecClassId;
    }

    /**
     * 设置编号
     *
     * @param productSpecClassId 编号
     */
    public void setProductSpecClassId(Long productSpecClassId) {
        this.productSpecClassId = productSpecClassId;
    }

    /**
     * 获取规格分类编号
     *
     * @return specification_class_id - 规格分类编号
     */
    public Integer getSpecificationClassId() {
        return specificationClassId;
    }

    /**
     * 设置规格分类编号
     *
     * @param specificationClassId 规格分类编号
     */
    public void setSpecificationClassId(Integer specificationClassId) {
        this.specificationClassId = specificationClassId;
    }

    /**
     * 获取商品编号
     *
     * @return product_id - 商品编号
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * 设置商品编号
     *
     * @param productId 商品编号
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * 获取模板ID
     *
     * @return appmodel_id - 模板ID
     */
    public String getAppmodelId() {
        return appmodelId;
    }

    /**
     * 设置模板ID
     *
     * @param appmodelId 模板ID
     */
    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }

}