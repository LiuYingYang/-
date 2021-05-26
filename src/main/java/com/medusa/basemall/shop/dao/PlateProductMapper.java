package com.medusa.basemall.shop.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.shop.entity.PlateProduct;

import java.util.List;

/**
 * @author Created by wx on 2018/05/26.
 */
public interface PlateProductMapper extends Mapper<PlateProduct> {

    /**
     * 根据商品展示区id删除展示商品
     *
     * @param plateId
     * @return int
     */
    int deleteByPlateId(Integer plateId);

    /**
     * 根据商品展示区id查询展示商品
     *
     * @param plateId
     * @return List<PlateProduct>
     */
    List<PlateProduct> seleteByPlateId(Integer plateId);

	int insertByproductIds(PlateProduct plateProduct);

}