package com.medusa.basemall.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.user.entity.Member;
import com.medusa.basemall.user.entity.MemberGroupCategory;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberGruopCategoryControllerTest extends BasemallApplicationTests {


    /**
     *
     */
    @Test
    public void setMemberToGroup() {
        List<Member> members = new ArrayList<>();
        Member member1 = new Member();
        member1.setAppmodelId(Constant.appmodelId);
        member1.setMemberId(1527317624015161L);
        members.add(member1);

        List<MemberGroupCategory> memberGroupCategories = new ArrayList<>();
        MemberGroupCategory memberGroupCategory1 = new MemberGroupCategory();
        memberGroupCategory1.setGroupId(53);
        MemberGroupCategory memberGroupCategory2 = new MemberGroupCategory();
        memberGroupCategory2.setGroupId(52);
        MemberGroupCategory memberGroupCategory3 = new MemberGroupCategory();
        memberGroupCategory3.setGroupId(54);
        memberGroupCategories.add(memberGroupCategory1);
        memberGroupCategories.add(memberGroupCategory2);

        Map<String, Object> map = new HashMap();
        map.put("members", members);
        map.put("memberGroupCategories", memberGroupCategories);
        System.out.println(JSONObject.toJSONString(map));
        MockMvcUtil.sendRequest("/member/gruop/category/setMemberToGroup", JSONObject.toJSONString(map), null, "post");
    }
}