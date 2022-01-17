package com.medusa.gruul.order.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 移出发货单消息体
 *
 * @author alan
 * @date 2019/10/6 14:08
 */
@Data
@NoArgsConstructor
public class RemoveSendBillOrderMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long sendBillOrderId;
    private Long orderId;


    public RemoveSendBillOrderMessage( Long orderId) {
        this.orderId = orderId;
    }
}
