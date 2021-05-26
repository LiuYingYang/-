package com.medusa.basemall.promotion.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.order.vo.GroupVo;
import com.medusa.basemall.promotion.entity.Group;

import java.util.List;

/**
 * @author Created by psy on 2018/05/30.
 */
public interface GroupMapper extends Mapper<Group> {

    /***
     * 根据团id查询对应的正在组的团
     *
     * @param groupId
     * @return Group
     */
    Group findByGroupId(Integer groupId);

    /***
     * 查询用户可以加入的团
     *
     * @param group
     * @return List<Group>
     */
    List<Group> selectGroup(Group group);

    /***
     * 根据团id查询团
     *
     * @param groupId
     * @return GroupVo
     */
    GroupVo selectByGroupId(Integer groupId);
}