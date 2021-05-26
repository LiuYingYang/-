package com.medusa.basemall.article.service;

import com.medusa.basemall.article.entity.Article;

import java.util.List;
import java.util.Map;

/**
 * @author Created by wx on 2018/06/07.
 */
public interface ArticleService {

	/***
	 * 分页查询店铺所有文章
	 *
	 * @param appmodelId
	 * @return MongoPageModel<Article>
	 */
	Map<String, Object> findAll(Integer pageNum, Integer pageSize, String appmodelId);

	/***
	 * 根据appmodelId查询除自身外的所有文章，根据sort值倒序排序
	 *
	 * @param articleId
	 * @param appmodelId
	 * @return List<Article>
	 */
	List<Article> findOthersOne(String articleId, String appmodelId);

	/***
	 * 根据appmodelId查询除自身外的所有文章，根据sort值正序排序
	 *
	 * @param articleId
	 * @param appmodelId
	 * @return List<Article>
	 */
	List<Article> findOthersTwo(String articleId, String appmodelId);

	/***
	 * 根据appmodelId查询店铺所有文章
	 *
	 * @param appmodelId
	 * @return List<Article>
	 */
	List<Article> findByAppmodelId(String appmodelId);

	/***
	 * 根据categoryId查询分页查询文章
	 * @param appmodelId
	 * @param categoryId
	 * @return MongoPageModel<Article>
	 */
	Map<String, Object> selectByCategoryId(Integer pageNum, Integer pageSize, String appmodelId, Long wxuserId,
			String categoryId);

	Article addArticle(Article article);

	void updateArticle(Article article);

	int setCategory(String articleIds, String appmodelId, String entirelyExcludeCategoryIds,
			String entirelyIncludeCategoryIds);

	void updateArticles(List<Article> articles);

	void cleanProduct(List<Long> productIds);
}
