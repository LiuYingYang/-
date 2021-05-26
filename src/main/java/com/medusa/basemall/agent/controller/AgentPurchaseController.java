package com.medusa.basemall.agent.controller;

import com.medusa.basemall.agent.entity.AgentPurchase;
import com.medusa.basemall.agent.service.AgentPurchaseService;
import com.medusa.basemall.agent.vo.AgentPurchaseVo;
import com.medusa.basemall.agent.vo.PurchaseVo;
import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.VersionNumber;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 *
 * @author medusa
 * @date 2018/06/02
 */
@RestController
@RequestMapping("/agent/purchase")
@Api(tags = "所有接口")
@VersionManager(versoinNumber = VersionNumber.MARKETINGVERSION)
public class AgentPurchaseController {

	@Resource
	private AgentPurchaseService agentPurchaseService;

	@GetMapping(path = "/v1/findPurchase")
	@ApiOperation(value = "查询我的采购单", tags = "查询接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success", response = PurchaseVo.class),
			@ApiResponse(code = 99, message = "删除失败")})
	public Result findPurchase(@RequestParam @ApiParam(value = "用户id") Long wxuserId,
			@RequestParam @ApiParam(value = "商家wxAppId") String appmodelId) {
		return agentPurchaseService.findPurchase(wxuserId, appmodelId);
	}

	@GetMapping(path = "/v1/batchDelete")
	@ApiOperation(value = "删除我的采购单商品", tags = "删除接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success"), @ApiResponse(code = 99, message = "删除失败")})
	public Result batchDelete(@RequestParam @ApiParam(value = "采购单ids") String purchaseIds,
			@RequestParam @ApiParam(value = "用户id") Long wxuserId,
			@RequestParam @ApiParam(value = "操作类型 1-删除单个 2-清空") Integer type) {
		return agentPurchaseService.batchDelete(purchaseIds, wxuserId, type);
	}

	@PostMapping("/v1/addProductToAgentPurchase")
	@ApiOperation(value = "加入我的采购单", tags = "添加接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success"), @ApiResponse(code = 99, message = "添加失败")})
	public Result addPurchase(@RequestBody AgentPurchaseVo purchase) {
		AgentPurchase b = agentPurchaseService.addPurchase(purchase);
		if (b != null) {
			return ResultGenerator.genSuccessResult();
		}
		return ResultGenerator.genFailResult("添加失败");
	}

	@GetMapping("/v1/findPurchaseSum")
	@ApiOperation(value = "查询采购单有效的商品数量", tags = "查询接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success", response = Integer.class),
			@ApiResponse(code = 99, message = "查询失败")})
	public Result findPurchaseSum(@RequestParam @ApiParam(value = "用户id") Long wxuserId,
			@RequestParam @ApiParam(value = "商家wxAppId") String appmodelId) {
		return ResultGenerator.genSuccessResult(agentPurchaseService.findPurchaseSum(wxuserId, appmodelId));
	}

	@PutMapping("/v1/updatePurchase")
	@ApiOperation(value = "更新采购单商品", tags = "更新接口", notes = "AgentPurchase对象修改后的所有数据")
	@ApiResponses({@ApiResponse(code = 100, message = "success"),
			@ApiResponse(code = 99, message = "更新失败")})
	public Result updatePurchase(@RequestBody AgentPurchase purchase) {
		return agentPurchaseService.updatePurchase(purchase);
	}

}
