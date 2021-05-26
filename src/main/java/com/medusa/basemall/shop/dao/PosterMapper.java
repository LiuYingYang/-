package com.medusa.basemall.shop.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.shop.entity.Poster;

import java.util.HashMap;
import java.util.List;

/**
 * @author Created by wx on 2018/05/25.
 */
public interface PosterMapper extends Mapper<Poster> {

    /**
     * 根据appmodelId查找商品展示区根据sort值正序排序
     *
     * @param appmodelId
     * @return List<Poster>
     */
    List<Poster> findByAppmodelId(String appmodelId);

    /**
     * 根据appmodelId查找商品展示区根据sort值倒序排序
     *
     * @param appmodelId
     * @return List<Poster>
     */
    List<Poster> findByAppmodelIdDesc(String appmodelId);

    /**
     * 批量删除轮播图
     *
     * @param posterId
     * @return int
     */
    int batchDelete(String[] posterId);

    /**
     * 删除文章/公告/商品/商品分类或下架商品时将链接此对象的轮播图的链接删除
     *
     * @param map
     * @return void
     */
    void updatePoster(HashMap<String, Object> map);
}