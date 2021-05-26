package com.medusa.basemall.agent.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medusa.basemall.agent.service.AgentPurchaseOrderService;
import com.medusa.basemall.agent.vo.*;
import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.VersionNumber;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 *  代理订单接口
 * @author medusa
 * @date 2018/06/02
 */
@RestController
@RequestMapping("/agent/purchase/order")
@Api(tags = "所有接口")
@VersionManager(versoinNumber = VersionNumber.MARKETINGVERSION)
public class AgentPurchaseOrderController {

	@Resource
	private AgentPurchaseOrderService agentPurchaseOrderService;

	@GetMapping("/v1/mini/list/detail")
	@ApiOperation(value = "小程序查询订单详情", tags = "查询接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success", response = MiniOrderListVo.class),
			@ApiResponse(code = 99, message = "查询失败")})
	public Result miniListDetail(@RequestParam @ApiParam(value = "代理订单id") Long purchaseOrderId) {
		MiniOrderListVo miniOrderListVo = agentPurchaseOrderService.miniListDetail(purchaseOrderId);
		return ResultGenerator.genSuccessResult(miniOrderListVo);
	}

	@GetMapping("/v1/mini/list")
	@ApiOperation(value = "小程序查询代理订单", tags = "查询接口")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success", response = MiniOrderListVo.class, responseContainer = "List"),
			@ApiResponse(code = 99, message = "查询失败")})
	public Result miniList(@RequestParam @ApiParam(value = "用户id") Long wxuserId,
			@RequestParam @ApiParam(value = "订单状态 null.查询全部 0.等待买家付款  1.买家已付款 待发货状态 2.卖家已发货  3.交易成功 4.订单关闭") Integer payFlag,
			@RequestParam(required = false) @ApiParam(value = "商品名称", required = false) String shopName,
			@RequestParam( ) @ApiParam(value = "条数") Integer pageSize,
			@RequestParam( ) @ApiParam(value = "页数") Integer pageNum) {
		PageHelper.startPage(pageNum, pageSize);
		sort(payFlag);
		if (wxuserId == null) {
			return ResultGenerator.genFailResult("参数错误");
		}
		List<MiniOrderListVo> miniOrderListVos = agentPurchaseOrderService.miniList(wxuserId, payFlag, shopName);
		PageInfo pageInfo = new PageInfo(miniOrderListVos);
		return ResultGenerator.genSuccessResult(pageInfo);
	}

	@GetMapping("/v1/list/backstage/detail")
	@ApiOperation(value = "后台查询代理订单详情", tags = "查询接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success", response = BackstageOrderListVo.class),
			@ApiResponse(code = 99, message = "查询失败")})
	public Result backstageDetail(@RequestParam @ApiParam(value = "代理订单id") Long purchaseOrderId) {
		BackstageOrderListVo backstageDetail = agentPurchaseOrderService.backstageDetail(purchaseOrderId);
		return ResultGenerator.genSuccessResult(backstageDetail);
	}

	@GetMapping("/v1/list/backstage")
	@ApiOperation(value = "后台代理代理订单查询", tags = "查询接口")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success", response = BackstageOrderListVo.class, responseContainer = "List"),
			@ApiResponse(code = 99, message = "商品库存不足/下单失败")})
	public Result backstageList(@RequestParam @ApiParam(value = "商家wxAppId") String appmodelId,
			@RequestParam( ) @ApiParam(value = "条数") Integer pageSize,
			@RequestParam( ) @ApiParam(value = "页数") Integer pageNum,
			@RequestParam(required = false) @ApiParam(value = "订单状态 null.查询全部  0.等待买家付款  1.买家已付款 待发货状态 2.卖家已发货  3.交易成功 4.订单关闭") Integer payFlag,
			@RequestParam(required = false) @ApiParam(value = "商品名称", required = false) String shopName,
			@RequestParam(required = false) @ApiParam(value = "卖家昵称") String nikeName,
			@RequestParam(required = false) @ApiParam(value = "收货人") String user,
			@RequestParam(required = false) @ApiParam(value = "手机号") String phone,
			@RequestParam(required = false) @ApiParam(value = "订单号") String outTrade_no,
			@RequestParam(required = false) @ApiParam(value = "手机号") String wlNum,
			@RequestParam(required = false) @ApiParam(value = "付款时间") String payTime) {
		PageHelper.startPage(pageNum, pageSize);
		sort(payFlag);
		List<BackstageOrderListVo> backstageOrderListVos = agentPurchaseOrderService
				.backstageList(payFlag, appmodelId, shopName, nikeName, user, phone, outTrade_no, wlNum, payTime);
		PageInfo pageInfo = new PageInfo(backstageOrderListVos);
		return ResultGenerator.genSuccessResult(pageInfo);
	}

	private void sort(Integer payFlag) {
		if (payFlag == null) {
			payFlag = 0;
		}
		switch (payFlag) {
			case 1:
				PageHelper.orderBy("pay_flag desc");
				break;
			case 2:
				PageHelper.orderBy("send_time desc");
				break;
			case 3:
				PageHelper.orderBy("record_time desc");
				break;
			case 4:
				PageHelper.orderBy("close_time desc");
				break;
			default:
				PageHelper.orderBy("create_time desc");
				break;
		}
	}

	/**
	 *  代理订单生成
	 *
	 * @return
	 */
	@PostMapping("/v1/generate")
	@ApiOperation(value = "代理订单生成", tags = "支付接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success:会返回orderId", response = Integer.class),
			@ApiResponse(code = 99, message = "商品库存不足/下单失败")})
	public Result saveOrder(@RequestBody PayAgentOrderVo payAgentOrderVo) {
		return agentPurchaseOrderService.saveOrder(payAgentOrderVo);
	}

	@PutMapping("/v1/payment")
	@ApiOperation(value = "订单支付", tags = "支付接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success", response = Integer.class),
			@ApiResponse(code = 99, message = "该订单已付款/该订单已发货/该订单已交易成功/该订单已订单关闭/未知错误/非会员用户,不可使用余额支付/余额不足/订单错误")})
	public Result payment(@RequestBody PaymentAgentOrderVo payAgentOrderVo, HttpServletRequest request) {
		return agentPurchaseOrderService.paymentOrder(payAgentOrderVo, request);
	}

	/**
	 *  代理订单支付
	 * @param request
	 * @return
	 */
	@PostMapping("/v1/notify")
	@ApiOperation(value = "支付回调", tags = "回调接口")
	public String notify(HttpServletRequest request) throws IOException {
		String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
		return agentPurchaseOrderService.notify(xmlResult);
	}

	@PutMapping("/v1/deliverGoods")
	@ApiOperation(value = "后台发货", tags = "更新接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success"), @ApiResponse(code = 99, message = "发货失败")})
	public Result deliverGoods(@RequestBody @ApiParam(value = "发货对象") List<AgentDeliverGoods> deliverGoods) {
		return agentPurchaseOrderService.deliverGoods(deliverGoods);
	}

	@DeleteMapping("/v1/closeOrder")
	@ApiOperation(value = "批量关闭单个或多个代理订单", tags = "删除接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success"), @ApiResponse(code = 99, message = "发货失败")})
	public Result closeOrder(@RequestParam @ApiParam(value = "订单Ids 字符串逗号分隔", required = true) String purchaseOrderIds,
			@RequestParam @ApiParam(value = "操作人   1001客户操作 2001商家操作", required = true) Integer operatiPerson) {
		return agentPurchaseOrderService.closeOrder(purchaseOrderIds, operatiPerson);
	}

	@PutMapping("/v1/batch/remark")
	@ApiOperation(value = "后台批量备注代理订单", tags = "更新接口", notes = "该接口swagger中不可调试")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "purchaseOrderIds", value = "订单Ids 字符串逗号分隔", required = true, paramType = "query"),
			@ApiImplicitParam(name = "backRemark", value = "备注", required = true, paramType = "query"  ),
			@ApiImplicitParam(name = "coverType ", value = "是否覆盖  true(覆盖)/false不覆盖", required = true, paramType = "query", dataType = "boolean"),})
	@ApiResponses({@ApiResponse(code = 100, message = "success"), @ApiResponse(code = 99, message = "备注失败")})
	public Result batchBackRemrk(
			@RequestBody @ApiParam(hidden = true, required = false, readOnly = true) JSONObject jsonObject) {
		return agentPurchaseOrderService.batchBackRemrk(jsonObject);
	}


	@PutMapping("/v1/addres")
	@ApiOperation(value = "修改收货地址", tags = "更新接口", notes = "该接口swagger中不可调试")
	@ApiImplicitParams({@ApiImplicitParam(name = "addres", value = "收货地址", required = true, paramType = "query"),
			@ApiImplicitParam(name = "purchaseOrderId", value = "订单Id", required = true, paramType = "query")})
	@ApiResponses({@ApiResponse(code = 100, message = "success"), @ApiResponse(code = 99, message = "备注失败")})
	public Result addres(
			@RequestBody @ApiParam(hidden = true, required = false, readOnly = true) JSONObject jsonObject) {
		agentPurchaseOrderService
				.updateOrderAddres(jsonObject.getLong("purchaseOrderId"), jsonObject.getString("addres"));
		return ResultGenerator.genSuccessResult();
	}

}
