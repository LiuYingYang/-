package com.medusa.basemall.shop.dao;

import com.medusa.basemall.shop.entity.TransactionRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRecordRepository extends MongoRepository<TransactionRecord,String> {

}
