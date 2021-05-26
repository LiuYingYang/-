package com.medusa.basemall.order.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.order.entity.Buycar;
import com.medusa.basemall.product.entity.ProductSpecItem;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import com.medusa.basemall.utils.RegexMatches;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BuycarControllerTest extends BasemallApplicationTests {

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
        buycar.setProductSpecItemInfo(p);
        MockMvcUtil.sendRequest("/Buycar/addProductToBuycar", JSON.toJSONString(buycar), null, "post");
    }

    /**
     * 相同商品相同规格      数量累加    更新购物车
     * 相同商品不相同规格                添加购物
     * 不相同商品                       添加购物车
     */
    @Test
    public void addBurCar() {
        for (int i = 10; i < 20; i++) {
            Buycar buycar = new Buycar();
            buycar.setAppmodelId(Constant.appmodelId);
            buycar.setProductId(10L + i);
            buycar.setProductImg("imgurl:" + RegexMatches.getRandomChar(1));

            //商品规格设置
            ProductSpecItem p = new ProductSpecItem();
            p.setAppmodelId(Constant.appmodelId);
            p.setPrice(500D);
            p.setSpecificationValue("白色,xxml");
            p.setStock(1000);
            p.setProductSpecItemId(1000L);
            buycar.setProductSpecItemInfo(p);

            buycar.setQuantity(1);
            buycar.setWxuserId(12345L);

            List<Integer> scope = new ArrayList<>();
            scope.add(1000);
            scope.add(2000);
            //buycar.setProductTypeList(scope);

            String s = JSON.toJSONString(buycar);
            System.out.println(s);
            MockMvcUtil.sendRequest("/Buycar/addProductToBuycar", JSON.toJSONString(buycar), null, "post");
        }

        /*for (int i = 0; i < 10; i++) {
            Buycar buycar = new Buycar();
            buycar.setAppmodelId(Constant.appmodelId);
            buycar.setProductId(10L);
            buycar.setProductImg("imgurl:" + RegexMatches.getRandomChar(1));

            //商品规格设置
            ProductSpecItem p = new ProductSpecItem();
            p.setAppmodelId(Constant.appmodelId);
            p.setPrice(500D);
            p.setSpecificationValue("白色,xxml");
            p.setStock(1000);
            p.setProductSpecItemId(1000L);
            buycar.setProductSpecInfo(p);

            buycar.setShelfState(1);
            buycar.setQuantity(1);
            buycar.setWxuserId(12345L);

            List<Integer> scope = new ArrayList<>();
            scope.add(1000);
            scope.add(2000);
            buycar.setProductTypeList(scope);
            JSONObject post = MockMvcUtil.sendRequest("/Buycar/addProductToBuycar", JSON.toJSONString(buycar), null, "post");
        }*/
    }

    @Test
    public void batchDelete() {
        Map<String, Object> map = new HashMap<>();
        map.put("buycarIds", "5b16444cbb99b25e0cb562f9,5b16444cbb99b25e0cb562fa");
        //map.put("wxuserId", 12355);
        map.put("type", 1);
        JSONObject post = MockMvcUtil.sendRequest("/Buycar/batchDelete", JSON.toJSONString(map), null, "post");
    }


    @Test
    public void findBuyCars() {
        Buycar buycar = new Buycar();
        buycar.setAppmodelId(Constant.appmodelId);
        buycar.setWxuserId(12345L);
        JSONObject post = MockMvcUtil.sendRequest("/Buycar/findPurchase", JSON.toJSONString(buycar), null, "post");
    }


    @Test
    public void findBurCarSum() {
        Buycar buycar = new Buycar();
        buycar.setWxuserId(1528246692051902L);
        JSONObject post = MockMvcUtil.sendRequest("/Buycar/findPurchaseSum", JSON.toJSONString(buycar), null, "post");
    }


    @Test
    public void updateBurCar() {
        Buycar buycar = new Buycar();
        buycar.setBuycarId("5b1b7737c82dbcd661d9a982");
        buycar.setQuantity(20);
        buycar.setWxuserId(1528246692051902L);
        buycar.setAppmodelId("S00020001wxa75115dccbe8ecec");
        buycar.setProductId(1527751305558135L);
        buycar.setShelfState(0);
        ProductSpecItem productSpecItem = new ProductSpecItem();
        productSpecItem.setPrice(10.0);
        productSpecItem.setSpecificationName("蓝色,11kg,xl");
        productSpecItem.setSkuImg("/medusa/basemall/S00020001wxa75115dccbe8ecec/image20180609093112244-500.jpg");
        productSpecItem.setStock(100);
        buycar.setProductSpecItemInfo(productSpecItem);
        JSONObject post = MockMvcUtil.sendRequest("/Buycar/updatePurchase", JSON.toJSONString(buycar), null, "post");
    }


}