package com.medusa.basemall.jobhandler;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.activemq.ActiveMqClient;
import com.medusa.basemall.constant.TimeType;
import com.medusa.basemall.jobhandler.to.ActiveMqTaskTO;
import com.mongodb.client.result.UpdateResult;
import com.vip.vjtools.vjkit.base.annotation.NotNull;
import com.vip.vjtools.vjkit.time.ClockUtil;
import com.vip.vjtools.vjkit.time.DateFormatUtil;
import com.vip.vjtools.vjkit.time.DateUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author whh
 */
@JobHandler(value = "ActiveDelaySendJobHandler")
@Component
public class ActiveDelaySendJobHandler extends IJobHandler {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private ActiveMqClient activeMqClient;

	public void savaTask(@NotNull String jsonData, @NotNull String queueName, @NotNull Long milliSecond,
			@NotNull String appmodelId) {
		try {
			//如果10分钟之内的直接发送
			if (milliSecond <= TimeType.TENMINUTES) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", jsonData);
				jsonObject.put("activeMqTaskTOId", null);
				activeMqClient.delaySend(jsonObject.toJSONString(), queueName, milliSecond);
				return;
			}
			ActiveMqTaskTO activeMqTaskTO = new ActiveMqTaskTO();
			activeMqTaskTO.setCreateTime(ClockUtil.currentDate());
			activeMqTaskTO.setJsonData(jsonData);
			long end = activeMqTaskTO.getCreateTime().getTime() + milliSecond;
			Date endTime = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT
					.parse(DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.format(end));
			activeMqTaskTO.setEndTime(endTime);
			activeMqTaskTO.setState(Boolean.FALSE);
			activeMqTaskTO.setQueueName(queueName);
			activeMqTaskTO.setSendState(false);
			activeMqTaskTO.setSendSum(0);
			activeMqTaskTO.setAppmodelId(appmodelId);
			mongoTemplate.save(activeMqTaskTO);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 延时发送消息确认队列是否已执行
	 * @param activeMqTaskTOId
	 */
	public void updateState(String activeMqTaskTOId) {
		if (activeMqTaskTOId == null || activeMqTaskTOId.length() == 0) {
			return;
		}
		int findSum = 0;
		while (true) {
			try {
				if (findSum == 5) {
					XxlJobLogger.log("mongodb 超时连接已达5次任务执行失败!!! Method ------> updateState)");
				}
				Query query = new Query();
				query.addCriteria(Criteria.where("id").is(activeMqTaskTOId));
				UpdateResult state = mongoTemplate
						.updateFirst(query, Update.update("state", Boolean.TRUE), ActiveMqTaskTO.class);
				break;
			} catch (Exception e) {
				findSum++;
				XxlJobLogger.log("mongodb查询超时,第" + findSum + "次, Method ------> updateState");
			}
		}

	}

	/**
	 * 发送到10分钟内需要处理的延迟队列
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Override
	public ReturnT<String> execute(String param) {

		Date currentDate = ClockUtil.currentDate();
		Date date = DateUtil.addMinutes(currentDate, 10);
		Query query = new Query();
		query.addCriteria(Criteria.where("endTime").lt(date).and("state").is(false));
		List<ActiveMqTaskTO> activeMqTaskTOS = null;
		int findSum = 0;
		while (true) {
			try {
				if (findSum == 5) {
					XxlJobLogger.log("mongodb 超时连接已达5次任务执行失败!!! Method ------> execute");
					return FAIL;
				}
				activeMqTaskTOS = mongoTemplate.find(query, ActiveMqTaskTO.class);
				break;
			} catch (Exception e) {
				findSum++;
				XxlJobLogger.log("mongodb查询超时,第" + findSum + "次,Method ------> execute");
			}
		}

		try {
			int i = 0;
			for (ActiveMqTaskTO activeMqTaskTO : activeMqTaskTOS) {

				isJson(activeMqTaskTO);
				if (activeMqTaskTO.getSendSum().equals(0)) {
					activeMqTaskTO.setSendState(true);
					activeMqTaskTO.setSendSum(1);
				} else {
					activeMqTaskTO.setSendSum(activeMqTaskTO.getSendSum() + 1);
				}
				i++;
				long endtime = activeMqTaskTO.getEndTime().getTime() - ClockUtil.currentTimeMillis();
				if (activeMqTaskTO.getLastSendTime() != null) {
					Date last = DateUtil.subMinutes(currentDate, 10);
					if (DateUtil.isBetween(activeMqTaskTO.getLastSendTime(), last, currentDate)) {
						continue;
					}
				}
				activeMqTaskTO.setLastSendTime(currentDate);
				activeMqClient.delaySend(activeMqTaskTO.getJsonData(), activeMqTaskTO.getQueueName(), endtime);
				mongoTemplate.save(activeMqTaskTO);
			}
			if (i != activeMqTaskTOS.size()) {
				if (activeMqTaskTOS.size() - i > 0) {
					XxlJobLogger.log("部分消息为发送成功");
				}
				if (i == 0) {
					XxlJobLogger.log("未发送任何消息");
				}
				return FAIL;
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private void isJson(ActiveMqTaskTO activeMqTaskTO) {
		try {
			JSONObject jsonObject = JSONObject.parseObject(activeMqTaskTO.getJsonData());
			jsonObject.put("activeMqTaskTOId", activeMqTaskTO.getId());
			activeMqTaskTO.setJsonData(jsonObject.toJSONString());
		} catch (JSONException e) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", activeMqTaskTO.getJsonData());
			jsonObject.put("activeMqTaskTOId", activeMqTaskTO.getId());
			activeMqTaskTO.setJsonData(jsonObject.toJSONString());
		}
	}

}
