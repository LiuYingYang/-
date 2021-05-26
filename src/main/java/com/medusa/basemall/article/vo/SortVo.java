package com.medusa.basemall.article.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Created by wx on 2018/06/07.
 */
public class SortVo {
    @ApiModelProperty(value = "模板id")
    private String appmodelId;

    @ApiModelProperty(value = "海报id")
    private String articleId;

    @ApiModelProperty(value = "操作类型(1置顶 2上移 3下移 4置底)")
    private Integer handleType;

    public String getAppmodelId() {
        return appmodelId;
    }

    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public Integer getHandleType() {
        return handleType;
    }

    public void setHandleType(Integer handleType) {
        this.handleType = handleType;
    }

    @Override
    public String toString() {
        return "SortVo{" +
                "appmodelId='" + appmodelId + '\'' +
                ", articleId='" + articleId + '\'' +
                ", handleType=" + handleType +
                '}';
    }
}
