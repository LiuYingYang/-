package com.medusa.gruul.goods.api.constant;

/**
 * 商品常量
 *
 * @author lcysike
 */
public interface GoodsConstant {

    /**
     * 16位货号最小值
     */
    Long MIN_ID = 1000000000000000L;

    /**
     * 16位货号最大值
     */
    Long MAX_ID = 9999999999999999L;

    /**
     * 一小时的毫秒数
     */
    int SECOND_OF_HOUR = 3600000;

    /**
     * 一小时的毫秒数
     */
    int HOUR_OF_DAY = 24;

    /**
     * 订单模块默认交换机
     */
    String EXCHANGE_NAME = "gruul.goods.direct";

    /**
     * 商超专区
     */
    String SUPERMARKET_MODE = "SUPERMARKET";

}
