package com.medusa.gruul.platform.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaQrcodeService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.util.Base64;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medusa.gruul.account.api.conf.MiniInfoProperty;
import com.medusa.gruul.common.core.constant.TimeConstants;
import com.medusa.gruul.common.core.util.Result;
import com.medusa.gruul.platform.api.entity.*;
import com.medusa.gruul.platform.api.feign.RemoteMiniInfoService;
import com.medusa.gruul.platform.conf.MeConstant;
import com.medusa.gruul.platform.constant.MpPermissionEnum;
import com.medusa.gruul.platform.mapper.MiniInfoMapper;
import com.medusa.gruul.platform.model.dto.WxaGetwxacode;
import com.medusa.gruul.platform.service.*;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenMaService;
import me.chanjar.weixin.open.api.WxOpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 小程序基本信息(非授权时获取的) 服务实现类
 * </p>
 *
 * @author whh
 * @since 2019-09-07
 */
@Service
public class MiniInfoServiceImpl extends ServiceImpl<MiniInfoMapper, MiniInfo> implements IMiniInfoService {

    @Autowired
    private RemoteMiniInfoService remoteMiniInfoService;
    @Autowired
    private MiniInfoProperty miniInfoProperty;


    private static Map<Integer, String> MP_PERMISSION = new HashMap<>(16);

    /**
     * 存储缓存(微信体验码)
     */
    private TimedCache<String, String> timedCache = CacheUtil.newTimedCache(TimeConstants.ONE_DAY);

    static {
        for (MpPermissionEnum value : MpPermissionEnum.values()) {
            MP_PERMISSION.put(value.getCode(), value.getDesc());
        }
    }

    @Override
    public MiniInfo getByAppId(String appId) {
        return this.getBaseMapper().selectOne(new QueryWrapper<MiniInfo>().eq("app_id", appId));
    }

    @Override
    public Result<String> wxaGetwxacode(WxaGetwxacode wxaGetwxacode) {
        Integer width = wxaGetwxacode.getWidth();
        if (width == null || width.equals(0)) {
            width = 430;
        }
        String key = miniInfoProperty.getAppId().concat(":").concat(wxaGetwxacode.getPath()).concat(":").concat(width.toString());
        String base64 = timedCache.get(key);
        if (StrUtil.isNotEmpty(base64)) {
            return Result.ok(base64);
        }
        WxMaService wxMaService = remoteMiniInfoService.getWxMaService();
        WxMaQrcodeService qrcodeService = wxMaService.getQrcodeService();
        try {
            byte[] qrcodeBytes = qrcodeService.createWxaCodeBytes(wxaGetwxacode.getPath(), width, true, null, false);
            base64 = "data:image/png;base64," + Base64.byteArrayToBase64(qrcodeBytes);
            timedCache.put(key, base64);
            return Result.ok(base64);
        } catch (WxErrorException e) {
            e.printStackTrace();
            String msg = "系统异常";
            if (e.getError().getErrorCode() == MeConstant.MINI_CODE_61007) {
                msg = "未授权小程管理权限,无法获取小程序二维码";
            }
            return Result.failed(e.getError().getErrorCode(), msg);
        }
    }
}
