package com.medusa.basemall.user.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.user.entity.Wxuser;
import com.medusa.basemall.user.entity.WxuserGroupCategory;

import java.util.List;
import java.util.Map;

public interface WxuserGroupCategoryMapper extends Mapper<WxuserGroupCategory> {

    /**
     * 查询商城下得所有用户分组记录
     *
     * @param appmodelId
     * @return
     */
    List<WxuserGroupCategory> selectWxuserGroup(String appmodelId);

    /**
     * 移除分组下得所有用户
     *
     * @param groupId
     * @return
     */
    int deleteGroup(Integer groupId);

    /**
     * 设置用户分分组
     *
     * @param params
     * @return
     */
    int setWxuserToGroup(Map<String, Object> params);

    /**
     * 删除分组用户
     *
     * @param wxusers
     * @return
     */
    int deleteWxuserId(List<Wxuser> wxusers);
}