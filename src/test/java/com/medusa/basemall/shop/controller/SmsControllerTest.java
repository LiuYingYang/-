package com.medusa.basemall.shop.controller;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class SmsControllerTest extends BasemallApplicationTests {


    @Test
    public void getVerificationCcode() {
        Map<String,Object> map = new HashMap();
        map.put("phone","15888030961");
        map.put("appmodelId",Constant.appmodelId);
        map.put("type",4);
        MockMvcUtil.sendRequest("/sms/getVerificationCcode",JSONObject.toJSONString(map), null, "post");
    }

}