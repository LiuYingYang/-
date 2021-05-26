package com.medusa.basemall.agent.controller;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.agent.entity.Agent;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class AgentControllerTest extends BasemallApplicationTests {

    @Test
    public void update() {
        Agent agent = new Agent();
        agent.setAgentId(1);
        agent.setAgentPhone("8888888888");
        MockMvcUtil.sendRequest("/agent/update", JSONObject.toJSONString(agent), null, "post");
    }

    @Test
    public void detail() {
        Agent agent = new Agent();
        agent.setAgentId(1);
        MockMvcUtil.sendRequest("/agent/detail", JSONObject.toJSONString(agent), null, "post");
    }

    @Test
    public void list() {
        Map<String, Object> map = new HashMap<>();
        map.put("page", 0);
        map.put("size", 2);
        map.put("agentStatus", 2);
        map.put("appmodelId", Constant.appmodelId);
        MockMvcUtil.sendRequest("/agent/list", JSONObject.toJSONString(map), null, "post");
    }
}