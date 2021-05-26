package com.medusa.basemall.order.dao;

import com.medusa.basemall.order.entity.CommonlyUsed;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Created by wx on 2018/09/13.
 */
public interface CommonlyUsedRepository extends MongoRepository<CommonlyUsed, String> {

    /***
     * 根据appmodelId查询商家设置的常用营销应用
     *
     * @param appmodelId
     * @return CommonlyUsed
     */
    CommonlyUsed findByAppmodelId(String appmodelId);
}
