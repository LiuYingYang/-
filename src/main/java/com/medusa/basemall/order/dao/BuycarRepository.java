package com.medusa.basemall.order.dao;


import com.medusa.basemall.order.entity.Buycar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BuycarRepository extends MongoRepository<Buycar, String> {

	/**
	 * 查询用户购物车相同的商品
	 *
	 * @param productId
	 * @param appmodelId
	 * @param wxuserId
	 * @return
	 */
	List<Buycar> findByProductIdAndAppmodelIdAndWxuserId(Long productId, String appmodelId, Long wxuserId);

	/**
	 * 查询用户购物车中所有商品
	 *
	 * @param appmodelId
	 * @param wxuserId
	 * @return
	 */
	List<Buycar> findByWxuserIdAndAppmodelId(Long wxuserId, String appmodelId);


	/**
	 * 查询商家今日商品加入购物车数量
	 *
	 * @param appmodelId
	 * @param pageable
	 * @return
	 */
	Page<Buycar> findByAppmodelId(String appmodelId, Pageable pageable);


	/**
	 * 清空用户购物车商品
	 *
	 * @param wxuserId
	 * @return
	 */
	int deleteByWxuserId(Long wxuserId);


	/**
	 * 删除用户购物车商品
	 *
	 * @param buycarIdIs
	 * @return
	 */
	int deleteByBuycarIdIn(String buycarIdIs);


	List<Buycar> findByWxuserIdAndAppmodelIdAndShelfState(Long wxuserId, String appmodelId, Integer shelfState);

}
