package com.medusa.basemall.user.controller;

import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.VersionNumber;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.user.entity.MemberGroup;
import com.medusa.basemall.user.service.MemberGroupService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author medusa
 * @date 2018/05/26
 * <p>
 * 会员组Controller
 */
@RestController
@RequestMapping("/member/group")
@Api(tags = {"所有接口"})
@VersionManager(versoinNumber = VersionNumber.STANDARDVERSION)
public class MemberGroupController {

    @Resource
    private MemberGroupService memberGroupService;


    @GetMapping("/v1/list")
    @ApiOperation(value = "查询会员组",tags ="查询接口")

    public Result list(@RequestParam @ApiParam(value = "商家wxAppId") String appmodelId) {
        if (null != appmodelId) {
        	MemberGroup memberGroup = new MemberGroup();
        	memberGroup.setAppmodelId(appmodelId);
            return memberGroupService.selectByAppmodelId(memberGroup);
        }
        return ResultGenerator.genFailResult("查询失败,参数为空");
    }

    @PostMapping("/v1/createGroup")
    @ApiOperation(value = "添加会员组",tags ="添加接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupName", required = true, value = "组名称", paramType = "string"  ),
    })
    public Result createGroup(@RequestBody @ApiParam(hidden = true)MemberGroup memberGroup) {
        if (null != memberGroup) {
            return memberGroupService.createGroup(memberGroup);
        }
        return ResultGenerator.genFailResult("创建失败,参数为空");
    }

    @PostMapping("/v1/deleteGroup")
    @ApiOperation(value = "删除会员组",tags ="删除接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", required = true, value = "组id", paramType = "int" ),
    })
    public Result deleteGroup(@RequestBody @ApiParam(hidden = true) MemberGroup memberGroup) {
        if (null != memberGroup) {
            return memberGroupService.deleteGroup(memberGroup);
        }
        return ResultGenerator.genFailResult("创建失败,参数为空");
    }

    @PostMapping("/v1/update")
    @ApiOperation(value = "修改会员组",tags ="更新接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupId", required = true, value = "组id", paramType = "int" ),
            @ApiImplicitParam(name = "groupName", required = true, value = "组名称", paramType = "string"  ),
    })
    public Result update(@RequestBody @ApiParam(hidden = true)MemberGroup memberGroup) {
        int update = memberGroupService.update(memberGroup);
        if (update > 0) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("更新失败");
    }

}
