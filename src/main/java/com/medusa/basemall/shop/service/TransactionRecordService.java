package com.medusa.basemall.shop.service;

import com.medusa.basemall.shop.entity.TransactionRecord;

import java.util.Map;

public interface TransactionRecordService {

	void save(TransactionRecord transactionRecord);


	Map<String, Object> getBalanceRecord(Integer pageSize, Integer pageNum, String startTime, String endTime, String appmodelId);
}
