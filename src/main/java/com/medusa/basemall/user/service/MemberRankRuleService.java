package com.medusa.basemall.user.service;

import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.Service;
import com.medusa.basemall.user.entity.MemberRankRule;

/**
 * Created by medusa on 2018/05/29.
 */
public interface MemberRankRuleService extends Service<MemberRankRule> {

    Result createRankRule(MemberRankRule memberRankRule);

    Result updateMemberRankRule(MemberRankRule memberRankRule);

    MemberRankRule detail(String appmodelId);
}
