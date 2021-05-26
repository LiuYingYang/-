package com.medusa.basemall.order.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.order.entity.OrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderDetailMapper extends Mapper<OrderDetail> {

    List<OrderDetail> selectByOrderId(Long orderId);

    /**
     *  小程序查询售后订单/查询订单详情
     * @param param
     * @return
     */
    List<OrderDetail> findOrderRefund(Map<String,Object> param);

    /**
     * 查询售后中的订单
     * @param map
     * @return
     */
    List<OrderDetail> findRefundIn(Map<String, Object> map);

    /**
     * 查询订单详情
     * @param detailId
     * @return
     */
    OrderDetail findByDetailId(Long detailId);

	/**
	 *
	 * @param orderDetail
	 * @return
	 */
    int findOrderRefundIn(OrderDetail orderDetail);

	Double countSaleNumber(@Param("productId") Long productId, @Param("appmodelId") String appmodelId);
}