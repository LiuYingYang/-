package com.medusa.gruul.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.medusa.gruul.sms.model.entity.TSmsOrderEntity;
import com.medusa.gruul.sms.model.dto.SendSmsDto;

import java.util.List;

/**
 * Copyright(C) 2019-12-22 17:32 美杜莎 Inc.ALL Rights Reserved.
 *
 * @version V1.0
 * @description: 美杜莎
 * @author: wangpeng
 * @date: 2019-12-22 17:32
 **/
public interface SmsOrderService extends IService<TSmsOrderEntity> {



   /***
   * 创建短信工单
   * @param sendSmsDto
   * @return: void
   * @throws:
   * @author: wangpeng
   * @version : v1.0
   * @date: 2019/12/29 9:14 PM
   */
    int doCreateOrder(SendSmsDto sendSmsDto);

    /***
     * 查询等待发送的短信工单
     * @param smsSendStatus
     * @return: List<TSmsOrderEntity>
     * @throws:
     * @author: wangpeng
     * @version : v1.0
     * @date: 2019/12/29 9:14 PM
     */
    List<TSmsOrderEntity> doListWaitSendOrder(int smsSendStatus);
}
