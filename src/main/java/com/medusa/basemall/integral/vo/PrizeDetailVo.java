package com.medusa.basemall.integral.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Created by wx on 2018/06/06.
 */
public class PrizeDetailVo {

    @ApiModelProperty(value = "模板id", required = true)
    private String appmodelId;

    @ApiModelProperty(value = "用户id", required = true)
    private Long wxuserId;

    @ApiModelProperty(value = "当前页数", required = true)
    private Integer pageNum;

    @ApiModelProperty(value = "每页数量", required = true)
    private Integer pageSize;

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

    public String getAppmodelId() {
        return appmodelId;
    }

    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }

    @Override
    public String toString() {
        return "PrizeDetailVo{" +
                "appmodelId='" + appmodelId + '\'' +
                ", wxuserId=" + wxuserId +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
