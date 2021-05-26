package com.medusa.basemall.user.service;


import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.Service;
import com.medusa.basemall.user.entity.MemberGroupCategory;
import com.medusa.basemall.user.vo.MemberVo;

/**
 * Created by medusa on 2018/05/26.
 */
public interface MemberGroupCategoryService extends Service<MemberGroupCategory> {


    /**
     * 批量设置会员分组
     *
     * @param params
     * @return
     */
    Result setMemberToGroup(MemberVo params);
}
