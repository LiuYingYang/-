package com.medusa.basemall.user.service;

import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.Service;
import com.medusa.basemall.user.entity.MemberRank;

/**
 * Created by medusa on 2018/05/24.
 */
public interface MemberRankService extends Service<MemberRank> {

    Result createRank(MemberRank memberRank);
    /**
     * 默认初始会员卡无法删除 只能修改
     *
     * @param memberRank
     * @return
     */
    Result deleteRank(Integer memberRank);

    Result updateRank(MemberRank memberRank);
}
