package com.medusa.basemall.product.service;

import com.medusa.basemall.core.Service;
import com.medusa.basemall.product.entity.Specification;
import com.medusa.basemall.product.vo.SpecificationVo;

import java.util.List;

/**
 * Created by psy on 2018/05/24.
 */
public interface SpecificationService extends Service<Specification> {

    int saveOrUpdateSpecificationClass(SpecificationVo specificationVo);

    int deleteSpecificationClassById(Integer specificationClassId);

    List<SpecificationVo> findByAppmodelId(String appmodelId);
}
