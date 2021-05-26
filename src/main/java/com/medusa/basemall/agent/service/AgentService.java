package com.medusa.basemall.agent.service;

import com.medusa.basemall.agent.entity.Agent;
import com.medusa.basemall.core.Service;

import java.util.List;

/**
 * Created by medusa on 2018/06/02.
 */
public interface AgentService extends Service<Agent> {

    List<Agent> list(String appmodelId, Integer agentStatus);
}
