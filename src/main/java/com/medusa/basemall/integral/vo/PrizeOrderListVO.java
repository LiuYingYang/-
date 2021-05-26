package com.medusa.basemall.integral.vo;

import com.medusa.basemall.integral.entity.Prize;
import com.medusa.basemall.integral.entity.PrizeOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PrizeOrderListVO extends PrizeOrder {

	@ApiModelProperty(value="积分商品")
	private Prize prize;
}
