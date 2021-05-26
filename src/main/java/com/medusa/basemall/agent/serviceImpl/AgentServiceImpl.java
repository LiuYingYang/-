package com.medusa.basemall.agent.serviceImpl;

import com.medusa.basemall.agent.dao.AgentMapper;
import com.medusa.basemall.agent.entity.Agent;
import com.medusa.basemall.agent.service.AgentService;
import com.medusa.basemall.core.AbstractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author medusa
 * @date 2018/06/02
 * 需要事物时添加  @Transactional
 */

@Service
public class AgentServiceImpl extends AbstractService<Agent> implements AgentService {
    @Resource
    private AgentMapper tAgentMapper;

    private Agent agent;

    @Override
    public List<Agent> list(String appmodelId, Integer agentStatus) {
        agent = new Agent();
        agent.setAppmodelId(appmodelId);
        agent.setAgentStatus(agentStatus);
        return tAgentMapper.select(agent);
    }
}
