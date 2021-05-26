package com.medusa.basemall.agent.controller;

import com.medusa.basemall.agent.entity.AgentGrade;
import com.medusa.basemall.agent.service.AgentGradeService;
import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.VersionNumber;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by medusa on 2018/07/16.
 */
@RestController
@RequestMapping("/agent/grade")
@Api(tags = "所有接口")
@VersionManager(versoinNumber = VersionNumber.MARKETINGVERSION)
public class AgentGradeController {

    @Resource
    private AgentGradeService agentGradeService;

    @PutMapping("/v1/update")
	@ApiOperation(value = "编辑代理等级",tags = "更新接口")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success",response = AgentGrade.class,responseContainer = "List"),
			@ApiResponse(code = 99, message = "查询失败")})
	@ApiImplicitParams({
			@ApiImplicitParam(name ="agentGradeId",value = "代理等级表id"),
			@ApiImplicitParam(name ="gradeDiscount",value = "等级折扣"),
			@ApiImplicitParam(name ="upgradePrice",value = "升级条件"),
			@ApiImplicitParam(name ="gradeInfo",value = "代理等级说明"),
			@ApiImplicitParam(name ="editState",value = "1-普通代理  2-金牌代理")
	})
	public Result update(@RequestBody @ApiParam(hidden = true)AgentGrade agentGrade) {
        return  agentGradeService.updateGrade(agentGrade);
    }

    @GetMapping("/v1/list")
	@ApiOperation(value = "查询商家代理等级",tags = "查询接口")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success",response = AgentGrade.class,responseContainer = "List"),
			@ApiResponse(code = 99, message = "查询失败")})
    public Result list(@RequestParam @ApiParam(value = "商家wxAppId")String appmodelId) {
	    List<AgentGrade> agentGrades = agentGradeService.findByAppmodelId(appmodelId);
	    return  ResultGenerator.genSuccessResult(agentGrades);
    }

    @PostMapping("/v1/initAgentGrade")
	@ApiIgnore
    public Result initAgentGrade(@RequestBody AgentGrade agentGrade){
        agentGradeService.initAgentGrade(agentGrade.getAppmodelId());
        return ResultGenerator.genSuccessResult();
    }


}
