package com.medusa.basemall.article.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Created by wx on 2018/06/07.
 */
public class ReplyVo {

    @ApiModelProperty(value = "留言id")
    private String leaveWordId;

    @ApiModelProperty(value = "回复内容")
    private String replyInfo;

    @ApiModelProperty(value = "精选状态")
    private Integer choiceness;

    @ApiModelProperty(value = "置顶状态")
    private Integer sortType;

    public String getLeaveWordId() {
        return leaveWordId;
    }

    public void setLeaveWordId(String leaveWordId) {
        this.leaveWordId = leaveWordId;
    }

    public String getReplyInfo() {
        return replyInfo;
    }

    public void setReplyInfo(String replyInfo) {
        this.replyInfo = replyInfo;
    }

    public Integer getChoiceness() {
        return choiceness;
    }

    public void setChoiceness(Integer choiceness) {
        this.choiceness = choiceness;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }

    @Override
    public String toString() {
        return "ReplyVo{" +
                "leaveWordId='" + leaveWordId + '\'' +
                ", replyInfo='" + replyInfo + '\'' +
                ", choiceness=" + choiceness +
                ", sortType=" + sortType +
                '}';
    }
}
