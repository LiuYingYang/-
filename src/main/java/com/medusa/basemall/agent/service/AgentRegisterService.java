package com.medusa.basemall.agent.service;

import com.medusa.basemall.agent.entity.AgentRegister;
import com.medusa.basemall.agent.vo.BindAgentVo;
import com.medusa.basemall.agent.vo.RegisterAgentVo;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.Service;

import java.util.List;

/**
 * @author medusa
 * @date 2018/06/02
 */
public interface AgentRegisterService extends Service<AgentRegister> {

    Result agentRegister(RegisterAgentVo agentRegister);

    Result bindingRegister(BindAgentVo agentVo);

    List<AgentRegister> findRegisteAgent(Integer type, String appmodelId);

    Result refuse(Integer regState, String refuseRemark, String regIds, String appmodelId);

    Result updateRegister(AgentRegister agentVo);

}
