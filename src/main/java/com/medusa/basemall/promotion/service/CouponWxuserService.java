package com.medusa.basemall.promotion.service;

import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.Service;
import com.medusa.basemall.promotion.entity.CouponWxuser;
import com.medusa.basemall.promotion.vo.CouponWxuserVo;

import java.util.List;

/**
 * @author Created by psy on 2018/05/30.
 */
public interface CouponWxuserService extends Service<CouponWxuser> {

    /***
     * 用户领取优惠券
     *
     * @param couponWxuser
     * @return Result
     */
    Result wxuserObtainCoupon(CouponWxuser couponWxuser);

    /***
     * 查询用户领取的优惠券
     *
     * @param couponWxuserVo
     * @return List<CouponWxuser>
     */
    List<CouponWxuser> findWxuserCoupons(CouponWxuserVo couponWxuserVo);

    /***
     * 查询用户可用优惠券
     *
     * @param couponWxuserVo
     * @return List<Coupon>
     */
    List<CouponWxuser> findWxuserCouponCanUse(CouponWxuserVo couponWxuserVo);

	/**
	 * 查询用户指定的优惠券
	 * @param wxuserId
	 * @param wxuserCouponId
	 * @return
	 */
	List<CouponWxuser> findWxuserCoupon(Long wxuserId, Integer wxuserCouponId);
}
