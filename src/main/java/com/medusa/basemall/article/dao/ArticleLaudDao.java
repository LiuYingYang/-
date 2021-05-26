package com.medusa.basemall.article.dao;

import com.medusa.basemall.article.entity.ArticleLaud;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Created by wx on 2018/06/07.
 */
@Repository
public interface ArticleLaudDao extends MongoRepository<ArticleLaud, String> {

}
