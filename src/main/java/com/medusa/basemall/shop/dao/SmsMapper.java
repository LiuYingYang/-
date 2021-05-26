package com.medusa.basemall.shop.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.shop.entity.Sms;

import java.util.Map;

public interface SmsMapper extends Mapper<Sms> {

    Sms selectByPhone(Map<String, Object> map);

    int updateCode(Map<String, Object> map);
}