package com.medusa.basemall.article.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Created by wx on 2018/06/07.
 */
public class LeaveWordVo {

    @ApiModelProperty(value = "当前页数")
    private Integer pageNum;

    @ApiModelProperty(value = "每页数量")
    private Integer pageSize;

    @ApiModelProperty(value = "用户id")
    private Long wxuserId;

    @ApiModelProperty(value = "文章id")
    private String articleId;

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

    public Long getWxuserId() {
        return wxuserId;
    }

    public void setWxuserId(Long wxuserId) {
        this.wxuserId = wxuserId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    @Override
    public String toString() {
        return "LeaveWordVo{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", wxuserId=" + wxuserId +
                ", articleId='" + articleId + '\'' +
                '}';
    }
}
