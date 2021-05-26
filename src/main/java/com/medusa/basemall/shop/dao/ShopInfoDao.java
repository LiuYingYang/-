package com.medusa.basemall.shop.dao;

import com.medusa.basemall.shop.entity.ShopInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Created by wx on 2018/05/25.
 */
@Repository
public interface ShopInfoDao extends MongoRepository<ShopInfo, String> {

    /**
     * 根据appmodelId查询店铺信息
     *
     * @param appmodelId
     * @return ShopInfo
     */
    ShopInfo getByAppmodelId(String appmodelId);
}
