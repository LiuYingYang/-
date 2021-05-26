package com.medusa.basemall.shop.serviceimpl;


import com.medusa.basemall.constant.TimeType;
import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.core.ServiceException;
import com.medusa.basemall.shop.dao.ShopInfoDao;
import com.medusa.basemall.shop.dao.SmsMapper;
import com.medusa.basemall.shop.entity.ShopInfo;
import com.medusa.basemall.shop.entity.Sms;
import com.medusa.basemall.shop.service.SmsService;
import com.medusa.basemall.utils.RegexMatches;
import com.medusa.basemall.utils.SSMSend;
import com.medusa.basemall.utils.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by medusa on 2018/05/24.
 * 需要事物时添加  @Transactional
 */

@Service
public class SmsServiceImpl extends AbstractService<Sms> implements SmsService {
	@Resource
	private SmsMapper tSmsMapper;

	@Resource
	private ShopInfoDao shopInfoDao;


	@Override
	public Sms selectByPhone(Map<String, Object> map) {
		return tSmsMapper.selectByPhone(map);
	}

	@Override
	public Integer updateCode(Map<String, Object> map) {
		return tSmsMapper.updateCode(map);
	}

	@Override
	public Result getVerificationCcode(String phone, Integer type, String appmodelId) throws Exception {
		if ("".equals(phone)) {
			return ResultGenerator.genFailResult("手机号不能为空");
		}
		//生成随机数
		String random = RegexMatches.getInt(900000, 99999).toString();
		ArrayList<String> params = new ArrayList<>();
		params.add(random);
		params.add("5");
		Map<String, Object> map = new HashMap<>(5);
		map.put("phone", phone);
		map.put("appmodelId", appmodelId);
		map.put("type", type);
		Sms memberSms = selectByPhone(map);
		int result = 1;
		//不存在 --->创建
		if (memberSms == null) {
			memberSms = new Sms();
			memberSms.setCreateTime(TimeUtil.getNowTime());
			memberSms.setUpdateTime(TimeUtil.getNowTime());
			memberSms.setType(type);
			memberSms.setSmsCode(random);
			memberSms.setUserTel(phone);
			memberSms.setAppmodelId(appmodelId);
			tSmsMapper.insertSelective(memberSms);
			result = SSMSend
					.sendNoto(1400038028, "040e0e068f9653eb7600b788f4499ef7", params, memberSms.getUserTel(), 106089);
			//存在,验证码更新并重新发送
		} else {
			//判断验证码是否在有效期之外
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date ctd = simpleDateFormat.parse(memberSms.getUpdateTime());
			if (System.currentTimeMillis() - ctd.getTime() > TimeType.FIVEMINUTES) {
				//根据手机号码更新验证码
				memberSms.setSmsCode(random);
				map.put("updateTime", TimeUtil.getNowTime());
				map.put("smsCode", random);
				map.put("type", type);
				updateCode(map);
				result = SSMSend
						.sendNoto(1400038028, "040e0e068f9653eb7600b788f4499ef7", params, memberSms.getUserTel(),
								106089);
			} else {
				result = 3;
			}
		}
		if (result == 0) {
			return ResultGenerator.genSuccessResult();
		} else if (result == 3) {
			return ResultGenerator.genFailResult("验证码未失效");
		} else {
			return ResultGenerator.genFailResult("短信发送失败");
		}
	}

	@Override
	public Boolean inspection(String appmodelId, Boolean isShopPhone, String phone, String code, Integer type) {
		//根据AppmodeId查找商家信息
		String telePhone = phone;
		if (isShopPhone) {
			ShopInfo info = shopInfoDao.getByAppmodelId(appmodelId);
			if (null == info) {
				throw new ServiceException("无此商家信息");
			}
			if (!info.getTelephone().equals(phone)) {
				throw new ServiceException("手机号有误");
			}
			telePhone = info.getTelephone();
		}
		Map<String, Object> param = new HashMap<>(3);
		param.put("phone", telePhone);
		param.put("appmodelId", appmodelId);
		param.put("type", type);
		Sms sms = selectByPhone(param);
		if (System.currentTimeMillis() - TimeUtil.str2Timestamp(sms.getUpdateTime()) > TimeType.FIVEMINUTES) {
			throw new ServiceException("验证码已失效");
		}
		if (!StringUtils.equals(code, sms.getSmsCode())) {
			throw new ServiceException("验证码有误");
		}
		return true;
	}


}
