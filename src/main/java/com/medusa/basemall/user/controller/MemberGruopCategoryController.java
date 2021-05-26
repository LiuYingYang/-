package com.medusa.basemall.user.controller;

import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.VersionNumber;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.user.service.MemberGroupCategoryService;
import com.medusa.basemall.user.vo.MemberVo;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * Created by medusa on 2018/05/24.
 */
@RestController
@RequestMapping("/member/group/category")
@Api(tags = {"所有接口"})
@VersionManager(versoinNumber = VersionNumber.STANDARDVERSION)
public class MemberGruopCategoryController {

    @Resource
    private MemberGroupCategoryService memberGroupCategoryService;

    /**
     * 设置/修改会员分组
     * 会员数组:members
     * 会员组数组:memberGroupCategories
     * appmodelId
     */
    @PostMapping(path = "/v1/setMemberToGroup")
    @ApiOperation(value = "设置/修改会员分组", tags = {"添加接口", "更新接口"},notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "members", required = true, value = "会员数组", paramType = "Member[]"),
            @ApiImplicitParam(name = "memberGroupCategories",  required = true, value = "会员组数组", paramType = "MemberGroupCategory[]"),
    })
    public Result setMemberToGroup(@RequestBody @ApiParam(hidden = true) MemberVo params) {
        return memberGroupCategoryService.setMemberToGroup(params);
    }

}
