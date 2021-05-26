package com.medusa.basemall.product.service;

import com.medusa.basemall.core.Service;
import com.medusa.basemall.product.entity.ProductSpec;

import java.util.List;

/**
 * Created by psy on 2018/05/24.
 */
public interface ProductSpecService extends Service<ProductSpec> {

    int saveProductSpec(List<ProductSpec> productSpecList, Long productId, String appmodelId);

    int updateProductSpec(List<ProductSpec> productSpecList, long productId, String appmodelId);

    List<ProductSpec> findByProductId(Long productId);

}
