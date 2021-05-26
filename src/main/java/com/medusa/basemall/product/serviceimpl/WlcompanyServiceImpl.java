package com.medusa.basemall.product.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.product.dao.WlcompanyMapper;
import com.medusa.basemall.product.entity.Wlcompany;
import com.medusa.basemall.product.service.WlcompanyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * Created by psy on 2018/05/24.
 * 需要事物时添加  @Transactional
 */

@Service

public class WlcompanyServiceImpl extends AbstractService<Wlcompany> implements WlcompanyService {
    @Resource
    private WlcompanyMapper tWlcompanyMapper;
}
