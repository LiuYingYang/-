package com.medusa.basemall.integral.service;

import com.medusa.basemall.core.Service;
import com.medusa.basemall.integral.entity.Prize;
import com.medusa.basemall.integral.vo.PrizeParamVO;
import com.medusa.basemall.integral.vo.PrizeVo;

import java.util.List;

/**
 * @author Created by wx on 2018/06/06.
 */
public interface PrizeService extends Service<Prize> {

    /***
     *  查询积分商品(可分类查询)
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

	Prize findByAppmodelIdAndPrizeId(String appmodelId, Integer prizeId);

	int updateCouponOrproduct(PrizeParamVO prizeParamVO);

}
