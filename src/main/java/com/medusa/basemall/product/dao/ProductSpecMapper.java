package com.medusa.basemall.product.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.product.entity.ProductSpec;

import java.util.List;

public interface ProductSpecMapper extends Mapper<ProductSpec> {

    List<ProductSpec> selectByProductId(Long productId);
}