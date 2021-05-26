package com.medusa.basemall.shop.controller;

import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.shop.dao.ShopInfoDao;
import com.medusa.basemall.shop.entity.ShopInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 门店信息
 *
 * @author Created by wx on 2018/05/25.
 */
@Api(tags = "所有接口")
@RequestMapping("/ShopInfo")
@RestController
@VersionManager
public class ShopInfoController {

    @Autowired
    private ShopInfoDao shopInfoDao;


    @ApiOperation(value = "保存或更新店铺信息", tags = "添加接口")
    @PostMapping("/v1/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody ShopInfo shopInfo) {
        if (shopInfo.getShopInfoId() == null) {
            ShopInfo newShopInfo = shopInfoDao.getByAppmodelId(shopInfo.getAppmodelId());
            if (newShopInfo != null) {
                return ResultGenerator.genFailResult("已存在信息，无法再次添加");
            }
        }
        ShopInfo result = shopInfoDao.save(shopInfo);
        return ResultGenerator.genSuccessResult("保存信息成功");
    }


    @ApiOperation(value = "查询店铺信息", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100, message = "success", response = ShopInfo.class, responseContainer = "ShopInfo")})
    @GetMapping("/v1/findByAppmodelId")
    public Result findByAppmodelId(@ApiParam(value = "模板id") @RequestParam String appmodelId) {
        ShopInfo shopInfo = shopInfoDao.getByAppmodelId(appmodelId);
        return ResultGenerator.genSuccessResult(shopInfo);
    }
}
