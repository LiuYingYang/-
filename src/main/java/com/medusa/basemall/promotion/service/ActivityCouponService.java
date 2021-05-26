package com.medusa.basemall.promotion.service;

import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.Service;
import com.medusa.basemall.integral.vo.FindProductVo;
import com.medusa.basemall.promotion.entity.ActivityCoupon;
import com.medusa.basemall.promotion.vo.ActivityCouponVo;

import java.util.List;
import java.util.Map;

/**
 * @author Created by psy on 2018/05/30.
 */
public interface ActivityCouponService extends Service<ActivityCoupon> {

    /***
     * 查询可供优惠券活动选择的商品
     *
     * @param findProductVo
     * @return Result
     */
    Result findProductForCoupon(FindProductVo findProductVo);

    /***
     * 创建团优惠券活动
     *
     * @param activityCouponVo
     * @return Result
     */
    Result saveActivityCoupon(ActivityCouponVo activityCouponVo);

    /***
     * 根据appmodelId查询优惠券活动
     *
     * @param appmodelId
     * @return List<ActivityCouponVo>
     */
    List<ActivityCouponVo> findByAppmodelId(String appmodelId);

    /***
     * 批量删除优惠券活动
     *
     * @param activityCouponId
     * @return int
     */
    int batchDelete(String activityCouponId, String appmodelId);

    /***
     * 更新优惠券活动
     *
     * @param activityCouponVo
     * @return Result
     */
    Result updateActivityCoupon(ActivityCouponVo activityCouponVo);

    /***
     * 根据特价活动id查询对应的未开始的优惠券活动
     *
     * @param activityCouponId
     * @return ActivityCoupon
     */
    ActivityCoupon findByActivityCouponIdToStart(Integer activityCouponId);

	ActivityCoupon findByActivityCouponIdToEnd(Integer activityCouponId);

	/**
	 *
	 * @param mapForCoupon
	 * @return
	 */
	List<ActivityCoupon> selectNotEnd(Map<String, Object> mapForCoupon);

	ActivityCouponVo selectByActivityCouponVo(Integer activityId);

	void updateEndDate(Map<String,Object> map);

	/**
	 *查询进行中的优惠券
	 * @param appmodelId
	 * @return
	 */
	ActivityCoupon findUnderwayActivityCoupon(String appmodelId);
}
