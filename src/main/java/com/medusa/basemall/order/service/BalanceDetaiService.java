package com.medusa.basemall.order.service;

import com.medusa.basemall.core.Result;
import com.medusa.basemall.user.entity.MemberActiveRecharge;
import com.medusa.basemall.user.entity.MemberRechargeRecord;

/**
 *
 */
public interface BalanceDetaiService {


    /**
     * 添加购买商品余额操作记录
     *
     * @return
     */
    Integer addOrderRecord(Long orderId, Long memberId, int countSize, Double payFee);

    /**
     * 查询余额明细
     * @param memberId
     * @param pageNum
     * @param pageSize
     * @param startTime
     * @param endTime
     * @return
     */
    Result selectAll(Long memberId, Integer pageNum, Integer pageSize, String startTime, String endTime);


    Integer addRechargeActive(MemberRechargeRecord record, MemberActiveRecharge activeRecharge);

    Result delete(String balanceDetailId);

	void orderRefoundUpdate(Long orderId, String balance, Integer quantity, Long memberId);
}
