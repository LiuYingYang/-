package com.medusa.basemall.shop.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.shop.entity.Footer;
import com.medusa.basemall.shop.service.FooterService;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author Created by medusawx on 2018/06/05.
 * @底部导航测试
 */
public class FooterControllerTest extends BasemallApplicationTests {

    @Autowired
    FooterService footerService;

    /**
     * 查询底部导航测试
     */
    @Test
    public void findByAppmodelIdTest() {
        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
        map.add("appmodelId", Constant.appmodelIdy);
        JSONObject post = MockMvcUtil.sendRequest("/Footer/findByAppmodelId/v1",
                "", map, "get");

        Assert.assertEquals(5,footerService.findByAppmoedelId(Constant.appmodelIdy).size());
    }

    /**
     * 编辑/开启关闭底部导航测试
     */
    @Test
    public void updateTest() {
        Footer footer = footerService.findById(8);
        footer.setFooterFlag(false);
        JSONObject post = MockMvcUtil.sendRequest("/Footer/update/v1",
                JSON.toJSONString(footer), null, "put");

        Assert.assertEquals(false,footerService.findById(8).getFooterFlag());
    }
}
