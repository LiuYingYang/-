package com.medusa.gruul.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medusa.gruul.platform.api.entity.SysShopPackageOrder;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 店铺套餐订单表 Mapper 接口
 * </p>
 *
 * @author whh
 * @since 2020-08-01
 */
public interface SysShopPackageOrderMapper extends BaseMapper<SysShopPackageOrder> {

    /**
     * 获取历史套餐是否有大于门店版
     *
     * @return 返回数量
     */
    Integer selectBoughtEnterpriseVersion();
}
