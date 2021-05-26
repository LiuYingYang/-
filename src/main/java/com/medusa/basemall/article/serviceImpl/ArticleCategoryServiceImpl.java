package com.medusa.basemall.article.serviceImpl;


import com.medusa.basemall.article.dao.ArticleCategoryRepository;
import com.medusa.basemall.article.entity.Article;
import com.medusa.basemall.article.entity.ArticleCategory;
import com.medusa.basemall.article.service.ArticleCategoryService;
import com.medusa.basemall.article.service.ArticleService;
import com.vip.vjtools.vjkit.time.ClockUtil;
import com.vip.vjtools.vjkit.time.DateFormatUtil;
import org.springframework.beans.BeanUtils;
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
public class ArticleCategoryServiceImpl implements ArticleCategoryService {

	@Resource
	private ArticleCategoryRepository articleCategoryRepository;
	@Resource
	private MongoTemplate mongoTemplate;

	@Resource
	private ArticleService articleService;

	@Override
	public List<ArticleCategory> getByAppmodelId(String appmodelId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("deleteState").is(0));
		query.addCriteria(Criteria.where("appmodelId").is(appmodelId));
		return mongoTemplate.find(query, ArticleCategory.class);
	}

	@Override
	public List<ArticleCategory> getByCategoryId(String categoryIds) {
		List<ArticleCategory> categories = mongoTemplate
				.find(new Query(Criteria.where("categoryId").in(categoryIds.split(","))), ArticleCategory.class);
		return categories;
	}

	@Override
	public ArticleCategory findByAppmodelIdAndDeleteState(String appmodelId, Integer deleteState) {
		ArticleCategory articleCategory = articleCategoryRepository
				.findByAppmodelIdIsAndCategoryTypeIs(appmodelId, deleteState);
		return articleCategory;
	}

	@Override
	public ArticleCategory deleteCategory(String categoryId, String appmodelId) {
		ArticleCategory articleCategoryDelete = articleCategoryRepository.findById(categoryId).get();
		articleCategoryDelete.setDeleteState(1);
		List<Article> articles = mongoTemplate
				.find(new Query(Criteria.where("categoryIds").regex(categoryId).and("appmodelId").is(appmodelId)),
						Article.class);
		for (Article article : articles) {
			String[] split = article.getCategoryIds().split(",");
			StringBuilder stringBuilder = new StringBuilder();
			for (String s : split) {
				if (!s.equals(categoryId)) {
					stringBuilder.append(s + ",");
				}
			}
			if (stringBuilder.length() > 0) {
				article.setCategoryIds(stringBuilder.substring(0, stringBuilder.length() - 1));
				String[] split1 = article.getCategoryNames().split(",");
				StringBuilder names = new StringBuilder();
				for (String s : split1) {
					if (!s.equals(articleCategoryDelete.getCategoryName())) {
						names.append(s);
					}
				}
				if (names.length() > 0) {
					article.setCategoryNames(names.substring(0,names.length()-1));
				}else{
					article.setCategoryNames("");
				}
			} else {
				article.setCategoryIds(stringBuilder.toString());
				article.setCategoryNames("");
			}
			articleService.updateArticle(article);
		}
		ArticleCategory result = articleCategoryRepository.save(articleCategoryDelete);
		return result;
	}

	@Override
	public ArticleCategory updateCategory(ArticleCategory newArticleCategory) {
		ArticleCategory oldArticleCategory = articleCategoryRepository.findById(newArticleCategory.getCategoryId()).get();
		List<Article> articles = mongoTemplate
				.find(new Query(Criteria.where("categoryNames").regex(oldArticleCategory.getCategoryName()).and("appmodelId").is(newArticleCategory.getAppmodelId())),
						Article.class);
		String newTime = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.format(ClockUtil.currentDate());
		for (Article article : articles) {
			article.getCategoryNames().replace(oldArticleCategory.getCategoryName(),newArticleCategory.getCategoryName());
			article.setUpdateTime(newTime);
		}
		articleService.updateArticles(articles);
		oldArticleCategory.setCategoryName(newArticleCategory.getCategoryName());
		BeanUtils.copyProperties(newArticleCategory,oldArticleCategory);
		return articleCategoryRepository.save(oldArticleCategory);

	}

}

