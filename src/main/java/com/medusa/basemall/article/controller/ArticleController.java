package com.medusa.basemall.article.controller;

import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.article.dao.ArticleCategoryRepository;
import com.medusa.basemall.article.dao.ArticleLaudDao;
import com.medusa.basemall.article.dao.ArticleRepository;
import com.medusa.basemall.article.dao.LeaveWordDao;
import com.medusa.basemall.article.entity.Article;
import com.medusa.basemall.article.entity.ArticleLaud;
import com.medusa.basemall.article.service.ArticleLaudService;
import com.medusa.basemall.article.service.ArticleService;
import com.medusa.basemall.article.vo.*;
import com.medusa.basemall.constant.HandType;
import com.medusa.basemall.constant.VersionNumber;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.shop.dao.FirstpageClassifyMapper;
import com.medusa.basemall.shop.dao.PosterMapper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 文章
 *
 * @author Created by wx on 2018/06/07.
 */
@Api(tags = "所有接口")
@RestController
@RequestMapping("/article")
@VersionManager(versoinNumber = VersionNumber.MARKETINGVERSION)
public class ArticleController {

	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleLaudDao articleLaudDao;
	@Autowired
	private ArticleCategoryRepository articleCategoryRepository;
	@Autowired
	private ArticleLaudService articleLaudService;
	@Autowired
	private LeaveWordDao leaveWordDao;
	@Resource
	private PosterMapper posterMapper;
	@Resource
	private FirstpageClassifyMapper firstpageClassifyMapper;


	@ApiOperation(value = "文章添加", tags = "添加接口")
	@PostMapping("/v1/add")
	public Result add(@RequestBody Article article) {
		Article newArticle = articleService.addArticle(article);
		if (newArticle.getArticleId() != null) {
			return ResultGenerator.genSuccessResult();
		} else {
			return ResultGenerator.genFailResult("添加失败");
		}
	}

	@ApiOperation(value = "文章编辑更新", tags = "更新接口")
	@PutMapping("/v1/update")
	public Result update(@RequestBody Article article) {
		articleService.updateArticle(article);

		return ResultGenerator.genSuccessResult();
	}


	@ApiOperation(value = "批量设置分类", tags = "更新接口")
	@PutMapping("/v1/setCategory")
	public Result setCategory(@RequestBody SetCategoryVo setCategoryVo) {
		articleService.setCategory(setCategoryVo.getArticleIds(), setCategoryVo.getAppmodelId(),
				setCategoryVo.getEntirelyExcludeCategoryIds(), setCategoryVo.getEntirelyIncludeCategoryIds());
		return ResultGenerator.genSuccessResult();
	}


	@ApiOperation(value = "批量删除文章", tags = "删除接口")
	@DeleteMapping("/v1/delete")
	public Result delete(@ApiParam(value = "文章id字符串") @RequestParam String articleIds) {
		String[] articleId = articleIds.split(",");
		for (String articleid : articleId) {
			Article article = articleRepository.getArticleByArticleId(articleid);
			leaveWordDao.deleteByArticleId(articleid);
			articleRepository.delete(article);

			HashMap<String, Object> map = new HashMap<>();
			map.put("articleId", articleid);
			posterMapper.updatePoster(map);
			firstpageClassifyMapper.updateFirstpage(map);
		}
		return ResultGenerator.genSuccessResult();
	}


	@ApiOperation(value = "文章分页查询", tags = "查询接口")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success", response = Article.class, responseContainer = "List"),})
	@GetMapping("/v1/select")
	public Result selete(ArticleVo articleVo) {
		Map<String, Object> all = articleService
				.findAll(articleVo.getPageNum(), articleVo.getPageSize(), articleVo.getAppmodelId());
		return ResultGenerator.genSuccessResult(all);
	}

	@ApiOperation(value = "文章排序", tags = "更新接口")
	@PutMapping("/v1/sort")
	public Result sort(@RequestBody SortVo sortVo) {
		String articleId = sortVo.getArticleId();
		String appmodelId = sortVo.getAppmodelId();
		if (sortVo.getHandleType().equals(HandType.TOP)) {
			Article article = articleRepository.getArticleByArticleId(articleId);
			Integer sort = article.getSort();
			List<Article> articles = articleService.findOthersOne(articleId, appmodelId);
			if (articles.size() > 0) {
				for (Article articleNew : articles) {
					if (article.getSort() > articleNew.getSort()) {
						change(article, articleNew);
					}
				}
				return ResultGenerator.genSuccessResult("置顶成功");
			}
		}
		if (sortVo.getHandleType().equals(HandType.FOOT)) {
			Article article = articleRepository.getArticleByArticleId(articleId);
			Integer sort = article.getSort();
			List<Article> articles = articleService.findOthersTwo(articleId, appmodelId);
			if (articles.size() > 0) {
				for (Article articleNew : articles) {
					if (articleNew.getSort() > article.getSort()) {
						change(article, articleNew);
					}
				}
				return ResultGenerator.genSuccessResult("置底成功");
			}
		}
		if (sortVo.getHandleType().equals(HandType.UP)) {
			Article article = articleRepository.getArticleByArticleId(articleId);
			Integer sort = article.getSort();
			List<Article> articles = articleService.findOthersOne(articleId, appmodelId);
			if (articles.size() > 0) {
				for (Article articleNew : articles) {
					if (article.getSort() > articleNew.getSort()) {
						change(article, articleNew);
						return ResultGenerator.genSuccessResult("上移成功");
					}
				}
			}
		}
		if (sortVo.getHandleType().equals(HandType.DOWN)) {
			Article article = articleRepository.getArticleByArticleId(articleId);
			Integer sort = article.getSort();
			List<Article> articles = articleService.findOthersTwo(articleId, appmodelId);
			if (articles.size() > 0) {
				for (Article articleNew : articles) {
					if (articleNew.getSort() > article.getSort()) {
						change(article, articleNew);
						return ResultGenerator.genSuccessResult("下移成功");
					}
				}
			}
		}
		return ResultGenerator.genSuccessResult("操作没有生效");
	}

	@ApiOperation(value = "根据Id查询文章", tags = "查询接口")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success", response = Article.class, responseContainer = "Article"),})
	@GetMapping("/v1/selectById")
	public Result selectById(ArticleLoudVo articleLoudVo) {
		Long wxuserId = articleLoudVo.getWxuserId();
		String articleId = articleLoudVo.getArticleId();
		Article article = articleRepository.getArticleByArticleId(articleId);
		// 增加浏览量
		article.setLookSum(article.getLookSum() + 1);
		Article articleResult = articleRepository.save(article);
		List<String> articleIds = new ArrayList<>();
		articleIds.add(articleId);
		if (articleLaudService.getByWxuserIdAndArticleId(wxuserId, articleIds) != null
				&& articleLaudService.getByWxuserIdAndArticleId(wxuserId, articleIds).size() > 0) {
			ArticleLaud articleLaud = articleLaudService.getByWxuserIdAndArticleId(wxuserId, articleIds).get(0);
			if (articleLaud == null) {
				article.setLaudOrNot(false);
			} else {
				article.setLaudOrNot(articleLaud.getLaudOrNot());
			}
		}
		if (articleResult != null) {
			return ResultGenerator.genSuccessResult(article);
		} else {
			return ResultGenerator.genFailResult("查询失败");
		}
	}


	@ApiOperation(value = "文章点赞", tags = "更新接口")
	@PutMapping("/v1/updateArticleLaud")
	public Result updateArticleLaud(@RequestBody ArticleLoudVo articleLoudVo) {

		Long wxuserId = articleLoudVo.getWxuserId();
		String articleId = articleLoudVo.getArticleId();
		Article article = articleRepository.getArticleByArticleId(articleId);
		List<String> articleIds = new ArrayList<>();
		articleIds.add(articleId);
		if (articleLaudService.getByWxuserIdAndArticleId(wxuserId, articleIds) != null
				&& articleLaudService.getByWxuserIdAndArticleId(wxuserId, articleIds).size() > 0) {
			ArticleLaud articleLaud = articleLaudService.getByWxuserIdAndArticleId(wxuserId, articleIds).get(0);
			if (articleLaud.getLaudOrNot() == false) {
				articleLaud.setLaudOrNot(true);
				articleLaudDao.save(articleLaud);
				article.setLaud(article.getLaud() + 1);
				article = articleRepository.save(article);
			} else {
				articleLaud.setLaudOrNot(false);
				articleLaudDao.save(articleLaud);
				article.setLaud(article.getLaud() - 1);
				article = articleRepository.save(article);
			}
		} else {
			ArticleLaud articleLaudNew = new ArticleLaud();
			articleLaudNew.setWxuserId(wxuserId);
			articleLaudNew.setArticleId(articleId);
			articleLaudNew.setLaudOrNot(true);
			articleLaudNew.setAppmodelId(articleLoudVo.getAppmodelId());
			articleLaudDao.save(articleLaudNew);
			article.setLaud(article.getLaud() + 1);
			article = articleRepository.save(article);
		}

		Map<String, Object> map = new HashMap<>();
		List<String> list = new ArrayList<>();
		list.add(articleId);
		map.put("laudOrNot", articleLaudService.getByWxuserIdAndArticleId(wxuserId, list).get(0).getLaudOrNot());
		map.put("laud", article.getLaud());
		return ResultGenerator.genSuccessResult(map);
	}


	@ApiOperation(value = "根据分类查文章", tags = "查询接口")
	@GetMapping("/v1/selectByCategoryId")
	public Result selectByCategoryId(SelectByCategoryIdVo selectByCategoryIdVo) {
		Map<String, Object> map = articleService
				.selectByCategoryId(selectByCategoryIdVo.getPageNum(), selectByCategoryIdVo.getPageSize(),
						selectByCategoryIdVo.getAppmodelId(), selectByCategoryIdVo.getWxuserId(),
						selectByCategoryIdVo.getCategoryId());
		return ResultGenerator.genSuccessResult(map);
	}

	private void change(Article article, Article articleNew) {
		Integer sortNew = articleNew.getSort();
		articleNew.setSort(article.getSort());
		articleRepository.save(articleNew);
		article.setSort(sortNew);
		articleRepository.save(article);
	}
}
