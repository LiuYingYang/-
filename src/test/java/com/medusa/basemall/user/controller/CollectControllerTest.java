package com.medusa.basemall.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.user.dao.CollectRepository;
import com.medusa.basemall.user.entity.Collect;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import com.medusa.basemall.utils.RegexMatches;
import com.medusa.basemall.utils.TimeUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

public class CollectControllerTest extends BasemallApplicationTests {

    @Autowired
    CollectRepository collectRepository;


    @Test
    public void basicFunction() {
        for (int i = 0; i < 2; i++) {
            Collect collect = new Collect();
            collect.setWxuserId(111L);
            collect.setImgUrl(RegexMatches.getRandomChar(200));
            collect.setAppmodelId(Constant.appmodelId);
            collect.setMaxPrice(RegexMatches.getRadomDouble());
            collect.setMinPrice(RegexMatches.getRadomDouble());
            collect.setProductId(1L);
            collect.setCreateTime(TimeUtil.getNowTime());
            collect.setProductName("商品" + i);
            List<Integer> integers = new ArrayList<>();
            integers.add(1000);
            integers.add(2000);
            //collect.setProductTypeList(integers);
            MockMvcUtil.sendRequest("/collect/add", JSONObject.toJSONString(collect), null, "post");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("wxuserId", 111L);
        map.put("appmodelId", Constant.appmodelId);
        map.put("pageNum", 1);
        map.put("pageSize", 2);
        JSONObject post = MockMvcUtil.sendRequest("/collect/list", JSONObject.toJSONString(map), null, "post");
        String data = post.getString("data");
        List<Collect> collects = JSONObject.parseArray(data, Collect.class);
        System.out.println(collects.toString());

        Map<String, Object> map2 = new HashMap<>();
        map2.put("collectId", collects.get(0).getCollectId());
        MockMvcUtil.sendRequest("/collect/detailed", JSONObject.toJSONString(map2), null, "post");


        Map<String, Object> map4 = new HashMap<>();
        map4.put("wxuserId", 111L);
        map4.put("productId", 1L);
        MockMvcUtil.sendRequest("/collect/findWxuserCollect", JSONObject.toJSONString(map4), null, "post");

        Map<String, Object> map3 = new HashMap<>();
        map3.put("collectId", collects.get(0).getCollectId());
        MockMvcUtil.sendRequest("/collect/delete", JSONObject.toJSONString(map3), null, "post");


    }

    @Test
    public void addTest() {
        for (int i = 0; i < 20; i++) {
            Collect collect = new Collect();
            collect.setWxuserId(111L);
            collect.setImgUrl(RegexMatches.getRandomChar(200));
            collect.setAppmodelId(Constant.appmodelId);
            collect.setMaxPrice(RegexMatches.getRadomDouble());
            collect.setMinPrice(RegexMatches.getRadomDouble());
            collect.setProductId(1L);
            collect.setCreateTime(TimeUtil.getNowTime());
            collect.setProductName("商品" + i);
            List<Integer> integers = new ArrayList<>();
            integers.add(1000);
            integers.add(2000);
           //collect.setProductTypeList(integers);
            collectRepository.save(collect);
        }
    }

    @Test
    public void deleteTest() {
        Collect collect = new Collect();
        collect.setCollectId("5b123f61bb99b25fb47dca8a");
        collectRepository.delete(collect);
    }

    @Test
    public void detailedTest() {
        Optional<Collect> byId = collectRepository.findById("5b123f61bb99b25fb47dca8c");
        System.out.println(byId.isPresent());
    }

    @Test
    public void list() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("wxuserId","1531212601449327");
        params.add("pageNum","1");
        params.add("pageSize","10");
       MockMvcUtil.sendRequest("/collect/list","", params, "get");
    }



    @Test
    public void listTest() {
        QSort orders = new QSort();
        Sort sort = new Sort(Sort.Direction.DESC, "create_time");
        orders.and(sort);
        Pageable pageRequest = new QPageRequest(0, 10, orders);
        Page<Collect> b = collectRepository.findByWxuserIdAndAppmodelId(111L, Constant.appmodelId, null);
        List<Collect> content = b.getContent();
        System.out.println("content " + content.size());
        System.out.println(b.getTotalElements());
    }


}