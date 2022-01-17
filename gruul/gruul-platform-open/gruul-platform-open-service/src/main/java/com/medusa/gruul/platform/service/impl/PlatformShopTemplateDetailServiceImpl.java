package com.medusa.gruul.platform.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medusa.gruul.common.core.exception.ServiceException;
import com.medusa.gruul.platform.api.entity.*;
import com.medusa.gruul.platform.mapper.PlatformShopTemplateDetailMapper;
import com.medusa.gruul.platform.model.vo.SkipUrlVo;
import com.medusa.gruul.platform.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 店铺模版详情表 服务实现类
 * </p>
 *
 * @author whh
 * @since 2020-03-06
 */
@Service
public class PlatformShopTemplateDetailServiceImpl extends ServiceImpl<PlatformShopTemplateDetailMapper, PlatformShopTemplateDetail> implements IPlatformShopTemplateDetailService {

    @Autowired
    private IPlatformShopInfoService platformShopInfoService;



    @Override
    public SkipUrlVo getSkipUrl() {
        PlatformShopInfo platformShopInfo = platformShopInfoService.getInfo();
        if (platformShopInfo == null) {
            throw new ServiceException("非法操作");
        }
        PlatformShopTemplateDetail platformShopTemplateDetail = this.getBaseMapper().selectById(platformShopInfo.getShopTemplateDetailId());
        if (platformShopTemplateDetail == null) {
            throw new ServiceException("不存在数据");
        }

        SkipUrlVo vo = new SkipUrlVo();
        BeanUtil.copyProperties(platformShopTemplateDetail, vo);
        String pcUrlMap = platformShopTemplateDetail.getPcUrlMap();
        if (StrUtil.isNotEmpty(pcUrlMap)) {
            JSONObject jsonObject = JSONObject.parseObject(pcUrlMap);
            vo.setRegionalUrl(jsonObject.getString("RegionalUrl"));
        }
        return vo;
    }

}
