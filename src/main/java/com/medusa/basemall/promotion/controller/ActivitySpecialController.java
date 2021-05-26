package com.medusa.basemall.promotion.controller;

import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.VersionNumber;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.integral.vo.FindProductVo;
import com.medusa.basemall.product.vo.ProductSimpleVo;
import com.medusa.basemall.promotion.service.ActivitySpecialService;
import com.medusa.basemall.promotion.vo.ActivitySpecialVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 特价活动相关
 *
 * @author Created by psy on 2018/05/30.
 */
@Api(tags = "所有接口")
@RestController
@RequestMapping("/ActivitySpecial")
@VersionManager(versoinNumber = VersionNumber.MARKETINGVERSION)
public class ActivitySpecialController {

     @Resource
    private ActivitySpecialService activitySpecialService;

    @ApiOperation(value = "查询可供特价活动选择的商品", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100, message = "success", response = ProductSimpleVo.class, responseContainer = "List"),})
    @GetMapping("/v1/findProductForSpecial")
    public Result findProductForSpecial(FindProductVo findProductVo) {
        return activitySpecialService.findProductForSpecial(findProductVo);
    }

    @ApiOperation(value = "创建特价活动", tags = "添加接口")
    @PostMapping("/v1/save")
    public Result save(@RequestBody ActivitySpecialVo activitySpecialVo) {
        int result = activitySpecialService.saveActivitySpecial(activitySpecialVo);

        if (result > 0) {
            return ResultGenerator.genSuccessResult(result);
        } else {
            return ResultGenerator.genFailResult("创建失败，请检查选择的商品是否与其他活动时间冲突");
        }
    }

    @ApiOperation(value = "查询特价活动", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100, message = "success", response = ActivitySpecialVo.class, responseContainer = "List"),})
    @GetMapping("/v1/findByAppmodelId")
    public Result findByAppmodelId(String appmodelId) {

        List<ActivitySpecialVo> list = activitySpecialService.findByAppmodelId(appmodelId);

        return ResultGenerator.genSuccessResult(list);
    }

    @ApiOperation(value = "批量删除特价活动", tags = "更新接口")
    @PutMapping("/v1/batchDelete")
    public Result batchDelete(@RequestBody String activitySpecialIds) {
        String[] activitySpecialId = activitySpecialIds.split(",");
        int result = activitySpecialService.batchDelete(activitySpecialId);
        if (result > 0) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }

    @ApiOperation(value = "更新特价活动", tags = "更新接口")
    @PutMapping("/v1/update")
    public Result update(@RequestBody ActivitySpecialVo activitySpecialVo) {

        int result = activitySpecialService.updateActivitySpecial(activitySpecialVo);

        if (result > 0) {
            return ResultGenerator.genSuccessResult(result);
        } else {
            return ResultGenerator.genFailResult("更新失败，请检查选择的商品是否与其他活动时间冲突");
        }
    }
}
