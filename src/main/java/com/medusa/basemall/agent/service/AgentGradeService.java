package com.medusa.basemall.agent.service;

import com.medusa.basemall.agent.entity.AgentGrade;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.Service;

import java.util.List;

/**
 * Created by medusa on 2018/07/16.
 */
public interface AgentGradeService extends Service<AgentGrade> {

    List<AgentGrade> findByAppmodelId(String appmodelId);

    Result updateGrade(AgentGrade agentGrade);

    void initAgentGrade(String appmodelId);
}
