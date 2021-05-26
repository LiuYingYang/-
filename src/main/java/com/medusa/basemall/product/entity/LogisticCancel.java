package com.medusa.basemall.product.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_logistic_cancel")
public class LogisticCancel {
    /**
     * 退货地址ID
     */
    @Id
    @Column(name = "logistic_cancel_id")
    private Integer logisticCancelId;

    /**
     * 联系人
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮编
     */
    @Column(name = "post_code")
    private String postCode;

    /**
     * 地址
     */
    @Column(name = "location_json")
    private String locationJson;

    /**
     * 是否默认
     */
    @Column(name = "default_state")
    private Boolean defaultState;

    /**
     * 模板ID
     */
    @Column(name = "appmodel_id")
    private String appmodelId;

    /**
     * 获取退货地址ID
     *
     * @return logistic_cancel_id - 退货地址ID
     */
    public Integer getLogisticCancelId() {
        return logisticCancelId;
    }

    /**
     * 设置退货地址ID
     *
     * @param logisticCancelId 退货地址ID
     */
    public void setLogisticCancelId(Integer logisticCancelId) {
        this.logisticCancelId = logisticCancelId;
    }

    /**
     * 获取联系人
     *
     * @return user_name - 联系人
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置联系人
     *
     * @param userName 联系人
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取联系电话
     *
     * @return phone - 联系电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置联系电话
     *
     * @param phone 联系电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取邮编
     *
     * @return post_code - 邮编
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * 设置邮编
     *
     * @param postCode 邮编
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * 获取地址
     *
     * @return location_json - 地址
     */
    public String getLocationJson() {
        return locationJson;
    }

    /**
     * 设置地址
     *
     * @param locationJson 地址
     */
    public void setLocationJson(String locationJson) {
        this.locationJson = locationJson;
    }

    /**
     * 获取是否默认
     *
     * @return default_state - 是否默认
     */
    public Boolean getDefaultState() {
        return defaultState;
    }

    /**
     * 设置是否默认
     *
     * @param defaultState 是否默认
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