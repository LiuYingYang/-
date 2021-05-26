package com.medusa.basemall.user.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_member_group")
public class MemberGroup {
    /**
     * 组别id
     */
    @Id
    @Column(name = "group_id")
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "组id")
    private Integer groupId;

    /**
     * 组名
     */
    @Column(name = "group_name")
    @ApiModelProperty(value = "组名"  )
    private String groupName;

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

    /**
     * 获取组别id
     *
     * @return group_id - 组别id
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * 设置组别id
     *
     * @param groupId 组别id
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * 获取组名
     *
     * @return group_name - 组名
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * 设置组名
     *
     * @param groupName 组名
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
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