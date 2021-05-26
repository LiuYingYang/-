package com.medusa.basemall.constant;

public interface ActiviMqQueueName {

	/**
	 *服务号模板消息
	 */
	String ORDER_FWH_MESSAGE = "Basemall_Order_Fwh_Message";

	/**
	 *商品状态改变操作
	 */
	String PRODUCT_SHELFSTATE_CHANGE = "Basemall_productShelfstateChange";

	/**
	 *订单关闭库存归还
	 */
	String ORDER_CLOSE_STOCK_RETURN = "Basemall_OrderCloseStockReturn";

	/**
	 *订单未付款关闭
	 */
	String ORDER_CLOSE = "Basemall_OrderClose";

	/**
	 *发送模板消息
	 */
	String ORDER_MINIPROGRAM_TEMPLATE_MESSAGE = "Basemall_OrderMiniProgramTemplateMessage";

	/**
	 *平台创建店铺初始化信息
	 */
	String MANAGER_CREATE = "Basemall_ManagerCreate";

	/**
	 *更新小程序信息
	 */
	String MANAGER_INFO_UPDATE = "Basemall_ManagerInfoUpdate";

	/**
	 * 退款订单
	 */
	String REFUND_ORDER = "Basemall_RefundOrder";
	/**
	 * 同意退货之后用户5天之内未填写地址
	 */
	String AGREE_REFOUND_PRODUCT = "Basemall_AgreeRefoundProduct";
	/**
	 * 卖家发货之后买家20天内未确认收货自动变为交易成功订单
	 */
	String CONFIRM_RECEIPT = "Basemall_ConfirmReceipt";
	/**
	 * 拒绝退款/退货
	 */
	String CLOSE_ORDER_REFUND = "Basemall_CloseOrderRefund";
	/**
	 * 买家物流填写完成后,20天内卖家无任何操作,自动退款给买家;
	 */
	String BUYERS_DELIVERY = "Basemall_BuyersDelivery";
	/**
	 * 买家物流填写完成后,19天内买家无操作,提醒商家24小时之后将退款
	 */
	String BUYERS_DELIVERY_AUTO_REFUND_REMIND = "Basemall_Buyers_Delivery_Auto_Refund_Remind";

	/**
	 *生成订单48小时内未支付,则关闭订单  (代理订单)
	 */
	String AGENT_PURCHASE_ORDER = "Basemall_AgentPurchaseOrder";
	
	/**
	 * 发货之后15天内未确认收货则自动确认货(代理订单)
	 */
	String AGENT_PURCHASE_ORDER_CONFIRM_RECEIPT = "Basemall_AgentPurchaseOrderConfirmReceipt";

	/**
	 * 团购活动时间限制队列
	 */
	String ACTIVITY_GROUP_START = "Basemall_ActivityGroupStart";
	String ACTIVITY_GROUP_END = "Basemall_ActivityGroupEnd";
	/**
	 *成团失败
	 */
	String GROUP_END = "Basemall_GroupEnd";


	/**
	 * 特价活动时间限制
	 */
	String ACTIVITY_SPECIAL_START = "Basemall_ActivitySpecialStart";
	String ACTIVITY_SPECIAL_END = "Basemall_ActivitySpecialEnd";

	/**
	 * 秒杀活动时间限制
	 */
	String ACTIVITY_SECKILL_START = "Basemall_ActivitySeckillStart";
	String ACTIVITY_SECKILL_END = "Basemall_ActivitySeckillEnd";

	/**
	 * 优惠券活动时间限制
	 */
	String ACTIVITY_COUPON_START = "Basemall_ActivityCouponStart";
	String ACTIVITY_COUPON_END = "Basemall_ActivityCouponEnd";

	/**
	 *
	 */
	String PRIZE_ORDER = "Basemall_PrizeOrder";

	/**
	 * 通知中心
	 */
	String MESSAGE_NOTIFY = "Basemall_Message_Notify";
}
