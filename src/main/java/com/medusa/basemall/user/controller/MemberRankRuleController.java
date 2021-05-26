package com.medusa.basemall.user.controller;

import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.VersionNumber;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.user.entity.MemberRankRule;
import com.medusa.basemall.user.service.MemberRankRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * Created by medusa on 2018/05/29.
 */
@RestController
@RequestMapping("/member/rank/rule")
@Api(tags = {"所有接口"})
@VersionManager(versoinNumber = VersionNumber.STANDARDVERSION)
public class MemberRankRuleController {
    @Resource
    private MemberRankRuleService memberRankRuleService;

    @PostMapping("/v1/createRankRule")
    @ApiOperation(value = "添加会员卡规则", tags = "添加接口")
    @ApiIgnore
    public Result createRankRule(@RequestBody MemberRankRule memberRankRule) {
        return memberRankRuleService.createRankRule(memberRankRule);
    }

    @PutMapping("/v1/update")
    @ApiOperation(value = "修改会员卡规则", tags = "更新接口")
    public Result update(@RequestBody MemberRankRule memberRankRule) {
        return memberRankRuleService.updateMemberRankRule(memberRankRule);
    }

    @GetMapping("/v1/detail")
    @ApiOperation(value = "查询会员卡规则", tags = "查询接口")
    public Result detail(@RequestParam String appmodelId) {
        MemberRankRule rankRule = memberRankRuleService.detail(appmodelId);
        return ResultGenerator.genSuccessResult(rankRule);
    }
}
