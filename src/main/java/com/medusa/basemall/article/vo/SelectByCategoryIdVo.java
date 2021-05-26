package com.medusa.basemall.article.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Created by wx on 2018/06/07.
 */
public class SelectByCategoryIdVo {

    @ApiModelProperty(value = "当前页数")
    private Integer pageNum;

    @ApiModelProperty(value = "每页数量")
    private Integer pageSize;

    @ApiModelProperty(value = "模板id")
    private String appmodelId;

    @ApiModelProperty(value = "分类id")
    private String categoryId ;

    @ApiModelProperty(value = "用户id")
    private Long wxuserId;

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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getAppmodelId() {
        return appmodelId;
    }

    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }

    public Long getWxuserId() {
        return wxuserId;
    }

    public void setWxuserId(Long wxuserId) {
        this.wxuserId = wxuserId;
    }

    @Override
    public String toString() {
        return "SelectByCategoryIdVo{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", appmodelId='" + appmodelId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", wxuserId=" + wxuserId +
                '}';
    }
}
