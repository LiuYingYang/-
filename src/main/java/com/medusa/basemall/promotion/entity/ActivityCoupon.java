package com.medusa.basemall.promotion.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Created by psy on 2018/05/30.
 */
@Table(name = "t_activity_coupon")
public class ActivityCoupon {

    @Id
    @Column(name = "activity_coupon_id")
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value="优惠券活动编号")
    private Integer activityCouponId;

    @Column(name = "activity_coupon_name")
    @ApiModelProperty(value="活动名称")
    private String activityCouponName;

    @Column(name = "activity_remark")
    @ApiModelProperty(value="活动备注")
    private String activityRemark;

    @Column(name = "activity_model")
    @ApiModelProperty(value="活动模板")
    private String activityModel;

    @Column(name = "activity_img")
    @ApiModelProperty(value="活动海报")
    private String activityImg;

    @Column(name = "overlay_state")
    @ApiModelProperty(value="是否叠加（0不叠加，1叠加）")
    private Boolean overlayState;

    @Column(name = "now_state")
    @ApiModelProperty(value="活动当前状态(0已结束，1未开始，2进行中)")
    private Integer nowState;

    @Column(name = "full_state")
    @ApiModelProperty(value="是否适用与所有商品的状态（0指定商品，1所有商品")
    private Boolean fullState;

    @Column(name = "create_time")
    @ApiModelProperty(value="创建时间")
    private String createTime;

    @Column(name = "upate_time")
    @ApiModelProperty(value="更新时间")
    private String upateTime;

    @Column(name = "start_date")
    @ApiModelProperty(value="活动开始时间")
    private String startDate;

    @Column(name = "end_date")
    @ApiModelProperty(value="活动结束时间")
    private String endDate;

    @Column(name = "delete_state")
    @ApiModelProperty(value="删除标志")
    private Boolean deleteState;

    @Column(name = "appmodel_id")
    @ApiModelProperty(value="用户模板ID")
    private String appmodelId;

    /**
     * 获取编号
     *
     * @return activity_coupon_id - 编号
     */
    public Integer getActivityCouponId() {
        return activityCouponId;
    }

    /**
     * 设置编号
     *
     * @param activityCouponId 编号
     */
    public void setActivityCouponId(Integer activityCouponId) {
        this.activityCouponId = activityCouponId;
    }

    /**
     * 获取活动名称
     *
     * @return activity_coupon_name - 活动名称
     */
    public String getActivityCouponName() {
        return activityCouponName;
    }

    /**
     * 设置活动名称
     *
     * @param activityCouponName 活动名称
     */
    public void setActivityCouponName(String activityCouponName) {
        this.activityCouponName = activityCouponName;
    }

    /**
     * 获取活动备注
     *
     * @return activity_remark - 活动备注
     */
    public String getActivityRemark() {
        return activityRemark;
    }

    /**
     * 设置活动备注
     *
     * @param activityRemark 活动备注
     */
    public void setActivityRemark(String activityRemark) {
        this.activityRemark = activityRemark;
    }

    /**
     * 获取活动模板
     *
     * @return activity_model - 活动模板
     */
    public String getActivityModel() {
        return activityModel;
    }

    /**
     * 设置活动模板
     *
     * @param activityModel 活动模板
     */
    public void setActivityModel(String activityModel) {
        this.activityModel = activityModel;
    }

    /**
     * 获取活动海报
     *
     * @return activity_img - 活动海报
     */
    public String getActivityImg() {
        return activityImg;
    }

    /**
     * 设置活动海报
     *
     * @param activityImg 活动海报
     */
    public void setActivityImg(String activityImg) {
        this.activityImg = activityImg;
    }

    /**
     * 获取是否叠加（0不叠加，1叠加）
     *
     * @return overlay_state - 是否叠加（0不叠加，1叠加）
     */
    public Boolean getOverlayState() {
        return overlayState;
    }

    /**
     * 设置是否叠加（0不叠加，1叠加）
     *
     * @param overlayState 是否叠加（0不叠加，1叠加）
     */
    public void setOverlayState(Boolean overlayState) {
        this.overlayState = overlayState;
    }


    /**
     * 获取活动当前状态(0未开始，1，进行中，2已结束)
     *
     * @return now_state - 活动当前状态(0未开始，1，进行中，2已结束)
     */
    public Integer getNowState() {
        return nowState;
    }

    /**
     * 设置活动当前状态(0未开始，1，进行中，2已结束)
     *
     * @param nowState 活动当前状态(0未开始，1，进行中，2已结束)
     */
    public void setNowState(Integer nowState) {
        this.nowState = nowState;
    }

    /**
     * 获取是否适用与所有商品的状态（0指定商品，1所有商品）
     *
     * @return full_state - 是否适用与所有商品的状态（0指定商品，1所有商品）
     */
    public Boolean getFullState() {
        return fullState;
    }

    /**
     * 设置是否适用与所有商品的状态（0指定商品，1所有商品）
     *
     * @param fullState 是否适用与所有商品的状态（0指定商品，1所有商品）
     */
    public void setFullState(Boolean fullState) {
        this.fullState = fullState;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return upate_time - 更新时间
     */
    public String getUpateTime() {
        return upateTime;
    }

    /**
     * 设置更新时间
     *
     * @param upateTime 更新时间
     */
    public void setUpateTime(String upateTime) {
        this.upateTime = upateTime;
    }

    /**
     * 获取活动开始时间
     *
     * @return start_date - 活动开始时间
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * 设置活动开始时间
     *
     * @param startDate 活动开始时间
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * 获取活动结束时间
     *
     * @return end_date - 活动结束时间
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * 设置活动结束时间
     *
     * @param endDate 活动结束时间
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * 获取删除标志
     *
     * @return delete_state - 删除标志
     */
    public Boolean getDeleteState() {
        return deleteState;
    }

    /**
     * 设置删除标志
     *
     * @param deleteState 删除标志
     */
    public void setDeleteState(Boolean deleteState) {
        this.deleteState = deleteState;
    }

    /**
     * 获取用户模板ID
     *
     * @return appmodel_id - 用户模板ID
     */
    public String getAppmodelId() {
        return appmodelId;
    }

    /**
     * 设置用户模板ID
     *
     * @param appmodelId 用户模板ID
     */
    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }

    @Override
    public String toString() {
        return "ActivityCoupon{" +
                "activityCouponId=" + activityCouponId +
                ", activityCouponName='" + activityCouponName + '\'' +
                ", activityRemark='" + activityRemark + '\'' +
                ", activityModel='" + activityModel + '\'' +
                ", activityImg='" + activityImg + '\'' +
                ", overlayState=" + overlayState +
                ", nowState=" + nowState +
                ", fullState=" + fullState +
                ", createTime='" + createTime + '\'' +
                ", upateTime='" + upateTime + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", deleteState=" + deleteState +
                ", appmodelId='" + appmodelId + '\'' +
                '}';
    }
}