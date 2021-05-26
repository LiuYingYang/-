package com.medusa.basemall.shop.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.shop.entity.Notice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Created by wx on 2018/05/24.
 */
public interface NoticeMapper extends Mapper<Notice> {

    /**
     * 根据appmodelId底部导航
     *
     * @param appmodelId
     * @return List<Notice>
     */
    List<Notice> selectByAppmodelId(String appmodelId);

    /**
     * 批量删除公告
     *
     * @param noticeId
     * @return int
     */
    int batchDelete(String[] noticeId);

	/**
	 *清空相关商品链接
	 * @param productIds
	 */
	void cleanProduct(@Param("productIds") List<Long> productIds);
}