package com.medusa.basemall.agent.vo;

import io.swagger.annotations.ApiModelProperty;

public class BindAgentVo {

	@ApiModelProperty(value = "用户id")
	private Long wxuserid;

	@ApiModelProperty(value = "申请人手机号")
	private String regPhone;

	@ApiModelProperty(value = "绑定码")
	private String bingCode;

	@ApiModelProperty(value = "验证码")
	private String regCode;

	@ApiModelProperty(value = "商家wxAppId")
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

	public String getBingCode() {
		return bingCode;
	}

	public void setBingCode(String bingCode) {
		this.bingCode = bingCode;
	}

	public String getRegCode() {
		return regCode;
	}

	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}

	public String getAppmodelId() {
		return appmodelId;
	}

	public void setAppmodelId(String appmodelId) {
		this.appmodelId = appmodelId;
	}
}
