package com.medusa.basemall.user.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_member_active_recharge")
@Data
public class MemberActiveRecharge {
    /**
     * 活动id
     */
    @Id
    @Column(name = "active_recharge_id")
    @ApiModelProperty(value = "活动id",required = true)
    private Integer activeRechargeId;

    /**
     * 活动名称
     */
    @Column(name = "active_name")
    @ApiModelProperty(value = "活动名称"  ,required = true)
    private String activeName;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
    @ApiModelProperty(value = "开始时间"  ,hidden = true)
    private String startTime;

    /**
     * 截止时间
     */
    @Column(name = "end_time")
    @ApiModelProperty(value = "截止时间"  ,hidden = true)
    private String endTime;

    /**
     * 活动状态  1-进行中 2.已结束
     */
    @ApiModelProperty(value = "活动状态:1-进行中 2.已结束")
    private Integer state;

    /**
     *赠送金额
     */
    @Column(name = "send_price")
    @ApiModelProperty(value = "赠送金额",required = true)
    private Double sendPrice;

    /**
     * 达标价格
     */
    @Column(name = "max_price")
    @ApiModelProperty(value = "达标价格",required = true)
    private Double maxPrice;

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
     * 删除标志
     */
    @Column(name = "delete_state")
    @ApiModelProperty(value = "删除标志")
    private Integer deleteState;
}