package com.medusa.basemall.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class JoinActiveInfo {

	@ApiModelProperty(value = "优惠券活动信息")
	private String couponInfo;
	@ApiModelProperty(value = "秒杀/特价/拼团关键信息")
	private String activeInfo;

}
