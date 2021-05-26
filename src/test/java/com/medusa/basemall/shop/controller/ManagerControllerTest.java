package com.medusa.basemall.shop.controller;

import com.alibaba.fastjson.JSON;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.shop.entity.Manager;
import com.medusa.basemall.shop.service.ManagerService;
import com.medusa.basemall.shop.vo.UpdateManagerVO;
import com.medusa.basemall.shop.vo.VersionBuyVO;
import com.medusa.basemall.utiles.MockMvcUtil;
import com.vip.vjtools.vjkit.number.RandomUtil;
import com.vip.vjtools.vjkit.time.ClockUtil;
import com.vip.vjtools.vjkit.time.DateUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Transactional
public class ManagerControllerTest extends BasemallApplicationTests {

	@Autowired
	private ManagerService managerService;

	@Test
	public void updateManagerTest() {
		String appId = "wx1aed4c1665e10c2f";
		UpdateManagerVO updateManagerVO = new UpdateManagerVO();
		updateManagerVO.setAppid(appId);
		updateManagerVO.setMchId(RandomUtil.randomAsciiFixLength(10));
		updateManagerVO.setMchKey(RandomUtil.randomAsciiFixLength(10));
		updateManagerVO.setCertificatePath(RandomUtil.randomAsciiFixLength(120));
		MockMvcUtil.sendRequest("/manager/updateManager/v1", JSON.toJSONString(updateManagerVO), null, "put");
		Manager manager = managerService.findBy("appId", appId);
		Assert.assertNotNull(manager);
		Assert.assertEquals(updateManagerVO.getMchId(), manager.getMchId());
		Assert.assertEquals(updateManagerVO.getCertificatePath(), manager.getCertificatePath());
		Assert.assertEquals(updateManagerVO.getMchKey(), manager.getMchKey());
	}

	//标准版余额更换营销版
	@Test
	public void versionContinuationfeeTest3() {
		Manager manager = managerService.findBy("appmodelId", "S00040001wx4f8730e96f773fa3");
		//预期余额
		BigDecimal balance = manager.getBalance().subtract(new BigDecimal(500)).setScale(2, BigDecimal.ROUND_HALF_UP);
		//预期增加时间
		Date expectDate = DateUtil.addMonths(ClockUtil.currentDate(), 12);
		VersionBuyVO versionBuyVO = new VersionBuyVO();
		versionBuyVO.setBuyVersion(2);
		versionBuyVO.setOriginalVersion(1);
		versionBuyVO.setOriginalVersionNum(-1);
		versionBuyVO.setType(2);
		versionBuyVO.setServiceContext("服务内容");
		versionBuyVO.setMarking("miniApp");
		versionBuyVO.setPayFee(500D);
		versionBuyVO.setAppmodelId("S00040001wx4f8730e96f773fa3");
		versionBuyVO.setDeduction(500D);
		versionBuyVO.setPayMethod(1);
		MockMvcUtil.sendRequest("/manager/version/continuation/fee/v1", JSON.toJSONString(versionBuyVO), null, "post");
		manager = managerService.findBy("appmodelId", "S00040001wx4f8730e96f773fa3");
		Assert.assertNotNull(manager);
		Assert.assertEquals(balance, manager.getBalance());
		Assert.assertEquals(new Integer(2), manager.getVersion());

		if (!DateUtil.isBetween(expectDate, DateUtil.subMinutes(manager.getExpiryDate(), 5),
				DateUtil.addMinutes(manager.getExpiryDate(), 5))) {
			throw new AssertionError();
		}
	}

	//营销版余额更换旗舰版
	@Test
	public void versionContinuationfeeTest2() {
		Manager manager = managerService.findBy("appmodelId", "S00040001wx4f8730e96f773fa3");
		BigDecimal balance = manager.getBalance();
		VersionBuyVO versionBuyVO = new VersionBuyVO();
		versionBuyVO.setBuyVersion(3);
		versionBuyVO.setOriginalVersion(2);
		versionBuyVO.setOriginalVersionNum(200);
		versionBuyVO.setType(2);
		versionBuyVO.setServiceContext("服务内容");
		versionBuyVO.setMarking("miniApp");
		double payFee = new BigDecimal(((1000D / 365) - (500D / 365)) * 200).setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
		versionBuyVO.setPayFee(payFee);
		versionBuyVO.setAppmodelId("S00040001wx4f8730e96f773fa3");
		versionBuyVO.setDeduction(payFee);
		versionBuyVO.setPayMethod(1);
		MockMvcUtil.sendRequest("/manager/version/continuation/fee/v1", JSON.toJSONString(versionBuyVO), null, "post");
		manager = managerService.findBy("appmodelId", "S00040001wx4f8730e96f773fa3");
		Assert.assertNotNull(manager);
		Assert.assertEquals(balance.subtract(new BigDecimal(payFee)).setScale(2, BigDecimal.ROUND_HALF_UP),
				manager.getBalance());
		Assert.assertEquals(new Integer(3), manager.getVersion());
		Date date = DateUtil.setYears(new Date(), 2099);
		date = DateUtil.setMonths(date, 10);
		date = DateUtil.setDays(date, 9);
		date = DateUtil.setHours(date, 0);
		date = DateUtil.setMinutes(date, 0);
		date = DateUtil.setSeconds(date, 0);
		date = DateUtil.setMilliseconds(date, 0);
		Assert.assertEquals(date, manager.getExpiryDate());
	}

	//营销版余额续费营销版
	@Test
	public void versionContinuationfeeTest1() {
		VersionBuyVO versionBuyVO = new VersionBuyVO();
		versionBuyVO.setBuyVersion(2);
		versionBuyVO.setOriginalVersion(2);
		versionBuyVO.setOriginalVersionNum(365);
		versionBuyVO.setType(1);
		versionBuyVO.setServiceContext("服务内容");
		versionBuyVO.setMarking("miniApp");
		versionBuyVO.setPayFee(500D);
		versionBuyVO.setAppmodelId("S00040001wx4f8730e96f773fa3");
		versionBuyVO.setDeduction(500D);
		versionBuyVO.setPayMethod(1);
		MockMvcUtil.sendRequest("/manager/version/continuation/fee/v1", JSON.toJSONString(versionBuyVO), null, "post");
		Manager manager = managerService.findBy("appmodelId", "S00040001wx4f8730e96f773fa3");
		Assert.assertNotNull(manager);
		Assert.assertEquals(new BigDecimal(1000).setScale(2, BigDecimal.ROUND_HALF_UP), manager.getBalance());
		Assert.assertEquals(new Integer(2), manager.getVersion());
		Date date = DateUtil.setYears(new Date(), 2100);
		date = DateUtil.setMonths(date, 10);
		date = DateUtil.setDays(date, 9);
		date = DateUtil.setHours(date, 0);
		date = DateUtil.setMinutes(date, 0);
		date = DateUtil.setSeconds(date, 0);
		date = DateUtil.setMilliseconds(date, 0);
		Assert.assertEquals(date, manager.getExpiryDate());
	}

}