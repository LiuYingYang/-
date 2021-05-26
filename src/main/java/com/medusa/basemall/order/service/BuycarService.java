package com.medusa.basemall.order.service;


import com.medusa.basemall.core.Result;
import com.medusa.basemall.order.entity.Buycar;
import com.medusa.basemall.order.vo.OrderSurveyVo;
import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.product.entity.ProductSpecItem;
import com.medusa.basemall.product.vo.JoinActiveInfo;

import java.util.List;
import java.util.Map;

public interface BuycarService {

	Buycar addProductToBuycar(Buycar buycar);


	Result batchDelete(String buycarIds, Long wxuserId, Integer type);

	Result findBurCarSum(Long wxuserId, String appmodelId);

	Result updateBurCar(Buycar buycar);

	Result findBuyCars(Long wxuserId, String appmodelId);

	List<Buycar> selectAllBuyCarNumber(OrderSurveyVo orderSurveyVo);

	void updateShelfState(List<Long> productIds, int shelfState);

	void updateActiveInfo(List<Map<String, Object>> maps);

	void addBuycar(Product product, Long wxuserId, Integer quantity, ProductSpecItem productSpecItemInfo, JoinActiveInfo joinActiveInfo);

	List<Buycar> findByProductId(List<Long> productId);

	void updateBurCars(List<Buycar> buycars);

	Map<String, Object> selectRealTimeBuycarList(Integer pageNum, Integer pageSize, String appmodelId);

	/**
	 * 查询指定时间加入购物车的数量
	 * @param startDate
	 * @param endDate
	 * @param appmodelId
	 * @return
	 */
	String selectJoinBuycarSum(String startDate, String endDate, String appmodelId);

	/**
	 * 购物车中的商品添加活动标签
	 * @param productId
	 * @param activityId
	 * @param activityType
	 */
	void updateActivityMark(Long productId, Integer activityId, Integer activityType);

	/**
	 * 更新优惠券活动信息标签
	 * @param productId
	 * @param appmodelId
	 * @param couponInfo
	 */
	void updateCouponInfo(Long productId, String appmodelId, String couponInfo);
}
