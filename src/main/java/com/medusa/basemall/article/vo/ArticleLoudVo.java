package com.medusa.basemall.article.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Created by wx on 2018/06/07.
 */
@Data
public class ArticleLoudVo {

    @ApiModelProperty(value = "用户id")
    private Long wxuserId;

    @ApiModelProperty(value = "文章id")
    private String articleId;

    @ApiModelProperty
    private String appmodelId;

}
