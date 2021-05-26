package com.medusa.basemall.article.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Created by wx on 2018/06/07.
 */
public class LeaveWordLaudVo {

    @ApiModelProperty(value = "用户id")
    private Long wxuserId;

    @ApiModelProperty(value = "留言id")
    private String leaveWordId;

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

    @Override
    public String toString() {
        return "LeaveWordLaudVo{" +
                "wxuserId=" + wxuserId +
                ", leaveWordId='" + leaveWordId + '\'' +
                '}';
    }
}
