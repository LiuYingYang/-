package com.medusa.basemall.promotion.service;

import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.Service;
import com.medusa.basemall.integral.vo.FindProductVo;
import com.medusa.basemall.promotion.entity.ActivityGroup;
import com.medusa.basemall.promotion.vo.ActivityGroupVo;

import java.util.List;
import java.util.Map;

/**
 * @author Created by psy on 2018/05/30.
 */
public interface ActivityGroupService extends Service<ActivityGroup> {

    /***
     * 查询可供团购活动选择的商品
     *
     * @param findProductVo
     * @return Result
     */
    Result findProductForGroup(FindProductVo findProductVo);

    /***
     * 创建团购活动
     *
     * @param activityGroupVo
     * @return int
     */
    int saveActivityGroup(ActivityGroupVo activityGroupVo);

    /***
     * 根据appmodelId查询团购活动
     *
     * @param appmodelId
     * @return List<ActivitySeckillVo>
     */
    List<ActivityGroupVo> findByAppmodelId(String appmodelId);

    /***
     * 批量删除团购活动
     *
     * @param activityGroupId
     * @param appmodelId
     * @return int
     */
    int batchDelete(String[] activityGroupId, String appmodelId);

    /***
     * 更新团购活动
     *
     * @param activityGroupVo
     * @return int
     */
    int updateActivityGroup(ActivityGroupVo activityGroupVo);

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

	List<ActivityGroup> selectNotEnd(Map<String, Object> mapForGroup);

	void updateEndDate(Map<String,Object> map);

	/**
	 *  查询指定时间段之内是否存在冲突的团购活动
	 * @param startTime
	 * @param endTime
	 * @param appmodelId
	 * @return
	 */
	List<ActivityGroup> findConflictingGroups(String startTime, String endTime, String appmodelId);
}
