package com.medusa.basemall.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.user.entity.Wxuser;
import com.medusa.basemall.user.service.WxuserService;
import com.medusa.basemall.user.vo.BackWxuserVo;
import com.medusa.basemall.user.vo.MiniWxuserVo;
import com.medusa.basemall.user.vo.SearchUser;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by medusa on 2018/05/23.
 */
@RestController
@RequestMapping("/wxuser")
@Api(tags = {"所有接口"})
@VersionManager
public class WxuserController {

	@Resource
	private WxuserService wxuserService;

	/**
	 * 用户手机号解密
	 * @return
	 */
	@PostMapping("/v1/decodeUserInfo")
	@ApiOperation(value = "用户手机号解密", tags = "解密接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "encryptedData", required = true, value = "加密数据", paramType = "string"),
			@ApiImplicitParam(name = "ivStr", required = true, value = "???", paramType = "string"),
			@ApiImplicitParam(name = "wxuserId", required = true, value = "用户id", paramType = "int"),})
	public Result decodeUserInfo(@RequestBody @ApiParam(hidden = true) JSONObject jsonObject) {
		String encryptedData = jsonObject.getString("encryptedData");
		String ivStr = jsonObject.getString("ivStr");
		Long wxuserId = jsonObject.getLong("wxuserId");
		return wxuserService.decodeUserInfo(encryptedData, ivStr, wxuserId);
	}

	/**
	 * @return wxuser
	 */
	@GetMapping("/v1/wxlogin")
	@ApiOperation(value = "用户登录", tags = {"更新接口", "添加接口"})
	public Result wxlogin(@RequestParam String code, @RequestParam String appmodelId) {
		return wxuserService.wxlogin(code, appmodelId);
	}

	@GetMapping("/v1/getUserInfo")
	@ApiOperation(value = "获取用户最新信息", tags = "查询接口")
	public Result<MiniWxuserVo> getUserInfo(@RequestParam @ApiParam Long wxuserId,
			@RequestParam @ApiParam String appmodelId) {
		MiniWxuserVo miniWxuserVo = wxuserService.getUserInfo(wxuserId, appmodelId);
		return ResultGenerator.genSuccessResult(miniWxuserVo);
	}

	/**
	 * @return wxuser
	 */
	@PostMapping("/v1/update")
	@ApiOperation(value = "更新微信用户信息", notes = "微信用户授权，更新用户头像昵称", tags = "更新接口")
	@ApiImplicitParams({@ApiImplicitParam(name = "nickName", required = false, value = "昵称", paramType = "string"),
			@ApiImplicitParam(name = "avatarUrl", required = false, value = "昵称", paramType = "string"),
			@ApiImplicitParam(name = "wxuserId", required = true, value = "用户id", paramType = "long"),})
	public Result update(@RequestBody @ApiParam(hidden = true) Wxuser wxuser) {
		if (wxuserService.updateUserInfo(wxuser) > 0) {
			return ResultGenerator.genSuccessResult();
		}
		return ResultGenerator.genFailResult("更新失败");
	}

	/**
	 * 普通用户批量备注
	 * rep
	 * wxuserIds
	 * markLevel
	 * backRemark
	 * overType      是否覆盖备注 	0 -不覆盖 1-覆盖
	 *
	 * @return
	 */
	@PostMapping("/v1/updateRemark")
	@ApiOperation(value = "普通用户批量备注", notes = "微信用户授权，更新用户头像昵称", tags = "更新接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "wxuserIds", required = true, value = "用户ids:多个id用逗号分隔", paramType = "string"),
			@ApiImplicitParam(name = "markLevel", required = false, value = "等级", paramType = "string"),
			@ApiImplicitParam(name = "backRemark", required = false, value = "备注", paramType = "string"),
			@ApiImplicitParam(name = "overType", required = true, value = "是否覆盖备注", paramType = "int"),})
	public Result updateRemark(@RequestBody @ApiParam(hidden = true) JSONObject jsonObject) {
		String wxuserIds = jsonObject.getString("wxuserIds");
		Integer markLevel = jsonObject.getInteger("markLevel");
		String backRemark = jsonObject.getString("backRemark");
		Integer coverType = jsonObject.getInteger("coverType");
		return wxuserService.updateRemark(wxuserIds, markLevel, backRemark, coverType);
	}


	/**
	 * 查询所有会员用户
	 * appmodelId
	 * nickName
	 * ogintime
	 * dealtime
	 * page
	 * size
	 * groupId
	 *
	 * @return
	 */
	@GetMapping("/v1/searchVipWxuser")
	@ApiOperation(value = "查询所有会员用户", tags = "查询接口")
	public Result searchVipWxuser(@ModelAttribute SearchUser searchUser) {
		List<BackWxuserVo> list = wxuserService
				.searchWxuser(searchUser.getAppmodelId(), searchUser.getNickName(), searchUser.getLogintime(),
						searchUser.getDealtime(), searchUser.getGroupId(), searchUser.getOrder(), "1",
						searchUser.getPageNum(), searchUser.getPageSize());
		PageInfo pageInfo = new PageInfo(list);
		return ResultGenerator.genSuccessResult(pageInfo);
	}

	/**
	 * 查询普通用户
	 * appmodelId
	 * nickName
	 * ogintime
	 * dealtime
	 * page
	 * size
	 * groupId
	 *
	 * @return
	 */
	@GetMapping("/v1/searchWxuser")
	@ApiOperation(value = "查询普通用户", tags = "查询接口", response = PageInfo.class, notes = "订单未写跟订单相关操作不使用   不使用的条件查询  除分页条件,分组条件外 其他条件都传入空字符串")
	public Result searchWxuser(@ModelAttribute SearchUser searchUser) {
		List<BackWxuserVo> wxusers = wxuserService
				.searchWxuser(searchUser.getAppmodelId(), searchUser.getNickName(), searchUser.getLogintime(),
						searchUser.getDealtime(), searchUser.getGroupId(), searchUser.getOrder(), "0",
						searchUser.getPageNum(), searchUser.getPageSize());
		PageInfo pageInfo = new PageInfo(wxusers);
		return ResultGenerator.genSuccessResult(pageInfo);
	}

}
