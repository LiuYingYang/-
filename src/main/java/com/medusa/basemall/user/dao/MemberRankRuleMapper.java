package com.medusa.basemall.user.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.user.entity.MemberRankRule;

public interface MemberRankRuleMapper extends Mapper<MemberRankRule> {
    void createRankRule(String appmodelId);
}