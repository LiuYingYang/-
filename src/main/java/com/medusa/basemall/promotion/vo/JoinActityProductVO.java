package com.medusa.basemall.promotion.vo;

import com.medusa.basemall.promotion.entity.ActivityProductStock;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author whh
 */
@Data
public class JoinActityProductVO {


	@ApiModelProperty(value = "商品id")
	private Long productId;

	@ApiModelProperty(value = "活动价 商品原价减去折扣的价格")
	private Double activityPrice;

	@ApiModelProperty(value = "活动折扣")
	private Double activityDiscount;

	@ApiModelProperty(value = "活动库存")
	private Integer activityStock;

	@ApiModelProperty(value = "限购")
	private Integer maxQuantity;

	@ApiModelProperty(value = "首页排序  数值越小越大 but required >= 0")
	private Integer sort;

	@ApiModelProperty(value = "是否显示在首页 0-不显示 1-显示")
	private Integer homeViewStat;

	@ApiModelProperty(value = "多规格库存")
	private List<ActivityProductStock> activityProductStocks;
}
