package com.medusa.basemall.agent.vo;

import io.swagger.annotations.ApiModelProperty;

public class RegisterAgentVo {


	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id",required = true)
	private Long wxuserid;
	/**
	 * 申请人手机号
	 */
	@ApiModelProperty(value = "申请人手机号",required = true)
	private String regPhone;
	/**
	 * 申请人姓名
	 */
	@ApiModelProperty(value = "申请人姓名",required = true)
	private String regName;

	/**
	 * 验证码
	 */
	@ApiModelProperty(value = "验证码",required = false)
	private String regCode;

	/**
	 * 申请类别    1-前端  2-后端
	 */
	@ApiModelProperty(value = "申请类别 1-前端  2-后端  type=1 必须带验证码不能为空       type=2 的时候后台发送绑定码 ",required = true)
	private Integer type;

	/**
	 * 申请等级
	 */
	@ApiModelProperty(value = "申请等级 ",required = false)
	private Integer agentGrade;

	/**
	 * 所在区域
	 */
	@ApiModelProperty(value = "所在区域   怎么存怎么取  字符串",required = true)
	private String agentDomain;

	/**
	 * 模板id
	 */
	@ApiModelProperty(value = "模板id",required = true)
	private String appmodelId;


	public Long getWxuserid() {
		return wxuserid;
	}

	public void setWxuserid(Long wxuserid) {
		this.wxuserid = wxuserid;
	}

	public String getRegPhone() {
		return regPhone;
	}

	public void setRegPhone(String regPhone) {
		this.regPhone = regPhone;
	}

	public String getRegName() {
		return regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}

	public String getRegCode() {
		return regCode;
	}

	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getAgentGrade() {
		return agentGrade;
	}

	public void setAgentGrade(Integer agentGrade) {
		this.agentGrade = agentGrade;
	}

	public String getAgentDomain() {
		return agentDomain;
	}

	public void setAgentDomain(String agentDomain) {
		this.agentDomain = agentDomain;
	}

	public String getAppmodelId() {
		return appmodelId;
	}

	public void setAppmodelId(String appmodelId) {
		this.appmodelId = appmodelId;
	}
}
