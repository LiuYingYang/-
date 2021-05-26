package com.medusa.basemall.shop.service;


import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.Service;
import com.medusa.basemall.shop.entity.Sms;

import java.util.Map;

/**
 * Created by medusa on 2018/05/24.
 */
public interface SmsService extends Service<Sms> {

	Sms selectByPhone(Map<String, Object> map);

	Integer updateCode(Map<String, Object> map);

	Result getVerificationCcode(String phone, Integer type, String appmodelId) throws Exception;

	Boolean inspection(String appmodelId, Boolean isShopPhone, String phone, String code, Integer type);

}
