package com.medusa.basemall.aop;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.RedisPrefix;
import com.medusa.basemall.core.ServiceException;
import com.medusa.basemall.shop.dao.ManagerMapper;
import com.medusa.basemall.shop.entity.Manager;
import com.medusa.basemall.utils.TimeUtil;
import com.vip.vjtools.vjkit.security.CryptoUtil;
import com.vip.vjtools.vjkit.text.EncodeUtil;
import com.vip.vjtools.vjkit.time.ClockUtil;
import com.vip.vjtools.vjkit.time.DateFormatUtil;
import com.vip.vjtools.vjkit.time.DateUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Date;

/**
 * 版本url权限拦截
 */
@Aspect
@Component
public class VsesionAspect {


	@Resource
	private ManagerMapper managerMapper;

	@Resource
	private RedisTemplate redisTemplate;


	private static final String SERRET = "CBrjph8U0DTp/SA8YM6Nnw==";

	@Pointcut(value = "@annotation(org.springframework.web.bind.annotation.PutMapping)"
			+ "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping) "
			+ "|| @annotation(org.springframework.web.bind.annotation.PostMapping)")
	public void Version() {
	}

	@Before("Version()")
	public void deBefore(JoinPoint jp) throws ParseException {
		//获取类和方法上的注解方法优先去方法上的注解
		MethodSignature signature = (MethodSignature) jp.getSignature();
		Method method = signature.getMethod();
		VersionManager annotation = method.getAnnotation(VersionManager.class);
		if (annotation == null) {
			Class<?> Class = jp.getTarget().getClass();
			annotation = Class.getAnnotation(VersionManager.class);
			if (annotation == null) {
				throw new ServiceException("程序出错!!");
			}
		}
		if (annotation.enable()) {
			//获取token并解密
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			String token = request.getHeader("Token");
			if (token == null) {
				throw new ServiceException("token not null");
			}
			String appmodelId = CryptoUtil.aesDecrypt(EncodeUtil.decodeBase64(token), EncodeUtil.decodeBase64(SERRET));
			//判断是否存在缓存或数据库
			String json = (String) redisTemplate.opsForValue().get(RedisPrefix.MANAGER_VERSION + appmodelId);
			Manager manager = JSONObject.parseObject(json, Manager.class);
			if (manager == null) {
				manager = managerMapper.selectByAppmodelId(appmodelId);
				if (manager == null) {
					throw new ServiceException("非法请求");
				}
				redisTemplate.opsForValue()
						.set(RedisPrefix.MANAGER_VERSION + appmodelId, JSONObject.toJSONString(manager));
			}

			boolean versionFlag = manager.getVersion() < annotation.versoinNumber();
			//请求方,1小程序端,2pc端
			String genre = request.getHeader("Genre");
			if (manager.getVersion() < 1 || manager.getVersion() > 3) {
				throw new ServiceException("非法访问");
			}
			//店铺版本为标准版,并且没有缴纳保证金
			if (manager.getVersion().equals(1) && manager.getFlag().equals(false)) {
				if (genre.equals("1")) {
					throw new ServiceException("小程序无法使用");
				} else if (genre.equals("2")) {
					throw new ServiceException("未缴纳保证金");
				}
			} else if (manager.getVersion().equals(1) && versionFlag) {
				throw new ServiceException("当前版本无法使用该功能");

			}
			//旗舰版和营销版设有到期时间
			Date start = DateFormatUtil.parseDate(DateFormatUtil.PATTERN_ISO_ON_DATE, manager.getCreateTime());
			Date end = manager.getExpiryDate();
			//不在有效期内,mini不允许访问,后台只可查询
			boolean timeFlag = !DateUtil.isBetween(ClockUtil.currentDate(), start, end);
			if (manager.getVersion().equals(2) || manager.getVersion().equals(3)) {
				//是否到期 && 当前使用的功能是标准则可使用
				if (timeFlag && annotation.versoinNumber() != 1) {
					if (genre.equals("1")) {
						throw new ServiceException("小程序到期,暂时该功能无法使用");
					} else if (genre.equals("2")) {
						throw new ServiceException("版本到期,只可使用标准功能");
					}
				}
			}
		}
	}


	public static void main(String[] args) {
		System.out.println(TimeUtil.str2Timestamp("2019-01-04 17:02:27"));
		String appmodelId = "S00040001wx4f8730e96f773fa3";
		String Secret = "CBrjph8U0DTp/SA8YM6Nnw==";
		byte[] bytes = CryptoUtil.aesEncrypt(appmodelId.getBytes(), EncodeUtil.decodeBase64(Secret));
		String encodeBase64 = EncodeUtil.encodeBase64(bytes);
		System.out.println("使用AES加密" + bytes + "  base64编码加密后的字符串:" + encodeBase64);
		String s = CryptoUtil.aesDecrypt(EncodeUtil.decodeBase64(encodeBase64), EncodeUtil.decodeBase64(Secret));
		System.out.println("解密后" + s);
	}

}

