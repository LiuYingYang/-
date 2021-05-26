package com.medusa.basemall.agent.dao;

import com.medusa.basemall.agent.entity.AgentRegister;
import com.medusa.basemall.core.Mapper;

import java.util.Map;

public interface AgentRegisterMapper extends Mapper<AgentRegister> {
    int refuse(Map<String, Object> map);
}