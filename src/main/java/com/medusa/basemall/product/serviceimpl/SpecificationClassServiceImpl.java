package com.medusa.basemall.product.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.product.dao.SpecificationClassMapper;
import com.medusa.basemall.product.entity.SpecificationClass;
import com.medusa.basemall.product.service.SpecificationClassService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * Created by psy on 2018/05/24.
 * 需要事物时添加  @Transactional
 */

@Service

public class SpecificationClassServiceImpl extends AbstractService<SpecificationClass> implements SpecificationClassService {
    @Resource
    private SpecificationClassMapper tSpecificationClassMapper;
}
