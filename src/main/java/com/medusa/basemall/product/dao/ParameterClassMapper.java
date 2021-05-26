package com.medusa.basemall.product.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.product.entity.ParameterClass;
import com.medusa.basemall.product.vo.ParameterVo;

import java.util.List;

public interface ParameterClassMapper extends Mapper<ParameterClass> {

    List<ParameterVo> findByAppmodelId(String appmodelId);
}