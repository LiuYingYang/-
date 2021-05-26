package com.medusa.basemall.user.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;

@Data
public class UserOperation {

	@Id
	private String userOperationId;

	@ApiModelProperty(value = "用户最后查看待付款时间")
	private String lastLookWaitPlayTime;

	@ApiModelProperty(value = "用户最后查看待发货时间")
	private String lastLookWaitSendProductTime;

	@ApiModelProperty(value = "用户最后查看待收货时间")
	private String lastLookWaitReceivingProductTime;

	@ApiModelProperty(value = "用户最后查看已完成时间")
	private String lastLookOkOrderTime;

	@ApiModelProperty(value = "用户最后查看售后时间")
	private String lastLookAfterSaleOrderTime;

	@ApiModelProperty(value = "商品appmodelId")
	private String appmodelId;

	@ApiModelProperty(value = "用户id")
	private Long wxuserId;
}
