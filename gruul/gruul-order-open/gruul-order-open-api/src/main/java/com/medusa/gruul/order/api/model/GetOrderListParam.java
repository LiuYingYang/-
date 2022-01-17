package com.medusa.gruul.order.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lcysike
 */
@Data
@NoArgsConstructor
public class GetOrderListParam {
    private List<Long> orderIds;

    public GetOrderListParam(List<Long> orderIds) {
        this.orderIds = orderIds;
    }
}
