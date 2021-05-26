package com.medusa.basemall.product.service;

import com.medusa.basemall.core.Service;
import com.medusa.basemall.product.entity.ProductSpecItem;
import com.medusa.basemall.product.vo.SpecItemUpdateVo;

import java.util.List;
import java.util.Map;

/**
 * Created by medusa on 2018/05/23.
 */
public interface ProductSpecItemService extends Service<ProductSpecItem> {

    int saveProductSpecItem(List<ProductSpecItem> productSpecItemList, Long productId, String appmodelId);

    int updateProductSpecItem(List<ProductSpecItem> productSpecItemList, long productId, String appmodelId);

    List<ProductSpecItem> findByProductId(Long productId);


	Integer updateSpecStockOrPrice(List<SpecItemUpdateVo> specItemUpdateVos);

	Integer updateSpecProductStockReturn(Map<Long, Integer> map);
}
