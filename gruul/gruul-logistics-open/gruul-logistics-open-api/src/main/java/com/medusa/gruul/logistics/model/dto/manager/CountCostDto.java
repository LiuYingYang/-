package com.medusa.gruul.logistics.model.dto.manager;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 订单运费信息
 * </p>
 *
 * @author lcysike
 * @since 2022-01-06
 */
@Data
public class CountCostDto {

    /**
     * 解释
     */
    private String des;

    /**
     * 运费金额 单位 :元
     */
    private BigDecimal cost;
}
