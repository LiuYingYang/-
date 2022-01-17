package com.medusa.gruul.platform.web.controller;


import com.medusa.gruul.common.core.util.Result;
import com.medusa.gruul.platform.api.model.vo.ShopMessageVo;
import com.medusa.gruul.platform.model.dto.MotifyMsgStateDto;
import com.medusa.gruul.platform.service.IPlatformShopMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 店铺消息配置 前端控制器
 * </p>
 *
 * @author whh
 * @since 2020-05-22
 */
@RestController
@RequestMapping("/shop-message")
@Api(tags = "店铺消息相关接口")
public class PlatformShopMessageController {

    @Autowired
    private IPlatformShopMessageService platformShopMessageService;


    @GetMapping(value = "buyer/notify")
    @ApiOperation(value = "获取店铺买家通知")
    public Result<List<ShopMessageVo>> msgAll() {
        List<ShopMessageVo> vos = platformShopMessageService.msgAll();
        return Result.ok(vos);
    }

    @PutMapping(value = "/state")
    @ApiOperation(value = "修改消息开启状态")
    public Result modifyMsgState(@RequestBody MotifyMsgStateDto msgStateDto) {
        platformShopMessageService.modifyState(msgStateDto);
        return Result.ok();
    }


}
