package com.medusa.basemall.order.vo;

import io.swagger.annotations.ApiModelProperty;

public class OrderSurveyVo {

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
}
