package com.medusa.basemall.promotion.service;

import com.medusa.basemall.core.Service;
import com.medusa.basemall.promotion.entity.Coupon;

import java.util.List;

/**
 * @author Created by psy on 2018/05/30.
 */
public interface CouponService extends Service<Coupon> {
    /***
     * 查询小程序首页优惠券显示
     *
     * @param appmodelId
     * @param wxuserId
     * @return List<Coupon>
     */
    List<Coupon> findByAppmodelId(String appmodelId, Long wxuserId);

    /***
     * 查询团活动对应活动商品
     *
     * @param productIds
     * @param appmodelId
     * @param wxuserId
     * @return List<Coupon>
     */
    List<Coupon> findCouponByProductIds(String productIds, String appmodelId, Long wxuserId);

	int deleteByActivityCouponId(String activityCouponIds);

}
