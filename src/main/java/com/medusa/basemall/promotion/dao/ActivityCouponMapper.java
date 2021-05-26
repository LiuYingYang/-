package com.medusa.basemall.promotion.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.promotion.entity.ActivityCoupon;
import com.medusa.basemall.promotion.vo.ActivityCouponVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Created by psy on 2018/05/30.
 */
public interface ActivityCouponMapper extends Mapper<ActivityCoupon> {

    /***
     * 查询未结束的优惠券活动
     * @param map
     * @return List<ActivityCoupon>
     */
    List<ActivityCoupon> selectNotEnd(Map<String,Object> map);

    /***
     * 根据appmodelId查询优惠券活动
     *
     * @param appmodelId
     * @return List<ActivityCoupon>
     */
    List<ActivityCouponVo> findByAppmodelId(String appmodelId);

    /***
     * 批量删除优惠券活动
     *
     * @param activityCouponIds
     * @return int
     */
    int batchDelete(@Param("activityCouponIds") String[] activityCouponIds);

    /***
     * 根据活动id查询优惠券活动
     *
     * @param activityId
     * @return ActivityCouponVo
     */
    ActivityCouponVo selectByActivityCouponVo(Integer activityId);

    /***
     * 根据特价活动id查询对应的未开始的优惠券活动
     *
     * @param activityCouponId
     * @return ActivityCoupon
     */
    ActivityCoupon findByActivityCouponIdToStart(Integer activityCouponId);

    /***
     * 根据特价活动id查询对应的进行中的优惠券活动
     *
     * @param activityCouponId
     * @return ActivityCoupon
     */
    ActivityCoupon findByActivityCouponIdToEnd(Integer activityCouponId);

    /***
     * 查询正在进行中的优惠券活动
     *
     * @param appmodelId
     * @return List<ActivityCoupon>
     */
    ActivityCoupon selectStart(String appmodelId);

    /***
     * 查询未开始的不可叠加的对全场商品有效的优惠券活动
     *
     * @param appmodelId
     * @return List<ActivityCoupon>
     */
    List<ActivityCoupon> selectSpecialOne(String appmodelId);

    /***
     * 查询未开始的对全场商品有效的优惠券活动
     *
     * @param map
     * @return List<ActivityCoupon>
     */
    List<ActivityCoupon> selectSpecialTwo(Map<String,Object> map);

    /***
     * 查询正在进行中的对全场商品有效的优惠券活动
     *
     * @param appmodelId
     * @return List<ActivityCoupon>
     */
    List<ActivityCoupon> selectStartAndFullAll(String appmodelId);

    /***
     * 查询正在进行中的和已结束的优惠券活动
     *
     * @param appmodelId
     * @return List<ActivityCoupon>
     */
    List<ActivityCoupon> selectStartAndEnd(String appmodelId);


	/**
	 * 商品删除/下架 活动中没有任何商品时,修改活动的到期时间,和活动状态
	 * @param map
	 * @return
	 */
	int updateEndDate(Map<String, Object> map);

	/**
	 * 查询进行中和未开始的优惠券活动
	 * @param appmodelId
	 * @return
	 */
	List<ActivityCoupon> findGoingAndNotStarted(String appmodelId);
}