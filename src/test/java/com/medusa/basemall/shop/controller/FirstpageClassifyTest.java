package com.medusa.basemall.shop.controller;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.shop.entity.FirstpageClassify;
import com.medusa.basemall.shop.service.FirstpageClassifyService;
import com.medusa.basemall.shop.vo.ClassifySortVO;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * @author Created by medusawx on 2018/06/05.
 * @首页分类测试
 */
public class FirstpageClassifyTest extends BasemallApplicationTests {
    @Autowired
    FirstpageClassifyService firstpageClassifyService;

    /**
     * 添加首页分类测试
     */
    @Test
    public void addTest() {
        FirstpageClassify firstpageClassify = new FirstpageClassify();
        firstpageClassify.setClassifyName("1");
        firstpageClassify.setClassifyImg("xxx");
        firstpageClassify.setLinkType(1);
        firstpageClassify.setAppmodelId(Constant.appmodelIdy);
        JSONObject post = MockMvcUtil.sendRequest("/FirstpageClassify/add/v1",
                JSONObject.toJSONString(firstpageClassify), null, "post");

        Assert.assertEquals(8,firstpageClassifyService.findByAppmodelId(Constant.appmodelIdy).size());

        Assert.assertEquals("已达上限，无法添加",post.getString("message"));
    }

    /**
     * 更新首页分类测试
     */
    @Test
    public void updateTest() {
        FirstpageClassify firstpageClassify = firstpageClassifyService.findById(26);
        firstpageClassify.setClassifyName("名字修改了");
        JSONObject post = MockMvcUtil.sendRequest("/FirstpageClassify/update/v1",
                JSONObject.toJSONString(firstpageClassify), null, "put");

        Assert.assertEquals("更新成功", post.getString("data"));

        Assert.assertEquals("名字修改了", firstpageClassifyService.findById(26).getClassifyName());
    }

    /**
     * 首页分类查询测试
     */
    @Test
    public void findByAppmodelIdTest() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("appmodelId", Constant.appmodelIdy);
        JSONObject post = MockMvcUtil.sendRequest("/FirstpageClassify/findByAppmodelId/v1",
                "", map, "get");

        List<FirstpageClassify> firstpageClassifies = JSONObject.parseArray(post.getString("data"), FirstpageClassify.class);
        Assert.assertEquals(4, firstpageClassifies.size());
    }

    /**
     * 首页分类查询（根据Id)测试
     */
    @Test
    public void findByClassifyIdTest() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("classifyId", "17");
        JSONObject post = MockMvcUtil.sendRequest("/FirstpageClassify/findByClassifyId/v1",
                "", map, "get");

        FirstpageClassify firstpageClassify = JSONObject.parseObject(post.getString("data"), FirstpageClassify.class);
        Assert.assertEquals("默认分类导航1", firstpageClassify.getClassifyName());
    }

    /**
     * 批量删除首页分类测试
     */
    @Test
    public void batchDelete() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("classifyIds", "17");
        map.add("appmodelId", Constant.appmodelIdy);
        JSONObject post = MockMvcUtil.sendRequest("/FirstpageClassify/batchDelete/v1",
                "", map, "delete");

        Assert.assertEquals("删除失败,首页分类导航数量不能少于4", post.getString("message"));
    }

    @Test
    public void sortTest() {
        ClassifySortVO classifySortVO = new ClassifySortVO();
        classifySortVO.setAppmodelId(Constant.appmodelIdy);
        classifySortVO.setClassifyId(26);
        classifySortVO.setHandleType(2);
        JSONObject post = MockMvcUtil.sendRequest("/FirstpageClassify/sort/v1",
                JSONObject.toJSONString(classifySortVO), null, "put");

        Assert.assertEquals("上移成功", post.getString("data"));

        Assert.assertSame(1,firstpageClassifyService.findById(26).getSort());
    }

}
