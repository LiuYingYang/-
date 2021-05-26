package com.medusa.basemall.agent.serviceImpl;

import com.medusa.basemall.agent.dao.AgentPurchaseRepository;
import com.medusa.basemall.agent.entity.AgentPurchase;
import com.medusa.basemall.agent.service.AgentPurchaseService;
import com.medusa.basemall.agent.vo.AgentPurchaseVo;
import com.medusa.basemall.agent.vo.PurchaseVo;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.utils.TimeUtil;
import com.vip.vjtools.vjkit.mapper.BeanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 *
 * @author medusa
 * @date 2018/06/02
 * 需要事物时添加  @Transactional
 */

@Service
public class AgentPurchaseServiceImpl implements AgentPurchaseService {

	@Resource
	private AgentPurchaseRepository tAgentPurchaseRepository;

	@Override
	public AgentPurchase addPurchase(AgentPurchaseVo purchaseVo) {
		AgentPurchase purchase = BeanMapper.map(purchaseVo, AgentPurchase.class);
		List<AgentPurchase> oldBuys = tAgentPurchaseRepository
				.findByProductIdAndAppmodelIdAndWxuserId(purchase.getProductId(), purchase.getAppmodelId(),
						purchase.getWxuserId());
		//过滤规格相同
		if (null != purchase.getProductSpecItemInfo().getProductSpecItemId()) {
			//过滤有规格,相同的商品
			oldBuys = oldBuys.stream().filter(obj -> obj.getProductSpecItemInfo().getProductSpecItemId()
					.equals(purchase.getProductSpecItemInfo().getProductSpecItemId())).collect(Collectors.toList());
		} else {
			//过滤无规格相同的商品
			oldBuys = oldBuys.stream().filter(obj -> obj.getProductId().equals(purchase.getProductId()))
					.collect(Collectors.toList());
		}
		if (null != oldBuys && oldBuys.size() > 0) {
			int quantity = oldBuys.get(0).getQuantity();
			oldBuys.get(0).setQuantity(purchase.getQuantity() + quantity);
			oldBuys.get(0)
					.setCountPrice(purchase.getProductSpecItemInfo().getPrice() * (purchase.getQuantity() + quantity));
			oldBuys.get(0).setCreateTime(TimeUtil.getNowTime());
			if (null != purchase.getProductSpecItemInfo().getSpecificationValue()) {
				oldBuys.get(0).getProductSpecItemInfo().setSpecificationValue(purchase.getProductSpecItemInfo().getSpecificationValue());
			}
			AgentPurchase save = tAgentPurchaseRepository.save(oldBuys.get(0));
			return save;
		} else {
			purchase.setCreateTime(TimeUtil.getNowTime());
			purchase.setCountPrice(purchase.getQuantity() * purchase.getProductSpecItemInfo().getPrice());
			AgentPurchase insert = tAgentPurchaseRepository.insert(purchase);
			return insert;
		}
	}

	@Override
	public Result findPurchase(Long wxuserId, String appmodelId) {
		List<AgentPurchase> list = tAgentPurchaseRepository
				.findByWxuserIdAndAppmodelId(wxuserId, appmodelId);
		List<AgentPurchase> collect1 = list.stream().filter(obj -> obj.getShelfState() == 0)
				.collect(Collectors.toList());
		List<AgentPurchase> collect2 = list.stream().filter(obj -> obj.getShelfState() == 1 || obj.getShelfState() == 2)
				.collect(Collectors.toList());
		PurchaseVo vo =  new PurchaseVo();
		vo.setAvailable(collect1);
		vo.setNotAvailable(collect2);
		return ResultGenerator.genSuccessResult(vo);
	}

	@Override
	public Result batchDelete(String purchaseIds, Long wxuserId, Integer type) {
		if (type.equals(1)) {
			String[] s = purchaseIds.split(",");
			List<AgentPurchase> purchases = new ArrayList<>();
			for (int j = 0; j < s.length; j++) {
				AgentPurchase purchase = new AgentPurchase();
				purchase.setPurchaseId(s[j]);
				purchases.add(purchase);
			}
			tAgentPurchaseRepository.deleteAll(purchases);
			return ResultGenerator.genSuccessResult();
		}
		if (type.equals(2)) {
			tAgentPurchaseRepository.deleteByWxuserId(wxuserId);
			return ResultGenerator.genSuccessResult();
		}
		return null;
	}


	@Override
	public Result findPurchaseSum(Long wxuserId, String appmodelId) {
		List<AgentPurchase> byWxuserIdAndAndShelfState = tAgentPurchaseRepository
				.findByWxuserIdAndAppmodelIdAndShelfState(wxuserId, appmodelId, 0);
		return ResultGenerator.genSuccessResult(byWxuserIdAndAndShelfState.size());
	}

	@Override
	public Result updatePurchase(AgentPurchase purchase) {
		List<AgentPurchase> oldBuys = tAgentPurchaseRepository
				.findByProductIdAndAppmodelIdAndWxuserId(purchase.getProductId(), purchase.getAppmodelId(),
						purchase.getWxuserId());
		if (null != oldBuys) {
			if (oldBuys.size() > 1) {
				oldBuys.sort(Comparator.comparing(AgentPurchase::getQuantity));
				//如果出现重复取多个中数量最大的,并删除其他
				purchase.setQuantity(oldBuys.get(oldBuys.size() - 1).getQuantity());
				oldBuys.forEach(obj -> {
					tAgentPurchaseRepository.deleteById(obj.getPurchaseId());
				});
				purchase.setCreateTime(TimeUtil.getNowTime());
				purchase.setCountPrice(purchase.getQuantity() * purchase.getProductSpecItemInfo().getPrice());
				AgentPurchase insert = tAgentPurchaseRepository.insert(purchase);
				if (null != insert.getProductId()) {
					return ResultGenerator.genSuccessResult();
				}
			}
			purchase.setCreateTime(TimeUtil.getNowTime());
			purchase.setCountPrice(purchase.getQuantity() * purchase.getProductSpecItemInfo().getPrice());
			tAgentPurchaseRepository.save(purchase);
			return ResultGenerator.genSuccessResult();
		}
		return ResultGenerator.genFailResult("更新失败");
	}
}
