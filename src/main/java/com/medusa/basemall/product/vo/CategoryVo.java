package com.medusa.basemall.product.vo;

import com.medusa.basemall.product.entity.Category;

import java.util.List;

/**
 * 分类信息，包含子分类
 */
public class CategoryVo extends Category {

    private List<Category> subCategoryList;

    public List<Category> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(List<Category> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }
}
