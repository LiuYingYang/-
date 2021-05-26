package com.medusa.basemall.user.serviceimpl;

import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.user.dao.CollectRepository;
import com.medusa.basemall.user.entity.Collect;
import com.medusa.basemall.user.service.CollectService;
import com.medusa.basemall.utils.TimeUtil;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author whh
 */
@Service
public class CollectServiceImpl implements CollectService {

	@Resource
	private CollectRepository collectRepository;

	@Resource
	private MongoTemplate mongoTemplate;

	@Override
	public Result add(Collect collect) {
		Page<Collect> collects = collectRepository
				.findByWxuserIdAndAppmodelId(collect.getWxuserId(), collect.getAppmodelId(), null);
		if (collects.getTotalElements() > 0) {
			List<Collect> c = collects.getContent().stream()
					.filter(obj -> obj.getProductId().equals(collect.getProductId())).collect(Collectors.toList());
			if (c.size() > 0) {
				return ResultGenerator.genFailResult("已经收藏过该商品");
			}
		}
		collect.setCreateTime(TimeUtil.getNowTime());
		Collect save = collectRepository.save(collect);
		if (save.getCollectId() == null || "".equals(save.getCollectId())) {
			return ResultGenerator.genFailResult("添加失败");
		}
		return ResultGenerator.genSuccessResult();
	}

	@Override
	public Result list(Long wxuserId, String appmodelId, Integer page, Integer size) {
		QSort orders = new QSort();
		Sort sort = new Sort(Sort.Direction.DESC, "create_time");
		orders.and(sort);
		Pageable pageRequest = new QPageRequest(page - 1, size, orders);
		Page<Collect> list = collectRepository.findByWxuserIdAndAppmodelId(wxuserId, appmodelId, pageRequest);

		return ResultGenerator.genSuccessResult(list.getContent());
	}

	@Override
	public Result delete(String collectId) {
		collectRepository.deleteById(collectId);
		return ResultGenerator.genSuccessResult();
	}

	@Override
	public Collect detailed(String collectId) {
		Optional<Collect> byId = collectRepository.findById(collectId);
		if (byId.isPresent()) {
			return byId.get();
		}
		return null;
	}

	@Override
	public Collect findWxuserCollect(Long wxuserId, Long productId) {
		Collect collect = collectRepository.findByWxuserIdAndProductId(wxuserId, productId);
		return collect;
	}

	@Override
	public int count(Long wxuserId) {
		List<Collect> byWxuserIdEquals = collectRepository.findByWxuserIdEquals(wxuserId);
		return byWxuserIdEquals.size();
	}

	@Override
	public Long updateShelfState(List<Long> productIds, Integer shelfState) {
		Query query = new Query(Criteria.where("product_id").in(productIds.toArray()));
		UpdateResult state = mongoTemplate.updateMulti(query, Update.update("state", shelfState), Collect.class);
		return state.getMatchedCount();
	}

	@Override
	public List<Collect> findByProductId(List<Long> productId) {
		Query query = new Query(Criteria.where("productId").in(productId));
		return mongoTemplate.find(query, Collect.class);
	}

	@Override
	public void updateCollects(List<Collect> collects) {
		collectRepository.saveAll(collects);
	}

	@Override
	public void updateActivityMark(Product product, String appmodelId) {
		Query query = new Query(
				Criteria.where("product_id").is(product.getProductId()).and("appmodelId").is(appmodelId));
		List<Collect> collects = mongoTemplate.find(query, Collect.class);
		if(collects!=null && collects.size() > 0){
			collects.forEach(obj -> {
				obj.setProductTypeList(product.getProductTypeList());
				if (product.getMarketPrice() != null) {
					obj.setMaxPrice(product.getMarketPrice());
				}
				obj.setMinPrice(product.getPrice());
				mongoTemplate.save(obj);
			});
		}

	}
}
