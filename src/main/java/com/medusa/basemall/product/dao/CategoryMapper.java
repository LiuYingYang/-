package com.medusa.basemall.product.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.product.entity.Category;
import com.medusa.basemall.product.vo.CategoryProductVo;
import com.medusa.basemall.product.vo.CategoryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper extends Mapper<Category> {

    List<CategoryVo> findFirstCategory(String appmodelId);

    List<CategoryProductVo> findByFirstCategoryIds(@Param("categoryIds") List<Long> categoryIds);

    List<Category> selectByFatherId(Long fatherId);

    List<CategoryProductVo> selectByFatherIdSimple(Long fatherId);

    List<CategoryVo> selectFirstCategory(String appmodelId);

	int maxCategoryType(String appmodelId);
}