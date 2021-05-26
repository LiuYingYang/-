package com.medusa.basemall.product.service;

import com.medusa.basemall.core.Service;
import com.medusa.basemall.product.entity.ProductCategory;
import com.medusa.basemall.product.vo.CategoryProductVo;

import java.util.List;

/**
 * Created by psy on 2018/05/24.
 */
public interface ProductCategoryService extends Service<ProductCategory> {

    int saveProductCategory(List<Long> categoryIds, Long productId, String appmodelId);

    int updateProductCategory(List<Long> categoryIds, long productId, String appmodelId);

    List<ProductCategory> findByProductId(Long productId);

    int batchSetCategory(List<Long> productIds, List<Long> categoryIds, String appmodelId);

	List<CategoryProductVo> findByCategoryById(List<Long> productIds, String appmodelId);
}
