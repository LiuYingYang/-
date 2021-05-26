package com.medusa.basemall.shop.vo;

import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.shop.entity.Notice;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NoticeVO extends Notice {

	@ApiModelProperty(value = "商品信息,前端判断根据delete = 2 /shelfState= 1 判断商品是否下架")
	private Product productInfo;

}
