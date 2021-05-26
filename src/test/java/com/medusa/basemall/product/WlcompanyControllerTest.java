package com.medusa.basemall.product;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.utiles.MockMvcUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class WlcompanyControllerTest extends BasemallApplicationTests {

    @Test
    public void list() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", 1);
        map.put("pageSize", 10);
	    JSONObject jsonObject = MockMvcUtil.sendRequest("/wlcompany/list/v1", JSON.toJSONString(map), null, "post");
	    Integer code = jsonObject.getInteger("code");
	    assertEquals("请求失败:" +jsonObject.getString("messages"), new Integer(100),code);
    }
}