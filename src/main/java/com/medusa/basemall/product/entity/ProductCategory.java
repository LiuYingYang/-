package com.medusa.basemall.product.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_product_category")
public class ProductCategory {
    /**
     * 商品分类编号
     */
    @Id
    @Column(name = "product_category_id")
    private Long productCategoryId;

    /**
     * 分类ID
     */
    @Column(name = "category_id")
    private Long categoryId;

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
     * 获取商品分类编号
     *
     * @return product_category_id - 商品分类编号
     */
    public Long getProductCategoryId() {
        return productCategoryId;
    }

    /**
     * 设置商品分类编号
     *
     * @param productCategoryId 商品分类编号
     */
    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    /**
     * 获取分类ID
     *
     * @return category_id - 分类ID
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * 设置分类ID
     *
     * @param categoryId 分类ID
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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