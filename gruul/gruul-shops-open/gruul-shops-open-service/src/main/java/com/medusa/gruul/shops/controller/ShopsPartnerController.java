package com.medusa.gruul.shops.controller;


import com.medusa.gruul.common.core.annotation.EscapeLogin;
import com.medusa.gruul.common.core.util.Result;
import com.medusa.gruul.shops.api.entity.ShopsPartner;
import com.medusa.gruul.shops.model.vo.ShopsPartnerVo;
import com.medusa.gruul.shops.service.ShopsPartnerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author create by zq
 * @date created in 2020/01/14
 */
@RestController
@RequestMapping(value = "/shops_partner")
public class ShopsPartnerController {


    @Autowired
    private ShopsPartnerService shopsPartnerService;


    /**
     * 新增默认店铺
     *
     * @param pass
     * @param phone
     * @return Result
     */
    @GetMapping("/save")
    @EscapeLogin
    @ApiOperation(value = "新增默认店铺")
    public Result<ShopsPartner> save(@RequestParam @NotNull String pass,
                       @RequestParam @NotNull String phone,
                       @RequestParam @NotNull Long platformId) {
        return shopsPartnerService.saveShopsPartner(pass, phone,platformId);
    }


    /**
     * 获取店铺list
     *
     * @return result
     */
    @PostMapping("/list")
    @ApiOperation(value = "获取店铺list")
    public Result listShopsPartner() {
        return shopsPartnerService.listShopsPartner();
    }


    /**
     * 获取店铺
     *
     * @return ShopsPartner
     */
    @GetMapping("/one")
    @EscapeLogin
    @ApiOperation(value = "获取店铺")
    public ShopsPartner oneByShopId() {
        return shopsPartnerService.oneByShopId();
    }


}
