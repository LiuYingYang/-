package com.medusa.basemall.jobhandler;


import com.medusa.basemall.integral.dao.PrizeDetailMapper;
import com.medusa.basemall.integral.dao.PrizeRuleMapper;
import com.medusa.basemall.integral.entity.PrizeDetail;
import com.medusa.basemall.integral.entity.PrizeRule;
import com.medusa.basemall.shop.dao.ColumnFlagMapper;
import com.medusa.basemall.shop.dao.ManagerMapper;
import com.medusa.basemall.shop.entity.ColumnFlag;
import com.medusa.basemall.shop.entity.Manager;
import com.medusa.basemall.user.service.WxuserService;
import com.medusa.basemall.utils.TimeUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 定时任务(每月20号为商家查询用户即将过期的积分给予提醒，每月1号进行积分定时清理)
 * @author Created by wx on 2018/08/22.
 */
@Component
@JobHandler(value = "EmptyExpiredIntegral")
public class EmptyExpiredIntegral extends IJobHandler {

	@Autowired
	public WxuserService wxuserService;

	@Resource
	private ManagerMapper managerMapper;

	@Resource
	private ColumnFlagMapper columnFlagMapper;

	@Resource
	private PrizeRuleMapper prizeRuleMapper;

	@Resource
	private PrizeDetailMapper prizeDetailMapper;

	@Transactional(rollbackFor = Exception.class)
	public void check(String now, String creatDtae, Integer inDate, PrizeDetail prizeDetail) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 积分获得时间
		Date creatDtaeNew = sdf.parse(creatDtae);
		// 当前时间
		Date nowNew = sdf.parse(now);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowNew);
		// 比较当前月份与有效时间
		if (calendar.get(Calendar.MONTH) < inDate) {
			calendar.set(Calendar.MONTH, 12 - (inDate % 12 - calendar.get(Calendar.MONTH)));
			calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - (inDate / 12) - 1);
			// 比较获得时间与清除时间
			if (creatDtaeNew.before(calendar.getTime())) {
				prizeDetail.setDeleteState(true);
				prizeDetailMapper.updateByPrimaryKeySelective(prizeDetail);
			}
		} else {
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - inDate);
			if (creatDtaeNew.before(calendar.getTime())) {
				prizeDetail.setDeleteState(true);
				prizeDetailMapper.updateByPrimaryKeySelective(prizeDetail);
			}
		}
	}


	@Override
	public ReturnT<String> execute(String s) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Manager> managers = managerMapper.selectAll();
		if (managers.size() > 0) {
			for (Manager manager : managers) {
				ColumnFlag columnFlag = columnFlagMapper.findByAppmodelId(manager.getAppmodelId());
				if (columnFlag.getShopFlag() == true) {
					PrizeRule prizeRule = prizeRuleMapper.findByAppmodelId(manager.getAppmodelId());
					if (prizeRule.getIndate() != 0) {
						List<PrizeDetail> prizeDetails = prizeDetailMapper.selectByAppmodelId(manager.getAppmodelId());
						if (prizeDetails.size() > 0) {
							String now = TimeUtil.getNowTime();
							for (PrizeDetail prizeDetail : prizeDetails) {

								check(now, prizeDetail.getCreateTime(), prizeRule.getIndate(), prizeDetail);

							}
						}
					}
				}
			}

		}
		return ReturnT.SUCCESS;
	}

}
