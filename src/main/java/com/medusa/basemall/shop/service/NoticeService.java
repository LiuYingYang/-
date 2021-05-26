package com.medusa.basemall.shop.service;

import com.medusa.basemall.core.Service;
import com.medusa.basemall.shop.entity.Notice;
import com.medusa.basemall.shop.vo.NoticeVO;

import java.util.List;

/**
 * Created by medusa on 2018/05/24.
 */
public interface NoticeService extends Service<Notice> {

    /***
     * 根据appmodelId查询公告
     *
     * @param appmodelId
     * @return List<Notice>
     */
    List<NoticeVO> selectByAppmodelId(String appmodelId);

    /***
     * 批量删除公告
     *
     * @param noticeId
     * @return int
     */
    int batchDelete(String[] noticeId);


	void cleanProduct(List<Long> productId);
}

