package com.medusa.basemall.product.vo;

import com.medusa.basemall.product.entity.ProductSpecItem;

import java.util.List;

/**
 * 商品信息，用在PC后端查询所有商品时（findProductForBack）
 */
public class ProductBackVo extends ProductSimpleVo {

    private List<CategoryProductVo> categoryList;

    private List<ProductSpecItem> productSpecItems;

	public List<ProductSpecItem> getProductSpecItems() {
		return productSpecItems;
	}

	public void setProductSpecItems(List<ProductSpecItem> productSpecItems) {
		this.productSpecItems = productSpecItems;
	}

	public List<CategoryProductVo> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryProductVo> categoryList) {
        this.categoryList = categoryList;
    }
}
