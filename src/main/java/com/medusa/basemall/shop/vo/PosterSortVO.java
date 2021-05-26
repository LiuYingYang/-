package com.medusa.basemall.shop.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Created by wx on 2018/05/25.
 */
public class PosterSortVO {

    @ApiModelProperty(value = "模板id")
    private String appmodelId;

    @ApiModelProperty(value = "海报id")
    private Integer posterId;

    @ApiModelProperty(value = "操作类型(1置顶 2上移 3下移 4置底)")
    private Integer handleType;

    public String getAppmodelId() {
        return appmodelId;
    }

    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }

    public Integer getPosterId() {
        return posterId;
    }

    public void setPosterId(Integer posterId) {
        this.posterId = posterId;
    }

    public Integer getHandleType() {
        return handleType;
    }

    public void setHandleType(Integer handleType) {
        this.handleType = handleType;
    }

    @Override
    public String toString() {
        return "PosterSortVO{" +
                "appmodelId='" + appmodelId + '\'' +
                ", posterId=" + posterId +
                ", handleType=" + handleType +
                '}';
    }
}
