package com.medusa.basemall.agent.controller;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.agent.entity.AgentRegister;
import com.medusa.basemall.agent.vo.AgentVo;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import com.medusa.basemall.utils.RegexMatches;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgentRegisterControllerTest extends BasemallApplicationTests {

	private Logger LOGGER = LoggerFactory.getLogger(AgentRegisterControllerTest.class);

	//后端代理申请测试1
	@Test
	public void agentRegister1() {
		AgentRegister register = new AgentRegister();
		register.setRegName(RegexMatches.getRandomChar(3));
		register.setRegPhone("15888030961");
		register.setType(2);
		register.setAgentDomain(RegexMatches.getRoad());
		register.setAgentGradeId(7);
		register.setAppmodelId(Constant.appmodelId);
		MockMvcUtil.sendRequest("/agent/register/agentRegister", JSONObject.toJSONString(register), null, "post");
	}

	//前端代理申请测试
	@Test
	public void agentRegister2() {
		for (int i = 0; i < 1; i++) {
			AgentRegister register = new AgentRegister();
			register.setWxuserId(111L);
			register.setRegName(RegexMatches.getRandomChar(3));
			register.setRegPhone("15888030961");
			register.setRegCode("1067654");
			register.setType(1);
			register.setAgentDomain(RegexMatches.getRoad());
			register.setAgentGradeId(7);
			register.setAppmodelId(Constant.appmodelId);
			MockMvcUtil.sendRequest("/agent/register/agentRegister", JSONObject.toJSONString(register), null, "post");
		}
	}


	@Test
	public void bindingRegister() {
		AgentVo agentVo = new AgentVo();
		agentVo.setWxuserid(1531212601449327L);
		agentVo.setRegPhone("15888030961");
		agentVo.setRegCode("370198");
		agentVo.setBingCode("174024");
		agentVo.setAppmodelId(Constant.appmodelIdy);
		agentVo.setType(2);
		MockMvcUtil.sendRequest("/agent/register/bindingRegister", JSONObject.toJSONString(agentVo), null, "put");
	}

	@Test
	public void deleteReg() {
		Map<String, Object> map = new HashMap<>();
		map.put("regIds", "21,22,23");
		MockMvcUtil.sendRequest("/agent/register/deleteReg", JSONObject.toJSONString(map), null, "post");
	}

	//查询待待审核
	@Test
	public void findRegisteAgent1() {
		AgentVo agentVo = new AgentVo();
		agentVo.setRegState(0);
		agentVo.setPageNum(0);
		agentVo.setPageSize(0);
		agentVo.setType(1);
		MockMvcUtil.sendRequest("/agent/register/findRegisteAgent", JSONObject.toJSONString(agentVo), null, "post");
	}

	//查询已拒绝
	@Test
	public void findRegisteAgent2() {
		AgentVo agentVo = new AgentVo();
		agentVo.setRegState(0);
		agentVo.setPageNum(0);
		agentVo.setPageSize(0);
		agentVo.setType(1);
		MockMvcUtil.sendRequest("/agent/register/findRegisteAgent", JSONObject.toJSONString(agentVo), null, "post");

	}

	//查询待验证
	@Test
	public void findRegisteAgent3() {
		AgentVo agentVo = new AgentVo();
		agentVo.setRegState(0);
		agentVo.setPageNum(0);
		agentVo.setPageSize(0);
		agentVo.setType(2);
		MockMvcUtil.sendRequest("/agent/register/findRegisteAgent", JSONObject.toJSONString(agentVo), null, "post");
	}

	@Test
	public void refuse() {
		Map<String, Object> map = new HashMap<>();
		List<Integer> regIds = new ArrayList<>();
		regIds.add(32);
		map.put("regIds", regIds);
		map.put("regState", 1);
		map.put("appmodelId", Constant.appmodelId);
		MockMvcUtil.sendRequest("/agent/register/refuse", JSONObject.toJSONString(map), null, "post");

	}

	@Test
	public void updateRegister() {
		AgentVo agentVo = new AgentVo();
		agentVo.setRegId(27);
		agentVo.setRegName("12344");
		agentVo.setAgentGrade(2);
		agentVo.setAppmodelId(Constant.appmodelId);
		MockMvcUtil.sendRequest("/agent/register/updateRegister", JSONObject.toJSONString(agentVo), null, "post");
	}

	@Test
	public void detailRegister() {
		AgentVo agentVo = new AgentVo();
		agentVo.setRegId(26);

		MockMvcUtil.sendRequest("/agent/register/detailRegister", JSONObject.toJSONString(agentVo), null, "post");
	}
}