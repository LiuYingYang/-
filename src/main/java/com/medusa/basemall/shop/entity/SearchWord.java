package com.medusa.basemall.shop.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Created by wx on 2018/05/25.
 */
@Table(name = "t_search_word")
public class SearchWord {

    @Id
    @Column(name = "search_word_id")
    @ApiModelProperty(value = "搜索词id")
    private Integer searchWordId;

    @ApiModelProperty(value = "关键词")
    private String keyword;

    @ApiModelProperty(value = "关键词类型(0-推荐文字 1-默认文字)")
    private Boolean wordtype;

    @ApiModelProperty(value = "模板id")
    @Column(name = "appmodel_id")
    private String appmodelId;

    /**
     * 获取编号
     *
     * @return search_word_id - 编号
     */
    public Integer getSearchWordId() {
        return searchWordId;
    }

    /**
     * 设置编号
     *
     * @param searchWordId 编号
     */
    public void setSearchWordId(Integer searchWordId) {
        this.searchWordId = searchWordId;
    }

    /**
     * 获取关键词
     *
     * @return keyword - 关键词
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * 设置关键词
     *
     * @param keyword 关键词
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * 获取关键词类型
     *
     * @return wordtype - 关键词类型
     */
    public Boolean getWordtype() {
        return wordtype;
    }

    /**
     * 设置关键词类型
     *
     * @param wordtype 关键词类型
     */
    public void setWordtype(Boolean wordtype) {
        this.wordtype = wordtype;
    }

    /**
     * 获取模板ID
     *
     * @return appmodel_id - 模板ID
     */
    public String getAppmodelId() {
        return appmodelId;
    }

    /**
     * 设置模板ID
     *
     * @param appmodelId 模板ID
     */
    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }

    @Override
    public String toString() {
        return "SearchWord{" +
                "searchWordId=" + searchWordId +
                ", keyword='" + keyword + '\'' +
                ", wordtype=" + wordtype +
                ", appmodelId='" + appmodelId + '\'' +
                '}';
    }
}