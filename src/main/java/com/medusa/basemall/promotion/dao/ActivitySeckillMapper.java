package com.medusa.basemall.promotion.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.promotion.entity.ActivitySeckill;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Created by psy on 2018/05/30.
 */
public interface ActivitySeckillMapper extends Mapper<ActivitySeckill> {

	/***
	 * 查询未结束的秒杀活动
	 *
	 * @param appmodelId
	 * @return List<ActivitySeckill>
	 */
	List<ActivitySeckill> selectNotEnd(@Param("appmodelId") String appmodelId);

	/***
	 * 根据appmodelId查询秒杀活动
	 *
	 * @param appmodelId
	 * @return List<ActivitySeckill>
	 */
	List<ActivitySeckill> findByAppmodelId(String appmodelId);

	/***
	 * 批量删除秒杀活动
	 *
	 * @param activitySpecialIds
	 * @return int
	 */
	int batchDelete(@Param("activitySpecialIds") String[] activitySpecialIds, @Param("appmodelId") String appmodelId);

	/***
	 * 根据活动id查询秒杀活动
	 *
	 * @param activityId
	 * @return ActivitySeckill
	 */
	ActivitySeckill selectById(Integer activityId);

	/***
	 * 根据特价活动id查询对应的未开始的秒杀活动
	 *
	 * @param activitySeckillId
	 * @return ActivitySeckill
	 */
	ActivitySeckill findByActivitySeckillIdStart(Integer activitySeckillId);

	/***
	 * 根据特价活动id查询对应的进行中的秒杀活动
	 *
	 * @param activitySeckillId
	 * @return ActivitySeckill
	 */
	ActivitySeckill findByActivitySeckillIdEnd(Integer activitySeckillId);

	/***
	 * 查询正在进行中秒杀活动
	 *
	 * @param appmodelId
	 * @return List<ActivitySeckill>
	 */
	List<ActivitySeckill> selectStart(String appmodelId);

	int updateEndDate(Map<String, Object> map);

	/**
	 * 查询时间段内是否存在其他秒杀活动
	 * @param startDate
	 * @param endDate
	 * @param appmodelId
	 * @return
	 */
	List<ActivitySeckill> findConflictingSeckill(@Param("startDate") String startDate, @Param("endDate") String endDate,
			@Param("appmodelId") String appmodelId);

	List<ActivitySeckill> selectThatVeryDaySeckillActivity(
			@Param("start") String start, @Param("end") String end, @Param("appmodelId") String appmodelId);

}