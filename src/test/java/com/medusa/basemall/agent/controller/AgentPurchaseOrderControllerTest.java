package com.medusa.basemall.agent.controller;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.agent.vo.AgentProductOrderVo;
import com.medusa.basemall.agent.vo.PayAgentOrderVo;
import com.medusa.basemall.agent.vo.SaveAgentOrderVo;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import com.vip.vjtools.vjkit.number.RandomUtil;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgentPurchaseOrderControllerTest extends BasemallApplicationTests {

	@Test
	public void saveOrder() {
		PayAgentOrderVo payAgentOrderVo = new PayAgentOrderVo();
		SaveAgentOrderVo  orderVo = new SaveAgentOrderVo();
		orderVo.setAddress(RandomUtil.randomAsciiFixLength(3));
		orderVo.setAppmodelId(Constant.appmodelIdy);
		orderVo.setPayFee(new BigDecimal(0.01));
		orderVo.setTotalFee(new BigDecimal(300));
		orderVo.setWxuserId(1531212601449327L);
		payAgentOrderVo.setOrder(orderVo);
		AgentProductOrderVo agentProductOrderVo = new AgentProductOrderVo();
		agentProductOrderVo.setPrice(10.0);
		agentProductOrderVo.setQuantity(1);
		agentProductOrderVo.setProductId(1527751305558135L);
		agentProductOrderVo.setProductImg("123");
		agentProductOrderVo.setProductName("商品1");
		agentProductOrderVo.setProductSpecInfo("{\"specificationValue\":\"\",\"skuImg\":\"/medusa/basemall/S00020001wxa75115dccbe8ecec/image20180531152135048-218.jpg\",\"activityStock\":0,\"productId\":0,\"price\":0,\"appmodelId\":\"\",\"agentStock\":0,\"stock\":560,\"productSpecItemId\":1530496795561573,\"specificationName\":\"\"}");
		agentProductOrderVo.setSpecType(true);
		List<AgentProductOrderVo> productOrderVos = new ArrayList<>();
		productOrderVos.add(agentProductOrderVo);
		payAgentOrderVo.setProductList(productOrderVos);
		JSONObject post = MockMvcUtil
				.sendRequest("/agent/purchase/order/v1/generate", JSONObject.toJSONString(payAgentOrderVo), null, "post");
		String data = post.getString("data");
	}
	@Test
	public void batchBackRemrk() {
		Map<String,Object> map = new HashMap<>();
		map.put("purchaseOrderIds","1533012095233024,1533092722216011,1533017425266562");
		map.put("backRemark","22");
		map.put("coverType",false);
		MockMvcUtil
				.sendRequest("/agent/purchase/order/v1/batchBackRemrk", JSONObject.toJSONString(map), null, "put");
	}

	@Test
	public void addres() {
		Map<String,Object> map = new HashMap<>();
		map.put("purchaseOrderId","1533012095233024");
		map.put("addres","22");
		MockMvcUtil
				.sendRequest("/agent/purchase/order/v1/addres", JSONObject.toJSONString(map), null, "put");
	}

}