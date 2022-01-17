package com.medusa.gruul.logistics.util.express.yto;

import com.medusa.gruul.logistics.util.HttpClientUtil;
import com.medusa.gruul.logistics.util.VerifyCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p>
 * 快递接口工具类
 * </p>
 *
 * @author lcysike
 * @since 2020-03-10
 */
public class YTOExpressUtil {

    public static Logger logger = LoggerFactory.getLogger(YTOExpressUtil.class);


    /**
     * 快递下单接口
     */
    private static final String REQ_URL = "http://opentestapi.yto.net.cn/service/order_create/v1/G7v1Kn";

    /**
     * 快递物流查询接口
     */
    private static final String ROUTE_URL = "http://opentestapi.yto.net.cn/service/waybill_query/v1/G7v1Kn";

    /**
     * 物流查询顾客编码
     */
    private static final String ROUTE_CLIENT_CODE = "YTOTEST";
    /**
     * 物流查询校验码
     */
    private static final String ROUTE_CHECK_WORD = "1QLlIZ";

    /**
     * 下单顾客编码
     */
    private static final String CLIENT_CODE = "TEST";
    /**
     * 下单校验码
     */
    private static final String CHECK_WORD = "123456";
    /**
     * 编码格式
     */
    private static final String UNION_CODE = "UTF-8";
    /**
     * 语言
     */
    private static final String LANG = "zh-CN";


    public static String callYTOExpressServiceByCSIM(String reqURL, String reqXML, String clientCode) {
        String result = null;
        String verifyCode = VerifyCodeUtil.md5EncryptAndBase64(reqXML + CHECK_WORD);
        result = queryAPIservice(reqURL, reqXML, verifyCode);
        return result;
    }

    public static String queryAPIRouteService(String routeURL, String sign, String appKey, String format, String method, String timestamp, String userId, String v, String reqXml) {
        HttpClientUtil httpclient = new HttpClientUtil();
        if (routeURL == null) {
            routeURL = REQ_URL;
        }
        String result;
        try {
            result = httpclient.postYTORouteAPI(routeURL, sign, appKey, format, method, timestamp, userId, v, reqXml);
            return result;
        } catch (Exception var6) {
            logger.warn(" " + var6);
            return null;
        }
    }

    public static String queryAPIservice(String url, String xml, String verifyCode) {
        HttpClientUtil httpclient = new HttpClientUtil();
        if (url == null) {
            url = ROUTE_URL;
        }
        String result;
        try {
            result = httpclient.postYTOAPI(url, xml, verifyCode, CLIENT_CODE, "online");
            return result;
        } catch (Exception var6) {
            logger.warn(" " + var6);
            return null;
        }
    }

}