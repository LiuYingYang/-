package com.medusa.basemall.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.VersionNumber;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.user.service.MemberService;
import com.medusa.basemall.user.vo.MemberVo;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author medusa
 * @date 2018/05/24
 */
@RestController
@RequestMapping("/member")
@Api(tags = {"所有接口"})
@VersionManager(versoinNumber = VersionNumber.STANDARDVERSION)
public class MemberController {

	@Resource
	private MemberService memberService;

	@GetMapping("/v1/myMemberCore")
	@Deprecated
	@ApiOperation(value = "我的会员中心", tags = "查询接口")
	public Result myMemberCore(@RequestParam @ApiParam(value = "用户id") Long wxuserId) {
		//	return memberService.myMemberCore(wxuserId);
		return null;
	}

	@GetMapping("/v1/balanceDetails")
	@ApiOperation(value = "余额详情(不可用)", tags = "查询接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "memberId", required = true, value = "会员id", paramType = "intber"  ),})
	public Result balanceDetails(@RequestParam @ApiParam(value = "用户id") Long memberId) {
		return memberService.balanceDetails(memberId);
	}

	/**
	 * 校验验证码并注册会员
	 * 更新会员信息基础信息
	 *
	 * @ phone
	 * @ memberId  //用户未激活的会员id
	 * @ code
	 * @ appmodelId
	 * (修改注册状态为1,补全其他信息)
	 * @
	 */
	@PostMapping("/v1/validateCode")
	@ApiOperation(value = "注册会员接口", tags = "添加接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "memberId", required = true, value = "用户未激活的会员id", paramType = "intber"  ),
			@ApiImplicitParam(name = "phone", required = true, value = "手机号", paramType = "string"  ),
			@ApiImplicitParam(name = "code", required = true, value = "验证码    type=1时无需发送验证码", paramType = "string"  ),
			@ApiImplicitParam(name = "type", required = true, value = "注册类型  1-微信注册  2-用户注册" ),})
	public Result validateCode(@RequestBody @ApiParam(hidden = true) MemberVo memberVo) {
		return memberService
				.validateCode(memberVo.getPhone(), memberVo.getMemberId(), memberVo.getCode(), memberVo.getAppmodelId(),
						memberVo.getType());
	}

	@PostMapping("/v1/updateRemark")
	@ApiOperation(value = "批量备注", tags = "更新接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "memberIds", required = true, value = "用户ids,多个逗号分隔", paramType = "string"  ),
			@ApiImplicitParam(name = "markLevel", required = false, value = "备注等级", paramType = "int" ),
			@ApiImplicitParam(name = "backRemark", required = false, value = "后台备注", paramType = "string"  ),
			@ApiImplicitParam(name = "coverType", required = true, value = "是否覆盖备注  0-不覆盖 1-覆盖", paramType = "int" ),})
	public Result updateRemark(@RequestBody @ApiParam(hidden = true) JSONObject jsonObject) {
		String memberIds = jsonObject.getString("memberIds");
		Integer markLevel = jsonObject.getInteger("markLevel");
		String backRemark = jsonObject.getString("backRemark");
		Integer coverType = jsonObject.getInteger("coverType");
		return memberService.updateRemark(memberIds, markLevel, backRemark, coverType);
	}

	/**
	 * 设置普通用户为会员
	 * 手机号码:phone
	 * 用户未激活的会员id:memberId
	 * 分组id:groupIds			多个id用逗号分隔
	 * 模板id:appmodelId
	 */
	@PutMapping(path = "/v1/setUserToMember")
	@ApiOperation(value = "设置普通用户为会员", tags = "更新接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "phone", required = true, value = "手机号码", paramType = "string"  ),
			@ApiImplicitParam(name = "memberId", required = true, value = "会员id", paramType = "intber"  ),
			@ApiImplicitParam(name = "groupIds", required = true, value = "分组ids:多个id用逗号分隔", paramType = "string"  ),})
	public Result setUserToMember(@RequestBody @ApiParam(hidden = true) MemberVo memberVo) {
		return memberService.setUserToMember(memberVo.getPhone(), memberVo.getMemberId(), memberVo.getGroupIds(),
				memberVo.getAppmodelId());
	}

	/**
	 * 修改绑定手机号
	 * 手机号码:phone
	 * 验证码:code
	 * 会员id:memberId
	 * 模板id:appmodelId
	 */
	@PutMapping(path = "/v1/updatePhone")
	@ApiOperation(value = "修改绑定手机号", tags = "更新接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "phone", required = true, value = "手机号码", paramType = "string"  ),
			@ApiImplicitParam(name = "code", required = true, value = "验证码", paramType = "string"  ),
			@ApiImplicitParam(name = "memberId", required = true, value = "会员id", paramType = "intber"  ),})
	public Result updatePhone(@RequestBody @ApiParam(hidden = true) MemberVo memberVo) {
		return memberService
				.updatePhone(memberVo.getPhone(), memberVo.getMemberId(), memberVo.getCode(), memberVo.getAppmodelId());
	}

}
