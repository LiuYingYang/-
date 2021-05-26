package com.medusa.basemall.integral.serviceimpl;

import com.medusa.basemall.constant.PrizeState;
import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.core.ServiceException;
import com.medusa.basemall.integral.dao.PrizeMapper;
import com.medusa.basemall.integral.entity.Prize;
import com.medusa.basemall.integral.service.PrizeService;
import com.medusa.basemall.integral.vo.PrizeParamVO;
import com.medusa.basemall.integral.vo.PrizeVo;
import com.medusa.basemall.promotion.entity.Coupon;
import com.medusa.basemall.promotion.service.CouponService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author Created by wx on 2018/06/06.
 */
@Service
public class PrizeServiceImpl extends AbstractService<Prize> implements PrizeService {

	@Resource
	private PrizeMapper tPrizeMapper;
	@Resource
	private CouponService couponService;


	@Override
	public List<Prize> findByState(PrizeVo prizeVo) {
		if (prizeVo.getSearchName() != null) {
			StringBuilder stringBuilder = new StringBuilder("%");
			for (char c : prizeVo.getSearchName().toCharArray()) {
				stringBuilder.append(c + "%");
			}
			prizeVo.setSearchName(stringBuilder.toString());
		}
		List<Prize> prizes = tPrizeMapper.findByState(prizeVo);
		if (prizes.size() > 0) {
			for (Prize prize : prizes) {
				if (prize.getPrizeType() == 2) {
					Coupon coupon = couponService.findById(prize.getCouponId());
					prize.setCoupon(coupon);
				}
			}
		}
		return prizes;
	}

	@Override
	public Prize findByCouponId(Integer couponId) {
		return tPrizeMapper.findByCouponId(couponId);
	}

	@Override
	public Prize findByAppmodelIdAndPrizeId(String appmodelId, Integer prizeId) {
		Prize prize = new Prize();
		prize.setPrizeId(prizeId);
		prize.setAppmodelId(appmodelId);
		prize = tPrizeMapper.selectOne(prize);
		if (prize.getPrizeType() == 2) {
			Coupon coupon = couponService.findById(prize.getCouponId());
			prize.setCoupon(coupon);
		}
		return prize;
	}

	@Override
	public int updateCouponOrproduct(PrizeParamVO prizeParamVO) {
		Prize prize = tPrizeMapper.selectByPrimaryKey(prizeParamVO.getPrizeId());
		if (prize == null) {
			throw new ServiceException("积分商品为空");
		}
		int i = 0;
		if (prize.getPrizeStock() != null && prize.getPrizeStock().equals(0)) {
			prize.setState(PrizeState.SELLOUT);
		}
		if (prize.getPrizeType().equals(1)) {
			if (StringUtils.isNotBlank(prizeParamVO.getPrizeName())) {
				prize.setPrizeName(prizeParamVO.getPrizeName());
			}
			if (prizeParamVO.getStock() != null) {
				prize.setPrizeStock(prizeParamVO.getStock());
			}
			if (prizeParamVO.getConvertPrice() != null) {
				prize.setConvertPrice(prizeParamVO.getConvertPrice());
			}
			i = tPrizeMapper.updateByPrimaryKey(prize);
		}
		if (prize.getPrizeType().equals(2)) {
			Coupon coupon = couponService.findById(prize.getCouponId());
			if (prizeParamVO.getStock() != null) {
				coupon.setStockQuantity(prizeParamVO.getStock());
			}
			if (prizeParamVO.getConvertPrice() != null) {
				coupon.setConvertPrice(prizeParamVO.getConvertPrice());
			}
			i = couponService.update(coupon);
		}
		return i;
	}

}
