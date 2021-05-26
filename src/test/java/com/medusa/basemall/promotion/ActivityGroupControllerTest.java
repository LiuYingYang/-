package com.medusa.basemall.promotion;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.product.entity.ProductSpecItem;
import com.medusa.basemall.product.vo.ProductWxVo;
import com.medusa.basemall.promotion.entity.Group;
import com.medusa.basemall.promotion.vo.ActivityGroupVo;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import com.medusa.basemall.utils.TimeUtil;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityGroupControllerTest extends BasemallApplicationTests {

    /**
     * 查询可供团购选择的商品测试
     */
    @Test
    public void findProductForGroupTest() {
        Map<String, Object> object = new HashMap<>();
        object.put("pageNum", 1);
        object.put("pageSize", 10);
        object.put("appmodelId", Constant.appmodelId);
        object.put("startDate", TimeUtil.getNowTime());
        object.put("endDate", TimeUtil.getMonth(2));
        object.put("overlayState", false);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        JSONObject post = MockMvcUtil.sendRequest("/ActivityGroup/findProductForGroup", JSON.toJSONString(object), map, "post");
        System.out.println(post.toJSONString());
    }

    /**
     * 创建团购活动测试
     */
    @Test
    public void saveTest() {

        List<ProductWxVo> productWxVoList = new ArrayList<>();

        ProductWxVo productWxVo = new ProductWxVo();
        /*productWxVo.setActivityPrice(2.00);*/
        productWxVo.setProductId(1535185853453380L);
        productWxVo.setActivityDiscount(0.60);
        productWxVo.setMaxQuantity(10);
       /* productWxVo.setActivityStock(300);*/

        List<ProductSpecItem> productSpecItemList = new ArrayList<>();
        ProductSpecItem productSpecItem1 = new ProductSpecItem();
        productSpecItem1.setProductSpecItemId(1535185853502909L);
        productSpecItem1.setActivityStock(50);
        ProductSpecItem productSpecItem2 = new ProductSpecItem();
        productSpecItem2.setProductSpecItemId(1535185853505934L);
        productSpecItem2.setActivityStock(100);
   /*     ProductSpecItem productSpecItem3 = new ProductSpecItem();
        productSpecItem3.setProductSpecItemId(1530260770493763L);
        productSpecItem3.setActivityStock(300);
        ProductSpecItem productSpecItem4 = new ProductSpecItem();
        productSpecItem4.setProductSpecItemId(1530260770503681L);
        productSpecItem4.setActivityStock(400);*/
        productSpecItemList.add(productSpecItem1);
        productSpecItemList.add(productSpecItem2);
       /* productSpecItems.add(productSpecItem3);
        productSpecItems.add(productSpecItem4);*/
        productWxVo.setProductSpecItemList(productSpecItemList);
        productWxVoList.add(productWxVo);

        ActivityGroupVo activityGroupVo = new ActivityGroupVo();
        activityGroupVo.setProductList(productWxVoList);
        activityGroupVo.setAppmodelId(Constant.appmodelIdy);
        activityGroupVo.setActivityGroupName("拼团活动测试1");
        activityGroupVo.setActivityImg(Constant.imgUrl);
        activityGroupVo.setActivityRemark("1");
        String start = TimeUtil.DATETIMEFORMAT.format((System.currentTimeMillis() + 30000));
        activityGroupVo.setStartDate(start);
        String end = TimeUtil.DATETIMEFORMAT.format((System.currentTimeMillis() + 90000));
        activityGroupVo.setEndDate(end);
        activityGroupVo.setLimitNum(2);//人数
        activityGroupVo.setLimitTime(1);//小时
        activityGroupVo.setOverlayState(false);
        JSONObject post = MockMvcUtil.sendRequest("/ActivityGroup/save/v1",
                JSON.toJSONString(activityGroupVo), null, "post");
        System.out.println(post.toJSONString());
    }

    /**
     * 查询团购活动测试
     */
    @Test
    public void findByAppmodelIdTest() {
        Map<String, Object> object = new HashMap<>();
        object.put("appmodelId", Constant.appmodelIdy);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        JSONObject post = MockMvcUtil.sendRequest("/ActivityGroup/findByAppmodelId", JSON.toJSONString(object), map, "post");
        System.out.println(post.toJSONString());
    }

    /**
     * 批量删除团购活动测试
     */
    @Test
    public void batchDeleteTest() {
        Map<String, Object> object = new HashMap<>();
        List<Integer> activityGroupIds = new ArrayList<>();
        activityGroupIds.add(1);
        activityGroupIds.add(2);
        activityGroupIds.add(44);
        object.put("activityGroupIds", activityGroupIds);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        JSONObject post = MockMvcUtil.sendRequest("/ActivityGroup/batchDelete", JSON.toJSONString(object), map, "post");
        System.out.println(post.toJSONString());
    }

    /**
     * 批量关闭团购活动测试
     */
    @Test
    public void batchCloseTest() {
        Map<String, Object> object = new HashMap<>();
        List<Integer> activityGroupIds = new ArrayList<>();
        activityGroupIds.add(1);
        activityGroupIds.add(2);
        activityGroupIds.add(44);
        object.put("activityGroupIds", activityGroupIds);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        JSONObject post = MockMvcUtil.sendRequest("/ActivityGroup/batchClose", JSON.toJSONString(object), map, "post");
        System.out.println(post.toJSONString());
    }

    /**
     * 更新团购活动测试
     */
    @Test
    public void updateTest() {
        List<ProductWxVo> productWxVoList = new ArrayList<>();

        ProductWxVo productWxVo = new ProductWxVo();
        productWxVo.setActivityPrice(0.00);
        productWxVo.setProductId(1527751305558135L);
        productWxVo.setActivityDiscount(0.60);
        productWxVo.setActivityStock(500);
        productWxVo.setMaxQuantity(200);

        List<ProductSpecItem> productSpecItemList = new ArrayList<>();
        ProductSpecItem productSpecItem1 = new ProductSpecItem();
        productSpecItem1.setProductSpecItemId(1527833372734171L);
        productSpecItem1.setActivityStock(100);
        ProductSpecItem productSpecItem2 = new ProductSpecItem();
        productSpecItem2.setProductSpecItemId(1527833372736957L);
        productSpecItem2.setActivityStock(200);
        ProductSpecItem productSpecItem3 = new ProductSpecItem();
        productSpecItem3.setProductSpecItemId(1527833372738014L);
        productSpecItem3.setActivityStock(300);
        ProductSpecItem productSpecItem4 = new ProductSpecItem();
        productSpecItem4.setProductSpecItemId(1527833372741123L);
        productSpecItem4.setActivityStock(400);
        productSpecItemList.add(productSpecItem1);
        productSpecItemList.add(productSpecItem2);
        productSpecItemList.add(productSpecItem3);
        productSpecItemList.add(productSpecItem4);
        productWxVo.setProductSpecItemList(productSpecItemList);
        productWxVoList.add(productWxVo);

        ActivityGroupVo activityGroupVo = new ActivityGroupVo();
        activityGroupVo.setProductList(productWxVoList);
        activityGroupVo.setAppmodelId(Constant.appmodelIdy);
        activityGroupVo.setActivityGroupName("拼团活动一");
        activityGroupVo.setActivityImg(Constant.imgUrl);
        activityGroupVo.setActivityRemark("团购备注");
        activityGroupVo.setStartDate(TimeUtil.getMonth(6));
        activityGroupVo.setEndDate(TimeUtil.getMonth(7));
        activityGroupVo.setLimitNum(2);
        activityGroupVo.setLimitTime(30);
        activityGroupVo.setOverlayState(false);
        activityGroupVo.setActivityGroupId(1);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        JSONObject post = MockMvcUtil.sendRequest("/ActivityGroup/update",
                JSON.toJSONString(activityGroupVo), map, "post");
        System.out.println(post.toJSONString());
    }

    /**
     * 小程序查询团购活动（未开始和进行中）测试
     */
    @Test
    public void findGroupActivityNotEndTest() {
        Map<String, Object> object = new HashMap<>();
        object.put("appmodelId", Constant.appmodelIdy);
        JSONObject post = MockMvcUtil.sendRequest("/ActivityGroup/findGroupActivityNotEnd",
                JSON.toJSONString(object), null, "post");
    }

    /**
     * 可以加入的团测试
     */
    @Test
    public void groupToJoinTest() {
        Group group = new Group();
        group.setProductId(1527751305558135L);
        group.setWxuserId(1528246624180515L);
        group.setAppmodelId(Constant.appmodelIdy);
        JSONObject post = MockMvcUtil.sendRequest("/ActivityGroup/groupToJoin",
                JSON.toJSONString(group), null, "post");
    }
}
