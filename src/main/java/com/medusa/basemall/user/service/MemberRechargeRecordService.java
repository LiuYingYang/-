package com.medusa.basemall.user.service;

import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.Service;
import com.medusa.basemall.user.entity.MemberRechargeRecord;

import java.util.List;

/**
 * Created by medusa on 2018/05/30.
 */
public interface MemberRechargeRecordService extends Service<MemberRechargeRecord> {

    Result payRechargeActive(Long wxuserId, Long MemberId, double price, Integer activeRechargeId, String appmodelId, int type, String userIp);

    String payRechargeActiveNotify(String xmlResult);

    List<MemberRechargeRecord> findByMemberId(Long memberId);
}
