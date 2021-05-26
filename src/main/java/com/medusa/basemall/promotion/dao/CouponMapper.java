package com.medusa.basemall.promotion.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.promotion.entity.Coupon;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Created by psy on 2018/05/30.
 */
public interface CouponMapper extends Mapper<Coupon> {

    /***
     * 根据优惠券活动id查询优惠券
     *
     * @param activityCouponId
     * @return List<Coupon>
     */
    List<Coupon> selectByActivityCouponId(Integer activityCouponId);

    /***
     * 根据优惠券活动id删除优惠券
     *
     * @param activityCouponIds
     * @return int
     */
    int deleteByActivityCouponId(@Param("activityCouponIds") String[] activityCouponIds);

    /***
     * 查询已结束的优惠券活动的优惠券
     *
     * @param activityCouponId
     * @return List<Coupon>
     */
    List<Coupon> selectByActivityCouponIdOver(Integer activityCouponId);

    /***
     * 查询已结束的优惠券活动的优惠券
     *
     * @param couponId
     * @return List<Coupon>
     */
    Coupon selectById(Long couponId);
}