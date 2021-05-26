package com.medusa.basemall.article.entity;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;

/**
 * @author Created by wx on 2018/06/07.
 */
public class LeaveWordLaud {
    @Id
    @ApiModelProperty(value = "记录留言点赞id")
    private String leaveWordLaudId;

    @ApiModelProperty(value = "用户id")
    private Long wxuserId;

    @ApiModelProperty(value = "留言id")
    private String leaveWordId;

    @ApiModelProperty(value = "留言是否点赞状态")
    private Boolean laudOrNot;

    public String getLeaveWordLaudId() {
        return leaveWordLaudId;
    }

    public void setLeaveWordLaudId(String leaveWordLaudId) {
        this.leaveWordLaudId = leaveWordLaudId;
    }

    public Long getWxuserId() {
        return wxuserId;
    }

    public void setWxuserId(Long wxuserId) {
        this.wxuserId = wxuserId;
    }

    public String getLeaveWordId() {
        return leaveWordId;
    }

    public void setLeaveWordId(String leaveWordId) {
        this.leaveWordId = leaveWordId;
    }

    public Boolean getLaudOrNot() {
        return laudOrNot;
    }

    public void setLaudOrNot(Boolean laudOrNot) {
        this.laudOrNot = laudOrNot;
    }

    @Override
    public String toString() {
        return "LeaveWordLaud{" +
                "leaveWordLaudId='" + leaveWordLaudId + '\'' +
                ", wxuserId=" + wxuserId +
                ", leaveWordId='" + leaveWordId + '\'' +
                ", laudOrNot=" + laudOrNot +
                '}';
    }
}
