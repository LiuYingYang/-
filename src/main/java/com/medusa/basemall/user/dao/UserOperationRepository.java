package com.medusa.basemall.user.dao;

import com.medusa.basemall.user.entity.UserOperation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOperationRepository  extends MongoRepository<UserOperation, String> {

}
