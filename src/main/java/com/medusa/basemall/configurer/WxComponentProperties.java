package com.medusa.basemall.configurer;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "wechat.open")
@Data
public class WxComponentProperties {
	/**
	 * 设置微信三方平台的appid
	 */
	private String componentAppId;

	/**
	 * 设置微信三方平台的app secret
	 */
	private String componentSecret;

	/**
	 * 设置微信三方平台的token
	 */
	private String componentToken;
	/**
	 * 设置微信三方平台的EncodingAESKey
	 */
	private String componentAesKey;

	/**
	 * 小程序支付文件路径
	 */
	private String keyPath;


}
