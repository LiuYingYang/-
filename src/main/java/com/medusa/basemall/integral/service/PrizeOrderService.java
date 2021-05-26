package com.medusa.basemall.integral.service;

import com.github.pagehelper.PageInfo;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.Service;
import com.medusa.basemall.integral.entity.PrizeOrder;
import com.medusa.basemall.integral.vo.PrizeOrderListVO;
import com.medusa.basemall.integral.vo.PrizeOrderVo;
import com.medusa.basemall.integral.vo.SavePrizeOrderVO;

import java.util.List;

/**
 * @author Created by wx on 2018/06/06.
 */
public interface PrizeOrderService extends Service<PrizeOrder> {

    /***
     * 查询积分订单(可根据买家昵称查询)
     *
     * @param prizeOrderVo
     * @return List<PrizeOrder>
     */
    List<PrizeOrderListVO> findByAppmodelId(PrizeOrderVo prizeOrderVo);

    /**
     * 支付回调函数
     *
     * @param xmlResult
     * @return String
     * @throws Exception
     */
    String notify(String xmlResult) throws Exception;

    /***
     * 添加积分订单积分商品为优惠券
     *
     * @param prizeOrder
     * @return Result
     */
    Result saveCouponOrder(SavePrizeOrderVO prizeOrder);

    /***
     * 添加积分订单积分商品为实物
     *
     * @param prizeOrder
     * @return Result
     */
    Result saveProductOrder(SavePrizeOrderVO prizeOrder);

    /***
     * 根据appmodelId分页查询交易记录
     *
     * @param pageNum
     * @param pageSize
     * @param appmodelId
     * @return PageInfo
     */
    PageInfo selectPrizeOrder(Integer pageNum, Integer pageSize, String appmodelId);
}
