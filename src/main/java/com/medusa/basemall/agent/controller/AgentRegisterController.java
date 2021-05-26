package com.medusa.basemall.agent.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medusa.basemall.agent.entity.AgentRegister;
import com.medusa.basemall.agent.service.AgentRegisterService;
import com.medusa.basemall.agent.vo.BindAgentVo;
import com.medusa.basemall.agent.vo.RegisterAgentVo;
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
@RequestMapping("/agent/register")
@Api(tags = "所有接口")
@VersionManager(versoinNumber = VersionNumber.MARKETINGVERSION)
public class AgentRegisterController {


    @Resource
    private AgentRegisterService agentRegisterService;

    /**
     * 申请代理
     * @param registerAgentVo
     * @return
     */
    @PostMapping("/v1/agentRegister")
	@ApiOperation(value = "申请代理",tags = "添加接口")
    public Result agentRegister(@RequestBody RegisterAgentVo registerAgentVo) {
        return agentRegisterService.agentRegister(registerAgentVo);
    }

    /**
     * 绑定代理
     *
     * @param bindAgentVo
     * @return
     */
    @PutMapping("/v1/bindingRegister")
	@ApiOperation(value = "绑定代理",tags = "更新接口")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success"),
			@ApiResponse(code = 99, message = "验证码超时/绑定失败/绑定码已被绑定/绑定码无效/验证码错误")})
	public Result bindingRegister(@RequestBody BindAgentVo bindAgentVo) {
        return agentRegisterService.bindingRegister(bindAgentVo);
    }

    /**
     * 申请表记录删除
     * regIds    //多个id用逗号分隔,批量刪除 eg：ids -> “1,2,3,4”
     * @return
     */
    @DeleteMapping("/v1/deleteReg")
	@ApiOperation(value = "申请表记录删除",tags = "删除接口")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success"),
			@ApiResponse(code = 99, message = "删除失败")})
	public Result deleteReg(@RequestParam @ApiParam(value = "申请表id 多个id用逗号分隔,批量刪除 ") String regIds) {
        agentRegisterService.deleteByIds(regIds);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 查询待审核/已拒绝/待验证

     * @return
     */
    @GetMapping("/v1/findRegisteAgent")
	@ApiOperation(value = "查询待审核/已拒绝/待验证",tags = "查询接口")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success", response = AgentRegister.class, responseContainer = "List"),
			@ApiResponse(code = 99, message = "查询失败")})
    public Result findRegisteAgent(@RequestParam @ApiParam(value = "查询类型 0待审核 1已拒绝 2.待验证") Integer type,
			@RequestParam @ApiParam(value = "页数")Integer pageNum,
			@RequestParam @ApiParam(value = "条数")Integer pageSize,
			@RequestParam @ApiParam(value = "商家wxAppId")String appmodelId) {
        PageHelper.startPage(pageNum,pageSize);
        List<AgentRegister> list = agentRegisterService.findRegisteAgent(type,appmodelId);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 代理申请同意/拒绝
     *
     * @return
     */
    @PutMapping("/v1/refuse")
	@ApiOperation(value = "代理申请同意/拒绝",tags = "更新接口",notes = "swagger调试不可用")
	@ApiImplicitParams({
			@ApiImplicitParam(name ="regState",value = "审核状态   1-通过   0-不通过"),
			@ApiImplicitParam(name ="refuseRemar   同意不必写k",value = "拒绝原因"),
			@ApiImplicitParam(name ="regIds",value = "申请id 字符串 用,逗号分隔   1,2,3,4"),
			@ApiImplicitParam(name ="appmodelId",value = "商家wxAppId"),
	})
	@ApiResponses({
			@ApiResponse(code = 100, message = "success"),
			@ApiResponse(code = 99, message = "操作失败/参数有误")})
    public Result refuse(@RequestBody @ApiParam(hidden = true) JSONObject jsonObject) {
        Integer regState = jsonObject.getInteger("regState");
        String refuseRemark = jsonObject.getString("refuseRemark");
        String regIds = jsonObject.getString("regIds");
        String appmodelId = jsonObject.getString("appmodelId");
        return agentRegisterService.refuse(regState,refuseRemark,regIds,appmodelId);
    }

    /**
     * 更新申请人信息
     *
     * @return
     */
    @PutMapping("/v1/updateRegister")
	@ApiOperation(value = "更新申请人信息",tags = "更新接口",notes = "swagger调试不可用")
	@ApiImplicitParams({
			@ApiImplicitParam(name ="regId",value = "申请id"),
			@ApiImplicitParam(name ="appmodelId",value = "商家wxAppId"),
			@ApiImplicitParam(name ="regPhone",value = "申请人手机号"),
			@ApiImplicitParam(name ="agentGrade",value = "申请等级   前端申请默认为1   后端可选择"),
	})
	@ApiResponses({
			@ApiResponse(code = 100, message = "success"),
			@ApiResponse(code = 99, message = "更新失败")})
    public Result updateRegister(@RequestBody @ApiParam(hidden = true) AgentRegister agentRegister) {
        return agentRegisterService.updateRegister(agentRegister);
    }

    /**
     * 编辑申请人信息
     *
     * @return
     */
    @GetMapping("/v1/detailRegister")
	@ApiOperation(value = "查询申请人信息",tags = "查询接口")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success",response = AgentRegister.class),
			@ApiResponse(code = 99, message = "查询失败")})
    public Result detailRegister(@RequestBody @ApiParam(value = "regId") Integer regId) {
        return ResultGenerator.genSuccessResult(agentRegisterService.findById(regId));
    }

}
