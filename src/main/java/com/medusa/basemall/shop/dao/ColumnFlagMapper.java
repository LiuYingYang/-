package com.medusa.basemall.shop.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.shop.entity.ColumnFlag;

/**
 * @author Created by wx on 2018/06/04.
 */
public interface ColumnFlagMapper extends Mapper<ColumnFlag> {

    /**
     * 根据appmodelId查找店铺开关
     *
     * @param appmodelId
     * @return ColumnFlag
     */
    ColumnFlag findByAppmodelId(String appmodelId);

}