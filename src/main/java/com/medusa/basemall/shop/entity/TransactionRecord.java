package com.medusa.basemall.shop.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 存储交易记录
 * @author whh
 */
@Data
@Document(collection = "TransactionRecord")
public class TransactionRecord {

	@Id
	@ApiModelProperty(hidden = true, value = "id")
	private String transactionRecordId;
	@ApiModelProperty(hidden = true, value = "标示,存入交易记录是区别是购买小程序还是购买增值服务  miniApp小程序  extendService增值服务 balance余额充值")
	public String marking;
	@ApiModelProperty(hidden = true, value = "微信支付的productId,当前项目指的是标准商城")
	public String productId;
	@ApiModelProperty(hidden = true, value = "创建时间")
	private Date createTime;
	@ApiModelProperty(hidden = true, value = "订单号")
	private String outTradeNo;
	@ApiModelProperty(hidden = true, value = "支付状态 0-待支付,1-已支付")
	private Integer payState;
	@ApiModelProperty(hidden = true, value = "空间大小 256 512  1024")
	private Integer roomSize;
	@ApiModelProperty(value = "支付价格/充值金额")
	private Double payFee;
	@ApiModelProperty(value = "商家wxAppId")
	private String appmodelId;
	@ApiModelProperty(value = "支付方式 1微信支付 2余额支付")
	private Integer payMethod;
	@ApiModelProperty(value = "余额抵扣")
	private Double deduction;
	@ApiModelProperty(value = "购买类型 1-续费,2-版本更换 3-新开店铺")
	private Integer type;
}
