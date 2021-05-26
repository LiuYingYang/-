package com.medusa.basemall.order.controller;


import com.alibaba.fastjson.JSON;
import com.medusa.basemall.order.vo.OrderInfo;
import com.medusa.basemall.order.vo.ProductOrderVo;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utils.RegexMatches;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrderGenerateTest {

	private TestRestTemplate testRestTemplate;

	@Test
	public void generateOrder() {
		Map<String,Object> map = new HashMap<>();
		OrderInfo orderInfo =new OrderInfo();
		orderInfo.setTelPhone(RegexMatches.getTel());
		orderInfo.setPayFee(new BigDecimal(100.0));
		orderInfo.setRemark("备注");
		orderInfo.setUserAddress(RegexMatches.getRoad());
		orderInfo.setWlPrice(new BigDecimal(10));
		orderInfo.setOrderType("");
		orderInfo.setAppmodelId(Constant.appmodelIdy);
		orderInfo.setDistributeMode("商家配送");
		orderInfo.setDeliveryStaff("{\"Distributor\":\"\",\"phone\":\"\",\"DeliveryTime\":\"\"}");
		orderInfo.setFactpayWxuserId(1531212601449327L);
		orderInfo.setWxuserId(1531212601449327L);
		orderInfo.setWxuserCouponId(null);
		orderInfo.setGroupState(-1);
		orderInfo.setGroupMemberId(null);
		orderInfo.setGroupId(null);

		List<ProductOrderVo> productList = new ArrayList<>();
		ProductOrderVo productOrderVo = new ProductOrderVo();
		productOrderVo.setProductSpecItemInfo("");
		productOrderVo.setAppmodelId(Constant.appmodelIdy);
		productOrderVo.setBuyPrice(20.0);
		productOrderVo.setBuyQuantity(5);
		productOrderVo.setPrice(300.0);
		productOrderVo.setProductImg("/medusa/basemall/S00020001wxa75115dccbe8ecec/image20180816150813352-47.jpg");
		productOrderVo.setProductId(1534402533009687L);
		productOrderVo.setProductName("SINCE THEN新款时尚欧美耳饰耳坠夸张圈圈羽毛耳环");
		productOrderVo.setRemark("备注");
		productOrderVo.setSalesVolume(0);
		productOrderVo.setShelfState(0);
		productOrderVo.setSpecType(false);
		productOrderVo.setStock(10);
		productList.add(productOrderVo);
		map.put("orderInfo",orderInfo);
		map.put("productList",productList);
		System.out.println(JSON.toJSONString(map));
//		//headers
//		HttpHeaders requestHeaders = new HttpHeaders();
//		requestHeaders.add("api-version", "1.0");
//		HttpEntity<MultiValueMap> multiValueMapHttpEntity = new HttpEntity<>(map,requestHeaders);
//
//		Result result = testRestTemplate.postForObject("/Order/saveOrder/v1",map, Result.class);
//		Assert.assertEquals(result.getCode(), 100);
//		assert result.getData().toString().length() < 16 || result.getData().toString().length() > 16;
	}

}
