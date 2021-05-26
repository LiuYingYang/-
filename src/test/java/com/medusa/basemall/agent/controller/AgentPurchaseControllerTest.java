package com.medusa.basemall.agent.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.agent.entity.AgentPurchase;
import com.medusa.basemall.order.entity.Buycar;
import com.medusa.basemall.product.entity.ProductSpecItem;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import com.medusa.basemall.utils.RegexMatches;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class AgentPurchaseControllerTest extends BasemallApplicationTests {

    @Test
    public void findPurchase() {
    }

    @Test
    public void batchDelete() {
        Map<String, Object> map = new HashMap<>();
        //map.put("buycarIds", "5b16444cbb99b25e0cb562f9,5b16444cbb99b25e0cb562fa");
        map.put("wxuserId", 12345L);
        map.put("type", 2);
        JSONObject post = MockMvcUtil.sendRequest("/agent/purchase/batchDelete", JSON.toJSONString(map), null, "post");
    }

    @Test
    public void addBurCar2() {
        Buycar buycar = new Buycar();
        buycar.setAppmodelId(Constant.appmodelId);
        buycar.setProductId(1527818113535098L);
        buycar.setProductImg("/medusa/basemall/S00020001wxa75115dccbe8ecec/image20180601095510775-243.jpg");
        buycar.setProductName("商品标题2");
        buycar.setQuantity(1);
        buycar.setWxuserId(1528246692051902L);
        ProductSpecItem p = new ProductSpecItem();
        p.setPrice(5.0);
        p.setSkuImg("/medusa/basemall/S00020001wxa75115dccbe8ecec/image20180601095510775-243.jpg");
        p.setStock(10);
        p.setProductSpecItemId(111L);
        buycar.setProductSpecItemInfo(p);
        MockMvcUtil.sendRequest("/agent/purchase/addProductToAgentPurchase", JSON.toJSONString(buycar), null, "post");

    }

    @Test
    public void addProductToAgentPurchase() {
        for (int i = 10; i < 20; i++) {
            AgentPurchase purchase = new AgentPurchase();
            purchase.setAppmodelId(Constant.appmodelId);
            purchase.setProductId(10L + i);
            purchase.setProductImg("imgurl:" + RegexMatches.getRandomChar(1));

            //商品规格设置
            ProductSpecItem p = new ProductSpecItem();
            p.setAppmodelId(Constant.appmodelId);
            p.setPrice(500D);
            p.setSpecificationValue("白色,xxml");
            p.setStock(1000);
            p.setProductSpecItemId(1000L);

            purchase.setProductSpecItemInfo(p);
            purchase.setQuantity(1);
            purchase.setWxuserId(12345L);


            String s = JSON.toJSONString(purchase);
            System.out.println(s);
            MockMvcUtil.sendRequest("/agent/purchase/addProductToAgentPurchase", JSON.toJSONString(purchase), null, "post");
        }
    }



    @Test
    public void findPurchaseSum() {
    }

    @Test
    public void updatePurchase() {
    }
}