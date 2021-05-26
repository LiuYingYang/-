package com.medusa.basemall.user.service;

import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.Service;
import com.medusa.basemall.user.entity.WxuserGroupCategory;

import java.util.Map;

/**
 * Created by medusa on 2018/06/06.
 */
public interface WxuserGroupCategoryService extends Service<WxuserGroupCategory> {

    /**
     * 批量设置用户分组
     *
     * @param map
     * @return
     */
    Result setWxuserToGroup(Map<String, Object> map);
}
