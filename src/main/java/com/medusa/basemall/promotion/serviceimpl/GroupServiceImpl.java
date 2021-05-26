package com.medusa.basemall.promotion.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.promotion.dao.GroupMapper;
import com.medusa.basemall.promotion.entity.Group;
import com.medusa.basemall.promotion.service.GroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author Created by psy on 2018/05/30.
 */
@Service
public class GroupServiceImpl extends AbstractService<Group> implements GroupService {
    @Resource
    private GroupMapper tGroupMapper;

    @Override
    public Group findByGroupId(Integer groupId) {
        return tGroupMapper.findByGroupId(groupId);
    }

    @Override
    public List<Group> selectGroup(Group group) {
        return tGroupMapper.selectGroup(group);
    }
}
