package com.medusa.gruul.account.api.constant;

/**
 * @author whh
 * @description 用户模块队列
 * @data: 2019/12/11
 */
public class AccountQueueNameConstant {

    /**
     * 用户积分加减队列
     */
    public static final String ACCOUNT_INTEGRAL_QUEUE_CHANGE = "gruul.account.integral.queue.change";


    /**
     * 用户订单支付队列
     */
    public static final String ACCOUNT_ORDER_PAY_OK_QUEUE_CHANGE = "gruul.account.order.pay.ok";


    /**
     * 用户订单完成队列(评价之后)
     */
    public static final String ACCOUNT_ORDER_COMPLETE_OK_QUEUE_CHANGE = "gruul.account.order.complete.ok";


    /**
     * 用户收藏
     */
    public static final String ACCOUNT_COLLECT = "gruul.account.collect";


    /**
     * 默认用户账号
     */
    public static final String ACCOUNT_DEFAULT = "gruul.account.default";





}
