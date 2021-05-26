package com.medusa.basemall.product.service;

import com.github.pagehelper.PageInfo;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.Service;
import com.medusa.basemall.order.vo.ProductAgentVo;
import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.product.vo.*;

import java.util.List;

/**
 *
 * @author psy
 * @date 2018/05/24
 */
public interface ProductService extends Service<Product> {

    int saveProduct(ProductEditVo productEditVo);

    ProductEditVo findProductForUpdate(Long productId, String appmodelId);

    int updateProduct(ProductEditVo productEditVo);


    int batchDelete(List<DeleteProduct> productIds);

    PageInfo findProductForBack(ProductFindRequestVo productFindRequestVo);

    ProductSpecVo findSpecByProductId(Long productId, String appmodelId);

    PageInfo findProductForWX(ProductFindRequestVo productFindRequestVo);

    ProductWxViewDetailsVo findDetailByProductId(Long productId, String appmodelId,Long wxuserId);

    List<ProductAgentVo> findProducttoAgent(String product);

    Result pitchOn(String productIds, String product);


	int batchUpdateShelfState(List<DeleteProduct> deleteProduct);

	List<Product> findProductNotDeleteAndShelfstate(String productIds);

	int updateStockOrPrice(Long productId, Integer stock, Double price);

	List<Product> selectByAppmodelId(String appmodelId);

	List<ProductSimpleVo> selectProductSimpleVo(String appmodelId);

	void updateActivityLabel(Long productId, String label);

	/**
	 * 商品增加库存
	 * @param productId
	 * @param stock
	 */
	void addStock(Long productId, int stock);

	/**
	 * 首页查询活动商品
	 * @param appmodelId
	 * @return
	 */
	List<HomeSeckillVO> homePageAppmodelId(String appmodelId);

	List<HomeSeckillVO> findSeckillProduct(String appmodelId);

	/**
	 * 查询首页分类的商品信息
	 * @param productIds
	 */
	List<ProductBackViewVo> findPlateProductIds(List<Long> productIds);

}
