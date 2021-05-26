package com.medusa.basemall.promotion.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Created by psy on 2018/05/30.
 */
@Table(name = "t_group")
public class Group {

    @ApiModelProperty(value="团编号")
    @Id
    @Column(name = "group_id")
    @GeneratedValue(generator = "JDBC")
    private Integer groupId;

    @ApiModelProperty(value="用户ID")
    @Column(name = "wxuser_id")
    private Long wxuserId;

    @ApiModelProperty(value="开团用户ID")
    @Column(name = "activity_group_id")
    private Integer activityGroupId;

    @ApiModelProperty(value="开团时间")
    @Column(name = "create_time")
    private String createTime;

    @ApiModelProperty(value="拼团结束时间")
    @Column(name = "end_time")
    private String endTime;

    @ApiModelProperty(value="成团状态")
    @Column(name = "group_state")
    private Integer groupState;

    @ApiModelProperty(value="产品ID")
    @Column(name = "product_id")
    private Long productId;

    @ApiModelProperty(value="限购人数")
    @Column(name = "limit_num")
    private Integer limitNum;

    @ApiModelProperty(value="删除状态")
    @Column(name = "delete_state")
    private Boolean deleteState;

    @ApiModelProperty(value="模板ID")
    @Column(name = "appmodel_id")
    private String appmodelId;

    /**
     * 获取编号
     *
     * @return group_id - 编号
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * 设置编号
     *
     * @param groupId 编号
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
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
     * 获取开团用户ID
     *
     * @return activity_group_id - 开团用户ID
     */
    public Integer getActivityGroupId() {
        return activityGroupId;
    }

    /**
     * 设置开团用户ID
     *
     * @param activityGroupId 开团用户ID
     */
    public void setActivityGroupId(Integer activityGroupId) {
        this.activityGroupId = activityGroupId;
    }

    /**
     * 获取开团时间
     *
     * @return create_time - 开团时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置开团时间
     *
     * @param createTime 开团时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取拼团结束时间
     *
     * @return end_time - 拼团结束时间
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * 设置拼团结束时间
     *
     * @param endTime 拼团结束时间
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取成团状态
     *
     * @return group_state - 成团状态
     */
    public Integer getGroupState() {
        return groupState;
    }

    /**
     * 设置成团状态
     *
     * @param groupState 成团状态
     */
    public void setGroupState(Integer groupState) {
        this.groupState = groupState;
    }

    /**
     * 获取产品ID
     *
     * @return product_id - 产品ID
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * 设置产品ID
     *
     * @param productId 产品ID
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * 获取限购人数
     *
     * @return limit_num - 限购人数
     */
    public Integer getLimitNum() {
        return limitNum;
    }

    /**
     * 设置限购人数
     *
     * @param limitNum 限购人数
     */
    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }

    /**
     * 获取删除状态
     *
     * @return delete_state - 删除状态
     */
    public Boolean getDeleteState() {
        return deleteState;
    }

    /**
     * 设置删除状态
     *
     * @param deleteState 删除状态
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
        return "Group{" +
                "groupId=" + groupId +
                ", wxuserId=" + wxuserId +
                ", activityGroupId=" + activityGroupId +
                ", createTime='" + createTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", groupState=" + groupState +
                ", productId=" + productId +
                ", limitNum=" + limitNum +
                ", deleteState=" + deleteState +
                ", appmodelId='" + appmodelId + '\'' +
                '}';
    }
}