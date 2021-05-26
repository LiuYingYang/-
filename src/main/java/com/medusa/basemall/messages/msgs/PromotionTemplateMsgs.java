package com.medusa.basemall.messages.msgs;

import cn.binarywang.wx.miniapp.api.WxMaMsgService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.agent.dao.AgentRegisterMapper;
import com.medusa.basemall.agent.entity.AgentRegister;
import com.medusa.basemall.constant.ActivityType;
import com.medusa.basemall.messages.entity.WxuserFormId;
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
import com.medusa.basemall.user.dao.MemberMapper;
import com.medusa.basemall.user.dao.MemberRankMapper;
import com.medusa.basemall.user.dao.WxuserMapper;
import com.medusa.basemall.user.entity.Wxuser;
import com.medusa.basemall.utils.TimeUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 小程序模板消息(营销相关)
 *
 * @author Created by wx on 2018/08/09.
 */
@Service
public class PromotionTemplateMsgs {

    private static String template_id_url = "http://localhost:8080/medusaplatform/rest/openwx/getTemplateID?authorizerAppid=";
    @Resource
    private WxOpenService wxOpenService;
    @Resource
    private GroupMapper groupMapper;
    @Resource
    private ActivityGroupMapper activityGroupMapper;
    @Resource
    private GroupMemberMapper groupMemberMapper;
    @Resource
    private ActivityProductMapper activityProductMapper;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderDetailMapper orderDetailMapper;
    @Resource
    private WxuserMapper wxuserMapper;
    @Resource
    private AgentRegisterMapper agentRegisterMapper;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private MemberRankMapper memberRankMapper;


    /**
     * 拼团成功提醒
     */
    public void successfulCollage(WxuserFormId wxuserFormId, String appid, Integer groupMemberId) throws Exception {
        WxMaService wxMaServiceByAppid = wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(appid);
        WxMaMsgService msgService = wxMaServiceByAppid.getMsgService();
        // 模板消息id
        String json = "c1tAPhDz_fduzsmZd80ro4N89m0xJ3PDaY6i3KdH4LM";
        // String json = HttpClientUtil.testHttpGet(template_id_url + "wxa75115dccbe8ecec" + "&titleId=" + "AT0007");
        // 团成员
        GroupMember groupMember = groupMemberMapper.selectByPrimaryKey(groupMemberId);
        // 团
        Group group = groupMapper.selectByPrimaryKey(groupMember.getOrderId());
        // 所有团成员
        List<GroupMember> groupMembers = groupMemberMapper.findByGroupId(group.getGroupId());
        StringBuffer groupMembersNameSb = new StringBuffer();
        if (groupMembers.size() > 0) {
            for (int i = 0; i < groupMembers.size(); i++) {
                if (i == groupMembers.size() - 1) {
                    groupMembersNameSb = groupMembersNameSb.append(groupMembers.get(i).getNickName());
                } else {
                    groupMembersNameSb = groupMembersNameSb.append(groupMembers.get(i).getNickName() + ",");
                }
            }
        }
        String groupMembersName = String.valueOf(groupMembersNameSb);
        // 团活动
        ActivityGroup activityGroup = activityGroupMapper.selectByPrimaryKey(group.getActivityGroupId());
        // 订单
        Order order = orderMapper.selectByPrimaryKey(groupMemberId);
        // 订单详情
        OrderDetail orderDetail = orderDetailMapper.selectByOrderId(order.getOrderId()).get(0);
        // 商品
        Product product = productMapper.selectByPrimaryKey(orderDetail.getProductId());
        Map<String, Object> map = new HashMap<>(3);
        map.put("activityId", activityGroup.getActivityGroupId());
        map.put("activityType", ActivityType.GROUP);
        map.put("productId", product.getProductId());
        // 团活动商品
        ActivityProduct activityProduct = activityProductMapper.selectGroupActivityProduct(map);
        Double groupPrice = 0.0;
        if (activityProduct.getActivityPrice() != 0) {
            groupPrice = activityProduct.getActivityPrice();
        } else {
            JSONObject object = JSONObject.parseObject(orderDetail.getProductSpecInfo());
            groupPrice = Double.valueOf(object.getString("price")) * activityProduct.getActivityDiscount();
        }
        List<WxMaTemplateData> data = new ArrayList<>();
        data.add(new WxMaTemplateData("keyword1", activityGroup.getActivityGroupName()));
        data.add(new WxMaTemplateData("keyword2", order.getOutTradeNo()));
        data.add(new WxMaTemplateData("keyword3", groupMembersName));
        data.add(new WxMaTemplateData("keyword4", product.getProductName()));
        data.add(new WxMaTemplateData("keyword5", String.valueOf(groupPrice)));
        //发送支付成功模板消息
        WxMaTemplateMessage message = new WxMaTemplateMessage(wxuserFormId.getOpenId(),
                json, "", wxuserFormId.getFormValue(), data, "", "");
        try {
            msgService.sendTemplateMsg(message);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

    }

    /**
     * 拼团失败提醒
     */
    public void failCollage(WxuserFormId wxuserFormId, String appid, Integer groupMemberId) throws Exception {
        WxMaService wxMaServiceByAppid = wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(appid);
        WxMaMsgService msgService = wxMaServiceByAppid.getMsgService();
        // 模板消息id
        String json = "c1tAPhDz_fduzsmZd80ro4N89m0xJ3PDaY6i3KdH4LM";
        // String json = HttpClientUtil.testHttpGet(template_id_url + "wxa75115dccbe8ecec" + "&titleId=" + "AT0007");
        // 团成员
        GroupMember groupMember = groupMemberMapper.selectByPrimaryKey(groupMemberId);
        // 团
        Group group = groupMapper.selectByPrimaryKey(groupMember.getGroupId());
        // 所有团成员
        List<GroupMember> groupMembers = groupMemberMapper.findByGroupId(group.getGroupId());
        // 团活动
        ActivityGroup activityGroup = activityGroupMapper.selectByPrimaryKey(group.getActivityGroupId());
        // 订单
        Order order = orderMapper.selectByPrimaryKey(groupMember.getOrderId());
        // 订单详情
        OrderDetail orderDetail = orderDetailMapper.selectByOrderId(order.getOrderId()).get(0);
        // 商品
        Product product = productMapper.selectByPrimaryKey(orderDetail.getProductId());
        Map<String, Object> map = new HashMap<>(3);
        map.put("activityId", activityGroup.getActivityGroupId());
        map.put("activityType", ActivityType.GROUP);
        map.put("productId", product.getProductId());
        // 团活动商品
        ActivityProduct activityProduct = activityProductMapper.selectGroupActivityProduct(map);
        Double groupPrice = 0.0;
        if (activityProduct.getActivityPrice() != 0) {
            groupPrice = activityProduct.getActivityPrice();
        } else {
            JSONObject object = JSONObject.parseObject(orderDetail.getProductSpecInfo());
            groupPrice = Double.valueOf(object.getString("price")) * activityProduct.getActivityDiscount();
        }
        List<WxMaTemplateData> data = new ArrayList<>();
        data.add(new WxMaTemplateData("keyword1", product.getProductName()));
        data.add(new WxMaTemplateData("keyword2", String.valueOf(groupPrice)));
        data.add(new WxMaTemplateData("keyword3", "拼团失败，退款中"));
        data.add(new WxMaTemplateData("keyword4", order.getOutTradeNo()));
        data.add(new WxMaTemplateData("keyword5", "温馨提示"));
        //发送支付成功模板消息
        WxMaTemplateMessage message = new WxMaTemplateMessage(wxuserFormId.getOpenId(),
                json, "", wxuserFormId.getFormValue(), data, "", "");
        try {
            msgService.sendTemplateMsg(message);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }


    /**
     * 积分到期提醒
     */
    public void integralExpiration(WxuserFormId wxuserFormId, String appid, Integer allIntegral) throws Exception {
        WxMaService wxMaServiceByAppid = wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(appid);
        WxMaMsgService msgService = wxMaServiceByAppid.getMsgService();
        // 模板消息id
        String json = "FwRugKYih4QNwj_uwGspjQi_hdOE2KW7WPMKIo-FzFk";
        // String json = HttpClientUtil.testHttpGet(template_id_url + "wxa75115dccbe8ecec" + "&titleId=" + "AT0007");
        Wxuser wxuser = wxuserMapper.selectByOpenID(wxuserFormId.getOpenId(), "");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String now = TimeUtil.getNowDate();
        Date nowNew = sdf.parse(now);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowNew);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));

        List<WxMaTemplateData> data = new ArrayList<>();
        data.add(new WxMaTemplateData("keyword1", wxuser.getNikeName()));
        data.add(new WxMaTemplateData("keyword2", String.valueOf(allIntegral)));
        data.add(new WxMaTemplateData("keyword3", String.valueOf(sdf.format(calendar.getTime()))));
        data.add(new WxMaTemplateData("keyword4", "温馨提示"));
        //发送支付成功模板消息
        WxMaTemplateMessage message = new WxMaTemplateMessage(wxuserFormId.getOpenId(),
                json, "", wxuserFormId.getFormValue(), data, "", "");
        try {
            msgService.sendTemplateMsg(message);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

    /**
     * 申请成功提醒
     */
    public void applySuccess(WxuserFormId wxuserFormId, String appid, Integer regId) throws Exception {
        WxMaService wxMaServiceByAppid = wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(appid);
        WxMaMsgService msgService = wxMaServiceByAppid.getMsgService();
        // 模板消息id
        String json = "1nfTqSaRpBeKPDmcCG7PZiS3aS-2VRrGxTeW0QZzhJU";
        // String json = HttpClientUtil.testHttpGet(template_id_url + "wxa75115dccbe8ecec" + "&titleId=" + "AT0007");
        AgentRegister agentRegister = agentRegisterMapper.selectByPrimaryKey(regId);
        List<WxMaTemplateData> data = new ArrayList<>();
        data.add(new WxMaTemplateData("keyword1", "代理"));
        data.add(new WxMaTemplateData("keyword2", agentRegister.getRegTime()));
        data.add(new WxMaTemplateData("keyword3", "已通过"));
        data.add(new WxMaTemplateData("keyword4", "温馨提示"));
        //发送支付成功模板消息
        WxMaTemplateMessage message = new WxMaTemplateMessage(wxuserFormId.getOpenId(),
                json, "", wxuserFormId.getFormValue(), data, "", "");
        try {
            msgService.sendTemplateMsg(message);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

}
