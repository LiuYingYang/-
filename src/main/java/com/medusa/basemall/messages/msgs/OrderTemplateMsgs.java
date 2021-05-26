package com.medusa.basemall.messages.msgs;


import cn.binarywang.wx.miniapp.api.WxMaMsgService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import com.medusa.basemall.messages.entity.WxuserFormId;
import com.medusa.basemall.order.dao.OrderDetailMapper;
import com.medusa.basemall.order.dao.OrderMapper;
import com.medusa.basemall.order.dao.OrderRefoundMapper;
import com.medusa.basemall.order.entity.OrderDetail;
import com.medusa.basemall.product.dao.LogisticCancelMapper;
import com.medusa.basemall.product.entity.LogisticCancel;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 小程序消息(订单相关)
 *
 * @author Created by wx on 2018/08/09.
 */
@Service
public class OrderTemplateMsgs {

    private static String template_id_url = "http://localhost:8080/medusaplatform/rest/openwx/getTemplateID?authorizerAppid=";

    @Resource
    private WxOpenService wxOpenService;
    @Resource
    private OrderDetailMapper orderDetailMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private LogisticCancelMapper logisticCancelMapper;
    @Resource
    private OrderRefoundMapper orderRefoundMapper;

    /**
     *  同意填写退货信息通知
     */
    public void  returnInformation(WxuserFormId wxuserFormId, String appid, String appmodelId) throws Exception {
        WxMaService wxMaServiceByAppid = wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(appid);
        WxMaMsgService msgService = wxMaServiceByAppid.getMsgService();
        // 模板消息id
        String json = "lGBw1d3lmolIaPKZeI6ZMRzAgQjPgg7R654yw0aiVkk";
        // String json = HttpClientUtil.testHttpGet(template_id_url + "wxa75115dccbe8ecec" + "&titleId=" + "AT0007");
        List<WxMaTemplateData> data = new ArrayList<>();
        LogisticCancel logisticCancel = logisticCancelMapper.selectDefultTrue(appmodelId);
        String address = logisticCancel.getLocationJson().replace("[","").replace("]","")
                .replace(",","").replace("\"","");
        data.add(new WxMaTemplateData("keyword1",logisticCancel.getUserName()));
        data.add(new WxMaTemplateData("keyword2",logisticCancel.getPhone()));
        data.add(new WxMaTemplateData("keyword3",address));
        data.add(new WxMaTemplateData("keyword4","温馨提示"));
        //发送支付成功模板消息
        WxMaTemplateMessage message = new WxMaTemplateMessage(wxuserFormId.getOpenId(),
                json,"",wxuserFormId.getFormValue(),data,"","");
        try {
            msgService.sendTemplateMsg(message);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }



    /**
     *  获取商品名称
     */
    private String productName(Long orderId){
        StringBuffer productNameSb = new StringBuffer();
        List<OrderDetail> orderDetails = orderDetailMapper.selectByOrderId(orderId);
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
        return productName;
    }
}
