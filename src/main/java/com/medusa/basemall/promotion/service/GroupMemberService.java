package com.medusa.basemall.promotion.service;

import com.medusa.basemall.core.Service;
import com.medusa.basemall.promotion.entity.GroupMember;

import java.util.List;

/**
 * @author Created by psy on 2018/05/30.
 */
public interface GroupMemberService extends Service<GroupMember> {

    /***
     * 查询团id查询团成员
     *
     * @param groupId
     * @return List<GroupMember>
     */
    List<GroupMember> findByGroupId(Integer groupId);
}
