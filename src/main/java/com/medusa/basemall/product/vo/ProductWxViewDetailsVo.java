package com.medusa.basemall.product.vo;

import com.medusa.basemall.product.entity.Parameter;
import com.medusa.basemall.user.entity.Collect;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author whh
 */
@Data
public class ProductWxViewDetailsVo extends ProductWxViewVo {

	@ApiModelProperty(value = "市场价(划掉的价格)")
	private Double marketPrice;

	@ApiModelProperty(value = "发货地")
	private String sendPlace;

	@ApiModelProperty(value = "发货日期")
	private String sendDate;

	@ApiModelProperty(value = "服务保障")
	private List<String> serviceAssuranceList;

	@ApiModelProperty(value = "图文详情")
	private String textImg;

	@ApiModelProperty(value = "轮播图")
	private List<String> rimgurlList;

	@ApiModelProperty(value = "属性数组")
	private List<Parameter> paramValueList;

	@ApiModelProperty(value = "收藏对象")
	private Collect collect;

	@ApiModelProperty(value = "最多可获得多少积分")
	private Integer maxIntegral;

	@ApiModelProperty(value = "活动详情信息(秒杀/特价/拼团)")
	private ActiveInfo activeInfo;

	@ApiModelProperty(value = "活动类型")
	private Integer activeType;

	@ApiModelProperty(value = "预热状态  0-无预热,1-带预热商品")
	private Integer preheatState;

	@Data
	public class ActiveInfo {
		@ApiModelProperty(value = "活动名称")
		private String activityName;
		@ApiModelProperty(value = "活动结束时间")
		private String endDate;
		@ApiModelProperty(value = "活动开始时间")
		private String startDate;
		@ApiModelProperty(value = "限购")
		private Integer maxQuantity;
		@ApiModelProperty(value = "已售数量")
		private Integer soldQuantity;
		@ApiModelProperty(value = "活动库存")
		private Integer activityStock;
		@ApiModelProperty(value = "商品总销量")
		private Integer totleSold;
		@ApiModelProperty(value = "活动状态")
		private Integer nowState;
	}


}
