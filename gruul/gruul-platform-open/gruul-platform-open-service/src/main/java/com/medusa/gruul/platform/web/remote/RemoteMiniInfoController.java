package com.medusa.gruul.platform.web.remote;

import com.medusa.gruul.common.core.annotation.EscapeLogin;
import com.medusa.gruul.common.core.util.Result;
import com.medusa.gruul.platform.api.model.dto.*;
import com.medusa.gruul.platform.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author whh
 */
@RestController(value = "remoteMiniInfoController")
@RequestMapping("/")
@Api(tags = "远程调用接口,仅限后端feign调用-->RemoteMiniInfoService")
public class RemoteMiniInfoController {

    @Autowired
    private ISystemConfService systemConfService;
    @Autowired
    private IPlatformShopInfoService platformShopInfoService;

    /**
     * 获取店铺信息
     *
     * @return com.medusa.gruul.platform.api.model.dto.ShopInfoDto
     */
    @GetMapping(value = "/get/shop/info")
    @EscapeLogin
    @ApiOperation(value = "获取店铺信息")
    public Result<ShopInfoDto> getShopInfo() {
        return platformShopInfoService.getShopInfo();
    }

    @GetMapping("/oss/config")
    @EscapeLogin
    @ApiOperation(value = "获取当前平台使用oss配置")
    public Result<OssConfigDto> currentOssConfig() {
        return systemConfService.currentOssConfig();
    }

    @GetMapping("/shop/config")
    @EscapeLogin
    @ApiOperation(value = "获取店铺配置信息(小程序信息,支付配置)")
    public ShopConfigDto getShopConfig() {
        return platformShopInfoService.getShopConfig();
    }

    @GetMapping("/shop/config/appid")
    @EscapeLogin
    @ApiOperation(value = "根据appid获取店铺配置信息(小程序信息,支付配置)")
    public ShopConfigDto getShopConfigAndAppId(@RequestParam(value = "appId", required = true) String appId) {
        return platformShopInfoService.getShopConfigAndAppId(appId);
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
