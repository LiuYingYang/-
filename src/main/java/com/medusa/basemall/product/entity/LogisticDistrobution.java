package com.medusa.basemall.product.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_logistic_distrobution")
public class LogisticDistrobution {
    /**
     * 商家配送ID
     */
    @Id
    @Column(name = "distrobution_id")
    @GeneratedValue(generator = "JDBC")
    private Integer distrobutionId;

    /**
     * 商家地址
     */
    @Column(name = "shop_address")
    private String shopAddress;

    /**
     * 起送价（顾客支付最低xx元才配送）
     */
    @Column(name = "min_pay_price")
    private Double minPayPrice;

    /**
     * 配送范围
     */
    @Column(name = "delivery_range")
    private Integer deliveryRange;

    //营业时间
    @Column(name = "operating_time")
    private String operatingTime;

    /**
     * 配送费用
     */
    @Column(name = "seller_price")
    private Double sellerPrice;

    /**
     * 开启关闭
     */
    @Column(name = "turn_state")
    private Boolean turnState;

    /**
     * 用户模板Id
     */
    @Column(name = "appmodel_id")
    private String appmodelId;

    /**
     * 配送员
     */
    @Column(name = "delivery_staffs")
    private String deliveryStaffs;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;


    public String getOperatingTime() {
        return operatingTime;
    }

    public void setOperatingTime(String operatingTime) {
        this.operatingTime = operatingTime;
    }

    /**
     * 获取商家配送ID
     *
     * @return distrobution_id - 商家配送ID
     */
    public Integer getDistrobutionId() {
        return distrobutionId;
    }

    /**
     * 设置商家配送ID
     *
     * @param distrobutionId 商家配送ID
     */
    public void setDistrobutionId(Integer distrobutionId) {
        this.distrobutionId = distrobutionId;
    }

    /**
     * 获取商家地址
     *
     * @return shop_address - 商家地址
     */
    public String getShopAddress() {
        return shopAddress;
    }

    /**
     * 设置商家地址
     *
     * @param shopAddress 商家地址
     */
    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    /**
     * 获取起送价（顾客支付最低xx元才配送）
     *
     * @return min_pay_price - 起送价（顾客支付最低xx元才配送）
     */
    public Double getMinPayPrice() {
        return minPayPrice;
    }

    /**
     * 设置起送价（顾客支付最低xx元才配送）
     *
     * @param minPayPrice 起送价（顾客支付最低xx元才配送）
     */
    public void setMinPayPrice(Double minPayPrice) {
        this.minPayPrice = minPayPrice;
    }

    /**
     * 获取配送范围
     *
     * @return delivery_range - 配送范围
     */
    public Integer getDeliveryRange() {
        return deliveryRange;
    }

    /**
     * 设置配送范围
     *
     * @param deliveryRange 配送范围
     */
    public void setDeliveryRange(Integer deliveryRange) {
        this.deliveryRange = deliveryRange;
    }

    /**
     * 获取配送费用
     *
     * @return seller_price - 配送费用
     */
    public Double getSellerPrice() {
        return sellerPrice;
    }

    /**
     * 设置配送费用
     *
     * @param sellerPrice 配送费用
     */
    public void setSellerPrice(Double sellerPrice) {
        this.sellerPrice = sellerPrice;
    }

    /**
     * 获取开启关闭
     *
     * @return turn_state - 开启关闭
     */
    public Boolean getTurnState() {
        return turnState;
    }

    /**
     * 设置开启关闭
     *
     * @param turnState 开启关闭
     */
    public void setTurnState(Boolean turnState) {
        this.turnState = turnState;
    }

    /**
     * 获取用户模板Id
     *
     * @return appmodel_id - 用户模板Id
     */
    public String getAppmodelId() {
        return appmodelId;
    }

    /**
     * 设置用户模板Id
     *
     * @param appmodelId 用户模板Id
     */
    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }

    /**
     * 获取配送员
     *
     * @return delivery_staffs - 配送员
     */
    public String getDeliveryStaffs() {
        return deliveryStaffs;
    }

    /**
     * 设置配送员
     *
     * @param deliveryStaffs 配送员
     */
    public void setDeliveryStaffs(String deliveryStaffs) {
        this.deliveryStaffs = deliveryStaffs;
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
}