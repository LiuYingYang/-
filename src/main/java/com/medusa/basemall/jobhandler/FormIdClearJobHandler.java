package com.medusa.basemall.jobhandler;

import com.medusa.basemall.messages.service.WxuserFormIdService;
import com.vip.vjtools.vjkit.time.ClockUtil;
import com.vip.vjtools.vjkit.time.DateFormatUtil;
import com.vip.vjtools.vjkit.time.DateUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@JobHandler(value = "FormIdClearJobHandler")
@Component
public class FormIdClearJobHandler extends IJobHandler {

	@Autowired
	private WxuserFormIdService wxuserFormIdService;

	@Override
	public ReturnT<String> execute(String param) throws Exception {
		String format = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.format(DateUtil.subDays(ClockUtil.currentDate(), 6));
		wxuserFormIdService.deleteBeyondIndate(format);
		return SUCCESS;
	}
}
