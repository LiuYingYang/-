package com.medusa.basemall.promotion.service;

import com.medusa.basemall.core.Service;
import com.medusa.basemall.promotion.entity.ActivityProductStock;

import java.util.List;

/**
 * @author Created by medusa on 2018/07/16.
 */
public interface ActivityProductStockService extends Service<ActivityProductStock> {

	List<ActivityProductStock> findByActivityProductIds(List<Long> activityProductIds);

	/**
	 * 查询活动商品库存
	 * @param activityProductId
	 * @return
	 */
	List<ActivityProductStock> findActivityProductSpec(Long activityProductId);

	/**
	 * 更新库存数据删除状态
	 * @param activityProductStockIds
	 * @param isDelete
	 */
	void updateDeleteState(List<Integer> activityProductStockIds, boolean isDelete);

	/**
	 * 通过规格id查询所有活动规格库存
	 * @param productSpecItemId
	 * @return
	 */
	List<ActivityProductStock> findByProductSpecItemId(Long productSpecItemId);

	/**
	 * 查询制定活动商品规格库存
	 * @param activityId
	 * @param activityType
	 * @param activityProductId
	 * @return
	 */
	ActivityProductStock findByActivityIdAndActivityTypeAndActivityProductId(Integer activityId, Integer activityType, Long activityProductId);

}
