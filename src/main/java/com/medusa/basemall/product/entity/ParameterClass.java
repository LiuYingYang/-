package com.medusa.basemall.product.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Table(name = "t_parameter_class")
public class ParameterClass {
    /**
     * 总分类编号
     */
    @Id
    @Column(name = "param_class_id")
    @GeneratedValue(generator = "JDBC")
    private Integer paramClassId;

    /**
     * 类别名称
     */
    @Column(name = "param_class_name")
    @NotBlank(message = "类别名称不能为空")
    private String paramClassName;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private String createTime;

    /**
     * 模板ID
     */
    @Column(name = "appmodel_id")
    private String appmodelId;

    /**
     * 获取总分类编号
     *
     * @return param_class_id - 总分类编号
     */
    public Integer getParamClassId() {
        return paramClassId;
    }

    /**
     * 设置总分类编号
     *
     * @param paramClassId 总分类编号
     */
    public void setParamClassId(Integer paramClassId) {
        this.paramClassId = paramClassId;
    }

    /**
     * 获取类别名称
     *
     * @return param_class_name - 类别名称
     */
    public String getParamClassName() {
        return paramClassName;
    }

    /**
     * 设置类别名称
     *
     * @param paramClassName 类别名称
     */
    public void setParamClassName(String paramClassName) {
        this.paramClassName = paramClassName;
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