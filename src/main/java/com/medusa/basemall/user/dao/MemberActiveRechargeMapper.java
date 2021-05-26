package com.medusa.basemall.user.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.user.entity.MemberActiveRecharge;

import java.util.Map;

public interface MemberActiveRechargeMapper extends Mapper<MemberActiveRecharge> {
    int OpenOrCloseActive(Map<String, Object> map);
}