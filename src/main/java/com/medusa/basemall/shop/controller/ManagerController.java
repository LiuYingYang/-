package com.medusa.basemall.shop.controller;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.shop.entity.TransactionRecord;
import com.medusa.basemall.shop.service.ManagerService;
import com.medusa.basemall.shop.vo.MiniInfoVO;
import com.medusa.basemall.shop.vo.UpdateManagerVO;
import com.medusa.basemall.shop.vo.VersionBuyVO;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 *
 * @author medusa
 * @date 2018/05/23
 */
@RestController
@RequestMapping("/manager")
@VersionManager
public class ManagerController {

	@Autowired
	private ManagerService managerService;

	@GetMapping("/v1/orderstate")
	@ApiOperation(value = "查询订单状态", tags = "查询接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success"), @ApiResponse(code = 100, message = "fail")})
	public Result fidnOrderState(@RequestParam String outTradeNo,
			@RequestParam @ApiParam(value = "type=1 查询版本购买/续费/升级   type=2 查询余额充值 ") Integer type) {
		return managerService.fidnOrderState(outTradeNo, type);
	}

	@GetMapping("/v1/myproperty")
	@ApiOperation(value = "我的资产", tags = "查询接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success", response = MiniInfoVO.class)})
	public Result getMyproperty(@RequestParam String appmodelId) {
		return ResultGenerator.genSuccessResult(managerService.getMyproperty(appmodelId));
	}

	@PutMapping("/v1/update/expiry/date/notiyf")
	@ApiOperation(value = "确认版本到期提醒", tags = "更新接口")
	@ApiImplicitParams({@ApiImplicitParam(name = "appmodelId", value = "商家appid")})
	public Result updateExpiryDateNotiyf(@RequestBody JSONObject jsonObject) {
		return managerService.updateExpiryDateNotiyf(jsonObject.getString("appmodelId"));
	}

	@GetMapping("/v1/miniInfo")
	@ApiOperation(value = "获取小程序信息", tags = "查询接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success", response = MiniInfoVO.class)})
	public Result getMiniInfo(@RequestParam String appmodelId) {
		return ResultGenerator.genSuccessResult(managerService.getMiniInfo(appmodelId));
	}

	@PutMapping("/v1/updateManager")
	@ApiOperation(value = "更新商户秘钥/商户号/商户证书", tags = "更新接口")
	public Result updateManager(@RequestBody UpdateManagerVO updateManagerVO) {
		if (updateManagerVO.getAppid() == null) {
			return ResultGenerator.genFailResult("appId不能为空");
		}
		if (managerService.updateSecretKey(updateManagerVO.getAppid(), updateManagerVO.getCertificatePath(),
				updateManagerVO.getMchId(), updateManagerVO.getMchKey()) > 0) {
			return ResultGenerator.genSuccessResult();
		}
		return ResultGenerator.genFailResult("更新失败");
	}

	@PostMapping("/v1/version/continuation/fee")
	@VersionManager(enable = false)
	@ApiOperation(value = "版本续费/购买/升级/新开店铺", tags = "支付接口")
	public Result versionContinuationfee(@RequestBody @ApiParam(value = "参数") VersionBuyVO versionBuyVO) {
		return managerService.versionContinuationFee(versionBuyVO);
	}

	@PostMapping("/v1/version/continuation/fee/notify")
	@VersionManager(enable = false)
	@ApiOperation(value = "版本购买支付回调", tags = "回调接口")
	public String versionContinuationfeeNotify(HttpServletRequest request) throws IOException {
		String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
		return managerService.versionContinuationFeeNotify(xmlResult);
	}

	@GetMapping("/v1/top/up/balance")
	@ApiOperation(value = "我的资产充值余额", tags = "余额接口")
	@ApiResponses({@ApiResponse(code = 100, message = "支付图片路径")})
	public Result topUpBalance(@RequestParam String appmodelId,
			@RequestParam @ApiParam(value = "充值金额") String topUpBalance) {
		return managerService.topUpBalance(appmodelId, topUpBalance);
	}

	@PostMapping("/v1/top/up/balance/notify")
	@VersionManager(enable = false)
	@ApiOperation(value = "余额充值支付回调", tags = "回调接口")
	public String topUpBalanceNotify(HttpServletRequest request) throws IOException {
		String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
		return managerService.topUpBalanceNotify(xmlResult);
	}

	@GetMapping("/v1/unfreezeBlance")
	@ApiOperation(value = "保证金解冻", tags = "余额接口")
	public Result unfreezeBlance(@RequestParam String appmodelId, @RequestParam String code,
			@RequestParam String phone) {
		return managerService.unfreezeBlance(appmodelId, code, phone);
	}

	@GetMapping("/v1/balance/record")
	@ApiOperation(value = "资产中心查询交易记录", tags = "查询接口")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success", response = TransactionRecord.class, responseContainer = "list"),})
	public Result getBalanceRecord(@RequestParam @ApiParam(value = "页数", required = true) Integer pageNum,
			@RequestParam @ApiParam(value = "条数", required = true) Integer pageSize,
			@RequestParam @ApiParam(value = "商家appmodelId", required = true) String appmodelId,
			@RequestParam(required = false, defaultValue = "") @ApiParam(value = "开始时间") String startTime,
			@RequestParam(required = false, defaultValue = "") @ApiParam(value = "结束时间") String endTime) {
		return managerService.getBalanceRecord(pageSize, pageNum, startTime, endTime, appmodelId);
	}

	@GetMapping(path = "/v1/balance/withdraw/deposit")
	@ApiOperation(value = "余额提现", tags = "余额接口")
	public Result balanceWithdrawdeposit(@RequestParam @ApiParam(value = "提现余额", required = true) String balance,
			@RequestParam @ApiParam(value = "验证码", required = true) String code,
			@RequestParam @ApiParam(value = "手机号码", required = true) String phone,
			@RequestParam @ApiParam(value = "真实姓名", required = true) String realName,
			@RequestParam @ApiParam(value = "商家appmodelId", required = true) String appmodelId) {
		managerService.balanceWithdrawdeposit(balance, code, phone, realName, appmodelId);
		return ResultGenerator.genSuccessResult();
	}

}
