package com.medusa.basemall.constant;

public interface Url {

	/**
	 * 订单支付回调
	 */
	String ORDER_NOTIFY = "https://www.superprism.cn/basemall/Order/v1/Notify";

	/**
	 * 积分订单回调
	 */
	String PRICZE_NOTIFY = "https://www.superprism.cn/basemall/prize/order/v1/Notify";

	/**
	 * 版本续费/购买/升级回调
	 */
	String MANAGER_VERSION_NOTIFY = "https://www.superprism.cn/basemall/manager/v1/version/continuation/fee/notify";

	/**
	 * 版本续费/购买/升级回调
	 */
	String MANAGER_BALANCE_NOTIFY = "https://www.superprism.cn/basemall/manager/v1/top/up/balance/notify";

	/**
	 *获取标准商城模板消息id
	 */
	String GET_TEMPLATID_URL = "https://www.superprism.cn/medusaplatform/rest/mini/templateid";

	/**
	 * 代理订单下单回调
	 */
	String AGENT_ORDER_NOTIFY = "https://www.superprism.cn/basemall/agent/purchase/order/v1/notify";

	/**
	 * 会员余额充值
	 */
	String MEMBER_RECHARGE_URL = "https://www.superprism.cn/basemall/member/recharge/record/v1/payRechargeActiveNotify";

	/**
	 *获取商家关注服务号之后的openid
	 */
	String GET_FWH_OPENID = "http://127.0.0.1:8080/medusaplatform/UserInfo/getUserOpenId?appmodelId=APPMODELID";

	/**
	 *小程序模板消息转跳页面
	 */
	String MINI_TEMPLATE_MESSAGE_PAGE = "/pages/login/login?url=URL&share=true&params=JSON";
}
