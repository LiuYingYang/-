package com.medusa.basemall.product.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.product.entity.ProductSpecClass;

import java.util.List;

public interface ProductSpecClassMapper extends Mapper<ProductSpecClass> {
    List<ProductSpecClass> selectByProductId(Long productId);
}