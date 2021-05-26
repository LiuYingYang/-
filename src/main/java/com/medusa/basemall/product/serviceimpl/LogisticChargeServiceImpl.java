package com.medusa.basemall.product.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.product.dao.LogisticChargeMapper;
import com.medusa.basemall.product.entity.LogisticCharge;
import com.medusa.basemall.product.service.LogisticChargeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * Created by psy on 2018/05/24.
 * 需要事物时添加  @Transactional
 */

@Service
public class LogisticChargeServiceImpl extends AbstractService<LogisticCharge> implements LogisticChargeService {
    @Resource
    private LogisticChargeMapper tLogisticChargeMapper;
}
