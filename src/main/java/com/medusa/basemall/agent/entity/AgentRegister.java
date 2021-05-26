package com.medusa.basemall.agent.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_agent_register")
public class AgentRegister {
    /**
     * 申请表id
     */
    @Id
    @Column(name = "reg_id")
	@ApiModelProperty(value ="申请表id")
    @GeneratedValue(generator = "JDBC")
    private Integer regId;

    /**
     * 用户id
     */
    @Column(name = "wxuser_id")
	@ApiModelProperty(value ="用户id")
    private Long wxuserId;

    /**
     * 申请人名称
     */
    @Column(name = "reg_name")
	@ApiModelProperty(value ="申请人名称")
    private String regName;

    /**
     * 申请人手机
     */
    @Column(name = "reg_phone")
	@ApiModelProperty(value ="申请人手机")
    private String regPhone;

    /**
     * 绑定码
     */
    @Column(name = "reg_code")
	@ApiModelProperty(value ="绑定码")
    private String regCode;

    /**
     * 是否已绑定
     */
	@ApiModelProperty(value ="是否已绑定")
    private Integer binding;

    /**
     * 申请类别   1-小程序端申请,2-后端注册时需要发送绑定码
     */
	@ApiModelProperty(value ="申请类别   1-小程序端申请,2-后端注册时需要发送绑定码")
    private Integer type;

    /**
     * 申请等级
     */
    @Column(name = "agent_grade_id")
	@ApiModelProperty(value ="申请等级id")
    private Integer agentGradeId;

    /**
     * 审核人
     */
    @Column(name = "reg_auditor")
	@ApiModelProperty(value ="审核人")
    private String regAuditor;

    /**
     * 审核状态  1-通过   0-不通过
     */
    @Column(name = "reg_state")
	@ApiModelProperty(value ="审核状态  1-通过   0-不通过")
    private Integer regState;

    /**
     * 审核时间
     */
    @Column(name = "reg_time")
	@ApiModelProperty(value ="审核时间")
    private String regTime;

    /**
     * 备注
     */
    @Column(name = "reg_remark")
	@ApiModelProperty(value ="备注")
    private String regRemark;

    /**
     * 拒绝原因
     */
    @Column(name = "refuse_remark")
	@ApiModelProperty(value ="拒绝原因")
    private String refuseRemark;

    /**
     * 模板id
     */
    @Column(name = "appmodel_id")
	@ApiModelProperty(value ="模板id")
    private String appmodelId;

    /**
     * 所在区域
     */
    @Column(name = "agent_domain")
	@ApiModelProperty(value ="所在区域")
    private String agentDomain;

    /**
     * 获取申请表id
     *
     * @return reg_id - 申请表id
     */
    public Integer getRegId() {
        return regId;
    }

    /**
     * 设置申请表id
     *
     * @param regId 申请表id
     */
    public void setRegId(Integer regId) {
        this.regId = regId;
    }

    /**
     * 获取用户id
     *
     * @return wxuser_id - 用户id
     */
    public Long getWxuserId() {
        return wxuserId;
    }

    /**
     * 设置用户id
     *
     * @param wxuserId 用户id
     */
    public void setWxuserId(Long wxuserId) {
        this.wxuserId = wxuserId;
    }

    /**
     * 获取申请人名称
     *
     * @return reg_name - 申请人名称
     */
    public String getRegName() {
        return regName;
    }

    /**
     * 设置申请人名称
     *
     * @param regName 申请人名称
     */
    public void setRegName(String regName) {
        this.regName = regName;
    }

    /**
     * 获取申请人手机
     *
     * @return reg_phone - 申请人手机
     */
    public String getRegPhone() {
        return regPhone;
    }

    /**
     * 设置申请人手机
     *
     * @param regPhone 申请人手机
     */
    public void setRegPhone(String regPhone) {
        this.regPhone = regPhone;
    }

    /**
     * 获取绑定码
     *
     * @return reg_code - 绑定码
     */
    public String getRegCode() {
        return regCode;
    }

    /**
     * 设置绑定码
     *
     * @param regCode 绑定码
     */
    public void setRegCode(String regCode) {
        this.regCode = regCode;
    }

    /**
     * 获取是否已绑定
     *
     * @return binding - 是否已绑定
     */
    public Integer getBinding() {
        return binding;
    }

    /**
     * 设置是否已绑定
     *
     * @param binding 是否已绑定
     */
    public void setBinding(Integer binding) {
        this.binding = binding;
    }

    /**
     * 获取申请类别   1-小程序端申请,2-后端注册时需要发送绑定码
     *
     * @return type - 申请类别   1-小程序端申请,2-后端注册时需要发送绑定码
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置申请类别   1-小程序端申请,2-后端注册时需要发送绑定码
     *
     * @param type 申请类别   1-小程序端申请,2-后端注册时需要发送绑定码
     */
    public void setType(Integer type) {
        this.type = type;
    }

	public Integer getAgentGradeId() {
		return agentGradeId;
	}

	public void setAgentGradeId(Integer agentGradeId) {
		this.agentGradeId = agentGradeId;
	}

	/**
     * 获取审核人
     *
     * @return reg_auditor - 审核人
     */
    public String getRegAuditor() {
        return regAuditor;
    }

    /**
     * 设置审核人
     *
     * @param regAuditor 审核人
     */
    public void setRegAuditor(String regAuditor) {
        this.regAuditor = regAuditor;
    }

    /**
     * 获取审核状态  1-通过   0-不通过
     *
     * @return reg_state - 审核状态  1-通过   0-不通过
     */
    public Integer getRegState() {
        return regState;
    }

    /**
     * 设置审核状态  1-通过   0-不通过
     *
     * @param regState 审核状态  1-通过   0-不通过
     */
    public void setRegState(Integer regState) {
        this.regState = regState;
    }

    /**
     * 获取审核时间
     *
     * @return reg_time - 审核时间
     */
    public String getRegTime() {
        return regTime;
    }

    /**
     * 设置审核时间
     *
     * @param regTime 审核时间
     */
    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    /**
     * 获取备注
     *
     * @return reg_remark - 备注
     */
    public String getRegRemark() {
        return regRemark;
    }

    /**
     * 设置备注
     *
     * @param regRemark 备注
     */
    public void setRegRemark(String regRemark) {
        this.regRemark = regRemark;
    }

    /**
     * 获取拒绝原因
     *
     * @return refuse_remark - 拒绝原因
     */
    public String getRefuseRemark() {
        return refuseRemark;
    }

    /**
     * 设置拒绝原因
     *
     * @param refuseRemark 拒绝原因
     */
    public void setRefuseRemark(String refuseRemark) {
        this.refuseRemark = refuseRemark;
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

    /**
     * 获取所在区域
     *
     * @return agent_domain - 所在区域
     */
    public String getAgentDomain() {
        return agentDomain;
    }

    /**
     * 设置所在区域
     *
     * @param agentDomain 所在区域
     */
    public void setAgentDomain(String agentDomain) {
        this.agentDomain = agentDomain;
    }
}