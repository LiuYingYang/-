package com.medusa.basemall.shop.service;

import com.medusa.basemall.core.Service;
import com.medusa.basemall.shop.entity.FirstpageClassify;

import java.util.List;

/**
 * @author Created by wx on 2018/06/04.
 */
public interface FirstpageClassifyService extends Service<FirstpageClassify> {

    /***
     * 根据appmodelId查询首页分类
     *
     * @param appmodelId
     * @return List<FirstpageClassify>
     */
    List<FirstpageClassify> findByAppmodelId(String appmodelId);
}
