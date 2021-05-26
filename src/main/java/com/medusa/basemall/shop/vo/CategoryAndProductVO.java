package com.medusa.basemall.shop.vo;

import com.medusa.basemall.product.entity.Category;
import com.medusa.basemall.product.entity.Product;

import java.util.List;

/**
 * @author Created by wx on 2018/09/07.
 */
public class CategoryAndProductVO extends Category {

    private List<CategoryAndProductVO> categoryAndProductVOS;

    private List<Product> products;

    public List<CategoryAndProductVO> getCategoryAndProductVOS() {
        return categoryAndProductVOS;
    }

    public void setCategoryAndProductVOS(List<CategoryAndProductVO> categoryAndProductVOS) {
        this.categoryAndProductVOS = categoryAndProductVOS;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
