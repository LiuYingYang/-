package com.medusa.basemall.order.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medusa.basemall.activemq.ActiveMqClient;
import com.medusa.basemall.aop.annotation.LookTimeAspect;
import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.ScopeType;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.order.entity.Order;
import com.medusa.basemall.order.entity.OrderDetail;
import com.medusa.basemall.order.entity.OrderExtend;
import com.medusa.basemall.order.entity.OrderRefound;
import com.medusa.basemall.order.service.OrderService;
import com.medusa.basemall.order.vo.*;
import com.medusa.basemall.product.vo.OrderStatisticsVo;
import io.swagger.annotations.*;
import me.chanjar.weixin.open.api.WxOpenService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


/**
 * @author medusa
 * @date 2018/06/02
 */
@RestController
@RequestMapping("/Order")
@Api(tags = "所有接口")
@VersionManager
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private WxOpenService wxOpenService;

	@Resource
	private ActiveMqClient activeMqClient;

	@GetMapping("/v1/test")
	public Result test() {
		activeMqClient.deleteAllScheduleMessage();
		return ResultGenerator.genSuccessResult();
	}

	@PostMapping("/v1/saveOrder")
	@ApiOperation(value = "订单生成", tags = "支付接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success:会返回orderId", response = Integer.class),
			@ApiResponse(code = 99, message = "商品库存不足", response = ProductOrderVo.class, responseContainer = "list")})
	public Result generateOrder(@RequestBody SaveOrderVo saveOrderVo) {
		return orderService.generateOrder(saveOrderVo);
	}


	@PostMapping("/v1/payOrder")
	@ApiOperation(value = "订单支付", tags = "支付接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success:微信调用支付调用参数", response = Integer.class),
			@ApiResponse(code = 99, message = "订单不存在/支付金额有误/订单已支付/订单已关闭/支付失败/订单出错")})
	public Result payOrder(@RequestBody OrderPay orderPay) {
		return orderService.payOrder(orderPay);
	}

	/**
	 * 支付回调
	 * @param request
	 * @throws ServletException
	 * @throws IOException
	 */
	@PostMapping("/v1/Notify")
	@VersionManager(enable = false)
	@ApiOperation(value = "订单支付回调", tags = "回调接口")
	protected String notify(HttpServletRequest request) throws Exception {
		String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
		WxPayOrderNotifyResult payOrderNotifyResult = WxPayOrderNotifyResult.fromXML(xmlResult);
		return orderService.notify(payOrderNotifyResult);
	}

	/**
	 * 批量关闭单个或多个订单
	 * @param orderIds
	 * @return
	 */
	@DeleteMapping("/v1/closeOrder")
	@ApiOperation(value = "批量关闭单个或多个订单", tags = "删除接口")
	public Result closeOrder(@RequestParam @ApiParam(value = "订单Ids 字符串逗号分隔", required = true) String orderIds,
			@RequestParam @ApiParam(value = "操作人  1001客户操作 2001商家操作", required = true) Integer operatiPerson,
			@RequestParam String appmodelId) {
		return orderService.closeOrder(orderIds, operatiPerson,appmodelId);
	}

	/**
	 * 小程序确认收货
	 * @param orderId
	 * @return
	 */
	@PutMapping("/v1/ConfirmReceipt")
	@ApiOperation(value = "小程序确认收货", tags = "更新接口")
	@ApiImplicitParam(name = "orderId", value = "订单ID")
	@ApiResponses({@ApiResponse(code = 100, message = "success"), @ApiResponse(code = 99, message = "确认收货失败")})
	public Result confirmReceipt(@RequestBody @ApiParam(hidden = true) Order orderId) {
		return orderService.confirmReceipt(orderId);
	}

	/**
	 * 小程序申请退款
	 *
	 * @param orderRefundVo
	 * @return
	 */
	@PostMapping("/v1/RefoundOrderPrice")
	@ApiOperation(value = "小程序申请退款", tags = "添加接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success"),
			@ApiResponse(code = 99, message = "" + "记录为空/订单已在申请退款状态/订单已退款成功/" + "申请退款失败/订单申请已关闭")})
	public Result refoundOrderPrice(@RequestBody OrderRefundVo orderRefundVo) {
		return orderService.refoundOrderPrice(orderRefundVo);
	}

	/**
	 * 后台同意退款
	 * @param paramVo
	 * @return
	 */
	@PutMapping("/v1/agreeRefoundOrder")
	@ApiOperation(value = "后台同意退款", tags = "更新接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success"),
			@ApiResponse(code = 99, message = "appmodeId错误/退款失败/退款订单更新失败/订单号有误/验证码不正确/验证码已失效/该手机号无验证信息/商家手机号与认证手机号不一致")})
	@ApiImplicitParams({@ApiImplicitParam(name = "orderRefoundId", value = "退款订单ID"),
			@ApiImplicitParam(name = "phone", value = "手机号"), @ApiImplicitParam(name = "smsCode", value = "验证码"),
			@ApiImplicitParam(name = "appmodelId", value = "商家wxAppId"),})
	public Result agreeRefoundOrder(@RequestBody @ApiParam(hidden = true) OrderParamVo paramVo) {
		return orderService.agreeRefoundOrder(paramVo);
	}

	/**
	 * 后台同意退货
	 * @param orderRefound
	 * @return
	 */
	@PutMapping("/v1/agreeRefoundProduct")
	@ApiOperation(value = "后台同意退货", tags = "更新接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success"),
			@ApiResponse(code = 99, message = "退货地址为空/同意退货失败/参数有误")})
	@ApiImplicitParams({@ApiImplicitParam(name = "orderRefoundId", value = "退款订单ID"),
			@ApiImplicitParam(name = "orderDetailId", value = "订单详情id"),
			@ApiImplicitParam(name = "shopAddress", value = "退款地址  怎么取得怎么存"),})
	public Result agreeRefoundProduct(@RequestBody @ApiParam(hidden = true) OrderRefound orderRefound) {
		return orderService.agreeRefoundProduct(orderRefound);
	}

	/**
	 * 后台拒绝退款
	 *
	 * 退款ID：orderRefoundId
	 * 订单id:  orderId
	 * 拒绝原因：refuseReason
	 * @param orderRefound
	 * @return
	 */
	@PutMapping("/v1/refuseRefoundOrder")
	@ApiOperation(value = "后台拒绝退款", tags = "更新接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success"),
			@ApiResponse(code = 99, message = "订单id错误/订单退款拒绝失败!/订单条件出错/更新订单详情失败")})
	@ApiImplicitParams({@ApiImplicitParam(name = "orderRefoundId", value = "退款ID"),
			@ApiImplicitParam(name = "orderId", value = "订单id"),
			@ApiImplicitParam(name = "refuseReason", value = "拒绝原因"),})
	public Result refuseRefoundOrder(@RequestBody @ApiParam(hidden = true) OrderParamVo orderRefound) {
		return orderService.refuseRefoundOrder(orderRefound);
	}

	/**
	 * 小程序申请退货填写物流
	 * @return
	 */
	@PutMapping("/v1/updateRefoundAddress")
	@ApiOperation(value = "用户填写填写退货物流", tags = "更新接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success"), @ApiResponse(code = 99, message = "退款id参数不能为空/填写失败")})
	public Result updateRefoundAddress(
			@RequestBody @ApiParam(required = true, type = "OrderWlVo") OrderWlVo orderWlVo) {
		return orderService.updateRefoundAddress(orderWlVo);
	}

	/**
	 * 小程序撤消退款申请
	 * @return
	 */
	@PutMapping("/v1/repealRefund")
	@ApiOperation(value = "小程序撤消退款申请", tags = "更新接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success"),
			@ApiResponse(code = 99, message = "订单更新失败/撤消失败/参数有误")})
	@ApiImplicitParams({@ApiImplicitParam(name = "orderRefoundId", value = "退款Id"),
			@ApiImplicitParam(name = "orderDetailId", value = "订单详情Id"),
			@ApiImplicitParam(name = "appmodelId", value = "商家wxAppId"),})
	public Result repealRefund(@RequestBody @ApiParam(hidden = true) OrderRefound orderRefound) {
		return orderService.repealRefund(orderRefound);
	}


	/**
	 * 小程序查询订单
	 *
	 * @return
	 */
	@GetMapping("/v1/findOrderMini")
	@ApiOperation(value = "小程序查询订单")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success", response = OrderExtend.class, responseContainer = "List"),
			@ApiResponse(code = 99, message = "更新失败")})
	@LookTimeAspect(scope = ScopeType.ARGUMENTS)
	public Result findOrderMini(@RequestParam @ApiParam(value = "商家wxAppId") String appmodelId,
			@RequestParam @ApiParam(value = "页数") Integer pageNum, @ApiParam(value = "条数") Integer pageSize,
			@RequestParam @ApiParam(value = "支付状态   0.待付款 1.待发货状态  2.待收货   3.交易成功   4.关闭订单") Integer orderState,
			@RequestParam @ApiParam(value = "用户id") Long wxuserId) {
		if (orderState > 4) {
			return ResultGenerator.genFailResult("非法参数");
		}
		PageHelper.startPage(pageNum, pageSize, "create_time desc");
		List<OrderExtend> orders = orderService.findOrderMini(wxuserId, appmodelId, orderState);
		PageInfo pageInfo = new PageInfo(orders);
		return ResultGenerator.genSuccessResult(pageInfo);
	}

	/**
	 * 历史记录
	 *
	 * @return
	 */
	@GetMapping("/v1/refoundHistory")
	@ApiOperation(value = "历史记录")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success", response = RefoundRecordVo.class, responseContainer = "list"),
			@ApiResponse(code = 99, message = "更新失败")})
	public Result refoundhistory(@RequestParam @ApiParam(value = "订单详情id") Long detailId) {
		return orderService.refoundHistory(detailId);
	}

	/**
	 * 提醒买家发货
	 *
	 * @return
	 */
	@GetMapping("/v1/warnSellerSendProduct")
	@ApiOperation(value = "提醒买家发货", tags = "更新接口", notes = "消息发送")
	@ApiResponses({@ApiResponse(code = 100, message = "success"), @ApiResponse(code = 99, message = "更新失败")})
	public Result warnSellerSendProduct(@RequestParam @ApiParam(value = "订单id") Long orderId) {
		return orderService.warnSellerSendProduct(orderId);
	}

	/**
	 * 再次购买
	 *
	 * @return
	 */
	@GetMapping("/v1/againBuyProduct")
	@ApiOperation(value = "再次购买", tags = "更新接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success  data会返回 -1 所有商品不可购买  0 已加入购物车  1 可购买的商品已加入购物车")})
	public Result againBuyProduct(@RequestParam Long orderId) {
		return orderService.againBuyProduct(orderId);
	}


	@PutMapping("/v1/updateRefund")
	@ApiOperation(value = "修改退款申请", tags = "更新接口", notes = "仅在申请状态下才可修改,该接口在swagger中不可调试")
	@ApiResponses({@ApiResponse(code = 100, message = "success"), @ApiResponse(code = 99, message = "更新失败")})
	@ApiImplicitParams({@ApiImplicitParam(name = "orderRefoundId", required = true, value = "退款订单id"),
			@ApiImplicitParam(name = "productState", required = false, value = "货物状态"),
			@ApiImplicitParam(name = "refoundFee", required = false, value = "退款金额"),
			@ApiImplicitParam(name = "remark", required = false, value = "备注"),
			@ApiImplicitParam(name = "reason", required = false, value = "原因"),})
	public Result updateRefund(@RequestBody @ApiParam(hidden = true) OrderParamVo paramVo) {
		return orderService.updateRefund(paramVo);
	}

	/**
	 * 小程序查询订单详情
	 * @return
	 */
	@GetMapping("/v1/findOrderDetail")
	@ApiOperation(value = "小程序查询订单详情", tags = "查询接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success", response = OrderExtend.class),
			@ApiResponse(code = 99, message = "更新失败")})
	public Result findOrderDetail(@RequestParam @ApiParam(value = "订单Id") Long orderId,
			@RequestParam @ApiParam(value = "商家wxAppId") String appmodelId) {
		return orderService.findOrderDetail(appmodelId, orderId);
	}

	/**
	 * 小程序查询售后订单
	 *
	 * @return
	 */
	@GetMapping("/v1/findOrderRefund")
	@ApiOperation(value = "小程序查询售后订单", tags = "查询接口")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success", response = OrderDetail.class, responseContainer = "List"),
			@ApiResponse(code = 99, message = "更新失败")})
	@LookTimeAspect(scope = ScopeType.ARGUMENTS)
	public Result findOrderRefund(@RequestParam @ApiParam(value = "商家wxAppId") String appmodelId,
			@RequestParam @ApiParam(value = "条数") Integer pageSize,
			@RequestParam @ApiParam(value = "页数") Integer pageNum,
			@RequestParam(required = false) @ApiParam(value = "订单详情id") Long detailId,
			@RequestParam @ApiParam(value = "用户id") Long wxuserId) {
		PageHelper.startPage(pageNum, pageSize, "update_time desc");
		List<OrderDetail> orderRefund = orderService.findOrderRefund(wxuserId, detailId, appmodelId);
		PageInfo pageInfo = new PageInfo(orderRefund);
		return ResultGenerator.genSuccessResult(pageInfo);
	}

	/**
	 * 小程序查询团购订单
	 *
	 * @return
	 */
	@GetMapping("/v1/findOrderMiniGroup")
	@ApiOperation(value = "小程序查询团购订单", tags = "查询接口")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success", response = OrderExtend.class, responseContainer = "List"),
			@ApiResponse(code = 99, message = "更新失败")})
	public Result findOrderMiniGroup(@RequestParam @ApiParam(value = "商家wxAppId") String appmodelId,
			@RequestParam @ApiParam(value = "条数") Integer pageSize, @ApiParam(value = "页数") Integer pageNum,
			@RequestParam @ApiParam(value = "团购状态表 0--待成团，1--已成团 ，2--失败") Integer orderState,
			@RequestParam @ApiParam(value = "用户id") Long wxuserId) {
		PageHelper.startPage(pageNum, pageSize);
		List<OrderExtend> orders = orderService.findOrderMiniGroup(wxuserId, appmodelId, orderState);
		PageInfo pageInfo = new PageInfo(orders);
		return ResultGenerator.genSuccessResult(pageInfo);
	}


	/**
	 * 小程序删除订单
	 *
	 * @param orderId
	 * @return
	 */
	@DeleteMapping("/v1/deleteOrder")
	@ApiOperation(value = "小程序删除订单", tags = "删除接口")
	public Result deleteOrder(@RequestParam @ApiParam(name = "orderId", value = "订单id") Long orderId) {
		Order order = new Order();
		order.setOrderId(orderId);
		order.setDeleteState(true);
		if (orderService.update(order) > 0) {
			return ResultGenerator.genSuccessResult();
		}
		return ResultGenerator.genFailResult("删除失败");
	}

	/**
	 * 后台批量备注订单
	 *
	 * @param jsonObject
	 * @return
	 */
	@PutMapping("/v1/batchBackRemrk")
	@ApiOperation(value = "后台批量备注订单", tags = "更新接口", notes = "该接口swagger中不可调试")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orderIds", value = "订单ids  逗号分隔", paramType = "query", required = true),
			@ApiImplicitParam(name = "backRemark", value = "备注", required = true, paramType = "query"),
			@ApiImplicitParam(name = "coverType ", value = "是否覆盖  true(覆盖)/false不覆盖", required = true, paramType = "query", dataType = "boolean"),})
	@ApiResponses({@ApiResponse(code = 100, message = "success"), @ApiResponse(code = 99, message = "备注失败")})
	public Result batchBackRemrk(
			@RequestBody @ApiParam(hidden = true, required = false, readOnly = true) JSONObject jsonObject) {
		return orderService.batchBackRemrk(jsonObject);
	}

	/**
	 * 无法直接用数组接收
	 * JSON格式化问题,无法直接转换
	 * 前端发送格式有问题
	 *
	 * 后台发货
	 * @return
	 */
	@PutMapping("/v1/deliverGoods")
	@ApiOperation(value = "后台发货", tags = "更新接口")
	@ApiImplicitParam(name = "orderVos", value = "发货对象", paramType = "body", dataType = "UpdateOrderVo")
	@ApiResponses({@ApiResponse(code = 100, message = "success"), @ApiResponse(code = 99, message = "更新失败")})
	public Result deliverGoods(@RequestBody @ApiParam(hidden = true, required = false) JSONObject jsonObject)
			throws Exception {
		List<UpdateOrderVo> orderVos = JSON.parseArray(jsonObject.getString("orderVos"), UpdateOrderVo.class);
		return orderService.deliverGoods(orderVos, jsonObject.getString("appmodelId"));
	}

	/**
	 * 后台查询订单
	 *
	 * @param paramVo
	 * @return
	 */
	@GetMapping("/v1/findOrderManager")
	@ApiOperation(value = "后台查询订单", tags = "查询接口")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success", response = OrderExtend.class, responseContainer = "List"),
			@ApiResponse(code = 99, message = "更新失败")})
	public Result findOrderManager(@ApiParam(value = "查询参数") OrderBtQueryVo paramVo) {
		PageHelper.startPage(paramVo.getPageNum(), paramVo.getPageSize());
		if (paramVo.getOrderState().equals(0) || paramVo.getOrderState().equals(1) || paramVo.getOrderState()
				.equals(2)) {
			PageHelper.orderBy("create_time desc");
		} else if (paramVo.getOrderState().equals(3)) {
			PageHelper.orderBy("pay_time  desc");
		} else if (paramVo.getOrderState().equals(4)) {
			PageHelper.orderBy("send_time desc");
		} else if (paramVo.getOrderState().equals(5)) {
			PageHelper.orderBy("record_time desc");
		} else if (paramVo.getOrderState().equals(6)) {
			PageHelper.orderBy("update_time desc");
		}
		List<OrderExtend> orderExtends = orderService.findOrderManager(paramVo);
		PageInfo pageInfo = new PageInfo(orderExtends);
		return ResultGenerator.genSuccessResult(pageInfo);
	}

	/**
	 *
	 * @return
	 */
	@GetMapping("/v1/findRefundOrderManager")
	@ApiOperation(value = "后台查询退款中的售后订单", tags = "查询接口")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success", response = OrderDetail.class, responseContainer = "List"),
			@ApiResponse(code = 99, message = "更新失败")})
	public Result findRefundOrderManager(@ApiParam(value = "查询参数") OrderBtQueryVo paramVo) {
		PageHelper.orderBy("update_time desc");
		PageHelper.startPage(paramVo.getPageNum(), paramVo.getPageSize());
		List<OrderDetail> orderExtends = orderService.findRefundOrderManager(paramVo);
		PageInfo pageInfo = new PageInfo(orderExtends);
		return ResultGenerator.genSuccessResult(pageInfo);
	}

	/**
	 * 后台更新订单
	 * @param paramVo
	 * @return
	 */
	@PutMapping("/v1/updateOrder")
	@ApiOperation(value = "后台更新订单", tags = "更新接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success"), @ApiResponse(code = 99, message = "更新失败")})
	public Result updateOrder(@RequestBody UpdateOrderVo paramVo) {
		return orderService.updateOrder(paramVo);
	}

	/**
	 * 后台查询所有已付款订单数量
	 *
	 * @param appmodelId
	 */
	@GetMapping("/v1/OrderPayOkSum")
	@ApiOperation(value = "后台查询订单统计(已付款,退款中,团购订单,积分订单,代理采购订单,所有订单数量,待发货的订单)", tags = "查询接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success", response = OrderSensus.class),
			@ApiResponse(code = 99, message = "查询失败")})
	public Result orderPayOkSum(@RequestParam String appmodelId) {
		return orderService.orderPayOkSum(appmodelId);
	}


	@GetMapping("/v1/mini/order/statistics")
	@ApiOperation(value = "小程序端查询我的订单数量", tags = "查询接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success", response = OrderStatisticsVo.class),
			@ApiResponse(code = 99, message = "查询失败")})
	public Result miniOrderStatistics(@RequestParam Long wxuserId) {
		OrderStatisticsVo miniOrderStatistics = orderService.findMiniOrderStatistics(wxuserId);
		return ResultGenerator.genSuccessResult(miniOrderStatistics);
	}
}
