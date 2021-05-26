package com.medusa.basemall.promotion.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Created by psy on 2018/05/30.
 */
@Table(name = "t_group_member")
public class GroupMember {

    @ApiModelProperty(value="团成员编号")
    @Id
    @Column(name = "group_member_id")
    @GeneratedValue(generator = "JDBC")
    private Integer groupMemberId;

    @ApiModelProperty(value="用户ID")
    @Column(name = "wxuser_id")
    private Long wxuserId;

    @ApiModelProperty(value="团ID")
    @Column(name = "group_id")
    private Integer groupId;

    @ApiModelProperty(value="订单ID")
    @Column(name = "order_id")
    private Long orderId;

    @ApiModelProperty(value="参团时间")
    @Column(name = "create_time")
    private String createTime;

    @ApiModelProperty(value="参团人类型（0-团长 1-团员）")
    @Column(name = "member_type")
    private Boolean memberType;

    @ApiModelProperty(value="昵称")
    @Column(name = "nick_name")
    private String nickName;

    @ApiModelProperty(value="头像")
    @Column(name = "avatar_url")
    private String avatarUrl;

    @ApiModelProperty(value="模板ID")
    @Column(name = "appmodel_id")
    private String appmodelId;

    @ApiModelProperty(value="删除标志")
    @Column(name = "delete_state")
    private Boolean deleteState;

    /**
     * 获取编号
     *
     * @return group_member_id - 编号
     */
    public Integer getGroupMemberId() {
        return groupMemberId;
    }

    /**
     * 设置编号
     *
     * @param groupMemberId 编号
     */
    public void setGroupMemberId(Integer groupMemberId) {
        this.groupMemberId = groupMemberId;
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
     * 获取团ID
     *
     * @return group_id - 团ID
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * 设置团ID
     *
     * @param groupId 团ID
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * 获取订单ID
     *
     * @return order_id - 订单ID
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置订单ID
     *
     * @param orderId 订单ID
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取参团时间
     *
     * @return create_time - 参团时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置参团时间
     *
     * @param createTime 参团时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取参团人类型
     *
     * @return member_type - 参团人类型
     */
    public Boolean getMemberType() {
        return memberType;
    }

    /**
     * 设置参团人类型
     *
     * @param memberType 参团人类型
     */
    public void setMemberType(Boolean memberType) {
        this.memberType = memberType;
    }

    /**
     * 获取昵称
     *
     * @return nick_name - 昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置昵称
     *
     * @param nickName 昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 获取头像
     *
     * @return avatar_url - 头像
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * 设置头像
     *
     * @param avatarUrl 头像
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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

    @Override
    public String toString() {
        return "GroupMember{" +
                "groupMemberId=" + groupMemberId +
                ", wxuserId=" + wxuserId +
                ", groupId=" + groupId +
                ", orderId=" + orderId +
                ", createTime='" + createTime + '\'' +
                ", memberType=" + memberType +
                ", nickName='" + nickName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", appmodelId='" + appmodelId + '\'' +
                ", deleteState=" + deleteState +
                '}';
    }
}