package com.medusa.basemall.shop.service;

import com.medusa.basemall.core.Service;
import com.medusa.basemall.shop.entity.Poster;
import com.medusa.basemall.shop.vo.PosterVO;

import java.util.List;

/**
 * @author Created by wx on 2018/05/25.
 */
public interface PosterService extends Service<Poster> {

    /***
     * 根据appmodelId查询轮播图按sort值正序排序
     *
     * @param appmodelId
     * @return List<Poster>
     */
    List<PosterVO> findByAppmodelId(String appmodelId);

    /***
     * 批量删除轮播图
     *
     * @param posterId
     * @return int
     */
    int batchDelete(String[] posterId);

    /***
     * 根据appmodelId查询轮播图按sort值倒序排序
     *
     * @param appmodelId
     * @return List<Poster>
     */
    List<Poster> findByAppmodelIdDesc(String appmodelId);
}
