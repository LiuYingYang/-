package com.medusa.basemall.agent.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.agent.vo.PitchonProduct;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import com.medusa.basemall.utils.RegexMatches;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgentProductControllerTest extends BasemallApplicationTests {

    @Test
    public void createAgentProduct() {
        Map<String,Object> map = new HashMap<>();
        map.put("productIds","1527751305558135,1531039346422673,1531968359437691,1531452035375383");
        JSONObject post = MockMvcUtil.sendRequest("/Product/pitchon", JSON.toJSONString(map), null, "post");
        List<PitchonProduct> data = JSON.parseArray(post.getString("data"), PitchonProduct.class);
        data.forEach(obj->{
            if(obj.getSpecType().equals(true)){
                obj.getSpecVos().forEach(specVo->{
                    specVo.setAgentStock(RegexMatches.getInt(1000,100));
                });
            }else{
                obj.setAgentStock(RegexMatches.getInt(1000,100));
            }
        });
        map.put("data",data);
       MockMvcUtil.sendRequest("/agent/product/createAgentProduct", JSON.toJSONString(map), null, "post");
    }

    @Test
    public void delete() {
        Map<String,Object> map = new HashMap<>();
        map.put("agentProductIds","45,46");
        MockMvcUtil.sendRequest("/agent/product/delete", JSON.toJSONString(map), null, "post");
    }


    @Test
    public void update() {
        Map<String,Object> map = new HashMap<>();
        map.put("agentProductIds","47,48");
        map.put("type",1);
        MockMvcUtil.sendRequest("/agent/product/update", JSON.toJSONString(map), null, "post");
    }

    @Test
    public void list() {
        Map<String,Object> map = new HashMap<>();
        map.put("appmodelId",Constant.appmodelIdy);
        map.put("type",0);
        map.put("pageNum",0);
        map.put("pageSize",0);
        map.put("productName","");
        MockMvcUtil.sendRequest("/agent/product/list", JSON.toJSONString(map), null, "post");
    }


}