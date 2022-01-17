package com.medusa.gruul.platform.web.controller;


import cn.hutool.core.util.StrUtil;
import com.medusa.gruul.common.core.annotation.EscapeLogin;
import com.medusa.gruul.common.core.constant.CommonConstants;
import com.medusa.gruul.common.core.exception.ServiceException;
import com.medusa.gruul.common.core.util.Result;
import com.medusa.gruul.platform.api.model.dto.ShopPackageFunctionDto;
import com.medusa.gruul.platform.api.model.vo.PayInfoVo;
import com.medusa.gruul.platform.model.dto.ConsoleUpdateDto;
import com.medusa.gruul.platform.model.dto.PayInfoUpdateDto;
import com.medusa.gruul.platform.model.vo.ShopInfoVo;
import com.medusa.gruul.platform.service.IPlatformShopInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 店铺信息表 前端控制器
 * </p>
 *
 * @author whh
 * @since 2020-03-06
 */
@RestController
@RequestMapping("/shop")
@Api(tags = "商户后台店铺相关接口")
public class PlatformShopInfoConsoleController {

    @Autowired
    private IPlatformShopInfoService platformShopInfoService;


    @PutMapping(value = "/console/update")
    @ApiOperation(value = "店铺设置(修改店铺信息)")
    public Result consoleUpdate(@RequestBody @Validated ConsoleUpdateDto dto) {
        platformShopInfoService.consoleUpdate(dto);
        return Result.ok();
    }

    @PutMapping(value = "/console/close-open")
    @ApiOperation(value = "控制台营业或打烊,只有营业中或已打烊调用才有效果")
    public Result closeOrOpen() {
        platformShopInfoService.closeOrOpen();
        return Result.ok();
    }

    @GetMapping("/info")
    @EscapeLogin
    @ApiOperation(value = "获取店铺基本信息")
    public Result<ShopInfoVo> shopInfo() {
        ShopInfoVo infoVo = platformShopInfoService.shopInfo();
        return Result.ok(infoVo);
    }


    @GetMapping("/pay/info")
    @EscapeLogin
    @ApiOperation(value = "获取支付配置")
    public Result<PayInfoVo> payInfo(@ApiParam(value = "获取类型 1-默认加密  2-明文数据需带上code", required = true) @RequestParam(value = "type", defaultValue = "1") Integer type,
                                     @ApiParam(value = "扫码校验返回的code") @RequestParam(name = "code", required = false) String code) {
        if (type < CommonConstants.NUMBER_ONE || type > CommonConstants.NUMBER_TWO) {
            throw new ServiceException("获取类型错误");
        }
        if (CommonConstants.NUMBER_TWO.equals(type)) {
            if (StrUtil.isEmpty(code)) {
                throw new ServiceException("无效获取");
            }
        }
        PayInfoVo infoVo = platformShopInfoService.payInfo(type, code);
        return Result.ok(infoVo);
    }

    @PostMapping("/upload/certificate")
    @EscapeLogin
    @ApiOperation(value = "上传支付证书")
    public Result uploadCertificate(@RequestParam("file") MultipartFile file) {
        String certificatePath = platformShopInfoService.uploadCertificate(file);
        return Result.ok(certificatePath);
    }

    @PostMapping("/pay/info")
    @EscapeLogin
    @ApiOperation(value = "商家后台支付配置修改")
    public Result payInfoUpdate(@RequestBody PayInfoUpdateDto payInfoUpdateDto) {
        platformShopInfoService.payInfoUpdate(payInfoUpdateDto);
        return Result.ok();
    }

    /**
     * 获取店铺当前使用的套餐功能状态
     * <p>
     * code == 200 返回正确数据
     *
     * @return com.medusa.gruul.platform.api.model.dto.ShopInfoDto
     */
    @GetMapping(value = "/get/shop/function")
    @EscapeLogin
    @ApiOperation(value = "获取店铺当前使用的套餐功能状态,当前仅限拼团模板数据")
    public Result<ShopPackageFunctionDto> getShopFunction() {
        return platformShopInfoService.getShopFunction();
    }
}
