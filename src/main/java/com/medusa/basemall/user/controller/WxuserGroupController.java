package com.medusa.basemall.user.controller;

import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.user.entity.WxuserGroup;
import com.medusa.basemall.user.service.WxuserGroupService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author medusa
 * @date 2018/06/06
 */
@RestController
@RequestMapping("/wxuser/group")
@Api(tags = {"所有接口"})
@VersionManager
public class WxuserGroupController {
	@Resource
	private WxuserGroupService wxuserGroupService;


	@PostMapping("/v1/list")
	@ApiOperation(value = "查询用户组", tags = "查询接口")
	public Result list(@RequestBody @ApiParam(hidden = true) WxuserGroup wxuserGroup) {
		if (null != wxuserGroup) {
			return wxuserGroupService.selectByAppmodelId(wxuserGroup);
		}
		return ResultGenerator.genFailResult("查询失败,参数为空");
	}

	@PostMapping("/v1/createGroup")
	@ApiOperation(value = "添加用户组", tags = "添加接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "wxGroupName", required = true, value = "组名称", paramType = "string"  ),})
	public Result createGroup(@RequestBody @ApiParam(hidden = true) WxuserGroup wxuserGroup) {
		if (null != wxuserGroup) {
			return wxuserGroupService.createGroup(wxuserGroup);
		}
		return ResultGenerator.genFailResult("创建失败,参数为空");
	}
	@PostMapping("/v1/deleteGroup")
	@ApiOperation(value = "删除用户组", tags = "删除接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "wxuserGroupId", required = true, value = "组id", paramType = "int" ),})
	public Result deleteGroup(@RequestBody @ApiParam(hidden = true) WxuserGroup wxuserGroup) {
		if (null != wxuserGroup) {
			return wxuserGroupService.deleteGroup(wxuserGroup);
		}
		return ResultGenerator.genFailResult("创建失败,参数为空");
	}
	@PostMapping("/v1/update")
	@ApiOperation(value = "修改用户组", tags = "更新接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "wxuserGroupId", required = true, value = "组id", paramType = "int" ),
			@ApiImplicitParam(name = "wxGroupName", required = true, value = "组名称", paramType = "string"  ),})
	public Result update(@RequestBody WxuserGroup wxuserGroup) {
		int update = wxuserGroupService.update(wxuserGroup);
		if (update > 0) {
			return ResultGenerator.genSuccessResult();
		}
		return ResultGenerator.genFailResult("更新失败");
	}
}
