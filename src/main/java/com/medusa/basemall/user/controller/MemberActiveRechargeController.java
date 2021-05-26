package com.medusa.basemall.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.VersionNumber;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.user.entity.MemberActiveRecharge;
import com.medusa.basemall.user.service.MemberActiveRechargeService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author medusa
 * @date 2018/05/30
 */
@RestController
@RequestMapping("/member/active/recharge")
@Api(tags = {"所有接口"})
@VersionManager(versoinNumber = VersionNumber.STANDARDVERSION)
public class MemberActiveRechargeController {

    @Resource
    private MemberActiveRechargeService memberActiveRechargeService;

	@PostMapping("/v1/add")
    @ApiOperation(value = "创建会员充值活动", tags = "添加接口")
    @ApiResponses({
            @ApiResponse(code = 100, message = "success"),
            @ApiResponse(code = 99, message = "相同充值活动已存在/充值活动添加失败"),
    })
    public Result add(@RequestBody MemberActiveRecharge memberActiveRecharge) {
        return memberActiveRechargeService.createRecharge(memberActiveRecharge);
    }

	@DeleteMapping("/v1/delete")
    @ApiOperation(value = "删除/批量删除会员充值活动", tags = "删除接口")
    @ApiResponses({
            @ApiResponse(code = 100, message = "success"),
            @ApiResponse(code = 99, message = "ids为空"),
    })
    public Result delete(@RequestParam String ids) {
        if (ids != null && ids.length() > 0) {
            memberActiveRechargeService.deleteByIds(ids);
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("ids为空");
    }

	@PutMapping("/v1/update")
    @ApiOperation(value = "修改会员充值活动", tags = "更新接口")
    @ApiResponses({
            @ApiResponse(code = 100, message = "success"),
            @ApiResponse(code = 99, message = "相同充值活动已存在/充值活动更新失败"),
    })
    public Result update(@RequestBody MemberActiveRecharge memberActiveRecharge) {
        return memberActiveRechargeService.updateRecharge(memberActiveRecharge);
    }

    @GetMapping("/v1/list")
    @ApiOperation(value = "查询会员充值活动", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100, message = "success"),
    })
    public Result list(@RequestParam String appmodelId) {
        return memberActiveRechargeService.selectAppmodelId(appmodelId);
    }

	@PutMapping("/v1/OpenActive")
    @ApiOperation(value = "开启或关闭会员充值活动", tags = "更新接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", required = true, value = "用户id   0-关闭,1-开启", paramType = "body" ),
            @ApiImplicitParam(name = "appmodelId", required = true, value = "商家wxAppid", paramType = "body" ),
    })
    public Result OpenActive(@RequestBody @ApiParam(hidden = true) JSONObject jsonObject) {
        String appmodelId = jsonObject.getString("appmodelId");
        Integer type = jsonObject.getInteger("type");
        return memberActiveRechargeService.OpenOrCloseActive(appmodelId, type);
    }

}
