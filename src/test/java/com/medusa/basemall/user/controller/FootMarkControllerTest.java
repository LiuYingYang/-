package com.medusa.basemall.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.user.dao.FootMarkRepository;
import com.medusa.basemall.user.entity.FootMark;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import com.medusa.basemall.utils.RegexMatches;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FootMarkControllerTest extends BasemallApplicationTests {


    @Test
    public void add() {
        for (int i = 0; i < 30; i++) {
            FootMark footMark = new FootMark();
            footMark.setAppmodelId(Constant.appmodelId);
            footMark.setWxuserId(1111);
            footMark.setImgUrl(RegexMatches.getRandomChar(128));
            footMark.setMaxPrice(RegexMatches.getRadomDouble());
            footMark.setMinPrice(RegexMatches.getRadomDouble());
            footMark.setProductId(11L + i);
            footMark.setProductName("商品" + RegexMatches.getInt(200, 1));
            List<Integer> integers = new ArrayList<>();
            integers.add(1000);
            integers.add(2000);
            //footMark.setProductTypeList(integers);
            footMark.setRemark(RegexMatches.getRandomChar(50));
            MockMvcUtil.sendRequest("/footmark/add", JSONObject.toJSONString(footMark), null, "post");
        }
       /* for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                FootMark footMark = new FootMark();
                footMark.setAppmodelId(Constant.appmodelId);
                footMark.setWxuserId(1110+i);
                footMark.setImgUrl(RegexMatches.getRandomChar(128));
                footMark.setMaxPrice(RegexMatches.getRadomDouble());
                footMark.setMinPrice(RegexMatches.getRadomDouble());
                footMark.setProductId(11L);
                footMark.setProductName("商品"+RegexMatches.getInt(200,1));
                List<Integer> integers = new ArrayList<>();
                integers.add(1000);
                integers.add(2000);
                footMark.setProductTypeList(integers);
                footMark.setRemark(RegexMatches.getRandomChar(50));
                MockMvcUtil.sendRequest("/footmark/add",JSONObject.toJSONString(footMark),null,"post");
            }
        }*/
    }

    @Autowired
    private FootMarkRepository footMarkRepository;

    @Test
    public void test() {
        Long wxuserId = 1111L;
        String appmodelId = Constant.appmodelId;
        QSort orders = new QSort();
        Sort sort = new Sort(Sort.Direction.DESC, "update_time");
        orders.and(sort);
        Pageable pageRequest = new QPageRequest(0, 10, orders);
        Page<FootMark> page = footMarkRepository.findByWxuserIdAndAppmodelId(wxuserId, appmodelId, pageRequest);
        System.out.println("==================");
        System.out.println(JSONObject.toJSONString(page.getContent()));
    }


    @Test
    public void list() {
        Map<String, Object> map = new HashMap<>();
        map.put("wxuserId", 1111L);
        map.put("appmodelId", Constant.appmodelId);
        map.put("pageNum", 1);
        map.put("pageSize", 2);
        MockMvcUtil.sendRequest("/footmark/list", JSONObject.toJSONString(map), null, "post");
    }

    @Test
    public void delete() {
        Map<String, Object> map = new HashMap<>();
        map.put("wxuserId", 1111);
        map.put("footmarkId", "5b123c1dbb99b2442473a67c");
        map.put("type", 2);
        MockMvcUtil.sendRequest("/footmark/delete", JSONObject.toJSONString(map), null, "post");
    }
}
