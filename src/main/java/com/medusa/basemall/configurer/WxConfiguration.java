package com.medusa.basemall.configurer;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.medusa.basemall.shop.entity.Manager;
import com.medusa.basemall.shop.service.ManagerService;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInRedisConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.api.impl.WxOpenInRedisConfigStorage;
import me.chanjar.weixin.open.api.impl.WxOpenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * @author Binary Wang
 */
@Component
@EnableConfigurationProperties({WxComponentProperties.class,WechatMpProperties.class})
public class WxConfiguration {

	@Resource
	private WxComponentProperties wxComponentProperties;

	@Resource
	private WechatMpProperties wechatMpProperties;

	@Resource
	private ManagerService managerService;

	@Autowired
	private JedisPool jedisPool;

	@Bean
	public WxOpenService getWxOpenService() {
		WxOpenInRedisConfigStorage open = new WxOpenInRedisConfigStorage(this.jedisPool);
		open.setComponentAppId(this.wxComponentProperties.getComponentAppId());
		open.setComponentAppSecret(this.wxComponentProperties.getComponentSecret());
		open.setComponentToken(this.wxComponentProperties.getComponentToken());
		open.setComponentAesKey(this.wxComponentProperties.getComponentAesKey());
		WxOpenService wxOpenService = new WxOpenServiceImpl();
		wxOpenService.setWxOpenConfigStorage(open);
		return wxOpenService;
	}

	@Bean
	public WxMpService getWxMpService() {
		WxMpService wxMpService = new WxMpServiceImpl();
		WxMpInMemoryConfigStorage configStorage = new WxMpInRedisConfigStorage(this.jedisPool);
		configStorage.setAppId(this.wechatMpProperties.getAppId());
		configStorage.setSecret(this.wechatMpProperties.getSecret());
		configStorage.setToken(this.wechatMpProperties.getToken());
		configStorage.setAesKey(this.wechatMpProperties.getAesKey());
		wxMpService.setWxMpConfigStorage(configStorage);
		return wxMpService;
	}

	public WxPayService init(String appmodelId) {
		WxPayService wxPayService = new WxPayServiceImpl();
		Manager manager = managerService.selectByAppmodelId(appmodelId);
		WxPayConfig payConfig = new WxPayConfig();
		payConfig.setAppId(manager.getAppId());
		payConfig.setMchId(manager.getMchId());
		payConfig.setMchKey(manager.getMchKey());
		payConfig.setKeyPath(this.wxComponentProperties.getKeyPath().replace("APPMODELID", manager.getAppmodelId()));
		wxPayService.setConfig(payConfig);
		return wxPayService;
	}

}
