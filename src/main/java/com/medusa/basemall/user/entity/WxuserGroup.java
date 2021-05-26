package com.medusa.basemall.user.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_wxuser_group")
public class WxuserGroup {
    /**
     * 组id
     */
    @Id
    @Column(name = "wxuser_group_id")
    @ApiModelProperty(value = "组id")
    private Integer wxuserGroupId;

    /**
     * 组名称
     */
    @Column(name = "wx_group_name")
    @ApiModelProperty(value = "组名称"  )
    private String wxGroupName;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间"  )
    private String createTime;

    @Column(name = "appmodel_id")
    @ApiModelProperty(value = "商家appmodelId"  )
    private String appmodelId;

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
     * 获取组名称
     *
     * @return wx_group_name - 组名称
     */
    public String getWxGroupName() {
        return wxGroupName;
    }

    /**
     * 设置组名称
     *
     * @param wxGroupName 组名称
     */
    public void setWxGroupName(String wxGroupName) {
        this.wxGroupName = wxGroupName;
    }

    /**
     * @return create_time
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * @return appmodel_id
     */
    public String getAppmodelId() {
        return appmodelId;
    }

    /**
     * @param appmodelId
     */
    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }
}