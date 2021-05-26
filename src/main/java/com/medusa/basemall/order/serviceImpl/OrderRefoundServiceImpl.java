package com.medusa.basemall.order.serviceImpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.order.dao.OrderRefoundMapper;
import com.medusa.basemall.order.entity.OrderRefound;
import com.medusa.basemall.order.service.OrderRefoundService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * Created by medusa on 2018/06/02.
 * 需要事物时添加  @Transactional
 */

@Service
public class OrderRefoundServiceImpl extends AbstractService<OrderRefound> implements OrderRefoundService {

    @Resource
    private OrderRefoundMapper tOrderRefoundMapper;

}
