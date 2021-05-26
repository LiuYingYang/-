package com.medusa.basemall.promotion.vo;

import com.medusa.basemall.promotion.entity.*;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author Created by psy on 2018/05/30.
 */
public class ActivityProductVo {

    @ApiModelProperty(value="活动商品对象")
    private ActivityProduct activityProduct;

    @ApiModelProperty(value="优惠券活动对象")
    private ActivityCouponVo activityCoupon;

    @ApiModelProperty(value="团活动对象")
    private ActivityGroup activityGroup;

    @ApiModelProperty(value="特价活动对象")
    private ActivitySpecial activitySpecial;

    @ApiModelProperty(value="秒杀活动对象")
    private ActivitySeckill activitySeckill;

    @ApiModelProperty(value="团对象集合")
    private List<Group> groupList;


}
