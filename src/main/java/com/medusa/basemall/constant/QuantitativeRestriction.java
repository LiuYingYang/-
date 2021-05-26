package com.medusa.basemall.constant;


/***
 * 店铺相关功能添加个数限制
 *
 * @author Created by wx on 2018/09/01.
 */
public interface QuantitativeRestriction {

    /**
     * 首页分类导航下限
     */
     Integer  FIRSTPAGECLASSIFYLOWERLIMIT = 4;

    /**
     * 首页分类导航上限
     */
     Integer  FIRSTPAGECLASSIFYUPPERLIMIT = 8;

    /**
     * 商品展示区、轮播图上限
     */
     Integer  PLATEUPPERLIMIT = 5;

    /**
     * 搜索词上限
     */
     Integer  SEARCHWORD = 10;

}