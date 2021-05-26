package com.medusa.basemall.user.vo;

import io.swagger.annotations.ApiModelProperty;

public class CollectList {

    @ApiModelProperty(value = "用户id",example = "1531212718240325")
    private Long wxuserId;
    @ApiModelProperty(value = "页数",example = "1")
    private Integer pageNum;
    @ApiModelProperty(value = "条数",example = "10")
    private Integer pageSize;
    @ApiModelProperty(value = "商家唯一id",example = "S00020001wxa75115dccbe8ecec")
    private String appmodelId;

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
}
