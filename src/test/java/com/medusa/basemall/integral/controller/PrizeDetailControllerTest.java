package com.medusa.basemall.integral.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.agent.dao.AgentMapper;
import com.medusa.basemall.agent.entity.Agent;
import com.medusa.basemall.messages.dao.WxuserFormIdMapper;
import com.medusa.basemall.messages.entity.WxuserFormId;
import com.medusa.basemall.order.dao.OrderDetailMapper;
import com.medusa.basemall.order.entity.Order;
import com.medusa.basemall.order.entity.OrderDetail;
import com.medusa.basemall.order.service.OrderService;
import com.medusa.basemall.order.vo.OrderSurveyVo;
import com.medusa.basemall.product.dao.LogisticCancelMapper;
import com.medusa.basemall.product.entity.LogisticCancel;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import com.medusa.basemall.utils.TimeUtil;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PrizeDetailControllerTest extends BasemallApplicationTests {

    @Resource
    WxuserFormIdMapper wxuserFormIdMapper;

    @Resource
    OrderService orderService;

    @Resource
    OrderDetailMapper orderDetailMapper;

    @Resource
    LogisticCancelMapper logisticCancelMapper;

    /**
     * 查看积分明细测试
     */
    @Test
    public void seletePrizeDetailTest() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("appmodelId", Constant.appmodelIdy);
        map.add("wxuserId","1532054646815917");
        map.add("pageNum","2");
        map.add("pageSize","2");
        JSONObject post = MockMvcUtil.sendRequest("/prize/detail/list/v1",
                "", map, "get");

    }

    @Test
    public void Test() {
        WxuserFormId wxuserFormId = new WxuserFormId();
        wxuserFormId.setCreateTime(TimeUtil.getNowTime());
        wxuserFormId.setFormValue("1532768059747");
        wxuserFormId.setOpenId("o_dvr0AEePLVE28nHgz3ckh8C-QI");
        wxuserFormIdMapper.insert(wxuserFormId);
    }

    @Test
    public void TestTwo() {
        Map<String,Object> map = new HashMap<>();
        List<Order> orders = new ArrayList<>();
        Order order = orderService.findById(1532768071189545L);
        order.setWlName("申通");
        order.setWlNum("dadas132123");
        orders.add(order);
        map.put("orders",orders);
        map.put("appmodelId",Constant.appmodelIdy);
        JSONObject post = MockMvcUtil.sendRequest("/Order/deliverGoods",
                JSON.toJSONString(map), null, "post");
    }

    @Test
    public void TestThree() {
        Order order = orderService.findById(1532768071189545L);
        String s = order.getUserAddress();
        JSONObject jsonObject = JSONObject.parseObject(order.getUserAddress());
        String user = jsonObject.getString("user");
        String tel = jsonObject.getString("tel");
        String address = jsonObject.getString("string");
        StringBuffer productNameSb = new StringBuffer();
        List<OrderDetail> orderDetails = orderDetailMapper.selectByOrderId(order.getOrderId());
        if (orderDetails.size() > 0) {
            for (int i = 0; i < orderDetails.size(); i ++) {
                if (i == orderDetails.size() - 1) {
                    productNameSb = productNameSb.append(orderDetails.get(i).getProductName());
                } else {
                    productNameSb = productNameSb.append(orderDetails.get(i).getProductName() + ",");
                }
            }
        }
        String productName = String.valueOf(productNameSb);
    }

    @Test
    public void TestFour() {
        LogisticCancel logisticCancel = logisticCancelMapper.selectDefultTrue(Constant.appmodelIdy);
        JSONObject jsonObject = JSONObject.parseObject(logisticCancel.getLocationJson());
        String address = jsonObject.getString("address").replace("[","").replace("]","")
                .replace(",","").replace("\"","");

    }

    @Test
    public void Testfive() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String now = "2018-7-1";
        Date nowNew = sdf.parse(now);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowNew);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        String s = String.valueOf(sdf.format(calendar.getTime()));

    }

    @Resource
    AgentMapper agentMapper;
    @Test
    public void Testsix() throws Exception {
        Agent agent = agentMapper.selectByPrimaryKey(10);
        String message = "姓名："+ agent.getAgentName() + "；联系方式：" + agent.getAgentPhone() + "；所在区域：" + agent.getAgentDomain();
    }

    @Test
    public void TestSeven() throws Exception {
        String s = "User-" + 12;
    }


    @Test
    public void TestEight() throws Exception {
        OrderSurveyVo orderSurveyVo = new OrderSurveyVo();
        orderSurveyVo.setAppmodelId(Constant.appmodelIdy);

        JSONObject post = MockMvcUtil.sendRequest("/order/survey/selectCount",
                JSON.toJSONString(orderSurveyVo), null, "post");
    }
}
