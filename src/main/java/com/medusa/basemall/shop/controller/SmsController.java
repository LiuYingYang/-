package com.medusa.basemall.shop.controller;

import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.shop.service.SmsService;
import com.medusa.basemall.shop.vo.SendSmsVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * Created by medusa on 2018/05/24.
 */
@RestController
@RequestMapping("/sms")
@VersionManager
public class SmsController {
    @Resource
    private SmsService smsService;

    /**
     * 手机号获取验证码
     * phone           //手机号码
     * appmodelId      //模板ID
     * type            //短信类型
     */
    @RequestMapping("/getVerificationCcode")
    public Result getVerificationCcode(@RequestBody SendSmsVO sendSmsVO) throws Exception {
        return smsService.getVerificationCcode(sendSmsVO.getPhone(), sendSmsVO.getType(), sendSmsVO.getAppmodelId());
    }

}
