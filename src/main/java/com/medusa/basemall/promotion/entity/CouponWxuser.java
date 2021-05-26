package com.medusa.basemall.promotion.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Created by psy on 2018/05/30.
 */
@Table(name = "t_coupon_wxuser")
public class CouponWxuser {

    @Id
    @Column(name = "wxuser_coupon_id")
    @ApiModelProperty(value="用户领取的优惠券编号(新增不需要)")
    private Integer wxuserCouponId;

    @Column(name = "wxuser_id")
    @ApiModelProperty(value="用户ID", required = true)
    private Long wxuserId;

    @Column(name = "coupon_id")
    @ApiModelProperty(value="优惠券ID", required = true)
    private Integer couponId;

    @Column(name = "flag")
    @ApiModelProperty(value="使用状态（0:未使用,1:已使用,2:已失效）(不需要)")
    private Integer flag;

    @Column(name = "create_time")
    @ApiModelProperty(value="领取优惠券时间(不需要)")
    private String createTime;

    @Column(name = "use_time")
    @ApiModelProperty(value="使用优惠券时间(不需要)")
    private String useTime;

    @Column(name = "appmodel_id")
    @ApiModelProperty(value="模板ID", required = true)
    private String appmodelId;

    @Column(name = "activity_coupon_id")
    @ApiModelProperty(value="优惠活动ID")
    private Integer activityCouponId;

    /**
     * 对应的优惠券
     */
    @ApiModelProperty(value="对应的优惠券对象")
    private Coupon coupon;

    /**
     * 对应的优惠券
     */
    @ApiModelProperty(value="对应的优惠券活动")
    private ActivityCoupon activityCoupon;


    /**
     * 获取编号
     *
     * @return wxuser_coupon_id - 编号
     */
    public Integer getWxuserCouponId() {
        return wxuserCouponId;
    }

    /**
     * 设置编号
     *
     * @param wxuserCouponId 编号
     */
    public void setWxuserCouponId(Integer wxuserCouponId) {
        this.wxuserCouponId = wxuserCouponId;
    }

    /**
     * 获取用户ID
     *
     * @return wxuser_id - 用户ID
     */
    public Long getWxuserId() {
        return wxuserId;
    }

    /**
     * 设置用户ID
     *
     * @param wxuserId 用户ID
     */
    public void setWxuserId(Long wxuserId) {
        this.wxuserId = wxuserId;
    }

    /**
     * 获取优惠券ID
     *
     * @return coupon_id - 优惠券ID
     */
    public Integer getCouponId() {
        return couponId;
    }

    /**
     * 设置优惠券ID
     *
     * @param couponId 优惠券ID
     */
    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    /**
     * 获取使用状态
     *
     * @return flag - 使用状态
     */
    public Integer getFlag() {
        return flag;
    }

    /**
     * 设置使用状态
     *
     * @param flag 使用状态
     */
    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    /**
     * 获取领取优惠券时间
     *
     * @return create_time - 领取优惠券时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置领取优惠券时间
     *
     * @param createTime 领取优惠券时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取使用优惠券时间
     *
     * @return use_time - 使用优惠券时间
     */
    public String getUseTime() {
        return useTime;
    }

    /**
     * 设置使用优惠券时间
     *
     * @param useTime 使用优惠券时间
     */
    public void setUseTime(String useTime) {
        this.useTime = useTime;
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

    /**
     * 获取优惠活动ID
     *
     * @return activity_coupon_id - 优惠活动ID
     */
    public Integer getActivityCouponId() {
        return activityCouponId;
    }

    /**
     * 设置优惠活动ID
     *
     * @param activityCouponId 优惠活动ID
     */
    public void setActivityCouponId(Integer activityCouponId) {
        this.activityCouponId = activityCouponId;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public ActivityCoupon getActivityCoupon() {
        return activityCoupon;
    }

    public void setActivityCoupon(ActivityCoupon activityCoupon) {
        this.activityCoupon = activityCoupon;
    }

    @Override
    public String toString() {
        return "CouponWxuser{" +
                "wxuserCouponId=" + wxuserCouponId +
                ", wxuserId=" + wxuserId +
                ", couponId=" + couponId +
                ", flag=" + flag +
                ", createTime='" + createTime + '\'' +
                ", useTime='" + useTime + '\'' +
                ", appmodelId='" + appmodelId + '\'' +
                ", activityCouponId=" + activityCouponId +
                ", coupon=" + coupon +
                ", activityCoupon=" + activityCoupon +
                '}';
    }
}