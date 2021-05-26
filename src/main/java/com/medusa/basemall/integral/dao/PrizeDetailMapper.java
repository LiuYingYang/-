package com.medusa.basemall.integral.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.integral.entity.PrizeDetail;
import com.medusa.basemall.integral.vo.PrizeDetailVo;
import com.medusa.basemall.user.entity.Wxuser;

import java.util.List;
import java.util.Map;

/**
 * @author Created by wx on 2018/06/06.
 */
public interface PrizeDetailMapper extends Mapper<PrizeDetail> {

    /***
     * 用户查看积分明细
     *
     * @param prizeDetailVo
     * @return List<PrizeDetail>
     */
    List<PrizeDetail> seletePrizeDetailByWxuserId(PrizeDetailVo prizeDetailVo);

    /***
     * 根据appmodelId查询购买商品获取积分明细
     *
     * @param appmodelId
     * @return List<PrizeDetail>
     */
    List<PrizeDetail> selectByAppmodelId(String appmodelId);

    /***
     * 查询用户获取积分明细
     *
     * @param wxuser
     * @return List<PrizeDetail>
     */
    List<PrizeDetail> selectByWxuserId(Wxuser wxuser);

    /***
     * 更新用户获取积分时间
     *
     * @param map
     * @return void
     */
    void updateCreatTime(Map<String,Object> map);
}