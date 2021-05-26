package com.medusa.basemall.product.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.product.entity.ProductCategory;
import com.medusa.basemall.product.vo.CategoryProductVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryMapper extends Mapper<ProductCategory> {
    int deleteByCategoryId(@Param("categoryIds") List<Long> categoryIds);

    int deleteByProductIds(@Param("productIds") List<Long> productIds);

    List<ProductCategory> selectByCategoryId(Long categoryId);

	List<CategoryProductVo> selectByProdctParentCategoryId(@Param("productIds") List<Long> productIds,
			@Param("appmodelId") String appmodelId, @Param("categoryType") int categoryType, @Param("fatherId") Long fatherId);
}