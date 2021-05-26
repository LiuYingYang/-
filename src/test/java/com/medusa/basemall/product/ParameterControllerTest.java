package com.medusa.basemall.product;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.product.entity.Parameter;
import com.medusa.basemall.product.vo.ParameterVo;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import com.medusa.basemall.utils.RegexMatches;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParameterControllerTest extends BasemallApplicationTests {


    /**
     * 新增或更新商品属性
     */
    @Test
    public void saveOrUpdate() {
        ParameterVo parameterVo = new ParameterVo();
        parameterVo.setParamClassId(-1);
        parameterVo.setAppmodelId(Constant.appmodelId);
        parameterVo.setParamClassName("属性模板2");
        List<Parameter> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Parameter parameter = new Parameter();
            parameter.setParamClassId(-1);
            parameter.setParamId(-1);
            parameter.setParamKey("生产地" + i);
            parameter.setParamValue(RegexMatches.getRoad());
            list.add(parameter);
        }

        parameterVo.setParameterList(list);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        JSONObject post = MockMvcUtil.sendRequest("/Parameter/saveOrUpdate", JSON.toJSONString(parameterVo), map, "post");
        System.out.println(post.toJSONString());
    }

    /**
     * 查询商品属性
     */
    @Test
    public void findByAppmodelId() {

        Map<String, Object> object = new HashMap<>();
        object.put("appmodelId", Constant.appmodelId);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        JSONObject post = MockMvcUtil.sendRequest("/Parameter/findByAppmodelId", JSON.toJSONString(object), map, "post");
        System.out.println(post.toJSONString());
    }


    /**
     * 删除商品属性模板
     */
    @Test
    public void deleteById() {

        Map<String, Object> object = new HashMap<>();
        object.put("paramClassId", 3);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        JSONObject post = MockMvcUtil.sendRequest("/Parameter/deleteById", JSON.toJSONString(object), map, "post");
        System.out.println(post.toJSONString());
    }
}
