package com.medusa.basemall.agent.serviceImpl;

import com.medusa.basemall.agent.dao.AgentPurchaseOrderDetailedMapper;
import com.medusa.basemall.agent.entity.AgentPurchaseOrder;
import com.medusa.basemall.agent.entity.AgentPurchaseOrderDetailed;
import com.medusa.basemall.agent.service.AgentPurchaseOrderDetailedService;
import com.medusa.basemall.agent.vo.AgentProductOrderVo;
import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.utils.IdGenerateUtils;
import com.medusa.basemall.utils.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author medusa
 * @date 2018/06/02
 * 需要事物时添加  @Transactional
 */

@Service
public class AgentPurchaseOrderDetailedServiceImpl extends AbstractService<AgentPurchaseOrderDetailed>
		implements AgentPurchaseOrderDetailedService {
	@Resource
	private AgentPurchaseOrderDetailedMapper tAgentPurchaseOrderDetailedMapper;

	@Override
	public void saveOrderDetail(List<AgentProductOrderVo> productList, AgentPurchaseOrder order) {
		String appmodelId = order.getAppmodelId();
		Long purchaseOrderId = order.getPurchaseOrderId();
		List<AgentPurchaseOrderDetailed> detaileds = new ArrayList<>();
		for (AgentProductOrderVo productOrderVo : productList) {
			AgentPurchaseOrderDetailed detailed = new AgentPurchaseOrderDetailed();
			detailed.setPurchaseOrderDetailedId(IdGenerateUtils.getItemId());
			detailed.setPurchaseOrderId(purchaseOrderId);
			detailed.setAppmodelId(appmodelId);
			detailed.setProductId(productOrderVo.getProductId());
			detailed.setProductImg(productOrderVo.getProductImg());
			detailed.setCreateTime(TimeUtil.getNowTime());
			detailed.setProductName(productOrderVo.getProductName());
			detailed.setQuantity(productOrderVo.getQuantity());
			detailed.setPrice(new BigDecimal(productOrderVo.getPrice()));
			detailed.setProductSpecInfo(productOrderVo.getProductSpecInfo());
			detailed.setDeleteState(false);
			detaileds.add(detailed);
			tAgentPurchaseOrderDetailedMapper.insertSelective(detailed);
		}
	}
}
