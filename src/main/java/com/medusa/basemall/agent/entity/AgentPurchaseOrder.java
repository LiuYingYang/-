package com.medusa.basemall.agent.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "t_agent_purchase_order")
public class AgentPurchaseOrder {
	/**
	 * 采购订单表id
	 */
	@Id
	@Column(name = "purchase_order_id")
	@ApiModelProperty(value = "采购订单表id")
	private Long purchaseOrderId;

	/**
	 * 代理商品id
	 */
	@Column(name = "agent_product_id")
	@ApiModelProperty(value = "代理商品id")
	private Long agentProductId;

	/**
	 * 再次支付订单号
	 */
	@Column(name = "out_trade_no_ext")
	@ApiModelProperty(value = "再次支付订单号")
	private String outTradeNoExt;

	/**
	 * 订单号
	 */
	@Column(name = "out_trade_no")
	@ApiModelProperty(value = "订单号")
	private String outTradeNo;

	@Column(name = "pay_flag")
	@ApiModelProperty(value = "支付状态  0.等待买家付款  1.买家已付款 待发货状态 2.卖家已发货  3.交易成功 4.订单关闭")
	private Integer payFlag;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	@ApiModelProperty(value = "创建时间")
	private String createTime;

	@Column(name = "pay_type")
	@ApiModelProperty(value = "支付方式")
	private Integer payType;

	/**
	 * 支付价
	 */
	@Column(name = "pay_fee")
	@ApiModelProperty(value = "支付价")
	private BigDecimal payFee;

	/**
	 * 总价
	 */
	@Column(name = "total_fee")
	@ApiModelProperty(value = "总价")
	private BigDecimal totalFee;

	/**
	 * 用户ID
	 */
	@Column(name = "wxuser_id")
	@ApiModelProperty(value = "用户ID")
	private Long wxuserId;

	/**
	 * 支付时间
	 */
	@Column(name = "pay_time")
	@ApiModelProperty(value = "支付时间")
	private String payTime;

	/**
	 * 发货时间
	 */
	@Column(name = "send_time")
	@ApiModelProperty(value = "发货时间")
	private String sendTime;

	/**
	 * 收货时间
	 */
	@Column(name = "record_time")
	@ApiModelProperty(value = "收货时间")
	private String recordTime;

	/**
	 * 退款时间
	 */
	@Column(name = "refound_time")
	@ApiModelProperty(value = "退款时间")
	private String refoundTime;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	/**
	 * 收货地址
	 */
	@ApiModelProperty(value = "收货地址")
	private String address;

	/**
	 * 物流单号
	 */
	@Column(name = "wl_num")
	@ApiModelProperty(value = "物流单号")
	private String wlNum;

	/**
	 * 物流公司代码
	 */
	@Column(name = "wl_code")
	@ApiModelProperty(value = "物流公司代码")
	private String wlCode;

	/**
	 * 物流公司名称
	 */
	@Column(name = "wl_name")
	@ApiModelProperty(value = "物流公司名称")
	private String wlName;

	/**
	 * 邮费
	 */
	@Column(name = "wl_price")
	@ApiModelProperty(value = "邮费")
	private Long wlPrice;

	/**
	 * 配送方式
	 */
	@Column(name = "distri_mode")
	@ApiModelProperty(value = "配送方式")
	private String distriMode;


	/**
	 * 配送信息
	 */
	@Column(name = "delivery_staff")
	@ApiModelProperty(value = "配送信息")
	private String deliveryStaff;

	/**
	 * 商家备注
	 */
	@Column(name = "back_remark")
	@ApiModelProperty(value = "商家备注")
	private String backRemark;

	/**
	 * 订单关闭时间
	 */
	@Column(name = "close_time")
	@ApiModelProperty(value = "订单关闭时间")
	private String closeTime;

	/**
	 * 删除标志
	 */
	@Column(name = "delete_state")
	@ApiModelProperty(value = "删除标志")
	private Boolean deleteState;

	/**
	 * 模板ID
	 */
	@Column(name = "appmodel_id")
	@ApiModelProperty(value = "模板ID")
	private String appmodelId;

	/**
	 * 更新时间
	 */
	@Column(name = "update_time")
	@ApiModelProperty(value = "更新时间")
	private String updateTime;


	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Integer getPayFlag() {
		return payFlag;
	}

	public void setPayFlag(Integer payFlag) {
		this.payFlag = payFlag;
	}

	/**
	 * 获取采购订单表id
	 *
	 * @return purchase_order_id - 采购订单表id
	 */
	public Long getPurchaseOrderId() {
		return purchaseOrderId;
	}

	/**
	 * 设置采购订单表id
	 *
	 * @param purchaseOrderId 采购订单表id
	 */
	public void setPurchaseOrderId(Long purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	/**
	 * 获取代理商品id
	 *
	 * @return agent_product_id - 代理商品id
	 */
	public Long getAgentProductId() {
		return agentProductId;
	}

	/**
	 * 设置代理商品id
	 *
	 * @param agentProductId 代理商品id
	 */
	public void setAgentProductId(Long agentProductId) {
		this.agentProductId = agentProductId;
	}

	/**
	 * 获取再次支付订单号
	 *
	 * @return out_trade_no_ext - 再次支付订单号
	 */
	public String getOutTradeNoExt() {
		return outTradeNoExt;
	}

	/**
	 * 设置再次支付订单号
	 *
	 * @param outTradeNoExt 再次支付订单号
	 */
	public void setOutTradeNoExt(String outTradeNoExt) {
		this.outTradeNoExt = outTradeNoExt;
	}

	/**
	 * 获取订单号
	 *
	 * @return out_trade_no - 订单号
	 */
	public String getOutTradeNo() {
		return outTradeNo;
	}

	/**
	 * 设置订单号
	 *
	 * @param outTradeNo 订单号
	 */
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	/**
	 * 获取创建时间
	 *
	 * @return create_time - 创建时间
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * 设置创建时间
	 *
	 * @param createTime 创建时间
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取支付价
	 *
	 * @return pay_fee - 支付价
	 */
	public BigDecimal getPayFee() {
		return payFee;
	}

	/**
	 * 设置支付价
	 *
	 * @param payFee 支付价
	 */
	public void setPayFee(BigDecimal payFee) {
		this.payFee = payFee;
	}

	/**
	 * 获取总价
	 *
	 * @return total_fee - 总价
	 */
	public BigDecimal getTotalFee() {
		return totalFee;
	}

	/**
	 * 设置总价
	 *
	 * @param totalFee 总价
	 */
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	/**
	 * 获取用户ID
	 *
	 * @return wxuser_id - 用户ID
	 */
	public Long getWxuserId() {
		return wxuserId;
	}

	/**
	 * 设置用户ID
	 *
	 * @param wxuserId 用户ID
	 */
	public void setWxuserId(Long wxuserId) {
		this.wxuserId = wxuserId;
	}

	/**
	 * 获取支付时间
	 *
	 * @return pay_time - 支付时间
	 */
	public String getPayTime() {
		return payTime;
	}

	/**
	 * 设置支付时间
	 *
	 * @param payTime 支付时间
	 */
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	/**
	 * 获取发货时间
	 *
	 * @return send_time - 发货时间
	 */
	public String getSendTime() {
		return sendTime;
	}

	/**
	 * 设置发货时间
	 *
	 * @param sendTime 发货时间
	 */
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	/**
	 * 获取收货时间
	 *
	 * @return record_time - 收货时间
	 */
	public String getRecordTime() {
		return recordTime;
	}

	/**
	 * 设置收货时间
	 *
	 * @param recordTime 收货时间
	 */
	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	/**
	 * 获取退款时间
	 *
	 * @return refound_time - 退款时间
	 */
	public String getRefoundTime() {
		return refoundTime;
	}

	/**
	 * 设置退款时间
	 *
	 * @param refoundTime 退款时间
	 */
	public void setRefoundTime(String refoundTime) {
		this.refoundTime = refoundTime;
	}

	/**
	 * 获取备注
	 *
	 * @return remark - 备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置备注
	 *
	 * @param remark 备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取收货地址
	 *
	 * @return address - 收货地址
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 设置收货地址
	 *
	 * @param address 收货地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取物流单号
	 *
	 * @return wl_num - 物流单号
	 */
	public String getWlNum() {
		return wlNum;
	}

	/**
	 * 设置物流单号
	 *
	 * @param wlNum 物流单号
	 */
	public void setWlNum(String wlNum) {
		this.wlNum = wlNum;
	}

	/**
	 * 获取物流公司代码
	 *
	 * @return wl_code - 物流公司代码
	 */
	public String getWlCode() {
		return wlCode;
	}

	/**
	 * 设置物流公司代码
	 *
	 * @param wlCode 物流公司代码
	 */
	public void setWlCode(String wlCode) {
		this.wlCode = wlCode;
	}

	/**
	 * 获取邮费
	 *
	 * @return wl_price - 邮费
	 */
	public Long getWlPrice() {
		return wlPrice;
	}

	/**
	 * 设置邮费
	 *
	 * @param wlPrice 邮费
	 */
	public void setWlPrice(Long wlPrice) {
		this.wlPrice = wlPrice;
	}

	/**
	 * 获取配送方式
	 *
	 * @return distri_mode - 配送方式
	 */
	public String getDistriMode() {
		return distriMode;
	}

	/**
	 * 设置配送方式
	 *
	 * @param distriMode 配送方式
	 */
	public void setDistriMode(String distriMode) {
		this.distriMode = distriMode;
	}

	/**
	 * 获取商家备注
	 *
	 * @return back_remark - 商家备注
	 */
	public String getBackRemark() {
		return backRemark;
	}

	/**
	 * 设置商家备注
	 *
	 * @param backRemark 商家备注
	 */
	public void setBackRemark(String backRemark) {
		this.backRemark = backRemark;
	}

	/**
	 * 获取订单关闭时间
	 *
	 * @return close_time - 订单关闭时间
	 */
	public String getCloseTime() {
		return closeTime;
	}

	/**
	 * 设置订单关闭时间
	 *
	 * @param closeTime 订单关闭时间
	 */
	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}

	/**
	 * 获取删除标志
	 *
	 * @return delete_state - 删除标志
	 */
	public Boolean getDeleteState() {
		return deleteState;
	}

	/**
	 * 设置删除标志
	 *
	 * @param deleteState 删除标志
	 */
	public void setDeleteState(Boolean deleteState) {
		this.deleteState = deleteState;
	}

	/**
	 * 获取模板ID
	 *
	 * @return appmodel_id - 模板ID
	 */
	public String getAppmodelId() {
		return appmodelId;
	}

	/**
	 * 设置模板ID
	 *
	 * @param appmodelId 模板ID
	 */
	public void setAppmodelId(String appmodelId) {
		this.appmodelId = appmodelId;
	}

	/**
	 * 获取物流公司名称
	 *
	 * @return wl_name - 物流公司名称
	 */
	public String getWlName() {
		return wlName;
	}

	/**
	 * 设置物流公司名称
	 *
	 * @param wlName 物流公司名称
	 */
	public void setWlName(String wlName) {
		this.wlName = wlName;
	}

	/**
	 * 获取更新时间
	 *
	 * @return update_time - 更新时间
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置更新时间
	 *
	 * @param updateTime 更新时间
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 获取配送信息
	 *
	 * @return delivery_staff - 配送信息
	 */
	public String getDeliveryStaff() {
		return deliveryStaff;
	}

	/**
	 * 设置配送信息
	 *
	 * @param deliveryStaff 配送信息
	 */
	public void setDeliveryStaff(String deliveryStaff) {
		this.deliveryStaff = deliveryStaff;
	}
}