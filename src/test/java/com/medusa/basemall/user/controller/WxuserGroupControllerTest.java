package com.medusa.basemall.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.user.entity.Wxuser;
import com.medusa.basemall.user.entity.WxuserGroup;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import com.medusa.basemall.utils.RegexMatches;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WxuserGroupControllerTest extends BasemallApplicationTests {

    @Test
    public void basicFunction() {
        for (int i = 0; i < 5; i++) {
            WxuserGroup wxuserGroup = new WxuserGroup();
            wxuserGroup.setWxGroupName(RegexMatches.getRandomChar(3));
            wxuserGroup.setAppmodelId(Constant.appmodelId);
            MockMvcUtil.sendRequest("/wxuser/group/createGroup", JSONObject.toJSONString(wxuserGroup), null, "post");
        }

        WxuserGroup wxuserGroup = new WxuserGroup();
        wxuserGroup.setAppmodelId(Constant.appmodelId);
        JSONObject post = MockMvcUtil.sendRequest("/wxuser/group/list", JSONObject.toJSONString(wxuserGroup), null, "post");
        String data = post.getString("data");

        List<WxuserGroup> collects = JSONObject.parseArray(data, WxuserGroup.class);

        WxuserGroup wxuserGroup1 = new WxuserGroup();
        wxuserGroup1.setWxuserGroupId(collects.get(0).getWxuserGroupId());
        MockMvcUtil.sendRequest("/wxuser/group/deleteGroup", JSONObject.toJSONString(wxuserGroup1), null, "post");

        WxuserGroup wxuserGroup2 = new WxuserGroup();
        wxuserGroup2.setWxuserGroupId(collects.get(1).getWxuserGroupId());
        wxuserGroup2.setWxGroupName("分组111");
        MockMvcUtil.sendRequest("/wxuser/group/update", JSONObject.toJSONString(wxuserGroup2), null, "post");

        List<Wxuser> wxusers = new ArrayList<>();
        Wxuser wxuser1 = new Wxuser();
        wxuser1.setWxuserId(1528246624180515L);
        wxuser1.setAppmodelId(Constant.appmodelIdy);
        wxusers.add(wxuser1);

        List<WxuserGroup> groups = new ArrayList<>();
        groups.add(collects.get(2));
        groups.add(collects.get(3));
        Map<String, Object> map = new HashMap<>();
        map.put("wxusers", wxusers);
        map.put("wxuserGroups", groups);
        MockMvcUtil.sendRequest("/wxuser/group/category/setWxuserToGroup", JSONObject.toJSONString(map), null, "post");
    }

    @Test
    public void list() {
        WxuserGroup wxuserGroup = new WxuserGroup();
        wxuserGroup.setAppmodelId(Constant.appmodelId);
        MockMvcUtil.sendRequest("/wxuser/group/list", JSONObject.toJSONString(wxuserGroup), null, "post");
    }

    @Test
    public void createGroup() {
        for (int i = 0; i < 5; i++) {
            WxuserGroup wxuserGroup = new WxuserGroup();
            wxuserGroup.setWxGroupName(RegexMatches.getRandomChar(3));
            wxuserGroup.setAppmodelId(Constant.appmodelId);
            MockMvcUtil.sendRequest("/wxuser/group/createGroup", JSONObject.toJSONString(wxuserGroup), null, "post");
        }

    }

    @Test
    public void deleteGroup() {
        WxuserGroup wxuserGroup = new WxuserGroup();
        wxuserGroup.setWxuserGroupId(1);
        MockMvcUtil.sendRequest("/wxuser/group/deleteGroup", JSONObject.toJSONString(wxuserGroup), null, "post");
    }

    @Test
    public void update() {
        WxuserGroup wxuserGroup = new WxuserGroup();
        wxuserGroup.setWxuserGroupId(2);
        wxuserGroup.setWxGroupName("分组111");
        MockMvcUtil.sendRequest("/wxuser/group/update", JSONObject.toJSONString(wxuserGroup), null, "post");
    }


    @Test
    public void setWxuserToGroup() {

        List<Wxuser> wxusers = new ArrayList<>();
        Wxuser wxuser1 = new Wxuser();
        wxuser1.setWxuserId(1528246624180515L);
        wxuser1.setAppmodelId(Constant.appmodelIdy);
        wxusers.add(wxuser1);

        /*Wxuser wxuser2 = new Wxuser();
        wxuser2.setWxuserId(2L);
        wxuser2.setAppmodelId(Constant.appmodelIdy);
        wxusers.add(wxuser2);*/
        List<WxuserGroup> groups = new ArrayList<>();
        WxuserGroup wxuserGroup1 = new WxuserGroup();
        wxuserGroup1.setWxuserGroupId(2);
        groups.add(wxuserGroup1);
        WxuserGroup wxuserGroup2 = new WxuserGroup();
        wxuserGroup2.setWxuserGroupId(3);
        groups.add(wxuserGroup2);
        Map<String, Object> map = new HashMap<>();
        map.put("wxusers", wxusers);
        map.put("wxuserGroups", groups);
        MockMvcUtil.sendRequest("/wxuser/group/category/setWxuserToGroup", JSONObject.toJSONString(map), null, "post");
    }


}