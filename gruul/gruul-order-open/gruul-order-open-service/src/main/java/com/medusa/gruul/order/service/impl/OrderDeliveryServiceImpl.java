package com.medusa.gruul.order.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medusa.gruul.common.core.exception.ServiceException;
import com.medusa.gruul.common.core.util.SystemCode;
import com.medusa.gruul.order.api.entity.Order;
import com.medusa.gruul.order.api.entity.OrderDelivery;
import com.medusa.gruul.order.api.enums.OrderStatusEnum;
import com.medusa.gruul.order.mapper.OrderDeliveryMapper;
import com.medusa.gruul.order.mapper.OrderMapper;
import com.medusa.gruul.order.model.ReceiverAddressDto;
import com.medusa.gruul.order.service.IOrderDeliveryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * <p>
 * 订单物流信息 服务实现类
 * </p>
 *
 * @author alan
 * @since 2019 -09-02
 */
@Slf4j
@Service
public class OrderDeliveryServiceImpl extends ServiceImpl<OrderDeliveryMapper, OrderDelivery> implements IOrderDeliveryService {
    @Resource
    private OrderMapper orderMapper;

    @Override
    public void updateReceiverAddress(ReceiverAddressDto dto) {
        OrderDelivery orderDelivery = baseMapper.selectById(dto.getOrderId());
        if (ObjectUtil.isNull(orderDelivery)) {
            throw new ServiceException(SystemCode.DATA_NOT_EXIST);
        }
        Order order = orderMapper.selectById(dto.getOrderId());
        if (OrderStatusEnum.canChangeReceiverAddress(order.getStatus())) {
            orderDelivery.setReceiverProvince(dto.getReceiverProvince());
            orderDelivery.setReceiverCity(dto.getReceiverCity());
            orderDelivery.setReceiverRegion(dto.getReceiverRegion());
            orderDelivery.setReceiverDetailAddress(dto.getReceiverDetailAddress());
            orderDelivery.setReceiverName(dto.getReceiverName());
            orderDelivery.setReceiverPhone(dto.getReceiverPhone());
            orderDelivery.setReceiverPostCode(dto.getReceiverPostCode());
            baseMapper.updateById(orderDelivery);
        } else {
            throw new ServiceException("当前状态不允许修改收货地址");

        }
    }
}
