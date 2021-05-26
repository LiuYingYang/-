package com.medusa.basemall.shop.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.kevinsawicki.http.HttpRequest;
import com.medusa.basemall.activemq.ActiveMqClient;
import com.medusa.basemall.configurer.WxServiceUtils;
import com.medusa.basemall.constant.ActiviMqQueueName;
import com.medusa.basemall.constant.RedisPrefix;
import com.medusa.basemall.constant.TimeType;
import com.medusa.basemall.constant.Url;
import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.core.ServiceException;
import com.medusa.basemall.shop.dao.ManagerMapper;
import com.medusa.basemall.shop.dao.ShopInfoDao;
import com.medusa.basemall.shop.entity.Manager;
import com.medusa.basemall.shop.entity.ShopInfo;
import com.medusa.basemall.shop.entity.Sms;
import com.medusa.basemall.shop.entity.TransactionRecord;
import com.medusa.basemall.shop.service.ManagerService;
import com.medusa.basemall.shop.service.SmsService;
import com.medusa.basemall.shop.service.TransactionRecordService;
import com.medusa.basemall.shop.vo.MiniInfoVO;
import com.medusa.basemall.shop.vo.VersionBuyVO;
import com.medusa.basemall.utils.IdGenerateUtils;
import com.vip.vjtools.vjkit.io.FileUtil;
import com.vip.vjtools.vjkit.time.ClockUtil;
import com.vip.vjtools.vjkit.time.DateFormatUtil;
import com.vip.vjtools.vjkit.time.DateUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author medusa
 * @date 2018/05/23 需要事物时添加 @Transactional
 */
@Service
@Log4j2
public class ManagerServiceImpl extends AbstractService<Manager> implements ManagerService {

	@Resource
	private ShopInfoDao shopInfoDao;

	@Resource
	private SmsService smsService;

	@Resource
	private ManagerMapper tManagerMapper;

	@Resource
	private WxServiceUtils wxServiceUtils;

	@Autowired
	private ActiveMqClient activeMqClient;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private TransactionRecordService transactionRecordService;

	/** 临时存放支付码路径 */
	private static final String HOST = "https://www.superprism.cn/";

	private static final String UPPATH = "/data/webFile/basemall/APPMODELID/temp/versionbuy-APPMODELID.png";

	//private static final String UPPATH = "F:\\temp\\versionbuy";
	@Override
	public Manager selectByAppmodelId(String appmodelId) {
		return tManagerMapper.selectByAppmodelId(appmodelId);
	}

	@Override
	public Result versionContinuationFee(VersionBuyVO versionBuyVO) {
		if (null == versionBuyVO.getAppmodelId()) {
			throw new ServiceException("参数错误");
		}
		Integer totalfee = BaseWxPayRequest.yuanToFen(versionBuyVO.getPayFee().toString());
		if (totalfee <= 0 && versionBuyVO.getDeduction() + versionBuyVO.getPayFee() < 0) {
			throw new ServiceException("支付金额有误");
		}
		String outTradeNo = "mds" + ClockUtil.currentTimeMillis();
		versionBuyVO.setOutTradeNo(outTradeNo);
		versionBuyVO.setCreateTime(ClockUtil.currentDate());
		versionBuyVO.setPayState(0);
		versionBuyVO.setMarking("miniApp");
		versionBuyVO.setProductId("S00040001");
		StringBuffer buffer = new StringBuffer("标准商城小程序");
		switch (versionBuyVO.getType()) {
			case 1:
				buffer.append("小程序续费");
				break;
			case 2:
				buffer.append("小程序升级");
				break;
			case 3:
				buffer.append("新开店铺");
				break;
			default:
				break;
		}
		redisTemplate.opsForValue()
				.set(RedisPrefix.MANAGER_ORDER_VERSION + outTradeNo, JSONObject.toJSONString(versionBuyVO),
						TimeType.HALFHOUR, TimeUnit.MILLISECONDS);
		Map<String, Object> map = new HashMap<>();
		map.put("outTradeNo", outTradeNo);
		if (versionBuyVO.getPayMethod().equals(2)) {
			Manager manager = tManagerMapper.selectByAppmodelId(versionBuyVO.getAppmodelId());
			if (!versionBuyVO.getDeduction().equals(500) && versionBuyVO.getPayFee() != 0) {
				BigDecimal balance = manager.getBalance().subtract(new BigDecimal(versionBuyVO.getPayFee()))
						.setScale(2, BigDecimal.ROUND_HALF_UP);
				if (balance.doubleValue() < 0) {
					throw new ServiceException("余额不足");
				}
			}
			handleTransaction(versionBuyVO);
			return ResultGenerator.genSuccessResult(map);
		}
		if (versionBuyVO.getPayMethod().equals(1)) {
			String imgPath = weChatPay(buffer.toString(), outTradeNo, versionBuyVO.getPayFee().toString(),
					versionBuyVO.getProductId(), Url.MANAGER_VERSION_NOTIFY, versionBuyVO.getAppmodelId());
			map.put("imgPath", imgPath);
			return ResultGenerator.genSuccessResult(map);
		}

		return ResultGenerator.genFailResult("系统出错");
	}

	private String weChatPay(String body, String outTradeNo, String payFee, String productId,
			String managerVersionNotify, String appmodelId) {
		try {
			byte[] bytes = wxServiceUtils
					.wxNativePayRequest(body, outTradeNo, payFee, productId, managerVersionNotify, appmodelId);
			if (bytes == null) {
				throw new ServiceException("生成二维码失败");
			}
			File file = new File(UPPATH.replace("APPMODELID", appmodelId));
			FileUtil.makesureParentDirExists(file);
			FileUtil.touch(file);
			OutputStream fileOutputStream = FileUtil.asOututStream(file);
			fileOutputStream.write(bytes);
			fileOutputStream.flush();
			fileOutputStream.close();
			// 生成预订单到redis中
			return HOST + UPPATH.replace("/data/", "");
		} catch (WxPayException e) {
			e.printStackTrace();
			throw new ServiceException("下单失败");
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServiceException("支付码生成失败");
		}
	}

	@Override
	public String versionContinuationFeeNotify(String xmlResult) {
		log.info("订单回调XmlResult --------->" + xmlResult);
		WxPayOrderNotifyResult payOrderNotifyResult = WxPayOrderNotifyResult.fromXML(xmlResult);
		if ("SUCCESS".equalsIgnoreCase(payOrderNotifyResult.getResultCode())) {
			String s = (String) redisTemplate.opsForValue()
					.get(RedisPrefix.MANAGER_ORDER_VERSION + payOrderNotifyResult.getOutTradeNo());
			VersionBuyVO versionBuyVO = JSONObject.parseObject(s, VersionBuyVO.class);
			if (versionBuyVO.getPayState().equals(0)) {
				handleTransaction(versionBuyVO);
			}
			return "success";
		}
		return null;
	}

	private void handleTransaction(VersionBuyVO versionBuyVO) {
		// 修改订单状态,防止回调重复调用
		versionBuyVO.setPayState(1);
		transactionRecordService.save(versionBuyVO);
		switch (versionBuyVO.getBuyVersion()) {
			case 1:
				versionBuyVO.setRoomSize(256);
				break;
			case 2:
				versionBuyVO.setRoomSize(512);
				break;
			case 3:
				versionBuyVO.setRoomSize(1024);
				break;
			default:
				break;
		}
		redisTemplate.opsForValue().set(RedisPrefix.MANAGER_ORDER_VERSION + versionBuyVO.getOutTradeNo(),
				JSONObject.toJSONString(versionBuyVO), TimeType.FIVEMINUTES, TimeUnit.MILLISECONDS);
		if (versionBuyVO.getType().equals(3)) {
			// 支付成功通知平台生成订单
			activeMqClient.send(versionBuyVO.getOutTradeNo(), "Basemall_ContinuationFee");
			return;
		}
		// 修改当前版本
		Manager manager = tManagerMapper.selectByAppmodelId(versionBuyVO.getAppmodelId());
		// 标准版余额中冻结500元
		if (versionBuyVO.getBuyVersion().equals(1)) {
			manager.setFlag(true);
			manager.setExpiryDate(DateUtil.setYears(new Date(), 2099));
		}
		// 营销版 旗舰版
		if (versionBuyVO.getBuyVersion().equals(2) || versionBuyVO.getBuyVersion().equals(3)) {
			BigDecimal balance = manager.getBalance().subtract(new BigDecimal(versionBuyVO.getPayFee()))
					.setScale(2, BigDecimal.ROUND_HALF_UP);
			//原本版本是标准版
			if (versionBuyVO.getOriginalVersion().equals(1) && versionBuyVO.getDeduction() > 0) {
				balance = balance.subtract(new BigDecimal(versionBuyVO.getDeduction()))
						.setScale(2, BigDecimal.ROUND_HALF_UP);
				manager.setFlag(false);
			}
			manager.setBalance(balance);
			manager.setExpiryDateNotify(false);
			// 续费增加时间
			if (versionBuyVO.getType().equals(1)) {
				Date date = DateUtil.addMonths(manager.getExpiryDate(), 12);
				manager.setExpiryDate(date);
			}
			//更换版本
			if (versionBuyVO.getType().equals(2)) {
				//如果是从标准版购买升级版本,修改版本截止时间
				if (versionBuyVO.getOriginalVersion().equals(1)) {
					Date date = DateUtil.addMonths(ClockUtil.currentDate(), 12);
					System.out.println(DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.format(date));
					manager.setExpiryDate(date);
				}
				if (versionBuyVO.getOriginalVersion().equals(2) || versionBuyVO.getOriginalVersion().equals(3)) {
					Date date = DateUtil.addMonths(manager.getExpiryDate(), 12);
					manager.setExpiryDate(date);
				}
				manager.setVersion(versionBuyVO.getBuyVersion());
			}
		}
		redisTemplate.opsForValue()
				.set(RedisPrefix.MANAGER_VERSION + manager.getAppmodelId(), JSONObject.toJSONString(manager));
		tManagerMapper.updateByPrimaryKeySelective(manager);
		// 支付成功通知平台生成订单以及通知中心
		activeMqClient.send(versionBuyVO.getOutTradeNo(), "Basemall_ContinuationFee");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("module", 4);
		jsonObject.put("type", 2);
		jsonObject.put("managerId", manager.getId());
		jsonObject.put("appmodelId", manager.getAppmodelId());
		activeMqClient.send(jsonObject.toJSONString(), ActiviMqQueueName.MESSAGE_NOTIFY);
	}

	@Override
	public int updateSecretKey(String appId, String certificatePath, String mchId, String mchKey) {
		Manager manager = new Manager();
		manager.setMchKey(mchKey);
		manager.setMchId(mchId);
		manager.setCertificatePath(certificatePath);
		Condition condition = new Condition(Manager.class);
		condition.createCriteria().andEqualTo("appId", appId);
		if (tManagerMapper.updateByConditionSelective(manager, condition) > 0) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("appId", appId);
			jsonObject.put("mchId", mchId);
			jsonObject.put("mchKey", mchKey);
			jsonObject.put("certificatePath", certificatePath);
			HttpRequest.put("http://127.0.0.1:8080/medusaplatform/MiniProgramy/update/secret/key", true)
					.contentType("application/json;charset=UTF-8").send(jsonObject.toJSONString()).created();
			return 1;
		}
		return 0;
	}

	@Override
	public MiniInfoVO getMiniInfo(String appmodelId) {
		Manager manager = tManagerMapper.selectByAppmodelId(appmodelId);
		MiniInfoVO miniInfoVO = new MiniInfoVO();
		miniInfoVO.setLogo(manager.getLogo());
		miniInfoVO.setMiniName(manager.getMiniName());
		miniInfoVO.setMiniCode(manager.getMiniCode());
		miniInfoVO.setVersionSubscript(manager.getVersionSubscript());
		return miniInfoVO;
	}

	@Override
	public void updateInfo(Manager manager) {
		Condition condition = new Condition(Manager.class);
		condition.createCriteria().andEqualTo("appId", manager.getAppId());
		tManagerMapper.updateByConditionSelective(manager, condition);
	}

	@Override
	public MiniInfoVO getMyproperty(String appmodelId) {
		Manager manager = tManagerMapper.selectByAppmodelId(appmodelId);
		MiniInfoVO miniInfoVO = new MiniInfoVO();
		miniInfoVO.setMiniName(manager.getMiniName());
		miniInfoVO.setVersion(manager.getVersion());
		miniInfoVO.setLogo(manager.getLogo());
		miniInfoVO.setExpiryDate(manager.getExpiryDate());
		miniInfoVO.setFlag(manager.getFlag());
		miniInfoVO.setBalance(manager.getBalance());
		miniInfoVO.setExpiryDateNotify(manager.getExpiryDateNotify());
		return miniInfoVO;
	}

	@Override
	public Result topUpBalance(String appmodelId, String topUpBalance) {
		try {
			TransactionRecord transactionRecord = new TransactionRecord();
			transactionRecord.setOutTradeNo(IdGenerateUtils.getOrderNum());
			transactionRecord.setMarking("blance");
			transactionRecord.setProductId("S00040001");
			transactionRecord.setPayFee(new Double(topUpBalance));
			transactionRecord.setCreateTime(ClockUtil.currentDate());
			transactionRecord.setAppmodelId(appmodelId);
			transactionRecord.setPayState(0);
			byte[] bytes = wxServiceUtils.wxNativePayRequest("余额充值", transactionRecord.getOutTradeNo(), topUpBalance,
					transactionRecord.getProductId(), Url.MANAGER_BALANCE_NOTIFY, transactionRecord.getAppmodelId());
			if (bytes == null) {
				return ResultGenerator.genFailResult("下单错误");
			}
			File file = new File(UPPATH.replace("APPMODELID", appmodelId));
			FileUtil.makesureParentDirExists(file);
			FileUtil.touch(file);
			OutputStream fileOutputStream = FileUtil.asOututStream(file);
			fileOutputStream.write(bytes);
			fileOutputStream.flush();
			fileOutputStream.close();
			// 生成预订单到redis中
			redisTemplate.opsForValue().set(RedisPrefix.MANAGER_ORDER_BALANCE + transactionRecord.getOutTradeNo(),
					JSONObject.toJSONString(transactionRecord), TimeType.HALFHOUR, TimeUnit.MILLISECONDS);
			Map<String, Object> map = new HashMap<>();
			map.put("imgPath", HOST + UPPATH.replace("/data/", ""));
			map.put("outTradeNo", transactionRecord.getOutTradeNo());
			return ResultGenerator.genSuccessResult(map);
		} catch (WxPayException e) {
			e.printStackTrace();
			throw new ServiceException("下单失败");
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServiceException("支付码生成失败");
		}
	}

	@Override
	public String topUpBalanceNotify(String xmlResult) {
		log.info("余额订单回调XmlResult --------->" + xmlResult);
		WxPayOrderNotifyResult payOrderNotifyResult = WxPayOrderNotifyResult.fromXML(xmlResult);
		if ("SUCCESS".equalsIgnoreCase(payOrderNotifyResult.getResultCode())) {
			String json = (String) redisTemplate.opsForValue()
					.get(RedisPrefix.MANAGER_ORDER_BALANCE + payOrderNotifyResult.getOutTradeNo());
			TransactionRecord transactionRecord = JSONObject.parseObject(json, TransactionRecord.class);
			if (transactionRecord.getPayState().equals(0)) {
				// 修改订单状态,防止回调重复调用
				transactionRecord.setPayState(1);
				transactionRecordService.save(transactionRecord);
				redisTemplate.opsForValue().set(RedisPrefix.MANAGER_ORDER_BALANCE + transactionRecord.getOutTradeNo(),
						JSONObject.toJSONString(transactionRecord), TimeType.FIVEMINUTES, TimeUnit.MILLISECONDS);
				Manager manager = tManagerMapper.selectByAppmodelId(transactionRecord.getAppmodelId());
				BigDecimal balance = manager.getBalance().add(new BigDecimal(transactionRecord.getPayFee()))
						.setScale(2, BigDecimal.ROUND_HALF_UP);
				manager.setBalance(balance);
				tManagerMapper.updateByPrimaryKeySelective(manager);
			}
			return "success";
		}
		return "fail";
	}

	@Override
	public Result unfreezeBlance(String appmodelId, String code, String phone) {
		// 根据AppmodeId查找商家信息
		ShopInfo info = shopInfoDao.getByAppmodelId(appmodelId);
		if (null == info) {
			throw new ServiceException("无此商家信息");
		}
		if (!info.getTelephone().equals(phone)) {
			throw new ServiceException("手机号有误");
		}
		Map<String, Object> param = new HashMap<>(3);
		param.put("phone", info.getTelephone());
		param.put("appmodelId", appmodelId);
		param.put("type", 5);
		Sms sms = smsService.selectByPhone(param);
		if (!StringUtils.equals(code, sms.getSmsCode())) {
			throw new ServiceException("验证码有误");
		}
		Manager manager = tManagerMapper.selectByAppmodelId(appmodelId);
		//标准版解除保证金
		if (manager.getVersion().equals(1)) {
			manager.setExpiryDate(DateUtil.subDays(ClockUtil.currentDate(), 1));
		}
		manager.setFlag(false);
		tManagerMapper.updateByPrimaryKeySelective(manager);
		redisTemplate.opsForValue().set(RedisPrefix.MANAGER_VERSION + appmodelId, JSONObject.toJSONString(manager));
		return ResultGenerator.genSuccessResult();
	}

	@Override
	public Result getBalanceRecord(Integer pageSize, Integer pageNum, String startTime, String endTime,
			String appmodelId) {
		Map<String, Object> balanceRecord = transactionRecordService
				.getBalanceRecord(pageSize, pageNum, startTime, endTime, appmodelId);
		List<TransactionRecord> records = (List<TransactionRecord>) balanceRecord.get("list");
		List<String> date = records.stream().map(obj -> DateFormatUtil.formatDate("MM月dd日", obj.getCreateTime()))
				.collect(Collectors.toList());
		Map<String, List<TransactionRecord>> list = new HashMap<>();
		for (String time : date) {
			List<TransactionRecord> record = records.stream()
					.filter(obj -> DateFormatUtil.formatDate("MM月dd日", obj.getCreateTime()).equals(time))
					.collect(Collectors.toList());
			list.put(time, record);
		}
		balanceRecord.put("list", list);
		return ResultGenerator.genSuccessResult(balanceRecord);
	}

	@Override
	public String balanceWithdrawdeposit(String balance, String code, String phone, String realName,
			String appmodelId) {
		Boolean inspection = smsService.inspection(appmodelId, true, phone, code, 6);
		if (inspection) {
			Manager manager = selectByAppmodelId(appmodelId);
			String orderNum = IdGenerateUtils.getOrderNum();
			TransactionRecord transactionRecord = new TransactionRecord();
			transactionRecord.setMarking("balance");
			transactionRecord.setCreateTime(ClockUtil.currentDate());
			transactionRecord.setOutTradeNo(orderNum);
			transactionRecord.setPayFee(new Double(balance));
			//这里的openid指的是平台项目中关注服务号的openid  使用的appid也是公众号的
			String openId = HttpRequest.get(Url.GET_FWH_OPENID.replace("APPMODELID", appmodelId)).body();
			Map<String, String> map = wxServiceUtils
					.enterprisePayment(appmodelId, openId.replace("\"", ""), orderNum, realName, balance, "标准商城余额提现",
							"wx70715838e115372f");
			if (map.size() > 0) {
				throw new ServiceException(map.get("errCodeDes"));
			}
			transactionRecord.setPayState(1);
			transactionRecordService.save(transactionRecord);
			BigDecimal b = manager.getBalance().subtract(new BigDecimal(balance)).setScale(2, BigDecimal.ROUND_HALF_UP);
			manager.setBalance(b);
			tManagerMapper.updateByPrimaryKeySelective(manager);
			return "success";
		}
		throw new ServiceException("校验失败");
	}

	@Override
	public Result fidnOrderState(String outTradeNo, Integer type) {
		if (type == 1) {
			String s = (String) redisTemplate.opsForValue().get(RedisPrefix.MANAGER_ORDER_VERSION + outTradeNo);
			VersionBuyVO versionBuyVO = JSONObject.parseObject(s, VersionBuyVO.class);
			if (versionBuyVO.getPayState().equals(1)) {
				return ResultGenerator.genSuccessResult();
			}
		}
		if (type == 2) {
			String json = (String) redisTemplate.opsForValue().get(RedisPrefix.MANAGER_ORDER_BALANCE + outTradeNo);
			TransactionRecord transactionRecord = JSONObject.parseObject(json, TransactionRecord.class);
			if (transactionRecord.getPayState().equals(1)) {
				return ResultGenerator.genSuccessResult();
			}
		}
		return ResultGenerator.genFailResult("fail");
	}

	@Override
	public Result updateExpiryDateNotiyf(String appmodelId) {
		Manager manager = new Manager();
		manager.setExpiryDateNotify(true);
		Condition condition = new Condition(Manager.class);
		condition.createCriteria().andEqualTo("appmodelId", appmodelId);
		if (tManagerMapper.updateByConditionSelective(manager, condition) > 0) {
			return ResultGenerator.genSuccessResult();
		}
		return ResultGenerator.genFailResult("确认失败");
	}
}
