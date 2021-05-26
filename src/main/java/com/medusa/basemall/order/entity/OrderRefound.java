package com.medusa.basemall.order.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "t_order_refound")
public class OrderRefound {
	/**
	 * 退款ID
	 */
	@Id
	@Column(name = "order_refound_id")
	@ApiModelProperty(value = "退款ID  ")
	private Long orderRefoundId;

	@Column(name = "order_detail_id")
	@ApiModelProperty(value = "退款ID  ")
	private Long orderDetailId;
	/**
	 * 订单ID
	 */
	@Column(name = "order_id")
	@ApiModelProperty(value = "退款ID  ")
	private Long orderId;

	/**
	 * 用户ID
	 */
	@Column(name = "wxuser_id")
	@ApiModelProperty(value = "用户ID  ")
	private Long wxuserId;

	/**
	 * 退款状态        0-用户申请退款  1-订单属于待发货状态,卖家超时未处理退款成功 2-同意退款退货
	 3-用户填写物流 4-商家确认收货退款 5-订单属于待收货状态,卖家操作超时未处理,自动填写物流
	 6-买家物流填写超时 7-商家发货 8-拒绝退款 9-拒绝退货退款
	 10-买家撤销退款 11-退款成功  12-用户撤消申请
	 */
	@Column(name = "flow_path")
	@ApiModelProperty(value = "退款状态: 0-用户申请退款  1-订单属于待发货状态,卖家超时未处理退款成功 2-同意退款退货"
			+ "3-用户填写物流 4-商家确认收货退款 5-订单属于待收货状态,卖家操作超时未处理,自动填写物流 "
			+ " 6-买家物流填写超时 7-商家发货 8-拒绝退款 9-拒绝退货退款   10-买家撤销退款 11-退款成功  12-用户撤消申请", dataType = "Integer")
	private Integer flowPath;

	/**
	 * 交易状态
	 1.申请中 2.卖家同意退货退款
	 3.卖家已拒绝   4.退款关闭
	 5.退款完成
	 */
	@Column(name = "business_state")
	@ApiModelProperty(value = "交易状态 1.申请中 2.卖家同意退货退款 3.卖家已拒绝   4.退款关闭 5.退款完成 ", dataType = "Integer")
	private Integer businessState;

	/**
	 * 退款类型  0--只退款，1--退货退款
	 */
	@Column(name = "refound_type")
	@ApiModelProperty(value = "退款类型  0--只退款，1--退货退款  ", dataType = "Integer")
	private Integer refoundType;

	/**
	 * 退款原因
	 */
	@ApiModelProperty(value = "退款原因  ")
	private String reason;

	/**
	 * 退款金额
	 */
	@Column(name = "refound_fee")
	@ApiModelProperty(value = "退款金额  ", dataType = "BigDecimal")
	private BigDecimal refoundFee;

	/**
	 * 退款备注
	 */
	@ApiModelProperty(value = "退款备注  ")
	private String remark;

	/**
	 * 货物状态
	 */
	@ApiModelProperty(value = "货物状态  ")
	@Column(name = "product_state")
	private String productState;

	/**
	 * 拒绝原因
	 */
	@ApiModelProperty(value = "拒绝原因  ")
	@Column(name = "refuse_reason")
	private String refuseReason;

	/**
	 * 退款物流公司
	 */
	@ApiModelProperty(value = "退款物流公司  ")
	@Column(name = "u_wl_name")
	private String uWlName;

	/**
	 * 快递备注
	 */
	@ApiModelProperty(value = "快递备注  ")
	@Column(name = "u_wl_remark")
	private String uWlRemark;

	/**
	 * 快递联系人手机号
	 */
	@ApiModelProperty(value = "快递联系人手机号  ")
	@Column(name = "u_wl_phone")
	private String uWlPhone;

	/**
	 * 物流单号
	 */
	@ApiModelProperty(value = "物流单号  ")
	@Column(name = "u_wl_num")
	private String uWlNum;

	/**
	 * 快递公司代码
	 */
	@ApiModelProperty(value = "快递公司代码  ")
	@Column(name = "u_wl_code")
	private String uWlCode;

	/**
	 * 卖家收货地址
	 */
	@ApiModelProperty(value = "卖家收货地址")
	@Column(name = "shop_address")
	private String shopAddress;

	/**
	 * 申请时间
	 */
	@ApiModelProperty(value = "申请时间")
	@Column(name = "create_time")
	private String createTime;

	/**
	 * 操作更新时间
	 */
	@ApiModelProperty(value = "操作更新时间 ")
	@Column(name = "update_time")
	private String updateTime;

	/**
	 * 退款时间
	 */
	@ApiModelProperty(value = "退款时间")
	@Column(name = "refound_time")
	private String refoundTime;

	/**
	 * 模板ID
	 */
	@ApiModelProperty(value = "模板ID")
	@Column(name = "appmodel_id")
	private String appmodelId;

	/**
	 * 删除标志
	 */
	@ApiModelProperty(value = "删除标志", dataType = "Boolean")
	@Column(name = "delete_state")
	private Boolean deleteState;

	public String getuWlCode() {
		return uWlCode;
	}

	public void setuWlCode(String uWlCode) {
		this.uWlCode = uWlCode;
	}

	/**
	 * 获取退款ID
	 *
	 * @return order_refound_id - 退款ID
	 */
	public Long getOrderRefoundId() {
		return orderRefoundId;
	}

	/**
	 * 设置退款ID
	 *
	 * @param orderRefoundId 退款ID
	 */
	public void setOrderRefoundId(Long orderRefoundId) {
		this.orderRefoundId = orderRefoundId;
	}

	/**
	 * @return order_detail_id
	 */
	public Long getOrderDetailId() {
		return orderDetailId;
	}

	/**
	 * @param orderDetailId
	 */
	public void setOrderDetailId(Long orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	/**
	 * 获取订单ID
	 *
	 * @return order_id - 订单ID
	 */
	public Long getOrderId() {
		return orderId;
	}

	/**
	 * 设置订单ID
	 *
	 * @param orderId 订单ID
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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
	 * @return flow_path - 退款状态        0-用户申请退款  1-订单属于待发货状态,卖家超时未处理退款成功 2-同意退款退货
	3-用户填写物流 4-商家确认收货退款 5-订单属于待收货状态,卖家操作超时未处理,自动填写物流
	6-买家物流填写超时 7-商家发货 8-拒绝退款 9-拒绝退货退款
	10-买家撤销退款 11-退款成功  12-用户撤消申请
	 */
	public Integer getFlowPath() {
		return flowPath;
	}

	/**
	 * @param flowPath 退款状态        0-用户申请退款  1-订单属于待发货状态,卖家超时未处理退款成功 2-同意退款退货
	3-用户填写物流 4-商家确认收货退款 5-订单属于待收货状态,卖家操作超时未处理,自动填写物流
	6-买家物流填写超时 7-商家发货 8-拒绝退款 9-拒绝退货退款
	10-买家撤销退款 11-退款成功  12-用户撤消申请
	 */
	public void setFlowPath(Integer flowPath) {
		this.flowPath = flowPath;
	}

	/**
	 * 获取交易状态
	 1.申请中 2.卖家同意退货退款
	 3.卖家已拒绝   4.退款关闭
	 5.退款完成
	 *
	 * @return business_state*/
	public Integer getBusinessState() {
		return businessState;
	}

	/**
	 * 设置交易状态
	 1.申请中 2.卖家同意退货退款
	 3.卖家已拒绝   4.退款关闭
	 5.退款完成
	 *
	 * @param businessState 交易状态
	1.申请中 2.卖家同意退货退款
	3.卖家已拒绝   4.退款关闭
	5.退款完成
	 */
	public void setBusinessState(Integer businessState) {
		this.businessState = businessState;
	}

	/**
	 * 获取退款类型  0--只退款，1--退货退款
	 *
	 * @return refound_type - 退款类型  0--只退款，1--退货退款
	 */
	public Integer getRefoundType() {
		return refoundType;
	}

	/**
	 * 设置退款类型  0--只退款，1--退货退款
	 *
	 * @param refoundType 退款类型  0--只退款，1--退货退款
	 */
	public void setRefoundType(Integer refoundType) {
		this.refoundType = refoundType;
	}

	/**
	 * 获取退款原因
	 *
	 * @return reason - 退款原因
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * 设置退款原因
	 *
	 * @param reason 退款原因
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * 获取退款金额
	 *
	 * @return refound_fee - 退款金额
	 */
	public BigDecimal getRefoundFee() {
		return refoundFee;
	}

	/**
	 * 设置退款金额
	 *
	 * @param refoundFee 退款金额
	 */
	public void setRefoundFee(BigDecimal refoundFee) {
		this.refoundFee = refoundFee;
	}

	/**
	 * 获取退款备注
	 *
	 * @return remark - 退款备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置退款备注
	 *
	 * @param remark 退款备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取货物状态
	 *
	 * @return product_state - 货物状态
	 */
	public String getProductState() {
		return productState;
	}

	/**
	 * 设置货物状态
	 *
	 * @param productState 货物状态
	 */
	public void setProductState(String productState) {
		this.productState = productState;
	}

	/**
	 * 获取拒绝原因
	 *
	 * @return refuse_reason - 拒绝原因
	 */
	public String getRefuseReason() {
		return refuseReason;
	}

	/**
	 * 设置拒绝原因
	 *
	 * @param refuseReason 拒绝原因
	 */
	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	/**
	 * 获取退款物流公司
	 *
	 * @return u_wl_name - 退款物流公司
	 */
	public String getuWlName() {
		return uWlName;
	}

	/**
	 * 设置退款物流公司
	 *
	 * @param uWlName 退款物流公司
	 */
	public void setuWlName(String uWlName) {
		this.uWlName = uWlName;
	}

	/**
	 * 获取快递备注
	 *
	 * @return u_wl_remark - 快递备注
	 */
	public String getuWlRemark() {
		return uWlRemark;
	}

	/**
	 * 设置快递备注
	 *
	 * @param uWlRemark 快递备注
	 */
	public void setuWlRemark(String uWlRemark) {
		this.uWlRemark = uWlRemark;
	}

	/**
	 * 获取快递联系人手机号
	 *
	 * @return u_wl_phone - 快递联系人手机号
	 */
	public String getuWlPhone() {
		return uWlPhone;
	}

	/**
	 * 设置快递联系人手机号
	 *
	 * @param uWlPhone 快递联系人手机号
	 */
	public void setuWlPhone(String uWlPhone) {
		this.uWlPhone = uWlPhone;
	}

	/**
	 * 获取物流单号
	 *
	 * @return u_wl_num - 物流单号
	 */
	public String getuWlNum() {
		return uWlNum;
	}

	/**
	 * 设置物流单号
	 *
	 * @param uWlNum 物流单号
	 */
	public void setuWlNum(String uWlNum) {
		this.uWlNum = uWlNum;
	}

	/**
	 * 获取卖家收货地址
	 *
	 * @return shop_address - 卖家收货地址
	 */
	public String getShopAddress() {
		return shopAddress;
	}

	/**
	 * 设置卖家收货地址
	 *
	 * @param shopAddress 卖家收货地址
	 */
	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	/**
	 * 获取申请时间
	 *
	 * @return create_time - 申请时间
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * 设置申请时间
	 *
	 * @param createTime 申请时间
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取操作更新时间
	 *
	 * @return update_time - 操作更新时间
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置操作更新时间
	 *
	 * @param updateTime 操作更新时间
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
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
}