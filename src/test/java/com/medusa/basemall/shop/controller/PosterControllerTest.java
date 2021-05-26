package com.medusa.basemall.shop.controller;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.shop.dao.PosterMapper;
import com.medusa.basemall.shop.entity.Poster;
import com.medusa.basemall.shop.vo.PosterSortVO;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;

public class PosterControllerTest extends BasemallApplicationTests {

    @Resource
    PosterMapper posterMapper;

    /**
     * 新增轮播图测试
     */
    @Test
    public void saveTest() {
        Integer old = posterMapper.findByAppmodelId(Constant.appmodelIdy).size();
        Poster poster = new Poster();
        poster.setJumpType(0);
        poster.setPosterImg("xxx.jpg");
        poster.setAppmodelId(Constant.appmodelIdy);
        JSONObject post = MockMvcUtil.sendRequest("/Poster/save/v1",
                JSONObject.toJSONString(poster), null, "post");

        Assert.assertEquals("已达上限，无法添加", post.getString("message"));
        Assert.assertSame(old , posterMapper.findByAppmodelId(Constant.appmodelIdy).size());
    }

    /**
     * 修改轮播图测试
     */
    @Test
    public void updateTest() {
        Poster poster = posterMapper.selectByPrimaryKey(8);
        poster.setPosterImg("修改了");
        JSONObject post = MockMvcUtil.sendRequest("/Poster/update/v1",
                JSONObject.toJSONString(poster), null, "put");

        Assert.assertEquals("修改了", posterMapper.selectByPrimaryKey(8).getPosterImg());
    }

    /**
     * 根据AppmodelId查询轮播图测试
     */
    @Test
    public void findByAppmodelIdTest() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("appmodelId", Constant.appmodelIdy);
        JSONObject post = MockMvcUtil.sendRequest("/Poster/findByAppmodelId/v1",
                "", map, "get");

        Assert.assertEquals(5, posterMapper.findByAppmodelId(Constant.appmodelIdy).size());
    }

    /**
     * 根据Id查询轮播图测试
     */
    @Test
    public void findByIdTest() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("posterId", "16");
        JSONObject post = MockMvcUtil.sendRequest("/Poster/findById/v1",
                "", map, "get");
    }

    /**
     * 批量删除轮播图测试
     */
    @Test
    public void batchDeleteTest() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("posterIds", "8,13");
        JSONObject post = MockMvcUtil.sendRequest("/Poster/batchDelete/v1",
                "", map, "delete");

        Assert.assertEquals(3, posterMapper.findByAppmodelId(Constant.appmodelIdy).size());
    }

    /**
     * 海报排序操作
     */
    @Test
    public void sort() {
        PosterSortVO posterSortVO = new PosterSortVO();
        posterSortVO.setAppmodelId(Constant.appmodelIdy);
        posterSortVO.setPosterId(16);
        posterSortVO.setHandleType(2);
        JSONObject post = MockMvcUtil.sendRequest("/Poster/sort/v1",
                JSONObject.toJSONString(posterSortVO), null, "put");

        Assert.assertEquals("上移成功",post.getString("data"));
        Assert.assertSame(1,posterMapper.selectByPrimaryKey(16).getSort());
    }
}
