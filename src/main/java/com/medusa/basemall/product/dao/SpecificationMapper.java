package com.medusa.basemall.product.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.product.entity.Specification;

import java.util.List;

public interface SpecificationMapper extends Mapper<Specification> {
    int logicDelete(Integer specificationClassId);

    List<Specification> findBySpecificationClassId(Integer specificationClassId);
}