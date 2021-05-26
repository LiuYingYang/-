package com.medusa.basemall.shop.serviceimpl;

import com.medusa.basemall.shop.dao.TransactionRecordRepository;
import com.medusa.basemall.shop.entity.TransactionRecord;
import com.medusa.basemall.shop.service.TransactionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author whh
 */
@Service
public class TransactionRecordServiceImpl implements TransactionRecordService {

	@Autowired
	private TransactionRecordRepository recordRepository;
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(TransactionRecord transactionRecord) {
		//生成交易记录
		recordRepository.save(transactionRecord);
	}

	@Override
	public Map<String, Object> getBalanceRecord(Integer pageSize, Integer pageNum, String startTime, String endTime,
			String appmodelId) {
		Query query = new Query(Criteria.where("appmodelId").is(appmodelId));
		query.with(PageRequest.of(pageNum - 1, pageSize, Sort.Direction.DESC, "createTime"));
		if (!startTime.equals("")) {
			query.addCriteria((Criteria.where("createTime").gt(startTime)));
		}
		if (!endTime.equals("")) {
			query.addCriteria((Criteria.where("createTime").lt(endTime)));
		}
		List<TransactionRecord> records = mongoTemplate.find(query, TransactionRecord.class);
		long count = mongoTemplate.count(query, TransactionRecord.class);
		Map<String,Object> map = new HashMap<>();
		map.put("list",records);
		map.put("totle",count);
		return map;
	}

}
