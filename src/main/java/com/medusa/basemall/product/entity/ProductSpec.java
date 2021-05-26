package com.medusa.basemall.product.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_product_spec")
public class ProductSpec {
    /**
     * 编号
     */
    @Id
    @Column(name = "product_spec_id")
    private Long productSpecId;

    /**
     * 规格分类编号
     */
    @Column(name = "specification_class_id")
    private Integer specificationClassId;

    /**
     * 规格ID
     */
    @Column(name = "specification_id")
    private Integer specificationId;

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
     * @return product_spec_id - 编号
     */
    public Long getProductSpecId() {
        return productSpecId;
    }

    /**
     * 设置编号
     *
     * @param productSpecId 编号
     */
    public void setProductSpecId(Long productSpecId) {
        this.productSpecId = productSpecId;
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
     * 获取规格ID
     *
     * @return specification_id - 规格ID
     */
    public Integer getSpecificationId() {
        return specificationId;
    }

    /**
     * 设置规格ID
     *
     * @param specificationId 规格ID
     */
    public void setSpecificationId(Integer specificationId) {
        this.specificationId = specificationId;
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