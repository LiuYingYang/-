package com.medusa.basemall.integral.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Created by wx on 2018/06/06.
 */
public class PrizeSurveyVo {

    @ApiModelProperty(value = "模板id", required = true)
    private String appmodelId;

    @ApiModelProperty(value = "开始日期(查询一段时间内营销概况传)")
    private String startDate;

    @ApiModelProperty(value = "结束日期(查询一段时间内营销概况传)")
    private String endDate;

    @ApiModelProperty(value = "当前时间", hidden = true)
    private String realTime;

    public String getAppmodelId() {
        return appmodelId;
    }

    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRealTime() {
        return realTime;
    }

    public void setRealTime(String realTime) {
        this.realTime = realTime;
    }

    @Override
    public String toString() {
        return "PrizeSurveyVo{" +
                "appmodelId='" + appmodelId + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", realTime='" + realTime + '\'' +
                '}';
    }
}
