package com.medusa.basemall.agent.dao;

import com.medusa.basemall.agent.entity.AgentPurchaseOrder;
import com.medusa.basemall.agent.vo.BackstageOrderListVo;
import com.medusa.basemall.agent.vo.MiniOrderListVo;
import com.medusa.basemall.core.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AgentPurchaseOrderMapper extends Mapper<AgentPurchaseOrder> {
	AgentPurchaseOrder selectByOutTradeNo(String outTradeNo);

	List<MiniOrderListVo>  selectMiniList(Map<String, Object> wxuserId);

	MiniOrderListVo selectMiniListDetail(Long purchaseOrderId);

	List<BackstageOrderListVo> selectBackstageList(Map<String, Object> map);

	void closeOrder(@Param("closeType") Integer closeType, @Param("purchaseOrderIds") String[] purchaseOrderIds);

	BackstageOrderListVo selectBackstageDetail(Long purchaseOrderId);
}