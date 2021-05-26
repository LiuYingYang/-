package com.medusa.basemall.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 后台查询参数对象
 *
 * @author whh
 */
@Data
public class OrderBtQueryVo {

	@ApiModelProperty(required = true, value = "模板ID")
	private String appmodelId;
	@ApiModelProperty(required = true, dataType = "Integer", value = "查询类型 0--所有订单，1--近三个月订单，2--待付款 3--已付款 ，4--已发货，5--成功，6--已关闭 条件搜索时默认0")
	private Integer orderState;
	@ApiModelProperty(value = "页数", required = true)
	private Integer pageNum;
	@ApiModelProperty(value = "条数", required = true)
	private Integer pageSize;
	@ApiModelProperty(value = "商品名称")
	private String productName;
	@ApiModelProperty(value = "收货人姓名")
	private String userName;
	@ApiModelProperty(value = "卖家昵称")
	private String nickName;
	@ApiModelProperty(value = "收货人手机号")
	private String phone;
	@ApiModelProperty(value = "订单号")
	private String outTradeNum;
	@ApiModelProperty(value = "物流单号")
	private String wlNum;
	@ApiModelProperty(value = "付款时间 \"2017-08-02,2018-05-23\"")
	private String payTime;
	@ApiModelProperty(value = "该查询只有在查看详情的时候才带  pageNum pageSize 为0")
	private Long detailId;
}
