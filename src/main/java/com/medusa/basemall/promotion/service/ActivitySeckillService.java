package com.medusa.basemall.promotion.service;

import com.medusa.basemall.core.Service;
import com.medusa.basemall.integral.vo.FindProductVo;
import com.medusa.basemall.promotion.entity.ActivitySeckill;
import com.medusa.basemall.promotion.vo.ActivitySeckillDetailVo;
import com.medusa.basemall.promotion.vo.ActivitySeckillVo;
import com.medusa.basemall.promotion.vo.OptionalProductItems;

import java.util.List;
import java.util.Map;

/**
 * @author Created by psy on 2018/05/30.
 */
public interface ActivitySeckillService extends Service<ActivitySeckill> {

	/***
	 * 查询可供秒杀活动选择的商品
	 *
	 * @param findProductVo
	 * @return Result
	 */
	List<OptionalProductItems> findProductForSeckill(FindProductVo findProductVo);

	/***
	 * 创建秒杀活动
	 *
	 * @param activitySeckillVo
	 * @return int
	 */
	int saveActivitySeckill(ActivitySeckillVo activitySeckillVo);

	/***
	 * 根据appmodelId查询秒杀活动
	 *
	 * @param appmodelId
	 * @param pageNum
	 * @param pageSize
	 * @return List<ActivitySeckillVo>
	 */
	List<ActivitySeckillVo> findByAppmodelId(String appmodelId, Integer pageNum, Integer pageSize);

	/***
	 * 批量删除秒杀活动
	 *
	 * @param activitySeckillId
	 * @param appmodelId
	 * @return int
	 */
	int batchDelete(String activitySeckillId, String appmodelId);

	/***
	 * 更新秒杀活动
	 *
	 * @param activitySeckillVo
	 * @return int
	 */
	int updateActivitySeckill(ActivitySeckillVo activitySeckillVo);

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

	/**
	 * 查询进行中和未开始的活动
	 * @param appmodelId
	 * @return
	 */
	List<ActivitySeckill> selectNotEnd(String appmodelId);

	/**
	 *根据活动id更新 活动当前状态  活动结束数据
	 * @param map
	 */
	void updateEndDate(Map<String, Object> map);

	/**
	 * 查询进行中的
	 * @param appmodelId
	 * @return
	 */
	ActivitySeckill findUnderwayActivitySeckill(String appmodelId);

	/**
	 * 查询某个时间内是否有进行中的或未开始的活动
	 * @param startTime
	 * @param endTime
	 * @param appmodelId
	 * @return
	 */
	List<ActivitySeckill> findConflictingSeckill(String startTime, String endTime, String appmodelId);


	/**
	 * 查询秒杀活动详情
	 * @param activitySeckillId
	 * @param appmodelId
	 * @return
	 */
	ActivitySeckillDetailVo findseckillDetail(Integer activitySeckillId, String appmodelId);

	/**
	 * 查询当天秒杀互动
	 * @param appmodelId
	 * @return
	 */
	List<ActivitySeckill> selectThatVeryDaySeckillActivity(String appmodelId);

	/**
	 * 查询预热中的活动
	 * @param appmodelId
	 * @return
	 */
	ActivitySeckill findPreheatActivitySeckill(String appmodelId);

}
