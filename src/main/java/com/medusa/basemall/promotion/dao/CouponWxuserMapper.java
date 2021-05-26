package com.medusa.basemall.promotion.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.promotion.entity.CouponWxuser;
import com.medusa.basemall.promotion.vo.CouponWxuserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Created by psy on 2018/05/30.
 */
public interface CouponWxuserMapper extends Mapper<CouponWxuser> {

    /***
     * 查询用户未使用的优惠券
     *
     * @param couponWxuserVo
     * @return List<CouponWxuser>
     */
    List<CouponWxuser> selectByWxuserIdNotUse(CouponWxuserVo couponWxuserVo);

    /***
     * 查询用户失效的优惠券
     *
     * @param couponWxuserVo
     * @return List<CouponWxuser>
     */
    List<CouponWxuser> selectByWxuserIdUsed(CouponWxuserVo couponWxuserVo);

    /***
     * 根据appmodelId和用户id查询用户的优惠券
     *
     * @param map
     * @return List<CouponWxuser>
     */
    List<CouponWxuser> selectByAppmodelId(Map<String,Object> map);

    /***
     * 更改用户优惠券的使用状态
     *
     * @param activityCouponId
     * @return void
     */
    void updateFlag(Integer activityCouponId);

    /***
     * 根据优惠券id查询用户领取此优惠券的数量
     *
     * @param map
     * @return Integer
     */
    Integer selectByCouponId(Map<String,Object> map);


	/**
	 *
	 * @param wxuserId
	 * @param wxuserCouponId
	 * @return
	 */
	List<CouponWxuser> findWxuserCoupon(@Param("wxuserId") Long wxuserId, @Param("wxuserCouponId") Integer wxuserCouponId);
}