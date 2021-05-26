package com.medusa.basemall.article.serviceimpl;


import com.medusa.basemall.article.entity.Article;
import com.medusa.basemall.article.entity.ArticleLaud;
import com.medusa.basemall.article.service.ArticleLaudService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Created by wx on 2018/06/07.
 */
@Service
public class ArticleLaudServiceImpl implements ArticleLaudService {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public List<ArticleLaud> getByWxuserIdAndArticleId(Long wxuserId, List<String> articleId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("wxuserId").is(wxuserId).and("articleId").in(articleId));
        List<ArticleLaud> articleLaud = mongoTemplate.find(query, ArticleLaud.class);
        return articleLaud;
    }

	@Override
	public Article getByArticleId(String articleId) {
		return null;
	}
}
