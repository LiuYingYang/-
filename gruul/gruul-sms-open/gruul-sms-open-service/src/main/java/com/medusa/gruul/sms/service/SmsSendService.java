package com.medusa.gruul.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.medusa.gruul.sms.model.entity.TSmsOrderEntity;

/**
 * Copyright(C) 2019-12-30 21:55 美杜莎 Inc.ALL Rights Reserved.
 *
 * @version V1.0
 * @description: 美杜莎
 * @author: wangpeng
 * @date: 2019-12-30 21:55
 **/
public interface SmsSendService extends IService<TSmsOrderEntity> {

    /***
    * 短信发送
    * @param smsOrderEntity
    * @return: void
    * @throws:
    * @author: wangpeng
    * @version : v1.0
    * @date: 2019/12/30 10:01 PM
    */
    void sendSms(TSmsOrderEntity smsOrderEntity);
}
