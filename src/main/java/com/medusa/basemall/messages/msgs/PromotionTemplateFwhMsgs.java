package com.medusa.basemall.messages.msgs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.agent.dao.AgentMapper;
import com.medusa.basemall.agent.dao.AgentRegisterMapper;
import com.medusa.basemall.constant.ActivityType;
import com.medusa.basemall.messages.entity.TemplateEntityFwh;
import com.medusa.basemall.messages.util.HttpClientUtil;
import com.medusa.basemall.messages.util.NHttpClientUtil;
import com.medusa.basemall.order.dao.OrderDetailMapper;
import com.medusa.basemall.order.dao.OrderMapper;
import com.medusa.basemall.order.entity.Order;
import com.medusa.basemall.order.entity.OrderDetail;
import com.medusa.basemall.product.dao.ProductMapper;
import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.promotion.dao.ActivityGroupMapper;
import com.medusa.basemall.promotion.dao.ActivityProductMapper;
import com.medusa.basemall.promotion.dao.GroupMapper;
import com.medusa.basemall.promotion.dao.GroupMemberMapper;
import com.medusa.basemall.promotion.entity.ActivityGroup;
import com.medusa.basemall.promotion.entity.ActivityProduct;
import com.medusa.basemall.promotion.entity.Group;
import com.medusa.basemall.promotion.entity.GroupMember;
import com.medusa.basemall.utils.TimeUtil;
import me.chanjar.weixin.mp.api.WxMpService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 服务号消息(营销相关)
 *
 * @author Created by wx on 2018/08/09.
 */
public class PromotionTemplateFwhMsgs {

	private static String to_user = "http://localhost:8080/medusaplatform/UserInfo/getUserOpenId?appmodelId=";

    /**
     * 超级棱镜+服务号token获取地址
     */
    private static String fwh_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=";

    /**
     *  超级棱镜+ 服务号appid
     */
    private static String fwh_appid = "wx70715838e115372f";

    /**
     *  超级棱镜+ 服务号secret
     */
    private static String fwh_secret = "cb05f751caf8e092697606cdb462fdc8";

    /**
     *  发送服务号消息url
     */
    private static String fwh_send_template_msg = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

    @Resource
    GroupMapper groupMapper;

    @Resource
    ProductMapper productMapper;

    @Resource
    ActivityGroupMapper activityGroupMapper;

    @Resource
    ActivityProductMapper activityProductMapper;

    @Resource
    OrderDetailMapper orderDetailMapper;

    @Resource
    GroupMemberMapper groupMemberMapper;

    @Resource
    OrderMapper orderMapper;

    @Resource
    AgentMapper agentMapper;

    @Resource
    AgentRegisterMapper agentRegisterMapper;

    private WxMpService wxMpService;

    /**
     * 拼团成功通知
     */
    public void successfulCollage(String appmodelId, Integer groupId) {
        String jsonstr = NHttpClientUtil.sendHttpGet(fwh_token_url + fwh_appid + "&secret=" + fwh_secret);
        JSONObject obj = JSON.parseObject(jsonstr);
        String token = obj.get("access_token").toString();
        String result = "";
        Group group = groupMapper.selectByPrimaryKey(groupId);
        Product product = productMapper.selectByPrimaryKey(group.getProductId());
        ActivityGroup activityGroup = activityGroupMapper.selectByPrimaryKey(group.getActivityGroupId());
        Map<String, Object> map = new HashMap<>(3);
        map.put("activityId", activityGroup.getActivityGroupId());
        map.put("activityType", ActivityType.GROUP);
        map.put("productId", product.getProductId());
        // 团活动商品
        Double groupPrice = 0.0;
        ActivityProduct activityProduct = activityProductMapper.selectGroupActivityProduct(map);
        if (activityProduct.getActivityPrice() != 0) {
            groupPrice = activityProduct.getActivityPrice();
        } else {
            GroupMember groupMember = groupMemberMapper.findByGroupId(group.getGroupId()).get(0);
            Order order = orderMapper.selectByPrimaryKey(groupMember.getOrderId());
            OrderDetail orderDetail = orderDetailMapper.selectByOrderId(order.getOrderId()).get(0);
            JSONObject object = JSONObject.parseObject(orderDetail.getProductSpecInfo());
            groupPrice = Double.valueOf(object.getString("price")) * activityProduct.getActivityDiscount();
        }
        TemplateEntityFwh td = new TemplateEntityFwh();
        td.setDataProperty("first", "拼团成功通知", "#173177");
        td.setDataProperty("keyword1", product.getProductName(), "#173177");
        td.setDataProperty("keyword2", String.valueOf(groupPrice), "#173177");
        td.setDataProperty("keyword3",String.valueOf(activityGroup.getLimitNum()), "#173177");
        td.setDataProperty("remark", "请尽快安排发货，提高服务质量！", "#173177");
        td.setTouser("oI_pGwIaYu7_Br3r0s5kEYFigM54");
        td.setTemplate_id("3wvGvMps3CWKkqeH45cezd2lTYjpvfdVybBHmFHDQx4");
        try {
            result = HttpClientUtil.WxHttpPost(fwh_send_template_msg + token, JSON.toJSONString(td));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 新会员加入提醒
     */
    public void joinMember(String appmodelId, String membershipNumber) throws Exception {
        String jsonstr = NHttpClientUtil.sendHttpGet(fwh_token_url + fwh_appid + "&secret=" + fwh_secret);
        JSONObject obj = JSON.parseObject(jsonstr);
        String token = obj.get("access_token").toString();
        String result = "";
        TemplateEntityFwh td = new TemplateEntityFwh();
	    td.setDataProperty("first", "会员注册通知", "#173177");
	    td.setDataProperty("keyword1", membershipNumber, "#173177");
	    td.setDataProperty("keyword2", TimeUtil.getNowTime(), "#173177");
	    td.setDataProperty("remark", "恭喜您有新会员注册成功", "#173177");
	    td.setTouser("oI_pGwIaYu7_Br3r0s5kEYFigM54");
	    td.setTemplate_id("iRXGgRE-79M19EY3IOIoeVd6DR50TDHp2b25oIzijxU");
        try {
            result = HttpClientUtil.WxHttpPost(fwh_send_template_msg + token, JSON.toJSONString(td));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
