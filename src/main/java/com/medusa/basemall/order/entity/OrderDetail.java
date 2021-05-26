package com.medusa.basemall.order.entity;


import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author whh
 */
@Table(name = "t_order_detail")
public class OrderDetail {

    @Id
    @Column(name = "order_detail_id")
    @ApiModelProperty(value = "订单详情id"  )
    private Long orderDetailId;

    @Column(name = "order_id")
    @ApiModelProperty(value = "订单编号"  )
    private Long orderId;

    @Column(name = "product_id")
    @ApiModelProperty(value = "产品ID"  )
    private Long productId;

    @ApiModelProperty(value = "商品数量", dataType = "Integer")
    private Integer quantity;

    @Column(name = "product_name")
    @ApiModelProperty(value = "商品名称"  )
    private String productName;


    @ApiModelProperty(value = "商品价格", dataType = "BigDecimal")
    private BigDecimal price;


    @Column(name = "product_img")
    @ApiModelProperty(value = "商品主图"  )
    private String productImg;

    @Column(name = "product_spec_info")
    @ApiModelProperty(value = "商品规格  json字符串"  )
    private String productSpecInfo;

    @Column(name = "update_time")
    @ApiModelProperty(value = "添加时间"  )
    private String updateTime;

    @Column(name = "appmodel_id")
    @ApiModelProperty(value = "模板ID"  )
    private String appmodelId;

    @Column(name = "delete_state")
    @ApiModelProperty(value = "删除标志", dataType = "Boolean")
    private Boolean deleteState;

    @Column(name = "activity_info")
    @ApiModelProperty(value = "活动信息"  )
    private String activityInfo;

    @Column(name = "apply_state")
    @ApiModelProperty(value = "申请状态 0-正常订单商品 1申请中  2退款成功  3退款失败", dataType = "Integer")
    private Integer applyState;

    @ApiModelProperty(value = "商品退款次数", dataType = "Integer")
    private Integer number;

    @ApiModelProperty(value = "优惠价格", dataType = "BigDecimal")
    private BigDecimal preferential;

    @ApiModelProperty(value = "售后操作记录 json字符串"  )
    private String record;

    @Column(name = "wl_price")
    @ApiModelProperty(value = "物流价格", dataType = "BigDecimal")
    private BigDecimal wlPrice;

    @Transient
    @ApiModelProperty(value = "订单支付状态 ", dataType = "Integer")
    private Integer payFlag;

    @Transient
    @ApiModelProperty(value = "微信昵称"  )
    private String nickName;
    @Transient
    @ApiModelProperty(value = "订单信息", dataType = "Order")
	private Order orderInfo;

    public Order getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(Order orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getPayFlag() {
        return payFlag;
    }

    public void setPayFlag(Integer payFlag) {
        this.payFlag = payFlag;
    }

    public void setWlPrice(BigDecimal wlPrice) {
        this.wlPrice = wlPrice;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    private List<OrderRefound> orderRefounds;

    public List<OrderRefound> getOrderRefounds() {
        return orderRefounds;
    }

    public void setOrderRefounds(List<OrderRefound> orderRefounds) {
        this.orderRefounds = orderRefounds;
    }

    public BigDecimal getPreferential() {
        return preferential;
    }

    public void setPreferential(BigDecimal preferential) {
        this.preferential = preferential;
    }

    /**
     * 交易状态
     * 1.申请中
     * 2.卖家同意退货退款
     * 3.卖家已拒绝
     * 4.退款关闭
     * 5.退款完
     */
    @Column(name = "refuse_state")
    private Integer refuseState;

    /**
     * 获取编号
     *
     * @return order_detail_id - 编号
     */
    public Long getOrderDetailId() {
        return orderDetailId;
    }

    /**
     * 设置编号
     *
     * @param orderDetailId 编号
     */
    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    /**
     * 获取订单编号
     *
     * @return order_id - 订单编号
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置订单编号
     *
     * @param orderId 订单编号
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取产品ID
     *
     * @return product_id - 产品ID
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * 设置产品ID
     *
     * @param productId 产品ID
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * 获取商品数量
     *
     * @return quantity - 商品数量
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * 设置商品数量
     *
     * @param quantity 商品数量
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * 获取商品名称
     *
     * @return product_name - 商品名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设置商品名称
     *
     * @param productName 商品名称
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 获取商品价格
     *
     * @return price - 商品价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置商品价格
     *
     * @param price 商品价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取商品主图
     *
     * @return product_img - 商品主图
     */
    public String getProductImg() {
        return productImg;
    }

    /**
     * 设置商品主图
     *
     * @param productImg 商品主图
     */
    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    /**
     * 获取商品规格
     *
     * @return product_spec_info - 商品规格
     */
    public String getProductSpecInfo() {
        return productSpecInfo;
    }

    /**
     * 设置商品规格
     *
     * @param productSpecInfo 商品规格
     */
    public void setProductSpecInfo(String productSpecInfo) {
        this.productSpecInfo = productSpecInfo;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
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

    /**
     * 获取活动信息
     *
     * @return activity_info - 活动信息
     */
    public String getActivityInfo() {
        return activityInfo;
    }

    /**
     * 设置活动信息
     *
     * @param activityInfo 活动信息
     */
    public void setActivityInfo(String activityInfo) {
        this.activityInfo = activityInfo;
    }

    /**
     * 获取申请状态0-正常订单商品 1申请中  2退款成功  3退款失败
     *
     * @return apply_state - 申请状态0-正常订单商品 1申请中  2退款成功  3退款失败
     */
    public Integer getApplyState() {
        return applyState;
    }

    /**
     * 设置申请状态0-正常订单商品 1申请中  2退款成功  3退款失败
     *
     * @param applyState 申请状态0-正常订单商品 1申请中  2退款成功  3退款失败
     */
    public void setApplyState(Integer applyState) {
        this.applyState = applyState;
    }

    /**
     * 获取商品退款次数
     *
     * @return number - 商品退款次数
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * 设置商品退款次数
     *
     * @param number 商品退款次数
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * 获取交易状态
     * 1.申请中
     * 2.卖家同意退货退款
     * 3.卖家已拒绝
     * 4.退款关闭
     * 5.退款完
     *
     * @return refuse_state - 交易状态
     * 1.申请中
     * 2.卖家同意退货退款
     * 3.卖家已拒绝
     * 4.退款关闭
     * 5.退款完
     */
    public Integer getRefuseState() {
        return refuseState;
    }

    /**
     * 设置交易状态
     * 1.申请中
     * 2.卖家同意退货退款
     * 3.卖家已拒绝
     * 4.退款关闭
     * 5.退款完
     *
     * @param refuseState 交易状态
     *                    1.申请中
     *                    2.卖家同意退货退款
     *                    3.卖家已拒绝
     *                    4.退款关闭
     *                    5.退款完
     */
    public void setRefuseState(Integer refuseState) {
        this.refuseState = refuseState;
    }

    public void setWlprice(BigDecimal wlPrice) {
        this.wlPrice = wlPrice;
    }

    public BigDecimal getWlPrice() {
        return wlPrice;
    }
}