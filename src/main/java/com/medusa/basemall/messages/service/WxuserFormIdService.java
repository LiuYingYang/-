package com.medusa.basemall.messages.service;

import com.medusa.basemall.core.Service;
import com.medusa.basemall.messages.entity.WxuserFormId;

/**
 * @author Created by wx on 2018/07/25.
 */
public interface WxuserFormIdService extends Service<WxuserFormId> {

    /**
     * 保存用户formId
     *
     *
     * @param formIds
     * @param openId
     * @return void
     */
    void saveWxuserFoemId(String formIds, String openId, String appmodelId);

	void deleteBeyondIndate(String format);
}
