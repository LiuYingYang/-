package com.medusa.basemall.promotion.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.promotion.dao.GroupMemberMapper;
import com.medusa.basemall.promotion.entity.GroupMember;
import com.medusa.basemall.promotion.service.GroupMemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author Created by psy on 2018/05/30.
 */
@Service
public class GroupMemberServiceImpl extends AbstractService<GroupMember> implements GroupMemberService {
    @Resource
    private GroupMemberMapper tGroupMemberMapper;

    @Override
    public List<GroupMember> findByGroupId(Integer groupId) {
        return tGroupMemberMapper.findByGroupId(groupId);
    }
}
