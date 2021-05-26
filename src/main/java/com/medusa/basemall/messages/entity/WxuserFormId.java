package com.medusa.basemall.messages.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * formId实体用于发小程序模板消息
 *
 * @author Created by wx on 2018/08/09.
 */
@Table(name = "t_wxuser_formid")
public class WxuserFormId {

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "user_form_id")
    private Integer userFormId;

    @Column(name = "open_id")
    private String openId;

    @Column(name = "form_value")
    private String formValue;

    @Column(name = "create_time")
    private String createTime;


    /**
     * 获取编号
     *
     * @return user_form_id - 编号
     */
    public Integer getUserFormId() {
        return userFormId;
    }

    /**
     * 设置编号
     *
     * @param userFormId 编号
     */
    public void setUserFormId(Integer userFormId) {
        this.userFormId = userFormId;
    }


    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * 获取formId
     *
     * @return form_value - formId
     */
    public String getFormValue() {
        return formValue;
    }

    /**
     * 设置formId
     *
     * @param formValue formId
     */
    public void setFormValue(String formValue) {
        this.formValue = formValue;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "WxuserFormId{" +
                "userFormId=" + userFormId +
                ", openId='" + openId + '\'' +
                ", formValue='" + formValue + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
