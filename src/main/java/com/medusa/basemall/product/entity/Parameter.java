package com.medusa.basemall.product.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Table(name = "t_parameter")
public class Parameter {
    /**
     * 编号
     */
    @Id
    @Column(name = "param_id")
    private Integer paramId;

    /**
     * 参数名称
     */
    @Column(name = "param_key")
    @NotBlank(message = "参数名称不能为空")
    private String paramKey;

    /**
     * 参数值
     */
    @Column(name = "param_value")
    @NotBlank(message = "参数值不能为空")
    private String paramValue;

    /**
     * 参数分类ID
     */
    @Column(name = "param_class_id")
    private Integer paramClassId;

    /**
     * 模板ID
     */
    @Column(name = "appmodel_id")
    private String appmodelId;

    /**
     * 获取编号
     *
     * @return param_id - 编号
     */
    public Integer getParamId() {
        return paramId;
    }

    /**
     * 设置编号
     *
     * @param paramId 编号
     */
    public void setParamId(Integer paramId) {
        this.paramId = paramId;
    }

    /**
     * 获取参数名称
     *
     * @return param_key - 参数名称
     */
    public String getParamKey() {
        return paramKey;
    }

    /**
     * 设置参数名称
     *
     * @param paramKey 参数名称
     */
    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    /**
     * 获取参数值
     *
     * @return param_value - 参数值
     */
    public String getParamValue() {
        return paramValue;
    }

    /**
     * 设置参数值
     *
     * @param paramValue 参数值
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    /**
     * 获取参数分类ID
     *
     * @return param_class_id - 参数分类ID
     */
    public Integer getParamClassId() {
        return paramClassId;
    }

    /**
     * 设置参数分类ID
     *
     * @param paramClassId 参数分类ID
     */
    public void setParamClassId(Integer paramClassId) {
        this.paramClassId = paramClassId;
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