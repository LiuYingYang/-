package com.medusa.basemall.shop.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.shop.entity.FirstpageClassify;

import java.util.HashMap;
import java.util.List;

/**
 * @author Created by wx on 2018/06/04.
 */
public interface FirstpageClassifyMapper extends Mapper<FirstpageClassify> {

    /**
     * 根据appmodelId查找首页分类根据sort值正序排序
     *
     * @param appmodelId
     * @return List<FirstpageClassify>
     */
    List<FirstpageClassify> findByAppmodelId(String appmodelId);

    /**
     * 根据appmodelId查找首页分类根据sort值倒序排序
     *
     * @param appmodelId
     * @return List<FirstpageClassify>
     */
    List<FirstpageClassify> findByAppmodelIdDesc(String appmodelId);

    /**
     * 删除文章/公告/商品/商品分类或下架商品时将链接此对象的首页分类的链接删除
     *
     * @param map
     * @return void
     */
    void updateFirstpage(HashMap<String, Object> map);
}