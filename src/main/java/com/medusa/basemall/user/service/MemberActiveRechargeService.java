package com.medusa.basemall.user.service;

import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.Service;
import com.medusa.basemall.user.entity.MemberActiveRecharge;

/**
 * Created by medusa on 2018/05/30.
 */
public interface MemberActiveRechargeService extends Service<MemberActiveRecharge> {

    Result createRecharge(MemberActiveRecharge memberActiveRecharge);

    Result updateRecharge(MemberActiveRecharge memberActiveRecharge);

    Result selectAppmodelId(String appmodelId);

    Result OpenOrCloseActive(String appmodelId, int type);
}
