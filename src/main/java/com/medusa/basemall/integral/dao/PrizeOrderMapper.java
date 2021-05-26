package com.medusa.basemall.integral.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.integral.entity.PrizeOrder;
import com.medusa.basemall.integral.vo.PrizeOrderListVO;
import com.medusa.basemall.integral.vo.PrizeOrderVo;
import com.medusa.basemall.integral.vo.PrizeSurveyVo;

import java.util.List;

/**
 * @author Created by wx on 2018/06/06.
 */
public interface PrizeOrderMapper extends Mapper<PrizeOrder> {

    /***
     * 根据appmodelId查询库存警告的积分商品
     *
     * @param prizeOrderVo
     * @return List<PrizeOrder>
     */
    List<PrizeOrderListVO> findByAppmodelId(PrizeOrderVo prizeOrderVo);

    /***
     * 根据兑换单号查询积分订单
     *
     * @param changeNum
     * @return PrizeOrder
     */
    PrizeOrder selectByChangeNum(String changeNum);

    /***
     * 统计今日订单数
     *
     * @param prizeSurveyVo
     * @return Integer
     */
    Integer selectCountTodayOverOrder(PrizeSurveyVo prizeSurveyVo);

    /***
     * 统计今日待发货订单数
     *
     * @param prizeSurveyVo
     * @return Integer
     */
    Integer selectCountTodayWaitOrder(PrizeSurveyVo prizeSurveyVo);

    /***
     * 统计今日优惠券订单数
     *
     * @param prizeSurveyVo
     * @return Integer
     */
    Integer selectCountTodayCouponOrder(PrizeSurveyVo prizeSurveyVo);

    /***
     * 统计今日成交额
     *
     * @param prizeSurveyVo
     * @return Integer
     */
    Integer selectSumTodayIntegral(PrizeSurveyVo prizeSurveyVo);

    /***
     * 根据appmodelId查询所有成交积分商城订单
     *
     * @param appmodelId
     * @return List<PrizeOrder>
     */
    List<PrizeOrder> selectPrizeOrder(String appmodelId);

    /***
     * 统计成交订单总数
     *
     * @param prizeSurveyVo
     * @return Integer
     */
    Integer selectCountAllOverOrder(PrizeSurveyVo prizeSurveyVo);

    /***
     * 统计实物订单总数
     *
     * @param prizeSurveyVo
     * @return Integer
     */
    Integer selectCountAllOverProductOrder(PrizeSurveyVo prizeSurveyVo);

    /***
     * 统计优惠券订单总数
     *
     * @param prizeSurveyVo
     * @return Integer
     */
    Integer selectCountAllOverCouponOrder(PrizeSurveyVo prizeSurveyVo);

    /***
     * 统计积分成交总额
     *
     * @param prizeSurveyVo
     * @return Integer
     */
    Integer selectSumAllIntegral(PrizeSurveyVo prizeSurveyVo);
}