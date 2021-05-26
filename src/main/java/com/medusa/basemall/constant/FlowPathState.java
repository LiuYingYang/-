package com.medusa.basemall.constant;

/**
 * 售后订单申请流程状态
 *
 * @author whh
 */
public interface FlowPathState {

    /**
     * 0-用户申请退款
     */
    Integer APPLYFOR_IN = 0;

    /**
     * 1-退款订单类型为仅退款,卖家超时未处理,自动退款
     */
    Integer OVER_TIME = 1;

    /**
     * 2-订单属于待发货状态,卖家拒绝,订单发货
     */
    Integer SEMD_PRODUCT = 2;

    /**
     * 3-同意退款退货
     */
    Integer AGREE_PRICE_RODUCT = 3;

    /**
     * 4-退款订单类型为退款退货,卖家超时未处理,自动填写物流
     */
    Integer SELLER_OVER_TIME = 4;

    /**
     * 5-用户填写物流
     */
    Integer USER_WRITE_EXPRESS = 5;

    /**
     * 6-买家物流填写超时,退款关闭
     */
    Integer WAIT_OVER_TIME = 6;

    /**
     * 7-商家确认收货并退款
     */
    Integer SELLER_CONFIRM = 7;

    /**
     * 8-拒绝退款
     */
    Integer REFUSE_PRICE = 8;

    /**
     * 9-拒绝退货退款
     */
    Integer REFUSE_PRICE_PRODUCT = 9;

    /**
     * 10-用户撤消申请
     */
    Integer USER_CANCEL_APPLYFOR = 10;

    /**
     * 11-卖家拒绝,用户超时未处理
     */
    Integer USER_OVER_TIEM = 11;

    /**
     * 12-同意退款
     */
    Integer AGREE_PRICE = 12;

    /**
     * 13-商家超时未确认收货,自动退款
     */
    Integer SELLER_OVER_AUTO_REFUND = 13;

}
