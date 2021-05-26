package com.medusa.basemall.user.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.user.dao.FootMarkRepository;
import com.medusa.basemall.user.entity.FootMark;
import com.medusa.basemall.user.service.FootMarkService;
import com.mongodb.client.result.UpdateResult;
import com.vip.vjtools.vjkit.time.ClockUtil;
import com.vip.vjtools.vjkit.time.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author whh
 */
@Service
public class FootMarkServiceImpl implements FootMarkService {

	@Autowired
	private FootMarkRepository footMarkRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Result save(FootMark footMark) {
		Page<FootMark> totle = footMarkRepository
				.findByWxuserIdAndAppmodelId(footMark.getWxuserId(), footMark.getAppmodelId(),
						PageRequest.of(0, 30, Sort.Direction.DESC, "updateTime"));
		String updateTime = DateFormatUtil.ISO_ON_DATE_FORMAT.format(ClockUtil.currentDate());
		if (null != totle && totle.getTotalElements() < 30) {
			//查询30条足迹中是否查看过改商品,查看过则更新时间
			List<FootMark> collect = totle.getContent().stream()
					.filter(obj -> obj.getProductId().equals(footMark.getProductId())).collect(Collectors.toList());
			if (collect.size() > 0) {
				FootMark f = collect.get(0);
				f.setUpdateTime(updateTime);
				f.setState(footMark.getState());
				footMarkRepository.save(f);
				if ("" != f.getFootmarkId()) {
					return ResultGenerator.genSuccessResult();
				}
			} else {
				footMark.setUpdateTime(updateTime);
				FootMark insert = footMarkRepository.save(footMark);
				if ("" != insert.getFootmarkId()) {
					return ResultGenerator.genSuccessResult();
				}
			}
		} else if (null != totle && totle.getTotalElements() >= 30) {
			//查询30条足迹中是否查看过该商品,查看过则更新时间
			List<FootMark> collect = totle.getContent().stream()
					.filter(obj -> obj.getProductId().equals(footMark.getProductId())).collect(Collectors.toList());
			if (collect.size() > 0) {
				FootMark f = collect.get(0);
				f.setUpdateTime(updateTime);
				f.setState(footMark.getState());
				footMarkRepository.save(f);
				if ("" != f.getFootmarkId()) {
					return ResultGenerator.genSuccessResult();
				}
			} else {
				List<FootMark> content = totle.getContent();
				Collections.sort(content, Comparator.comparing(FootMark::getUpdateTime));
				footMarkRepository.deleteById(content.get(0).getFootmarkId());
				footMark.setUpdateTime(updateTime);
				FootMark insert = footMarkRepository.save(footMark);
				if ("" != insert.getFootmarkId()) {
					return ResultGenerator.genSuccessResult();
				}
			}
			return ResultGenerator.genSuccessResult();
		}
		return ResultGenerator.genFailResult("添加失败");
	}

	@Override
	public Result delete(Long wxuserId, Integer type, String footmarkId) {
		if (type.equals(2)) {
			footMarkRepository.deleteByWxuserId(wxuserId);
			return ResultGenerator.genSuccessResult();
		}
		if (type.equals(1)) {
			footMarkRepository.deleteByFootmarkId(footmarkId);
			return ResultGenerator.genSuccessResult();
		}
		return ResultGenerator.genFailResult("删除失败");
	}

	@Override
	public Result findUserFootMark(Long wxuserId, String appmodelId, Integer page, Integer size) {

		Page<FootMark> footMarks = footMarkRepository.findByWxuserIdAndAppmodelId(wxuserId, appmodelId,
				PageRequest.of(page - 1, size, Sort.Direction.DESC, "updateTime"));
		List<FootMark> content = footMarks.getContent();
		List<String> collect = content.stream().map(FootMark::getUpdateTime).distinct().collect(Collectors.toList());
		List<String> list = new ArrayList<>();
		try {
			for (String s : collect) {
				List<FootMark> temp = new ArrayList<>();
				for (FootMark footMark : content) {
					if (footMark.getUpdateTime().equals(s)) {
						Date date = DateFormatUtil.ISO_ON_DATE_FORMAT.parse(footMark.getUpdateTime());
						String tiem = DateFormatUtil.formatDate("MM月dd日", date);
						footMark.setUpdateTime(tiem);
						temp.add(footMark);
					}
				}

				list.add(JSON.toJSONString(temp));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ResultGenerator.genSuccessResult(list);
	}

	@Override
	public int count(Long wxuserId) {
		List<FootMark> collects = footMarkRepository.findByWxuserIdEquals(wxuserId);
		return collects.size();
	}

	@Override
	public Long updateShelfState(List<Long> productIds, Integer shelfState) {
		Query query = new Query(Criteria.where("product_id").in(productIds));
		UpdateResult state = mongoTemplate.updateMulti(query, Update.update("state", shelfState), FootMark.class);
		return state.getModifiedCount();
	}

	@Override
	public void updateFootMarks(List<FootMark> footMarks) {
		footMarkRepository.saveAll(footMarks);
	}

	@Override
	public List<FootMark> findByProductId(List<Long> productId) {
		Query query = new Query(Criteria.where("productId").in(productId));
		return mongoTemplate.find(query, FootMark.class);
	}

	@Override
	public void updateActivityMark(Product product, String appmodelId) {
		Query query = new Query(
				Criteria.where("product_id").is(product.getProductId()).and("appmodelId").is(appmodelId));
		List<FootMark> footMarks = mongoTemplate.find(query, FootMark.class);
		if (footMarks != null && footMarks.size() > 0) {
			footMarks.forEach(obj -> {
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
