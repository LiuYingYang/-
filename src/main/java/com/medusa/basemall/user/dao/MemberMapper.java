package com.medusa.basemall.user.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.user.entity.Member;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface MemberMapper extends Mapper<Member> {

    Member selectByPhone(Map<String, Object> map);

	Member selectUser(@Param("wxuserId") Long wxuserId, @Param("appmodelId") String appmodelId);

    Member selectMemberIntegral(Member member);

    
}