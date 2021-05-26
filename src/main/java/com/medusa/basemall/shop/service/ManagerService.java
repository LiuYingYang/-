package com.medusa.basemall.shop.service;

import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.Service;
import com.medusa.basemall.shop.entity.Manager;
import com.medusa.basemall.shop.vo.MiniInfoVO;
import com.medusa.basemall.shop.vo.VersionBuyVO;

/**
 * Created by medusa on 2018/05/23.
 */
public interface ManagerService extends Service<Manager> {


    Manager selectByAppmodelId(String appmodelId);

	Result versionContinuationFee(VersionBuyVO versionBuyVO);

	String versionContinuationFeeNotify(String xmlResult);

	int updateSecretKey(String appid, String certificatePath, String mchId, String mchKey);

	MiniInfoVO getMiniInfo(String appmodelId);

	void updateInfo(Manager manager);

	Object getMyproperty(String appmodelId);

	Result topUpBalance(String appmodelId, String topUpBalance);

	String topUpBalanceNotify(String xmlResult);

	Result unfreezeBlance(String appmodelId, String code, String phone);

	Result getBalanceRecord(Integer pageSize, Integer pageNum, String startTime, String endTime, String appmodelId);

	String balanceWithdrawdeposit(String balance, String code, String phone, String realName, String appmodelId);

	Result fidnOrderState(String outTradeNo, Integer type);

	Result updateExpiryDateNotiyf(String appmodelId);
}
