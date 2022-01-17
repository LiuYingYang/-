package com.medusa.gruul.order.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medusa.gruul.common.core.constant.CommonConstants;
import com.medusa.gruul.order.api.constant.OrderShareSettingRedisKey;
import com.medusa.gruul.order.api.entity.OrderShareSetting;
import com.medusa.gruul.order.mapper.OrderShareSettingMapper;
import com.medusa.gruul.order.service.IOrderShareSettingService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单设置表 服务实现类
 * </p>
 *
 * @author alan
 * @since 2019 -09-02
 */
@Service
public class OrderShareSettingServiceImpl extends ServiceImpl<OrderShareSettingMapper, OrderShareSetting> implements IOrderShareSettingService {



    @Override
    public OrderShareSetting getSetting() {
        OrderShareSettingRedisKey redisKey = new OrderShareSettingRedisKey();
        OrderShareSetting shareSetting = redisKey.getObject("order", new OrderShareSetting());
        if (ObjectUtil.isNull(shareSetting)) {
            shareSetting = this.getOne(null);
            redisKey.setObject("order", shareSetting);
        }
        return shareSetting;
    }
}
