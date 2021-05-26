package com.medusa.basemall.messages.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Created by wx on 2018/08/09.
 */
public class MessagesVo {

    @ApiModelProperty(value = "当前页数")
    private Integer pageNum;

    @ApiModelProperty(value = "每页数量")
    private Integer pageSize;

    @ApiModelProperty(value = "所属模快(1订单 2营销 3售后 4系统 5平台)")
    private Integer module;

    @ApiModelProperty(value = "模板id")
    private String appmodelId;

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

    public Integer getModule() {
        return module;
    }

    public void setModule(Integer module) {
        this.module = module;
    }

    public String getAppmodelId() {
        return appmodelId;
    }

    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }

    @Override
    public String toString() {
        return "MessagesVo{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", module=" + module +
                '}';
    }
}
