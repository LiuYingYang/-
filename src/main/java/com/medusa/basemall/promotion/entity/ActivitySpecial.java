package com.medusa.basemall.promotion.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Created by psy on 2018/05/30.
 */
@Table(name = "t_activity_special")
public class ActivitySpecial {

    @ApiModelProperty(value="特价活动编号")
    @Id
    @Column(name = "activity_special_id")
    @GeneratedValue(generator = "JDBC")
    private Integer activitySpecialId;

    @ApiModelProperty(value="活动名称")
    @Column(name = "activity_special_name")
    private String activitySpecialName;

    @ApiModelProperty(value="活动备注")
    @Column(name = "activity_remark")
    private String activityRemark;

    @ApiModelProperty(value="活动海报")
    @Column(name = "activity_img")
    private String activityImg;

    @ApiModelProperty(value="是否叠加（0不叠加，1叠加）")
    @Column(name = "overlay_state")
    private Boolean overlayState;

    @ApiModelProperty(value="活动当前状态 同优惠券")
    @Column(name = "now_state")
    private Integer nowState;

    @ApiModelProperty(value="创建时间")
    @Column(name = "create_time")
    private String createTime;

    @ApiModelProperty(value="更新时间")
    @Column(name = "update_time")
    private String updateTime;

    @ApiModelProperty(value="活动开始时间")
    @Column(name = "start_date")
    private String startDate;

    @ApiModelProperty(value="活动结束时间")
    @Column(name = "end_date")
    private String endDate;

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
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

    /**
     * 删除标志
     */
    @Column(name = "delete_state")
    private Boolean deleteState;

    /**
     * 用户模板ID
     */
    @Column(name = "appmodel_id")
    private String appmodelId;

    /**
     * 获取编号
     *
     * @return activity_special_id - 编号
     */
    public Integer getActivitySpecialId() {
        return activitySpecialId;
    }

    /**
     * 设置编号
     *
     * @param activitySpecialId 编号
     */
    public void setActivitySpecialId(Integer activitySpecialId) {
        this.activitySpecialId = activitySpecialId;
    }

    /**
     * 获取活动名称
     *
     * @return activity_special_name - 活动名称
     */
    public String getActivitySpecialName() {
        return activitySpecialName;
    }

    /**
     * 设置活动名称
     *
     * @param activitySpecialName 活动名称
     */
    public void setActivitySpecialName(String activitySpecialName) {
        this.activitySpecialName = activitySpecialName;
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
     * 获取活动当前状态
     *
     * @return now_state - 活动当前状态
     */
    public Integer getNowState() {
        return nowState;
    }

    /**
     * 设置活动当前状态
     *
     * @param nowState 活动当前状态
     */
    public void setNowState(Integer nowState) {
        this.nowState = nowState;
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
        return "ActivitySpecial{" +
                "activitySpecialId=" + activitySpecialId +
                ", activitySpecialName='" + activitySpecialName + '\'' +
                ", activityRemark='" + activityRemark + '\'' +
                ", activityImg='" + activityImg + '\'' +
                ", overlayState=" + overlayState +
                ", nowState=" + nowState +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", deleteState=" + deleteState +
                ", appmodelId='" + appmodelId + '\'' +
                '}';
    }
}