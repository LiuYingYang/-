package com.medusa.basemall.product.service;

import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.Service;
import com.medusa.basemall.product.entity.LogisticCancel;
import com.medusa.basemall.product.vo.CalculateFareVo;
import com.medusa.basemall.product.vo.CalculateLogsticFeeVo;

import java.util.List;

/**
 * Created by psy on 2018/05/25.
 */
public interface LogisticCancelService extends Service<LogisticCancel> {

	int saveOrUpdateLogisticCancel(LogisticCancel logisticCancel);

	List<LogisticCancel> findByAppmodelId(String appmodelId);

	Result findWLMsg(String wlCode, String wlNum);

	List<CalculateFareVo> calculateLogsticFee(CalculateLogsticFeeVo calculateLogsticFeeVo);

	LogisticCancel selectDefultTrue(String appmodelId);
}
