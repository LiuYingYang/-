package com.medusa.basemall.user.entity;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Transient;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_wxuser_group_category")
public class WxuserGroupCategory {
    /**
     * 用户分组id
     */
    @Id
    @Column(name = "wx_group_category_id")
    @ApiModelProperty(value = "用户分组id")
    private Integer wxGroupCategoryId;

    /**
     * 组id
     */
    @Column(name = "wxuser_group_id")
    @ApiModelProperty(value = "用户组id")
    private Integer wxuserGroupId;

    /**
     * 用户id
     */
    @Column(name = "wxuser_id")
    @ApiModelProperty(value = "用户id"  )
    private Long wxuserId;

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

    @Transient
    @ApiModelProperty(value = "分组名"  )
    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * 获取分组id
     *
     * @return wx_group_category_id - 分组id
     */
    public Integer getWxGroupCategoryId() {
        return wxGroupCategoryId;
    }

    /**
     * 设置分组id
     *
     * @param wxGroupCategoryId 分组id
     */
    public void setWxGroupCategoryId(Integer wxGroupCategoryId) {
        this.wxGroupCategoryId = wxGroupCategoryId;
    }

    /**
     * 获取组id
     *
     * @return wxuser_group_id - 组id
     */
    public Integer getWxuserGroupId() {
        return wxuserGroupId;
    }

    /**
     * 设置组id
     *
     * @param wxuserGroupId 组id
     */
    public void setWxuserGroupId(Integer wxuserGroupId) {
        this.wxuserGroupId = wxuserGroupId;
    }

    /**
     * 获取会员id
     *
     * @return wxuser_id - 会员id
     */
    public Long getWxuserId() {
        return wxuserId;
    }

    /**
     * 设置会员id
     *
     * @param wxuserId 会员id
     */
    public void setWxuserId(Long wxuserId) {
        this.wxuserId = wxuserId;
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