package com.medusa.basemall.product.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.product.dao.LogisticFreeMapper;
import com.medusa.basemall.product.entity.LogisticFree;
import com.medusa.basemall.product.service.LogisticFreeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * Created by psy on 2018/05/24.
 * 需要事物时添加  @Transactional
 */

@Service
public class LogisticFreeServiceImpl extends AbstractService<LogisticFree> implements LogisticFreeService {
    @Resource
    private LogisticFreeMapper tLogisticFreeMapper;
}
