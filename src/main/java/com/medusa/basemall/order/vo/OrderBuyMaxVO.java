package com.medusa.basemall.order.vo;

import com.medusa.basemall.order.entity.Order;
import com.medusa.basemall.order.entity.OrderDetail;
import lombok.Data;

import java.util.List;

@Data
public class OrderBuyMaxVO extends Order {

	private List<OrderDetail> orderDetails;
}
