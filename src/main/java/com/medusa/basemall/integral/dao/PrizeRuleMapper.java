package com.medusa.basemall.integral.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.integral.entity.PrizeRule;

/**
 * @author Created by wx on 2018/06/06.
 */
public interface PrizeRuleMapper extends Mapper<PrizeRule> {

    /***
     * 根据appmodelId查询积分获得规则表
     *
     * @param appmodelId
     * @return PrizeRule
     */
    PrizeRule findByAppmodelId(String appmodelId);
}