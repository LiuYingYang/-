package com.medusa.basemall.promotion.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.promotion.entity.ActivityProductStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Created by psy on 2018/05/30.
 */
public interface ActivityProductStockMapper extends Mapper<ActivityProductStock> {

    /***
     * 根据活动商品id和规格id查询活动商品规格库存
     *
     * @param activityProductStock
     * @return ActivityProductStock
     */
    ActivityProductStock selectActivityProductStock(ActivityProductStock activityProductStock);

    /***
     * 根据活动商品id查询活动商品规格库存
     *
     * @param activityProductId
     * @return List<ActivityProductStock>
     */
    List<ActivityProductStock> findByAcitivityProductId(Long activityProductId);

	/**
	 * 根据某个多规格id 查询对应的所有未删除的活动库存
	 * @param productSpecItemId
	 * @return
	 */
	List<ActivityProductStock> selectByProductSpecItemId(Long productSpecItemId);

	/**
	 * 查询指定活动商品规格库存
	 * @param activityId
	 * @param activityType
	 * @param activityProductId
	 * @return
	 */
	ActivityProductStock selectByActivityIdAndActivityTypeAndActivityProductId(@Param("activityId") Integer activityId, @Param("activityType") Integer activityType, @Param("activityProductId") Long activityProductId);
}