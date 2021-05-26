package com.medusa.basemall.promotion.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.promotion.entity.GroupMember;

import java.util.List;
import java.util.Map;

/**
 * @author Created by psy on 2018/05/30.
 */
public interface GroupMemberMapper extends Mapper<GroupMember> {

    /***
     * 根据团id查询团成员
     *
     * @param groupId
     * @return List<GroupMember>
     */
    List<GroupMember> findByGroupId(Integer groupId);

    /***
     * 根据订单id查询团成员
     *
     * @param orderId
     * @return GroupMember
     */
    GroupMember findByOrderId(Long orderId);

    /***
     * 根据用户id和团id查询团成员
     *
     * @param map
     * @return GroupMember
     */
    GroupMember findByWxuserIdAndGroupId(Map<String,Object> map);

    /***
     * 根据用户id查询用户参的团
     *
     * @param wxuserId
     * @return List<GroupMember>
     */
    List<GroupMember> findByWxuserId(Long wxuserId);

}