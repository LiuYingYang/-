package com.medusa.basemall.article.controller;

import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.article.dao.ArticleCategoryRepository;
import com.medusa.basemall.article.dao.ArticleRepository;
import com.medusa.basemall.article.entity.ArticleCategory;
import com.medusa.basemall.article.service.ArticleCategoryService;
import com.medusa.basemall.article.service.ArticleService;
import com.medusa.basemall.constant.VersionNumber;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文章分类
 *
 * @author Created by wx on 2018/06/07.
 */
@Api(tags = "所有接口")
@RestController
@RequestMapping("/information")
@VersionManager(versoinNumber = VersionNumber.MARKETINGVERSION)
public class ArticleCategoryController {
    @Autowired
    private ArticleCategoryRepository articleCategoryRepository;
    @Autowired
    private ArticleCategoryService articleCategoryService;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "添加分类", tags = "添加接口")
    @PostMapping("/v1/addCategory")
    public Result addCategory(@RequestBody ArticleCategory articleCategory){
        articleCategory.setDeleteState(0);
        articleCategory.setCategoryType(1);
        ArticleCategory result = articleCategoryRepository.save(articleCategory);
        if (result != null){
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("保存失败");
        }
    }

    @ApiOperation(value = "更新分类", tags = "更新接口")
    @PutMapping("/v1/updateCategory")
    public Result updateCategory(@RequestBody ArticleCategory articleCategory){
	    ArticleCategory newArticleCategory = articleCategoryService.updateCategory(articleCategory);
	    if (newArticleCategory != null){
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("保存失败");
        }
    }

    @ApiOperation(value = "删除分类", tags = "删除接口")
    @DeleteMapping("/v1/deleteCategory")
    public Result deleteCategory(@ApiParam(value = "分类id") @RequestParam String categoryId,
                                 @ApiParam(value = "模板id") @RequestParam String appmodelId){
	    ArticleCategory articleCategory = articleCategoryService.deleteCategory(categoryId, appmodelId);
	    if(articleCategory != null && articleCategory.getDeleteState().equals(1)){
	        return ResultGenerator.genSuccessResult();
	    }
	    return  ResultGenerator.genFailResult("删除失败");
    }


    @ApiOperation(value = "获取分类", tags = "查询接口")
    @ApiResponses(
            {@ApiResponse(code = 100, message = "success", response = ArticleCategory.class, responseContainer = "List"),})
    @GetMapping("/v1/selectCategory")
    public Result selectCategory(@ApiParam(value = "模板id") @RequestParam String appmodelId){
        List<ArticleCategory> articleCategories = articleCategoryService.getByAppmodelId(appmodelId);
        if (articleCategories.size() > 0){
            return ResultGenerator.genSuccessResult(articleCategories);
        } else {
            ArticleCategory articleCategoryNew = new ArticleCategory();
            articleCategoryNew.setCategoryName("所有");
            articleCategoryNew.setCategoryType(0);
            articleCategoryNew.setDeleteState(0);
            articleCategoryNew.setAppmodelId(appmodelId);
            articleCategoryRepository.save(articleCategoryNew);
            return ResultGenerator.genSuccessResult(articleCategoryService.getByAppmodelId(appmodelId));
        }
    }
}
