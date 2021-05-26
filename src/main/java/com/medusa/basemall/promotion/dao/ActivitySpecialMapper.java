package com.medusa.basemall.promotion.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.promotion.entity.ActivitySpecial;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Created by psy on 2018/05/30.
 */
public interface ActivitySpecialMapper extends Mapper<ActivitySpecial> {

    /***
     * 查询未结束的特价活动
     *
     * @param map
     * @return List<ActivitySpecial>
     */
    List<ActivitySpecial> selectNotEnd(Map<String, Object> map);

    /***
     * 根据appmodelId查询特价活动
     *
     * @param appmodelId
     * @return List<ActivitySpecial>
     */
    List<ActivitySpecial> findByAppmodelId(String appmodelId);

    /***
     * 批量删除特价活动
     *
     * @param activitySpecialId
     * @return int
     */
    int batchDelete(String[] activitySpecialId);

    /***
     * 根据活动id查询特价活动
     *
     * @param activityId
     * @return ActivitySpecial
     */
    ActivitySpecial selectById(Integer activityId);

    /***
     * 根据特价活动id查询对应的未开始的特价活动
     *
     * @param activitySpecialId
     * @return ActivitySpecial
     */
    ActivitySpecial findByActivitySpecialIdStart(Integer activitySpecialId);

    /***
     * 根据特价活动id查询对应的进行中的特价活动
     *
     * @param activitySpecialId
     * @return ActivitySpecial
     */
    ActivitySpecial findByActivitySpecialIdEnd(Integer activitySpecialId);

    /***
     * 查询正在进行中特价活动
     *
     * @param appmodelId
     * @return List<ActivitySeckill>
     */
    List<ActivitySpecial> selectStart(String appmodelId);

	int updateEndDate(Map<String, Object> map);

	List<ActivitySpecial> findConflictingSeckill(@Param("startDate") long startDate, @Param("endDate") long endDate, @Param("appmodelId") String appmodelId);

}