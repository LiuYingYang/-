package com.medusa.basemall.article.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Created by wx on 2018/06/07.
 */
@Data
public class SetCategoryVo {

	@ApiModelProperty(value = "选择的文章id  逗号分隔")
	private String articleIds;

	@ApiModelProperty(value = "所有的文章有要的分类")
	private String entirelyIncludeCategoryIds;

	@ApiModelProperty(value = "完全排除的分类")
	private String entirelyExcludeCategoryIds;

	@ApiModelProperty(value = "商家appmodelId")
	private String appmodelId;
}
