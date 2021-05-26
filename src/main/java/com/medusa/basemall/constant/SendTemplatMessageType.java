package com.medusa.basemall.constant;

public interface SendTemplatMessageType {

	/**
	 * 物流配送消息
	 */
	Integer LOGISTICS_DISPATCHING = 1001;
	/**
	 * 商家配送消息
	 */
	Integer MERCHANT_DISPATCHING = 1002;
	/**
	 * 订单改价通知
	 */
	Integer ORDER_CHANGE_PRICE = 2001;
	/**
	 * 订单关闭通知
	 */
	Integer ORDER_CLOSE = 2002;

	/**
	 * 售后订单拒绝退款
	 */
	Integer REFUSAL_OF_REFUND = 3001;

	/**
	 *  售后订单同意退货
	 */
	Integer AGREE_RETREAT_PRODUCT = 3002;
	/**
	 *  售后订单退款成功
	 */
	Integer REFUND_SUCCESS = 3003;

	/**
	 *  会员升级通知
	 */
	Integer UPDATE_NOTIFICATION = 4001;

	/**
	 *  充值成功通知
	 */
	Integer BE_RECHARGED_SUCCESSFULLY = 4002;

	/**
	 *  参团成功
	 */
	Integer JONIN_GROUP_SUCCESS = 4003;

	/**
	 *  拼团成功
	 */
	Integer GROUP_BUYING_SUCCESS = 4004;

	/**
	 *  拼团失败
	 */
	Integer GROUP_BUYING_FAIL = 4005;

	/**
	 *  积分兑换成功
	 */
	Integer INTEGRAL_EXCHANGE_SUCCESS = 5001;

	/**
	 *  积分过期
	 */
	Integer INTEGRAL_EXPIRE = 5002;


}
