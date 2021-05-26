package com.medusa.basemall.messages.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.messages.dao.WxuserFormIdMapper;
import com.medusa.basemall.messages.entity.WxuserFormId;
import com.medusa.basemall.messages.service.WxuserFormIdService;
import com.medusa.basemall.utils.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author Created by wx on 2018/08/09.
 */
@Service
public class WxuserFormIdServiceImpl extends AbstractService<WxuserFormId> implements WxuserFormIdService {

	@Resource
	private WxuserFormIdMapper tWxuserFormidMapper;


	@Override
	public void saveWxuserFoemId(String formIds, String openId, String appmodelId) {
		// 保存formid数据
		String[] formId = formIds.split(",");
		for (int i = 0; i < formId.length; i++) {
			if (formId[i].contains("the formId is a mock one")) {
				continue;
			}
			WxuserFormId wxuserFormId = new WxuserFormId();
			String now = TimeUtil.getNowTime();
			wxuserFormId.setCreateTime(now);
			wxuserFormId.setFormValue(formId[i]);
			wxuserFormId.setOpenId(openId);
			tWxuserFormidMapper.insert(wxuserFormId);
		}
	}

	@Override
	public void deleteBeyondIndate(String time) {
		tWxuserFormidMapper.deleteBeyondIndate(time);
	}

}
