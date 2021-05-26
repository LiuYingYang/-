package com.medusa.basemall.article.serviceimpl;


import com.medusa.basemall.article.dao.ArticleRepository;
import com.medusa.basemall.article.entity.Article;
import com.medusa.basemall.article.entity.LeaveWord;
import com.medusa.basemall.article.service.LeaveWordService;
import com.medusa.basemall.utils.MongoPageModel;
import com.mongodb.DBObject;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author Created by wx on 2018/06/07.
 */
@Service
public class LeaveWordServiceImpl implements LeaveWordService {
    @Resource
    MongoTemplate mongoTemplate;

    @Resource
    ArticleRepository articleRepository;
    @Override
    public List<LeaveWord> findAll(String articleId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("articleId").is(articleId));
        query.with(new Sort(Sort.Direction.DESC, "sortTime"));
        query.with(new Sort(Sort.Direction.DESC, "leaveTime"));
        List<LeaveWord> datas = mongoTemplate.find(query, LeaveWord.class);
        return datas;
    }

    @Override
    public List<LeaveWord> findByArticleId(String articleId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("choiceness").is(1));
        query.addCriteria(Criteria.where("articleId").is(articleId));
        List<LeaveWord> leaveWords = mongoTemplate.find(query, LeaveWord.class);
        Collections.sort(leaveWords,(o1, o2)->o2.getSortTime().compareTo(o1.getSortTime()));
        return leaveWords;
    }

    @Override
    public MongoPageModel<LeaveWord> findByWxuserIdAndArticleId(MongoPageModel<LeaveWord> page, DBObject basicDBObject, Long wxuserId, String articleId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("wxuserId").is(wxuserId));
        query.addCriteria(Criteria.where("articleId").is(articleId));
        int count = (int) mongoTemplate.count(query, LeaveWord.class);
        page.setRowCount(count);
        query.skip(page.getSkip()).limit(page.getPageSize());
        query.with(new Sort(Sort.Direction.DESC, "leaveWordId"));
        List<LeaveWord> leaveWords = mongoTemplate.find(query, LeaveWord.class);
        page.setDatas(leaveWords);
        return page;
    }

    @Override
    public MongoPageModel<LeaveWord> getByChoicenessType(MongoPageModel<LeaveWord> page, DBObject basicDBObject, String articleId) {
        Query query = new BasicQuery(String.valueOf(basicDBObject));
        query.addCriteria(Criteria.where("articleId").is(articleId));
        query.addCriteria(Criteria.where("choiceness").is(1));
        int count = (int) mongoTemplate.count(query, LeaveWord.class);
        page.setRowCount(count);
        query.skip(page.getSkip()).limit(page.getPageSize());
        query.with(new Sort(Sort.Direction.DESC, "sortTime"));
        List<LeaveWord> datas = mongoTemplate.find(query, LeaveWord.class);
        page.setDatas(datas);
        return page;
}

    @Override
    public List<LeaveWord> findLeaveWord(String findWord, String appmodelId) {
        Criteria criterias = new Criteria();
        criterias.orOperator(Criteria.where("wxuserName").regex(".*?" + findWord + ".*"),
                Criteria.where("leaveInfo").regex(".*?" + findWord + ".*"));
        Query query = new Query(criterias);
        query.addCriteria(Criteria.where("appmodelId").is(appmodelId));
        query.with(new Sort(Sort.Direction.DESC, "leaveTime"));
        List<LeaveWord> leaveWords = mongoTemplate.find(query, LeaveWord.class);
        for (LeaveWord leaveWord : leaveWords) {
            Article article = articleRepository.getArticleByArticleId(leaveWord.getArticleId());
            leaveWord.setArticle(article);
        }
        return leaveWords;
    }
}
