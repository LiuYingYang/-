package com.medusa.basemall.shop.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_sms")
public class Sms {
    /**
     * 短信id
     */
    @Id
    @Column(name = "sms_id")
    private Integer smsId;

    /**
     * 手机号
     */
    @Column(name = "user_tel")
    private String userTel;

    /**
     * 验证码
     */
    @Column(name = "sms_code")
    private String smsCode;

    /**
     * 验证类型 1.会员注册获取验证码 2.代理商绑定获取验证码3.代理商申请获取验证码 4,商家同意退款验证 5.余额解冻验证,6余额提现验证
     */
    private Integer type;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private String updateTime;

    /**
     * 模板id
     */
    @Column(name = "appmodel_id")
    private String appmodelId;

    /**
     * 获取短信id
     *
     * @return sms_id - 短信id
     */
    public Integer getSmsId() {
        return smsId;
    }

    /**
     * 设置短信id
     *
     * @param smsId 短信id
     */
    public void setSmsId(Integer smsId) {
        this.smsId = smsId;
    }

    /**
     * 获取手机号
     *
     * @return user_tel - 手机号
     */
    public String getUserTel() {
        return userTel;
    }

    /**
     * 设置手机号
     *
     * @param userTel 手机号
     */
    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    /**
     * 获取验证码
     *
     * @return sms_code - 验证码
     */
    public String getSmsCode() {
        return smsCode;
    }

    /**
     * 设置验证码
     *
     * @param smsCode 验证码
     */
    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    /**
     * 获取验证类型 1.会员注册获取验证码 2.代理商绑定获取验证码3.代理商申请获取验证码
     *
     * @return type - 验证类型 1.会员注册获取验证码 2.代理商绑定获取验证码3.代理商申请获取验证码
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置验证类型 1.会员注册获取验证码 2.代理商绑定获取验证码3.代理商申请获取验证码
     *
     * @param type 验证类型 1.会员注册获取验证码 2.代理商绑定获取验证码3.代理商申请获取验证码
     */
    public void setType(Integer type) {
        this.type = type;
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
}