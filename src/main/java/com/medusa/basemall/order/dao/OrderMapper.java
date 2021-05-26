package com.medusa.basemall.order.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.order.entity.Order;
import com.medusa.basemall.order.entity.OrderExtend;
import com.medusa.basemall.order.vo.OrderBuyMaxVO;
import com.medusa.basemall.order.vo.OrderSurveyVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderMapper extends Mapper<Order> {

	/**
	 * 通过订单号查询订单
	 * @param outTradeNo
	 * @return
	 */
	OrderExtend findByOutTradeNo(String outTradeNo);

	/**
	 * 小程序查询订单    待发货,已发货,代付款,已完成
	 * @param order
	 * @return
	 */
	List<OrderExtend> findOrderMini(Order order);

	/**
	 * 小程序查询团购订单
	 * @param map
	 * @return
	 */
	List<OrderExtend> findOrderMiniGroup(Map<String, Object> map);

	/**
	 * 后台查询订单
	 * @param param
	 * @return
	 */
	List<OrderExtend> findOrderManager(Map<String, Object> param);

	/**
	 * 查询订单详情
	 * @param map
	 * @return
	 */
	OrderExtend findOrderDetail(Map<String, Object> map);

	/**
	 * 统计用户确认收货的订单数量
	 * @param wxuserId
	 * @return
	 */
	Integer findWxuserConfirmOrderSum(Long wxuserId);

	/**
	 * 统计用户确认收货的交易总额
	 * @param wxuserId
	 * @return
	 */
	Double findWxuserConfirmOrderTotlePrice(Long wxuserId);

	/**
	 * 查询用户最后的支付时间
	 * @param wxuserId
	 * @return
	 */
	String findWxuserLastPay(Long wxuserId);



	Integer selectCountSuccessful(OrderSurveyVo orderSurveyVo);

	Double selectSumSuccessful(OrderSurveyVo orderSurveyVo);

	Integer selectTodayWaitOrder(OrderSurveyVo orderSurveyVo);

	Integer selectTodayOverOrder(OrderSurveyVo orderSurveyVo);

	Double selectSumTodayPrice(OrderSurveyVo orderSurveyVo);

	List<OrderExtend> selectByWxuserIdAndGroupState(Map<String, Object> map);

	Integer findMiniOrderStatistics(Map<String, Object> map);

	Integer findJoinActivityNumberOfPeople(@Param("activityId") Integer activityId, @Param("activityType") Integer activityType, @Param("appmodelId") String appmodelId);

	Integer findActivityOrderPayOkSum(@Param("activityId") Integer activityId, @Param("activityType") Integer activityType, @Param("appmodelId") String appmodelId);


	Double findActivityOrdertotleFee(@Param("activityId") Integer activityId, @Param("activityType") Integer activityType, @Param("appmodelId") String appmodelId);

	List<OrderBuyMaxVO> selectIfBuyMax(
			@Param("wxuserId") Long wxuserId, @Param("activityId") Integer activityId, @Param("activityType") Integer activityType);
}