package com.medusa.basemall.agent.vo;

import io.swagger.annotations.ApiModelProperty;


public class BackstageOrderListVo extends MiniOrderListVo {

	@ApiModelProperty(value = "微信昵称")
	private String nikeName;

	public String getNikeName() {
		return nikeName;
	}

	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}
}
