package com.medusa.basemall.integral.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author Created by wx on 2018/06/06.
 */
public class PrizeSurvey {

    @ApiModelProperty(value = "成交订单总数 ")
    private Integer allOverOrder;

    @ApiModelProperty(value = "成交总额")
    private Integer allIntegral;

    @ApiModelProperty(value = "优惠券订单总数")
    private Integer allOverCouponOrder;

    @ApiModelProperty(value = "积分商品订单总数")
    private Integer allOverProductOrder;

    @ApiModelProperty(value = "今日订单数")
    private Integer todayOverOrder;

    @ApiModelProperty(value = "待发货订单")
    private Integer todayWaitOrder;

    @ApiModelProperty(value = "优惠券订单")
    private Integer todayCouponOrder;

    @ApiModelProperty(value = "今日成交额")
    private Integer todayIntegral;

    @ApiModelProperty(value = "库存警告数量")
    private Integer urgentPrizeNum;

    @ApiModelProperty(value = "当前时间")
    private String nowTime;

    @ApiModelProperty(value = "库存警告商品")
    private List<Prize> urgentPrizes;

    public Integer getAllOverOrder() {
        return allOverOrder;
    }

    public void setAllOverOrder(Integer allOverOrder) {
        this.allOverOrder = allOverOrder;
    }

    public Integer getAllIntegral() {
        return allIntegral;
    }

    public void setAllIntegral(Integer allIntegral) {
        this.allIntegral = allIntegral;
    }

    public Integer getAllOverCouponOrder() {
        return allOverCouponOrder;
    }

    public void setAllOverCouponOrder(Integer allOverCouponOrder) {
        this.allOverCouponOrder = allOverCouponOrder;
    }

    public Integer getAllOverProductOrder() {
        return allOverProductOrder;
    }

    public void setAllOverProductOrder(Integer allOverProductOrder) {
        this.allOverProductOrder = allOverProductOrder;
    }

    public Integer getTodayOverOrder() {
        return todayOverOrder;
    }

    public void setTodayOverOrder(Integer todayOverOrder) {
        this.todayOverOrder = todayOverOrder;
    }

    public Integer getTodayWaitOrder() {
        return todayWaitOrder;
    }

    public void setTodayWaitOrder(Integer todayWaitOrder) {
        this.todayWaitOrder = todayWaitOrder;
    }

    public Integer getTodayCouponOrder() {
        return todayCouponOrder;
    }

    public void setTodayCouponOrder(Integer todayCouponOrder) {
        this.todayCouponOrder = todayCouponOrder;
    }

    public Integer getTodayIntegral() {
        return todayIntegral;
    }

    public void setTodayIntegral(Integer todayIntegral) {
        this.todayIntegral = todayIntegral;
    }

    public List<Prize> getUrgentPrizes() {
        return urgentPrizes;
    }

    public void setUrgentPrizes(List<Prize> urgentPrizes) {
        this.urgentPrizes = urgentPrizes;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public Integer getUrgentPrizeNum() {
        return urgentPrizeNum;
    }

    public void setUrgentPrizeNum(Integer urgentPrizeNum) {
        this.urgentPrizeNum = urgentPrizeNum;
    }

    @Override
    public String toString() {
        return "PrizeSurvey{" +
                "allOverOrder=" + allOverOrder +
                ", allIntegral=" + allIntegral +
                ", allOverCouponOrder=" + allOverCouponOrder +
                ", allOverProductOrder=" + allOverProductOrder +
                ", todayOverOrder=" + todayOverOrder +
                ", todayWaitOrder=" + todayWaitOrder +
                ", todayCouponOrder=" + todayCouponOrder +
                ", todayIntegral=" + todayIntegral +
                ", urgentPrizeNum=" + urgentPrizeNum +
                ", nowTime='" + nowTime + '\'' +
                ", urgentPrizes=" + urgentPrizes +
                '}';
    }
}
