package com.medusa.basemall.integral.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.integral.entity.Prize;
import com.medusa.basemall.integral.vo.PrizeVo;

import java.util.List;

/**
 * @author Created by wx on 2018/06/06.
 */
public interface PrizeMapper extends Mapper<Prize> {

    /***
     * 查询积分商品(可分类查询)
     *
     * @param prizeVo
     * @return List<Prize>
     */
    List<Prize> findByState(PrizeVo prizeVo);

    /***
     * 根据优惠券id查询积分商品对象
     *
     * @param couponId
     * @return Prize
     */
    Prize findByCouponId(Integer couponId);

    /***
     * 根据appmodelId查询库存警告的积分商品
     *
     * @param appmodelId
     * @return List<Prize>
     */
    List<Prize> selectUrgentPrizes(String appmodelId);

    /***
     * 根据appmodelId查询热销商品
     *
     * @param appmodelId
     * @return List<Prize>
     */
    List<Prize> selectHotProduct(String appmodelId);
}