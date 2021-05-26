package com.medusa.basemall.shop.controller;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.shop.entity.Notice;
import com.medusa.basemall.shop.service.NoticeService;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class NoticeControllerTest extends BasemallApplicationTests {

    @Autowired
    NoticeService noticeService;

    /**
     * 新增公告测试
     */
    @Test
    public void saveOrUpdate_saveTest() {
        Integer old = noticeService.selectByAppmodelId(Constant.appmodelIdy).size();
        Notice notice = new Notice();
        notice.setTitle("now");
        notice.setAppmodelId(Constant.appmodelIdy);
        JSONObject post = MockMvcUtil.sendRequest("/NoticeVO/saveOrUpdate/v1",
                JSONObject.toJSONString(notice), null, "post");

        Assert.assertEquals(old + 1,noticeService.selectByAppmodelId(Constant.appmodelIdy).size());
    }

    /**
     * 更新公告测试
     */
    @Test
    public void saveOrUpdate_updateTest() {
        Notice notice = noticeService.findById(27);
        notice.setTitle("我修改了");
        JSONObject post = MockMvcUtil.sendRequest("/Notice/saveOrUpdate/v1",
                JSONObject.toJSONString(notice), null, "post");

        Assert.assertEquals("我修改了", noticeService.findById(27).getTitle());

    }

    /**
     * 根据AppmodelId分页查询公告测试
     */
    @Test
    public void findByAppmodelIdTest() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("pageNum", "2");
        map.add("pageSize", "4");
        map.add("appmodelId", Constant.appmodelIdy);
        JSONObject post = MockMvcUtil.sendRequest("/Notice/findByAppmodelId/v1",
        "", map, "get");

        JSONObject data = JSONObject.parseObject(post.getString("data"));
        Assert.assertEquals("6",data.getString("total"));
        Assert.assertEquals("2",data.getString("size"));
    }

    /**
     * 根据Id查询公告测试
     */
    @Test
    public void findByIdTest() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("noticeId","27");
        JSONObject post = MockMvcUtil.sendRequest("/NoticeVO/findById/v1",
                "", map, "get");

        Notice notice = JSONObject.parseObject(post.getString("data"), Notice.class);
        Assert.assertEquals(notice.getTitle(),noticeService.findById(27).getTitle());
    }

    /**
     * 批量删除公告测试
     */
    @Test
    public void batchDeleteTest() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("noticeIds", "50");
        JSONObject post = MockMvcUtil.sendRequest("/NoticeVO/batchDelete/v1",
                "", map, "delete");

        Assert.assertEquals(4,noticeService.selectByAppmodelId(Constant.appmodelIdy).size());
    }
}
