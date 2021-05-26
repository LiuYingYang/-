package com.medusa.basemall.article.dao;

import com.medusa.basemall.article.entity.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Created by wx on 2018/06/07.
 */
@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {

    /***
     * 根据文章id查询文章
     *
     * @param articleId
     * @return Article
     */
    Article getArticleByArticleId(String articleId);

    List<Article> getArticleByArticleIdIn(String[] articleIds);

}
