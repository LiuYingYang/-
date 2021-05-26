package com.medusa.basemall.article.controller;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.article.dao.ArticleCategoryRepository;
import com.medusa.basemall.article.dao.ArticleRepository;
import com.medusa.basemall.article.entity.Article;
import com.medusa.basemall.article.entity.ArticleCategory;
import com.medusa.basemall.article.service.ArticleCategoryService;
import com.medusa.basemall.article.service.ArticleService;
import com.medusa.basemall.article.vo.SortVo;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import com.vip.vjtools.vjkit.mapper.BeanMapper;
import com.vip.vjtools.vjkit.number.RandomUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional
public class ArticleControllerTest extends BasemallApplicationTests {


	@Autowired
	private ArticleCategoryRepository articleCategoryRepository;
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private ArticleService articleService;

	private List<Article> createArticle(int num){
		List<Article> articles = new ArrayList<>();
		for (int i = 0; i < num; i++) {
			Article article = new Article();
			article.setArticleTitle(RandomUtil.randomAsciiFixLength(5));
			article.setCentent(RandomUtil.randomAsciiFixLength(255));
			int i1 = RandomUtil.nextInt(1, 2);
			if(i1 == 1){
				List<ArticleCategory> category = getCategory(RandomUtil.nextInt(1, 3));
				String categoryIds = category.stream().map(obj -> obj.getCategoryId()).collect(Collectors.joining(","));
				article.setCategoryIds(categoryIds);
			}else{
				article.setCategoryIds("");
			}
			article.setAppmodelId(Constant.appmodelIdy);

			articles.add(article);
		}
		return articles;
	}

	private List<Article> getArticles(Integer num){
		List<Article> byAppmodelId = articleService.findByAppmodelId(Constant.appmodelIdy);
		if (num == null){
			return byAppmodelId;
		}
		return byAppmodelId.stream().limit(num).collect(Collectors.toList());
	}

	/**
	 * 添加文章测试
	 */
	@Test
	public void addTest() {
		List<Article> articles = createArticle(1);
		Article article = articles.get(0);
		MockMvcUtil.sendRequest("/article/add/v1", JSONObject.toJSONString(article), null, "post");
		Article article2 = articleRepository.findOne(Example.of(article)).get();
		Assert.assertNotNull(article2);

	}

	/**
	 * 编辑文章测试
	 */
	@Test
	public void updateTest() {
		Article article = getArticles(1).get(0);
		String articleId = article.getArticleId();
		Article newA = BeanMapper.map(createArticle(1).get(0), Article.class);
		newA.setArticleId(articleId);
		MockMvcUtil.sendRequest("/article/update/v1", JSONObject.toJSONString(newA), null, "put");
		Article article1 = articleRepository.findById(newA.getArticleId()).get();
		Assert.assertNotNull(article1);
		Assert.assertEquals(newA.getCentent(),article1.getCentent());
		Assert.assertNotEquals(newA.getCategoryIds(),article1.getCategoryIds());
	}


	/**
	 * 批量删除文章
	 */
	@Test
	public void deleteTest() {
		Map<String, Object> object = new HashMap<>();
		object.put("articleIds", "5b3f0c00de70293e145c417b");
		JSONObject post = MockMvcUtil.sendRequest("/article/delete", JSONObject.toJSONString(object), null, "post");
	}

	/**
	 * 批量设置分类测试
	 */
	@Test
	public void setCategory() {
		List<Article> articles = articleService.findByAppmodelId(Constant.appmodelIdy);
		Stream<Article> limit = articles.stream().limit(2);
		Map<String, Object> object = new HashMap<>();
		object.put("articleIds", "5b3f120ede70293b5c6a8cbf,5b3f124bde7029408c636f66");
		object.put("categoryIds", "5b3f0b6ede7029321cf686bf,5b3f0076de702936f42baea3");
		JSONObject post = MockMvcUtil
				.sendRequest("/article/setCategory", JSONObject.toJSONString(object), null, "post");
	}

	/**
	 * 文章分页查询测试
	 */
	@Test
	public void selectTest() {
		Map<String, Object> object = new HashMap<>();
		object.put("pageNum", 2);
		object.put("pageSize", 2);
		object.put("appmodelId", Constant.appmodelIdy);
		JSONObject post = MockMvcUtil.sendRequest("/article/select", JSONObject.toJSONString(object), null, "post");
	}

	/**
	 * *文章排序测试
	 */
	@Test
	public void sortTest() {
		SortVo sortVo = new SortVo();
		sortVo.setHandleType(1);
		sortVo.setAppmodelId(Constant.appmodelIdy);
		sortVo.setArticleId("5b85033ae4606017221b8602");
		JSONObject post = MockMvcUtil.sendRequest("/article/sort/v1", JSONObject.toJSONString(sortVo), null, "put");
	}


	/**
	 * 根据Id查询文章测试
	 */
	@Test
	public void selectByIdTest() {
		Map<String, Object> object = new HashMap<>();
		object.put("articleId", "5b404a77de70293a94963381");
		object.put("wxuserId", 1528246692051902L);
		JSONObject post = MockMvcUtil.sendRequest("/article/selectById", JSONObject.toJSONString(object), null, "post");
	}

	/**
	 * 根据分类查文章测试
	 */
	@Test
	public void selectByCategoryIdTest() {
		Map<String, Object> object = new HashMap<>();
		object.put("appmodelId", Constant.appmodelIdy);
		object.put("wxuserId", 1528246692051902L);
		/*object.put("categoryId","5b3f0b6ede7029321cf686bf");*/
		JSONObject post = MockMvcUtil
				.sendRequest("/article/selectByCategoryId", JSONObject.toJSONString(object), null, "post");
	}

	/**
	 * 用户点赞/取消点赞文章
	 */
	@Test
	public void updateArticleLaudTest() {
		Map<String, Object> object = new HashMap<>();
		object.put("articleId", "5b404a77de70293a94963381");
		object.put("wxuserId", 1528246692051902L);
		JSONObject post = MockMvcUtil
				.sendRequest("/article/updateArticleLaud", JSONObject.toJSONString(object), null, "post");
	}


	/***************分类测试*********/

	@Autowired
	private ArticleCategoryService articleCategoryService;
	@Autowired
	private MongoTemplate mongoTemplate;

	private List<ArticleCategory> createCategory(int num) {
		List<ArticleCategory> categories = new ArrayList<>(num);
		for (int i = 0; i < num; i++) {
			ArticleCategory articleCategory = new ArticleCategory();
			articleCategory.setCategoryName(RandomUtil.randomAsciiFixLength(5));
			articleCategory.setCategoryImg(RandomUtil.randomAsciiFixLength(20));
			articleCategory.setAppmodelId(Constant.appmodelIdy);
			MockMvcUtil
					.sendRequest("/information/addCategory/v1", JSONObject.toJSONString(articleCategory), null, "post");
			categories.add(articleCategory);
		}
		return categories;
	}

	private List<ArticleCategory> getCategory(Integer num) {
		List<ArticleCategory> byAppmodelId = articleCategoryService.getByAppmodelId(Constant.appmodelIdy);
		if(num == null){
			return byAppmodelId;
		}
		return byAppmodelId.stream().limit(num).collect(Collectors.toList());
	}

	private void deleteArticleCategory(String categoryId, String appmodelId) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("categoryId",categoryId);
		params.add("appmodelId",appmodelId);
		JSONObject post = MockMvcUtil
				.sendRequest("/information/deleteCategory/v1", "", params, "delete");
	}

	/**
	 * 添加分类测试
	 */
	@Test
	public void addCategoryTest() {
		List<ArticleCategory> category = createCategory(1);
		List<ArticleCategory> byAppmodelId = getCategory(null);
		boolean flag = false;
		for (ArticleCategory articleCategory : byAppmodelId) {
			if (articleCategory.getCategoryName().equals(category.get(0).getCategoryName()) && articleCategory
					.getCategoryImg().equals(category.get(0).getCategoryImg())) {
				flag = true;
				deleteArticleCategory(articleCategory.getCategoryId(),articleCategory.getAppmodelId());
			}
		}
		Assert.assertEquals(true, flag);
	}
	/**
	 * 删除分类测试
	 */
	@Test
	public void deleteCategoryTest() {
		List<ArticleCategory> byAppmodelId = getCategory(1);
		deleteArticleCategory(byAppmodelId.get(0).getCategoryId(),byAppmodelId.get(0).getAppmodelId());
		List<ArticleCategory> byCategoryId = articleCategoryService
				.getByCategoryId(byAppmodelId.get(0).getCategoryId());
		Assert.assertEquals(new Integer(1),byCategoryId.get(0).getDeleteState());
		ArticleCategory articleCategory = byCategoryId.get(0);
		articleCategory.setDeleteState(0);
		articleCategoryService.updateCategory(articleCategory);
	}

	/**
	 * 更新分类名字测试
	 */
	@Test
	public void updateCategoryTest(){
		List<ArticleCategory> category = getCategory(1);
		ArticleCategory articleCategory = category.get(0);
		//旧的名称
		String categoryName = articleCategory.getCategoryName();
		//设置新的名称
		articleCategory.setCategoryName(RandomUtil.randomAsciiFixLength(7));
		MockMvcUtil.sendRequest("/information/updateCategory/v1",
				JSONObject.toJSONString(articleCategory), null, "put");
		//文章中分类名都修改为最新的分类名,查不到久的
		List<Article> articles = mongoTemplate
				.find(new Query(Criteria.where("categoryNames").regex(categoryName).and("appmodelId").is(articleCategory.getAppmodelId())),
						Article.class);
		Assert.assertEquals(0,articles.size());
		ArticleCategory articleCategory1 = articleCategoryService.getByCategoryId(articleCategory.getCategoryId())
				.get(0);
		//与修改的名称对比是否修改成功
		Assert.assertEquals(articleCategory.getCategoryName(),articleCategory1.getCategoryName());
		articleCategory1.setCategoryName(categoryName);
		articleCategoryService.updateCategory(articleCategory1);
	}


	/**
	 * 获取分类测试
	 */
	@Test
	public void selectCategoryTest() {
		MultiValueMap<String, String> params  = new LinkedMultiValueMap<>();
		params.add("appmodelId",Constant.appmodelIdy);
		JSONObject post = MockMvcUtil
				.sendRequest("/information//selectCategory/v1", "", params, "get");
		String data = post.getString("data");
		Assert.assertNotNull(data);
	}
}
