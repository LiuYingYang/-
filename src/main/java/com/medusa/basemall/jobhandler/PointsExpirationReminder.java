package com.medusa.basemall.jobhandler;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.activemq.ActiveMqClient;
import com.medusa.basemall.constant.ActiviMqQueueName;
import com.medusa.basemall.constant.SendTemplatMessageType;
import com.medusa.basemall.core.ServiceException;
import com.medusa.basemall.integral.dao.PrizeDetailMapper;
import com.medusa.basemall.integral.dao.PrizeRuleMapper;
import com.medusa.basemall.integral.entity.PrizeDetail;
import com.medusa.basemall.integral.entity.PrizeRule;
import com.medusa.basemall.shop.dao.ColumnFlagMapper;
import com.medusa.basemall.shop.entity.ColumnFlag;
import com.medusa.basemall.shop.entity.Manager;
import com.medusa.basemall.shop.service.ManagerService;
import com.medusa.basemall.user.entity.Wxuser;
import com.medusa.basemall.user.service.WxuserService;
import com.vip.vjtools.vjkit.time.ClockUtil;
import com.vip.vjtools.vjkit.time.DateFormatUtil;
import com.vip.vjtools.vjkit.time.DateUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@JobHandler(value = "PointsExpirationReminder")
@Component
public class PointsExpirationReminder extends IJobHandler {


	@Resource
	public WxuserService wxuserService;

	@Resource
	private ColumnFlagMapper columnFlagMapper;
	@Resource
	private PrizeRuleMapper prizeRuleMapper;
	@Resource
	private PrizeDetailMapper prizeDetailMapper;
	@Resource
	private ActiveMqClient activeMqClient;
	@Resource
	private ManagerService managerService;

	@Override
	public ReturnT<String> execute(String param) {
		try {
			Condition condition = new Condition(Manager.class);
			condition.createCriteria().andGreaterThan("version", 1).andGreaterThan("createTime",
					DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.format(ClockUtil.currentDate()));
			List<Manager> managers = managerService.findByCondition(condition);
			for (Manager manager : managers) {
				ColumnFlag columnFlag = columnFlagMapper.findByAppmodelId(manager.getAppmodelId());
				//积分商城关闭,跳过本次
				if (columnFlag.getShopFlag() == false) {
					continue;
				}
				Condition wxuserCondition = new Condition(Wxuser.class);
				wxuserCondition.createCriteria().andEqualTo("appmodelId").andEqualTo("deleteState", 0);
				List<Wxuser> wxusers = wxuserService.findByCondition(wxuserCondition);
				PrizeRule prizeRule = prizeRuleMapper.findByAppmodelId(manager.getAppmodelId());
				if (wxusers.size() > 0 && prizeRule.getIndate() != 0) {
					for (Wxuser wxuser : wxusers) {
						Integer all = 0;
						List<PrizeDetail> prizeDetails = prizeDetailMapper.selectByWxuserId(wxuser);
						if (prizeDetails.size() > 0) {
							Date currentDate = ClockUtil.currentDate();
							String now = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.format(currentDate);

							for (PrizeDetail prizeDetail : prizeDetails) {
								all += remindCheck(now, prizeDetail.getCreateTime(), prizeRule.getIndate(),
										prizeDetail);
							}
							if (all > 0) {
								//模板消息积分到期
								JSONObject jsonObject = new JSONObject();
								jsonObject.put("wxuserId", wxuser.getWxuserId());
								jsonObject.put("integralDue", all);
								jsonObject.put("endTime", DateFormatUtil.DEFAULT_ON_SECOND_FORMAT
										.format(DateUtil.nextMonth(currentDate)));
								jsonObject.put("messageType", SendTemplatMessageType.INTEGRAL_EXPIRE);
								activeMqClient.send(jsonObject.toJSONString(),
										ActiviMqQueueName.ORDER_MINIPROGRAM_TEMPLATE_MESSAGE);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return ReturnT.SUCCESS;
	}

	public Integer remindCheck(String now, String creatDtae, Integer inDate, PrizeDetail prizeDetail) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			// 积分获得时间
			Date creatDtaeNew = sdf.parse(creatDtae);
			// 当前时间
			Date nowNew = sdf.parse(now);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(nowNew);
			// 比较当前月份与有效时间
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
			calendar.set(Calendar.DATE, 1);
			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			System.err.print(calendar.getTime());
			if (calendar.get(Calendar.MONTH) < inDate) {
				calendar.set(Calendar.MONTH, 12 - (inDate % 12 - calendar.get(Calendar.MONTH)));
				calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - (inDate / 12) - 1);
				// 比较获得时间与清除时间
				if (creatDtaeNew.before(calendar.getTime())) {
					return prizeDetail.getIntegral();
				} else {
					return 0;
				}
			} else {
				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - inDate);
				if (creatDtaeNew.before(calendar.getTime())) {
					return prizeDetail.getIntegral();
				} else {
					return 0;
				}
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
