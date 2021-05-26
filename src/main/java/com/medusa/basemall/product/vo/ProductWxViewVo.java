package com.medusa.basemall.product.vo;

import com.medusa.basemall.product.entity.ProductSpecItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author whh
 */
@Data
public class ProductWxViewVo extends ProductSimpleVo{
	
	@ApiModelProperty(value = "商品正在参加的活动类型")
	private String productTypeList;
	@ApiModelProperty(value = "参加的活动信息,查询商品详情时为空")
	private JoinActiveInfo joinActiveInfo;
	@ApiModelProperty(value = "规格详情,查询商品详情时为空")
	private List<ProductSpecItem> productSpecItems;
	@ApiModelProperty(value = "预热状态  0-无预热,1-带预热商品")
	private Integer preheatState;
}
