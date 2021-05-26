package com.medusa.basemall.order.controller;


import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.order.entity.Buycar;
import com.medusa.basemall.order.service.BuycarService;
import com.medusa.basemall.order.vo.BuyCarsVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author whh
 */
@RestController
@RequestMapping("/Buycar")
@Api(tags = "所有接口")
@VersionManager
public class BuycarController {

    @Autowired
    private BuycarService buycarService;

    @GetMapping(path = "/v1/findBuyCars")
    @ApiOperation(value = "查询用户购物车", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100,message = "success",response = BuyCarsVo.class),
            @ApiResponse(code = 99,message = "")
    })
    public Result findBuyCars(@ApiParam(value = "wxuserId", required = true) @RequestParam Long wxuserId,
                              @ApiParam(value = "商家wxAppId", required = true) @RequestParam String appmodelId) {
        return buycarService.findBuyCars(wxuserId, appmodelId);
    }

    @DeleteMapping(path = "/v1/batchDelete")
    @ApiOperation(value = "删除商品/清空商品", tags = "删除接口")
    public Result batchDelete( @ApiParam(value = "购物车id", required = true) @RequestParam String buycarIds,
                               @ApiParam(value = "用户id", required = true) @RequestParam Long wxuserId,
                               @ApiParam(value = "1.删除商品 2.清空商品   tyep=2时只需传入用户id", required = true) @RequestParam Integer type ) {
        return buycarService.batchDelete(buycarIds, wxuserId, type);
    }

    @PostMapping("/v1/addProductToBuycar")
    @ApiOperation(value = "添加商品到购物车", tags = "添加接口")
    public Result addBurCar(@RequestBody Buycar buycar) {
	    buycar.setJoinActiveInfo(null);
        Buycar b = buycarService.addProductToBuycar(buycar);
        if (b != null) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("添加失败");
    }

    @GetMapping("/v1/findBurCarSum")
    @ApiOperation(value = "查询购物车有效的商品数量", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100,message = "success",response = Integer.class),
            @ApiResponse(code = 99,message = "查询失败")
    })
    public Result findBurCarSum(  @ApiParam(value = "用户id", required = true) @RequestParam Long wxuserId,
                                  @ApiParam(value = "商家wxAppId", required = true) @RequestParam String appmodelId) {
        return ResultGenerator.genSuccessResult(buycarService.findBurCarSum(wxuserId, appmodelId));
    }


    @PutMapping("/v1/updateBurCar")
    @ApiOperation(value = "更新购物车商品", tags = "更新接口", notes = "Buycar对象修改后的所有数据")
    public Result updateBurCar(@RequestBody Buycar buycar) {
        return buycarService.updateBurCar(buycar);
    }

}