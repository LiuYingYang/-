package com.medusa.basemall.agent.dao;

import com.medusa.basemall.agent.entity.Agent;
import com.medusa.basemall.core.Mapper;

import java.util.Map;

public interface AgentMapper extends Mapper<Agent> {

    int updateAgentSwitch(Map<String, Object> map);
}