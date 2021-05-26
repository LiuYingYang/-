package com.medusa.basemall.promotion.common;

import com.medusa.basemall.constant.ActivityType;
import com.medusa.basemall.integral.vo.FindProductVo;
import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.product.entity.ProductSpecItem;
import com.medusa.basemall.product.service.ProductService;
import com.medusa.basemall.product.service.ProductSpecItemService;
import com.medusa.basemall.promotion.entity.*;
import com.medusa.basemall.promotion.service.*;
import com.medusa.basemall.utils.TimeUtil;
import com.vip.vjtools.vjkit.time.DateFormatUtil;
import com.vip.vjtools.vjkit.time.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Created by wx on 2018/08/25.
 */
@Component
public class ActivityUtils {

	@Autowired
	private ActivitySeckillService activitySeckillService;

	@Autowired
	private ActivityGroupService activityGroupService;

	@Autowired
	private ActivitySpecialService activitySpecialService;
	@Autowired
	private ActivityProductService activityProductService;

	@Autowired
	private ProductService productService;
	@Resource
	private ActivityProductStockService activityProductStockService;
	@Autowired
	private ProductSpecItemService productSpecItemService;

	/**
	 * 移除活动标签
	 * @param productTypeList
	 * @param type
	 * @return
	 */
	public static String removeProductTypeList(String productTypeList, Integer type) {
		if (productTypeList != null && productTypeList.length() > 5) {
			return Arrays.stream(productTypeList.split(",")).filter(obj -> !obj.equals(type.toString()))
					.collect(Collectors.joining(","));
		} else {
			return "";
		}
	}

	/**
	 * 时间加/减一秒
	 * @param endDate
	 * @param type  1-加  2-减
	 * @return
	 */
	public static String setEndDate(String endDate, int type) {
		if(type == 1){
			return DateFormatUtil.DEFAULT_ON_SECOND_FORMAT
					.format(DateUtil.addSeconds(TimeUtil.str2Date(endDate), 1));
		}
		if(type == 2){
			return DateFormatUtil.DEFAULT_ON_SECOND_FORMAT
					.format(DateUtil.subSeconds(TimeUtil.str2Date(endDate), 1));
		}
		throw new RuntimeException("类型异常");
	}

	/**
	 * 添加活动标签
	 * @param productTypeList
	 * @param type
	 * @return
	 */
	public static String addProductTypeList(String productTypeList, Integer type) {
		if (productTypeList == null || "".equals(productTypeList)) {
			return type.toString();
		} else {
			if (productTypeList.contains(type.toString())) {
				return productTypeList;
			}
			return productTypeList + "," + type;
		}
	}

	/**
	 * 校验新创建的活动开始时间和结束时间是否冲突
	 * @param createStartTime
	 * @param createEndTime
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean verifyStartTimeAndEndTimeIfClash(Date createStartTime, Date createEndTime, String startDate,
			String endDate) {
		try {
			boolean start = DateUtil
					.isBetween(createStartTime, DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.parse(startDate),
							DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.parse(endDate));
			boolean end = DateUtil.isBetween(createEndTime, DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.parse(startDate),
					DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.parse(endDate));
			if (start == true || end == true) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void activityProductStockReturn(String activityId, String appmodelId) {
		//归还剩余库存
		List<ActivityProduct> activityProduct = activityProductService
				.findActivityProduct(activityId, ActivityType.SECONDKILL, appmodelId);
		List<ActivityProduct> notspec = new ArrayList<>();
		List<ActivityProduct> spec = new ArrayList<>();
		activityProduct.forEach(obj -> {
			if (obj.getSpecType().equals(false)) {
				spec.add(obj);
			} else {
				notspec.add(obj);
			}
		});
		//分别归还多规格库存以及单规格库存
		if (notspec.size() > 0) {
			String productIds = notspec.stream().map(obj -> obj.getProductId().toString())
					.collect(Collectors.joining(","));
			List<Product> products = productService.findByIds(productIds);
			Map<Long, ActivityProduct> map = notspec.stream()
					.collect(Collectors.toMap(ActivityProduct::getProductId, obj -> obj));
			for (Product product : products) {
				product.setStock(product.getStock() + map.get(product.getProductId()).getActivityStock());
				productService.update(product);
			}
		}
		if (spec.size() > 0) {
			//查询活动库存
			List<Long> activityProductIds = spec.stream().map(ActivityProduct::getActivityProductId)
					.collect(Collectors.toList());
			List<ActivityProductStock> activityProductStocks = activityProductStockService
					.findByActivityProductIds(activityProductIds);
			//删除活动商品多规格数据
			List<Integer> activityProductStockIds = activityProductStocks.stream().map(obj -> obj.getActivityProductStockId())
					.collect(Collectors.toList());
			activityProductStockService.updateDeleteState(activityProductStockIds,true);
			//查询商品库存
			String specItemIds = activityProductStocks.stream().map(obj -> obj.getProductSpecItemId().toString())
					.collect(Collectors.joining(","));
			List<ProductSpecItem> productSpecItems = productSpecItemService.findByIds(specItemIds);
			//根据商品id分组
			Map<Long, List<ProductSpecItem>> produtIdGroup = productSpecItems.stream()
					.collect(Collectors.groupingBy(ProductSpecItem::getProductId));
			//转为k-v
			Map<Long, ActivityProductStock> activityProductStockMap = activityProductStocks.stream()
					.collect(Collectors.toMap(ActivityProductStock::getProductSpecItemId, obj -> obj));
			for (List<ProductSpecItem> specItems : produtIdGroup.values()) {
				for (ProductSpecItem specItem : specItems) {
					int stock = specItem.getStock() + activityProductStockMap.get(specItem.getProductSpecItemId())
							.getActivityStock();
					specItem.setStock(stock);
					productSpecItemService.update(specItem);
				}
				int sum = specItems.stream().mapToInt(ProductSpecItem::getStock).sum();
				productService.addStock(specItems.get(0).getProductId(), sum);
			}
			//activityProductStockService.deleteByIds(Joiner.on(",").join(activityProductIds));
		}
	}

	public boolean checkCoupon(FindProductVo findProductVo) {
		/*try {
			if (findProductVo.getOverlayState() == false) {
				Map<String, Object> map = new HashMap<>();
				map.put("appmodelId", findProductVo.getAppmodelId());
				List<ActivityCoupon> activityCoupons = activityGroupService.selectSpecialTwo(map);
				if (activityCoupons.size() > 0) {
					for (ActivityCoupon activityCoupon : activityCoupons) {
						Date createStartDate = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT
								.parse(activityCoupon.getEndDate());
						Date createEndDate = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT
								.parse(activityCoupon.getStartDate());
						if (ActivityUtils.verifyStartTimeAndEndTimeIfClash(createStartDate, createEndDate,
								findProductVo.getEndDate(), findProductVo.getStartDate()) == true) {
							return false;
						}
					}

				}
			}
			if (findProductVo.getOverlayState() == true) {
				List<ActivityCoupon> activityCoupons = activityGroupService
						.selectSpecialOne(findProductVo.getAppmodelId());
				if (activityCoupons.size() > 0) {
					for (ActivityCoupon activityCoupon : activityCoupons) {
						Date createStartDate = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT
								.parse(activityCoupon.getEndDate());
						Date createEndDate = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT
								.parse(activityCoupon.getStartDate());
						if (ActivityUtils.verifyStartTimeAndEndTimeIfClash(createStartDate, createEndDate,
								findProductVo.getEndDate(), findProductVo.getStartDate()) == true) {
							return false;
						}
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		return true;
	}

	/**
	 * 查询创建的时间段内是否存在其他活动,如果存在其他活动则排除其他活动已经选中商品
	 * 活动就算是冲突也只会查出一个
	 * @param appmodelId
	 * @param activeType
	 */
	public List<Long> findRestsActivityConflicting(String appmodelId, Integer activeType, String startTime,
			String endTime) {
		List<Long> productIds = new ArrayList<>();
		//不查询指定活动
		if (!activeType.equals(ActivityType.SPECIAL)) {
			List<ActivitySpecial> activitySpecials = activitySpecialService
					.findConflictingSpecial(startTime, endTime, appmodelId);
			if (activitySpecials.size() > 0) {
				String activityId = activitySpecials.get(0).getActivitySpecialId().toString();
				List<ActivityProduct> activityProduct = activityProductService
						.findActivityProduct(activityId, ActivityType.SPECIAL, appmodelId);
				List<Long> collect = activityProduct.stream().map(obj -> obj.getProductId())
						.collect(Collectors.toList());
				productIds.addAll(collect);
			}
		}
		if (!activeType.equals(ActivityType.GROUP)) {
			List<ActivityGroup> activityGroups = activityGroupService
					.findConflictingGroups(startTime, endTime, appmodelId);
			if (activityGroups.size() > 0) {
				String activityId = activityGroups.get(0).getActivityGroupId().toString();
				List<ActivityProduct> activityProduct = activityProductService
						.findActivityProduct(activityId, ActivityType.GROUP, appmodelId);
				List<Long> collect = activityProduct.stream().map(obj -> obj.getProductId())
						.collect(Collectors.toList());
				productIds.addAll(collect);
			}
		}
		if (!activeType.equals(ActivityType.SECONDKILL)) {
			List<ActivitySeckill> conflictingSeckill = activitySeckillService
					.findConflictingSeckill(startTime, endTime, appmodelId);
			if (conflictingSeckill.size() > 0) {
				String activityId = conflictingSeckill.get(0).getActivitySeckillId().toString();
				List<ActivityProduct> activityProduct = activityProductService
						.findActivityProduct(activityId, ActivityType.GROUP, appmodelId);
				List<Long> collect = activityProduct.stream().map(obj -> obj.getProductId())
						.collect(Collectors.toList());
				productIds.addAll(collect);
			}
		}

		return productIds;
	}

}
