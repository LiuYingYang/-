package com.medusa.basemall.order.service;

import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.Service;
import com.medusa.basemall.order.entity.Order;
import com.medusa.basemall.order.entity.OrderDetail;
import com.medusa.basemall.order.entity.OrderExtend;
import com.medusa.basemall.order.entity.OrderRefound;
import com.medusa.basemall.order.vo.*;
import com.medusa.basemall.product.vo.OrderStatisticsVo;

import java.util.List;

/**
 * @author medusa
 * @date 2018/06/02
 */
public interface OrderService extends Service<Order> {


    /**
     * 订单回调接口
     *
     * @param xmlResult
     * @return
     */
    String notify(WxPayOrderNotifyResult xmlResult) throws Exception;

    /**
     * 订单确认收货
     *
     * @param orderId
     * @return
     */
    Result confirmReceipt(Order orderId);

    /**
     * 小程序查找订单
     *
     * @param wxuserId
     * @param appmodelId
     * @param orderState
     * @return
     */
    List<OrderExtend> findOrderMini(Long wxuserId, String appmodelId, Integer orderState);

    /**
     * 小程序申请退款
     *
     * @param orderRefound
     * @return
     */
    Result refoundOrderPrice(OrderRefundVo orderRefound);

    /**
     * 小程序查询团购订单
     *
     * @param wxuserId
     * @param appmodelId
     * @param orderState
     * @return
     */
    List<OrderExtend> findOrderMiniGroup(Long wxuserId, String appmodelId, Integer orderState);

    /**
     * 后台批量备注
     *
     * @param paramVo
     * @return
     */
    Result batchBackRemrk(JSONObject paramVo);

    /**
     * 小程序申请退货填写物流
     *
     * @param orderRefound
     * @return
     */
    Result updateRefoundAddress(OrderWlVo orderRefound);

    /**
     * 同意退款
     *
     * @param paramVo
     * @return
     */
    Result agreeRefoundOrder(OrderParamVo paramVo);

    /**
     * 拒绝退款
     *
     * @param paramVo
     * @return
     */
    Result refuseRefoundOrder(OrderParamVo paramVo);

    /**
     * 同意退货退款
     *
     * @param orderRefound
     * @return
     */
    Result agreeRefoundProduct(OrderRefound orderRefound);

    /**
     * 小程序撤消退款申请
     *
     * @param orderRefound
     * @return
     */
    /**
     * 后台发货
     *
     * @param jsonObject
     * @param appmodelId
     * @return
     */
    Result deliverGoods(List<UpdateOrderVo> jsonObject, String appmodelId) throws Exception;

    /**
     * 查询订单详情
     * @param appmodelId
     * @param orderId
     * @return
     */
    Result findOrderDetail(String appmodelId, Long orderId);

    /**
     * 查询售后订单
     * @param paramVo
     * @return
     */

    List<OrderExtend> findOrderManager(OrderBtQueryVo paramVo);


    Result updateOrder(UpdateOrderVo paramVo);

    Result orderPayOkSum(String appmodelId);


    Result againBuyProduct(Long orderId);


    Result refoundHistory(Long detailId);

    Result updateRefund(OrderParamVo paramVo);

    Result warnSellerSendProduct(Long orderId);

	List<OrderDetail> findRefundOrderManager(OrderBtQueryVo queryVo);

	List<OrderDetail> findOrderRefund(Long wxuserId, Long detailId, String appmodelId);

	Result repealRefund(OrderRefound orderRefound);

	Result closeOrder(String orderIds, Integer operatiPerson,String appmodelId);

	/**
	 * 支付订单
	 * @param orderPay
	 * @return
	 */
	Result payOrder(OrderPay orderPay);

	/**
	 * 生成订单
	 * @param saveOrderVo
	 * @return
	 */
	Result generateOrder(SaveOrderVo saveOrderVo);

	/**
	 * 我的订单数量标示
	 * @param wxuserId
	 * @return
	 */
	OrderStatisticsVo findMiniOrderStatistics(Long wxuserId);

	/**
	 * 查询参加活动的人数
	 * @param activityId
	 * @param activityType
	 * @param appmodelId
	 * @return
	 */
	Integer findJoinActivityNumberOfPeople(Integer activityId, Integer activityType, String appmodelId);

	/**
	 * 查询活动订单已付款的数量
	 * @param activityId
	 * @param activityType
	 * @param appmodelId
	 * @return
	 */
	Integer findActivityOrderPayOkSum(Integer activityId, Integer activityType, String appmodelId);

	/**
	 * 查询活动订单已支付的订单总额
	 * @param activityId
	 * @param activityType
	 * @param appmodelId
	 * @return
	 */
	Double findActivityOrdertotleFee(Integer activityId, Integer activityType, String appmodelId);

	/**
	 * 是否达到最大购买值
	 * @param wxuserId
	 * @param activitySeckillId
	 * @param secondkill
	 * @param productId
	 * @param maxQuantity
	 * @return
	 */
	Boolean findIfBuyMax(Long wxuserId, Integer activitySeckillId, Integer secondkill, Long productId,
			Integer maxQuantity);
}
