package com.medusa.basemall.shop.service;

import com.medusa.basemall.core.Service;
import com.medusa.basemall.shop.entity.PlateProduct;

import java.util.List;

/**
 * Created by medusa on 2018/05/26.
 */
public interface PlateProductService extends Service<PlateProduct> {

    /***
     * 根据商品展示区id查询展示区商品
     *
     * @param plateId
     * @return List<PlateProduct>
     */
    List<PlateProduct> seleteByPlateId(Integer plateId);

    /***
     * 根据商品展示区id删除展示区商品
     *
     * @param plateId
     * @return int
     */
    int deleteByPlateId(Integer plateId);

    int insertByproductIds(PlateProduct plateProduct);

	int deleteByPlateIdAndProduct(Integer plateId, Long productId);

}
