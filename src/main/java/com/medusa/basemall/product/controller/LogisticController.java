package com.medusa.basemall.product.controller;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.product.entity.LogisticCancel;
import com.medusa.basemall.product.entity.LogisticDistrobution;
import com.medusa.basemall.product.entity.LogisticModel;
import com.medusa.basemall.product.service.LogisticCancelService;
import com.medusa.basemall.product.service.LogisticDistrobutionService;
import com.medusa.basemall.product.service.LogisticModelService;
import com.medusa.basemall.product.vo.CalculateFareVo;
import com.medusa.basemall.product.vo.CalculateLogsticFeeVo;
import com.medusa.basemall.product.vo.LogisticModelVo;
import com.medusa.basemall.utils.GeoCodeUtil;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author psy
 * @date 2018/05/24
 */
@RestController
@RequestMapping("/Logistic")
@VersionManager
@Api(tags = "所有接口")
public class LogisticController {

	@Resource
	private LogisticDistrobutionService logisticDistrobutionService;

	@Resource
	private LogisticModelService logisticModelService;

	@Resource
	private LogisticCancelService logisticCancelService;

	/**
	 * 添加更新商家配送
	 *
	 * @param logisticDistrobution
	 * @return
	 */
	@PostMapping("/v1/saveOrUpdateDistrobution")
	public Result saveOrUpdateDistrobution(@RequestBody LogisticDistrobution logisticDistrobution) {
		int result = logisticDistrobutionService.saveOrUpDate(logisticDistrobution);
		if (result == 1) {
			return ResultGenerator.genSuccessResult();
		} else {
			return ResultGenerator.genFailResult("添加或更新失败");
		}
	}

	/**
	 * 查询商家配送
	 *
	 * @param object(appmodelId)
	 * @return
	 */
	@PostMapping("/v1/findDistrobution")
	public Result findDistrobution(@RequestBody JSONObject object) {
		String appmodelId = object.getString("appmodelId");
		LogisticDistrobution logisticDistrobution = logisticDistrobutionService.findDistrobution(appmodelId);
		return ResultGenerator.genSuccessResult(logisticDistrobution);
	}

	/**
	 * 添加更新物流模板，包含计价和包邮
	 *
	 * @param logisticModelVo
	 * @return
	 */
	@PostMapping("/v1/saveOrUpdateModel")
	public Result saveOrUpdateModel(@RequestBody LogisticModelVo logisticModelVo) {

		int result = logisticModelService.saveOrUpdateModel(logisticModelVo);

		if (result >= 1) {
			return ResultGenerator.genSuccessResult(result);
		} else {
			return ResultGenerator.genFailResult("更新失败");
		}
	}

	/**
	 * 查询物流模板
	 *
	 * @param object(appmodelId)
	 * @return
	 */
	@PostMapping("/v1/findModelByAppmodelId")
	public Result findModelByAppmodelId(@RequestBody JSONObject object) {
		String appmodelId = object.getString("appmodelId");
		List<LogisticModel> list = logisticModelService.findModelByAppmodelId(appmodelId);

		return ResultGenerator.genSuccessResult(list);

	}

	/**
	 * 查询物流模板（包含计价和包邮）
	 *
	 * @param object(appmodelId)
	 * @return
	 */
	@PostMapping("/v1/findModelByAppmodelIdDetail")

	public Result findModelByAppmodelIdDetail(@RequestBody JSONObject object) {
		String appmodelId = object.getString("appmodelId");
		List<LogisticModelVo> list = logisticModelService.findModelByAppmodelIdDetail(appmodelId);
		return ResultGenerator.genSuccessResult(list);

	}

	/**
	 * 开启或关闭物流模板
	 * <p>
	 * appmodelId    模板ID
	 * turnState       开启关闭状态
	 *
	 * @return
	 */
	@PostMapping("/v1/openOrCloseModel")
	public Result openOrCloseModel(@RequestBody JSONObject object) {
		String appmodelId = object.getString("appmodelId");
		Boolean turnState = object.getBoolean("turnState");
		int result = logisticModelService.openOrCloseModel(appmodelId, turnState);
		if (result > 0) {
			return ResultGenerator.genSuccessResult(result);
		} else {
			return ResultGenerator.genFailResult("更新失败");
		}
	}


	@DeleteMapping("/v1/deleteModelById")
	@ApiOperation(value = "删除物流模板", tags = "删除接口")
	public Result deleteModelById(@RequestParam Integer logisticModelId) {

		int result = logisticModelService.deleteModelById(logisticModelId);

		if (result > 1) {
			return ResultGenerator.genSuccessResult(result);
		} else {
			return ResultGenerator.genFailResult("删除失败");
		}
	}

	/**
	 * 保存或更新退货地址
	 *
	 * @param logisticCancel
	 * @return
	 */
	@PostMapping("/v1/saveOrUpdateLogisticCancel")
	public Result saveOrUpdateLogisticCancel(@RequestBody LogisticCancel logisticCancel) {

		int result = logisticCancelService.saveOrUpdateLogisticCancel(logisticCancel);

		if (result == 1) {
			return ResultGenerator.genSuccessResult(result);
		} else {
			return ResultGenerator.genFailResult("更新失败");
		}
	}


	@DeleteMapping("/v1/deleteLogisticCancel")
	@ApiOperation(value = "删除退货地址", tags = "删除接口")
	public Result deleteLogisticCancel(@RequestParam  Integer logisticCancelId) {
		int result = logisticCancelService.deleteById(logisticCancelId);
		if (result == 1) {
			return ResultGenerator.genSuccessResult(result);
		} else {
			return ResultGenerator.genFailResult("删除失败");
		}
	}

	@GetMapping("/v1/findLogisticCancel")
	@ApiResponses({
			@ApiResponse(message = "success", code = 100, response = LogisticCancel.class, responseContainer = "list"),
			@ApiResponse(message = "fail", code = 99),})
	@ApiOperation(value = "查询退货地址", tags = "查询接口")
	public Result findLogisticCancel(@RequestParam String appmodelId) {
		List<LogisticCancel> list = logisticCancelService.findByAppmodelId(appmodelId);
		return ResultGenerator.genSuccessResult(list);
	}


	@ApiOperation(value = "小程序查询可用的配送方式", tags = "查询接口")
	@ApiResponses({
			@ApiResponse(message = "success", code = 100, response = CalculateFareVo.class, responseContainer = "list"),
			@ApiResponse(message = "fail", code = 99),})
	@PostMapping("/v1/calculateLogsticFee")
	public Result calculateLogsticFee(@RequestBody CalculateLogsticFeeVo calculateLogsticFeeVo) {
		List<CalculateFareVo> fareVos = logisticCancelService.calculateLogsticFee(calculateLogsticFeeVo);
		return ResultGenerator.genSuccessResult(fareVos);
	}


	@GetMapping("/v1/findWLMsg")
	@ApiOperation(value = "查询物流信息", tags = "查询接口")
	public Result findWLMsg(@RequestParam @ApiParam(value = "物流公司代码") String wlCode,
			@RequestParam @ApiParam(value = "物流单号") String wlNum) {
		return logisticCancelService.findWLMsg(wlCode, wlNum);
	}

	@GetMapping("/v1/district")
	@ApiOperation(value = "高德地图联级请求", tags = "查询接口")
	public String getDistrict() {
		return GeoCodeUtil.getDistrict();
	}

}
