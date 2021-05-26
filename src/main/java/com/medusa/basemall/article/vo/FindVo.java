package com.medusa.basemall.article.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Created by wx on 2018/06/07.
 */
public class FindVo {

    @ApiModelProperty(value = "查询关键字")
    private String findWord;

    @ApiModelProperty(value = "模板id")
    private String appmodelId;

    public String getFindWord() {
        return findWord;
    }

    public void setFindWord(String findWord) {
        this.findWord = findWord;
    }

    public String getAppmodelId() {
        return appmodelId;
    }

    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }

    @Override
    public String toString() {
        return "FindVo{" +
                "findWord='" + findWord + '\'' +
                ", appmodelId='" + appmodelId + '\'' +
                '}';
    }
}
