package com.medusa.gruul.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medusa.gruul.platform.api.entity.*;
import com.medusa.gruul.platform.mapper.SysShopPackageOrderMapper;
import com.medusa.gruul.platform.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 店铺套餐订单表 服务实现类
 * </p>
 *
 * @author whh
 * @since 2020-08-01
 */
@Service
@Slf4j
public class SysShopPackageOrderServiceImpl extends ServiceImpl<SysShopPackageOrderMapper, SysShopPackageOrder> implements ISysShopPackageOrderService {

    /**
     * 获取历史套餐是否有大于门店版
     *
     * @return 返回数量
     */
    @Override
    public Integer selectBoughtEnterpriseVersion() {
        return this.baseMapper.selectBoughtEnterpriseVersion();
    }

}
