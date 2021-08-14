package com.medusa.basemall.aop;


import com.medusa.basemall.user.dao.UserOperationRepository;
import com.medusa.basemall.user.entity.UserOperation;
import com.vip.vjtools.vjkit.time.ClockUtil;
import com.vip.vjtools.vjkit.time.DateFormatUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LookTimeAspect {

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private UserOperationRepository userOperationRepository;

	@Pointcut(value = "@annotation(com.medusa.basemall.aop.annotation.LookTimeAspect)")
	public void LookTime() {
	}

	@Before("LookTime()")
	public void deBefore(JoinPoint jp) {
		//获取参数名
		Signature signature = jp.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		String methodName = methodSignature.getMethod().getName();
		if (methodName.equals("findOrderRefund")) {
			Long wxuserId = (Long) getParameterValue("wxuserId", methodSignature, jp.getArgs());
			if (wxuserId != null) {
				Query query = new Query(Criteria.where("wxuserId").is(wxuserId));
				UserOperation userOperation = mongoTemplate.findOne(query, UserOperation.class);
				String appmodelId = (String) getParameterValue("appmodelId", methodSignature, jp.getArgs());
				String currterTime = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.format(ClockUtil.currentDate());
				if (userOperation == null) {
					userOperation = new UserOperation();
					userOperation.setWxuserId(wxuserId);
					userOperation.setLastLookAfterSaleOrderTime(currterTime);
					userOperation.setAppmodelId(appmodelId);
					mongoTemplate.save(userOperation);
				}
				mongoTemplate.updateMulti(query, new Update().set("lastLookAfterSaleOrderTime", currterTime),
						UserOperation.class);

			}
		}
		if (methodName.equals("findOrderMini")) {
			Long wxuserId = (Long) getParameterValue("wxuserId", methodSignature, jp.getArgs());
			Integer orderState = (Integer) getParameterValue("orderState", methodSignature, jp.getArgs());
			if (wxuserId != null && orderState != null) {
				Query query = new Query(Criteria.where("wxuserId").is(wxuserId));
				UserOperation userOperation = mongoTemplate.findOne(query, UserOperation.class);
				String appmodelId = (String) getParameterValue("appmodelId", methodSignature, jp.getArgs());
				String currterTime = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.format(ClockUtil.currentDate());
				if (userOperation == null) {
					userOperation = new UserOperation();
					userOperation.setWxuserId(wxuserId);
					userOperation.setAppmodelId(appmodelId);
					mongoTemplate.save(userOperation);
				}
				if (orderState == 3) {
					mongoTemplate.updateMulti(query, new Update().set("lastLookOkOrderTime", currterTime),
							UserOperation.class);
				}
			}
		}
	}

	private Object getParameterValue(String parameterName, MethodSignature methodSignature, Object[] args) {
		String[] parameterNames = methodSignature.getParameterNames();
		Integer index = null;
		for (int i = 0; i < parameterNames.length; i++) {
			boolean flag = parameterNames[i].equals(parameterName);
			if (flag) {
				index = i;
			}
		}
		if (index != null) {
			return args[index];
		}
		return null;
	}
}