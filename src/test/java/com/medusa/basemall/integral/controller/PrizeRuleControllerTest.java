package com.medusa.basemall.integral.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.integral.dao.PrizeRuleMapper;
import com.medusa.basemall.integral.entity.PrizeRule;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

public class PrizeRuleControllerTest extends BasemallApplicationTests {

    @Resource
    PrizeRuleMapper prizeRuleMapper;

    /**
     *  积分获得规则表编辑测试
     */
    @Test
    public void updateTest() {
        PrizeRule prizeRule = prizeRuleMapper.selectByPrimaryKey(1);
        prizeRule.setTypeOne(1);
        prizeRule.setTypeTwo(1);
        prizeRule.setTypeThreePay(1);
        prizeRule.setTypeThreeGet(1);
        prizeRule.setInfo("积分规则");
        prizeRule.setIndate(12);
        JSONObject post = MockMvcUtil.sendRequest("/prize/rule/update",
                JSON.toJSONString(prizeRule), null, "post");
    }

    /**
     * 积分获得规则表获取测试
     */
    @Test
    public void findByAppmodelIdTest() {
        Map<String, Object> object = new HashMap<>();
        object.put("appmodelId", Constant.appmodelIdy);
        JSONObject post = MockMvcUtil.sendRequest("/prize/rule/findByAppmodelId",
                JSON.toJSONString(object), null, "post");
    }
}
