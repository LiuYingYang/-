package com.medusa.basemall.promotion;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplication;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.promotion.entity.Coupon;
import com.medusa.basemall.promotion.entity.CouponWxuser;
import com.medusa.basemall.promotion.service.ActivityCouponService;
import com.medusa.basemall.promotion.vo.ActivityCouponVo;
import com.medusa.basemall.promotion.vo.CouponWxuserVo;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import com.medusa.basemall.utils.TimeUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BasemallApplication.class)
@WebAppConfiguration
public class ActivityCouponControllerTest extends BasemallApplicationTests {

    @Autowired
    ActivityCouponService activityCouponService;


    /**
     * 查询可供优惠券选择的商品
     */
    @Test
    public void findProductForCoupon() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("pageNum", "1");
        map.add("pageSize", "10");
        map.add("appmodelId", Constant.appmodelIdy);
        map.add("startDate", TimeUtil.DATETIMEFORMAT.format(System.currentTimeMillis() + 30000));
        map.add("endDate",TimeUtil.getMonth(3));
        map.add("overlayState", "0");
        JSONObject post = MockMvcUtil.sendRequest("/ActivityCoupon/findProductForCoupon/v1", "", map, "get");
        JSONObject data = JSONObject.parseObject(post.getString("data"));
        Assert.assertSame("success", 8, data.getInteger("total"));
    }


    /**
     * 创建优惠券活动
     */
    @Test
    public void save() {
        ActivityCouponVo activityCouponVo = new ActivityCouponVo();
        activityCouponVo.setActivityCouponName("优惠券活动1");
        activityCouponVo.setActivityImg(Constant.imgUrl);
        activityCouponVo.setActivityModel(Constant.imgUrl);
        activityCouponVo.setActivityRemark("1");
        activityCouponVo.setAppmodelId(Constant.appmodelIdy);
        activityCouponVo.setStartDate(TimeUtil.DATETIMEFORMAT.format(System.currentTimeMillis() + 10*1000));
        /*activityCouponVo.setStartDate(TimeUtil.getMonth(3));*/
        activityCouponVo.setEndDate(TimeUtil.DATETIMEFORMAT.format(System.currentTimeMillis() + 50*1000));
        activityCouponVo.setOverlayState(false);//不叠加
        activityCouponVo.setFullState(true);//不适用于所有商品

        List<Coupon> couponList = new ArrayList<>();
//        Coupon coupon1 = new Coupon();
//        coupon1.setAppmodelId(Constant.appmodelIdy);
//        coupon1.setCouponType(1);
//        coupon1.setStockQuantity(50);
//        coupon1.setLimitQuantity(5);
//        coupon1.setMaxPrice(100.00);
//        coupon1.setMinPrice(20.00);
//        coupon1.setSourceType(1);
//
//
//        Coupon coupon2 = new Coupon();
//        coupon2.setAppmodelId(Constant.appmodelIdy);
//        coupon2.setCouponType(2);
//        coupon2.setStockQuantity(50);
//        coupon2.setLimitQuantity(5);
//        coupon2.setMaxPrice(100.00);
//        coupon2.setDiscount(0.70);
//        coupon2.setSourceType(1);
//
//
//        Coupon coupon3 = new Coupon();
//        coupon3.setAppmodelId(Constant.appmodelIdy);
//        coupon3.setCouponType(3);
//        coupon3.setStockQuantity(50);
//        coupon3.setLimitQuantity(5);
//        coupon3.setMinPrice(20.00);
//        coupon3.setSourceType(1);

        Coupon coupon4 = new Coupon();
        coupon4.setAppmodelId(Constant.appmodelIdy);
        coupon4.setCouponType(4);
        coupon4.setStockQuantity(10);
        coupon4.setLimitQuantity(60);
        coupon4.setDiscount(0.80);
        coupon4.setSourceType(1);


//        couponList.add(coupon1);
//        couponList.add(coupon2);
//        couponList.add(coupon3);
        couponList.add(coupon4);
        activityCouponVo.setCouponList(couponList);

        Map<String, Object> map = new HashMap<>();
        map.put("activityCouponVo", activityCouponVo);

        JSONObject post = MockMvcUtil.sendRequest("/ActivityCoupon/save/v1",
                JSON.toJSONString(map), null, "post");

        Integer activityCouponId = activityCouponVo.getActivityCouponId();

/*        while (true) {
            if (System.currentTimeMillis() > TimeUtil.str2Timestamp(activityCouponVo.getStartDate()).getTime()) {
                break;
            }
        }
        Assert.assertSame();
        while (true) {
            if (System.currentTimeMillis() > TimeUtil.str2Timestamp(activityCouponVo.getEndDate()).getTime()) {
                break;
            }
        }
        Assert.assertSame();*/
    }

    /**
     * 查询优惠券活动
     */
    @Test
    public void findByAppmodelId() {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("appmodelId", Constant.appmodelIdy);

        JSONObject post = MockMvcUtil.sendRequest("/ActivityCoupon/findByAppmodelId/v1",
                "", map, "get");

        JSONObject data = JSONObject.parseObject(post.getString("data"));

    }


    /**
     * 批量删除优惠券活动
     */
    @Test
    public void batchDelete() {
        Map<String, Object> object = new HashMap<>();
        List<Integer> activityCouponIds = new ArrayList<>();
        object.put("activityCouponIdsGet", "153");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        JSONObject post = MockMvcUtil.sendRequest("/ActivityCoupon/batchDelete/v1", JSON.toJSONString(object), map, "put");
        System.out.println(post.toJSONString());
    }


    /**
     * 更新优惠券活动
     */
    @Test
    public void update() {
        ActivityCouponVo activityCouponVo = new ActivityCouponVo();
        List<Long> productIds = new ArrayList<>();
        productIds.add(1534402802398513L);
        activityCouponVo.setProductIds(productIds);
        activityCouponVo.setActivityCouponId(125);
        activityCouponVo.setActivityCouponName("优惠活动618--111");
        activityCouponVo.setActivityImg(Constant.imgUrl);
        activityCouponVo.setActivityModel(Constant.imgUrl);
        activityCouponVo.setActivityRemark("活动备注不要太长");
        activityCouponVo.setAppmodelId(Constant.appmodelIdy);
        activityCouponVo.setStartDate(TimeUtil.getMonth(1));
        activityCouponVo.setEndDate(TimeUtil.getMonth(2));
        activityCouponVo.setOverlayState(false);//不叠加
        activityCouponVo.setFullState(false);//不适用于所有商品
        activityCouponVo.setNowState(0);

        List<Coupon> couponList = new ArrayList<>();
        Coupon coupon1 = new Coupon();
        coupon1.setAppmodelId(Constant.appmodelIdy);
        coupon1.setCouponType(1);
        coupon1.setLimitQuantity(5);
        coupon1.setMaxPrice(200.00);
        coupon1.setMinPrice(20.00);
        coupon1.setSourceType(1);
        Coupon coupon2 = new Coupon();
        coupon2.setAppmodelId(Constant.appmodelIdy);
        coupon2.setCouponType(2);
        coupon2.setLimitQuantity(5);
        coupon2.setMaxPrice(200.00);
        coupon2.setDiscount(0.70);
        coupon2.setSourceType(1);
        Coupon coupon3 = new Coupon();
        coupon3.setAppmodelId(Constant.appmodelIdy);
        coupon3.setCouponType(3);
        coupon3.setLimitQuantity(5);
        coupon3.setMinPrice(10.00);
        coupon3.setSourceType(1);
        Coupon coupon4 = new Coupon();
        coupon4.setAppmodelId(Constant.appmodelIdy);
        coupon4.setCouponType(1);
        coupon4.setLimitQuantity(5);
        coupon4.setDiscount(0.80);
        coupon4.setSourceType(1);

        couponList.add(coupon1);
        couponList.add(coupon2);
        couponList.add(coupon3);
        couponList.add(coupon4);
        activityCouponVo.setCouponList(couponList);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        JSONObject post = MockMvcUtil.sendRequest("/ActivityCoupon/update", JSON.toJSONString(activityCouponVo), map, "post");
        System.out.println(post.toJSONString());
    }


    /**
     * 用户领取优惠券测试
     */
    @Test
    public void wxuserObtainCoupon() {
        CouponWxuser couponWxuser = new CouponWxuser();
        couponWxuser.setWxuserId(1528246692051902L);
        couponWxuser.setCouponId(45);
        couponWxuser.setActivityCouponId(14);
        couponWxuser.setAppmodelId(Constant.appmodelId);
        JSONObject post = MockMvcUtil.sendRequest("/ActivityCoupon/wxuserObtainCoupon",
                JSON.toJSONString(couponWxuser), null, "post");

    }

    /**
     * 查询用户领取优惠券测试
     */
    @Test
    public void findWxuserCoupon() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("wxuserId", "1532054646815917");
        map.add("useFlag", "0");
        map.add("appmodelId", Constant.appmodelIdy);
        JSONObject post = MockMvcUtil.sendRequest("/ActivityCoupon/findWxuserCoupons/v1",
                "", map, "get");
    }

    /**
     * 查询用户可用优惠券测试
     */
    @Test
    public void findWxuserCouponCanUse() {
        Cookie cookie = new Cookie("test","测试");
        CouponWxuserVo couponWxuserVo = new CouponWxuserVo();
        couponWxuserVo.setWxuserId(1528246692051902L);
        couponWxuserVo.setUseFlag(0);
        couponWxuserVo.setAppmodelId(Constant.appmodelId);
        JSONObject post = MockMvcUtil.sendRequest("/ActivityCoupon/findWxuserCouponCanUse",
                JSON.toJSONString(couponWxuserVo), null, "post");
    }

}
