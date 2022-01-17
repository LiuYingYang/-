package com.medusa.gruul.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.medusa.gruul.sms.model.entity.TSmsTemplateEntity;
import com.medusa.gruul.sms.model.dto.TemplateDto;

import java.util.List;

/**
 * Copyright(C) 2020-01-05 10:19 美杜莎 Inc.ALL Rights Reserved.
 *
 * @version V1.0
 * @description: 美杜莎
 * @author: wangpeng
 * @date: 2020-01-05 10:19
 **/
public interface SmsTempLateService extends IService<TSmsTemplateEntity> {

    /**
    * 添加模版
    * @param templateDto
    * @return: int
    * @throws:
    * @author: wangpeng
    * @version : v1.0
    * @date: 2020/1/5 10:22 AM
    */
    int doAddTempLate(TemplateDto templateDto);

    /***
    * 待推送审核模版
    * @param status
    * @return: java.util.List<com.medusa.gruul.sms.dao.entity.TSmsOrderEntity>
    * @throws:
    * @author: wangpeng
    * @version : v1.0
    * @date: 2020/1/5 10:32 AM
    */
    List<TSmsTemplateEntity> doListWaitVerifyTempLate(long status);

    /***
    * 短信模版推审
    * @param tSmsTemplateEntity
    * @return: void
    * @throws:
    * @author: wangpeng
    * @version : v1.0
    * @date: 2020/1/5 10:39 AM
    */
    void doVerify(TSmsTemplateEntity tSmsTemplateEntity);
}
