package com.medusa.basemall.article.service;

import com.medusa.basemall.article.entity.LeaveWordLaud;

/**
 * @author Created by wx on 2018/06/07.
 */
public interface LeaveWordLaudService {

    /***
     * 根据用户id和留言id查询留言是否点赞
     *
     * @param wxuserId
     * @param leaveWordId
     * @return LeaveWordLaud
     */
    LeaveWordLaud getByWxuserIdAndLeaveWordId(Long wxuserId, String leaveWordId);
}
