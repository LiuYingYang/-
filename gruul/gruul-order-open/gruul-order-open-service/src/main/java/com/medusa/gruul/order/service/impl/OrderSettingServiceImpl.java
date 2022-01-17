package com.medusa.gruul.order.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medusa.gruul.order.api.entity.OrderSetting;
import com.medusa.gruul.order.mapper.OrderSettingMapper;
import com.medusa.gruul.order.service.IOrderSettingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 * 订单设置表 服务实现类
 * </p>
 *
 * @author alan
 * @since 2019 -09-02
 */
@Service
public class OrderSettingServiceImpl extends ServiceImpl<OrderSettingMapper, OrderSetting> implements IOrderSettingService {



    @Override
    public OrderSetting update(OrderSetting setting) {
        if (ObjectUtil.isNotNull(setting.getId())) {
            this.saveOrUpdate(setting);
            return setting;
        }
        return null;
    }
}
