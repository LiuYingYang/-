package com.medusa.basemall.shop.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.shop.entity.Footer;

import java.util.List;

/**
 * @author Created by medusawx on 2018/06/04.
 */
public interface FooterMapper extends Mapper<Footer> {

    /**
     * 根据appmodelId底部导航
     *
     * @param appmodelId
     * @return List<Footer>
     */
    List<Footer> findByAppmoedelId(String appmodelId);
}