package com.medusa.basemall.order.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.constant.ActivityType;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.core.ServiceException;
import com.medusa.basemall.order.dao.BuycarRepository;
import com.medusa.basemall.order.entity.Buycar;
import com.medusa.basemall.order.service.BuycarService;
import com.medusa.basemall.order.vo.BuyCarsVo;
import com.medusa.basemall.order.vo.OrderSurveyVo;
import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.product.entity.ProductSpecItem;
import com.medusa.basemall.product.service.ProductService;
import com.medusa.basemall.product.service.ProductSpecItemService;
import com.medusa.basemall.product.vo.JoinActiveInfo;
import com.medusa.basemall.promotion.entity.ActivityCoupon;
import com.medusa.basemall.promotion.entity.ActivityProduct;
import com.medusa.basemall.promotion.entity.ActivityProductStock;
import com.medusa.basemall.promotion.service.ActivityCouponService;
import com.medusa.basemall.promotion.service.ActivityProductService;
import com.medusa.basemall.promotion.service.ActivityProductStockService;
import com.medusa.basemall.promotion.service.ActivitySeckillService;
import com.medusa.basemall.utils.TimeUtil;
import com.vip.vjtools.vjkit.time.DateFormatUtil;
import com.vip.vjtools.vjkit.time.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;


/**
 * @author whh
 */
@Service
public class BuycarServiceImpl implements BuycarService {

	@Autowired
	private BuycarRepository buycarRepository;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductSpecItemService specItemService;

	@Autowired
	private ActivitySeckillService activitySeckillService;

	@Autowired
	private ActivityProductService activityProductService;
	@Autowired
	private ActivityProductStockService activityProductStockService;

	@Autowired
	private ActivityCouponService activityCouponService;


	@Resource
	private MongoTemplate mongoTemplate;

	@Override
	public Buycar addProductToBuycar(Buycar newBuycar) {
		if (newBuycar == null) {
			throw new ServiceException("参数为空");
		}
		if (newBuycar.getJoinActiveInfo() != null && newBuycar.getJoinActiveInfo().getActiveInfo() != null) {
			JSONObject jsonObject = JSON.parseObject(newBuycar.getJoinActiveInfo().getActiveInfo());
			if (jsonObject != null && (jsonObject.getInteger("activityType").equals(ActivityType.GROUP) || jsonObject
					.getInteger("activityType").equals(ActivityType.SECONDKILL))) {
				throw new ServiceException("活动商品不能加入购物车");
			}
		}
		List<Buycar> oldBuys = buycarRepository
				.findByProductIdAndAppmodelIdAndWxuserId(newBuycar.getProductId(), newBuycar.getAppmodelId(),
						newBuycar.getWxuserId());
		//过滤规格相同
		if (newBuycar.getProductSpecItemInfo() != null && null != newBuycar.getProductSpecItemInfo()
				.getProductSpecItemId()) {
			//过滤有规格,相同的商品
			oldBuys = oldBuys.stream().filter(obj -> obj.getProductSpecItemInfo().getProductSpecItemId()
					.equals(newBuycar.getProductSpecItemInfo().getProductSpecItemId())).collect(Collectors.toList());
		} else {
			//过滤无规格相同的商品
			oldBuys = oldBuys.stream().filter(obj -> obj.getProductId().equals(newBuycar.getProductId()))
					.collect(Collectors.toList());
		}
		if (null != oldBuys && oldBuys.size() > 0) {
			int quantity = oldBuys.get(0).getQuantity();
			oldBuys.get(0).setQuantity(newBuycar.getQuantity() + quantity);
			oldBuys.get(0).setCountPrice(
					newBuycar.getProductSpecItemInfo().getPrice() * (newBuycar.getQuantity() + quantity));
			oldBuys.get(0).setCreateTime(TimeUtil.getNowTime());
			oldBuys.get(0).getProductSpecItemInfo().setSpecificationValue("");
			Buycar save = buycarRepository.save(oldBuys.get(0));
			return save;
		} else {
			newBuycar.setCreateTime(TimeUtil.getNowTime());
			newBuycar.setCountPrice(newBuycar.getQuantity() * newBuycar.getProductSpecItemInfo().getPrice());
			Buycar insert = buycarRepository.insert(newBuycar);
			return insert;
		}
	}

	@Override
	public Result findBuyCars(Long wxuserId, String appmodelId) {
		List<Buycar> list = buycarRepository.findByWxuserIdAndAppmodelId(wxuserId, appmodelId);
		list.sort(Comparator.comparing(Buycar::getCreateTime).reversed());
		List<Buycar> collect1 = list.stream().filter(obj -> obj.getShelfState() == 0).collect(Collectors.toList());
		List<Buycar> collect2 = list.stream().filter(obj -> obj.getShelfState() == 1 || obj.getShelfState() == 2)
				.collect(Collectors.toList());
		BuyCarsVo buyCarsVo = new BuyCarsVo();
		buyCarsVo.setNotAvailable(collect2);
		List<Buycar> buycars = new ArrayList<>();
		if (collect1.size() > 0) {
			//单规格的
			List<Buycar> only = collect1.stream()
					.filter(obj -> obj.getProductSpecItemInfo().getProductSpecItemId() == null
							|| obj.getProductSpecItemInfo().getProductSpecItemId() == 0).collect(Collectors.toList());
			if (only != null && only.size() > 0) {
				String productIds = String.join(",",
						only.stream().map(obj -> obj.getProductId().toString()).collect(Collectors.toList()));
				List<Product> byIds = productService.findByIds(productIds);
				Map<Long, Product> productMap = byIds.stream()
						.collect(Collectors.toMap(obj -> obj.getProductId(), obj -> obj));
				for (Buycar buycar : only) {
					Product product = productMap.get(buycar.getProductId());
					updateBuyCar(buycar, product.getStock());
				}
				buycars.addAll(only.stream().filter(obj -> obj.getQuantity() > 0).collect(Collectors.toList()));
				collect2.addAll(only.stream().filter(obj -> obj.getQuantity() == 0).collect(Collectors.toList()));
			}
			//多规格
			List<Buycar> more = collect1.stream()
					.filter(obj -> obj.getProductSpecItemInfo().getProductSpecItemId() != null
							&& obj.getProductSpecItemInfo().getProductSpecItemId() > 0).collect(Collectors.toList());
			//区分多规格中的特价商品
			if (more != null && more.size() > 0) {
				List<ProductSpecItem> productSpecItems = more.stream().map(obj -> obj.getProductSpecItemInfo())
						.collect(Collectors.toList());
				String productSpecItemIds = String.join(",",
						productSpecItems.stream().map(obj -> obj.getProductSpecItemId().toString())
								.collect(Collectors.toList()));
				List<ProductSpecItem> productSpecItemList = specItemService.findByIds(productSpecItemIds);
				Map<Long, ProductSpecItem> productSpecItemMap = productSpecItemList.stream()
						.collect(Collectors.toMap(obj -> obj.getProductSpecItemId(), obj -> obj));
				for (Buycar buycar : more) {
					ProductSpecItem specItem = productSpecItemMap
							.get(buycar.getProductSpecItemInfo().getProductSpecItemId());
					updateBuyCar(buycar, specItem.getStock());
				}
				buycars.addAll(more.stream().filter(obj -> obj.getQuantity() > 0).collect(Collectors.toList()));
				collect2.addAll(more.stream().filter(obj -> obj.getQuantity() == 0).collect(Collectors.toList()));
			}
			//查询是否是活动商品
			if (buycars.size() > 0) {
				String productId = buycars.stream().map(obj -> obj.getProductId().toString())
						.collect(Collectors.joining(","));
				List<Product> products = productService.findByIds(productId);
				products = products.stream()
						.filter(obj -> obj.getProductTypeList() != null && obj.getProductTypeList().length() > 0)
						.collect(Collectors.toList());
				if (products != null && products.size() > 0) {
					//封装商品活动信息
					//优惠券活动只会有一个进行中的
					ActivityCoupon coupon = activityCouponService.findUnderwayActivityCoupon(appmodelId);
					Map<String, Object> couponJson = new HashMap<>(2);
					if (coupon != null) {
						couponJson.put("activityId", coupon.getActivityCouponId());
						couponJson.put("activityType", ActivityType.COUPON);
					}
					//根据商品进行分类
					Map<Long, List<Buycar>> buycarsMap = buycars.stream()
							.collect(Collectors.groupingBy(Buycar::getProductId));
					for (Product product : products) {
						JoinActiveInfo joinActiveInfo = new JoinActiveInfo();
						String[] activityType = product.getProductTypeList().split(",");
						int maxQuantity = 0;
						Map<Long, ActivityProduct> activityProductMap = new HashMap<>();
						for (String type : activityType) {
							if (type.equals(ActivityType.COUPON)) {
								joinActiveInfo.setCouponInfo(JSON.toJSONString(couponJson));
							}
							if (StringUtils.equals(type, ActivityType.SECONDKILL.toString())) {
								//查询指定商品存在的秒杀活动(进行中和未开始)
								ActivityProduct activityProduct = activityProductService
										.findProductJoinActivityNotEnd(product.getProductId(), ActivityType.SECONDKILL);
								if (activityProduct == null) {
									continue;
								}
								activityProductMap.put(activityProduct.getProductId(), activityProduct);
								maxQuantity = activityProduct.getMaxQuantity();

								Map<String, Object> activeInfo = new HashMap<>(3);
								activeInfo.put("activityId", activityProduct.getActivityId());
								activeInfo.put("activityProductId", activityProduct.getActivityProductId());
								activeInfo.put("activityType", type);
								joinActiveInfo.setActiveInfo(JSON.toJSONString(activeInfo));
							}
							if (type.equals(ActivityType.GROUP)) {

							}
							if (type.equals(ActivityType.SPECIAL)) {

							}
						}
						//封装活动信息到购物对象
						List<Buycar> buycarList = buycarsMap.get(product.getProductId());
						for (Buycar buycar : buycarList) {
							buycar.setJoinActiveInfo(joinActiveInfo);
							if (maxQuantity != 0) {
								buycar.setMaxQuantity(maxQuantity);
							}
							if (buycar.getQuantity() < maxQuantity) {
								buycar.setQuantity(buycar.getMaxQuantity());
							}
							ActivityProduct activityProduct = activityProductMap.get(buycar.getProductId());
							if (buycar.getProductSpecItemInfo().getProductSpecItemId() != null) {
								ProductSpecItem productSpecItemInfo = buycar.getProductSpecItemInfo();
								List<ActivityProductStock> activityProductSpec = activityProductStockService
										.findActivityProductSpec(activityProduct.getActivityProductId());
								for (ActivityProductStock productStock : activityProductSpec) {
									if (productStock.getProductSpecItemId()
											.equals(productSpecItemInfo.getProductSpecItemId())) {
										buycar.getProductSpecItemInfo().setPrice(productStock.getActivityPrice());
										break;
									}
								}
							} else {
								buycar.getProductSpecItemInfo().setPrice(activityProduct.getActivityPrice());
							}

						}
					}
				}

			}
		}
		buyCarsVo.setAvailable(buycars);
		return ResultGenerator.genSuccessResult(buyCarsVo);
	}

	/**
	 * 更新购物车中的商品为最新的价格
	 * @param buycar
	 * @param stock
	 */
	private void updateBuyCar(Buycar buycar, Integer stock) {
		if (buycar.getQuantity() > stock) {
			int quntity = buycar.getQuantity();
			while (true) {
				quntity -= 1;
				if (quntity <= stock) {
					if (quntity == 0 || quntity < 0) {
						buycar.setQuantity(0);
						break;
					}
					buycar.setQuantity(quntity);
					break;
				}
			}
			buycar.getProductSpecItemInfo().setStock(stock);
			buycarRepository.save(buycar);
		}
	}

	@Override
	public Result batchDelete(String buycarIds, Long wxuserId, Integer type) {
		if (type.equals(1)) {
			String[] s = buycarIds.split(",");
			List<Buycar> buycars = new ArrayList<>();
			for (int j = 0; j < s.length; j++) {
				Buycar buycar = new Buycar();
				buycar.setBuycarId(s[j]);
				buycars.add(buycar);
			}
			buycarRepository.deleteAll(buycars);
			return ResultGenerator.genSuccessResult();
		}
		if (type.equals(2)) {
			buycarRepository.deleteByWxuserId(wxuserId);
			return ResultGenerator.genSuccessResult();
		}
		return ResultGenerator.genFailResult("删除失败");
	}

	@Override
	public Result findBurCarSum(Long wxuserId, String appmodelId) {
		List<Buycar> byWxuserIdAndAndShelfState = buycarRepository
				.findByWxuserIdAndAppmodelIdAndShelfState(wxuserId, appmodelId, 0);
		int sum = 0;
		if (byWxuserIdAndAndShelfState != null && byWxuserIdAndAndShelfState.size() > 0) {
			sum = byWxuserIdAndAndShelfState.stream().mapToInt(Buycar::getQuantity).sum();
		}
		return ResultGenerator.genSuccessResult(sum);
	}

	@Override
	public Result updateBurCar(Buycar buycar) {
		List<Buycar> oldBuys = buycarRepository
				.findByProductIdAndAppmodelIdAndWxuserId(buycar.getProductId(), buycar.getAppmodelId(),
						buycar.getWxuserId());
		if (null != oldBuys) {
			if (oldBuys.size() > 1) {
				Collections.sort(oldBuys, Comparator.comparing(Buycar::getQuantity));
				//如果出现重复取多个中数量最大的,并删除其他
				buycar.setQuantity(oldBuys.get(oldBuys.size() - 1).getQuantity());
				oldBuys.forEach(obj -> {
					buycarRepository.deleteById(obj.getBuycarId());
				});
				buycar.setCreateTime(TimeUtil.getNowTime());
				buycar.setCountPrice(buycar.getQuantity() * buycar.getProductSpecItemInfo().getPrice());
				Buycar insert = buycarRepository.insert(buycar);
				if (null != insert.getProductId()) {
					return ResultGenerator.genSuccessResult();
				}
			}
			buycar.setCreateTime(TimeUtil.getNowTime());
			buycar.setCountPrice(buycar.getQuantity() * buycar.getProductSpecItemInfo().getPrice());
			buycarRepository.save(buycar);
			return ResultGenerator.genSuccessResult();
		}
		return ResultGenerator.genFailResult("更新失败");
	}

	@Override
	public List<Buycar> selectAllBuyCarNumber(OrderSurveyVo orderSurveyVo) {
		Query query = new Query();
		query.addCriteria(Criteria.where("shelfState").is(0));
		query.addCriteria(Criteria.where("appmodelId").is(orderSurveyVo.getAppmodelId()));
		if (orderSurveyVo.getStartDate() != null && orderSurveyVo.getEndDate() != null) {
			if (!"".equals(orderSurveyVo.getStartDate()) && !"".equals(orderSurveyVo.getEndDate())) {
				Criteria criteria = new Criteria()
						.andOperator(Criteria.where("createTime").gte(orderSurveyVo.getStartDate()),
								Criteria.where("createTime").lte(orderSurveyVo.getEndDate()));
				query.addCriteria(criteria);
			}
		}
		if (orderSurveyVo.getRealTime() != null) {
			query.addCriteria(Criteria.where("createTime").regex(".*?" + orderSurveyVo.getRealTime() + ".*"));
		}

		List<Buycar> buycars = mongoTemplate.find(query, Buycar.class);

		List<Buycar> result = new ArrayList<>();
		//		Map<Long, List<Buycar>> counting = buycars.stream().collect(Collectors.groupingBy(Buycar::getProductId));
		//		counting.
		//		for (Map.Entry entry : counting.entrySet()) {
		//			List<Buycar> buycarsTwo = (List) entry.getValue();
		//			/*for (Buycar buycar : buycarsTwo) {
		//				if (buycar.getProductSpecItemInfo().getSpecificationValue() == null || buycar.getProductSpecItemInfo()
		//						.getSpecificationValue().equals("")) {
		//					buycar.setSpecificationValue("0");
		//				} else {
		//					buycar.setSpecificationValue(buycar.getProductSpecItemInfo().getSpecificationValue());
		//				}
		//			}*/
		//			Map<String, List<Buycar>> countingNew = buycarsTwo.stream()
		//					.collect(Collectors.groupingBy(Buycar::getSpecificationValue));
		//			for (Map.Entry entryNew : countingNew.entrySet()) {
		//				List<Buycar> buycarsThree = (List) entryNew.getValue();
		//				Integer summingInt = buycarsThree.stream().collect(Collectors.summingInt(Buycar::getQuantity));
		//				Buycar buycar = buycarsThree.get(0);
		//				buycar.setQuantity(summingInt);
		//				result.add(buycar);
		//			}
		//		}
		return buycars;
	}

	@Override
	public void updateShelfState(List<Long> productIds, int shelfState) {
		Query query = new Query(Criteria.where("productId").in(productIds));
		mongoTemplate.updateMulti(query, Update.update("shelfState", shelfState), Buycar.class);
	}

	@Override
	public void updateActiveInfo(List<Map<String, Object>> maps) {
		for (Map<String, Object> map : maps) {
			Long productId = (Long) map.get("productId");
			Integer otherId = (Integer) map.get("otherId");
			Integer couponId = (Integer) map.get("couponId");
			List<Buycar> buycars = mongoTemplate
					.find(new Query(Criteria.where("productId").is(productId)), Buycar.class);
			if (otherId != null && otherId > 0 && couponId != null && couponId > 0) {
				Query query = new Query(Criteria.where("productId").is(productId));
				mongoTemplate.updateMulti(query, Update.update("joinActiveInfo", null), Buycar.class);
			} else if (otherId != null && otherId > 0) {
				updateActiveInfo(buycars, 1);
			} else if (couponId != null && couponId > 0) {
				updateActiveInfo(buycars, 2);
			}
		}
	}

	/**
	 * @param buycars
	 * @param type    1-修改购物车中特价活动信息 2-修改优惠券活动信息
	 */
	private void updateActiveInfo(List<Buycar> buycars, Integer type) {
		for (Buycar buycar : buycars) {
			if (buycar.getJoinActiveInfo() != null) {
				switch (type) {
					case 1:
						buycar.getJoinActiveInfo().setActiveInfo(null);
						break;
					case 2:
						buycar.getJoinActiveInfo().setCouponInfo("");
						break;
					default:
						break;
				}
				mongoTemplate.save(buycar);
			}
		}
	}


	@Override
	public void addBuycar(Product product, Long wxuserId, Integer quantity, ProductSpecItem productSpecItemInfo,
			JoinActiveInfo joinActiveInfo) {
		Buycar buycar = new Buycar();
		buycar.setWxuserId(wxuserId);
		buycar.setProductName(product.getProductName());
		buycar.setAppmodelId(product.getAppmodelId());
		buycar.setProductId(product.getProductId());
		buycar.setProductImg(product.getProductImg());
		buycar.setQuantity(quantity);
		buycar.setShelfState(0);
		buycar.setJoinActiveInfo(joinActiveInfo);
		buycar.setProductSpecItemInfo(productSpecItemInfo);
		addProductToBuycar(buycar);
	}

	@Override
	public List<Buycar> findByProductId(List<Long> productId) {
		Query query = new Query(Criteria.where("productId").in(productId));
		return mongoTemplate.find(query, Buycar.class);
	}

	@Override
	public void updateBurCars(List<Buycar> buycars) {
		buycarRepository.saveAll(buycars);
	}

	@Override
	public Map<String, Object> selectRealTimeBuycarList(Integer pageNum, Integer pageSize, String appmodelId) {
		String startTime = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.format(DateUtil.beginOfDate(new Date()));
		String endtime = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.format(DateUtil.endOfDate(new Date()));
		Aggregation aggregation = Aggregation.newAggregation(Aggregation
						.match(Criteria.where("createTime").gte(startTime).lte(endtime).and("appmodelId").is(appmodelId)),
				sort(Sort.Direction.DESC, "createTime"),
				Aggregation.group("productId").first("productId").as("productId").sum("quantity").as("totle")
						.first("productImg").as("productImg").first("productName").as("productName"),
				Aggregation.skip(Long.valueOf(pageNum - 1)), Aggregation.limit(pageSize));
		AggregationResults<Buycar> buycarInfo = mongoTemplate.aggregate(aggregation, "BuycarInfo", Buycar.class);
		List<Buycar> mappedResults = buycarInfo.getMappedResults();
		Map<String, Object> map = new HashMap<>();
		map.put("list", mappedResults);
		aggregation = Aggregation
				.newAggregation(Aggregation.match(Criteria.where("createTime").gte(startTime).lte(endtime)),
						sort(Sort.Direction.DESC, "createTime"),
						Aggregation.group("productId").first("productId").as("productId").sum("quantity").as("totle")
								.first("productImg").as("productImg"), Aggregation.count().as("totle"));
		AggregationResults<Buycar> totle = mongoTemplate.aggregate(aggregation, "BuycarInfo", Buycar.class);
		if (totle.getMappedResults().size() == 0) {
			map.put("totle", 0);
		} else {
			map.put("totle", totle.getUniqueMappedResult().getTotle());
		}
		return map;
	}

	@Override
	public String selectJoinBuycarSum(String startDate, String endDate, String appmodelId) {
		Aggregation aggregation = null;
		if (startDate != null && startDate.length() > 0) {
			try {
				String startTime = null;
				startTime = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT
						.format(DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.parse(startDate));
				String endtime = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT
						.format(DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.parse(endDate));
				aggregation = Aggregation.newAggregation(Aggregation
						.match(Criteria.where("createTime").gte(startTime).lte(endtime).and("appmodelId")
								.is(appmodelId)), Aggregation.count().as("totle"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			aggregation = Aggregation.newAggregation(Aggregation.match(Criteria.where("appmodelId").is(appmodelId)),
					Aggregation.count().as("totle"));
		}
		AggregationResults<Buycar> buycarInfo = mongoTemplate.aggregate(aggregation, "BuycarInfo", Buycar.class);
		if (buycarInfo.getUniqueMappedResult() != null) {
			return buycarInfo.getUniqueMappedResult().getTotle();
		}
		return "0";
	}

	@Override
	public void updateActivityMark(Long productId, Integer activityId, Integer activityType) {
		Query query = new Query(Criteria.where("productId").is(productId));
		if (activityId != null && activityId > 0) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("activityId", activityId);
			jsonObject.put("activityType", activityType);
			mongoTemplate.updateMulti(query, Update.update("joinActiveInfo.activeInfo", jsonObject.toJSONString()),
					Buycar.class);
		} else {
			mongoTemplate.updateMulti(query, Update.update("joinActiveInfo.activeInfo", ""), Buycar.class);
		}
	}

	@Override
	public void updateCouponInfo(Long productId, String appmodelId, String couponInfo) {
		Query query = new Query(Criteria.where("productId").is(productId).and("appmodelId").is(appmodelId));
		mongoTemplate.updateMulti(query, Update.update("joinActiveInfo.couponInfo", couponInfo), Buycar.class);

	}
}
