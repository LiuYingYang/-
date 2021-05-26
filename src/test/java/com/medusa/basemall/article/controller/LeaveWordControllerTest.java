package com.medusa.basemall.article.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.article.dao.LeaveWordDao;
import com.medusa.basemall.article.entity.LeaveWord;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class LeaveWordControllerTest extends BasemallApplicationTests {
    @Autowired
    LeaveWordDao leaveWordDao;

    /**
     * 留言测试
     */
    @Test
    public void addTest(){
        LeaveWord leaveWord = new LeaveWord();
        leaveWord.setAppmodelId(Constant.appmodelId);
        leaveWord.setArticleId("5b404a77de70293a94963381");
        leaveWord.setWxuserId(1528246692051902L);
        leaveWord.setWxuserName("王兴");
        leaveWord.setLeaveInfo("留言3");
        JSONObject post = MockMvcUtil.sendRequest("/leaveWord/add",
                JSONObject.toJSONString(leaveWord), null, "post");
    }

    /**
     * 删除留言测试
     */
    @Test
    public void deleteTest(){
        LeaveWord leaveWord = new LeaveWord();
        leaveWord.setLeaveWordId("5b62d65e3268edd65bd8aee0");
        JSONObject post = MockMvcUtil.sendRequest("/leaveWord/delete",
                JSON.toJSONString(leaveWord), null, "post");
    }


    /**
     * 留言回复测试
     */
    @Test
    public void update_deleteTest(){
        LeaveWord leaveWord = new LeaveWord();
        leaveWord.setLeaveWordId("5b62d65e3268edd65bd8aee0");
        JSONObject post = MockMvcUtil.sendRequest("/leaveWord/update",
                JSONObject.toJSONString(leaveWord), null, "post");
    }


    /**
     * 留言回复测试
     */
    @Test
    public void update_replyTest(){
        LeaveWord leaveWord = new LeaveWord();
        leaveWord.setLeaveWordId("5b4054c3de70292b7cd9c754");
        leaveWord.setReplyInfo("嗨你好");
        JSONObject post = MockMvcUtil.sendRequest("/leaveWord/update",
                JSONObject.toJSONString(leaveWord), null, "post");
    }

    /**
     * 设置精选测试
     */
    @Test
    public void update_choicenessTest(){
        Map<String, Object> object = new HashMap<>();
        object.put("leaveWordId", "5b4054c3de70292b7cd9c754");
        object.put("choiceness", 0);
        JSONObject post = MockMvcUtil.sendRequest("/leaveWord/update",
                JSONObject.toJSONString(object), null, "post");
    }

    /**
     * 留言置顶测试
     */
    @Test
    public void sort(){
        Map<String, Object> object = new HashMap<>();
        object.put("leaveWordId", "5b4054abde702905e0ebf996");
        object.put("sortType",1);
        JSONObject post = MockMvcUtil.sendRequest("/leaveWord/sort",
                JSONObject.toJSONString(object), null, "post");
    }

    /**
     * 留言分页查询测试
     */
    @Test
    public void selectLaterTest(){
        Map<String, Object> object = new HashMap<>();
        object.put("articleId", "5b5191aef3f1e5908bf7a171");
        JSONObject post = MockMvcUtil.sendRequest("/leaveWord/selectLater",
                JSONObject.toJSONString(object), null, "post");
    }

    /**
     * 小程序留言查询测试
     */
    @Test
    public void selectBeforeTest(){
        Map<String, Object> object = new HashMap<>();
        object.put("articleId", "5b404a77de70293a94963381");
        JSONObject post = MockMvcUtil.sendRequest("/leaveWord/selectBefore",
                JSONObject.toJSONString(object), null, "post");
    }



    /**
     * 用户留言查询测试
     */
    @Test
    public void selectMineTest(){
        Map<String, Object> object = new HashMap<>();
        object.put("articleId", "5b404a77de70293a94963381");
        object.put("wxuserId", 1528246692051902L);
        JSONObject post = MockMvcUtil.sendRequest("/leaveWord/selectMine",
                JSONObject.toJSONString(object), null, "post");
    }

    /**
     * 根据输入条件查询留言
     */
    @Test
    public void findLeaveWord(){
        Map<String, Object> object = new HashMap<>();
        object.put("appmodelId", "S00020001wxa75115dccbe8ecec");
        object.put("findWord", "1");
        JSONObject post = MockMvcUtil.sendRequest("/leaveWord/findLeaveWord",
                JSONObject.toJSONString(object), null, "post");
    }
}
