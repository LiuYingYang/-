package com.medusa.basemall.utils;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;

import java.util.ArrayList;

public class SSMSend {

    /***
     发送腾讯云短信模板  单发
     参数	必选	类型	描述
     result	是	number	错误码，0 表示成功(计费依据)，非 0 表示失败，参考 错误码
     errmsg	是	string	错误消息，result 非 0 时的具体错误信息
     ext	否	string	用户的 session 内容，腾讯 server 回包中会原样返回
     fee	否	number	短信计费的条数，"fee" 字段计费说明
     sid	否	string	本次发送标识 id，标识一次短信下发记录
     */
    public static int sendNoto(int appid, String strAppKey, ArrayList<String> params, String phone, Integer templateId) throws Exception {
        SmsSingleSender sender = new SmsSingleSender(appid, strAppKey);
        SmsSingleSenderResult singleSenderResult = new SmsSingleSenderResult();
        singleSenderResult.result = 1;
        singleSenderResult = sender.sendWithParam("86", phone, templateId, params, "", "", "");
        return singleSenderResult.result;
    }

}
