package com.medusa.basemall.user.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_member_group_category")
public class MemberGroupCategory {
    @Id
    @Column(name = "group_category_id")
    @ApiModelProperty(value = "会员分组中间表id")
    private Integer groupCategoryId;

    /**
     * 组id
     */
    @Column(name = "group_id")
    @ApiModelProperty(value = "组id")
    private Integer groupId;

    @ApiModelProperty(value = "组名"  )
    private String groupName;
    /**
     * 会员id
     */
    @Column(name = "member_id")
    @ApiModelProperty(value = "会员id"  )
    private Long memberId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间"  )
    private String createTime;

    /**
     * 模板id
     */
    @Column(name = "appmodel_id")
    @ApiModelProperty(value = "模板id"  )
    private String appmodelId;


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * @return group_category_id
     */
    public Integer getGroupCategoryId() {
        return groupCategoryId;
    }

    /**
     * @param groupCategoryId
     */
    public void setGroupCategoryId(Integer groupCategoryId) {
        this.groupCategoryId = groupCategoryId;
    }

    /**
     * 获取组id
     *
     * @return group_id - 组id
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * 设置组id
     *
     * @param groupId 组id
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * 获取会员id
     *
     * @return member_id - 会员id
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * 设置会员id
     *
     * @param memberId 会员id
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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
     * 获取模板id
     *
     * @return appmodel_id - 模板id
     */
    public String getAppmodelId() {
        return appmodelId;
    }

    /**
     * 设置模板id
     *
     * @param appmodelId 模板id
     */
    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }
}