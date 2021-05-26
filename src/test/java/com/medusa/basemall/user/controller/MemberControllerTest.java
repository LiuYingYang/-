package com.medusa.basemall.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.user.entity.MemberRank;
import com.medusa.basemall.user.entity.MemberRankRule;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import com.medusa.basemall.utils.RegexMatches;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MemberControllerTest extends BasemallApplicationTests {


    @Test
    public void updateRemark() {

       /* //不覆盖备注
        Map<String,Object> map2 = new HashMap<>();
        map2.put("memberIds","1528246624181390,1528246692051879");
        map2.put("markLevel","");
        map2.put("backRemark","备22222注");
        map2.put("appmodelId",Constant.appmodelIdy);
        map2.put("coverType",0);
        MockMvcUtil.sendRequest("/member/updateRemark", JSONObject.toJSONString(map2), null, "post");
*/
        //覆盖备注
        Map<String, Object> map = new HashMap<>();
        map.put("memberIds", "1528246624181390,1528246692051879");
        map.put("markLevel", "");
        map.put("backRemark", "");
        map.put("appmodelId", Constant.appmodelIdy);
        map.put("coverType", 1);
        MockMvcUtil.sendRequest("/member/updateRemark", JSONObject.toJSONString(map), null, "post");
    }

    /**
     * 设置普通用户为会员,可选择分组或不分组
     */
    @Test
    public void setUserToMember() {
        Map<String, Object> map = new HashMap();
        map.put("memberId", 1531212601449249L);
        map.put("phone", "15888030961");
        map.put("groupIds", "");
        map.put("appmodelId", "S00020001wxa75115dccbe8ecec");
        MockMvcUtil.sendRequest("/member/setUserToMember", JSONObject.toJSONString(map), null, "post");
    }

    /**
     * 会员注册校验验证码
     */
    @Test
    public void validateCode() {
        Map map = new HashMap();
        map.put("memberId", 1527317624015161L);
        map.put("phone", "15888030961");
        map.put("code", "492996");
        map.put("appmodelId", "S00020001wxa75115dccbe8ecec");
        JSONObject post = MockMvcUtil.sendRequest("/member/validateCode", JSONObject.toJSONString(map), null, "post");
    }

    /**
     * 修改会员绑定的手机号
     */
    @Test
    public void updatePhone() {
        Map map = new HashMap();
        map.put("memberId", 1528944995164094L);
        map.put("phone", "13333333333");
        map.put("code", "000000");
        map.put("appmodelId", "S00020001wxa75115dccbe8ecec");
        JSONObject post = MockMvcUtil.sendRequest("/member/updatePhone", JSONObject.toJSONString(map), null, "post");
    }

    //创建会员规则
    @Test
    public void createRankRule() {
        MemberRankRule rankRule = new MemberRankRule();
        rankRule.setAppmodelId(Constant.appmodelIdy);
        rankRule.setValidity(12);
        rankRule.setExplainInfo(RegexMatches.getRandomChar(255));
        rankRule.setIntroduce(RegexMatches.getRandomChar(255));
        rankRule.setConsumeIntegral(1);
        rankRule.setLoginIntegral(5);
        rankRule.setShareIntegral(5);
        JSONObject post = MockMvcUtil.sendRequest("/member/rank/rule/createRankRule", JSONObject.toJSONString(rankRule), null, "post");
    }

    /**
     * 更新会员卡规则
     */
    @Test
    public void RankRuleUpdate() {
        MemberRankRule rankRule = new MemberRankRule();
        rankRule.setMemberRuleId(1);
        rankRule.setAppmodelId(Constant.appmodelIdy);
        rankRule.setValidity(12);
        rankRule.setExplainInfo(RegexMatches.getRandomChar(355));
        rankRule.setIntroduce(RegexMatches.getRandomChar(355));
        rankRule.setConsumeIntegral(1);
        rankRule.setLoginIntegral(10);
        rankRule.setShareIntegral(10);
        JSONObject post = MockMvcUtil.sendRequest("/member/rank/rule/update", JSONObject.toJSONString(rankRule), null, "post");
    }

    /**
     * 查询会员卡规则
     */
    @Test
    public void RankRuledetail() {
        Map<String, Object> map = new HashMap<>();
        map.put("appmodelId", Constant.appmodelIdy);
        JSONObject post = MockMvcUtil.sendRequest("/member/rank/rule/detail", JSONObject.toJSONString(map), null, "post");
    }

    /**
     * 新增会员卡等级
     */
    @Test
    public void RankCreate() {
        MemberRank memberRank = new MemberRank();
        memberRank.setRankName(RegexMatches.getRandomChar(3));
        memberRank.setDiscount(9.5);
        memberRank.setDeleteState(2);
        memberRank.setGrowthValue(0);
        memberRank.setAppmodelId(Constant.appmodelIdy);
        MockMvcUtil.sendRequest("/member/rank/createRank", JSONObject.toJSONString(memberRank), null, "post");

        MemberRank memberRank2 = new MemberRank();
        memberRank2.setRankName(RegexMatches.getRandomChar(3));
        memberRank2.setDiscount(8.5);
        memberRank2.setDeleteState(0);
        memberRank2.setGrowthValue(5000);
        memberRank2.setAppmodelId(Constant.appmodelIdy);
        MockMvcUtil.sendRequest("/member/rank/createRank", JSONObject.toJSONString(memberRank2), null, "post");

        MemberRank memberRank3 = new MemberRank();
        memberRank3.setRankName(RegexMatches.getRandomChar(3));
        memberRank3.setDiscount(7.5);
        memberRank3.setDeleteState(0);
        memberRank3.setGrowthValue(10000);
        memberRank3.setAppmodelId(Constant.appmodelIdy);
        MockMvcUtil.sendRequest("/member/rank/createRank", JSONObject.toJSONString(memberRank3), null, "post");
    }


    /**
     * 修改会员卡等级信息
     */
    @Test
    public void updateRank() {
        MemberRank memberRank = new MemberRank();
        memberRank.setRankId(20);
        memberRank.setGrowthValue(200);
        memberRank.setDiscount(9.7);
        JSONObject post = MockMvcUtil.sendRequest("/member/rank/updateRank", JSONObject.toJSONString(memberRank), null, "put");
    }

    /**
     * 删除会员卡等级信息
     */
    @Test
    public void deleteRank() {
        MemberRank memberRank = new MemberRank();
        memberRank.setRankId(19);
        JSONObject post = MockMvcUtil.sendRequest("/member/rank/deleteRank", JSONObject.toJSONString(memberRank), null, "post");
    }

    /**
     * 查询所有会员卡
     */
    @Test
    public void RankList() {
        Map<String, Object> map = new HashMap<>();
        map.put("appmodelId", Constant.appmodelId);
        MockMvcUtil.sendRequest("/member/rank/list", JSONObject.toJSONString(map), null, "post");
    }

    /**
     * 查询我的会员中心
     */
    @Test
    public void myMemberCore() {
        Map<String, Object> map = new HashMap<>();
        map.put("appmodelId", Constant.appmodelIdy);
        map.put("wxuserId", 1531212601449327L);
        MockMvcUtil.sendRequest("/member/myMemberCore", JSONObject.toJSONString(map), null, "post");
    }


}