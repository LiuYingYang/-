package com.medusa.basemall.agent.service;

import com.medusa.basemall.agent.entity.AgentProduct;
import com.medusa.basemall.agent.vo.AgentVo;
import com.medusa.basemall.agent.vo.PitchonProduct;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.Service;

import java.util.List;

/**
 *
 * @author medusa
 * @date 2018/06/02
 */
public interface AgentProductService extends Service<AgentProduct> {

    Result createAgentProduct(List<PitchonProduct> pitchonProducts);

    Result updateProduct(AgentVo agentVo);

    Result deleteProduct(String agentVo);

    List<AgentProduct> findAgentProduct(String agentVo, String productName, Integer type);
}
