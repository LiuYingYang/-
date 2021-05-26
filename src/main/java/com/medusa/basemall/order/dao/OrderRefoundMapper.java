package com.medusa.basemall.order.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.order.entity.OrderRefound;
import com.medusa.basemall.order.vo.OrderSurveyVo;

import java.util.List;

public interface OrderRefoundMapper extends Mapper<OrderRefound> {

    List<OrderRefound> findByDetailId(Long detailId);

    Integer selectTodayRefoundOrder(OrderSurveyVo orderSurveyVo);
}