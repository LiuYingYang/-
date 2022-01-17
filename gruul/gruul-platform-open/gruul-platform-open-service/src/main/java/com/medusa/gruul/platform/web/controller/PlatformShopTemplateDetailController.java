package com.medusa.gruul.platform.web.controller;


import com.medusa.gruul.common.core.annotation.EscapeLogin;
import com.medusa.gruul.common.core.util.Result;
import com.medusa.gruul.platform.model.vo.SkipUrlVo;
import com.medusa.gruul.platform.service.IPlatformShopTemplateDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 店铺模版详情表 前端控制器
 * </p>
 *
 * @author whh
 * @since 2020-03-06
 */
@RestController
@RequestMapping("/shopTemplate/version")
@Api(tags = "模板中心相关接口")
public class PlatformShopTemplateDetailController {

    @Autowired
    private IPlatformShopTemplateDetailService platformShopTemplateDetailService;


    @ApiOperation(value = "获取当前版本配置的跳转地址")
    @GetMapping
    @EscapeLogin
    public Result<SkipUrlVo> getSkipUrl() {
        SkipUrlVo vo = platformShopTemplateDetailService.getSkipUrl();
        return Result.ok(vo);
    }

}
