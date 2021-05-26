package com.medusa.basemall.shop.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.shop.entity.SearchWord;

import java.util.List;
import java.util.Map;

/**
 * @author Created by wx on 2018/05/25.
 */
public interface SearchWordMapper extends Mapper<SearchWord> {

    /**
     * 批量删除搜索词
     *
     * @param searchWordId
     * @return int
     */
    int batchDelete(String[] searchWordId);

    /**
     * 根据appmodelId查询搜索词
     *
     * @param appmodelId
     * @return List<SearchWord>
     */
    List<SearchWord> findByAppmodelId(String appmodelId);

    /**
     * 根据搜索词类型查询搜索词
     *
     * @param map
     * @return List<SearchWord>
     */
    List<SearchWord> findByType(Map<String,Object> map);
}