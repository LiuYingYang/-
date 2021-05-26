package com.medusa.basemall.integral.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Created by wx on 2018/06/06.
 */
@Data
public class PrizeVo {

    @ApiModelProperty(value = "模板id", required = true)
    private String appmodelId;

    @ApiModelProperty(value = "商品状态(小程序端不传 后台查询1上架 2下架 3库存不足)")
    private Integer state;

    @ApiModelProperty(value = "当前页数", required = true)
    private Integer pageNum;

    @ApiModelProperty(value = "每页数量", required = true)
    private Integer pageSize;

	@ApiModelProperty(value = "商品名/优惠券名")
	private String  searchName;

	@ApiModelProperty(value = "商品类型 (小程序端:查询全部 prizeType不传 查询积分商品 " +
            "prizeType 传1 查询积分优惠券 prizeType 传2 后台:查询积分商品 prizeType 传1 查询积分优惠券)")
    private Integer prizeType;


}
