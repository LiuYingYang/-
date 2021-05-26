package com.medusa.basemall.messages.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.messages.entity.WxuserFormId;

import java.util.List;

/**
 * @author Created by wx on 2018/08/09.
 */
public interface WxuserFormIdMapper extends Mapper<WxuserFormId> {


    /**
     * 根据openId查询可用的formId
     *
     * @param openId
     * @return List<WxuserFormId>
     */
    List<WxuserFormId> selectByOpenId(String openId);

	void deleteBeyondIndate(String time);
}