package com.medusa.basemall.promotion.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.promotion.entity.ActivityProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Created by psy on 2018/05/30.
 */
public interface ActivityProductMapper extends Mapper<ActivityProduct> {

    /***
     * 根据商品id查询活动商品
     *
     * @param productId
     * @return ActivityProduct
     */
    ActivityProduct selectByProductId(Long productId);

    /***
     * 根据活动id和类型查询活动商品
     *
     * @param map
     * @return List<ActivityProduct>
     */
    List<ActivityProduct> selectByActivityId(Map<String,Integer> map);

    /***
     * 根据商品id和活动类型查询活动商品
     *
     * @param map
     * @return List<ActivityProduct>
     */
    List<ActivityProduct> selectActivityProduct(Map<String,Object> map);

    /***
     * 根据商品id、活动id和活动类型查询活动商品
     *
     * @param map
     * @return ActivityProduct
     */
    ActivityProduct selectGroupActivityProduct(Map<String,Object> map);

    /***
     * 根据活动id和活动类型查询活动商品
     *
     * @param map
     * @return List<ActivityProduct>
     */
    List<ActivityProduct> selectByActivity(Map<String, Integer> map);

    /***
     * 根据活动id和类型查询活动商品(包括删除的)
     *
     * @param map
     * @return List<ActivityProduct>
     */
    List<ActivityProduct> selectWhetherDelete(Map<String,Integer> map);

	/**
	 * 根据活动ids和活动类型查询商品
	 * @param activityIds
	 * @param activeType
	 * @return
	 */
	List<ActivityProduct> selectByActivityIdsProduct(@Param("activityIds") String[] activityIds,
			@Param("activeType") Integer activeType, @Param("appmodelId") String appmodelId);

	/**
	 * * 根据活动id和活动类型删除活动商品
	 * @param activityIds
	 * @param type
	 * @param appmodelId
	 */
	int deleteByActivityId(@Param("activityIds") String[] activityIds, @Param("type") Integer type, @Param("appmodelId") String appmodelId);

	void updatePreheatState(@Param("activityId") Integer activityId, @Param("activetyType") Integer activetyType,
			@Param("appmodelId") String appmodelId, @Param("state") Integer state);

	void updateSoldQuantity(@Param("activityProductId") Long activityProductId, @Param("quantity") Integer quantity);

	List<ActivityProduct> findPreheatTimetoStartDateActivityProduct(
			@Param("preheatTime") String preheatTime,  @Param("appmodelId") String appmodelId, @Param("activityType") Integer activityType,
			@Param("activityId") Integer activityId);

	/**
	 * 查询指定商品是否在活动商品表中
	 * @param productId
	 * @return
	 */
	List<ActivityProduct> findProductIfJoninActivity(@Param("productIds") List<Long> productId);

	/**
	 * 更新活动商品是否删除状态
	 * @param activityPorudctIds
	 * @param deleteState
	 */
	void updateDelete(@Param("activityPorudctIds") List<Long> activityPorudctIds, @Param("deleteState") Boolean deleteState);


	/**
	 * 查询商品参加活动的活动商品信息,并且活动未结束
	 * @param productId
	 * @param activityType
	 * @return
	 */
	ActivityProduct selectProductJoinActivityNotEnd(@Param("productId") Long productId,
			 @Param("activityType") Integer activityType);

	/**
	 *
	 * @param productIds
	 * @param activityType
	 * @return
	 */
	List<ActivityProduct> selectProductJoinActivityNotEnds(
			@Param("productIds") List<Long> productIds, @Param("activityType") Integer activityType);
}