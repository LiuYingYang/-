package com.medusa.basemall.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class HomeSeckillVO {

	@ApiModelProperty(value="秒杀活动id")
	private Integer activitySeckillId;

	@ApiModelProperty(value = "活动名称")
	private String activitySeckillName;

	@ApiModelProperty(value = "活动海报")
	private String activityImg;

	@ApiModelProperty(value="预热时间 ")
	private String preheatTime;

	@ApiModelProperty(value = "活动开始时间")
	private String startDate;

	@ApiModelProperty(value = "活动结束时间")
	private String endDate;

	@ApiModelProperty(value = "活动商品")
	private List<ProductBackViewVo> activityProduct;

	@ApiModelProperty(value="活动当前状态 0已结束，1未开始，2进行中")
	private Integer nowState;

}
