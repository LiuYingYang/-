package com.medusa.basemall.agent.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AgentDeliverGoods")
public class AgentDeliverGoods {

	@ApiModelProperty(value = "代理订单id"  )
	private Long purchaseOrderId;
	@ApiModelProperty(value = "配送方式  商家配送，物流配送"  )
	private String distributeMode;
	@ApiModelProperty(  value = "配送信息   JSON字符串格式,{Distributor:\"z张三\",phone:\"18368094914\",DeliveryTime:\"2018-06-26 15:55:39\"} ")
	private String deliveryStaff;
	@ApiModelProperty(value = "物流公司名称" )
	private String wlName;
	@ApiModelProperty(value = "物流单号" )
	private String wlNum;
	@ApiModelProperty(value = "物流公司代码" )
	private String wlCode;

	public String getDeliveryStaff() {
		return deliveryStaff;
	}

	public void setDeliveryStaff(String deliveryStaff) {
		this.deliveryStaff = deliveryStaff;
	}

	public Long getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(Long purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	public String getDistributeMode() {
		return distributeMode;
	}

	public void setDistributeMode(String distributeMode) {
		this.distributeMode = distributeMode;
	}

	public String getWlName() {
		return wlName;
	}

	public void setWlName(String wlName) {
		this.wlName = wlName;
	}

	public String getWlNum() {
		return wlNum;
	}

	public void setWlNum(String wlNum) {
		this.wlNum = wlNum;
	}

	public String getWlCode() {
		return wlCode;
	}

	public void setWlCode(String wlCode) {
		this.wlCode = wlCode;
	}
}
