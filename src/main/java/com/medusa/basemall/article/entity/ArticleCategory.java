package com.medusa.basemall.article.entity;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;

/**
 * @author Created by wx on 2018/06/07.
 */
public class ArticleCategory {

    @Id
    @ApiModelProperty(value = "分类id")
    private String categoryId;

    @ApiModelProperty(value = "分类名")
    private String categoryName;

    @ApiModelProperty(value = "分类图片")
    private String categoryImg;

    @ApiModelProperty(value = "分类类型(0-系统添加无法删除 1-商家添加可删除)")
    private Integer categoryType;

    @ApiModelProperty(value = "删除标示")
    private Integer deleteState;

    @ApiModelProperty(value = "模板Id")
    private String appmodelId;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(String categoryImg) {
        this.categoryImg = categoryImg;
    }

    public Integer getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(Integer deleteState) {
        this.deleteState = deleteState;
    }

    public String getAppmodelId() {
        return appmodelId;
    }

    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }

    public Integer getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }

    @Override
    public String toString() {
        return "ArticleCategory{" +
                "categoryId='" + categoryId + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", categoryImg='" + categoryImg + '\'' +
                ", categoryType=" + categoryType +
                ", deleteState=" + deleteState +
                ", appmodelId='" + appmodelId + '\'' +
                '}';
    }
}
