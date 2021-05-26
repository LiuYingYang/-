package com.medusa.basemall.order.controller;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.order.entity.Order;
import com.medusa.basemall.order.entity.OrderRefound;
import com.medusa.basemall.order.service.OrderRefoundService;
import com.medusa.basemall.order.vo.ProductOrderVo;
import com.medusa.basemall.order.vo.SaveOrderVo;
import com.medusa.basemall.order.vo.UpdateOrderVo;
import com.medusa.basemall.shop.dao.SmsMapper;
import com.medusa.basemall.shop.entity.Sms;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderControllerTest extends BasemallApplicationTests {

	private Long OrderId = 1529989157243152L;
	private Long OrderRefundId = 180626848244L;
	private Long WxuserId = 1531212601449327L;

	@Autowired
	private OrderRefoundService refoundService;

	@Resource
	private SmsMapper tSmsMapper;

	private String getCode(String phone) {
		Map<String, Object> map = new HashMap();
		map.put("phone", phone);
		map.put("appmodelId", Constant.appmodelIdy);
		map.put("type", 4);
		MockMvcUtil.sendRequest("/sms/getVerificationCcode", JSONObject.toJSONString(map), null, "post");
		Map<String, Object> param = new HashMap<>(3);
		param.put("userTel", phone);
		param.put("appmodelId", Constant.appmodelIdy);
		param.put("type", 4);
		Sms sms = tSmsMapper.selectByPhone(param);
		return sms.getSmsCode();
	}

	//买家申请仅退款商家同意退款完整流程用例
	@Test
	public void RefundPrice1() {
		Long orderId = 1530858109979755L;
		Long wxuserId = 1528246624180515L;
		OrderRefound orderRefound = new OrderRefound();
		orderRefound.setOrderId(orderId);
		orderRefound.setWxuserId(wxuserId);
		orderRefound.setOrderDetailId(1530858110332180L);
		orderRefound.setReason("我是原因");
		orderRefound.setRefoundFee(new BigDecimal("0.01"));
		orderRefound.setRemark("我是备注");
		orderRefound.setRefoundType(0);
		orderRefound.setAppmodelId(Constant.appmodelIdy);
		JSONObject post = MockMvcUtil
				.sendRequest("/Order/RefoundOrderPrice", JSONObject.toJSONString(orderRefound), null, "post");
		Integer code = post.getInteger("code");
		if (code.equals(100)) {
			Condition condition = new Condition(OrderRefound.class);
			condition.createCriteria().andEqualTo("orderId", orderId);
			condition.orderBy("createTime");
			List<OrderRefound> byCondition = refoundService.findByCondition(condition);
			Map<String, Object> map = new HashMap<>();
			map.put("orderRefoundId", byCondition.get(byCondition.size() - 1).getOrderRefoundId());
			map.put("phone", "15888030961");
			map.put("smsCode", "111111");   //固定假设值
			map.put("appmodelId", Constant.appmodelIdy);
			JSONObject post1 = MockMvcUtil
					.sendRequest("/Order/agreeRefoundOrder", JSONObject.toJSONString(map), null, "post");
		}
	}

	//买家申请退款退货,同意退货退款完整流程用例
	@Test
	public void RefundPrice2() {
		Long orderId = 1532939717100940L;
		OrderRefound orderRefound = new OrderRefound();
		orderRefound.setOrderId(orderId);
		orderRefound.setOrderDetailId(1532939717889283L);
		orderRefound.setWxuserId(1531212601449327L);
		orderRefound.setReason("我是原因");
		orderRefound.setRefoundFee(new BigDecimal("0.01"));
		orderRefound.setRemark("我是备注");
		orderRefound.setRefoundType(1);
		orderRefound.setProductState("货物状态");
		orderRefound.setAppmodelId(Constant.appmodelIdy);
		JSONObject post1 = MockMvcUtil
				.sendRequest("/Order/RefoundOrderPrice", JSONObject.toJSONString(orderRefound), null, "post");
		if (post1.getInteger("code").equals(100)) {
			Map<String, Object> map1 = new HashMap<>();
			Condition condition = new Condition(OrderRefound.class);
			condition.createCriteria().andEqualTo("orderId", orderId);
			condition.orderBy("createTime");
			List<OrderRefound> byCondition = refoundService.findByCondition(condition);
			Long orderRefoundId = byCondition.get(byCondition.size() - 1).getOrderRefoundId();
			map1.put("orderRefoundId", orderRefoundId);
			map1.put("orderId", orderId);
			map1.put("shopAddress", "商家接收快递地址");
			map1.put("appmodelId", Constant.appmodelIdy);
			//同意退款退货
			JSONObject post2 = MockMvcUtil
					.sendRequest("/Order/agreeRefoundProduct", JSONObject.toJSONString(map1), null, "put");
			if (post2.getInteger("code").equals(100)) {
				Map<String, Object> map2 = new HashMap<>();
				map2.put("orderRefoundId", orderRefoundId);
				map2.put("uWlName", "YTO");
				map2.put("uWlNum", "123456789");
				map2.put("uWlPhone", "15888030961");
				map2.put("uWlRemark", "物流备注");
				map2.put("appmodelId", Constant.appmodelIdy);
				//更新用户收货地址
				JSONObject post3 = MockMvcUtil
						.sendRequest("/Order/updateRefoundAddress", JSONObject.toJSONString(map2), null, "post");
				if (post3.getInteger("code").equals(100)) {
					Map<String, Object> map3 = new HashMap<>();
					map3.put("orderRefoundId", orderRefoundId);
					map3.put("phone", "15888030961");
					map3.put("smsCode", "111111");   //固定假设值
					map3.put("appmodelId", Constant.appmodelIdy);
					//同意退货
					MockMvcUtil.sendRequest("/Order/agreeRefoundOrder", JSONObject.toJSONString(map3), null, "post");
				}
			}
		}
	}

	/**
	 * 商家同意退货退款，用户5天未填写地址订单关闭用例
	 */
	@Test
	public void RefundPrice3() {
		Long orderId = 1530773539139019L;
		OrderRefound orderRefound = new OrderRefound();
		orderRefound.setOrderId(orderId);
		orderRefound.setOrderDetailId(1530773539457993L);
		orderRefound.setWxuserId(WxuserId);
		orderRefound.setReason("我是原因");
		orderRefound.setRefoundFee(new BigDecimal("0.01"));
		orderRefound.setRemark("我是备注");
		orderRefound.setRefoundType(1);
		orderRefound.setProductState("货物状态");
		orderRefound.setAppmodelId(Constant.appmodelIdy);
		JSONObject post1 = MockMvcUtil
				.sendRequest("/Order/RefoundOrderPrice", JSONObject.toJSONString(orderRefound), null, "post");
		if (post1.getInteger("code").equals(100)) {
			Map<String, Object> map1 = new HashMap<>();
			Condition condition = new Condition(OrderRefound.class);
			condition.createCriteria().andEqualTo("orderId", orderId);
			condition.orderBy("createTime");
			List<OrderRefound> byCondition = refoundService.findByCondition(condition);
			Long orderRefoundId = byCondition.get(byCondition.size() - 1).getOrderRefoundId();
			map1.put("orderRefoundId", orderRefoundId);
			map1.put("orderId", orderId);
			map1.put("shopAddress", "商家接收快递地址");
			map1.put("appmodelId", Constant.appmodelIdy);
			//商家同意退货退款
			JSONObject post2 = MockMvcUtil
					.sendRequest("/Order/agreeRefoundProduct", JSONObject.toJSONString(map1), null, "post");
		}
	}

	/**
	 * 商家同意退货退款，用户5天填写完地址,商家15天内未确认收货,自动退款
	 * 退款订单状态为退款成功(6)   订单变为交易退款完成(5)
	 */
	@Test
	public void RefundPrice4() throws InterruptedException {
		Long orderId = 1530773539139019L;
		OrderRefound orderRefound = new OrderRefound();
		orderRefound.setOrderId(orderId);
		orderRefound.setOrderDetailId(1530773539457993L);
		orderRefound.setWxuserId(WxuserId);
		orderRefound.setReason("我是原因");
		orderRefound.setRefoundFee(new BigDecimal("0.01"));
		orderRefound.setRemark("我是备注");
		orderRefound.setRefoundType(1);
		orderRefound.setProductState("货物状态");
		orderRefound.setAppmodelId(Constant.appmodelIdy);
		JSONObject post1 = MockMvcUtil
				.sendRequest("/Order/RefoundOrderPrice", JSONObject.toJSONString(orderRefound), null, "post");
		Thread.sleep(2000);
		if (post1.getInteger("code").equals(100)) {
			Map<String, Object> map1 = new HashMap<>();
			Condition condition = new Condition(OrderRefound.class);
			condition.createCriteria().andEqualTo("orderId", orderId);
			condition.orderBy("createTime");
			List<OrderRefound> byCondition = refoundService.findByCondition(condition);
			Long orderRefoundId = byCondition.get(byCondition.size() - 1).getOrderRefoundId();
			map1.put("orderRefoundId", orderRefoundId);
			map1.put("orderId", orderId);
			map1.put("shopAddress", "商家接收快递地址");
			map1.put("appmodelId", Constant.appmodelIdy);
			JSONObject post2 = MockMvcUtil
					.sendRequest("/Order/agreeRefoundProduct", JSONObject.toJSONString(map1), null, "post");
			Thread.sleep(2000);
			if (post2.getInteger("code").equals(100)) {
				Map<String, Object> map2 = new HashMap<>();
				map2.put("orderRefoundId", orderRefoundId);
				map2.put("uWlName", "YTO");
				map2.put("uWlNum", "123456789");
				map2.put("uWlPhone", "15888030961");
				map2.put("uWlRemark", "物流备注");
				map2.put("appmodelId", Constant.appmodelIdy);
				MockMvcUtil.sendRequest("/Order/updateRefoundAddress", JSONObject.toJSONString(map2), null, "post");
				//买家发货,商家15天之后未收货,商家自动退款给买家
			}
		}
	}

	/**
	 * 订单属于待发货状态第一次被拒绝
	 * 订单变为已发货状态,同时退款订单关闭
	 */
	@Test
	public void RefundOrder() {
		Long orderId = 1530780051026527L;
		OrderRefound orderRefound = new OrderRefound();
		orderRefound.setOrderId(orderId);
		orderRefound.setOrderDetailId(1530780051367593L);
		orderRefound.setWxuserId(WxuserId);
		orderRefound.setReason("我是原因");
		orderRefound.setRefoundFee(new BigDecimal("0.01"));
		orderRefound.setRemark("我是备注");
		orderRefound.setRefoundType(1);
		orderRefound.setProductState("货物状态");
		orderRefound.setAppmodelId(Constant.appmodelIdy);
		JSONObject post1 = MockMvcUtil
				.sendRequest("/Order/RefoundOrderPrice", JSONObject.toJSONString(orderRefound), null, "post");
		if (post1.getInteger("code").equals(100)) {
			Map<String, Object> map1 = new HashMap<>();
			Condition condition = new Condition(OrderRefound.class);
			condition.createCriteria().andEqualTo("orderId", orderId);
			condition.orderBy("createTime");
			List<OrderRefound> byCondition = refoundService.findByCondition(condition);
			Long orderRefoundId = byCondition.get(byCondition.size() - 1).getOrderRefoundId();
			Map<String, Object> map = new HashMap<>();
			map.put("orderRefoundId", orderRefoundId);
			map.put("orderId", orderId);
			map.put("refoundState", 2);
			map.put("wlCode", 2);
			map.put("wlName", 2);
			map.put("wlNum", 2);
			map.put("distributeMode", 2);
			map.put("deliveryStaff ",
					"{Distributor:\"z张三\",phone:\"18368094914\",DeliveryTime:\"2018-06-26 15:55:39\"}");
			map.put("refuseReason", "商家发货了");
			map.put("appmodelId", Constant.appmodelIdy);
			MockMvcUtil.sendRequest("/Order/refuseRefoundOrder", JSONObject.toJSONString(map), null, "post");
		}

	}


	/**
	 * 订单属于已发货状态第二次被拒绝
	 * 订单如果已经发货时间超时20天,订单则关闭
	 * 订单全部退款成功,订单则关闭,否则保持原本状态
	 * 订单详情关闭关闭当前商品售后
	 */
	@Test
	public void RefundOrder2() {
		Long orderId = 1530780051026527L;
		OrderRefound orderRefound = new OrderRefound();
		orderRefound.setOrderId(orderId);
		orderRefound.setOrderDetailId(1530780051367593L);
		orderRefound.setWxuserId(WxuserId);
		orderRefound.setReason("我是原因");
		orderRefound.setRefoundFee(new BigDecimal("0.01"));
		orderRefound.setRemark("我是备注");
		orderRefound.setRefoundType(1);
		orderRefound.setProductState("货物状态");
		orderRefound.setAppmodelId(Constant.appmodelIdy);
		JSONObject post1 = MockMvcUtil
				.sendRequest("/Order/RefoundOrderPrice", JSONObject.toJSONString(orderRefound), null, "post");
		if (post1.getInteger("code").equals(100)) {
			Map<String, Object> map1 = new HashMap<>();
			Condition condition = new Condition(OrderRefound.class);
			condition.createCriteria().andEqualTo("orderId", orderId);
			condition.orderBy("createTime");
			List<OrderRefound> byCondition = refoundService.findByCondition(condition);
			Long orderRefoundId = byCondition.get(byCondition.size() - 1).getOrderRefoundId();
			Map<String, Object> map = new HashMap<>();
			map.put("orderRefoundId", orderRefoundId);
			map.put("orderId", orderId);
			map.put("refoundState", 1);
			map.put("refuseReason", "拒绝退货退款");
			map.put("appmodelId", Constant.appmodelIdy);
			MockMvcUtil.sendRequest("/Order/refuseRefoundOrder", JSONObject.toJSONString(map), null, "post");
		}
	}

	/**
	 * 订单属于已发货状态第1次被拒绝
	 */
	@Test
	public void RefundOrder3() {
		Long orderId = 1530780051026527L;
		OrderRefound orderRefound = new OrderRefound();
		orderRefound.setOrderId(orderId);
		orderRefound.setOrderDetailId(1530780051367593L);
		orderRefound.setWxuserId(WxuserId);
		orderRefound.setReason("我是原因");
		orderRefound.setRefoundFee(new BigDecimal("0.01"));
		orderRefound.setRemark("我是备注");
		orderRefound.setRefoundType(1);
		orderRefound.setProductState("货物状态");
		orderRefound.setAppmodelId(Constant.appmodelIdy);
		JSONObject post1 = MockMvcUtil
				.sendRequest("/Order/RefoundOrderPrice", JSONObject.toJSONString(orderRefound), null, "post");
		if (post1.getInteger("code").equals(100)) {
			Map<String, Object> map1 = new HashMap<>();
			Condition condition = new Condition(OrderRefound.class);
			condition.createCriteria().andEqualTo("orderId", orderId);
			condition.orderBy("createTime");
			List<OrderRefound> byCondition = refoundService.findByCondition(condition);
			Long orderRefoundId = byCondition.get(byCondition.size() - 1).getOrderRefoundId();
			Map<String, Object> map = new HashMap<>();
			map.put("orderRefoundId", orderRefoundId);
			map.put("orderId", orderId);
			map.put("refoundState", 1);
			map.put("refuseReason", "拒绝退款");
			map.put("appmodelId", Constant.appmodelIdy);
			MockMvcUtil.sendRequest("/Order/refuseRefoundOrder", JSONObject.toJSONString(map), null, "post");
		}
	}

	//小程序申请退货退款
	@Test
	public void RefoundOrderPrice() {
		Long orderId = 1530864395914268L;
		OrderRefound orderRefound = new OrderRefound();
		orderRefound.setOrderId(orderId);
		orderRefound.setOrderDetailId(1530864397015254L);
		orderRefound.setWxuserId(WxuserId);
		orderRefound.setReason("我是原因");
		orderRefound.setRefoundFee(new BigDecimal("0.01"));
		orderRefound.setRemark("我是备注");
		orderRefound.setRefoundType(1);
		orderRefound.setProductState("货物状态");
		orderRefound.setAppmodelId(Constant.appmodelIdy);
		JSONObject post = MockMvcUtil
				.sendRequest("/Order/RefoundOrderPrice", JSONObject.toJSONString(orderRefound), null, "post");
	}

	//后台同意退款
	@Test
	public void agreeRefoundOrder() {
		Map<String, Object> map = new HashMap<>();
		map.put("orderRefoundId", OrderRefundId);
		map.put("phone", "15888030961");
		map.put("smsCode", "674490");
		map.put("appmodelId", Constant.appmodelIdy);
		MockMvcUtil.sendRequest("/Order/agreeRefoundOrder", JSONObject.toJSONString(map), null, "post");
	}

	//同意退货
	@Test
	public void agreeRefoundProduct() {
		Map<String, Object> map = new HashMap<>();
		map.put("orderRefoundId", 180731831713L);
		map.put("orderDetailId", 1532939717889283l);
		map.put("shopAddress",
				"{\n" + "\"appmodelId\": \"S00020001wxa75115dccbe8ecec\",\n" + "\"defaultState\": true,\n"
						+ "\"locationJson\": {\n" + "\"address\": [\n" + "\"浙江省\",\n"
						+ "\"宁波市\",\n" + "            \"江北区\",\n" + "            \"工业c区长兴路158号\"\n"
						+ "],\n" + "       \"latLng\": {\n" + "            \"lng\": 121.492237,\n"
						+ "            \"lat\": 29.937831\n" + "        }\n" + "    ,\n"
						+ "    \"logisticCancelId\": 11,\n" + "    \"phone\": \"13999999999\",\n"
						+ "    \"postCode\": \"123131\",\n" + "    \"userName\": \"联系人\",\n"
						+ "    \"addr\":\"浙江省宁波市江北区工业c区长兴路158号\"\n" + " }\n" + "}");
		map.put("appmodelId", Constant.appmodelIdy);
		MockMvcUtil.sendRequest("/Order/agreeRefoundProduct", JSONObject.toJSONString(map), null, "put");
	}

	//订单未待收货状态,直接拒绝退款
	@Test
	public void refuseRefoundOrder() {
		Map<String, Object> map = new HashMap<>();
		map.put("orderRefoundId", OrderRefundId);
		map.put("orderId", OrderId);
		map.put("refoundState", 5);
		map.put("refuseReason", "拒绝原因");
		map.put("appmodelId", Constant.appmodelIdy);
		MockMvcUtil.sendRequest("/Order/refuseRefoundOrder", JSONObject.toJSONString(map), null, "post");
	}

	//订单状态为待发货状态,商家拒绝发货
	@Test
	public void refuseRefoundOrder2() {
		Map<String, Object> map = new HashMap<>();
		map.put("orderRefoundId", OrderRefundId);
		map.put("orderId", OrderId);
		map.put("refoundState", 10);
		map.put("refuseReason", "商家发货了");
		map.put("appmodelId", Constant.appmodelIdy);
		MockMvcUtil.sendRequest("/Order/refuseRefoundOrder", JSONObject.toJSONString(map), null, "post");
	}

	//小程序申请退货填写物流
	@Test
	public void updateRefoundAddress() {
		Map<String, Object> map = new HashMap<>();
		map.put("orderRefoundId", OrderRefundId);
		map.put("uWlName", "YTO");
		map.put("uWlNum", "123456789");
		map.put("uWlPhone", "15888030961");
		map.put("uWlRemark", "");
		map.put("appmodelId", Constant.appmodelIdy);
		MockMvcUtil.sendRequest("/Order/updateRefoundAddress", JSONObject.toJSONString(map), null, "post");
	}

	//小程序撤消退款申请
	@Test
	public void repealRefund() {
		Map<String, Object> map = new HashMap<>();
		map.put("orderRefoundId", OrderRefundId);
		map.put("orderId", OrderId);
		map.put("appmodelId", Constant.appmodelIdy);
		MockMvcUtil.sendRequest("/Order/repealRefund", JSONObject.toJSONString(map), null, "post");
	}

	//小程序确认收货
	@Test
	public void ConfirmReceipt() {
		Order order = new Order();
		order.setOrderId(1528956275465157L);
		MockMvcUtil.sendRequest("/Order/confirmReceipt", JSONObject.toJSONString(order), null, "post");
	}

	//关闭订单
	@Test
	public void closeOrder() {
		Map<String, Object> map = new HashMap<>();
		map.put("orderIds", "1529558478367567");
		MockMvcUtil.sendRequest("/Order/closeOrder", JSONObject.toJSONString(map), null, "post");
	}

	//小程序查看待付款订单
	@Test
	public void findOrderMini1() {
		Map<String, Object> map = new HashMap<>();
		map.put("wxuserId", 1528246624180515L);
		map.put("appmodelId", Constant.appmodelIdy);
		map.put("pageNum", 1);
		map.put("pageSize", 10);
		map.put("orderState", 0);
		System.out.println("待付款订单");
		MockMvcUtil.sendRequest("/Order/findOrderMini", JSONObject.toJSONString(map), null, "post");
	}

	//小程序查看待发货订单
	@Test
	public void findOrderMini2() {
		Map<String, Object> map = new HashMap<>();
		map.put("wxuserId", 1528246624180515L);
		map.put("appmodelId", Constant.appmodelIdy);
		map.put("pageNum", 1);
		map.put("pageSize", 10);
		map.put("orderState", 1);
		System.out.println("待付款订单");
		MockMvcUtil.sendRequest("/Order/findOrderMini", JSONObject.toJSONString(map), null, "post");
	}


	//小程序查看已发货订单
	@Test
	public void findOrderMini3() {
		Map<String, Object> map = new HashMap<>();
		map.put("wxuserId", 1528246624180515L);
		map.put("appmodelId", Constant.appmodelIdy);
		map.put("pageNum", 1);
		map.put("pageSize", 10);
		map.put("orderState", 2);
		System.out.println("待付款订单");
		MockMvcUtil.sendRequest("/Order/findOrderMini", JSONObject.toJSONString(map), null, "post");
	}

	//小程序查看已完成订单
	@Test
	public void findOrderMini4() {
		Map<String, Object> map = new HashMap<>();
		map.put("wxuserId", 1528246624180515L);
		map.put("appmodelId", Constant.appmodelIdy);
		map.put("pageNum", 1);
		map.put("pageSize", 10);
		map.put("orderState", 3);
		System.out.println("待付款订单");
		MockMvcUtil.sendRequest("/Order/findOrderMini", JSONObject.toJSONString(map), null, "post");
	}

	//小程序查看已关闭的订单
	@Test
	public void findOrderMini5() {
		Map<String, Object> map = new HashMap<>();
		map.put("wxuserId", 1528246624180515L);
		map.put("appmodelId", Constant.appmodelIdy);
		map.put("pageNum", 1);
		map.put("pageSize", 10);
		map.put("orderState", 4);
		System.out.println("关闭的订单");
		MockMvcUtil.sendRequest("/Order/findOrderMini", JSONObject.toJSONString(map), null, "post");
	}

	//删除订单
	@Test
	public void findOrderMini6() {
		Map<String, Object> map = new HashMap<>();
		map.put("orderId", 1530067654880509L);
		System.out.println("关闭的订单");
		MockMvcUtil.sendRequest("/Order/deleteOrder", JSONObject.toJSONString(map), null, "post");
	}

	//小程序查看订单详情
	@Test
	public void findOrderDetail() {
		Map<String, Object> map = new HashMap<>();
		map.put("wxuserId", 1528246624180515L);
		map.put("appmodelId", Constant.appmodelIdy);
		map.put("orderId", 1530844208449028L);
		System.out.println("订单详情");
		MockMvcUtil.sendRequest("/Order/findOrderDetail", JSONObject.toJSONString(map), null, "post");
	}

	//小程序查看售后订单
	@Test
	public void findOrderRefund() {
		Map<String, Object> map = new HashMap<>();
		map.put("wxuserId", 1528246692051902L);
		map.put("appmodelId", Constant.appmodelIdy);
		map.put("pageNum", 0);
		map.put("pageSize", 0);
		map.put("detailId", "1531734006975339");
		MockMvcUtil.sendRequest("/Order/findOrderRefund", JSONObject.toJSONString(map), null, "post");
	}

	//小程序查询团购订单
	@Test
	public void findOrderMiniGroup() {
		Map<String, Object> map = new HashMap<>();
		map.put("wxuserId", 1528246624180515L);
		map.put("appmodelId", Constant.appmodelIdy);
		map.put("pageNum", 1);
		map.put("pageSize", 10);
		map.put("orderState", 0);
		MockMvcUtil.sendRequest("/Order/findOrderMiniGroup", JSONObject.toJSONString(map), null, "post");
	}

	//后台查询所有订单
	@Test
	public void findOrderManager() {
		Map<String, Object> map = new HashMap<>();
		map.put("appmodelId", Constant.appmodelIdy);
		map.put("pageNum", 1);
		map.put("pageSize", 10);
		map.put("orderState", 0);
		MockMvcUtil.sendRequest("/Order/findOrderManager", JSONObject.toJSONString(map), null, "post");
	}

	//后台查询近3个月的订单
	@Test
	public void findOrderManager1() {
		Map<String, Object> map = new HashMap<>();
		map.put("appmodelId", Constant.appmodelIdy);
		map.put("pageNum", 1);
		map.put("pageSize", 10);
		map.put("orderState", 6);
		MockMvcUtil.sendRequest("/Order/findOrderManager", JSONObject.toJSONString(map), null, "post");
	}

	//后台查询按商品名称查询
	@Test
	public void findOrderManager2() {
		Map<String, Object> map = new HashMap<>();
		map.put("appmodelId", Constant.appmodelIdy);
		map.put("pageNum", 1);
		map.put("pageSize", 10);
		map.put("orderState", 0);
		map.put("productName", "1111");
		MockMvcUtil.sendRequest("/Order/findOrderManager", JSONObject.toJSONString(map), null, "post");
	}

	//后台查询时间段查询
	@Test
	public void findOrderManager3() {
		Map<String, Object> map = new HashMap<>();
		map.put("appmodelId", Constant.appmodelIdy);
		map.put("pageNum", 1);
		map.put("pageSize", 10);
		map.put("orderState", 0);
		map.put("payTime", "2018-05-03,2018-07-27");
		MockMvcUtil.sendRequest("/Order/findOrderManager", JSONObject.toJSONString(map), null, "post");
	}

	//后台退款中的售后订单
	@Test
	public void findRefundOrderManager() {
		Map<String, Object> map = new HashMap<>();
		map.put("appmodelId", Constant.appmodelIdy);
		map.put("pageNum", 1);
		map.put("pageSize", 10);
		map.put("detailId", "");
		MockMvcUtil.sendRequest("/Order/findRefundOrderManager", JSONObject.toJSONString(map), null, "post");
	}

	//后台更新订单
	@Test
	public void updateOrder() {
		UpdateOrderVo orderVo = new UpdateOrderVo();
		orderVo.setOrderId(1532504297973209L);
		orderVo.setTelPhone("1511232123");
		MockMvcUtil.sendRequest("/Order/updateOrder", JSONObject.toJSONString(orderVo), null, "put");
	}

	//后台查询待发货数,申请数等等
	@Test
	public void OrderPayOkSum() {
		Map<String, Object> map = new HashMap<>();
		map.put("appmodeId", Constant.appmodelIdy);
		MockMvcUtil.sendRequest("/Order/OrderPayOkSum", JSONObject.toJSONString(map), null, "post");
	}

	//后台批量备注订单
	@Test
	public void batchBackRemrk() {
		List<Long> list = new ArrayList<>();
		list.add(1528956239614211L);
		list.add(1528956275465157L);
		list.add(1528953972635839L);
		Map<String, Object> map = new HashMap<>();
		map.put("orderIds", "1528956239614211,1528956275465157,1528953972635839,1528953687192183");
		map.put("backRemark", "1");
		map.put("coverType", false);
		MockMvcUtil.sendRequest("/Order/batchBackRemrk", JSONObject.toJSONString(map), null, "post");
	}

	@Test
	public void add() {
        /*用户ID：WxuserId
        收货人：   userName
        联系电话：  telephone
        备注： remark
        收货地址：   userAddress
        支付价：   payFee
        总价：  totalFee
        配送方式: distributeMode                                商家配送，物流配送
        模板ID： appmodelId*/
		Order order = new Order();
		order.setWxuserId(1528246624180515L);
		order.setUserName("吴辉煌");
		order.setTelPhone("15888030961");
		order.setRemark("");
		order.setUserAddress("大学广场78号-1-9");
		order.setPayFee(new BigDecimal("0.01"));
		order.setTotalFee(new BigDecimal("100"));
		order.setDistributeMode("商家配送");
		order.setAppmodelId(Constant.appmodelIdy);

		order.setGroupMemberId(-1);
		order.setWxuserId(1528246624180515L);

		List<ProductOrderVo> productList = new ArrayList<>();
		ProductOrderVo product1 = new ProductOrderVo();
		product1.setProductId(111L);
		product1.setProductName("商品1");
		product1.setProductImg("商品圖片11111111111");
		product1.setBuyQuantity(1);
		product1.setBuyPrice(100.0);
		//product1.setProductType("1000,2000");
		product1.setProductSpecItemInfo("：productSpecItemInfo ：{--------------------转json字符串");

		productList.add(product1);

		Map<String, Object> map = new HashMap<>();
		map.put("order", order);
		map.put("productList", productList);
		map.put("couponIds", "");
		map.put("groupMemberId", -1);
		SaveOrderVo saveOrderVo = new SaveOrderVo();
		//saveOrderVo.setOrderInfo(order);
		saveOrderVo.setProductList(productList);
		MockMvcUtil.sendRequest("/Order/saveOrder", JSONObject.toJSONString(saveOrderVo), null, "post");
		String s = JSONObject.toJSONString(map);
		System.out.println(s);
        /*商品数组：productList: [{
            商品ID：productId
            商品图片:productImg
            商品名称：productName
            商品购买数量：buyQuantity
            商品购买价格:	  buyPrice
            商品参类型:productType：[1000,2000]

            商品选中的规格信息：productSpecItemInfo   ：{		---------------------转json字符串
                编号：productSpecItemId
                规格值：specificationValue
                规格名称：specificationName
                价格：price
                库存：stock
            }}
    }]*/

	}


	/**
	 * 后台发货
	 */
	@Test
	public void deliverGoods() {
		List<Order> orders = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		Order o1 = new Order();
		o1.setOrderId(1530858043979071L);
		o1.setWlCode("wlcode");
		o1.setWlNum("wlnum");
		o1.setWlName("wlname");
		o1.setDistributeMode("配送方式");
		o1.setDeliveryStaff("{Distributor:\"z张三\",phone:\"18368094914\",DeliveryTime:\"2018-06-26 15:55:39\"}");
		orders.add(o1);
		map.put("orders", orders);
		MockMvcUtil.sendRequest("/Order/deliverGoods", JSONObject.toJSONString(map), null, "post");
	}

	/**
	 * 查询协商记录
	 */
	@Test
	public void refoundHistory() {
		Map<String, Object> map = new HashMap<>();
		map.put("detailId", 1531186006584964L);
		MockMvcUtil.sendRequest("/Order/refoundHistory", JSONObject.toJSONString(map), null, "post");
	}


	/**
	 * 提醒买家发货
	 */
	@Test
	public void warnSellerSendProduct() {
		Map<String, Object> map = new HashMap<>();
		map.put("orderId", 1L);
		MockMvcUtil.sendRequest("/Order/warnSellerSendProduct", JSONObject.toJSONString(map), null, "post");
	}

}