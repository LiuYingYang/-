package com.medusa.gruul.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.medusa.gruul.order.api.entity.OrderSetting;

/**
 * <p>
 * 订单设置表 服务类
 * </p>
 *
 * @author alan
 * @since 2019 -09-02
 */
public interface IOrderSettingService extends IService<OrderSetting> {


    /**
     * Update order setting.
     *
     * @param setting the setting
     * @return the order setting
     */
    OrderSetting update(OrderSetting setting);
}
