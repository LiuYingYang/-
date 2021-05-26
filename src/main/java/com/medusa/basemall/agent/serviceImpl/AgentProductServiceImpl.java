package com.medusa.basemall.agent.serviceImpl;

import com.medusa.basemall.agent.dao.AgentProductMapper;
import com.medusa.basemall.agent.entity.AgentProduct;
import com.medusa.basemall.agent.service.AgentProductService;
import com.medusa.basemall.agent.vo.AgentVo;
import com.medusa.basemall.agent.vo.PitchonProduct;
import com.medusa.basemall.agent.vo.SpecVo;
import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.core.ServiceException;
import com.medusa.basemall.product.dao.ProductSpecItemMapper;
import com.medusa.basemall.product.entity.ProductSpecItem;
import com.medusa.basemall.utils.TimeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


/**
 * @author medusa
 * @date 2018/06/02
 * 需要事物时添加  @Transactional
 */

@Service
public class AgentProductServiceImpl extends AbstractService<AgentProduct> implements AgentProductService {

	@Resource
	private AgentProductMapper tAgentProductMapper;
	@Resource
	private ProductSpecItemMapper tProductSpecItemMapper;

	@Override
	public Result createAgentProduct(List<PitchonProduct> pitchonProducts) {
		if (pitchonProducts.size() > 0) {
			List<AgentProduct> agentProducts = new ArrayList<>();
			AgentProduct agentProduct = new AgentProduct();
			agentProduct.setDeleteState(0);
			agentProduct.setReleaseTime(TimeUtil.getNowTime());
			agentProduct.setAgentSalesVolume(0);
			agentProduct.setAgentShelfState(0);
			agentProduct.setAppmodelId(pitchonProducts.get(0).getAppmodelId());
			for (PitchonProduct pitchonProduct : pitchonProducts) {
				AgentProduct product = new AgentProduct();
				BeanUtils.copyProperties(agentProduct, product);
				product.setProductId(pitchonProduct.getProductId());
				product.setPrice(pitchonProduct.getPrice().doubleValue());
				product.setSpecType(pitchonProduct.getSpecType());
				product.setAgentStock(pitchonProduct.getAgentStock());
				product.setProductName(pitchonProduct.getProductName());
				if (product.getSpecType().equals(true)) {
					AtomicReference<Integer> totleStocl = new AtomicReference<>(0);
					ProductSpecItem specItem = new ProductSpecItem();
					List<SpecVo> specVos = pitchonProduct.getSpecVos();
					specVos.forEach(spec -> {
						totleStocl.updateAndGet(v -> v + spec.getAgentStock());
						specItem.setProductSpecItemId(spec.getSpecItemId());
						specItem.setAgentStock(spec.getAgentStock());
						tProductSpecItemMapper.updateByPrimaryKeySelective(specItem);
					});
					product.setAgentStock(totleStocl.get());
				}
				agentProducts.add(product);
			}
			if (tAgentProductMapper.insertList(agentProducts) > 0) {
				return ResultGenerator.genSuccessResult();
			}
		}
		return ResultGenerator.genFailResult("参数错误");
	}

	@Override
	public Result updateProduct(AgentVo agentVo) {
		String[] split = agentVo.getAgentProductIds().split(",");
		for (int i = 0; i < split.length; i++) {
			AgentProduct product = new AgentProduct();
			product.setAgentProductId(Long.valueOf(split[i]));
			if (agentVo.getType().equals(0) || agentVo.getType().equals(1)) {
				product.setAgentShelfState(agentVo.getType());
				tAgentProductMapper.updateByPrimaryKeySelective(product);
				continue;
			}
			throw new ServiceException("非法操作");
		}
		return ResultGenerator.genSuccessResult();
	}

	@Override
	public Result deleteProduct(String agentProductIds) {
		String[] split = agentProductIds.split(",");
		for (int i = 0; i < split.length; i++) {
			AgentProduct product = new AgentProduct();
			product.setAgentProductId(Long.valueOf(split[i]));
			product.setDeleteState(1);
			tAgentProductMapper.updateByPrimaryKeySelective(product);
		}
		return ResultGenerator.genSuccessResult();
	}

	@Override
	public List<AgentProduct> findAgentProduct(String appmodelId, String productName, Integer type) {

		Condition condition = new Condition(AgentProduct.class);
		condition.createCriteria().andEqualTo("appmodelId", appmodelId)
				.andEqualTo("deleteState", 0)
				.andEqualTo("agentShelfState",type);
		if (null != productName && !productName.equals("")) {
			char[] chars = productName.toCharArray();
			StringBuffer buffer = new StringBuffer("%");
			for (char aChar : chars) {
				buffer.append(aChar + "%");
			}
			condition.and().andLike("productName", buffer.toString());
		}

		StringBuffer ids = new StringBuffer();
		List<AgentProduct> list = tAgentProductMapper.selectByCondition(condition);
		list.forEach(obj -> {
			if (obj.getSpecType().equals(true)) {
				ids.append(obj.getProductId() + ",");
			}
		});
		if (ids.length() > 0) {
			Condition spec = new Condition(ProductSpecItem.class);
			spec.createCriteria().andIn("productId", Arrays.asList(ids.substring(0, ids.length() - 1).split(",")));
			List<ProductSpecItem> specItems = tProductSpecItemMapper.selectByCondition(spec);
			list.forEach(obj -> {
				if (obj.getSpecType().equals(true)) {
					List<ProductSpecItem> collect = specItems.stream()
							.filter(obi -> obi.getProductId().equals(obj.getProductId())).collect(Collectors.toList());
					Collections.sort(collect, Comparator.comparing(ProductSpecItem::getPrice));
					if (collect.size() < 2) {
						obj.setMaxPrice(collect.get(0).getPrice());
					} else {
						obj.setMinPrice(collect.get(0).getPrice());
						obj.setMaxPrice(collect.get(collect.size() - 1).getPrice());
					}
				}
			});
		}
		return list;
	}
}
