package com.medusa.gruul.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.medusa.gruul.common.core.util.Result;
import com.medusa.gruul.platform.api.entity.MiniInfo;
import com.medusa.gruul.platform.api.model.dto.LoginDto;
import com.medusa.gruul.platform.model.dto.WxaGetwxacode;

import java.util.List;

/**
 * @author whh
 * @since 2019-09-07
 */
public interface IMiniInfoService extends IService<MiniInfo> {

    /**
     * 根据appId查询小程序
     *
     * @param appId 小程序appId
     * @return com.medusa.gruul.platform.api.entity.MiniInfo
     */
    MiniInfo getByAppId(String appId);

    /**
     * 获取小程序码,返回base64
     *
     * @param wxaGetwxacode com.medusa.gruul.platform.model.dto.WxaGetwxacode
     * @return com.medusa.gruul.common.core.util.Result
     */
    Result<String> wxaGetwxacode(WxaGetwxacode wxaGetwxacode);
}
