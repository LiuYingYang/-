package com.medusa.basemall.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.user.dao.MemberMapper;
import com.medusa.basemall.user.dao.WxuserMapper;
import com.medusa.basemall.user.entity.Wxuser;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WxuserControllerTest extends BasemallApplicationTests {

    @Test
    public void searchVipWxuser() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageSize", 10);
        map.put("pageNum", 1);
        map.put("appmodelId", Constant.appmodelIdy);
        map.put("groupId", 105);
        MockMvcUtil.sendRequest("/wxuser/searchVipWxuser", JSONObject.toJSONString(map), null, "post");

//        Map<String, Object> map2 = new HashMap<>();
//        map2.put("pageSize",20);
//        map2.put("pageNum", 1);
//        map2.put("appmodelId", Constant.appmodelIdy);
//        map2.put("groupId","");
//        MockMvcUtil.sendRequest("/wxuser/searchWxuser", JSONObject.toJSONString(map2), null, "post");

    }

    @Test
    public void updateRemark() {
        Map<String, Object> map = new HashMap<>();
        map.put("wxuserIds", "1528249136347878,1528246692051902");
        map.put("markLevel", "");
        map.put("backRemark", "");
        map.put("appmodelId", Constant.appmodelIdy);
        map.put("coverType", 1);
        JSONObject post = MockMvcUtil.sendRequest("/wxuser/updateRemark", JSONObject.toJSONString(map), null, "post");
    }

    @Test
    public void update() {
        Map<String, Object> map = new HashMap<>();
        map.put("wxuserId", 1529457084350201L);
        map.put("nikeName", "23132");
        map.put("avatarUrl", "https://wx.qlogo.cn/mmopen/vi_32/icOgTJf22sD48OMiabrCXTUaxqma8YYuzjABnZ9kRicR1QH1cJdicscyzYzj2LTldicDibAgPWhDKrdOWkg7TT4QR2icg/132");
        map.put("appmodelId", "S00020001wxa75115dccbe8ecec");
        JSONObject post = MockMvcUtil.sendRequest("/wxuser/update", JSONObject.toJSONString(map), null, "post");
    }


    @Autowired
    private WxuserMapper tWxuserMapper;

    @Resource
    private MemberMapper tMemberMapper;

    @Test
    public void add() {
//        for (int i = 0; i < 21; i++) {
//            Wxuser wxuser = new Wxuser();
//            wxuser.setOpenId("bbc");
//            wxuser.setWxuserId(IdGenerateUtils.getItemId());
//            wxuser.setLastTime(TimeUtil.getNowTime());
//            wxuser.setCreateTime(TimeUtil.getNowTime());
//            wxuser.setAppmodelId(Constant.appmodelIdy);
//            wxuser.setSessionKey("1111");
//            //为用户创建未开通的会员,只做积分关联不生成其他数据
//            Member member = new Member();
//            member.setAppmodelId(Constant.appmodelIdy);
//            member.setWxuserId(wxuser.getWxuserId());
//            member.setMemberId(IdGenerateUtils.getItemId());
//            tMemberMapper.insertSelective(member);
//            wxuser.setMemberInfo(member);
//            tWxuserMapper.addUser(wxuser);
//        }
        Wxuser wxuser = new Wxuser();
        wxuser.setAppmodelId(Constant.appmodelIdy);
        int i = tWxuserMapper.selectCount(wxuser);
        System.out.println(i);
    }

    public static void main(String[] args) {
        List<Long> list = new ArrayList<>();
        list.add(123456L);
        list.add(12555555555L);
        list.add(188888888L);
        list.add(12345999999996L);
        String s = ArrayUtils.toString(list);
        System.out.println(s);
    }


}