package com.medusa.basemall.agent.dao;

import com.medusa.basemall.agent.entity.AgentPurchase;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AgentPurchaseRepository extends MongoRepository<AgentPurchase,String> {

    List<AgentPurchase> findByProductIdAndAppmodelIdAndWxuserId(Long productId, String appmodelId, Long wxuserId);

    List<AgentPurchase> findByWxuserIdAndAppmodelId(Long wxuserId, String appmodelId);

    int deleteByWxuserId(Long wxuserId);

    List<AgentPurchase> findByWxuserIdAndAppmodelIdAndShelfState(Long wxuserId, String appmodelId, int i);
}