package com.medusa.basemall.article.serviceImpl;

import com.medusa.basemall.article.dao.ArticleRepository;
import com.medusa.basemall.article.entity.Article;
import com.medusa.basemall.article.entity.ArticleCategory;
import com.medusa.basemall.article.entity.ArticleLaud;
import com.medusa.basemall.article.service.ArticleCategoryService;
import com.medusa.basemall.article.service.ArticleLaudService;
import com.medusa.basemall.article.service.ArticleService;
import com.medusa.basemall.utils.TimeUtil;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Created by wx on 2018/06/07.
 */
@Service
public class ArticleServiceImpl implements ArticleService {

	@Resource
	private MongoTemplate mongoTemplate;


	@Resource
	private ArticleLaudService articleLaudService;

	@Resource
	private ArticleRepository articleRepository;

	@Resource
	private ArticleCategoryService articleCategoryService;


	@Override
	public Map<String, Object> findAll(Integer pageNum, Integer pageSize, String appmodelId) {
		Query query = new Query();
		if (!"".equals(appmodelId)) {
			query.addCriteria(Criteria.where("appmodelId").in(appmodelId));
		}
		// 查询总数
		Long count = mongoTemplate.count(query, Article.class);
		query.skip((pageNum - 1) * pageSize).limit(pageSize);
		//设置起始数 和 查询条数
		query.skip((pageNum - 1) * pageSize).limit(pageSize);
		query.with(new Sort(Sort.Direction.ASC, "sort"));
		List<Article> datas = mongoTemplate.find(query, Article.class);
		Map<String, Object> map = new HashMap<>();
		map.put("list", datas);
		map.put("totle", count);
		return map;
	}

	@Override
	public List<Article> findOthersOne(String articleId, String appmodelId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("articleId").ne(articleId));
		query.addCriteria(Criteria.where("appmodelId").is(appmodelId));
		List<Article> datas = mongoTemplate.find(query, Article.class);
		Collections.sort(datas, (o1, o2) -> o2.getSort().compareTo(o1.getSort()));
		return datas;
	}

	@Override
	public List<Article> findOthersTwo(String articleId, String appmodelId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("articleId").ne(articleId));
		query.addCriteria(Criteria.where("appmodelId").is(appmodelId));
		List<Article> datas = mongoTemplate.find(query, Article.class);
		Collections.sort(datas, (o1, o2) -> o1.getSort().compareTo(o2.getSort()));
		return datas;
	}

	@Override
	public List<Article> findByAppmodelId(String appmodelId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("appmodelId").is(appmodelId));
		List<Article> datas = mongoTemplate.find(query, Article.class);
		return datas;
	}
	@Override
	public Map<String, Object> selectByCategoryId(Integer pageNum, Integer pageSize, String appmodelId, Long wxuserId,
			String categoryId) {
		List<Article> articles = new ArrayList<>(pageSize);
		Long count = 0L;
		if (categoryId == null) {
			Map<String, Object> all = findAll(pageNum, pageSize, appmodelId);
			articles = (List<Article>) all.get("list");
			count = (Long) all.get("totle");
		} else {
			Query query = new Query(Criteria.where("appmodelId").is(appmodelId).and("categoryIds").regex(categoryId));
			query.with(new Sort(Sort.Direction.ASC, "sort"));
			//设置起始数
			query.skip((pageNum - 1) * pageSize);
			//设置查询条数
			query.limit(pageSize);
			articles = mongoTemplate.find(query, Article.class);
			count = mongoTemplate.count(new Query(Criteria.where("appmodelId").is(appmodelId)), Article.class);
		}
		List<String> collect = articles.stream().map(obj -> obj.getArticleId()).collect(Collectors.toList());
		List<ArticleLaud> articleLaud = articleLaudService.getByWxuserIdAndArticleId(wxuserId, collect);
		for (Article article : articles) {
			for (ArticleLaud laud : articleLaud) {
				if (article.getArticleId().equals(laud.getArticleId())) {
					article.setLaudOrNot(laud.getLaudOrNot());
				}
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("list", articles);
		map.put("totle", count);
		return map;
	}

	@Override
	public Article addArticle(Article article) {
		article.setUpdateTime(TimeUtil.getNowTime());
		article.setLookSum(0);
		article.setLaud(0);
		article.setDiscussSum(0);
		article.setSort(1);
		ArticleCategory articleCategory = articleCategoryService.getByAppmodelId(article.getAppmodelId()).stream()
				.filter(obj -> obj.getCategoryType().equals(0)).collect(Collectors.toList()).get(0);
		article.setCategoryIds(articleCategory.getCategoryId());
		updateCategory(article);
		mongoTemplate.updateMulti(new Query(Criteria.where("appmodelId").is(article.getAppmodelId())),
				new Update().inc("sort", 1), Article.class);
		return articleRepository.save(article);
	}

	private void updateCategory(Article article) {
		if (!"".equals(article.getCategoryIds())) {
			List<ArticleCategory> articleList = articleCategoryService.getByCategoryId(article.getCategoryIds());
			String categoryNames = articleList.stream().map(obj -> obj.getCategoryName())
					.collect(Collectors.joining(","));
			article.setCategoryNames(categoryNames);
		}
		if (article.getCategoryNames() == null || !article.getCategoryNames().contains("所有")) {
			ArticleCategory articleCategory = articleCategoryService
					.findByAppmodelIdAndDeleteState(article.getAppmodelId(), 0);
			if (article.getCategoryIds().length() == 0) {
				article.setCategoryIds(articleCategory.getCategoryId());
				article.setCategoryNames(articleCategory.getCategoryName());
			} else {
				article.setCategoryIds(article.getCategoryIds() + "," + articleCategory.getCategoryId());
				article.setCategoryNames(article.getCategoryNames() + "," + articleCategory.getCategoryName());
			}

		}
	}

	@Override
	public void updateArticle(Article article) {
		Article articleUpdate = articleRepository.getArticleByArticleId(article.getArticleId());
		articleUpdate.setUpdateTime(TimeUtil.getNowTime());
		articleUpdate.setArticleTitle(article.getArticleTitle());
		articleUpdate.setCoverUrl(article.getCoverUrl());
		articleUpdate.setVideoUrl(article.getVideoUrl());
		articleUpdate.setCentent(article.getCentent());
		articleUpdate.setCategoryIds(article.getCategoryIds());
		articleUpdate.setProductId(article.getProductId());
		articleUpdate.setProductName(article.getProductName());
		updateCategory(articleUpdate);
		Article articleResult = articleRepository.save(articleUpdate);
	}

	/**
	 *
	 * @param articleIds
	 * @param appmodelId
	 * @param entirelyExcludeCategoryIds 完全排除的分类
	 * @param entirelyIncludeCategoryIds 所有的文章有要的分类
	 * @return
	 */
	@Override
	public int setCategory(String articleIds, String appmodelId, String entirelyExcludeCategoryIds,
			String entirelyIncludeCategoryIds) {

		List<Article> pitchArticle = articleRepository.getArticleByArticleIdIn(articleIds.split(","));
		if (pitchArticle == null || pitchArticle.size() == 0) {
			return 0;
		}
		List<String> allCid = new ArrayList<>();
		for (Article article : pitchArticle) {
			//完全排除的分类
			String[] ids = article.getCategoryIds().split(",");
			List<String> list = new ArrayList<>();
			for (String id : ids) {
				if (entirelyExcludeCategoryIds.contains(id)) {
					continue;
				}
				list.add(id);
			}
			if (entirelyIncludeCategoryIds.length() > 0 && list.size() > 0) {
				String[] split = entirelyIncludeCategoryIds.split(",");
				String entirelyInclude = list.toString();
				for (String cId : split) {
					//已经包含这个分类id
					if (entirelyInclude.contains(cId)) {
						continue;
					}
					list.add(cId);
				}
			}
			if (entirelyIncludeCategoryIds.length() > 0 && list.size() == 0) {
				list = Arrays.stream(entirelyIncludeCategoryIds.split(",")).collect(Collectors.toList());
			}
			if (list.size() > 0) {
				allCid.addAll(list);
				String cid = list.stream().collect(Collectors.joining(","));
				article.setCategoryIds(cid);
			}
		}
		String cids = allCid.stream().collect(Collectors.joining(","));
		List<ArticleCategory> categoryIds = articleCategoryService.getByCategoryId(cids);
		if (categoryIds != null && categoryIds.size() > 0) {
			Map<String, String> names = categoryIds.stream()
					.collect(Collectors.toMap(key -> key.getCategoryId(), value -> value.getCategoryName()));
			for (Article article : pitchArticle) {
				StringBuilder builder = new StringBuilder();
				String[] cIds = article.getCategoryIds().split(",");
				for (String cid : cIds) {
					if (names.get(cid) != null) {
						builder.append(names.get(cid) + ",");
					}
				}
				if (builder.length() > 0) {
					article.setCategoryNames(builder.substring(0, builder.length() - 1));
				} else {
					article.setCategoryNames(builder.toString());
				}
			}
		}
		articleRepository.saveAll(pitchArticle);
		return 1;
	}

	@Override
	public void updateArticles(List<Article> articles) {
		articleRepository.saveAll(articles);
	}

	@Override
	public void cleanProduct(List<Long> productIds) {
		Query query = new Query();
		query.addCriteria(Criteria.where("productId").in(productIds));
		mongoTemplate.updateMulti(query, Update.update("productId", "").set("productName", ""), Article.class);
	}

}
