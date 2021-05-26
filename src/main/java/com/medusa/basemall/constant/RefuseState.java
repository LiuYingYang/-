package com.medusa.basemall.constant;


/**
 * 售后状态
 *订单详情表才可使用
 * @author whh
 */
public interface RefuseState {

    /**
     * 0.正常订单
     */
    Integer NORMAL_ORDER = 0;

    /**
     * 1.申请中
     */
    Integer APPLYFOR_IN = 1;

    /**
     * 2.卖家同意退货退款
     */
    Integer AGREE_PRICE_PRODUCT = 2;

    /**
     * 3.卖家已拒绝
     */
    Integer REFUSE = 3;

    /**
     * 4.退款关闭
     */
    Integer REFUND_CLOSE = 4;

    /**
     * 5.退款完成
     */
    Integer REFUND_OK = 5;
}
