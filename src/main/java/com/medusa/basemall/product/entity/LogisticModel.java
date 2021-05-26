package com.medusa.basemall.product.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_logistic_model")
public class LogisticModel {
    /**
     * 物流配模板送ID
     */
    @Id
    @Column(name = "logistic_model_id")
    @GeneratedValue(generator = "JDBC")
    private Integer logisticModelId;

    /**
     * 配送模板名称
     */
    @Column(name = "logistic_model_name")
    private String logisticModelName;

    /**
     * 计价方式（0-件 1-kg 2-m³）
     */
    @Column(name = "valuation_type")
    private Integer valuationType;

    /**
     * 模板开启关闭的状态
     */
    @Column(name = "turn_state")
    private Boolean turnState;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 用户模板Id
     */
    @Column(name = "appmodel_id")
    private String appmodelId;

    /**
     * 是否指定包邮
     */
    @Column(name = "free_state")
    private Boolean freeState;

    /**
     * 获取物流配模板送ID
     *
     * @return logistic_model_id - 物流配模板送ID
     */
    public Integer getLogisticModelId() {
        return logisticModelId;
    }

    /**
     * 设置物流配模板送ID
     *
     * @param logisticModelId 物流配模板送ID
     */
    public void setLogisticModelId(Integer logisticModelId) {
        this.logisticModelId = logisticModelId;
    }

    /**
     * 获取配送模板名称
     *
     * @return logistic_model_name - 配送模板名称
     */
    public String getLogisticModelName() {
        return logisticModelName;
    }

    /**
     * 设置配送模板名称
     *
     * @param logisticModelName 配送模板名称
     */
    public void setLogisticModelName(String logisticModelName) {
        this.logisticModelName = logisticModelName;
    }

    /**
     * 获取计价方式（0-件 1-kg 2-m³）
     *
     * @return valuation_type - 计价方式（0-件 1-kg 2-m³）
     */
    public Integer getValuationType() {
        return valuationType;
    }

    /**
     * 设置计价方式（0-件 1-kg 2-m³）
     *
     * @param valuationType 计价方式（0-件 1-kg 2-m³）
     */
    public void setValuationType(Integer valuationType) {
        this.valuationType = valuationType;
    }

    /**
     * 获取模板开启关闭的状态
     *
     * @return turn_state - 模板开启关闭的状态
     */
    public Boolean getTurnState() {
        return turnState;
    }

    /**
     * 设置模板开启关闭的状态
     *
     * @param turnState 模板开启关闭的状态
     */
    public void setTurnState(Boolean turnState) {
        this.turnState = turnState;
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
     * 获取是否指定包邮
     *
     * @return free_state - 是否指定包邮
     */
    public Boolean getFreeState() {
        return freeState;
    }

    /**
     * 设置是否指定包邮
     *
     * @param freeState 是否指定包邮
     */
    public void setFreeState(Boolean freeState) {
        this.freeState = freeState;
    }
}