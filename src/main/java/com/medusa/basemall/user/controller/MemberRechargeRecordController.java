package com.medusa.basemall.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.configurer.WebMvcConfiguration;
import com.medusa.basemall.constant.VersionNumber;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.user.entity.MemberRechargeRecord;
import com.medusa.basemall.user.service.MemberRechargeRecordService;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by medusa on 2018/05/30.
 */
@RestController
@RequestMapping("/member/recharge/record")
@Api(tags = {"所有接口"})
@VersionManager(versoinNumber = VersionNumber.STANDARDVERSION)
public class MemberRechargeRecordController {


	@Resource
	private MemberRechargeRecordService memberRechargeRecordService;

	@PostMapping("/v1/payRechargeActive")
	@ApiOperation(value = "充值支付", tags = "支付接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "wxuserId", required = true, value = "用户id", paramType = "Long"  ),
			@ApiImplicitParam(name = "memberId", required = true, value = "会员id", paramType = "Long"  ),
			@ApiImplicitParam(name = "price", required = true, value = "充值金额", paramType = "double" ),
			@ApiImplicitParam(name = "type", required = true, value = "充值方式: 1.微信支付,2银行卡充值" ),
			@ApiImplicitParam(name = "activeRechargeId", required = false, value = "充值活动id" ),})
	public Result payRechargeActive(@RequestBody @ApiParam(hidden = true) JSONObject jsonObject,
			HttpServletRequest request) {
		Long wxuserId = jsonObject.getLong("wxuserId");
		Long memberId = jsonObject.getLong("memberId");
		Double price = jsonObject.getDouble("price");
		Integer activeRechargeId = jsonObject.getInteger("activeRechargeId");
		String appmodelId = jsonObject.getString("appmodelId");
		Integer type = jsonObject.getInteger("type");
		String userIp = WebMvcConfiguration.getIpAddress(request);
		return memberRechargeRecordService
				.payRechargeActive(wxuserId, memberId, price, activeRechargeId, appmodelId, type, userIp);
	}

	@RequestMapping("/v1/payRechargeActiveNotify")
	@VersionManager(enable = false)
	@ApiOperation(value = "充值支付回调", tags = "回调接口", notes = "微信官方回调,查看官方文档")
	public String payRechargeActiveNotify(HttpServletRequest request) throws IOException {
		String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());

		return memberRechargeRecordService.payRechargeActiveNotify(xmlResult);
	}

	@PostMapping("/v1/list")
	@ApiOperation(value = "查询充值记录", tags = "查询接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "memberId", required = true, value = "会员id", paramType = "Long"  ),
			@ApiImplicitParam(name = "pageNum", required = true, value = "页数" ),
			@ApiImplicitParam(name = "pageSize", required = false, value = "条数" ),})
	public Result list(@RequestBody JSONObject jsonObject) {
		Integer page = jsonObject.getInteger("pageNum");
		Integer size = jsonObject.getInteger("pageSize");
		PageHelper.startPage(page, size);
		List<MemberRechargeRecord> list = memberRechargeRecordService.findByMemberId(jsonObject.getLong("memberId"));
		PageInfo pageInfo = new PageInfo(list);
		return ResultGenerator.genSuccessResult(pageInfo);
	}

}
