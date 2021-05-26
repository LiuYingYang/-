package com.medusa.basemall.user.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_member_recharge_record")
public class MemberRechargeRecord {
    /**
     * 表id
     */
    @Id
    @Column(name = "recharge_record_id")
    @ApiModelProperty(value = "表id"  )
    private Long rechargeRecordId;

    /**
     * 订单号
     */
    @Column(name = "order_number")
    @ApiModelProperty(value = "订单号"  )
    private String orderNumber;

    /**
     * 充值方式  1.微信支付,2银行卡充值
     */
    @ApiModelProperty(value = "充值方式:1.微信支付,2银行卡充值")
    private Integer type;

    /**
     * 会员id
     */
    @Column(name = "member_id")
    @ApiModelProperty(value = "会员id"  )
    private Long memberId;

    /**
     * 充值金额
     */
    @ApiModelProperty(value = "充值金额",dataType = "Double")
    private Double price;

    /**
     * 充值活动id
     */
    @Column(name = "active_recharge_id")
    @ApiModelProperty(value = "充值活动id")
    private Integer activeRechargeId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间"  )
    private String createTime;

    /**
     * 支付时间
     */
    @Column(name = "pay_time")
    @ApiModelProperty(value = "支付时间"  )
    private String payTime;

    /**
     * 模板id
     */
    @Column(name = "appmodel_id")
    @ApiModelProperty(value = "模板id"  )
    private String appmodelId;

    /**
     * 充值状态:0未付款,1已付款
     */
    @ApiModelProperty(value = "充值状态:0未付款,1已付款")
    private Integer state;

    /**
     * 获取表id
     *
     * @return recharge_record_id - 表id
     */
    public Long getRechargeRecordId() {
        return rechargeRecordId;
    }

    /**
     * 设置表id
     *
     * @param rechargeRecordId 表id
     */
    public void setRechargeRecordId(Long rechargeRecordId) {
        this.rechargeRecordId = rechargeRecordId;
    }

    /**
     * 获取订单号
     *
     * @return order_number - 订单号
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * 设置订单号
     *
     * @param orderNumber 订单号
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * 获取充值方式  1.微信支付,2银行卡充值
     *
     * @return type - 充值方式  1.微信支付,2银行卡充值
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置充值方式  1.微信支付,2银行卡充值
     *
     * @param type 充值方式  1.微信支付,2银行卡充值
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取会员id
     *
     * @return member_id - 会员id
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * 设置会员id
     *
     * @param memberId 会员id
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取充值金额
     *
     * @return price - 充值金额
     */
    public Double getPrice() {
        return price;
    }

    /**
     * 设置充值金额
     *
     * @param price 充值金额
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * 获取充值活动id
     *
     * @return active_recharge_id - 充值活动id
     */
    public Integer getActiveRechargeId() {
        return activeRechargeId;
    }

    /**
     * 设置充值活动id
     *
     * @param activeRechargeId 充值活动id
     */
    public void setActiveRechargeId(Integer activeRechargeId) {
        this.activeRechargeId = activeRechargeId;
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
     * 获取模板id
     *
     * @return appmodel_id - 模板id
     */
    public String getAppmodelId() {
        return appmodelId;
    }

    /**
     * 设置模板id
     *
     * @param appmodelId 模板id
     */
    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }

    /**
     * 获取充值状态:0未付款,1已付款
     *
     * @return state - 充值状态:0未付款,1已付款
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置充值状态:0未付款,1已付款
     *
     * @param state 充值状态:0未付款,1已付款
     */
    public void setState(Integer state) {
        this.state = state;
    }
}