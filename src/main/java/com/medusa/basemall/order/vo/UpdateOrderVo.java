package com.medusa.basemall.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class UpdateOrderVo {

    @ApiModelProperty(value = "订单id"  )
    private Long orderId;
    @ApiModelProperty(value = "联系电话" )
    private String telPhone;
    @ApiModelProperty(value = "配送方式  商家配送，物流配送"  )
    private String distributeMode;
    @ApiModelProperty(  value = "配送信息   JSON字符串格式,{Distributor:\"z张三\",phone:\"18368094914\",DeliveryTime:\"2018-06-26 15:55:39\"} ")
    private String deliveryStaff;
    @ApiModelProperty(value = "收货地址" )
    private String userAddress;
    @ApiModelProperty(value = "收货人" )
    private String userName;
    @ApiModelProperty(value = "总价" )
    private BigDecimal payFee;
    @ApiModelProperty(value = "物流公司名称" )
    private String wlName;
    @ApiModelProperty(value = "物流单号" )
    private String wlNum;
    @ApiModelProperty(value = "物流公司代码" )
    private String wlCode;
	@ApiModelProperty(value = "商家wxAppId" )
	private String appmodelId;

}