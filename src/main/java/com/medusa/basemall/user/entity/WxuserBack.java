package com.medusa.basemall.user.entity;


import io.swagger.annotations.ApiModelProperty;

public class WxuserBack extends Wxuser{

    @ApiModelProperty(value = "" )
    private Integer nopay;
    @ApiModelProperty(value = "" )
    private Integer payed;
    @ApiModelProperty(value = "" )
    private Integer success;
    @ApiModelProperty(value = "" )
    private Integer close;
    @ApiModelProperty(value = "" )
    private Double totalFee;
    @ApiModelProperty(value = "" )
    private Integer tradeTimes;

    public Integer getNopay() {
        return nopay;
    }

    public void setNopay(Integer nopay) {
        this.nopay = nopay;
    }

    public Integer getPayed() {
        return payed;
    }

    public void setPayed(Integer payed) {
        this.payed = payed;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getClose() {
        return close;
    }

    public void setClose(Integer close) {
        this.close = close;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getTradeTimes() {
        return tradeTimes;
    }

    public void setTradeTimes(Integer tradeTimes) {
        this.tradeTimes = tradeTimes;
    }
}
