package com.medusa.basemall.article.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @author Created by wx on 2018/06/07.
 */
@Data
public class ArticleLaud {

    @ApiModelProperty(value = "记录文章点赞id")
    @Id
    private String articleLaudId;

    @ApiModelProperty(value = "用户id")
    private Long wxuserId;

    @ApiModelProperty(value = "文章id")
    private String articleId;

    @ApiModelProperty(value = "点赞状态")
    private Boolean laudOrNot;

	@ApiModelProperty(value = "商家appId")
	private String appmodelId;


}
