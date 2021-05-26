package com.medusa.basemall.product.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.product.entity.ProductSpecItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductSpecItemMapper extends Mapper<ProductSpecItem> {

    List<ProductSpecItem> selectByProductId(Long productId);

	/**
	 * 查询没有参加活动的商品规格项,
	 * @param productId
	 * @return
	 */
    List<ProductSpecItem> selectByNotActiveProduct(Long productId);

	Integer updateSpecProductStockReturn(@Param("map") Map<Long, Integer> map);
}