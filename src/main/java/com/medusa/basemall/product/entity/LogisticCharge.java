package com.medusa.basemall.product.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_logistic_charge")
public class LogisticCharge {
    /**
     * 物流价格ID
     */
    @Id
    @Column(name = "logistic_charge_id")
    private Integer logisticChargeId;

    /**
     * 物流模板Id
     */
    @Column(name = "logistic_model_id")
    private Integer logisticModelId;

    /**
     * 物流地区
     */
    @Column(name = "charge_address")
    private String chargeAddress;

    /**
     * 物流首重
     */
    @Column(name = "first_weight")
    private Integer firstWeight;

    /**
     * 首重价格
     */
    @Column(name = "first_price")
    private Double firstPrice;

    /**
     * 物流续重
     */
    @Column(name = "next_weight")
    private Integer nextWeight;

    /**
     * 续重价格
     */
    @Column(name = "next_price")
    private Double nextPrice;

    /**
     * 默认状态
     */
    @Column(name = "default_state")
    private Boolean defaultState;

    /**
     * 模板ID
     */
    @Column(name = "appmodel_id")
    private String appmodelId;

    /**
     * 获取物流价格ID
     *
     * @return logistic_charge_id - 物流价格ID
     */
    public Integer getLogisticChargeId() {
        return logisticChargeId;
    }

    /**
     * 设置物流价格ID
     *
     * @param logisticChargeId 物流价格ID
     */
    public void setLogisticChargeId(Integer logisticChargeId) {
        this.logisticChargeId = logisticChargeId;
    }

    /**
     * 获取物流模板Id
     *
     * @return logistic_model_id - 物流模板Id
     */
    public Integer getLogisticModelId() {
        return logisticModelId;
    }

    /**
     * 设置物流模板Id
     *
     * @param logisticModelId 物流模板Id
     */
    public void setLogisticModelId(Integer logisticModelId) {
        this.logisticModelId = logisticModelId;
    }

    /**
     * 获取物流地区
     *
     * @return charge_address - 物流地区
     */
    public String getChargeAddress() {
        return chargeAddress;
    }

    /**
     * 设置物流地区
     *
     * @param chargeAddress 物流地区
     */
    public void setChargeAddress(String chargeAddress) {
        this.chargeAddress = chargeAddress;
    }

    /**
     * 获取物流首重
     *
     * @return first_weight - 物流首重
     */
    public Integer getFirstWeight() {
        return firstWeight;
    }

    /**
     * 设置物流首重
     *
     * @param firstWeight 物流首重
     */
    public void setFirstWeight(Integer firstWeight) {
        this.firstWeight = firstWeight;
    }

    /**
     * 获取首重价格
     *
     * @return first_price - 首重价格
     */
    public Double getFirstPrice() {
        return firstPrice;
    }

    /**
     * 设置首重价格
     *
     * @param firstPrice 首重价格
     */
    public void setFirstPrice(Double firstPrice) {
        this.firstPrice = firstPrice;
    }

    /**
     * 获取物流续重
     *
     * @return next_weight - 物流续重
     */
    public Integer getNextWeight() {
        return nextWeight;
    }

    /**
     * 设置物流续重
     *
     * @param nextWeight 物流续重
     */
    public void setNextWeight(Integer nextWeight) {
        this.nextWeight = nextWeight;
    }

    /**
     * 获取续重价格
     *
     * @return next_price - 续重价格
     */
    public Double getNextPrice() {
        return nextPrice;
    }

    /**
     * 设置续重价格
     *
     * @param nextPrice 续重价格
     */
    public void setNextPrice(Double nextPrice) {
        this.nextPrice = nextPrice;
    }

    /**
     * 获取默认状态
     *
     * @return default_state - 默认状态
     */
    public Boolean getDefaultState() {
        return defaultState;
    }

    /**
     * 设置默认状态
     *
     * @param defaultState 默认状态
     */
    public void setDefaultState(Boolean defaultState) {
        this.defaultState = defaultState;
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
}