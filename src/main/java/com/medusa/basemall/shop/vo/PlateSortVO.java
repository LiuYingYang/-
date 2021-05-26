package com.medusa.basemall.shop.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Created by wx on 2018/05/26.
 */
public class PlateSortVO {

    @ApiModelProperty(value = "模板id")
    private String appmodelId;

    @ApiModelProperty(value = "操作类型(1置顶 2上移 3下移 4置底)")
    private Integer handleType;

    @ApiModelProperty(value = "展示区id")
    private Integer plateId;

    public String getAppmodelId() {
        return appmodelId;
    }

    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }

    public Integer getHandleType() {
        return handleType;
    }

    public void setHandleType(Integer handleType) {
        this.handleType = handleType;
    }

    public Integer getPlateId() {
        return plateId;
    }

    public void setPlateId(Integer plateId) {
        this.plateId = plateId;
    }

    @Override
    public String toString() {
        return "PlateSortVO{" +
                "appmodelId='" + appmodelId + '\'' +
                ", handleType=" + handleType +
                ", plateId=" + plateId +
                '}';
    }
}
