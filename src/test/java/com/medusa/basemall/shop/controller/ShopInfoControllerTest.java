package com.medusa.basemall.shop.controller;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.shop.dao.ShopInfoDao;
import com.medusa.basemall.shop.entity.ShopInfo;
import com.medusa.basemall.shop.entity.TopImg;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class ShopInfoControllerTest extends BasemallApplicationTests {
    @Resource
    ShopInfoDao shopInfoDao;


    /**
     * 保存店铺信息测试
     */
    @Test
    public void saveOrUpdate_saveTest() {
        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setShopName("wx2");
        shopInfo.setShopLogo("xx.jpg");
        shopInfo.setRemark("wx2");
        shopInfo.setColorStyle("255.255.255");
        shopInfo.setBusinessTime("早8-晚9");
        shopInfo.setShopLogo("xx.jpg");
        shopInfo.setWifiPass("479766");
        shopInfo.setWifiInfo("123456");
        shopInfo.setAppmodelId("9595");
        shopInfo.setWechatNumber("112480");
        shopInfo.setShopAddress("浙江");
        shopInfo.setTelephone("113857");
        TopImg topImgOne = new TopImg();
        topImgOne.setName("1");
        topImgOne.setUrl("xxx");
        TopImg topImgTwo = new TopImg();
        topImgTwo.setName("2");
        topImgTwo.setUrl("xxx");
        List<TopImg> topImgs = new ArrayList<>();
        topImgs.add(topImgOne);
        topImgs.add(topImgTwo);
        shopInfo.setTopImgs(topImgs);
        shopInfo.setAppmodelId(Constant.appmodelId);
        JSONObject post = MockMvcUtil.sendRequest("/ShopInfo/saveOrUpdate/v1",
                JSONObject.toJSONString(shopInfo), null, "post");
    }

    /**
     * 更新店铺信息
     */
    @Test
    public void saveOrUpdate_updateTest() {
        /*ShopInfo shopInfo = shopInfoDao.getByShopInfoId("5b2b648dde7029232c71a2fc");
        shopInfo.setShopName("lalala我修改了");*/
        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setShopInfoId("5b7d1416e6002f39ec252671");
        shopInfo.setTelephone("我修改了");
        JSONObject post = MockMvcUtil.sendRequest("/ShopInfo/saveOrUpdate/v1",
                JSONObject.toJSONString(shopInfo), null, "post");
    }

    /**
     * 根据appmodelId查询店铺信息测试
     */
    @Test
    public void findByAppmodelIdTest() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("appmodelId", Constant.appmodelIdy);
        JSONObject post = MockMvcUtil.sendRequest("/ShopInfo/findByAppmodelId/v1",
                "", map, "get");
    }
}
