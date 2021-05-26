package com.medusa.basemall.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 后端查询商品时的请求数据
 */
@Data
public class ProductFindRequestVo {

    /**
     * 模板ID
     */
    @ApiModelProperty(value = "商家wxAppId")
    private String appmodelId;

    /**
     * 产品上下架状态  默认上架，0------上架（出售中），1---------下架（仓库中），2---------已售完
     */
    @ApiModelProperty(value = "产品上下架状态  默认上架，0------上架（出售中），1---------下架（仓库中），2---------已售完")
    private Integer shelfState;

    /**
     * 第几页
     */
    @ApiModelProperty(value = "第几页")
    private Integer pageNum;

    /**
     * 一页的数量
     */
    @ApiModelProperty(value = "一页的数量")
    private Integer pageSize;

	/**
     * 商品分类ID
     */
    @ApiModelProperty(value = "商品分类ID")
    private Long categoryId;

    /**
     * 搜索词
     */
    @ApiModelProperty(value = "搜索词")
    private String searchString;

    /**
     * 0--综合，1--销量，2--新品，3--价格
     */
    @ApiModelProperty(value = "0--综合，1--销量，2--新品，3--价格")
    private Integer sortType;

	@ApiModelProperty(hidden = true,value = "排序语句",required = false)
    private String sortSql;



}
