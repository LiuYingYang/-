package com.medusa.basemall.order.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @author whh
 */
@Data
public class BalanceDetail {

    @Id
    @ApiModelProperty(value = "余额详情id"  )
    private String balanceDetailId;

    /**
     * 余额
     */
    @ApiModelProperty(value = "余额"  )
    private String balance;

    /**
	 * 余额增减状态
	 * 0-减余额  1-加余额
	 */
    @ApiModelProperty(value = "余额增减状态 0-减余额  1-加余额")
    private Integer balanceType;


    /**
     *余额增减类型
     * 1-购买商品,2-微信充值,3-商品退款
     */
    @ApiModelProperty(value = "余额增减类型   1-购买商品,2-微信充值,3-商品退款成功")
    private Integer type;

    /**
     * 充值活动信息
     */
    @ApiModelProperty(value = "充值活动信息" )
    private String rechargeActiveInfo;

    /**
     * 订单信息
     */
    @ApiModelProperty(value = "订单购买的商品数量",dataType = "itneger")
    private Integer productSize;

    @ApiModelProperty(value = "创建时间" )
    private String createTime;

    @ApiModelProperty(value = "会员id"  )
    private Long memberId;

	@ApiModelProperty(value = "订单id"  )
	private Long orderId;


}
