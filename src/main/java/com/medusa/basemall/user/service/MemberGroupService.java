package com.medusa.basemall.user.service;

import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.Service;
import com.medusa.basemall.user.entity.MemberGroup;

/**
 * Created by medusa on 2018/05/26.
 */
public interface MemberGroupService extends Service<MemberGroup> {

    Result createGroup(MemberGroup memberGroup);

    Result deleteGroup(MemberGroup memberGroup);

    Result selectByAppmodelId(MemberGroup memberGroup);
}
