package com.medusa.basemall.integral.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medusa.basemall.configurer.WxServiceUtils;
import com.medusa.basemall.constant.*;
import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.core.ServiceException;
import com.medusa.basemall.integral.dao.PrizeDetailMapper;
import com.medusa.basemall.integral.dao.PrizeMapper;
import com.medusa.basemall.integral.dao.PrizeOrderMapper;
import com.medusa.basemall.integral.entity.Prize;
import com.medusa.basemall.integral.entity.PrizeDetail;
import com.medusa.basemall.integral.entity.PrizeOrder;
import com.medusa.basemall.integral.service.PrizeOrderService;
import com.medusa.basemall.integral.vo.PrizeOrderListVO;
import com.medusa.basemall.integral.vo.PrizeOrderVo;
import com.medusa.basemall.integral.vo.SavePrizeOrderVO;
import com.medusa.basemall.promotion.dao.CouponWxuserMapper;
import com.medusa.basemall.promotion.entity.Coupon;
import com.medusa.basemall.promotion.entity.CouponWxuser;
import com.medusa.basemall.promotion.service.CouponService;
import com.medusa.basemall.user.dao.WxuserMapper;
import com.medusa.basemall.user.entity.Member;
import com.medusa.basemall.user.entity.Wxuser;
import com.medusa.basemall.user.service.MemberService;
import com.medusa.basemall.utils.IdGenerateUtils;
import com.medusa.basemall.utils.TimeUtil;
import com.vip.vjtools.vjkit.mapper.BeanMapper;
import com.vip.vjtools.vjkit.time.ClockUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Created by wx on 2018/06/06.
 */
@Service

public class PrizeOrderServiceImpl extends AbstractService<PrizeOrder> implements PrizeOrderService {

	@Resource
	private PrizeOrderMapper tPrizeOrderMapper;
	@Resource
	private WxServiceUtils wxServiceUtils;
	@Resource
	private WxuserMapper tWxuserMapper;
	@Resource
	private PrizeMapper prizeMapper;
	@Resource
	private CouponService couponService;
	@Resource
	private MemberService memberService;
	@Resource
	private PrizeDetailMapper prizeDetailMapper;
	@Resource
	private CouponWxuserMapper couponWxuserMapper;
	@Resource
	private RedisTemplate redisTemplate;

	private static Logger LOGGER = LoggerFactory.getLogger(PrizeOrderServiceImpl.class);

	@Override
	public List<PrizeOrderListVO> findByAppmodelId(PrizeOrderVo prizeOrderVo) {
		List<PrizeOrderListVO> prizeOrderListVOS = tPrizeOrderMapper.findByAppmodelId(prizeOrderVo);
		if (prizeOrderListVOS != null && prizeOrderListVOS.size() > 0) {
			for (PrizeOrderListVO prizeOrderListVO : prizeOrderListVOS) {
				if (prizeOrderVo.getOrderType().equals(2)) {
					Coupon coupon = couponService.findById(prizeOrderListVO.getPrize().getCouponId());
					prizeOrderListVO.getPrize().setCoupon(coupon);
				}
				if (prizeOrderListVO.getOrderState().equals(2)
						&& ClockUtil.currentTimeMillis() - TimeUtil.str2Timestamp(prizeOrderListVO.getSendTime())
						> TimeType.SEVENDAY) {
					prizeOrderListVO.setOrderState(3);
					tPrizeOrderMapper.updateByPrimaryKey(prizeOrderListVO);
				}
			}
		}

		return prizeOrderListVOS;
	}

	@Override
	public String notify(String xmlResult) throws Exception {
		LOGGER.info("微信回调XmlResult --------->" + xmlResult);
		WxPayOrderNotifyResult payOrderNotifyResult = WxPayOrderNotifyResult.fromXML(xmlResult);

		if (PayString.SUCCESS.equalsIgnoreCase(payOrderNotifyResult.getResultCode())) {
			String jsondata = (String) redisTemplate.opsForValue()
					.get(RedisPrefix.INTEGRAL_ORDER + payOrderNotifyResult.getOutTradeNo());
			PrizeOrder prizeOrder = JSON.parseObject(jsondata, PrizeOrder.class);
			if (prizeOrder.getOrderState().equals(PayFlag.WAIT_PAY)) {
				tPrizeOrderMapper.insert(prizeOrder);
				Member member = memberService.findMenberInfo(prizeOrder.getWxuserId(), prizeOrder.getAppmodelId());
				paySuccess(prizeOrder, member);
				redisTemplate.delete(RedisPrefix.INTEGRAL_ORDER + payOrderNotifyResult.getOutTradeNo());
				return WxPayNotifyResponse.success("处理成功");
			}
			return WxPayNotifyResponse.fail("处理失败");
		}
		return WxPayNotifyResponse.fail("订单回调处理失败");
	}

	private void paySuccess(PrizeOrder prizeOrder, Member member) {
		prizeOrder.setOrderState(PayFlag.PAY_OK);
		prizeOrder.setPayTime(TimeUtil.getNowTime());
		prizeOrder.setUpdateTime(TimeUtil.getNowTime());
		tPrizeOrderMapper.updateByPrimaryKey(prizeOrder);
		// 处理库存状态
		Prize prize = prizeMapper.selectByPrimaryKey(prizeOrder.getPrizeId());
		prize.setPrizeStock(prize.getPrizeStock() - 1);
		if (prize.getPrizeStock() - 1 <= 0) {
			prize.setState(3);
		}
		prize.setSalesVolume(prize.getSalesVolume() + 1);
		prize.setAllIntegral(prize.getAllIntegral() + prizeOrder.getExpendIntegral());
		prizeMapper.updateByPrimaryKey(prize);
		// 生成兑换记录
		PrizeDetail prizeDetail = new PrizeDetail();
		prizeDetail.setType(1);
		prizeDetail.setCreateTime(TimeUtil.getNowTime());
		prizeDetail.setIntegral(prizeOrder.getExpendIntegral());
		prizeDetail.setWxuserId(prizeOrder.getWxuserId());
		prizeDetail.setPrizeId(prizeOrder.getPrizeId());
		prizeDetail.setAppmodelId(prizeOrder.getAppmodelId());
		prizeDetail.setDeleteState(false);
		prizeDetailMapper.insert(prizeDetail);
		// 扣除积分
		member.setIntegralTotal(member.getIntegralTotal() - prizeOrder.getExpendIntegral());
		memberService.update(member);
	}


	/**
	 *  生成优惠券订单
	 * @param savePrizeOrderVO
	 */
	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Result saveCouponOrder(SavePrizeOrderVO savePrizeOrderVO) {
		Prize prize = prizeMapper.selectByPrimaryKey(savePrizeOrderVO.getPrizeId());
		Coupon coupon = couponService.findById(prize.getCouponId());
		Member member = memberService.findMenberInfo(savePrizeOrderVO.getWxuserId(), savePrizeOrderVO.getAppmodelId());
		if (coupon.getStockQuantity() > 0) {
			if (member.getIntegralTotal() >= savePrizeOrderVO.getExpendIntegral()) {
				// 生成订单
				PrizeOrder prizeOrder = BeanMapper.map(savePrizeOrderVO, PrizeOrder.class);
				String changeNum = IdGenerateUtils.getOrderNum();
				prizeOrder.setPrizeOrderId(IdGenerateUtils.getItemId());
				prizeOrder.setChangeNum(changeNum);
				prizeOrder.setCreateTime(TimeUtil.getNowTime());
				prizeOrder.setPayTime(TimeUtil.getNowTime());
				prizeOrder.setOrderState(3);
				int result = tPrizeOrderMapper.insert(prizeOrder);
				if (result > 0) {
					//处理状态，库存
					coupon.setStockQuantity(coupon.getStockQuantity() - 1);
					coupon.setObtainQuantity(coupon.getObtainQuantity() + 1);
					prize.setPrizeStock(prize.getPrizeStock() - 1);
					prize.setSalesVolume(prize.getSalesVolume() + 1);
					if (prize.getPrizeStock() <= 0) {
						prize.setState(3);
					}
					prizeMapper.updateByPrimaryKey(prize);
					couponService.update(coupon);
					// 生成兑换记录
					PrizeDetail prizeDetail = new PrizeDetail();
					prizeDetail.setType(1);
					prizeDetail.setCreateTime(TimeUtil.getNowTime());
					prizeDetail.setIntegral(prizeOrder.getExpendIntegral());
					prizeDetail.setWxuserId(prizeOrder.getWxuserId());
					prizeDetail.setPrizeId(prizeOrder.getPrizeId());
					prizeDetail.setAppmodelId(prizeOrder.getAppmodelId());
					prizeDetail.setDeleteState(false);
					prizeDetailMapper.insert(prizeDetail);
					// 为用户生成优惠券
					CouponWxuser couponWxuser = new CouponWxuser();
					couponWxuser.setWxuserId(prizeOrder.getWxuserId());
					couponWxuser.setCouponId(prize.getCouponId());
					couponWxuser.setFlag(0);
					couponWxuser.setCreateTime(TimeUtil.getNowTime());
					couponWxuser.setAppmodelId(prizeOrder.getAppmodelId());
					couponWxuserMapper.insert(couponWxuser);
					// 扣除积分
					member.setIntegralTotal(member.getIntegralTotal() - prizeOrder.getExpendIntegral());
					memberService.update(member);
					return ResultGenerator.genSuccessResult("兑换成功");
				}
				throw new ServiceException("兑换失败");
			}
			throw new ServiceException("积分不足");
		}
		throw new ServiceException("库存不足");
	}

	@Override
	@Transactional(rollbackFor = ServiceException.class)
	public Result saveProductOrder(SavePrizeOrderVO savePrizeOrderVO) {
		Wxuser wxuser = tWxuserMapper.selectByPrimaryKey(savePrizeOrderVO.getWxuserId());
		Prize prize = prizeMapper.selectByPrimaryKey(savePrizeOrderVO.getPrizeId());
		Member member = memberService.findMenberInfo(savePrizeOrderVO.getWxuserId(), savePrizeOrderVO.getAppmodelId());
		if (prize.getPrizeStock() > 0) {
			if (member.getIntegralTotal() >= savePrizeOrderVO.getExpendIntegral()) {
				PrizeOrder prizeOrder = BeanMapper.map(savePrizeOrderVO, PrizeOrder.class);
				// 生成订单
				prizeOrder.setPrizeOrderId(IdGenerateUtils.getItemId());
				prizeOrder.setChangeNum(IdGenerateUtils.getOrderNum());
				prizeOrder.setCreateTime(TimeUtil.getNowTime());
				prizeOrder.setOrderState(0);
				prizeOrder.setPayType(savePrizeOrderVO.getType());
				if (savePrizeOrderVO.getType().equals(1)) {
					Result result = payOrder(prizeOrder, prizeOrder.getWlPrice().toString(), wxuser, Url.PRICZE_NOTIFY);
					redisTemplate.opsForValue()
							.set(RedisPrefix.INTEGRAL_ORDER + prizeOrder.getChangeNum(), JSON.toJSONString(prizeOrder));
					return result;
				}
				if (savePrizeOrderVO.getType().equals(2)) {
					if (0 > member.getSupplyBonus() - savePrizeOrderVO.getWlPrice()) {
						throw new ServiceException("余额不足");
					}
					member.setSupplyBonus(member.getSupplyBonus() - savePrizeOrderVO.getWlPrice());
					memberService.update(member);
					tPrizeOrderMapper.insert(prizeOrder);
					paySuccess(prizeOrder, member);
					return ResultGenerator.genSuccessResult();
				}
				throw new ServiceException("兑换失败");
			}
			throw new ServiceException("积分不足");
		}
		throw new ServiceException("库存不足");
	}

	@Override
	public PageInfo selectPrizeOrder(Integer pageNum, Integer pageSize, String appmodelId) {
		PageHelper.startPage(pageNum, pageSize);
		List<PrizeOrder> prizeOrders = tPrizeOrderMapper.selectPrizeOrder(appmodelId);
		PageInfo pageInfo = new PageInfo(prizeOrders);
		return pageInfo;
	}

	/**
	 * 支付订单
	 *
	 * @param prizeOrder
	 * @param payFee
	 * @param wxuser
	 * @param notify
	 * @return
	 */
	private Result payOrder(PrizeOrder prizeOrder, String payFee, Wxuser wxuser, String notify) {
		try {
			Map<String, String> resultMap = wxServiceUtils
					.wxJsapiPayRequest("积分商城下单", prizeOrder.getChangeNum(), payFee, wxuser.getOpenId(), notify,
							wxuser.getAppmodelId());
			if (resultMap.get(PayString.RETURNCODE).equalsIgnoreCase(PayString.SUCCESS)) {
				resultMap.put("prizeOrderId", prizeOrder.getPrizeOrderId() + "");
			}
			return ResultGenerator.genSuccessResult(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("下单失败");
		}
	}
}
