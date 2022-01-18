package com.medusa.gruul.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.medusa.gruul.platform.api.entity.SysShopPackageOrder;

/**
 * <p>
 * 店铺套餐订单表 服务类
 * </p>
 *
 * @author whh
 * @since 2020-08-01
 */
public interface ISysShopPackageOrderService extends IService<SysShopPackageOrder> {


    /**
     * 获取历史套餐是否有大于门店版
     *
     * @return 返回数量
     */
    Integer selectBoughtEnterpriseVersion();


}
