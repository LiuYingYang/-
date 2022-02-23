package com.medusa.gruul.afs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medusa.gruul.afs.api.entity.AfsOrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 售后申请详情 Mapper 接口
 * </p>
 *
 * @author alan
 * @since 2020-08-21
 */
public interface AfsOrderItemMapper extends BaseMapper<AfsOrderItem> {



    /**
     * 根据原始订单ID查询换货单的ID
     *
     * @param orderId the order id
     * @return the list
     */
    List<Long> selectExchangeOrderIdsByOriginalOrderId(@Param(value = "orderId") Long orderId);

    /**
     *  获取该订单商品售后次数
     * @param orderId orderId
     * @return  List<Map<String,String>>
     */
    List<Map<String,String>> userApplyItem(@Param("orderId") Long orderId);
}
                                            