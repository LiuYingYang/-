package com.medusa.basemall.order.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.constant.ActivityType;
import com.medusa.basemall.constant.ApplyState;
import com.medusa.basemall.constant.CouponType;
import com.medusa.basemall.constant.RefuseState;
import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.core.ServiceException;
import com.medusa.basemall.order.dao.OrderDetailMapper;
import com.medusa.basemall.order.entity.Order;
import com.medusa.basemall.order.entity.OrderDetail;
import com.medusa.basemall.order.service.OrderDetailService;
import com.medusa.basemall.order.service.OrderService;
import com.medusa.basemall.order.vo.ProductOrderVo;
import com.medusa.basemall.product.entity.ProductSpecItem;
import com.medusa.basemall.product.vo.JoinActiveInfo;
import com.medusa.basemall.promotion.entity.*;
import com.medusa.basemall.promotion.service.ActivityCouponService;
import com.medusa.basemall.promotion.service.ActivityProductService;
import com.medusa.basemall.promotion.service.CouponService;
import com.medusa.basemall.promotion.service.CouponWxuserService;
import com.medusa.basemall.utils.IdGenerateUtils;
import com.medusa.basemall.utils.TimeUtil;
import com.vip.vjtools.vjkit.time.ClockUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by medusa on 2018/06/02.
 * 需要事物时添加  @Transactional
 */

@Service

public class OrderDetailServiceImpl extends AbstractService<OrderDetail> implements OrderDetailService {
	@Resource
	private OrderDetailMapper orderDetailMapper;

	@Resource
	private OrderService orderService;

	@Resource
	private ActivityProductService activityProductService;

	@Resource
	private CouponWxuserService couponWxuserService;

	@Resource
	private CouponService couponService;
	@Resource
	private ActivityCouponService activityCouponService;


	@Override
	public List<OrderDetail> selectByOrderId(Long orderId) {
		return orderDetailMapper.selectByOrderId(orderId);
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrderDetail(Order order, List<ProductOrderVo> productList) {
		Map<Long, Double> productPreferential = new HashMap<>(productList.size());
		//判断是否使用优惠券(计算使用优惠券的优惠价格)
		ActivityCoupon activityCoupon = null;
		if (order.getWxuserCouponId() != null && order.getWxuserCouponId() > 0) {
			//查询用户对应的活动优惠券(取其中一张)
			CouponWxuser couponWxuser = couponWxuserService.findById(order.getWxuserCouponId());
			Coupon coupon = couponService.findById(couponWxuser.getCouponId());
			//如果活动优惠券,只计算可使用该优惠券的商品
			if (couponWxuser.getActivityCouponId() != null && couponWxuser.getActivityCouponId() > 0) {
				activityCoupon = activityCouponService.findById(couponWxuser.getActivityCouponId());
			}
			//判断是全场优惠券还是指定商品优惠券
			if (activityCoupon != null) {
				if (activityCoupon.getFullState().equals(Boolean.FALSE)) {
					StringBuilder builder = new StringBuilder();
					for (ProductOrderVo productOrderVo : productList) {
						builder.append(productOrderVo.getProductId() + ",");
					}
					Condition condition = new Condition(ActivityProduct.class);
					condition.createCriteria().andEqualTo("activityId", couponWxuser.getActivityCouponId())
							.andEqualTo("activityType", ActivityType.COUPON)
							.andIn("productId", Arrays.asList(builder.toString().split(",")));
					//查询该优惠券支持的商品
					List<ActivityProduct> activityProduct = activityProductService.findByCondition(condition);
					List<ProductOrderVo> temp = new ArrayList<>();
					for (ProductOrderVo productOrderVo : productList) {
						for (ActivityProduct product : activityProduct) {
							if (productOrderVo.getProductId().equals(product.getProductId())) {
								temp.add(productOrderVo);
							}
						}
					}
					productPreferential = computerCouponPreferential(temp, coupon);
				} else {
					productPreferential = computerCouponPreferential(productList, coupon);
				}
			}
		}

		for (ProductOrderVo product : productList) {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrderDetailId(IdGenerateUtils.getItemId());
			orderDetail.setOrderId(order.getOrderId());
			orderDetail.setAppmodelId(order.getAppmodelId());
			orderDetail.setUpdateTime(TimeUtil.getNowTime());
			orderDetail.setProductId(product.getProductId());
			orderDetail.setProductImg(product.getProductImg());
			orderDetail.setProductName(product.getProductName());
			orderDetail.setQuantity(product.getBuyQuantity());
			orderDetail.setPrice(new BigDecimal(product.getBuyPrice()));
			ProductSpecItem productSpecItem = JSON.parseObject(product.getProductSpecItemInfo(), ProductSpecItem.class);
			if (null != productSpecItem && null != productSpecItem.getProductSpecItemId()) {
				orderDetail.setProductSpecInfo(product.getProductSpecItemInfo());
			}
			orderDetail.setApplyState(ApplyState.REGULAR);
			orderDetail.setDeleteState(false);
			orderDetail.setNumber(0);
			orderDetail.setRefuseState(RefuseState.NORMAL_ORDER);
			orderDetail.setPreferential(new BigDecimal(0));
			if (productPreferential.get(product.getProductId()) != null
					&& productPreferential.get(product.getProductId()) > 0) {
				orderDetail.setPreferential(new BigDecimal(productPreferential.get(product.getProductId())));
			}
			if (order.getMemberDiscount() != null && order.getMemberDiscount() > 1 && order.getMemberDiscount() < 10) {
				BigDecimal memberDiscount = orderDetail.getPrice()
						.subtract(new BigDecimal(order.getMemberDiscount() / 10).multiply(orderDetail.getPrice()))
						.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(product.getBuyQuantity()));
				orderDetail.setPreferential(orderDetail.getPreferential().add(memberDiscount));
			}
			//如果是活动商品,添加活动信息(秒杀/特价/拼团)
			if (product.getJoinActiveInfo() != null) {
				Map<String, Object> map = null;
				JoinActiveInfo joinActiveInfo = product.getJoinActiveInfo();
				if (joinActiveInfo.getActiveInfo() != null && !joinActiveInfo.getActiveInfo().equals("")) {
					JSONObject jsonObject = JSON.parseObject(joinActiveInfo.getActiveInfo());
					//存储商品下单时涉及的活动信息,活动商品信息,特价,秒杀,拼团
					Long activityProductId = jsonObject.getLong("activityProductId");
					Integer activityType = jsonObject.getInteger("activityType");
					Integer activityId = jsonObject.getInteger("activityId");
					map = activityProductService
							.findProductActivityForOrder(activityType, activityProductId, activityId);
					ActivityProduct activityProduct = (ActivityProduct) map.get("ActivityProductInfo");
					order.setActivityId(activityId);
					switch (activityProduct.getActivityType()) {
						case 2001:
							break;
						case 3001:
							break;
						case 4001:
							ActivitySeckill activitySeckill = (ActivitySeckill) map.get("ActivitySeckillInfo");
							//如果活动结束不可购买
							if (ClockUtil.currentTimeMillis() - TimeUtil.str2Timestamp(activitySeckill.getEndDate())
									> 0) {
								throw new ServiceException("活动已结束");
							}
							//如果使用了优惠券需要判断优惠券是否可叠加使用,活动是否可使用优惠券
							if (activitySeckill.getOverlayState().equals(false) && activityCoupon != null) {
								if (activityCoupon.getOverlayState().equals(false)) {
									throw new ServiceException("优惠券活动与拼团活动不可叠加");
								}
							}
							if (!orderService.findIfBuyMax(order.getWxuserId(), activitySeckill.getActivitySeckillId(),
									ActivityType.SECONDKILL, orderDetail.getProductId(),
									activityProduct.getMaxQuantity())) {
								throw new ServiceException("商品已达限购数量");
							}
							break;
						default:
							break;
					}


					orderDetail.setActivityInfo(JSON.toJSONString(map));
				}
				if (activityCoupon != null && joinActiveInfo.getCouponInfo() != null && !joinActiveInfo.getCouponInfo()
						.equals("")) {
					if (orderDetail.getActivityInfo() != null && orderDetail.getActivityInfo().length() > 0) {
						JSONObject activityInfo = JSON.parseObject(orderDetail.getActivityInfo());
						activityInfo.put("ActivityCouponInfo", JSON.toJSONString(activityCoupon));
						orderDetail.setActivityInfo(activityInfo.toJSONString());
					} else {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("ActivityCouponInfo", JSON.toJSONString(activityCoupon));
						orderDetail.setActivityInfo(jsonObject.toJSONString());
					}
				}
			} orderDetailMapper.insert(orderDetail);
		} if (order.getActivityId() != null && order.getActivityId() > 0) {
			Order o = new Order();
			o.setOrderId(order.getOrderId());
			o.setActivityId(order.getActivityId());
			orderService.update(o);
		}
	}


	@Override
	public int findCountSize(Long orderId) {
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setOrderId(orderId);
		List<OrderDetail> select = orderDetailMapper.select(orderDetail);
		AtomicInteger sum = new AtomicInteger(0);
		select.forEach(obj -> {
			sum.addAndGet(obj.getQuantity());
		});
		return sum.get();
	}

	private Map<Long, Double> computerCouponPreferential(List<ProductOrderVo> activityProduct, Coupon coupon) {
		//根据不同类型的优惠券(1---满减，2---满折，3--无门槛现金券，4---无门槛折扣）
		Map<Long, Double> productPreferential = new HashMap<>(activityProduct.size());
		Integer couponType = coupon.getCouponType();

		BigDecimal totlePrice = new BigDecimal(0.0);
		Integer sum = 0;
		for (ProductOrderVo productOrderVo : activityProduct) {
			totlePrice = totlePrice.add(new BigDecimal(productOrderVo.getBuyPrice()));
			sum += productOrderVo.getBuyQuantity();
		}
		BigDecimal preferentialPrice = null;
		//2---满折, 4---无门槛折扣  计算折扣 商品总价 - (总价*折扣)
		if (couponType.equals(CouponType.FULL_DISCOUNT_COUPON) || couponType.equals(CouponType.DISCOUNT_COUPON)) {
			preferentialPrice = totlePrice.subtract(totlePrice.multiply(new BigDecimal(coupon.getDiscount() / 10)));
		}
		//1---满减   3--无门槛现金券
		if (couponType.equals(CouponType.FULL_REDUCE_COUPON) || couponType.equals(CouponType.READY_MONEY_COUPON)) {
			preferentialPrice = new BigDecimal(coupon.getMinPrice());
		}
		for (ProductOrderVo product : activityProduct) {
			BigDecimal preferential = new BigDecimal(product.getBuyPrice() * product.getBuyQuantity())
					.divide(totlePrice, 2, BigDecimal.ROUND_HALF_UP).multiply(preferentialPrice)
					.setScale(2, BigDecimal.ROUND_HALF_UP);
			productPreferential.put(product.getProductId(), preferential.doubleValue());
		}
		return productPreferential;
	}

}
