package com.medusa.basemall.order.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.order.dao.CommonlyUsedRepository;
import com.medusa.basemall.order.entity.Buycar;
import com.medusa.basemall.order.entity.CommonlyUsed;
import com.medusa.basemall.order.service.BuycarService;
import com.medusa.basemall.order.vo.OrderSurveyVo;
import com.medusa.basemall.product.dao.ProductMapper;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.util.List;

public class OrderSurveyControllerTest extends BasemallApplicationTests {

    @Resource
    OrderSurveyController orderSurveyController;

    @Resource
    ProductMapper productMapper;

    @Resource
    BuycarService buycarService;

    @Resource
    private CommonlyUsedRepository commonlyUsedRepository;


    @Test
    public void selectCountTest() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("appmodelId", Constant.appmodelIdy);
        map.add("startDate", "");
        map.add("endDate", "");
        JSONObject post = MockMvcUtil.sendRequest("/order/survey/selectCount/v1",
                "", map, "get");

        JSONObject data = JSONObject.parseObject(post.getString("data"));

        Assert.assertEquals("2", data.getString("allOverOrder"));
        Assert.assertEquals("200", data.getString("allPrice"));
        Assert.assertEquals("314", data.getString("allBuyCarNumber"));
    }

    @Test
    public void selectRealTimeTest() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("appmodelId", Constant.appmodelIdy);
        JSONObject post = MockMvcUtil.sendRequest("/order/survey/selectRealTime/v1",
                "", map, "get");

        JSONObject data = JSONObject.parseObject(post.getString("data"));
        Assert.assertEquals("8", data.getString("todayBuyCarNumber"));
    }

    @Test
    public void selectHotProductTest() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("appmodelId", Constant.appmodelIdy);
        JSONObject post = MockMvcUtil.sendRequest("/order/survey/selectHotProduct/v1",
                "", map, "get");

        Assert.assertSame(3, productMapper.selectHotProduct(Constant.appmodelIdy).size());
        Assert.assertSame(40, productMapper.selectHotProduct(Constant.appmodelIdy).get(0).getSalesVolume());
        Assert.assertSame(30, productMapper.selectHotProduct(Constant.appmodelIdy).get(1).getSalesVolume());
        Assert.assertSame(10, productMapper.selectHotProduct(Constant.appmodelIdy).get(2).getSalesVolume());
    }

    @Test
    public void selectRealTimeBuycarListTest() {
        OrderSurveyVo orderSurveyVo = new OrderSurveyVo();
        orderSurveyVo.setAppmodelId(Constant.appmodelIdy);
        List<Buycar> buycarList = buycarService.selectAllBuyCarNumber(orderSurveyVo);
        Result result = orderSurveyController.selectRealTimeBuycarList(JSONObject.toJSONString(buycarList), 1, 10);
        System.out.println(result);
        Assert.assertEquals("7", JSONObject.parseObject(JSONObject.toJSONString(result.getData())).getString("total"));
    }

    @Test
    public void selectCommonlyUsedTest() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("appmodelId", Constant.appmodelIdy);
        JSONObject post = MockMvcUtil.sendRequest("/order/survey/selectCommonlyUsed/v1",
                "", map, "get");
    }

    @Test
    public void updateCommonlyUsedTest() {
        CommonlyUsed commonlyUsed = commonlyUsedRepository.findByAppmodelId(Constant.appmodelIdy);
        commonlyUsed.setCommonlyUseds("1,2");
        JSONObject post = MockMvcUtil.sendRequest("/order/survey/updateCommonlyUsed/v1",
                JSON.toJSONString(commonlyUsed), null, "put");
    }
}
