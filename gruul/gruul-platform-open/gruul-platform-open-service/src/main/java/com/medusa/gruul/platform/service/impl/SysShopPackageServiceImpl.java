package com.medusa.gruul.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medusa.gruul.platform.api.entity.SysShopPackage;
import com.medusa.gruul.platform.mapper.SysShopPackageMapper;
import com.medusa.gruul.platform.service.ISysShopPackageService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 店铺套餐 服务实现类
 * </p>
 *
 * @author whh
 * @since 2020-08-01
 */
@Service
public class SysShopPackageServiceImpl extends ServiceImpl<SysShopPackageMapper, SysShopPackage> implements ISysShopPackageService {


    @Override
    public List<SysShopPackage> getByTemplateLastPackage(Long templateId) {
        return this.baseMapper.selectByTemplateLastPackage(templateId);
    }




}
