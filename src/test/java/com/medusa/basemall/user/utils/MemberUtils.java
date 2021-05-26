package com.medusa.basemall.user.utils;

import com.medusa.basemall.user.entity.Member;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utils.IdGenerateUtils;
import com.medusa.basemall.utils.RegexMatches;
import com.medusa.basemall.utils.TimeUtil;

public class MemberUtils {

    public static Member getMemberObject(long wxuserId) {
        Member member = new Member();
        member.setAppmodelId(Constant.appmodelId);
        member.setMembershipNumber(IdGenerateUtils.getMembershipNumber());
        member.setPhone(RegexMatches.getTel());
        member.setSupplyBonus(0.0);
        member.setWxuserId(wxuserId);
        member.setCreateTime(TimeUtil.getNowTime());
        member.setState(1);
        return member;
    }
}
