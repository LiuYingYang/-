package com.medusa.gruul.platform.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medusa.gruul.common.core.constant.CommonConstants;
import com.medusa.gruul.common.core.exception.ServiceException;
import com.medusa.gruul.common.core.util.*;
import com.medusa.gruul.common.dto.CurShopInfoDto;
import com.medusa.gruul.common.dto.CurUserDto;
import com.medusa.gruul.platform.api.entity.*;
import com.medusa.gruul.platform.api.enums.QueueEnum;
import com.medusa.gruul.platform.api.model.dto.ShopConfigDto;
import com.medusa.gruul.platform.api.model.dto.ShopInfoDto;
import com.medusa.gruul.platform.api.model.dto.ShopPackageFunctionDto;
import com.medusa.gruul.platform.api.model.vo.PayInfoVo;
import com.medusa.gruul.platform.conf.MeConstant;
import com.medusa.gruul.platform.conf.PlatformRedis;
import com.medusa.gruul.platform.constant.RedisConstant;
import com.medusa.gruul.platform.mapper.PlatformPayConfigMapper;
import com.medusa.gruul.platform.mapper.PlatformShopInfoMapper;
import com.medusa.gruul.platform.mapper.PlatformShopTemplateInfoMapper;
import com.medusa.gruul.platform.model.dto.ConsoleUpdateDto;
import com.medusa.gruul.platform.model.dto.PayInfoUpdateDto;
import com.medusa.gruul.platform.model.vo.*;
import com.medusa.gruul.platform.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 店铺信息表 服务实现类
 * </p>
 *
 * @author whh
 * @since 2020-03-06
 */
@Service
@Slf4j
public class PlatformShopInfoServiceImpl extends ServiceImpl<PlatformShopInfoMapper, PlatformShopInfo> implements IPlatformShopInfoService {

    @Autowired
    private IAccountInfoService accountInfoService;
    @Autowired
    private PlatformShopTemplateInfoMapper platformShopTemplateInfoMapper;
    @Autowired
    private IPlatformShopTemplateDetailService platformShopTemplateDetailService;
    @Autowired
    private IMiniInfoService miniInfoService;
    @Autowired
    private PlatformPayConfigMapper platformPayConfigMapper;
    @Autowired
    private ISysShopPackageOrderService sysShopPackageOrderService;
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public void consoleUpdate(ConsoleUpdateDto dto) {
        PlatformShopInfo platformShopInfo = check();
        PlatformShopInfo up = new PlatformShopInfo();
        BeanUtils.copyProperties(dto, up);
        up.setId(platformShopInfo.getId());
        this.updateById(up);
        PlatformShopInfo info = this.getById(platformShopInfo.getId());
        initShopCache(info);
    }

    /**
     * @return
     */
    @Override
    public PlatformShopInfo check() {
        CurUserDto httpCurUser = CurUserUtil.getHttpCurUser();
        if (httpCurUser == null) {
            throw new ServiceException("无效token");
        }
        //校验商家是否存在
        AccountInfo accountInfo = accountInfoService.getById(httpCurUser.getUserId());
        if (accountInfo == null) {
            throw new ServiceException("错误账号");
        }
        PlatformShopInfo shopInfo = this.getOne(new QueryWrapper<>());
        if (shopInfo == null) {
            throw new ServiceException("不存在该店铺");
        }
        if (!shopInfo.getAccountId().equals(accountInfo.getId())) {
            throw new ServiceException("非法操作");
        }
        return shopInfo;
    }

    @Override
    public void closeOrOpen() {
        PlatformShopInfo platformShopInfo = check();
        if (platformShopInfo.getStatus() < CommonConstants.NUMBER_TWO && platformShopInfo.getStatus() > CommonConstants.NUMBER_THREE) {
            throw new ServiceException("店铺当前状态无法打烊或者营业");
        }
        if (platformShopInfo.getStatus().equals(CommonConstants.NUMBER_TWO)) {
            platformShopInfo.setStatus(CommonConstants.NUMBER_THREE);
        } else {
            platformShopInfo.setStatus(CommonConstants.NUMBER_TWO);
        }
        this.updateById(platformShopInfo);
        PlatformShopInfo info = this.getOne(new QueryWrapper<>());
        initShopCache(info);
    }


    @Override
    public LoginShopInfoVo getLoginShopInfoVo(PlatformShopInfo shopInfo) {
        LoginShopInfoVo infoVo = new LoginShopInfoVo();
        infoVo.setMiniId(shopInfo.getBindMiniId());
        infoVo.setPlatformShopId(shopInfo.getId());
        infoVo.setMpId(shopInfo.getBindMpId());
        infoVo.setLogoUrl(shopInfo.getLogoUrl());
        infoVo.setShopName(shopInfo.getShopName());
        return infoVo;
    }

    @Override
    public PlatformShopInfo getInfo() {
        return this.baseMapper.selectOne(new QueryWrapper<>());
    }

    @Override
    public PayInfoVo payInfo(Integer type, String code) {
        PlatformShopInfo platformShopInfo = this.getOne(new QueryWrapper<>());

        PlatformPayConfig platformPayConfig = platformPayConfigMapper.selectOne(new QueryWrapper<>());
        PayInfoVo payInfoVo = new PayInfoVo();
        BeanUtil.copyProperties(platformPayConfig, payInfoVo);
        payInfoVo.setCertificatesPath(platformPayConfig.getCertificatePath());
        payInfoVo.setPayType(platformShopInfo.getPayType());
        payInfoVo.setCertificatesState(StrUtil.isNotEmpty(platformPayConfig.getCertificatePath()));
        // type 1-默认加密  2-明文数据需带上code  3-内部调用  todo s1.0.7之后是否需要废弃前两个类型待定 1,2
        if (CommonConstants.NUMBER_ONE.equals(type)) {
            payInfoVo.setCertificatesPath("");
            payInfoVo.setIpsMerCode(encryptStart(payInfoVo.getIpsMerCode()));
            payInfoVo.setIpsAccCode(encryptStart(payInfoVo.getIpsAccCode()));
            payInfoVo.setIpsCertificatePsw(encryptStart(payInfoVo.getIpsCertificatePsw()));
            payInfoVo.setIpsRsaPublicKey(encryptStart(payInfoVo.getIpsRsaPublicKey()));
            payInfoVo.setIpsRsaPrivateKey(encryptStart(payInfoVo.getIpsRsaPrivateKey()));
            payInfoVo.setIpsAes(encryptStart(payInfoVo.getIpsAes()));
            payInfoVo.setIpsSha(encryptStart(payInfoVo.getIpsSha()));
            payInfoVo.setSxfOrgId(encryptStart(payInfoVo.getSxfOrgId()));
            payInfoVo.setSxfAccCode(encryptStart(payInfoVo.getSxfAccCode()));
            payInfoVo.setSxfCertificatePsw(encryptStart(payInfoVo.getSxfCertificatePsw()));
            payInfoVo.setSxfPublic(encryptStart(payInfoVo.getSxfPublic()));
            payInfoVo.setSxfPrivateKey(encryptStart(payInfoVo.getSxfPrivateKey()));
            payInfoVo.setMchId(encryptStart(payInfoVo.getMchId()));
            payInfoVo.setMchKey(encryptStart(payInfoVo.getMchKey()));
            return payInfoVo;
        }
        if (type.equals(CommonConstants.NUMBER_TWO)) {
            payInfoVo.setCertificatesPath("");
            //校验state数据是否存在
            String jsonData = new PlatformRedis().get(code.concat(":inside"));
            if (StrUtil.isEmpty(jsonData)) {
                throw new ServiceException("校验事变");
            }
            Result result = JSONObject.parseObject(jsonData, Result.class);
            if (result.getCode() != CommonConstants.SUCCESS) {
                throw new ServiceException("扫码异常:" + result.getMsg());
            }
        }
        return payInfoVo;
    }

    /**
     * 加密中间字符串为***
     *
     * @param str 字符串
     * @return
     */
    private String encryptStart(String str) {
        if (StrUtil.isEmpty(str)) {
            return "";
        }
        String encryptStart = StrUtil.sub(str, 0, 3).concat("****").concat(StrUtil.sub(str, str.length() - 3, str.length()));
        return encryptStart;
    }

    @Override
    public String uploadCertificate(MultipartFile file) {
        if (file == null) {
            throw new ServiceException("文件为空", SystemCode.DATA_NOT_EXIST_CODE);
        }
        if (StrUtil.isEmpty(file.getOriginalFilename())) {
            throw new ServiceException("文件格式不正确,请上传微信支付API证书apiclient_cert.p12", SystemCode.PARAM_TYPE_ERROR.getCode());
        }
        int index = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".");
        String postfix = file.getOriginalFilename().substring(index);
        if (!MeConstant.P12.equals(postfix)) {
            throw new ServiceException("文件格式不正确,请上传微信支付API证书apiclient_cert.p12", SystemCode.PARAM_TYPE_ERROR.getCode());
        }
        PlatformShopInfo platformShopInfo = null;
        platformShopInfo = this.getOne(new QueryWrapper<>());
        if (platformShopInfo == null) {
            throw new ServiceException("无效数据请求");
        }
        String dir = "/data/pay-certificate/".concat(platformShopInfo.getShopName()).concat("/apiclient_cert.p12");
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(dir));
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException("文件上传失败", SystemCode.FAILURE.getCode());
        }
        return dir;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void payInfoUpdate(PayInfoUpdateDto payInfoUpdateDto) {
        PlatformShopInfo platformShopInfo = this.getOne(new QueryWrapper<>());
        //修改当前支付渠道
        platformShopInfo.setPayType(payInfoUpdateDto.getPayType());
        this.updateById(platformShopInfo);
        //修改微信支付配置
        PlatformPayConfig platformPayConfig = platformPayConfigMapper.selectOne(new QueryWrapper<>());
        if (platformPayConfig == null) {
            throw new ServiceException("数据异常,请联系平台");
        }
        platformPayConfig.setMchId(payInfoUpdateDto.getMchId());
        platformPayConfig.setMchKey(payInfoUpdateDto.getMchKey());
        platformPayConfig.setCertificatePath(payInfoUpdateDto.getCertificatesPath());
        //修改其他类型配置
        switch (payInfoUpdateDto.getPayType()) {
            case 2:
                payInfoUpdateDto.ipsValidate();
                platformPayConfig.setIpsMerCode(payInfoUpdateDto.getIpsMerCode());
                platformPayConfig.setIpsAccCode(payInfoUpdateDto.getIpsAccCode());
                platformPayConfig.setIpsCertificatePsw(payInfoUpdateDto.getIpsCertificatePsw());
                platformPayConfig.setIpsRsaPublicKey(payInfoUpdateDto.getIpsRsaPublicKey());
                platformPayConfig.setIpsRsaPrivateKey(payInfoUpdateDto.getIpsRsaPrivateKey());
                platformPayConfig.setIpsAes(payInfoUpdateDto.getIpsAes());
                platformPayConfig.setIpsSha(payInfoUpdateDto.getIpsSha());
                platformPayConfigMapper.updateById(platformPayConfig);
                break;
            case 3:
                payInfoUpdateDto.sxfValidate();
                platformPayConfig.setSxfOrgId(payInfoUpdateDto.getSxfOrgId());
                platformPayConfig.setSxfAccCode(payInfoUpdateDto.getSxfAccCode());
                platformPayConfig.setSxfCertificatePsw(payInfoUpdateDto.getSxfCertificatePsw());
                platformPayConfig.setSxfPublic(payInfoUpdateDto.getSxfPublic());
                platformPayConfig.setSxfPrivateKey(payInfoUpdateDto.getSxfPrivateKey());
                platformPayConfigMapper.updateById(platformPayConfig);
                break;
            case 4:
                payInfoUpdateDto.sftValidate();
                platformPayConfig.setSftChannelId(payInfoUpdateDto.getSftChannelId());
                platformPayConfig.setSftMd5(payInfoUpdateDto.getSftMd5());
                platformPayConfig.setSftSubMerchantNo(payInfoUpdateDto.getSftSubMerchantNo());
                platformPayConfig.setSftTerminalId(payInfoUpdateDto.getSftTerminalId());
                platformPayConfigMapper.updateById(platformPayConfig);
                break;
            default:
                platformPayConfigMapper.updateById(platformPayConfig);
                break;
        }
    }


    @Override
    public ShopInfoVo shopInfo() {
        PlatformShopInfo platformShopInfo = this.getOne(new QueryWrapper<>());
        ShopInfoVo shopInfoVo = BeanUtil.toBean(platformShopInfo, ShopInfoVo.class);
        shopInfoVo.setPlatformShopId(platformShopInfo.getId());
        String businessHours = platformShopInfo.getBusinessHours();
        if (StrUtil.isNotEmpty(businessHours) && shopInfoVo.getStatus().equals(CommonConstants.NUMBER_TWO)) {
            JSONArray timeArr = JSON.parseArray(businessHours);
            DateTime start = DateUtil.parseTimeToday(DateUtil.formatTime(DateUtil.parse(timeArr.getString(0))));
            DateTime end = DateUtil.parseTimeToday(DateUtil.formatTime(DateUtil.parse(timeArr.getString(1))));
            if (!DateUtil.isIn(new Date(), start, end)) {
                shopInfoVo.setStatus(CommonConstants.NUMBER_FIVE);
            }
        }
        PlatformShopTemplateInfo templateInfo = platformShopTemplateInfoMapper.selectById(platformShopInfo.getShopTemplateId());
        if (templateInfo != null) {
            shopInfoVo.setTemplateName(templateInfo.getName());
            String teamplateVersion = "";
            if (platformShopInfo.getShopTemplateDetailId() != null || platformShopInfo.getShopTemplateDetailId() > 0) {
                PlatformShopTemplateDetail templateDetail = platformShopTemplateDetailService.getById(platformShopInfo.getShopTemplateDetailId());
                if (templateDetail != null) {
                    teamplateVersion = templateDetail.getVersion();
                }
            }
            shopInfoVo.setTeamplateVersion(teamplateVersion);
        }
        if (platformShopInfo.getPackageId() != null) {
            SysShopPackageOrder sysShopPackageOrder = sysShopPackageOrderService.getById(platformShopInfo.getPackageOrderId());
            if (sysShopPackageOrder != null) {
                SysShopPackage sysShopPackage = JSON.parseObject(sysShopPackageOrder.getPackageData(), SysShopPackage.class);
                shopInfoVo.setLevel(sysShopPackage.getLevel());
                shopInfoVo.setPackageName(sysShopPackage.getName());
                shopInfoVo.setOrderSource(sysShopPackageOrder.getOrderSource());
            }
        }
        return shopInfoVo;
    }

    @Override
    public ShopConfigDto getShopConfig() {
        ShopConfigDto shopConfigDto = new ShopConfigDto();
        shopConfigDto.setPayInfo(payInfo(CommonConstants.NUMBER_THREE, ""));
        return shopConfigDto;
    }



    @Override
    public Result<ShopInfoDto> getShopInfo() {
        PlatformShopInfo platformShopInfo = this.getOne(new QueryWrapper<>());
        if (platformShopInfo == null) {
            return Result.failed("无效店铺数据");
        }
        ShopInfoDto infoDto = new ShopInfoDto();
        BeanUtil.copyProperties(platformShopInfo, infoDto);
        return Result.ok(infoDto);
    }




    @Override
    public void validateShopPastDue() {
        DateTime currentTime = DateUtil.date();
        DateTime startTime = DateUtil.beginOfDay(currentTime);
        DateTime endTime = DateUtil.endOfDay(currentTime);
        //搜索当日到期店铺
        List<PlatformShopInfo> platformShopInfos = this.getBaseMapper().selectList(new QueryWrapper<PlatformShopInfo>()
                .and(i -> i.isNull("due_time")
                        .or(j -> j.lt("due_time", endTime.toString()))
                ).eq("is_due", 0));

        if (CollectionUtil.isEmpty(platformShopInfos)) {
            return;
        }
        //套餐到期处理
        LocalDateTime now = LocalDateTime.now();
        for (PlatformShopInfo platformShopInfo : platformShopInfos) {
            LocalDateTime dueTime = platformShopInfo.getDueTime();
            Duration between = Duration.ofSeconds(0);
            if (dueTime != null) {
                between = LocalDateTimeUtil.between(now, dueTime);
            }
            QueueEnum platformPackageDue = QueueEnum.PLATFORM_PACKAGE_DUE;
            Duration finalBetween = between;
            rabbitTemplate.convertAndSend(platformPackageDue.getExchange(), platformPackageDue.getRouteKey(), platformShopInfo.getId(),
                    message -> {
                        message.getMessageProperties().setDelay(((Long) finalBetween.getSeconds()).intValue());
                        return message;
                    }, new CorrelationData(IdUtil.fastSimpleUUID()));
        }

        DateTime dateTime = DateUtil.offsetDay(new Date(), 7);
        List<PlatformShopInfo> shopInfos = this.getBaseMapper().selectList(new QueryWrapper<PlatformShopInfo>()
                .le("due_time", dateTime.toSqlDate())
                .eq("is_due", 0));
        if (CollectionUtil.isEmpty(shopInfos)) {
            return;
        }
        agentNotify(shopInfos);

    }

    /**
     * 店铺到期通知
     *
     * @param shopInfos com.medusa.gruul.platform.api.entity.PlatformShopInfo
     */
    private void agentNotify(List<PlatformShopInfo> shopInfos) {
        for (PlatformShopInfo shopInfo : shopInfos) {
            Date currentDate = new Date();
            Long day = DateUtil.betweenDay(currentDate, LocalDateTimeUtils.convertLDTToDate(shopInfo.getDueTime()), false);
            //计算到期通知
            if (day <= 7 && day > 0) {
                PlatformRedis platformRedis = new PlatformRedis();
                String rv = platformRedis
                        .get(RedisConstant.SHOP_DUETIME_NOTIFY);
                if (StrUtil.isNotEmpty(rv)) {
                    break;
                }
                //计算即将到期通知
                LinkedList<String> titles = new LinkedList<>();
                MiniInfo miniInfo = miniInfoService.getOne(new QueryWrapper<>());
                String miniName = "-";
                if (miniInfo != null) {
                    miniName = miniInfo.getMiniName();
                }
                titles.add(miniName);
                titles.add(shopInfo.getShopName());
                titles.add(day.toString());
                //计算距离当天结束时间,设置当天缓存
                DateTime endOfDay = DateUtil.endOfDay(currentDate);
                long betweenTime = DateUtil.between(currentDate, endOfDay, DateUnit.MS);
                platformRedis.setNxPx(rv, "1", betweenTime);
            }
        }
    }

    @Override
    public void packageDueReceive(Integer platformInfoId) {
        PlatformShopInfo shopInfo = getById(platformInfoId);
        if (shopInfo == null) {
            throw new ServiceException("套餐过期失败,不存在该店铺 : 店铺id {}", platformInfoId);
        }
        if (null != shopInfo.getDueTime() && LocalDateTimeUtils.getMilliByTime(shopInfo.getDueTime()) > System.currentTimeMillis()) {
            log.warn("店铺未到期,不进行过期操作,shopId: {}", platformInfoId);
            return;
        }
    }

    @Override
    public Result<ShopPackageFunctionDto> getShopFunction() {
        PlatformShopInfo shopInfo = this.getOne(new QueryWrapper<>());
        if (shopInfo == null) {
            return Result.failed("店铺不存在");
        }
        //获取历史套餐是否有大于门店版
        Integer i = sysShopPackageOrderService.selectBoughtEnterpriseVersion();
        ShopPackageFunctionDto dto = new ShopPackageFunctionDto();
        dto.setBoughtEnterpriseVersion(i > 0 ? Boolean.TRUE : Boolean.FALSE);
        dto.setPoint(Boolean.FALSE);
        dto.setLive(Boolean.FALSE);
        dto.setArea(Boolean.FALSE);
        dto.setCopyright(Boolean.FALSE);
        dto.setHeadOffice(Boolean.FALSE);
        if (shopInfo.getIsDue().equals(CommonConstants.NUMBER_ONE)) {
            return Result.ok(dto);
        }
        SysShopPackageOrder sysShopPackageOrder = sysShopPackageOrderService.getById(shopInfo.getPackageOrderId());
        if (sysShopPackageOrder == null) {
            return Result.ok(dto);
        }
        SysShopPackage sysShopPackage = JSONObject.parseObject(sysShopPackageOrder.getPackageData(), SysShopPackage.class);
        dto.setCommunityPackagelevel(sysShopPackage.getLevel());
        if (sysShopPackage.getLevel() >= CommonConstants.NUMBER_TWO) {
            dto.setPoint(Boolean.TRUE);
            dto.setLive(Boolean.TRUE);
            dto.setArea(Boolean.TRUE);
            dto.setCopyright(Boolean.TRUE);
        }
        if (sysShopPackage.getLevel() >= CommonConstants.NUMBER_THREE) {
            dto.setHeadOffice(Boolean.TRUE);
        }
        return Result.ok(dto);
    }

    @Override
    public ShopConfigDto getShopConfigAndAppId(String appId) {
        MiniInfo info = miniInfoService.getByAppId(appId);
        if (info == null) {
            log.warn("不存在该小程序信息:{}", appId);
            return null;
        }
        ShopConfigDto shopConfigDto = new ShopConfigDto();
        shopConfigDto.setPayInfo(payInfo(CommonConstants.NUMBER_THREE, ""));
        shopConfigDto.setMiniInfo(info);
        return shopConfigDto;
    }

    /**
     * 缓存店铺基本信息
     *
     * @param shopInfo com.medusa.gruul.platform.api.entity.PlatformShopInfo
     */
    private void initShopCache(PlatformShopInfo shopInfo) {
        CurShopInfoDto curShopInfoDto = new CurShopInfoDto();
        curShopInfoDto.setPlatformUserId(shopInfo.getAccountId());
        curShopInfoDto.setShopName(shopInfo.getShopName());
        curShopInfoDto.setBindMiniId(shopInfo.getBindMiniId());
        curShopInfoDto.setBindMpId(shopInfo.getBindMpId());
        if (shopInfo.getPackageId() != null) {
            SysShopPackageOrder sysShopPackageOrder = sysShopPackageOrderService.getById(shopInfo.getPackageOrderId());
            if (sysShopPackageOrder != null) {
                SysShopPackage sysShopPackage = JSON.parseObject(sysShopPackageOrder.getPackageData(), SysShopPackage.class);
                curShopInfoDto.setLevel(sysShopPackage.getLevel());
                curShopInfoDto.setPackageName(sysShopPackage.getName());
            }
        }
        Long shopTemplateDetailId = shopInfo.getShopTemplateDetailId();
        if (shopTemplateDetailId != null && shopTemplateDetailId > 0) {
            PlatformShopTemplateDetail shopTemplateDetail = platformShopTemplateDetailService.getById(shopInfo.getShopTemplateDetailId());
            curShopInfoDto.setTemplateVersion(shopTemplateDetail.getVersion());
        }
        PlatformRedis allRedis = new PlatformRedis(CommonConstants.SHOP_INFO_REDIS_KEY);
        allRedis.set("", JSON.toJSONString(curShopInfoDto));
    }
}
