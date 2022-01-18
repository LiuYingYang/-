package com.medusa.gruul.platform.web.controller;

import com.medusa.gruul.common.core.util.Result;
import com.medusa.gruul.platform.model.dto.WxaGetwxacode;
import com.medusa.gruul.platform.service.IMiniInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 小程序相关接口
 * </p>
 *
 * @author whh
 * @since 2019-09-07
 */
@RestController
@RequestMapping("/mini-info")
@Api(tags = "小程序相关接口")
public class MiniInfoController {

    @Autowired
    private IMiniInfoService miniInfoService;

    @PostMapping("wxa/getwxacode")
    @ApiOperation(value = "获取小程序码,返回base64")
    public Result wxaGetwxacode(@RequestBody @Validated WxaGetwxacode wxaGetwxacode) {
        return miniInfoService.wxaGetwxacode(wxaGetwxacode);
    }

}
