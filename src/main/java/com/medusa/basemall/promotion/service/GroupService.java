package com.medusa.basemall.promotion.service;

import com.medusa.basemall.core.Service;
import com.medusa.basemall.promotion.entity.Group;

import java.util.List;

/**
 * @author Created by psy on 2018/05/30.
 */
public interface GroupService extends Service<Group> {

    /***
     * 根据团id查询团
     *
     * @param groupId
     * @return Group
     */
    Group findByGroupId(Integer groupId);

    /***
     * 查询可以加入的团
     *
     * @param group
     * @return List<Group>
     */
    List<Group> selectGroup(Group group);
}
