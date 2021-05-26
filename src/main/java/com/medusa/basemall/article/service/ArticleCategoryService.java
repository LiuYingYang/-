package com.medusa.basemall.article.service;


import com.medusa.basemall.article.entity.ArticleCategory;

import java.util.List;

/**
 * @author Created by wx on 2018/06/07.
 */
public interface ArticleCategoryService {

    /***
     * 根据appmodelId查询文章分类
     *
     * @param appmodelId
     * @return List<ArticleCategory>
     */
    List<ArticleCategory> getByAppmodelId(String appmodelId);

	List<ArticleCategory> getByCategoryId(String categoryIds);

	ArticleCategory findByAppmodelIdAndDeleteState(String appmodelId,Integer deleteState);

	ArticleCategory deleteCategory(String categoryId, String appmodelId);

	ArticleCategory updateCategory(ArticleCategory articleCategory);

}
