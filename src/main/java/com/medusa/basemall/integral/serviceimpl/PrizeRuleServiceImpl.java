package com.medusa.basemall.integral.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.integral.dao.PrizeRuleMapper;
import com.medusa.basemall.integral.entity.PrizeRule;
import com.medusa.basemall.integral.service.PrizeRuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author Created by wx on 2018/06/06.
 */
@Service

public class PrizeRuleServiceImpl extends AbstractService<PrizeRule> implements PrizeRuleService {
    @Resource
    private PrizeRuleMapper tPrizeRuleMapper;

    @Override
    public PrizeRule findByAppmodelId(String appmodelId) {
        return tPrizeRuleMapper.findByAppmodelId(appmodelId);
    }
}
