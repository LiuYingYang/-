package com.medusa.basemall.product.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.product.dao.ParameterClassMapper;
import com.medusa.basemall.product.entity.ParameterClass;
import com.medusa.basemall.product.service.ParameterClassService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * Created by psy on 2018/05/24.
 * 需要事物时添加  @Transactional
 */

@Service

public class ParameterClassServiceImpl extends AbstractService<ParameterClass> implements ParameterClassService {
    @Resource
    private ParameterClassMapper tParameterClassMapper;
}
