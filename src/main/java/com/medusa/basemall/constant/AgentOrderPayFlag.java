package com.medusa.basemall.constant;

public interface AgentOrderPayFlag {

	/**
	 *
	 * 等待买家付款
	 */
	Integer WAIT_PAY = 0;

	/**
	 * 买家已付款    待发货状态
	 */
	Integer PAY_OK = 1;

	/**
	 * 卖家已发货    待收货状态
	 */
	Integer DELIVERY = 2;

	/**
	 * 交易成功
	 */
	Integer BUSINESS_OK = 3;

	/**
	 * 订单关闭
	 */
	Integer BUSINESS_CLOASE = 4;

	/**
	 * 用户超时关闭订单
	 */
	Integer USER_OVER_CLOASE = 5;

	/**
	 * 商家关闭订单
	 */
	Integer SELLER_CLOASE = 6;

}
