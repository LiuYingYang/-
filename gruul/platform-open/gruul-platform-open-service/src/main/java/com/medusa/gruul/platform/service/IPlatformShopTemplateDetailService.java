package com.medusa.gruul.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.medusa.gruul.platform.api.entity.PlatformShopTemplateDetail;
import com.medusa.gruul.platform.model.vo.SkipUrlVo;

import java.util.List;

/**
 * <p>
 * 店铺模版详情表 服务类
 * </p>
 *
 * @author whh
 * @since 2020-03-06
 */
public interface IPlatformShopTemplateDetailService extends IService<PlatformShopTemplateDetail> {

    /**
     * 获取当前版本配置的跳转地址
     *
     * @return com.medusa.gruul.platform.model.vo.SkipUrlVo
     */
    SkipUrlVo getSkipUrl();

}
