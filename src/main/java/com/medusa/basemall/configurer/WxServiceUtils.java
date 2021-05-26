package com.medusa.basemall.configurer;

import cn.binarywang.wx.miniapp.api.WxMaMsgService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import com.github.binarywang.wxpay.bean.entpay.EntPayRequest;
import com.github.binarywang.wxpay.bean.entpay.EntPayResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.EntPayService;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.EntPayServiceImpl;
import com.github.binarywang.wxpay.util.SignUtils;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.core.ServiceException;
import com.vip.vjtools.vjkit.number.RandomUtil;
import lombok.extern.log4j.Log4j2;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author whh
 */
@Component
@Log4j2
public class WxServiceUtils {

	@Resource
	private WxConfiguration wxPayConfiguration;

	/**
	 * @param body         商品描述
	 * @param outTradeNo 订单号
	 * @param totalFee    订单总金额，单位为分
	 * @param openid       用户在商户appid下的唯一标识
	 * @param notify_url   通知地址
	 * @return
	 */
	public Map<String, String> wxJsapiPayRequest(String body, String outTradeNo, String totalFee, String openid,
			String notify_url, String appmodelId) throws Exception {
		WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
		orderRequest.setBody(body);
		orderRequest.setOutTradeNo(outTradeNo);
		orderRequest.setTotalFee(BaseWxPayRequest.yuanToFen(totalFee));
		orderRequest.setOpenid(openid);
		orderRequest.setSpbillCreateIp("192.168.1.1");
		orderRequest.setNonceStr(RandomUtil.randomStringFixLength(32));
		orderRequest.setTradeType("JSAPI");
		orderRequest.setNotifyUrl(notify_url);
		WxPayService wxPayService = wxPayConfiguration.init(appmodelId);
		WxPayUnifiedOrderResult wxPayUnifiedOrderResult = wxPayService.unifiedOrder(orderRequest);
		Map<String, String> resutlMap = new HashMap<>(5);
		if ("SUCCESS".equals(wxPayUnifiedOrderResult.getReturnCode())) {
			resutlMap.put("returnCode", wxPayUnifiedOrderResult.getReturnCode());
			String timeStamp = System.currentTimeMillis() / 1000 + "";
			String nonceStr = RandomUtil.randomStringFixLength(32);
			Map<String, String> map = new HashMap<>(5);
			map.put("appId", orderRequest.getAppid());
			map.put("nonceStr", nonceStr);
			map.put("package", "prepay_id=" + wxPayUnifiedOrderResult.getPrepayId());
			map.put("signType", "MD5");
			map.put("timeStamp", timeStamp);
			//再次签名
			String paySign = SignUtils.createSign(map, "MD5", wxPayService.getConfig().getMchKey(), new String[0]);
			resutlMap.put("timeStamp", timeStamp);
			resutlMap.put("paySign", paySign);
			resutlMap.put("package", "prepay_id=" + wxPayUnifiedOrderResult.getPrepayId());
			resutlMap.put("nonceStr", nonceStr);
			return resutlMap;
		} else {
			resutlMap.put("returnCode", wxPayUnifiedOrderResult.getReturnCode());
			resutlMap.put("returnMsg", wxPayUnifiedOrderResult.getReturnMsg());
			resutlMap.put("errorCode", wxPayUnifiedOrderResult.getErrCode());
			resutlMap.put("errCodeDes", wxPayUnifiedOrderResult.getErrCodeDes());
		}
		return resutlMap;
	}


	/**
	 * 微信扫码支付 模式2
	 * @param body
	 * @param outTradeNo
	 * @param totalFee
	 * @param productId
	 * @param notify_url
	 * @param appmodelId
	 * @return
	 * @throws WxPayException
	 * @throws IOException
	 */
	public byte[] wxNativePayRequest(String body, String outTradeNo, String totalFee, String productId,
			String notify_url, String appmodelId) throws WxPayException {
		WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
		orderRequest.setBody(body);
		orderRequest.setOutTradeNo(outTradeNo);
		orderRequest.setTotalFee(WxPayRefundRequest.yuanToFen(totalFee));
		orderRequest.setSpbillCreateIp("127.0.0.1");
		orderRequest.setNonceStr(RandomUtil.randomStringFixLength(32));
		orderRequest.setTradeType("NATIVE");
		orderRequest.setNotifyUrl(notify_url);
		orderRequest.setProductId(productId);
		WxPayService wxPayService = wxPayConfiguration.init(appmodelId);
		WxPayUnifiedOrderResult wxPayUnifiedOrderResult = wxPayService.unifiedOrder(orderRequest);
		if ("SUCCESS".equals(wxPayUnifiedOrderResult.getReturnCode())) {
			return wxPayService.createScanPayQrcodeMode2(wxPayUnifiedOrderResult.getCodeURL(), null, null);
		}
		return null;
	}


	/**
	 * 微信退款
	 * @param out_trade_no  商户订单号
	 * @param all_total_fee 订单金额
	 * @param refund_fee    退款金额
	 * @return
	 * @throws Exception
	 */
	public Result wechatRefund(String out_trade_no, String out_refund_no, int all_total_fee, int refund_fee,
			String appmodelId) throws Exception {
		WxPayRefundRequest wxPayRefundRequest = new WxPayRefundRequest();
		wxPayRefundRequest.setOutTradeNo(out_trade_no);
		wxPayRefundRequest.setOutRefundNo(out_refund_no);
		wxPayRefundRequest.setTotalFee(all_total_fee);
		wxPayRefundRequest.setRefundFee(refund_fee);
		WxPayService wxPayService = wxPayConfiguration.init(appmodelId);
		WxPayRefundResult refund = wxPayService.refund(wxPayRefundRequest);
		if (refund.getReturnCode().equalsIgnoreCase("SUCCESS")) {
			if (refund.getResultCode().equalsIgnoreCase("SUCCESS")) {
				return ResultGenerator.genSuccessResult();
			} else {
				return ResultGenerator.genFailResult(refund.getErrCode() + " : " + refund.getErrCodeDes());
			}
		} else {
			return ResultGenerator.genFailResult(refund.getReturnMsg());
		}
	}

	/**
	 * 第三方平台代小程序发模板消息
	 * @param keywords
	 * @param templateId
	 * @param openId
	 * @param formId
	 * @param pagePath
	 */
	public void platSendMiniProgramTemplateMessage(List<String> keywords, String authorizerAppid, String templateId,
			String openId, String formId, String pagePath) {
		WxMaService wxMaServiceByAppid = wxPayConfiguration.getWxOpenService().getWxOpenComponentService()
				.getWxMaServiceByAppid(authorizerAppid);
		WxMaMsgService msgService = wxMaServiceByAppid.getMsgService();
		List<WxMaTemplateData> data = new ArrayList<>();
		int i = 1;
		for (String keyword : keywords) {
			data.add(new WxMaTemplateData("keyword" + i, keyword));
			i++;
		}
		WxMaTemplateMessage message = new WxMaTemplateMessage(openId, templateId, pagePath, formId, data, "", "");
		try {
			msgService.sendTemplateMsg(message);
		} catch (WxErrorException e) {
			log.warn("----------小程序发模板消息发送失败------------");
			log.warn("小程序appId: " + authorizerAppid);
			log.warn("消息内容: " + keywords.toString());
			log.warn("微信报错: " + e.toString());
		}
	}

	/**
	 *企业付款
	 * @param appmodelId
	 * @param openId
	 * @param partnerTradeNo
	 * @param userName
	 * @param amount
	 * @param description
	 * @return
	 */
	public Map<String, String> enterprisePayment(String appmodelId, String openId, String partnerTradeNo,
			String userName, String amount, String description, String appId) {
		WxPayService wxPayService = wxPayConfiguration.init(appmodelId);
		wxPayService.getConfig().setAppId(appId);
		EntPayService entPayService = new EntPayServiceImpl(wxPayService);
		EntPayRequest entPayRequest = new EntPayRequest();
		entPayRequest.setOpenid(openId);
		entPayRequest.setPartnerTradeNo(partnerTradeNo);
		entPayRequest.setCheckName(WxPayConstants.CheckNameOption.FORCE_CHECK);
		entPayRequest.setReUserName(userName);
		entPayRequest.setAmount(WxPayRefundRequest.yuanToFen(amount));
		entPayRequest.setDescription(description);
		entPayRequest.setSpbillCreateIp("127.0.0.1");
		Map<String, String> resutlMap = new HashMap<>(5);
		try {
			EntPayResult entPayResult = entPayService.entPay(entPayRequest);
			if ("FAIL".equals(entPayResult.getReturnCode())) {
				log.error("企业支付失败:" + entPayResult.getReturnMsg());
				if (entPayResult.getErrCode().equalsIgnoreCase("NO_AUTH")) {
					throw new ServiceException(
							"无法使用该功能,原因:1.用户账号被冻结，无法付款,2. 产品权限没有开通或者被风控冻结,3. 此IP地址不允许调用接口，如有需要请登录微信支付商户平台更改配置");
				} else if (entPayResult.getErrCode().equalsIgnoreCase("AMOUNT_LIMIT")) {
					throw new ServiceException("金额超限");
				} else if (entPayResult.getErrCode().equalsIgnoreCase("NAME_MISMATCH")) {
					throw new ServiceException("姓名校验出错");
				} else if (entPayResult.getErrCode().equalsIgnoreCase("MONEY_LIMIT")) {
					throw new ServiceException("已经达到今日付款总额上限/已达到付款给此用户额度上限");
				} else if (entPayResult.getErrCode().equalsIgnoreCase("V2_ACCOUNT_SIMPLE_BAN")) {
					throw new ServiceException("无法给非实名用户付款");
				} else if (entPayResult.getErrCode().equalsIgnoreCase("SENDNUM_LIMIT")) {
					throw new ServiceException("该用户今日付款次数超过限制,如有需要请登录微信支付商户平台更改API安全配置");
				} else if (entPayResult.getErrCode().equalsIgnoreCase("PAY_CHANNEL_NOT_ALLOWED")) {
					throw new ServiceException("本商户号未配置API发起能力 ");
				}
				throw new ServiceException("支付异常");
			}
		} catch (WxPayException e) {
			throw new ServiceException(e.getErrCode());
		}
		return resutlMap;
	}

}
