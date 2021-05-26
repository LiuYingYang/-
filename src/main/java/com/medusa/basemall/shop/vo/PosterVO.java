package com.medusa.basemall.shop.vo;

import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.shop.entity.Poster;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PosterVO extends Poster {

	@ApiModelProperty(value = "商品信息,前端判断根据delete/shelfState判断商品是否下架")
	private Product productInfo;

}
