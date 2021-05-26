package com.medusa.basemall.promotion.vo;

import com.medusa.basemall.promotion.entity.ActivitySeckill;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Created by psy on 2018/05/30.
 */
@Data
public class ActivitySeckillVo extends ActivitySeckill {

	@ApiModelProperty(value="参加活动的商品")
	private List<JoinActityProductVO> joinActityProductVO;

	@ApiModelProperty(value="参加人数")
	private Integer numberOfPeople;
	@ApiModelProperty(value="商品数量")
	private Integer productSum;
	@ApiModelProperty(value="订单数")
	private Integer orderSum;
	@ApiModelProperty(value="成交总额")
	private Double payFeeTotle;
	@ApiModelProperty(value="修改类型 1名称 2整体")
	private Integer type;

}
