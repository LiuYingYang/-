package com.medusa.basemall.shop.service;

import com.medusa.basemall.core.Service;
import com.medusa.basemall.shop.entity.Footer;

import java.util.List;

/**
 * @author Created by wx on 2018/06/04.
 */
public interface FooterService extends Service<Footer> {

    /***
     * 根据appmodelId查询底部导航
     *
     * @param appmodelId
     * @return List<Footer>
     */
    List<Footer> findByAppmoedelId(String appmodelId);
}
