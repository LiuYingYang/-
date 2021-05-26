package com.medusa.basemall.product.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Table(name = "t_specification_class")
public class SpecificationClass {
    /**
     * 编号
     */
    @Id
    @Column(name = "specification_class_id")
    @GeneratedValue(generator = "JDBC")
    private Integer specificationClassId;

    /**
     * 规格分类名称
     */
    @NotBlank(message = "规格分类名称不能为空")
    private String name;

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
     * 删除标志（0删除，	1不删除）
     */
    @Column(name = "delete_state")
    private Boolean deleteState;

    /**
     * 获取编号
     *
     * @return specification_class_id - 编号
     */
    public Integer getSpecificationClassId() {
        return specificationClassId;
    }

    /**
     * 设置编号
     *
     * @param specificationClassId 编号
     */
    public void setSpecificationClassId(Integer specificationClassId) {
        this.specificationClassId = specificationClassId;
    }

    /**
     * 获取规格分类名称
     *
     * @return name - 规格分类名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置规格分类名称
     *
     * @param name 规格分类名称
     */
    public void setName(String name) {
        this.name = name;
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

    /**
     * 获取删除标志（0删除，	1不删除）
     *
     * @return delete_state - 删除标志（0删除，	1不删除）
     */
    public Boolean getDeleteState() {
        return deleteState;
    }

    /**
     * 设置删除标志（0删除，	1不删除）
     *
     * @param deleteState 删除标志（0删除，	1不删除）
     */
    public void setDeleteState(Boolean deleteState) {
        this.deleteState = deleteState;
    }
}