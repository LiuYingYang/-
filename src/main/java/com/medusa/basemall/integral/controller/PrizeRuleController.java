package com.medusa.basemall.integral.controller;


import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.VersionNumber;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.integral.entity.PrizeRule;
import com.medusa.basemall.integral.service.PrizeRuleService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 积分获得规则表
 *
 * @author Created by wx on 2018/06/06.
 */
@Api(tags = "所有接口")
@RestController
@RequestMapping("/prize/rule")
@VersionManager(versoinNumber = VersionNumber.STANDARDVERSION)
public class PrizeRuleController {

    @Autowired
    private PrizeRuleService prizeRuleService;

    @ApiOperation(value = "积分获得规则表编辑", tags = "更新接口")
    @PutMapping("/v1/update")
    public Result update(@ApiParam(value = "积分获得规则表对象", required = true)@RequestBody PrizeRule prizeRule) {
        int result = prizeRuleService.update(prizeRule);
        if (result > 0) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("保存失败");
        }
    }

    @ApiOperation(value = "积分获得规则表获取", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100,message = "success",response = PrizeRule.class,responseContainer = "List"),
    })
    @GetMapping("/v1/findByAppmodelId")
    public Result findByAppmodelId(@ApiParam(value = "模板id", required = true)@RequestParam String appmodelId) {
        PrizeRule prizeRule = prizeRuleService.findByAppmodelId(appmodelId);
        if (prizeRule == null) {
            PrizeRule prizeRuleNew = new PrizeRule();
	        prizeRuleNew.setAppmodelId(appmodelId);
	        prizeRuleService.save(prizeRuleNew);
            prizeRule = prizeRuleService.findByAppmodelId(appmodelId);
            return ResultGenerator.genSuccessResult(prizeRule);
        } else {
            return ResultGenerator.genSuccessResult(prizeRule);
        }
    }
}
