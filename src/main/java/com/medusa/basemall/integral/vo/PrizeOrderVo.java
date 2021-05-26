package com.medusa.basemall.integral.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Created by wx on 2018/06/06.
 */
@Data
public class PrizeOrderVo {
    @ApiModelProperty(value = "微信昵称")
    private String  wxuserName;

    @ApiModelProperty(value = "模板id", required = true)
    private String  appmodelId;

    @ApiModelProperty(value = "订单类型(1 商品订单 2优惠券订单)", required = true)
    private Integer orderType;

    @ApiModelProperty(value = "订单状态(商品订单 1 已付款 2已发货 3已收货 (不传查所有) 优惠券订单不传)")
    private Integer orderState;

    @ApiModelProperty(value = "当前页数", required = true)
    private Integer pageNum;

    @ApiModelProperty(value = "每页数量", required = true)
    private Integer pageSize;


}
