package com.medusa.basemall.configurer;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "wechat.mp")
@Data
public class WechatMpProperties {

	/**
	 * 设置微信公众号的appid
	 */
	private String appId;

	/**
	 * 设置微信公众号的app secret
	 */
	private String secret;

	/**
	 * 设置微信公众号的token
	 */
	private String token;

	/**
	 * 设置微信公众号的EncodingAESKey
	 */
	private String aesKey;

}
