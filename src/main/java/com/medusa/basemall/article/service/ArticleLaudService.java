package com.medusa.basemall.article.service;

import com.medusa.basemall.article.entity.Article;
import com.medusa.basemall.article.entity.ArticleLaud;

import java.util.List;

/**
 * @author Created by wx on 2018/06/07.
 */
public interface ArticleLaudService {

    /***
     * 根据用户id和文章id查询文章是否点赞
     *
     * @param wxuserId
     * @param articleId
     * @return List<ArticleCategory>
     */
    List<ArticleLaud> getByWxuserIdAndArticleId(Long wxuserId, List<String> articleId);

	Article getByArticleId(String articleId);
}
