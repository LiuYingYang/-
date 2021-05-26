package com.medusa.basemall.messages.dao;

import com.medusa.basemall.messages.entity.Messages;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Created by wx on 2018/08/09.
 */
@Repository
public interface MessagesDao extends MongoRepository<Messages, String> {

}
