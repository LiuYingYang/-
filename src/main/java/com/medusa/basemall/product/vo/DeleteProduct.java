package com.medusa.basemall.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author whh
 */
@Data
public class DeleteProduct {

	@ApiModelProperty(value = "商品id")
	private Long productId;

	@ApiModelProperty(value = "上下架状态  0-上架 1-下架 调用批量删除接口时不需要传")
	private Integer shelfState;

	@ApiModelProperty(value = "商品参加的活动")
	private JoinActiveInfo joinActiveInfo;

	@ApiModelProperty(value = "商品所属首页模块")
	private List<PlateVo> plateVos;

}
