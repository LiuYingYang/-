package com.medusa.basemall.product.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.product.entity.Parameter;

import java.util.List;

public interface ParameterMapper extends Mapper<Parameter> {

    List<Parameter> selectByParamClassId(Integer paramClassId);
}