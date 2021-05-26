package com.medusa.basemall.integral.service;

import com.medusa.basemall.core.Service;
import com.medusa.basemall.integral.entity.PrizeDetail;
import com.medusa.basemall.integral.vo.PrizeDetailVo;

import java.util.List;

/**
 * @author Created by wx on 2018/06/06.
 */
public interface PrizeDetailService extends Service<PrizeDetail> {

    /***
     * 用户查看积分明细
     *
     * @param prizeDetailVo
     * @return List<PrizeDetail>
     */
    List<PrizeDetail> seletePrizeDetailByWxuserId(PrizeDetailVo prizeDetailVo);

	/**
	 *
	 * @param type  3-登录增加积分 4-分享增加积分
	 * @param appmodelId
	 * @param wxuserId
	 * @return
	 */
	Integer addIntegral(int type, String appmodelId, Long wxuserId);
}
