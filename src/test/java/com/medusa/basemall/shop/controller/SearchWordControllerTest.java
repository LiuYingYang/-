package com.medusa.basemall.shop.controller;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.shop.entity.SearchWord;
import com.medusa.basemall.shop.service.SearchWordService;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class SearchWordControllerTest extends BasemallApplicationTests {

    @Autowired
    SearchWordService searchWordService;
    /**
     * 新增搜索词测试
     */
    @Test
    public void saveOrUpdate_saveTest() {
        SearchWord searchWord = new SearchWord();
        searchWord.setKeyword("滴滴");
        searchWord.setWordtype(false);
        searchWord.setAppmodelId(Constant.appmodelIdy);
        JSONObject post = MockMvcUtil.sendRequest("/SearchWord/saveOrUpdate/v1",
                JSONObject.toJSONString(searchWord), null, "post");

        Assert.assertEquals("已存在10个普通搜索词,无法再添加", post.getString("message"));
        Assert.assertEquals(11,searchWordService.findByAppmodelId(Constant.appmodelIdy).size());
    }

    /**
     * 修改搜索词测试
     */
    @Test
    public void saveOrUpdate_updateTest() {
        SearchWord searchWord = searchWordService.findById(2);
        searchWord.setKeyword("滴滴我修改了");
        JSONObject post = MockMvcUtil.sendRequest("/SearchWord/saveOrUpdate/v1",
                JSONObject.toJSONString(searchWord), null, "post");

        Assert.assertEquals("滴滴我修改了",searchWordService.findById(2).getKeyword());
    }

    /**
     * 根据AppmodelId查询搜索词测试
     */
    @Test
    public void findByAppmodelIdTest() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("appmodelId", Constant.appmodelIdy);
        JSONObject post = MockMvcUtil.sendRequest("/SearchWord/findByAppmodelId/v1",
                "", map, "get");
    }

    /**
     * 批量删除搜索词测试
     */
    @Test
    public void batchDeleteTest() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("searchWordIds", "11,12");
        JSONObject post = MockMvcUtil.sendRequest("/SearchWord/batchDelete/v1",
                "", map, "delete");

        Assert.assertEquals("删除成功", post.getString("data"));
        Assert.assertEquals(9,searchWordService.findByAppmodelId(Constant.appmodelIdy).size());
    }
}
