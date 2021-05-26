package com.medusa.basemall.promotion.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.constant.ActivityType;
import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.promotion.dao.ActivityCouponMapper;
import com.medusa.basemall.promotion.dao.ActivityProductMapper;
import com.medusa.basemall.promotion.dao.CouponMapper;
import com.medusa.basemall.promotion.dao.CouponWxuserMapper;
import com.medusa.basemall.promotion.entity.ActivityCoupon;
import com.medusa.basemall.promotion.entity.ActivityProduct;
import com.medusa.basemall.promotion.entity.Coupon;
import com.medusa.basemall.promotion.entity.CouponWxuser;
import com.medusa.basemall.promotion.service.CouponWxuserService;
import com.medusa.basemall.promotion.vo.CouponWxuserVo;
import com.medusa.basemall.promotion.vo.ProductVo;
import com.medusa.basemall.utils.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author Created by psy on 2018/05/30.
 */
@Service
public class CouponWxuserServiceImpl extends AbstractService<CouponWxuser> implements CouponWxuserService {
	@Resource
	private CouponWxuserMapper couponWxuserMapper;

	@Resource
	private CouponMapper couponMapper;

	@Resource
	private ActivityCouponMapper activityCouponMapper;

	@Resource
	private ActivityProductMapper activityProductMapper;

	@Override
	public Result wxuserObtainCoupon(CouponWxuser couponWxuser) {

		CouponWxuser temp = new CouponWxuser();
		temp.setWxuserId(couponWxuser.getWxuserId());
		temp.setCouponId(couponWxuser.getCouponId());
		List<CouponWxuser> usercoupon = couponWxuserMapper.select(temp);

		Coupon c = couponMapper.selectByPrimaryKey(couponWxuser.getCouponId());
		int stock = c.getStockQuantity();

		if (usercoupon.size() < c.getLimitQuantity()) {
			if (c != null && stock > 0) {
				// 领取时间
				couponWxuser.setCreateTime(TimeUtil.getNowTime());
				couponWxuser.setFlag(0);
				couponWxuserMapper.insertSelective(couponWxuser);
				c.setObtainQuantity(c.getObtainQuantity() + 1);
				couponMapper.updateByPrimaryKeySelective(c);
				return ResultGenerator.genSuccessResult("领取成功");
			} else {
				return ResultGenerator.genFailResult("优惠券已领取完");
			}
		} else {
			return ResultGenerator.genFailResult("每人限领" + c.getLimitQuantity() + "张");
		}
	}

	@Override
	public List<CouponWxuser> findWxuserCoupons(CouponWxuserVo couponWxuserVo) {
		List<CouponWxuser> couponWxusers = new ArrayList<>();
		if (couponWxuserVo.getUseFlag() == 0) {
			couponWxusers = couponWxuserMapper.selectByWxuserIdNotUse(couponWxuserVo);
		} else {
			couponWxusers = couponWxuserMapper.selectByWxuserIdUsed(couponWxuserVo);
		}
		if (couponWxusers != null && couponWxusers.size() > 0) {
			couponWxusers.forEach(obj -> obj.getCoupon().setActivityCoupon(obj.getActivityCoupon()));
		}
		return couponWxusers;
	}

	@Override
	public List<CouponWxuser> findWxuserCouponCanUse(CouponWxuserVo couponWxuserVo) {
		List<ProductVo> productVos = JSONObject.parseArray(couponWxuserVo.getProductVos(), ProductVo.class);
		List<CouponWxuser> couponWxusers = couponWxuserMapper.selectByWxuserIdNotUse(couponWxuserVo);
		if (couponWxusers.size() == 0) {
			return new ArrayList<>();
		}
		List<CouponWxuser> coupons = new ArrayList<>();
		couponWxusers = couponWxusers.stream()
				.filter(obj -> obj.getActivityCoupon() != null && obj.getActivityCoupon().getDeleteState()
						.equals(false)).collect(Collectors.toList());
		for (CouponWxuser couponWxuser : couponWxusers) {
			// 查询用户领取的对应的优惠券
			Coupon coupon = couponWxuser.getCoupon();
			ActivityCoupon activityCoupon = couponWxuser.getActivityCoupon();
			// 优惠券来源为积分商城且类型为无门槛都可以使用
			if (coupon.getSourceType() == 2) {
				if (coupon.getCouponType() == 3 || coupon.getCouponType() == 4) {
					coupons.add(couponWxuser);
				}
				if (coupon.getCouponType() == 1 || coupon.getCouponType() == 2) {
					if (couponWxuserVo.getAllPrice() >= coupon.getMaxPrice()) {
						coupons.add(couponWxuser);
					}
				}
			}
			// 优惠券来源为活动且类型为无门槛都可以使用
			if (coupon.getSourceType() == 1) {
				if (coupon.getCouponType() == 3 || coupon.getCouponType() == 4) {
					if (activityCoupon.getFullState() == true) {
						coupons.add(couponWxuser);
					} else {
						Map<String, Integer> map = new HashMap<>();
						map.put("activityId", coupon.getActivityCouponId());
						map.put("activityType", ActivityType.COUPON);
						List<ActivityProduct> activityProducts = activityProductMapper.selectWhetherDelete(map);
						if (activityProducts.size() > 0) {
							for (ActivityProduct activityProduct : activityProducts) {
								for (ProductVo productVo : productVos) {
									if (productVo.getProductId().equals(activityProduct.getProductId())) {
										coupons.add(couponWxuser);
									}
								}
							}
						}
					}
				}
				// 优惠券来源为活动且类型为满减满折都可以使用
				if (coupon.getCouponType() == 1 || coupon.getCouponType() == 2) {
					if (activityCoupon.getFullState() == true) {
						if (couponWxuserVo.getAllPrice() >= coupon.getMaxPrice()) {
							coupons.add(couponWxuser);
						}
					} else {
						Double all = 0D;
						Map<String, Integer> map = new HashMap<>();
						map.put("activityId", coupon.getActivityCouponId());
						map.put("activityType", 1001);
						List<ActivityProduct> activityProducts = activityProductMapper.selectWhetherDelete(map);
						if (activityProducts.size() > 0) {
							for (ActivityProduct activityProduct : activityProducts) {
								for (ProductVo productVo : productVos) {
									if (productVo.getProductId().equals(activityProduct.getProductId())) {
										all += productVo.getAllPrice();
									}
								}
							}
							if (all >= coupon.getMaxPrice()) {
								coupons.add(couponWxuser);
							}
						}
					}
				}
			}
		}
		if (coupons != null && coupons.size() > 0) {
			coupons.forEach(obj -> obj.getCoupon().setActivityCoupon(obj.getActivityCoupon()));
		}
		return coupons;
	}

	@Override
	public List<CouponWxuser> findWxuserCoupon(Long wxuserId, Integer wxuserCouponId) {
		return couponWxuserMapper.findWxuserCoupon(wxuserId, wxuserCouponId);
	}
}
