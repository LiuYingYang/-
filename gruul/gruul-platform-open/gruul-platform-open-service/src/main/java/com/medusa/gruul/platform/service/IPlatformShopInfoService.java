package com.medusa.gruul.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.medusa.gruul.common.core.util.Result;
import com.medusa.gruul.platform.api.entity.PlatformShopInfo;
import com.medusa.gruul.platform.api.model.dto.ShopConfigDto;
import com.medusa.gruul.platform.api.model.dto.ShopInfoDto;
import com.medusa.gruul.platform.api.model.dto.ShopPackageFunctionDto;
import com.medusa.gruul.platform.api.model.vo.PayInfoVo;
import com.medusa.gruul.platform.model.dto.ConsoleUpdateDto;
import com.medusa.gruul.platform.model.dto.PayInfoUpdateDto;
import com.medusa.gruul.platform.model.vo.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * <p>
 * 店铺信息表 服务类
 * </p>
 *
 * @author whh
 * @since 2020-03-06
 */
public interface IPlatformShopInfoService extends IService<PlatformShopInfo> {


    /**
     * 店铺设置
     *
     * @param dto    com.medusa.gruul.platform.model.dto.ConsoleUpdateDto
     */
    void consoleUpdate(ConsoleUpdateDto dto);

    /**
     * 校验当前店铺真实性(店铺校验,用户校验,用户对比校验)
     *
     * @return
     */
    PlatformShopInfo check();

    /**
     * 打烊或营业
     *
     */
    void closeOrOpen();

    /**
     * 获取指定店铺登录所需数据
     *
     * @param shopInfo com.medusa.gruul.platform.api.entity.PlatformShopInfo
     * @return com.medusa.gruul.platform.model.vo.LoginShopInfoVo
     */
    LoginShopInfoVo getLoginShopInfoVo(PlatformShopInfo shopInfo);

    /**
     * 获取店铺数据
     *
     * @return com.medusa.gruul.platform.api.entity.PlatformShopInfo
     */
    PlatformShopInfo getInfo();

    /**
     * 获取支付配置
     *
     * @param type     1-默认加密  2-明文数据需带上code 3-内部调用
     * @param code     扫码返回的code
     * @return com.medusa.gruul.platform.api.model.vo.PayInfoVo
     */
    PayInfoVo payInfo(Integer type, String code);

    /**
     * 上传支付证书
     *
     * @param file   文件
     * @return 上传路径
     */
    String uploadCertificate(MultipartFile file);


    /**
     * 更新支付配置信息
     *
     * @param payInfoUpdateDto com.medusa.gruul.platform.model.dto.PayInfoUpdateDto
     */
    void payInfoUpdate(PayInfoUpdateDto payInfoUpdateDto);


    /**
     * 获取店铺基本信息
     *
     * @return com.medusa.gruul.platform.model.vo.ShopInfoVo
     */
    ShopInfoVo shopInfo();

    /**
     * 获取店铺配置信息
     * 远程调用接口使用
     *
     * @return com.medusa.gruul.platform.api.model.dto.ShopConfigDto
     */
    ShopConfigDto getShopConfig();

    /**
     * 获取店铺信息
     *
     * @return com.medusa.gruul.platform.api.model.dto.ShopInfoDto
     */
    Result<ShopInfoDto> getShopInfo();

    /**
     * 检测当日到期店铺
     */
    void validateShopPastDue();

    /**
     * 套餐过期
     *
     * @param platformInfoId 平台id
     */
    void packageDueReceive(Integer platformInfoId);

    /**
     * 获取店铺当前使用的套餐功能状态
     *
     * @return com.medusa.gruul.platform.api.model.dto.ShopPackageFunctionDto
     */
    Result<ShopPackageFunctionDto> getShopFunction();

    /**
     * 获取店铺配置信息
     * 远程调用接口使用
     *
     * @param appId 小程序appId
     * @return com.medusa.gruul.platform.api.model.dto.ShopConfigDto
     */
    ShopConfigDto getShopConfigAndAppId(String appId);



}
