package com.medusa.basemall.order.vo;

import io.swagger.annotations.ApiModelProperty;

public class RefoundRecordVo {

	@ApiModelProperty(value = "操作时间")
	private String time;
	@ApiModelProperty(value = "操作内容")
	private String content;
	@ApiModelProperty(value = "代表人")
	private String name;
	@ApiModelProperty(value = "用户头像")
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
