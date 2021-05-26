package com.medusa.basemall.shop.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.shop.entity.Plate;
import com.medusa.basemall.shop.vo.PlateVO;

import java.util.List;

/**
 * @author Created by wx on 2018/05/26.
 */
public interface PlateMapper extends Mapper<Plate> {

    /**
     * 根据appmodelId查找商品展示区根据sort值正序排序
     *
     * @param appmodelId
     * @return List<Plate>
     */
    List<Plate> findByAppmodelId(String appmodelId);

    /**
     * 批量开启或关闭商品展示区
     *
     * @param plateVO
     * @return int
     */
    int batchUpdate(PlateVO plateVO);

    /**
     * 批量删除商品展示区
     *
     * @param plateId
     * @return int
     */
    int batchDelete(String[] plateId);

    /**
     * 根据appmodelId查找商品展示区根据sort值倒序排序
     *
     * @param appmodelId
     * @return List<Plate>
     */
    List<Plate> findByAppmodelIdDesc(String appmodelId);
}