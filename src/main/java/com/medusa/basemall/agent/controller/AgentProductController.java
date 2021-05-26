package com.medusa.basemall.agent.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medusa.basemall.agent.entity.AgentProduct;
import com.medusa.basemall.agent.service.AgentProductService;
import com.medusa.basemall.agent.vo.AgentVo;
import com.medusa.basemall.agent.vo.PitchonProduct;
import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.VersionNumber;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author medusa
 * @date 2018/06/02
 */
@RestController
@RequestMapping("/agent/product")
@Api(tags = "所有接口")
@VersionManager(versoinNumber = VersionNumber.MARKETINGVERSION)
public class AgentProductController {
	@Resource
	private AgentProductService agentProductService;

	@PostMapping("/v1/createAgentProduct")
	@ApiOperation(value = "添加代理商品", tags = "添加接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success"), @ApiResponse(code = 99, message = "添加失败")})
	public Result createAgentProduct(@RequestBody List<PitchonProduct> pitchonProducts) {
		return agentProductService.createAgentProduct(pitchonProducts);
	}

	@DeleteMapping("/v1/delete")
	@ApiOperation(value = "批量删除供应商品", tags = "删除接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success"), @ApiResponse(code = 99, message = "删除失败")})
	public Result deleteProduct(@RequestParam @ApiParam(value = "供应商品ids 字符串逗号分隔") String agentProductIds) {
		return agentProductService.deleteProduct(agentProductIds);
	}

	/**
	 * type 0------上架，1---------下架（仓库中）
	 * @param agentVo
	 * @return
	 */
	@PutMapping("/v1/update")
	@ApiOperation(value = "批量上架/下架供应商品", tags = "更新接口")
	@ApiImplicitParams({@ApiImplicitParam(name = "agentProductIds", value = "供应商品ids"),
			@ApiImplicitParam(name = "type", value = "0------上架，1---------下架（仓库中）"),})
	@ApiResponses({@ApiResponse(code = 100, message = "success"), @ApiResponse(code = 99, message = "更新失败")})
	public Result updateProduct(@RequestBody @ApiParam(hidden = true) AgentVo agentVo) {
		return agentProductService.updateProduct(agentVo);
	}

	/**
	 * 供应商品查询
	 * type  0------上架，1---------下架（仓库中）  2已售完
	 *
	 * @return
	 */
	@GetMapping("/v1/list")
	@ApiOperation(value = "供应商品查询", tags = "查询接口")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success", response = AgentProduct.class, responseContainer = "List"),
			@ApiResponse(code = 99, message = "更新失败")})
	public Result list(@RequestParam @ApiParam(value = "商家wxAppId") String appmodelId,
			@RequestParam( ) @ApiParam(value = "页数") Integer pageNum,
			@RequestParam( ) @ApiParam(value = "条数") Integer pageSize,
			@RequestParam( ) @ApiParam(value = "0------上架，1---------下架（仓库中）  2已售完") Integer type,
			@RequestParam @ApiParam(value = "商品名", required = false) String productName) {
		PageHelper.startPage(pageNum, pageSize);
		if (type.equals(0) || type.equals(2)) {
			PageHelper.orderBy("release_time desc");
		}
		if (type.equals(1)) {
			PageHelper.orderBy("down_shelves_time desc");
		}
		List<AgentProduct> list = agentProductService.findAgentProduct(appmodelId, productName, type);
		PageInfo pageInfo = new PageInfo(list);
		return ResultGenerator.genSuccessResult(pageInfo);
	}


}
