package com.medusa.basemall.user.service;

import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.Service;
import com.medusa.basemall.user.entity.WxuserGroup;

/**
 * Created by medusa on 2018/06/06.
 */
public interface WxuserGroupService extends Service<WxuserGroup> {

    Result createGroup(WxuserGroup wxuserGroup);

    Result deleteGroup(WxuserGroup wxuserGroup);

    Result selectByAppmodelId(WxuserGroup appmodelId);
}
