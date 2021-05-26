package com.medusa.basemall.agent.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medusa.basemall.agent.entity.Agent;
import com.medusa.basemall.agent.service.AgentService;
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
@RequestMapping("/agent")
@Api(tags = "所有接口")
@VersionManager(versoinNumber = VersionNumber.MARKETINGVERSION)
public class AgentController {

	@Resource
	private AgentService agentService;

	@PutMapping("/v1/update")
	@ApiOperation(value = "更新代理人信息",tags = "更新接口")
	public Result update(@RequestBody  Agent agent) {
		agentService.update(agent);
		return ResultGenerator.genSuccessResult();
	}

	@GetMapping("/v1/detail")
	@ApiOperation(value = "查询单个代理人", tags = "查询接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success", response = Agent.class),
			@ApiResponse(code = 99, message = "查询失败")})
	public Result detail(@RequestParam @ApiParam(value = "代理id") Integer agent_id) {
		Agent byId = agentService.findById(agent_id);
		return ResultGenerator.genSuccessResult(byId);
	}

	@GetMapping("/v1/list")
	@ApiOperation(value = "查询代理人(禁用/全部)", tags = "查询接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success", response = Agent.class, responseContainer = "List"),
			@ApiResponse(code = 99, message = "查询失败")})
	public Result list(@RequestParam @ApiParam(value = "页数") Integer pageNum,
			@RequestParam @ApiParam(value = "条数") Integer pageSize,
			@RequestParam @ApiParam(value = "商家wxAppId") String appmodelId,
			@RequestParam @ApiParam(value = "  状态 1通过审核，2禁用") Integer agentStatus) {
		PageHelper.startPage(pageNum, pageSize);
		List<Agent> list = agentService.list(appmodelId, agentStatus);
		PageInfo<Agent> pageInfo = new PageInfo<>(list);
		return ResultGenerator.genSuccessResult(pageInfo);
	}

}
