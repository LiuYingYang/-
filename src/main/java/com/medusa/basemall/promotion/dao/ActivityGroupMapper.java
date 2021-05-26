package com.medusa.basemall.promotion.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.promotion.entity.ActivityGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Created by psy on 2018/05/30.
 */
public interface ActivityGroupMapper extends Mapper<ActivityGroup> {

	/***
	 * 查询未结束的团购活动
	 *
	 * @param map
	 * @return List<ActivityGroup>
	 */
	List<ActivityGroup> selectNotEnd(Map<String, Object> map);

	/***
	 * 根据appmodelId查询优惠券活动
	 *
	 * @param appmodelId
	 * @return List<ActivityGroup>
	 */
	List<ActivityGroup> findByAppmodelId(String appmodelId);

	/***
	 * 批量删除团购活动
	 *
	 * @param activityGroupId
	 * @return int
	 */
	int batchDelete(String[] activityGroupId);

	/***
	 * 根据活动id查询团购活动
	 *
	 * @param activityId
	 * @return ActivityGroup
	 */
	ActivityGroup selectById(Integer activityId);

	/***
	 * 根据特价活动id查询对应的未开始的团购活动
	 *
	 * @param activityGroupId
	 * @return ActivityGroup
	 */
	ActivityGroup selectByActivityGroupIdStart(Integer activityGroupId);

	/***
	 * 根据特价活动id查询对应的进行中的团购活动
	 *
	 * @param activityGroupId
	 * @return ActivityGroup
	 */
	ActivityGroup selectByActivityGroupIdEnd(Integer activityGroupId);

	/***
	 * 查询正在进行中的团购活动
	 *
	 * @param appmodelId
	 * @return List<ActivityGroup>
	 */
	List<ActivityGroup> selectStart(String appmodelId);

	int updateEndDate(Map<String, Object> map);

	List<ActivityGroup> findConflictingSeckill(@Param("startDate") long startDate, @Param("endDate") long endDate,
			@Param("appmodelId") String appmodelId);
}