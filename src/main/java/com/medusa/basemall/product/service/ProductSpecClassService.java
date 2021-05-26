package com.medusa.basemall.product.service;

import com.medusa.basemall.core.Service;
import com.medusa.basemall.product.entity.ProductSpecClass;

import java.util.List;

/**
 * Created by psy on 2018/05/24.
 */
public interface ProductSpecClassService extends Service<ProductSpecClass> {

    int saveProductSpecClass(List<ProductSpecClass> productSpecClassList, Long productId, String appmodelId);

    int updateProductSpecClass(List<ProductSpecClass> productSpecClassList, long productId, String appmodelId);

    List<ProductSpecClass> findByProductId(Long productId, String appmodelId);

}
