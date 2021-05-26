package com.medusa.basemall.product.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.product.entity.SpecificationClass;
import com.medusa.basemall.product.vo.SpecificationVo;

import java.util.List;

public interface SpecificationClassMapper extends Mapper<SpecificationClass> {
    List<SpecificationVo> findByAppmodelId(String appmodelId);
}