package com.medusa.basemall.agent.service;

import com.medusa.basemall.agent.entity.AgentPurchase;
import com.medusa.basemall.agent.vo.AgentPurchaseVo;
import com.medusa.basemall.core.Result;

/**
 * Created by medusa on 2018/06/02.
 */
public interface AgentPurchaseService {


    Result findPurchase(Long purchase, String appmodelId);

    Result batchDelete(String purchaseIds, Long wxuserId, Integer type);

    AgentPurchase addPurchase(AgentPurchaseVo purchase);

	Result findPurchaseSum(Long wxuserId, String appmodelId);

	Result updatePurchase(AgentPurchase purchase);

}
