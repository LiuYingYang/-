package com.medusa.basemall.user.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.user.entity.MemberActiveRecharge;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import com.medusa.basemall.utils.RegexMatches;
import org.junit.Test;

import java.util.*;

public class MemberActiveRechargeControllerTest extends BasemallApplicationTests {

    @Test
    public void basicFunction() {
        //添加充值记录
        int size = 10;
        for (int i = 0; i < size; i++) {
            MemberActiveRecharge activeRecharge = new MemberActiveRecharge();
            activeRecharge.setActiveName(RegexMatches.getRandomChar(3));
            activeRecharge.setMaxPrice(100D);
            activeRecharge.setSendPrice(20000D);
            activeRecharge.setAppmodelId(Constant.appmodelId);
            MockMvcUtil.sendRequest("/member/active/recharge/add", JSONObject.toJSONString(activeRecharge), null, "post");
        }

        //查找最后添加的size条数据
        Map<String, Object> map = new HashMap<>();
        map.put("appmodeId", Constant.appmodelId);
        JSONObject post = MockMvcUtil.sendRequest("/member/active/recharge/list", JSONObject.toJSONString(map), null, "post");
        String object = post.getString("data");
        List<MemberActiveRecharge> ActiveRecharges = JSONArray.parseArray(object, MemberActiveRecharge.class);
        Collections.sort(ActiveRecharges, Comparator.comparing(MemberActiveRecharge::getCreateTime));


        //批量开启关闭
        StringBuffer ids2 = new StringBuffer();
        ActiveRecharges.forEach(obj -> {
            obj.setState(1);
            ids2.append(obj.getActiveRechargeId() + ",");
        });
        Map<String, Object> map3 = new HashMap<>();
        String substring3 = ids2.substring(0, ids2.length() - 1);
        map3.put("ids", substring3);
        map3.put("type", 1);
        MockMvcUtil.sendRequest("/member/active/recharge/OpenActive", JSONObject.toJSONString(map3), null, "post");


        MemberActiveRecharge activeRecharge = ActiveRecharges.get(0);
        activeRecharge.setState(0);
        MockMvcUtil.sendRequest("/member/active/recharge/update", JSONObject.toJSONString(activeRecharge), null, "post");

        //批量删除
        StringBuffer ids = new StringBuffer();
        for (int i = 0; i < size / 2; i++) {
            MemberActiveRecharge a = ActiveRecharges.get(i);
            ids.append(a.getActiveRechargeId() + ",");
            System.out.print("删除id为 : " + a.getActiveRechargeId());
        }
        System.out.println();
        Map<String, Object> map2 = new HashMap<>();
        String substring = ids.substring(0, ids.length() - 1);
        map2.put("ids", substring);
        MockMvcUtil.sendRequest("/member/active/recharge/delete", JSONObject.toJSONString(map2), null, "post");
    }

    @Test
    public void rechargeRecordList() {
        Map<String, Object> map = new HashMap<>();
        map.put("page", 0);
        map.put("size", 0);
        map.put("memberId", 1);
        MockMvcUtil.sendRequest("/member/recharge/record/list", JSONObject.toJSONString(map), null, "post");
    }

}