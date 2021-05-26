package com.medusa.basemall.shop.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Created by wx on 2018/05/25.
 */
public class TopImg {

    @ApiModelProperty(value = "轮播图名称")
    private String name;

    @ApiModelProperty(value = "轮播图链接")
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "TopImg{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
