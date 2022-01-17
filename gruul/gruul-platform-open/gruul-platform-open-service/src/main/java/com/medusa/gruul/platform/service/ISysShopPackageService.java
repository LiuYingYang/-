package com.medusa.gruul.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.medusa.gruul.platform.api.entity.SysShopPackage;

import java.util.List;

/**
 * <p>
 * 店铺套餐 服务类
 * </p>
 *
 * @author whh
 * @since 2020-08-01
 */
public interface ISysShopPackageService extends IService<SysShopPackage> {

    /**
     * 获取指定模板id下最新版本的套餐
     *
     * @param templateId 模板id
     * @return com.medusa.gruul.platform.api.entity.SysShopPackage
     */
    List<SysShopPackage> getByTemplateLastPackage(Long templateId);



}
