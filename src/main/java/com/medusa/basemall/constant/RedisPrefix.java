package com.medusa.basemall.constant;

/**
 * redis 前缀
 * @author whh
 */
public interface RedisPrefix {


	/**
	 * 商家版本订单前缀
	 */
	String MANAGER_ORDER_VERSION = "basemall:manager:order:version:";
	/**
	 * 商家余额订单前缀
	 */
	String MANAGER_ORDER_BALANCE = "basemall:manager:order:balance";
	/**
	 * 商家版本
	 */
	String MANAGER_VERSION = "basemall:manager:version:";
	/**
	 * 用户缓存前缀
	 */
	String USER = "basemall:user:";

	/**
	 * 当天是否增加积分
	 */
	String INTEGRAL = "basemall:user:integral:";

	/**
	 * 当天是否增加积分
	 */
	String GROWTH_VALUE = "basemall:user:growthvalue:";

	/**
	 *
	 */
	String INTEGRAL_ORDER = "basemall:integral:order:";
}
