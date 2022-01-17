package com.medusa.gruul.order.api.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 生成订单失败消息体
 *
 * @author alan
 * @date 2019/10/6 14:08
 */
@Data
public class OrderFailMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String userId;


    public OrderFailMessage stockFail(String userId) {
        OrderFailMessage failMessage=new OrderFailMessage();
        failMessage.setUserId(userId);
        return failMessage;
    }
}
