package com.medusa.basemall.product.service;

import com.medusa.basemall.core.Service;
import com.medusa.basemall.product.entity.Parameter;
import com.medusa.basemall.product.vo.ParameterVo;

import java.util.List;

/**
 * Created by psy on 2018/05/24.
 */
public interface ParameterService extends Service<Parameter> {

    int saveOrUpdate(ParameterVo parameterVo);

    List<ParameterVo> findByAppmodelId(String appmodelId);

    int deleteByParamClassId(Integer paramClassId);
}
