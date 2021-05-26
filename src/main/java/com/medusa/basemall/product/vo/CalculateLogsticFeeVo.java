package com.medusa.basemall.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CalculateLogsticFeeVo {

	@ApiModelProperty("买家收货地址 逗号分隔,省份,城市,区县,城镇,乡村,街道,门牌号码")
	private String address;

	@ApiModelProperty("商家wxAppId")
	private String appmodelId;

	@ApiModelProperty("订单结算的总金额")
	private Double totlePrice;

	@ApiModelProperty("商品信息")
	List<ProductInfoVo> ProductInfos;

	@ApiModelProperty("查询类型,1-普通订单,2-积分订单")
	private Integer type;

}
