package com.medusa.basemall.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.user.entity.Member;
import com.medusa.basemall.user.entity.MemberGroup;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import com.medusa.basemall.utils.RegexMatches;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberGroupControllerTest extends BasemallApplicationTests {


    @Test
    public void basicFunction() {
        for (int i = 0; i < 5; i++) {
            MemberGroup memberGroup = new MemberGroup();
            memberGroup.setGroupName(RegexMatches.getRandomChar(3));
            memberGroup.setAppmodelId(Constant.appmodelId);
            MockMvcUtil.sendRequest("/member/group/createGroup", JSONObject.toJSONString(memberGroup), null, "post");
        }

        MemberGroup memberGroup = new MemberGroup();
        memberGroup.setAppmodelId(Constant.appmodelId);
        JSONObject post = MockMvcUtil.sendRequest("/member/group/list", JSONObject.toJSONString(memberGroup), null, "post");
        String data = post.getString("data");

        List<MemberGroup> collects = JSONObject.parseArray(data, MemberGroup.class);

        MemberGroup wxuserGroup1 = new MemberGroup();
        wxuserGroup1.setGroupId(collects.get(0).getGroupId());
        MockMvcUtil.sendRequest("/member/group/deleteGroup", JSONObject.toJSONString(wxuserGroup1), null, "post");

        MemberGroup wxuserGroup2 = new MemberGroup();
        wxuserGroup2.setGroupId(collects.get(1).getGroupId());
        wxuserGroup2.setGroupName("分组111");
        MockMvcUtil.sendRequest("/member/group/update", JSONObject.toJSONString(wxuserGroup2), null, "post");

        List<Member> members = new ArrayList<>();
        Member member1 = new Member();
        member1.setAppmodelId(Constant.appmodelId);
        member1.setMemberId(1527317624015161L);
        members.add(member1);

        Map<String, Object> map = new HashMap();
        map.put("members", members);
        map.put("memberGroupCategories", collects);
        System.out.println(JSONObject.toJSONString(map));
        MockMvcUtil.sendRequest("/member/gruop/category/setMemberToGroup", JSONObject.toJSONString(map), null, "post");
    }

    /**
     * 创建组
     */
    @Test
    public void createGroup() {
        for (int i = 0; i < 5; i++) {
            MemberGroup memberGroup = new MemberGroup();
            memberGroup.setGroupName(RegexMatches.getRandomChar(3));
            memberGroup.setAppmodelId(Constant.appmodelId);
            MockMvcUtil.sendRequest("/member/group/createGroup", JSONObject.toJSONString(memberGroup), null, "post");
        }
    }

    /**
     * 删除组
     */
    @Test
    public void delete() {
        MemberGroup memberGroup = new MemberGroup();
        memberGroup.setGroupId(52);
        MockMvcUtil.sendRequest("/member/group/deleteGroup", JSONObject.toJSONString(memberGroup), null, "post");
    }

    /**
     * 更新组
     */
    @Test
    public void update() {
        MemberGroup memberGroup = new MemberGroup();
        memberGroup.setGroupId(53);
        memberGroup.setGroupName("aasd");
        MockMvcUtil.sendRequest("/member/group/update", JSONObject.toJSONString(memberGroup), null, "post");
    }

    /**
     * 删除组
     */
    @Test
    public void list() {
        MemberGroup memberGroup = new MemberGroup();
        memberGroup.setAppmodelId(Constant.appmodelId);
        MockMvcUtil.sendRequest("/member/group/list", JSONObject.toJSONString(memberGroup), null, "post");
    }
}