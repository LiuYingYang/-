package com.medusa.basemall.integral.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PrizeParamVO {

	@ApiModelProperty(value = "积分id", notes = "更新积分商品/优惠券部分数据")
	private String prizeId;
	@ApiModelProperty(value = "库存   不修改传null", notes = "更新积分商品/优惠券部分数据")
	private Integer stock;
	@ApiModelProperty(value = "商品名称", notes = "更新积分商品/优惠券部分数据")
	private String prizeName;
	@ApiModelProperty(value="兑换积分 不修改传null")
	private Integer convertPrice;

	@ApiModelProperty(value = "多个积分商品ids  1,2,3")
	private String prizeIds;
	@ApiModelProperty(value = "上下架状态 1上架 2下架")
	private Integer state;
}
