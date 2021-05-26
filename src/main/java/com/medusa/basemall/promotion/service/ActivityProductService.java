package com.medusa.basemall.promotion.service;

import com.medusa.basemall.core.Service;
import com.medusa.basemall.product.vo.DeleteProduct;
import com.medusa.basemall.promotion.entity.ActivityProduct;

import java.util.List;
import java.util.Map;

/**
 * @author Created by psy on 2018/05/30.
 */
public interface ActivityProductService extends Service<ActivityProduct> {


    /**
     * 查询出所有与当前活动时间冲突的活动产品
     *
     * @param appmodelId 模板ID
     * @param startDate  活动开始时间
     * @param endDate    结束时间
     * @param overlay    是否叠加
     * @param isCoupon   是否是优惠券活动
     * @param activityId 活动id
     * @param activityType 活动类型
     * @return List<Long>
     */
    List<Long> selectTimeConflictProduct(String appmodelId, String startDate, String endDate, Boolean overlay, boolean isCoupon,
                                         Integer activityId, Integer activityType);


    /**
     * 根据活动id和类型删除活动商品
     *
     * @param activityIds 活动id
     * @param activityType 活动类型
     * @param appmodelId
     * @return int
     */
    int deleteByActivityId(String activityIds, Integer activityType, String appmodelId);

    /**
     * 根据活动id和类型查询活动商品
     *
     * @param activityId 活动id
     * @param activityType 活动类型
     * @param limit
     * @return int
     */
    List<Long> findProductIds(Integer activityId, Integer activityType, Integer limit);

    /**
     * 为活动新增活动商品
     *
     * @param ap
     * @return int
     */
    void saveActivityProduct(ActivityProduct ap);


    /**
     * 查询团活动对应活动商品
     *
     * @param productId
     * @return ActivityProduct
     */
    ActivityProduct selectByProductId(Long productId);

    /**
     *  存储商品下单时涉及的活动信息,活动商品信息,优惠券,特价,秒杀,拼团
     *
     * @param activityType
     * @param activityProductId
     * @param activityId
     * @return Map<String,Object>
     */
	Map<String,Object> findProductActivityForOrder(Integer activityType, Long activityProductId, Integer activityId);
	/**
	 * 商家下架或删除 从活动中删除活动商品
	 * 如商品是活动中最后一个商品,活动结束
	 * @param haveActivity
	 * @return
	 */
	void deleteActivityProduct(List<DeleteProduct> haveActivity);

	/**
	 * 根据多个活动id和活动类型查询活动商品
	 * @param activityIds   活动ids  逗号分隔
	 * @param activeType
	 * @return
	 */
	List<ActivityProduct> findActivityProduct(String activityIds, Integer activeType, String appmodelId);

	/**
	 * 根据活动id和商品id查询活动商品
	 * @param activityId
	 * @param productId
	 * @param appmodelId
	 * @return
	 */
	ActivityProduct findActivityProduct(Integer activityId, Long productId, String appmodelId);

	/**
	 *更新活动商品预热状态
	 * @param activityId
	 * @param secondkill
	 * @param appmodelId
	 * @param state
	 */
	void updatePreheatState(Integer activityId, Integer secondkill, String appmodelId, Integer state);

	/**
	 * 更新活动商品销量
	 * @param activityProductId
	 * @param quantity
	 */
	void updateSoldQuantity(Long activityProductId, Integer quantity);

	/**
	 * 查询预热时间段内商品是否存在某个活动中
	 * @param preheatTime
	 * @param appmodelId
	 * @param activityType
	 * @param activityId
	 * @return
	 */
	List<ActivityProduct> findPreheatTimetoStartDateActivityProduct(String preheatTime,  String appmodelId, Integer activityType,
			Integer activityId);

	/**
	 * 查询指定的活动有多少商品
	 *
	 * @param activitySeckillId
	 * @param activityType
	 * @param appmodelId
	 * @return
	 */
	Integer selectActivityProductSum(Integer activitySeckillId, Integer activityType, String appmodelId);


	/**
	 * 查询商品参加活动的活动商品信息,并且活动未结束
	 * @param productId
	 * @param activityType
	 * @return
	 */
	ActivityProduct findProductJoinActivityNotEnd(Long productId,  Integer activityType);

	/**
	 * 查询商品参加活动的活动商品信息,并且活动未结束 返回多个
	 * @param productIds
	 * @param secondkill
	 * @return
	 */
	List<ActivityProduct> findProductJoinActivityNotEnds(List<Long> productIds, Integer secondkill);
}
