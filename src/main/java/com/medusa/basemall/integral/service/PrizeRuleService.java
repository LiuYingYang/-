package com.medusa.basemall.integral.service;

import com.medusa.basemall.core.Service;
import com.medusa.basemall.integral.entity.PrizeRule;


/**
 * @author Created by wx on 2018/06/06.
 */
public interface PrizeRuleService extends Service<PrizeRule> {

    /***
     * 根据appmodelId查询积分获取规则表
     *
     * @param appmodelId
     * @return PrizeRule
     */
    PrizeRule findByAppmodelId(String appmodelId);
}
