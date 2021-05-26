package com.medusa.basemall.product.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_category")
public class Category {
    /**
     * 分类id
     */
    @Id
    @Column(name = "category_id")
    private Long categoryId;

    /**
     * 分类名
     */
    @Column(name = "category_name")
    private String categoryName;

    /**
     * (1-一级分类 2-二级分类)
     */
    @Column(name = "category_type")
    private Integer categoryType;

    /**
     * father_id 分类父ID
     */
    @Column(name = "father_id")
    private Long fatherId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 分类图标
     */
    @Column(name = "category_icon")
    private String categoryIcon;

    /**
     * 分类主图
     */
    @Column(name = "category_img")
    private String categoryImg;

    /**
     * 排序值
     */
    @Column(name = "sort_index")
    private Integer sortIndex;

    /**
     * 模板ID
     */
    @Column(name = "appmodel_id")
    private String appmodelId;

    /**
     * 获取分类id
     *
     * @return category_id - 分类id
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * 设置分类id
     *
     * @param categoryId 分类id
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 获取分类名
     *
     * @return category_name - 分类名
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 设置分类名
     *
     * @param categoryName 分类名
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 获取(1-一级分类 2-二级分类)
     *
     * @return category_type - (1-一级分类 2-二级分类)
     */
    public Integer getCategoryType() {
        return categoryType;
    }

    /**
     * 设置(1-一级分类 2-二级分类)
     *
     * @param categoryType (1-一级分类 2-二级分类)
     */
    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }

    /**
     * 获取father_id 分类父ID
     *
     * @return father_id - father_id 分类父ID
     */
    public Long getFatherId() {
        return fatherId;
    }

    /**
     * 设置father_id 分类父ID
     *
     * @param fatherId father_id 分类父ID
     */
    public void setFatherId(Long fatherId) {
        this.fatherId = fatherId;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取分类图标
     *
     * @return category_icon - 分类图标
     */
    public String getCategoryIcon() {
        return categoryIcon;
    }

    /**
     * 设置分类图标
     *
     * @param categoryIcon 分类图标
     */
    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    /**
     * 获取分类主图
     *
     * @return category_img - 分类主图
     */
    public String getCategoryImg() {
        return categoryImg;
    }

    /**
     * 设置分类主图
     *
     * @param categoryImg 分类主图
     */
    public void setCategoryImg(String categoryImg) {
        this.categoryImg = categoryImg;
    }

    public Integer getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(Integer sortIndex) {
        this.sortIndex = sortIndex;
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