package com.medusa.basemall.product.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_logistic_free")
public class LogisticFree {
    /**
     * 包邮ID
     */
    @Id
    @Column(name = "free_id")
    private Integer freeId;

    /**
     * 包邮地区
     */
    @Column(name = "free_address")
    private String freeAddress;

    /**
     * 包邮类型（0件数,1金额,2件数+金额）
     */
    @Column(name = "unit_type")
    private Integer unitType;

    /**
     * 包邮条件-价钱（满100包邮）
     */
    @Column(name = "max_price")
    private Double maxPrice;

    /**
     * 物流模板ID
     */
    @Column(name = "logistic_model_id")
    private Integer logisticModelId;

    /**
     * 包邮条件-数量（1件）
     */
    @Column(name = "condition_ship")
    private Integer conditionShip;

    /**
     * 用户模板ID
     */
    @Column(name = "appmodel_id")
    private String appmodelId;

    /**
     * 获取包邮ID
     *
     * @return free_id - 包邮ID
     */
    public Integer getFreeId() {
        return freeId;
    }

    /**
     * 设置包邮ID
     *
     * @param freeId 包邮ID
     */
    public void setFreeId(Integer freeId) {
        this.freeId = freeId;
    }

    /**
     * 获取包邮地区
     *
     * @return free_address - 包邮地区
     */
    public String getFreeAddress() {
        return freeAddress;
    }

    /**
     * 设置包邮地区
     *
     * @param freeAddress 包邮地区
     */
    public void setFreeAddress(String freeAddress) {
        this.freeAddress = freeAddress;
    }

    /**
     * 获取包邮类型（0件数,1金额,2件数+金额）
     *
     * @return unit_type - 包邮类型（0件数,1金额,2件数+金额）
     */
    public Integer getUnitType() {
        return unitType;
    }

    /**
     * 设置包邮类型（0件数,1金额,2件数+金额）
     *
     * @param unitType 包邮类型（0件数,1金额,2件数+金额）
     */
    public void setUnitType(Integer unitType) {
        this.unitType = unitType;
    }

    /**
     * 获取包邮条件-价钱（满100包邮）
     *
     * @return max_price - 包邮条件-价钱（满100包邮）
     */
    public Double getMaxPrice() {
        return maxPrice;
    }

    /**
     * 设置包邮条件-价钱（满100包邮）
     *
     * @param maxPrice 包邮条件-价钱（满100包邮）
     */
    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    /**
     * 获取物流模板ID
     *
     * @return logistic_model_id - 物流模板ID
     */
    public Integer getLogisticModelId() {
        return logisticModelId;
    }

    /**
     * 设置物流模板ID
     *
     * @param logisticModelId 物流模板ID
     */
    public void setLogisticModelId(Integer logisticModelId) {
        this.logisticModelId = logisticModelId;
    }

    /**
     * 获取包邮条件-数量（1件）
     *
     * @return condition_ship - 包邮条件-数量（1件）
     */
    public Integer getConditionShip() {
        return conditionShip;
    }

    /**
     * 设置包邮条件-数量（1件）
     *
     * @param conditionShip 包邮条件-数量（1件）
     */
    public void setConditionShip(Integer conditionShip) {
        this.conditionShip = conditionShip;
    }

    /**
     * 获取用户模板ID
     *
     * @return appmodel_id - 用户模板ID
     */
    public String getAppmodelId() {
        return appmodelId;
    }

    /**
     * 设置用户模板ID
     *
     * @param appmodelId 用户模板ID
     */
    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }
}