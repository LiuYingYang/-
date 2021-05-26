package com.medusa.basemall.integral.controller;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class PrizeSurveyControllerTest extends BasemallApplicationTests {

    /**
     * 查询热销商品测试
     */
    @Test
    public void selectHotProductTest() {
        Map<String, Object> object = new HashMap<>();
        object.put("appmodelId",(Constant.appmodelIdy));
        JSONObject post = MockMvcUtil.sendRequest("/prize/survey/selectHotProduct",
                JSONObject.toJSONString(object), null, "post");
    }

    /**
     * 交易记录测试
     */
    @Test
    public void selectPrizeOrderTest(){
        Map<String, Object> object = new HashMap<>();
        object.put("appmodelId",Constant.appmodelIdy);
        object.put("pageNum", 1);
        object.put("pageSize", 3);
        JSONObject post = MockMvcUtil.sendRequest("/prize/survey/selectPrizeOrder",
                JSONObject.toJSONString(object), null, "post");
    }

    /**
     * 营销统计测试
     */
    @Test
    public void selectCountTest(){
        Map<String, Object> object = new HashMap<>();
        object.put("appmodelId",Constant.appmodelIdy);
        object.put("startDate", "2018-07-22 00:00:00");
        object.put("endDate", "2018-07-23 00:00:00");
        JSONObject post = MockMvcUtil.sendRequest("/prize/survey/selectCount",
                JSONObject.toJSONString(object), null, "post");
    }

    /**
     * 实时统计测试
     */
    @Test
    public void selectRealTime(){
        Map<String, Object> object = new HashMap<>();
        object.put("appmodelId",Constant.appmodelIdy);
        JSONObject post = MockMvcUtil.sendRequest("/prize/survey/selectRealTime",
                JSONObject.toJSONString(object), null, "post");
    }
}
