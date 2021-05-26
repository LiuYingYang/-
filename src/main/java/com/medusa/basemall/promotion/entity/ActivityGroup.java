package com.medusa.basemall.promotion.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Created by psy on 2018/05/30.
 */
@Table(name = "t_activity_group")
@Data
public class ActivityGroup {

    @ApiModelProperty(value="团活动编号")
    @Id
    @Column(name = "activity_group_id")
    @GeneratedValue(generator = "JDBC")
    private Integer activityGroupId;

    @ApiModelProperty(value="活动名称")
    @Column(name = "activity_group_name")
    private String activityGroupName;

    @ApiModelProperty(value="活动备注")
    @Column(name = "activity_remark")
    private String activityRemark;

    @ApiModelProperty(value="活动海报")
    @Column(name = "activity_img")
    private String activityImg;

    @ApiModelProperty(value="限制人数")
    @Column(name = "limit_num")
    private Integer limitNum;

    @ApiModelProperty(value="限制时间")
    @Column(name = "limit_time")
    private Integer limitTime;

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

    @ApiModelProperty(value="删除标志")
    @Column(name = "delete_state")
    private Boolean deleteState;

    @ApiModelProperty(value="模板ID")
    @Column(name = "appmodel_id")
    private String appmodelId;

}