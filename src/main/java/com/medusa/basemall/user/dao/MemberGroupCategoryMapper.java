package com.medusa.basemall.user.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.user.entity.Member;
import com.medusa.basemall.user.entity.MemberGroupCategory;

import java.util.List;
import java.util.Map;

public interface MemberGroupCategoryMapper extends Mapper<MemberGroupCategory> {


    /**
     * 查询商城下得所有会员分组记录
     *
     * @param appmodelId
     * @return
     */
    List<MemberGroupCategory> selectMemberGroup(String appmodelId);

    /**
     * 移除分组下得所有会员
     *
     * @param groupId
     * @return
     */
    int deleteGroup(Integer groupId);

    /**
     * 设置会员分组
     *
     * @param params
     * @return
     */
    int setMemberToGroup(Map<String, Object> params);

    /**
     * 删除分组会员
     *
     * @param members
     * @return
     */
    int deleteMemberId(List<Member> members);
}