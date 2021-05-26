package com.medusa.basemall.integral.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.integral.entity.PrizeOrder;
import com.medusa.basemall.integral.service.PrizeOrderService;
import com.medusa.basemall.integral.vo.PrizeOrderVo;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PrizeOrdeoControllerTest extends BasemallApplicationTests {

    @Autowired
    PrizeOrderService prizeOrderService;



    /**
     * 批量备注订单测试
     */
    @Test
    public void batchUpdateRemarkTest() {
        Map<String, Object> object = new HashMap<>();
        object.put("prizeOrderIds", "1532064330651478,1532064605863593");
        object.put("backRemark", "批量备注");
        JSONObject post = MockMvcUtil.sendRequest("/prize/order/batchUpdateRemark",
                JSON.toJSONString(object), null, "post");
    }


    /**
     * 批量发货测试
     */
    @Test
    public void batchUpdateStateTest() {
        Map<String, Object> object = new HashMap<>();
        List<PrizeOrder> prizeOrders = new ArrayList<>();
        PrizeOrder prizeOrder = prizeOrderService.findById(1532064330651478L);
        prizeOrder.setDistriMode("物流配送");
        prizeOrder.setWlCode("");
        prizeOrder.setWlNum("");
        prizeOrder.setDeliveryStaff("XXX");
        prizeOrders.add(prizeOrder);
        object.put("prizeOrders",JSONObject.toJSONString(prizeOrders));
        JSONObject post = MockMvcUtil.sendRequest("/prize/order/batchUpdateState",
                JSON.toJSONString(object), null, "post");
    }

    /**
     * 查询积分订单测试
     */
    @Test
    public void findOrderByAppmodelIdTest() {
        PrizeOrderVo prizeOrderVo = new PrizeOrderVo();
        prizeOrderVo.setOrderType(1);
        prizeOrderVo.setOrderState(2);
        prizeOrderVo.setAppmodelId(Constant.appmodelIdy);
        prizeOrderVo.setPageNum(1);
        prizeOrderVo.setPageSize(10);
        JSONObject post = MockMvcUtil.sendRequest("/prize/order/findOrderByAppmodelId",
                JSON.toJSONString(prizeOrderVo), null, "post");

    }

    /**
     * 查询积分订单测试
     */
    @Test
    public void findPrizeOrderTest() {
        PrizeOrderVo prizeOrderVo = new PrizeOrderVo();
        prizeOrderVo.setOrderType(1);
        prizeOrderVo.setWxuserName("w");
        prizeOrderVo.setOrderState(1);
        prizeOrderVo.setAppmodelId(Constant.appmodelIdy);
        prizeOrderVo.setPageNum(1);
        prizeOrderVo.setPageSize(10);
        JSONObject post = MockMvcUtil.sendRequest("/prize/order/findPrizeOrder",
                JSON.toJSONString(prizeOrderVo), null, "post");

    }
}
