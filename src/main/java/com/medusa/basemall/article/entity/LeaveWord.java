package com.medusa.basemall.article.entity;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;

/**
 * @author Created by wx on 2018/06/07.
 */
public class LeaveWord {
    @Id
    @ApiModelProperty(value = "留言id")
    private String leaveWordId;

    @ApiModelProperty(value = "文章id")
    private String articleId;

    @ApiModelProperty(value = "用户id")
    private Long wxuserId;

    @ApiModelProperty(value = "用户头像")
    private String wxuserImg;

    @ApiModelProperty(value = "用户名称")
    private String wxuserName;

    @ApiModelProperty(value = "留言时间")
    private String leaveTime;

    @ApiModelProperty(value = "留言信息")
    private String leaveInfo;

    @ApiModelProperty(value = "回复时间")
    private String replyTime;

    @ApiModelProperty(value = "回复内容")
    private String replyInfo;

    @ApiModelProperty(value = "回复状态(0-未回复 1-已回复)")
    private Integer replyType;

    @ApiModelProperty(value = "是否精选 0-普通 1-精选")
    private Integer choiceness;

    @ApiModelProperty(value = "置顶状态(0未置顶- 1-已置顶)")
    private Integer sortType;

    @ApiModelProperty(value = "排序值")
    private Long sortTime;

    @ApiModelProperty(value = "模板Id")
    private String appmodelId;

    @ApiModelProperty(value = "当前用户留言点赞状态")
    private Boolean laudOrNot;

    @ApiModelProperty(value = "点赞数")
    private Integer laud;

    @ApiModelProperty(value = "所属文章对象")
    private Article article;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public Long getWxuserId() {
        return wxuserId;
    }

    public void setWxuserId(Long wxuserId) {
        this.wxuserId = wxuserId;
    }

    public String getWxuserImg() {
        return wxuserImg;
    }

    public void setWxuserImg(String wxuserImg) {
        this.wxuserImg = wxuserImg;
    }

    public String getWxuserName() {
        return wxuserName;
    }

    public void setWxuserName(String wxuserName) {
        this.wxuserName = wxuserName;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getLeaveInfo() {
        return leaveInfo;
    }

    public void setLeaveInfo(String leaveInfo) {
        this.leaveInfo = leaveInfo;
    }

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

    public Integer getChoiceness() {
        return choiceness;
    }

    public void setChoiceness(Integer choiceness) {
        this.choiceness = choiceness;
    }

    public String getLeaveWordId() {
        return leaveWordId;
    }

    public void setLeaveWordId(String leaveWordId) {
        this.leaveWordId = leaveWordId;
    }

    public String getAppmodelId() {
        return appmodelId;
    }

    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }

    public Long getSortTime() {
        return sortTime;
    }

    public void setSortTime(Long sortTime) {
        this.sortTime = sortTime;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }

    public String getReplyInfo() {
        return replyInfo;
    }

    public void setReplyInfo(String replyInfo) {
        this.replyInfo = replyInfo;
    }

    public Boolean getLaudOrNot() {
        return laudOrNot;
    }

    public void setLaudOrNot(Boolean laudOrNot) {
        this.laudOrNot = laudOrNot;
    }

    public Integer getReplyType() {
        return replyType;
    }

    public void setReplyType(Integer replyType) {
        this.replyType = replyType;
    }

    public Integer getLaud() {
        return laud;
    }

    public void setLaud(Integer laud) {
        this.laud = laud;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @Override
    public String toString() {
        return "LeaveWord{" +
                "leaveWordId='" + leaveWordId + '\'' +
                ", articleId='" + articleId + '\'' +
                ", wxuserId=" + wxuserId +
                ", wxuserImg='" + wxuserImg + '\'' +
                ", wxuserName='" + wxuserName + '\'' +
                ", leaveTime='" + leaveTime + '\'' +
                ", leaveInfo='" + leaveInfo + '\'' +
                ", replyTime='" + replyTime + '\'' +
                ", choiceness=" + choiceness +
                ", sortTime='" + sortTime + '\'' +
                ", appmodelId='" + appmodelId + '\'' +
                '}';
    }
}
