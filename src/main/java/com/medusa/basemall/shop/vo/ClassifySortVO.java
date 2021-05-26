package com.medusa.basemall.shop.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Created by wx on 2018/06/04.
 */
public class ClassifySortVO {

    @ApiModelProperty(value = "模板id")
    private String appmodelId;

    @ApiModelProperty(value = "操作类型(1置顶 2上移 3下移 4置底)")
    private Integer handleType;

    @ApiModelProperty(value = "分类id")
    private Integer classifyId;

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

    public Integer getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Integer classifyId) {
        this.classifyId = classifyId;
    }

    @Override
    public String toString() {
        return "ClassifySortVO{" +
                "appmodelId='" + appmodelId + '\'' +
                ", handleType=" + handleType +
                ", classifyId=" + classifyId +
                '}';
    }
}
