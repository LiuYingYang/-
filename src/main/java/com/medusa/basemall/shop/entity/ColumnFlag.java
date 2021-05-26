package com.medusa.basemall.shop.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Created by wx on 2018/06/04.
 */
@Data
@Table(name = "t_column_flag")
public class ColumnFlag {

    @ApiModelProperty(value = "开关id")
    @Id
    @Column(name = "column_flag_id")
    private Integer columnFlagId;


    @ApiModelProperty(value = "模板Id")
    @Column(name = "appmodel_id")
    private String appmodelId;


    @ApiModelProperty(value = "搜索栏开关")
    @Column(name = "serarch_flag")
    private Boolean serarchFlag;


    @ApiModelProperty(value = "店铺公告开关")
    @Column(name = "notice_flag")
    private Boolean noticeFlag;

    @ApiModelProperty(value = "店铺故事开关")
    @Column(name = "shopInfo_flag")
    private Boolean shopinfoFlag;


    @ApiModelProperty(value = "分类页开关")
    @Column(name = "classify_flag")
    private Boolean classifyFlag;


    @ApiModelProperty(value = "发现页开关")
    @Column(name = "article_flag")
    private Boolean articleFlag;


    @ApiModelProperty(value = "会员功能开关")
    @Column(name = "member_flag")
    private Boolean memberFlag;


    @ApiModelProperty(value = "积分商城开关")
    @Column(name = "shop_flag")
    private Boolean shopFlag;


    @ApiModelProperty(value = "代理功能开关")
    @Column(name = "proxy_flag")
    private Boolean proxyFlag;


    @ApiModelProperty(value = "底部打标开关")
    @Column(name = "foot_flag")
    private Boolean footFlag;

    @ApiModelProperty(value = "会员储值功能开关")
    @Column(name = "member_recharge_flag")
    private Boolean memberRechargeFlag;
}