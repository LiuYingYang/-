package com.medusa.basemall.product.service;

import com.medusa.basemall.core.Service;
import com.medusa.basemall.product.entity.Category;
import com.medusa.basemall.product.vo.CategoryAndProductVo;
import com.medusa.basemall.product.vo.CategoryProductVo;
import com.medusa.basemall.product.vo.CategoryVo;

import java.util.List;

/**
 * Created by psy on 2018/05/24.
 */
public interface CategoryService extends Service<Category> {

    int saveOrUpdate(List<CategoryVo> categoryVoList, List<Long> categoryIds, String appmodelId);

    List<CategoryVo> findFirstAndSecond(String appmodelId);

    List<CategoryAndProductVo> findCatogoryAndProduct(String appmodelId);

	List<CategoryProductVo> findByFirstCategoryIds(List<Long> categoryIds);

	int maxCategoryType(String appmodelId);
}
