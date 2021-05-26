package com.medusa.basemall.order.entity;

import com.medusa.basemall.product.entity.Product;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderSurvey {

	@ApiModelProperty(value = "成交订单总数 ")
	private Integer allOverOrder;

	@ApiModelProperty(value = "成交总额")
	private Double allPrice;

	@ApiModelProperty(value = "购物车总商品数")
	private Integer allBuyCarNumber;

	@ApiModelProperty(value = "今日订单数")
	private Integer todayOverOrder;

	@ApiModelProperty(value = "待发货订单")
	private Integer todayWaitOrder;

	@ApiModelProperty(value = "今日商品加入购物车的数量")
	private Integer todayBuyCarNumber;

	@ApiModelProperty(value = "售后订单")
	private Integer todayRefoundOrder;

	@ApiModelProperty(value = "今日成交额")
	private Double todayPrice;

	@ApiModelProperty(value = "库存警告数量")
	private Integer urgentProductNum;

	@ApiModelProperty(value = "库存警告商品")
	private List<Product> urgentProducts;

	@ApiModelProperty(value = "购物车商品分类统计")
	private List<Buycar> buycarList;

	@ApiModelProperty(value = "当前时间")
	private String nowTime;


}
