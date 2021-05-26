package com.medusa.basemall.promotion.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Created by psy on 2018/05/30.
 */
@Table(name = "t_coupon")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {

    @Id
    @Column(name = "coupon_id")
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value="优惠券编号(新增时不传,其余操作时传)")
    private Integer couponId;

    @Column(name = "activity_coupon_id")
    @ApiModelProperty(value="优惠活动ID(优惠券来源为活动时传)")
    private Integer activityCouponId;

    @Column(name = "stock_quantity")
    @ApiModelProperty(value="优惠券数量", required = true)
    private Integer stockQuantity;

    @Column(name = "limit_quantity")
    @ApiModelProperty(value="单人限领量(优惠券来源为活动时传)")
    private Integer limitQuantity;

    @Column(name = "min_price")
    @ApiModelProperty(value="优惠值(满减时传)")
    private Double minPrice;

    @Column(name = "max_price")
    @ApiModelProperty(value="满减值(满减时传)")
    private Double maxPrice;

    @ApiModelProperty(value="折扣值(满折时传)")
    private Double discount;

    @Column(name = "coupon_type")
    @ApiModelProperty(value="优惠券类型（1---满减，2---满折，3---无门槛现金券，4---无门槛折扣）", required = true)
    private Integer couponType;

    @Column(name = "source_type")
    @ApiModelProperty(value="优惠券来源类型（1---优惠活动创建，2---积分领取）", required = true)
    private Integer sourceType;

    @Column(name = "obtain_quantity")
    @ApiModelProperty(value="领取量(不需要)")
    private Integer obtainQuantity;

    @Column(name = "used_quantity")
    @ApiModelProperty(value="使用量(不需要)")
    private Integer usedQuantity;

    @Column(name = "appmodel_id")
    @ApiModelProperty(value="模板id", required = true)
    private String appmodelId;

    @Column(name = "delete_state")
    @ApiModelProperty(value="删除标志(不需要)")
    private Boolean deleteState;

    @Column(name = "convert_price")
    @ApiModelProperty(value="兑换所需积分(优惠券来源为积分商城时传)")
    private Integer convertPrice;

    @Column(name = "live_time")
    @ApiModelProperty(value="使用期限(0-无时间限制,1-有效天数,2-有效日期)(优惠券来源为积分商城时传)")
    private Integer liveTime;

    @ApiModelProperty(value="有效天数(使用期限为有效天使时传)")
    private Integer day;

    @Column(name = "statr_time")
    @ApiModelProperty(value="开始日期(使用期限为有效日期时传)")
    private String statrTime;

    @Column(name = "end_time")
    @ApiModelProperty(value="结束日期(同开始日期)")
    private String endTime;

    @ApiModelProperty(value="活动对象")
    @Transient
    private ActivityCoupon activityCoupon;
    /**
     * 是否领取状态
     */
    @ApiModelProperty(value="是否领取状态")
    @Transient
    private Boolean receiveOrNot;


}