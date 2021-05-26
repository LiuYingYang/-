package com.medusa.basemall.promotion.service;

import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.Service;
import com.medusa.basemall.integral.vo.FindProductVo;
import com.medusa.basemall.promotion.entity.ActivitySpecial;
import com.medusa.basemall.promotion.vo.ActivitySpecialVo;

import java.util.List;
import java.util.Map;

/**
 * @author Created by psy on 2018/05/30.
 */
public interface ActivitySpecialService extends Service<ActivitySpecial> {

    /***
     * 查询可供特价活动选择的商品
     *
     * @param findProductVo
     * @return Result
     */
    Result findProductForSpecial(FindProductVo findProductVo);

    /***
     * 创建特价活动
     *
     * @param activitySpecialVo
     * @return int
     */
    int saveActivitySpecial(ActivitySpecialVo activitySpecialVo);

    /***
     * 根据appmodelId查询特价活动
     *
     * @param appmodelId
     * @return List<ActivitySpecialVo>
     */
    List<ActivitySpecialVo> findByAppmodelId(String appmodelId);

    /***
     * 批量删除特价活动
     *
     * @param activitySpecialId
     * @return int
     */
    int batchDelete(String[] activitySpecialId);

    /***
     * 更新特价活动
     *
     * @param activitySpecialVo
     * @return int
     */
    int updateActivitySpecial(ActivitySpecialVo activitySpecialVo);

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

	List<ActivitySpecial> selectNotEnd(Map<String, Object> mapForSpecial);

	void updateEndDate(Map<String, Object> map);

	List<ActivitySpecial> findConflictingSpecial(String startTime, String endTime, String appmodelId);
}
