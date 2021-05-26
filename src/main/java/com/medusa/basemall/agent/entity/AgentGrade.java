package com.medusa.basemall.agent.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "t_agent_grade")
public class AgentGrade {
    /**
     *  等级表id
     */
    @Id
    @Column(name = "agent_grade_id")
	@ApiModelProperty(value ="等级表id")
    private Integer agentGradeId;

    /**
     * 等级名称
     */
    @Column(name = "grade_name")
	@ApiModelProperty(value ="等级名称")
    private String gradeName;

    /**
     * 等级折扣
     */
    @Column(name = "grade_discount")
	@ApiModelProperty(value ="等级折扣")
    private BigDecimal gradeDiscount;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
	@ApiModelProperty(value ="创建时间")
    private String createTime;

    /**
     * 代理等级说明
     */
    @Column(name = "grade_info")
	@ApiModelProperty(value ="代理等级说明")
    private String gradeInfo;

    /**
     * 升级条件
     */
    @Column(name = "upgrade_price")
	@ApiModelProperty(value ="升级条件")
    private BigDecimal upgradePrice;

    /**
     * 可编辑状态  1-只可编辑折扣和说明,2-全部可编辑
     */
    @Column(name = "edit_state")
	@ApiModelProperty(value =" 可编辑状态  1-只可编辑折扣和说明,2-全部可编辑 ")
    private Integer editState;

    @Column(name = "appmodel_id")
	@ApiModelProperty(value ="商家wxAppId")
    private String appmodelId;

    /**
     * 获取 等级表id
     *
     * @return agent_grade_id -  等级表id
     */
    public Integer getAgentGradeId() {
        return agentGradeId;
    }

    /**
     * 设置 等级表id
     *
     * @param agentGradeId  等级表id
     */
    public void setAgentGradeId(Integer agentGradeId) {
        this.agentGradeId = agentGradeId;
    }

    /**
     * 获取等级名称
     *
     * @return grade_name - 等级名称
     */
    public String getGradeName() {
        return gradeName;
    }

    /**
     * 设置等级名称
     *
     * @param gradeName 等级名称
     */
    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    /**
     * 获取等级折扣
     *
     * @return grade_discount - 等级折扣
     */
    public BigDecimal getGradeDiscount() {
        return gradeDiscount;
    }

    /**
     * 设置等级折扣
     *
     * @param gradeDiscount 等级折扣
     */
    public void setGradeDiscount(BigDecimal gradeDiscount) {
        this.gradeDiscount = gradeDiscount;
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
     * 获取代理等级说明
     *
     * @return grade_info - 代理等级说明
     */
    public String getGradeInfo() {
        return gradeInfo;
    }

    /**
     * 设置代理等级说明
     *
     * @param gradeInfo 代理等级说明
     */
    public void setGradeInfo(String gradeInfo) {
        this.gradeInfo = gradeInfo;
    }

    /**
     * 获取升级条件
     *
     * @return upgrade_price - 升级条件
     */
    public BigDecimal getUpgradePrice() {
        return upgradePrice;
    }

    /**
     * 设置升级条件
     *
     * @param upgradePrice 升级条件
     */
    public void setUpgradePrice(BigDecimal upgradePrice) {
        this.upgradePrice = upgradePrice;
    }

    /**
     * 获取可编辑状态  1-只可编辑折扣和说明,2-全部可编辑
     *
     * @return edit_state - 可编辑状态  1-只可编辑折扣和说明,2-全部可编辑
     */
    public Integer getEditState() {
        return editState;
    }

    /**
     * 设置可编辑状态  1-只可编辑折扣和说明,2-全部可编辑
     *
     * @param editState 可编辑状态  1-只可编辑折扣和说明,2-全部可编辑
     */
    public void setEditState(Integer editState) {
        this.editState = editState;
    }

    /**
     * @return appmodel_id
     */
    public String getAppmodelId() {
        return appmodelId;
    }

    /**
     * @param appmodelId
     */
    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }
}