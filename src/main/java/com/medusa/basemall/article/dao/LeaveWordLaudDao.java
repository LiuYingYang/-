package com.medusa.basemall.article.dao;


import com.medusa.basemall.article.entity.LeaveWordLaud;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Created by wx on 2018/06/07.
 */
@Repository
public interface LeaveWordLaudDao extends MongoRepository<LeaveWordLaud, String> {

}
