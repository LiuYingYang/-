package com.medusa.basemall.product.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.product.vo.ProductBackViewVo;
import com.medusa.basemall.product.vo.ProductFindRequestVo;
import com.medusa.basemall.product.vo.ProductSimpleVo;
import com.medusa.basemall.product.vo.ProductWxVo;
import com.medusa.basemall.promotion.vo.OptionalProductItems;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ProductMapper extends Mapper<Product> {
	int updateByLogisticModel(Product product);

	/**
	 * 批量修改商品上下架状态
	 * @param map
	 * @return
	 */
	int batchUpdateShelfState(Map<String, Object> map);

	int batchDelete(@Param("productIds") List<Long> productIds);

	List<ProductBackViewVo> findProductForBack(ProductFindRequestVo productFindRequestVo);

	/**
	 * 查询不包含某些商品的
	 * @param map
	 * @return
	 */
	List<ProductSimpleVo> selectNotInIds(Map<String, Object> map);

	/**
	 * 查询不包含/包含 指定商品
	 * @param productIds
	 * @param appmodelId
	 * @param type   1-不包含 2-包含
	 * @return
	 */
	List<OptionalProductItems> selectNotinOrInIdsSpec(@Param("productIds") List<Long> productIds,
			@Param("appmodelId") String appmodelId, @Param("type") Integer type);

	List<ProductWxVo> selectByProductIds(@Param("productIds") List<Long> productIds);

	List<ProductBackViewVo> findProductForWX(ProductFindRequestVo productFindRequestVo);


    List<Product> selectByOrderDetail(Long orderId);

    List<Product> selectUrgentProducts(String appmodelId);

	List<Product> selectByAppmodelId(String appmodelId);

	void updateSetProductTypeListToNull(List<Long> productIds);

    List<Product> selectHotProduct(String appmodelId);

	List<ProductSimpleVo> selectProductSimpleVo(String appmodelId);

	void updateStock(@Param("productId") Long productId, @Param("stock") int stock);

	/**
	 * 查询首页秒杀商品
	 * @param appmodelId
	 * @param activitySeckillId
	 * @return
	 */
	List<ProductBackViewVo> selectHomePageSckillProduct(@Param("appmodelId") String appmodelId, @Param("activitySeckillId") Integer activitySeckillId);

	List<ProductBackViewVo> findSeckillProduct(@Param("appmodelId") String appmodelId, @Param("activitySeckillId") Integer activitySeckillId);

	/**
	 * 查询指定商品
	 * @param productIds
	 * @return
	 */
	List<ProductBackViewVo> selectPlateProductIds(@Param("productIds") List<Long> productIds);
}