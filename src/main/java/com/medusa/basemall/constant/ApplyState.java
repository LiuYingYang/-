package com.medusa.basemall.constant;

/**
 * 申请状态
 *
 * @author whh
 */
public interface ApplyState {

    /**
     *0-正常订单商品
     */
    Integer REGULAR = 0;
    /**
     *1-申请中
     */
    Integer APPLYFOR_IN = 1;
    /**
     *2-退款成功
     */
    Integer REFUND_OK = 2;
    /**
     *3-退款失败
     */
    Integer REFUND_FAIL = 3;

}
