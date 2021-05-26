package com.medusa.basemall.shop.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.shop.entity.ColumnFlag;
import com.medusa.basemall.shop.service.ColumnFlagService;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author Created by medusawx on 2018/06/05.
 * @栏目开关测试
 */
public class ColumnFlagControllerTest extends BasemallApplicationTests {

    @Autowired
    ColumnFlagService columnFlagService;

    /**
     * 查询栏目开关设置测试
     */
    @Test
    public void findByAppmodelIdTest() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("appmodelId", Constant.appmodelIdy);
        JSONObject post = MockMvcUtil.sendRequest("/ColumnFlag/findByAppmodelId/v1",
                "", map, "get");


        JSONObject data = JSONObject.parseObject(post.getString("data"));
        Assert.assertEquals(Constant.appmodelIdy, data.getString("appmodelId"));
    }

    /**
     * 打开/关闭开关测试
     */
    @Test
    public void updateFlagTest() {
        ColumnFlag columnFlag = columnFlagService.findById(5);
        columnFlag.setClassifyFlag(false);
        JSONObject post = MockMvcUtil.sendRequest("/ColumnFlag/updateFlag/v1",
                JSON.toJSONString(columnFlag), null, "put");

        ColumnFlag columnFlagNew = columnFlagService.findById(5);
        Assert.assertSame(false, columnFlagNew.getClassifyFlag());
    }
}
