package com.medusa.basemall.promotion.serviceimpl;

import com.medusa.basemall.constant.ActivityType;
import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.promotion.dao.ActivityCouponMapper;
import com.medusa.basemall.promotion.dao.ActivityProductMapper;
import com.medusa.basemall.promotion.dao.CouponMapper;
import com.medusa.basemall.promotion.dao.CouponWxuserMapper;
import com.medusa.basemall.promotion.entity.ActivityCoupon;
import com.medusa.basemall.promotion.entity.ActivityProduct;
import com.medusa.basemall.promotion.entity.Coupon;
import com.medusa.basemall.promotion.service.CouponService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Created by psy on 2018/05/30.
 */
@Service
public class CouponServiceImpl extends AbstractService<Coupon> implements CouponService {
	@Resource
	private CouponMapper tCouponMapper;

	@Resource
	private ActivityCouponMapper activityCouponMapper;

	@Resource
	private ActivityProductMapper activityProductMapper;

	@Resource
	private CouponWxuserMapper couponWxuserMapper;

	@Override
	public List<Coupon> findByAppmodelId(String appmodelId, Long wxuserId) {
		ActivityCoupon activityCoupons = activityCouponMapper.selectStart(appmodelId);
		if(activityCoupons != null){
			List<Coupon> couponsNew = tCouponMapper.selectByActivityCouponId(activityCoupons.getActivityCouponId());
			couponsNew.forEach(obj->obj.setReceiveOrNot(checkReceive(obj, wxuserId)));
			return couponsNew;
		}
		return new ArrayList<>(0);
	}

	@Override
	public List<Coupon> findCouponByProductIds(String productIds, String appmodelId, Long wxuserId) {
		List<ActivityCoupon> activityCoupons = activityCouponMapper.selectStartAndFullAll(appmodelId);
		List<Coupon> coupons = new ArrayList<>();
		if (activityCoupons.size() > 0) {
			for (ActivityCoupon activityCoupon : activityCoupons) {
				List<Coupon> couponsNew = tCouponMapper.selectByActivityCouponId(activityCoupon.getActivityCouponId());
				coupons.addAll(couponsNew);
			}
		} else {
			String[] productId = productIds.split(",");
			for (int i = 0; i < productId.length; i++) {
				Map<String, Object> map = new HashMap<>();
				map.put("productId", Long.valueOf(productId[i]));
				map.put("activityType", ActivityType.COUPON);
				List<ActivityProduct> activityProducts = activityProductMapper.selectActivityProduct(map);
				if (activityProducts.size() > 0) {
					for (ActivityProduct activityProduct : activityProducts) {
						ActivityCoupon activityCoupon = activityCouponMapper
								.selectByPrimaryKey(activityProduct.getActivityId());
						if (activityCoupon.getNowState() == 2 && activityCoupon.getDeleteState() == false) {
							List<Coupon> couponList = tCouponMapper
									.selectByActivityCouponId(activityCoupon.getActivityCouponId());
							coupons.addAll(couponList);
						}
					}
				}
			}
		}
		for (Coupon coupon : coupons) {
			coupon.setReceiveOrNot(checkReceive(coupon, wxuserId));
		}
		return coupons;
	}

	@Override
	public int deleteByActivityCouponId(String activityCouponIds) {
		String[] ids = activityCouponIds.split(",");
		return tCouponMapper.deleteByActivityCouponId(ids);
	}

	private boolean checkReceive(Coupon coupon, Long wxuserId) {
		Map<String, Object> map = new HashMap<>();
		map.put("wxuserId", wxuserId);
		map.put("couponId", coupon.getCouponId());
		Integer result = couponWxuserMapper.selectByCouponId(map);
		if (coupon.getLimitQuantity().equals(result)) {
			return true;
		}
		return false;
	}
}
