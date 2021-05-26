package com.medusa.basemall.product.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author Created by wx on 2018/09/10.
 */
public class PageUtil {

    @ApiModelProperty(value = "查询结果")
    private List<CategoryAndProductVo> list;

    @ApiModelProperty(value = "数据总量")
    private Integer total;

    public List<CategoryAndProductVo> getList() {
        return list;
    }

    public void setList(List<CategoryAndProductVo> list) {
        this.list = list;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
