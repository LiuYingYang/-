package com.medusa.basemall.order.service;

import com.medusa.basemall.core.Service;
import com.medusa.basemall.order.entity.Order;
import com.medusa.basemall.order.entity.OrderDetail;
import com.medusa.basemall.order.vo.ProductOrderVo;

import java.util.List;

/**
 * Created by medusa on 2018/06/02.
 */
public interface OrderDetailService extends Service<OrderDetail> {


    List<OrderDetail> selectByOrderId(Long orderId);

	void saveOrderDetail(Order order, List<ProductOrderVo> productList);

	/**
	 * 查询订单中的商品数量
	 * @param orderId
	 * @return
	 */
	int findCountSize(Long orderId);
}
