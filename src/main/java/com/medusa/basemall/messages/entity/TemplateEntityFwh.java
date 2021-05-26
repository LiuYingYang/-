package com.medusa.basemall.messages.entity;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 模板消息实体（服务号）
 *
 * @author Created by wx on 2018/08/09.
 */
public class TemplateEntityFwh {

	private String touser;

	private String template_id;

	//private String url;

	//private Map<String, String> miniprogram = new LinkedHashMap<String, String>();

	private Map<String, Object> data = new LinkedHashMap<String, Object>();
	
	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

/*	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getMiniprogram() {
		return miniprogram;
	}

	public void setMiniprogram(Map<String, String> miniprogram) {
		this.miniprogram = miniprogram;
	}*/

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public Map<String, Object> setDataProperty(String keywordName, String value, String color) {
		Map<String, String> keyword = new LinkedHashMap<String, String>();
		keyword.put("value", value);
		keyword.put("color", color);
		data.put(keywordName, keyword);
		return data;
	}

/*	public Map<String, String> setMiniProperty(String appid, String pagepath) {
		miniprogram.put("appid", appid);
		miniprogram.put("pagepath", pagepath);
		return miniprogram;
	}*/
}
