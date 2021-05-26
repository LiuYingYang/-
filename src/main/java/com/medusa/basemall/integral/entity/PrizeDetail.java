package com.medusa.basemall.integral.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Created by wx on 2018/06/06.
 */
@Table(name = "t_prize_detail")
@Data
public class PrizeDetail {

    @Id
    @Column(name = "integral_detail_id")
    @ApiModelProperty(value = "明细表编号")
    private Long integralDetailId;

	@ApiModelProperty(value = "使用类别  1. 兑换商品 2.购买商品 3,登录增加积分,4分享增加积分", required = true)
    private Integer type;

    @ApiModelProperty(value = "购买商品数量(购买商品时传)")
    private Integer quantity;

    @Column(name = "create_time")
    @ApiModelProperty(value = "添加时间(不需要)")
    private String createTime;

    @ApiModelProperty(value = "积分", required = true)
    private Integer integral;

    @Column(name = "wxuser_id")
    @ApiModelProperty(value = "用户id", required = true)
    private Long wxuserId;

    @Column(name = "appmodel_id")
    @ApiModelProperty(value = "模板id", required = true)
    private String appmodelId;

    @Column(name = "prize_id")
    @ApiModelProperty(value = "prize_id(兑换商品时传)")
    private Integer prizeId;

    @Column(name = "delete_state")
    @ApiModelProperty(value = "删除标志(不需要)")
    private Boolean deleteState;

    @Transient
    @ApiModelProperty(value = "积分商品对象")
    private Prize prize;


}