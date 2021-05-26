package com.medusa.basemall.product.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medusa.basemall.activemq.ActiveMqClient;
import com.medusa.basemall.agent.entity.AgentGrade;
import com.medusa.basemall.agent.service.AgentGradeService;
import com.medusa.basemall.agent.vo.PitchonProduct;
import com.medusa.basemall.agent.vo.SpecVo;
import com.medusa.basemall.article.service.ArticleService;
import com.medusa.basemall.constant.ActiviMqQueueName;
import com.medusa.basemall.constant.ActivityType;
import com.medusa.basemall.constant.RedisPrefix;
import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.core.ServiceException;
import com.medusa.basemall.integral.entity.PrizeRule;
import com.medusa.basemall.integral.service.PrizeRuleService;
import com.medusa.basemall.order.service.BuycarService;
import com.medusa.basemall.order.vo.ProductAgentVo;
import com.medusa.basemall.product.dao.ProductMapper;
import com.medusa.basemall.product.entity.*;
import com.medusa.basemall.product.service.*;
import com.medusa.basemall.product.vo.*;
import com.medusa.basemall.promotion.common.ActivityUtils;
import com.medusa.basemall.promotion.entity.*;
import com.medusa.basemall.promotion.service.*;
import com.medusa.basemall.shop.dao.FirstpageClassifyMapper;
import com.medusa.basemall.shop.dao.PosterMapper;
import com.medusa.basemall.shop.entity.ColumnFlag;
import com.medusa.basemall.shop.entity.Manager;
import com.medusa.basemall.shop.entity.Plate;
import com.medusa.basemall.shop.entity.PlateProduct;
import com.medusa.basemall.shop.service.ColumnFlagService;
import com.medusa.basemall.shop.service.NoticeService;
import com.medusa.basemall.shop.service.PlateProductService;
import com.medusa.basemall.shop.service.PlateService;
import com.medusa.basemall.user.service.CollectService;
import com.medusa.basemall.user.service.FootMarkService;
import com.medusa.basemall.utils.IdGenerateUtils;
import com.medusa.basemall.utils.TimeUtil;
import com.vip.vjtools.vjkit.collection.ListUtil;
import com.vip.vjtools.vjkit.mapper.BeanMapper;
import com.vip.vjtools.vjkit.reflect.ReflectionUtil;
import com.vip.vjtools.vjkit.time.ClockUtil;
import com.vip.vjtools.vjkit.time.DateFormatUtil;
import com.vip.vjtools.vjkit.time.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.Collectors;


/**
 * @author psy
 * @date 2018/05/24
 * 需要事物时添加  @Transactional
 */

@Service
public class ProductServiceImpl extends AbstractService<Product> implements ProductService {

	@Resource
	private ProductMapper productMapper;
	@Resource
	private ActiveMqClient activeMqClient;
	@Resource
	private ProductCategoryService productCategoryService;
	@Resource
	private ProductSpecClassService productSpecClassService;
	@Resource
	private ProductSpecService productSpecService;
	@Resource
	private ProductSpecItemService productSpecItemService;
	@Resource
	private SpecificationService specificationService;
	@Resource
	private AgentGradeService agentGradeService;
	@Resource
	private ActivityCouponService activityCouponService;
	@Resource
	private ActivitySeckillService activitySeckillService;
	@Resource
	private ActivitySpecialService activitySpecialService;
	@Resource
	private ActivityGroupService activityGroupService;
	@Resource
	private ActivityProductService activityProductService;
	@Resource
	private ActivityProductStockService activityProductStockService;
	@Resource
	private PrizeRuleService prizeRuleService;
	@Resource
	private RedisTemplate redisTemplate;
	@Resource
	private ColumnFlagService columnFlagService;
	@Resource
	private SpecificationClassService specificationClassService;
	@Resource
	private CollectService collectService;
	@Resource
	private FootMarkService footMarkService;
	@Resource
	private PlateProductService plateProductService;
	@Resource
	private PlateService plateService;
	@Resource
	private BuycarService buycarService;
	@Resource
	private PosterMapper posterMapper;
	@Resource
	private FirstpageClassifyMapper firstpageClassifyMapper;
	@Resource
	private NoticeService noticeService;
	@Resource
	private ArticleService articleService;

	/**
	 * 添加商品
	 *
	 * @param productEditVo
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int saveProduct(ProductEditVo productEditVo) {

		int rs = 0;
		//添加产品
		Long productId = IdGenerateUtils.getItemId();
		String appmodelId = productEditVo.getAppmodelId();

		productEditVo.setProductId(productId);
		productEditVo.setCreateTime(TimeUtil.getNowTime());
		productEditVo.setParamValue(JSON.toJSONString(productEditVo.getParamValueList()));
		productEditVo.setRimgUrl(JSON.toJSONString(productEditVo.getRimgurlList()));
		productEditVo.setServiceAssurance(JSON.toJSONString(productEditVo.getServiceAssuranceList()));
		productEditVo.setDeleteState(false);

		//有进行中的优惠券并且是针对全店商品的,需添加标签
		ActivityCoupon underwayActivityCoupon = activityCouponService.findUnderwayActivityCoupon(appmodelId);
		if (underwayActivityCoupon != null) {
			productEditVo.setProductTypeList(ActivityType.COUPON.toString());
		}
		rs += productMapper.insertSelective(productEditVo);

		//添加商品分类
		int crs = productCategoryService.saveProductCategory(productEditVo.getCategoryIds(), productId, appmodelId);
		if (crs > 0) {
			rs += crs;
		} else {
			rs = -1;
		}
		//判断是否是多规格
		if (!productEditVo.getSpecType()) {
			//添加商品规格分类
			int scrs = productSpecClassService
					.saveProductSpecClass(productEditVo.getProductSpecClassList(), productId, appmodelId);
			//添加商品规格
			int srs = productSpecService.saveProductSpec(productEditVo.getProductSpecList(), productId, appmodelId);
			//添加商品规格详情
			List<ProductSpecItem> productSpecItemList = productEditVo.getProductSpecItemList();
			productSpecItemList.sort(Comparator.comparing(ProductSpecItem::getPrice));
			int sirs = productSpecItemService.saveProductSpecItem(productSpecItemList, productId, appmodelId);
			int stock = 0;
			for (ProductSpecItem productSpecItem : productEditVo.getProductSpecItemList()) {
				stock = stock + productSpecItem.getStock();
			}
			productEditVo.setStock(stock);
			//多规格,商品的price去规格的最小价格
			productEditVo.setPrice(productSpecItemList.get(0).getPrice());
			productMapper.updateByPrimaryKeySelective(productEditVo);
			if (scrs > 0 && srs > 0 && sirs > 0) {
				rs = scrs + srs + sirs;
			} else {
				rs = -1;
			}
		}

		return rs;
	}

	/**
	 * 编辑商品查询数据
	 *
	 * @param productId
	 * @param appmodelId
	 * @return
	 */
	@Override
	public ProductEditVo findProductForUpdate(Long productId, String appmodelId) {
		//商品详情
		Product product = productMapper.selectByPrimaryKey(productId);
		ProductEditVo productEditVo = BeanMapper.map(product, ProductEditVo.class);
		productEditVo.setParamValueList(JSON.parseArray(productEditVo.getParamValue(), Parameter.class));
		productEditVo.setParamValue(null);
		productEditVo.setRimgurlList(JSON.parseArray(productEditVo.getRimgUrl(), String.class));
		productEditVo.setRimgUrl(null);
		productEditVo.setServiceAssuranceList(JSON.parseArray(productEditVo.getServiceAssurance(), String.class));
		productEditVo.setServiceAssurance(null);
		//商品分类
		List<ProductCategory> productCategoryList = productCategoryService.findByProductId(productId);
		if (!productCategoryList.isEmpty()) {
			List<Long> categoryIds = productCategoryList.stream().map(ProductCategory::getCategoryId)
					.collect(Collectors.toList());
			productEditVo.setCategoryIds(categoryIds);
		}
		//商品选中的规格分类
		List<ProductSpecClass> productSpecClassList = productSpecClassService.findByProductId(productId, appmodelId);
		if (!productSpecClassList.isEmpty()) {
			productEditVo.setProductSpecClassList(productSpecClassList);
		}
		//商品选中的规格
		List<ProductSpec> productSpecList = productSpecService.findByProductId(productId);
		if (!productSpecList.isEmpty()) {
			productEditVo.setProductSpecList(productSpecList);
		}
		//商品的规格价格详情
		List<ProductSpecItem> productSpecItemList = productSpecItemService.findByProductId(productId);
		if (!productSpecItemList.isEmpty()) {
			productEditVo.setProductSpecItemList(productSpecItemList);
		}
		return productEditVo;
	}

	/**
	 * 更新商品
	 *
	 * @param productEditVo
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updateProduct(ProductEditVo productEditVo) {
		int rs = 0;
		//更新商品
		productEditVo.setParamValue(JSON.toJSONString(productEditVo.getParamValueList()));
		productEditVo.setRimgUrl(JSON.toJSONString(productEditVo.getRimgurlList()));
		productEditVo.setServiceAssurance(JSON.toJSONString(productEditVo.getServiceAssuranceList()));
		rs += productMapper.updateByPrimaryKeySelective(productEditVo);
		long productId = productEditVo.getProductId();
		String appmodelId = productEditVo.getAppmodelId();
		//更新商品分类
		rs += productCategoryService.updateProductCategory(productEditVo.getCategoryIds(), productId, appmodelId);
		if (!productEditVo.getSpecType()) {
			//更新商品规格分类
			rs += productSpecClassService
					.updateProductSpecClass(productEditVo.getProductSpecClassList(), productId, appmodelId);
			//更新商品规格
			rs += productSpecService.updateProductSpec(productEditVo.getProductSpecList(), productId, appmodelId);
			//更新商品规格详情
			rs += productSpecItemService
					.updateProductSpecItem(productEditVo.getProductSpecItemList(), productId, appmodelId);
		}
		return rs;
	}

	/**
	 * 批量上下架
	 *
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int batchUpdateShelfState(List<DeleteProduct> deleteProduct) {
		List<Long> productIds = deleteProduct.stream().map(DeleteProduct::getProductId).collect(Collectors.toList());
		Integer shelfState = deleteProduct.get(0).getShelfState();
		int rs = 0;
		Map<String, Object> map = new HashMap<>(2);
		map.put("shelfState", shelfState);
		map.put("productIds", productIds);
		rs += productMapper.batchUpdateShelfState(map);
		//商品上下架   0-上架 1-下架   商品(收藏，足迹,购物车)都要上下架
		if (rs > 0) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("productIds", StringUtils.join(productIds, ","));
			jsonObject.put("shelfState", shelfState);
			activeMqClient.send(jsonObject.toJSONString(), ActiviMqQueueName.PRODUCT_SHELFSTATE_CHANGE);
			if (shelfState == 1) {
				noticeService.cleanProduct(productIds);
				articleService.cleanProduct(productIds);
				//商品的product_type_list 设置为null
				productMapper.updateSetProductTypeListToNull(productIds);
				activityAndPlate(deleteProduct);
				productIds.forEach(productId -> {
					HashMap<String, Object> mapNew = new HashMap<>(1);
					mapNew.put("productId", productId);
					posterMapper.updatePoster(mapNew);
					firstpageClassifyMapper.updateFirstpage(mapNew);
				});
			}
		}

		return rs;
	}

	/**
	 * 查询指定商品中是否可购买
	 *
	 * @param productIds
	 * @return
	 */
	@Override
	public List<Product> findProductNotDeleteAndShelfstate(String productIds) {
		List<Product> products = productMapper.selectByIds(productIds);
		//筛选出还在上架状态的商品
		products = products.stream().filter(obj -> obj.getShelfState().equals(0)).collect(Collectors.toList());
		if (products != null && products.size() > 0) {
			products = products.stream().filter(obj -> obj.getDeleteState().equals(false)).collect(Collectors.toList());
		}
		return products;
	}

	@Override
	public int updateStockOrPrice(Long productId, Integer stock, Double price) {
		Product up = new Product();
		up.setProductId(productId);
		if (stock != null && price != null) {
			return 2;
		}
		Product product = productMapper.selectByPrimaryKey(productId);
		JSONObject jsonObject = new JSONObject();
		//在仓库中商品修改库存
		if (product.getShelfState().equals(1) || product.getShelfState().equals(0)) {
			if (product.getShelfState().equals(0) && stock != null && stock == 0) {
				up.setShelfState(2);
			}
			if (stock != null) {
				up.setStock(stock);
			} else if (price != null) {
				up.setPrice(price);
			}
			jsonObject.put("productIds", productId.toString());
			jsonObject.put("shelfState", product.getShelfState());
			activeMqClient.send(jsonObject.toJSONString(), ActiviMqQueueName.PRODUCT_SHELFSTATE_CHANGE);
		}
		//已售罄
		if (product.getShelfState().equals(2)) {
			if (product.getStock().equals(0)) {
				up.setShelfState(2);
				up.setStock(stock);
				jsonObject.put("productIds", productId.toString());
				jsonObject.put("shelfState", 2);
				activeMqClient.send(jsonObject.toJSONString(), ActiviMqQueueName.PRODUCT_SHELFSTATE_CHANGE);
			} else if (stock != null && stock >= 0) {
				up.setStock(stock);
				up.setShelfState(0);
				jsonObject.put("productIds", productId.toString());
				jsonObject.put("shelfState", 0);
				activeMqClient.send(jsonObject.toJSONString(), ActiviMqQueueName.PRODUCT_SHELFSTATE_CHANGE);
			} else if (price != null) {
				up.setPrice(price);
				jsonObject.put("productIds", productId.toString());
				jsonObject.put("shelfState", productMapper.selectByPrimaryKey(productId).getShelfState());
				activeMqClient.send(jsonObject.toJSONString(), ActiviMqQueueName.PRODUCT_SHELFSTATE_CHANGE);
			}
		}
		return update(up);
	}

	@Override
	public List<Product> selectByAppmodelId(String appmodelId) {
		return productMapper.selectByAppmodelId(appmodelId);
	}

	@Override
	public List<ProductSimpleVo> selectProductSimpleVo(String appmodelId) {
		return productMapper.selectProductSimpleVo(appmodelId);
	}

	@Override
	public void updateActivityLabel(Long productId, String label) {
		if (productId == null) {
			throw new ServiceException("商品id不能为空");
		}
		Product product = new Product();
		product.setProductId(productId);
		product.setProductTypeList(label);
		productMapper.updateByPrimaryKeySelective(product);
	}

	@Override
	public void addStock(Long productId, int stock) {
		productMapper.updateStock(productId, stock);
	}

	@Override
	public List<HomeSeckillVO> homePageAppmodelId(String appmodelId) {
		List<ActivitySeckill> activitySeckills = activitySeckillService.selectThatVeryDaySeckillActivity(appmodelId);
		List<HomeSeckillVO> homeSeckillVOS = BeanMapper.mapList(activitySeckills, HomeSeckillVO.class);
		Iterator<HomeSeckillVO> iterator = homeSeckillVOS.iterator();
		while (iterator.hasNext()) {
			HomeSeckillVO next = iterator.next();
			//查询首页显示的商品
			List<ProductBackViewVo> list = productMapper.findSeckillProduct(appmodelId, next.getActivitySeckillId());
			//判断活动是在在预热期内,不在则删
			boolean notNull = next.getNowState().equals(1) && StringUtils.isNotBlank(next.getPreheatTime());
			boolean condition1 = notNull && next.getPreheatTime().length() != 0
					&& TimeUtil.str2Timestamp(TimeUtil.getNowTime()) < TimeUtil.str2Timestamp(next.getPreheatTime());
			boolean condition2 = notNull && next.getPreheatTime().length() == 0;
			if (condition1 || condition2) {
				iterator.remove();
				continue;
			}
			if (list != null && !list.isEmpty()) {
				next.setEndDate(ActivityUtils.setEndDate(next.getEndDate(), 1));
				List<ActivityProduct> activityProduct = activityProductService
						.findActivityProduct(next.getActivitySeckillId().toString(), ActivityType.SECONDKILL,
								appmodelId);
				Map<Long, ActivityProduct> activityProductMap = activityProduct.stream()
						.collect(Collectors.toMap(k -> k.getProductId(), v -> v));
				Iterator<ProductBackViewVo> iterator1 = list.iterator();
				while (iterator1.hasNext()) {
					ProductBackViewVo next1 = iterator1.next();
					ActivityProduct ap = activityProductMap.get(next1.getProductId());
					if (ap.getHomeViewStat().equals(0)) {
						iterator1.remove();
						continue;
					}
					getActivityProductSpec(next1, ap);
				}
				if (list.size() > 0) {
					next.setActivityProduct(list);
					//结束时间加一秒
					next.setEndDate(ActivityUtils.setEndDate(next.getEndDate(), 1));
					continue;
				}
			}
			iterator.remove();
		}
		homeSeckillVOS.sort(Comparator.comparing(obj -> obj.getStartDate()));
		return homeSeckillVOS;
	}

	@Override
	public List<HomeSeckillVO> findSeckillProduct(String appmodelId) {
		List<ActivitySeckill> activitySeckills = activitySeckillService.selectNotEnd(appmodelId);
		List<HomeSeckillVO> homeSeckillVOS = BeanMapper.mapList(activitySeckills, HomeSeckillVO.class);
		for (HomeSeckillVO homeSeckillVO : homeSeckillVOS) {
			List<ProductBackViewVo> list = productMapper
					.findSeckillProduct(appmodelId, homeSeckillVO.getActivitySeckillId());
			homeSeckillVO.setActivityProduct(getProductActivityinfo(list, 1));
			//结束时间加一秒
			homeSeckillVO.setEndDate(ActivityUtils.setEndDate(homeSeckillVO.getEndDate(), 1));
		}
		return homeSeckillVOS;
	}


	@Override
	public List<ProductBackViewVo> findPlateProductIds(List<Long> productIds) {
		return productMapper.selectPlateProductIds(productIds);
	}

	private void activityAndPlate(List<DeleteProduct> deleteProduct) {

		//如果是活动商品,活动商品中需要删除
		activityProductService.deleteActivityProduct(deleteProduct);

		//商品展示区商品直接删除
		List<DeleteProduct> havaPlate = deleteProduct.stream().filter(obj -> obj.getPlateVos() != null)
				.collect(Collectors.toList());
		if (havaPlate != null && havaPlate.size() > 0) {
			for (DeleteProduct product : havaPlate) {
				for (PlateVo plateVo : product.getPlateVos()) {
					Integer plateId = plateVo.getPlateId();
					Plate plate = plateService.findById(plateId);
					if (plate != null) {
						plate.setProductNum(plate.getProductNum() - 1);
						if (plate.getProductNum() < 1) {
							plateService.deleteById(plateId);
							plateProductService.deleteByPlateId(plateId);
						}
						if (plate.getProductNum() >= 1) {
							plateService.update(plate);
							plateProductService.deleteByPlateIdAndProduct(plateId, product.getProductId());
						}
					}
				}
			}
		}
	}

	/**
	 * 批量删除
	 *
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int batchDelete(List<DeleteProduct> deleteProducts) {
		List<Long> productIds = deleteProducts.stream().map(DeleteProduct::getProductId).collect(Collectors.toList());
		// 商品删除后，所有商品(包括活动商品，分销商品，商品展示区商品,轮播图链接的商品，公告链接的商品)都要上下架，删除
		int i = productMapper.batchDelete(productIds);
		if (i > 0) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("productIds", StringUtils.join(productIds, ","));
			jsonObject.put("shelfState", 1);
			activeMqClient.send(jsonObject.toJSONString(), ActiviMqQueueName.PRODUCT_SHELFSTATE_CHANGE);
			activityAndPlate(deleteProducts);

			noticeService.cleanProduct(productIds);
			articleService.cleanProduct(productIds);
			productIds.forEach(productId -> {
				HashMap<String, Object> map = new HashMap<>(1);
				map.put("productId", productId);
				posterMapper.updatePoster(map);
				firstpageClassifyMapper.updateFirstpage(map);
			});
		}
		return i;
	}

	@Override
	public PageInfo findProductForBack(ProductFindRequestVo requestVo) {
		PageHelper.startPage(requestVo.getPageNum(), requestVo.getPageSize(), "create_time desc");
		List<ProductBackViewVo> list = productMapper.findProductForBack(requestVo);
		list = getProductActivityinfo(list, 2);
		//添加分类信息
		List<CategoryProductVo> categoryProductVos = productCategoryService
				.findByCategoryById(list.stream().map(obj -> obj.getProductId()).collect(Collectors.toList()),
						requestVo.getAppmodelId());
		list.parallelStream().forEach(productBackViewVo -> {
			List<CategoryProductVo> categoryList = new ArrayList<>();
			for (CategoryProductVo categoryProductVo : categoryProductVos) {
				if (categoryProductVo.getProductId().equals(productBackViewVo.getProductId())) {
					categoryList.add(categoryProductVo);
				}
			}
			productBackViewVo.setCategoryList(categoryList);
		});
		//添加商品的首页模块信息
		List<Long> productIds = list.stream().map(obj -> obj.getProductId()).collect(Collectors.toList());
		if (productIds != null && productIds.size() > 0) {
			Condition condition = new Condition(PlateProduct.class);
			condition.createCriteria().andIn("productId", productIds);
			List<PlateProduct> plateProducts = plateProductService.findByCondition(condition);
			if (plateProducts != null && plateProducts.size() > 0) {
				Map<Integer, List<PlateProduct>> groupby = plateProducts.stream()
						.collect(Collectors.groupingBy(PlateProduct::getPlateId));
				Set<Integer> plateIds = groupby.keySet();
				StringBuilder sb = new StringBuilder();
				for (Integer plateId : plateIds) {
					sb.append(plateId + ",");
				}
				List<Plate> plates = plateService.findByIds(sb.substring(0, sb.length() - 1));
				plates = plates.stream().filter(plate -> plate.getPlateFlag().equals(true))
						.collect(Collectors.toList());
				List<PlateVo> plateVos = BeanMapper.mapList(plates, PlateVo.class);
				if (plateVos != null && plateVos.size() > 0) {
					for (PlateVo plateVo : plateVos) {
						plateVo.setPlateProducts(groupby.get(plateVo.getPlateId()));
					}
					Map<Long, List<PlateVo>> map = new HashMap<>(list.size());
					for (PlateVo plateVo : plateVos) {
						plateVo.getPlateProducts().forEach(obj -> {
							List<PlateVo> temp = map.get(obj.getProductId());
							if (temp == null || temp.size() == 0) {
								List<PlateVo> vos = new ArrayList<>();
								vos.add(plateVo);
								map.put(obj.getProductId(), vos);
							} else {
								temp.add(plateVo);
								map.put(obj.getProductId(), temp);
							}
						});
					}
					list.forEach(obj -> {
						List<PlateVo> vos = map.get(obj.getProductId());
						if (vos != null) {
							obj.setPlateVos(vos);
						}
					});
				}
			}
		}

		PageInfo pageInfo = new PageInfo(list);
		return pageInfo;
	}

	@Override
	public ProductSpecVo findSpecByProductId(Long productId, String appmodelId) {
		ProductSpecVo productSpecVo = new ProductSpecVo();
		//商品规格分类
		List<ProductSpecClass> productSpecClassList = productSpecClassService.findByProductId(productId, appmodelId);
		if (!productSpecClassList.isEmpty()) {
			productSpecVo.setProductSpecClassList(productSpecClassList);
		}
		//商品的规格
		List<ProductSpec> productSpecList = productSpecService.findByProductId(productId);
		if (!productSpecList.isEmpty()) {
			productSpecVo.setProductSpecList(productSpecList);
		}

		//商品的规格价格详情
		List<ProductSpecItem> productSpecItemList = productSpecItemService.findByProductId(productId);
		if (!productSpecItemList.isEmpty()) {
			Product product = productMapper.selectByPrimaryKey(productId);
			//如果是活动商品替换规格为活动商品的规格,活动商品的规格价格详情
			if (product.getProductTypeList().contains(ActivityType.SECONDKILL.toString())) {
				ActivitySeckill underwayActivitySeckill = activitySeckillService
						.findUnderwayActivitySeckill(appmodelId);
				//如果不是进行中的则是预热中的活动
				if (underwayActivitySeckill == null) {
					underwayActivitySeckill = activitySeckillService.findPreheatActivitySeckill(appmodelId);
				}
				ActivityProduct activityProduct = activityProductService
						.findActivityProduct(underwayActivitySeckill.getActivitySeckillId(), productId, appmodelId);
				List<ActivityProductStock> activityProductStocks = activityProductStockService
						.findActivityProductSpec(activityProduct.getActivityProductId());
				Map<Long, ActivityProductStock> activityProductStockMap = activityProductStocks.stream()
						.collect(Collectors.toMap(key -> key.getProductSpecItemId(), value -> value));
				productSpecItemList.forEach(obj -> {
					ActivityProductStock productStock = activityProductStockMap.get(obj.getProductSpecItemId());
					obj.setProductSpecItemId(Long.valueOf(productStock.getActivityProductStockId()));
					obj.setPrice(productStock.getActivityPrice());
					obj.setStock(productStock.getActivityStock());
				});
			}
			productSpecVo.setProductSpecItemList(productSpecItemList);
		}

		productSpecItemList = productSpecVo.getProductSpecItemList().stream().filter(obj -> obj.getStock() > 0)
				.collect(Collectors.toList());
		productSpecVo.setProductSpecItemList(productSpecItemList);
		productSpecVo.setSpecificationVoList(specificationService.findByAppmodelId(appmodelId));
		return productSpecVo;
	}

	/**
	 * 小程序查寻商品
	 *
	 * @param requestVo
	 * @return
	 */
	@Override
	public PageInfo findProductForWX(ProductFindRequestVo requestVo) {
		switch (requestVo.getSortType()) {
			//分页插件影响 不能在mapper文件中使用多个排序
			case 0:
				PageHelper.orderBy("sales_volume desc,create_time desc,price desc");
				break;
			case 1:
				PageHelper.orderBy("sales_volume desc");
				break;
			case 2:
				PageHelper.orderBy("create_time desc");
				break;
			case 3:
				PageHelper.orderBy("price desc");
				break;
			case 4:
				PageHelper.orderBy("price asc");
				break;
			default:
				PageHelper.orderBy("create_time desc");
				break;
		}
		if (requestVo.getSearchString() != null && requestVo.getSearchString().length() > 0) {
			char[] chars = requestVo.getSearchString().toCharArray();
			StringBuilder stringBuilder = new StringBuilder("%");
			for (int i = 0; i < chars.length; i++) {
				stringBuilder.append(chars[i] + "%");
			}
			requestVo.setSearchString(stringBuilder.toString().substring(0, stringBuilder.length()));
		}
		PageHelper.startPage(requestVo.getPageNum(), requestVo.getPageSize());
		List<ProductBackViewVo> list = productMapper.findProductForWX(requestVo);

		return new PageInfo(getProductActivityinfo(list, 1));
	}

	/**
	 * @param list
	 * @param type 1-前端 2-后台
	 * @return
	 */
	private List<ProductBackViewVo> getProductActivityinfo(List<ProductBackViewVo> list, int type) {
		//找出活动商品
		List<ProductBackViewVo> activeProduct = selectActiveProductInfo(list, type);
		if (activeProduct != null && activeProduct.size() > 0) {
			list.forEach(productWxVo -> {
				activeProduct.forEach(obj -> {
					if (productWxVo.getProductId().equals(obj.getProductId())) {
						productWxVo.setJoinActiveInfo(obj.getJoinActiveInfo());
					}
				});
			});
		}
		//后台查询所有商品不封装活动商品规格库存
		if (type == 2) {
			return list;
		}
		//如果是活动商品,封装活动商品规格库存/活动库存(显示规格价格区间,排除价格为0)
		list.forEach(product -> {
			//过滤多规格中库存等于0的规格
			if (product.getSpecType().equals(false)) {
				if (product.getProductSpecItems().size() == 0) {
					throw new ServiceException("规格错误");
				}
				ProductSpecItem productSpecItem = product.getProductSpecItems().get(0);
				//从大到小排序之后,如果最小的价格库存为0
				if (productSpecItem.getStock().equals(0) || productSpecItem.getStock().equals(0)) {
					Iterator<ProductSpecItem> iterator = product.getProductSpecItems().iterator();
					while (iterator.hasNext()) {
						ProductSpecItem next = iterator.next();
						if (next.getStock().equals(0) || next.getPrice().equals(0.0)) {
							iterator.remove();
						}
					}
				}
			}
		});
		return list;
	}

	private List<ProductBackViewVo> selectActiveProductInfo(List<ProductBackViewVo> productWxVos, int type) {
		List<ProductBackViewVo> collect = productWxVos.stream()
				.filter(obj -> obj.getProductTypeList() != null && !obj.getProductTypeList().equals(""))
				.collect(Collectors.toList());
		Date currentDate = ClockUtil.currentDate();
		ForkJoinTask<List<ProductBackViewVo>> submit = new ForkJoinPool(4).submit(() -> {
			collect.stream().forEach(productWxVo -> {
				JoinActiveInfo joinActiveInfo = new JoinActiveInfo();
				//如果有参加优惠券活动找出优惠券活动信息
				try {
					if (productWxVo.getProductTypeList().length() == 8 || productWxVo.getProductTypeList()
							.contains(ActivityType.COUPON + "")) {
						String activeInfo = null;
						activeInfo = findActiveInfo(currentDate, ActivityCoupon.class, ActivityType.COUPON,
								productWxVo.getAppmodelId());
						if (activeInfo != null) {
							joinActiveInfo.setCouponInfo(activeInfo);
						}
					}
					//找出活动商品参加的活动信息
					String activeInfo = "";
					String productTypeList = productWxVo.getProductTypeList();
					if (productTypeList.contains(ActivityType.SPECIAL + "")) {
						activeInfo = findActiveInfo(currentDate, ActivitySpecial.class, ActivityType.SPECIAL,
								productWxVo.getAppmodelId());
					}
					if (productTypeList.contains(ActivityType.GROUP.toString())) {
						activeInfo = findActiveInfo(currentDate, ActivityGroup.class, ActivityType.GROUP,
								productWxVo.getAppmodelId());
					}
					if (productTypeList.contains(ActivityType.SECONDKILL.toString())) {
						activeInfo = findActiveInfo(currentDate, ActivitySeckill.class, ActivityType.SECONDKILL,
								productWxVo.getAppmodelId());
					}
					if (activeInfo != null && !activeInfo.equals("")) {
						joinActiveInfo.setActiveInfo(activeInfo);
					}
					//查找活动商品信息(活动id+商品id+活动类别)
					JSONObject jsonObject = JSON.parseObject(activeInfo);
					if (jsonObject != null) {
						Condition condition = new Condition(ActivityProduct.class);
						condition.createCriteria().andEqualTo("productId", productWxVo.getProductId())
								.andEqualTo("activityId", jsonObject.getInteger("activityId"))
								.andEqualTo("activityType", jsonObject.getInteger("activityType"));
						ActivityProduct activityProduct = activityProductService.findByOneCondition(condition);
						//优惠券活动没有库存
						if (activityProduct != null) {
							//封装秒杀/拼团/特价活动商品的的商品id
							jsonObject.put("activityProductId", activityProduct.getActivityProductId());
							joinActiveInfo.setActiveInfo(jsonObject.toJSONString());
							productWxVo.setPreheatState(activityProduct.getPreheatState());
							productWxVo.setMaxQuantity(activityProduct.getMaxQuantity());
							if (type == 1) {
								//如果是多规格查找活动商品规格库存信息
								getActivityProductSpec(productWxVo, activityProduct);
							}

						}
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				//设置活动信息
				productWxVo.setJoinActiveInfo(joinActiveInfo);
			});
			return collect;
		});

		try {
			return submit.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		throw new ServiceException("系统异常");
	}

	private void getActivityProductSpec(ProductBackViewVo productWxVo, ActivityProduct activityProduct) {
		productWxVo.setMaxQuantity(activityProduct.getMaxQuantity());
		productWxVo.setStock(activityProduct.getActivityStock());
		productWxVo.setSalesVolume(activityProduct.getSoldQuantity());
		if (productWxVo.getSpecType().equals(false)) {
			List<ProductSpecItem> specItems = getActivityProductSpecItems(activityProduct);
			productWxVo.setProductSpecItems(specItems);
		} else {
			productWxVo.setMarketPrice(productWxVo.getPrice());
			productWxVo.setPrice(activityProduct.getActivityPrice());
		}
	}

	private List<ProductSpecItem> getActivityProductSpecItems(ActivityProduct activityProduct) {
		List<ActivityProductStock> activityProductStock = activityProductStockService
				.findByList("activityProductId", activityProduct.getActivityProductId());
		StringBuffer stringBuffer = new StringBuffer();
		activityProductStock.forEach(obj -> {
			stringBuffer.append(obj.getProductSpecItemId() + ",");
		});
		List<ProductSpecItem> specItems = productSpecItemService
				.findByIds(stringBuffer.substring(0, stringBuffer.length() - 1));
		for (ActivityProductStock productStock : activityProductStock) {
			for (ProductSpecItem specItem : specItems) {
				if (specItem.getProductSpecItemId().equals(productStock.getProductSpecItemId())) {
					specItem.setProductSpecItemId(productStock.getActivityProductStockId().longValue());
					specItem.setActivityStock(productStock.getActivityStock());
					specItem.setActivityPrice(productStock.getActivityPrice());
				}
			}
		}
		ListUtil.sort(specItems, Comparator.comparing(ProductSpecItem::getPrice));
		return specItems;
	}

	/**
	 * 异常不在for循环内里面捕获,向上抛
	 * @param currentDate
	 * @param targetClass
	 * @param activityType
	 * @param appmodelId
	 * @param <T>
	 * @return
	 * @throws ParseException
	 */
	private <T> String findActiveInfo(Date currentDate, Class<T> targetClass, Integer activityType, String appmodelId)
			throws ParseException {
		String format = DateFormatUtil.ISO_ON_DATE_FORMAT.format(currentDate);
		Condition condition = new Condition(targetClass);
		condition.createCriteria().andEqualTo("appmodelId", appmodelId).andIn("nowState", Arrays.asList(1, 2))
				.andEqualTo("deleteState", false).andGreaterThan("endDate", format);
		condition.orderBy("startDate").asc();
		List<T> activeInfo = null;
		switch (activityType) {
			case 1001:
				activeInfo = (List<T>) activityCouponService.findByCondition(condition);
				break;
			case 2001:
				activeInfo = (List<T>) activitySpecialService.findByCondition(condition);
				break;
			case 3001:
				activeInfo = (List<T>) activityGroupService.findByCondition(condition);
				break;
			case 4001:
				activeInfo = (List<T>) activitySeckillService.findByCondition(condition);
				break;
			default:
				break;
		}
		if (activeInfo != null) {
			for (T t : activeInfo) {
				Field startField = ReflectionUtil.getField(t.getClass(), "startDate");
				String startTime = ReflectionUtil.getFieldValue(t, startField);
				Date startDate = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.parse(startTime);

				Field endField = ReflectionUtil.getField(t.getClass(), "endDate");
				String endTime = ReflectionUtil.getFieldValue(t, endField);
				Date endDate = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.parse(endTime);
				boolean preheatFlag = false;
				if (!activityType.equals(ActivityType.COUPON)) {
					//判断是否在预热时间之内
					Field preheat_time = ReflectionUtil.getField(t.getClass(), "preheatTime");
					String preheatTime = ReflectionUtil.getFieldValue(t, preheat_time);
					if (preheatTime != null && preheatTime.length() > 0) {
						ReflectionUtil.getField(t.getClass(), "endDate");

						Date preheatStartTime = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.parse(preheatTime);
						preheatFlag = DateUtil.isBetween(currentDate, preheatStartTime, startDate);
					}
				}
				boolean between = DateUtil.isBetween(currentDate, startDate, endDate);
				if (preheatFlag || between) {
					Map<String, Object> map = new HashMap<>(2);
					map.put("activityType", activityType);
					switch (activityType) {
						case 1001:
							ActivityCoupon activityCoupon = BeanMapper.map(t, ActivityCoupon.class);
							map.put("activityId", activityCoupon.getActivityCouponId());
							break;
						case 2001:
							ActivitySpecial activitySpecial = BeanMapper.map(t, ActivitySpecial.class);
							map.put("activityId", activitySpecial.getActivitySpecialId());
							map.put("nowState", activitySpecial.getNowState());
							break;
						case 3001:
							ActivityGroup activityGroup = BeanMapper.map(t, ActivityGroup.class);
							map.put("activityId", activityGroup.getActivityGroupId());
							map.put("nowState", activityGroup.getNowState());
							break;
						case 4001:
							ActivitySeckill activitySeckill = BeanMapper.map(t, ActivitySeckill.class);
							map.put("activityId", activitySeckill.getActivitySeckillId());
							map.put("nowState", activitySeckill.getNowState());
							break;
						default:
							break;
					}
					return JSON.toJSONString(map);
				}
			}
		}
		return null;
	}

	@Override
	public ProductWxViewDetailsVo findDetailByProductId(Long productId, String appmodelId, Long wxuserId) {
		//商品详情
		Product product = productMapper.selectByPrimaryKey(productId);
		ProductWxViewDetailsVo detailsVo = BeanMapper.map(product, ProductWxViewDetailsVo.class);
		detailsVo.setServiceAssuranceList(JSON.parseArray(product.getServiceAssurance(), String.class));
		detailsVo.setRimgurlList(JSON.parseArray(product.getRimgUrl(), String.class));
		detailsVo.setParamValueList(JSON.parseArray(product.getParamValue(), Parameter.class));
		List<ProductSpecItem> productSpecItemList = null;
		if (product.getSpecType().equals(false)) {
			productSpecItemList = productSpecItemService.findByProductId(productId);
		}
		//如果活动商品封装活动信息
		if (product.getProductTypeList() != null && product.getProductTypeList().length() > 0) {
			JoinActiveInfo joinActiveInfo = new JoinActiveInfo();
			if (product.getProductTypeList().contains(ActivityType.COUPON.toString())) {
				ActivityCoupon underwayActivityCoupon = activityCouponService
						.findUnderwayActivityCoupon(product.getAppmodelId());
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("activityType", ActivityType.COUPON);
				jsonObject.put("activityId", underwayActivityCoupon.getActivityCouponId());
				joinActiveInfo.setCouponInfo(jsonObject.toJSONString());
				detailsVo.setJoinActiveInfo(joinActiveInfo);
			}

				/*if (product.getProductTypeList().contains(ActivityType.SPECIAL.toString())) {
					ActivitySpecial activitySpecial = activitySpecialService.findById(activityId);
					detailsVo.setActiveInfo(JSON.toJSONString(activitySpecial));
				} else if (product.getProductTypeList().contains(ActivityType.GROUP.toString())) {
					ActivityGroup activityGroup = activityGroupService.findById(activityId);
					detailsVo.setActiveInfo(JSON.toJSONString(activityGroup));
				} else*/
			if (product.getProductTypeList().contains(ActivityType.SECONDKILL.toString()) || product
					.getProductTypeList().contains(ActivityType.GROUP.toString()) || product.getProductTypeList()
					.contains(ActivityType.SPECIAL.toString())) {
				ActivityProduct activityProduct = null;
				JSONObject jsonObject = new JSONObject();
				if (product.getProductTypeList().contains(ActivityType.SECONDKILL.toString())) {
					ActivitySeckill activitySeckill = activitySeckillService
							.findUnderwayActivitySeckill(product.getAppmodelId());
					//如果不是进行中的则是预热中的活动
					if (activitySeckill == null) {
						activitySeckill = activitySeckillService.findPreheatActivitySeckill(appmodelId);
					}
					//如果不是预热中的活动则是未开始的活动
					if (activitySeckill == null) {

					}
					activityProduct = activityProductService
							.findActivityProduct(activitySeckill.getActivitySeckillId(), productId, appmodelId);
					if (activityProduct == null) {
						activitySeckill = activitySeckillService.findPreheatActivitySeckill(appmodelId);
						activityProduct = activityProductService
								.findActivityProduct(activitySeckill.getActivitySeckillId(), productId, appmodelId);
						if (activityProduct == null) {
							throw new ServiceException("活动商品不存在");
						}
					}
					jsonObject.put("activityType", ActivityType.SECONDKILL);
					jsonObject.put("activityId", activitySeckill.getActivitySeckillId());
					jsonObject.put("activityProductId", activityProduct.getActivityProductId());
					ProductWxViewDetailsVo.ActiveInfo activeInfo = detailsVo.new ActiveInfo();
					BeanUtils.copyProperties(activitySeckill, activeInfo);
					BeanUtils.copyProperties(activityProduct, activeInfo);
					activeInfo.setActivityName(activitySeckill.getActivitySeckillName());
					activeInfo.setTotleSold(product.getSalesVolume());
					detailsVo.setActiveInfo(activeInfo);
					detailsVo.setMarketPrice(detailsVo.getPrice());
					detailsVo.setPrice(activityProduct.getActivityPrice());
				}
				joinActiveInfo.setActiveInfo(jsonObject.toJSONString());
				activityProduct = activityProductService
						.findActivityProduct(jsonObject.getInteger("activityId"), product.getProductId(), appmodelId);
				detailsVo.setStock(activityProduct.getActivityStock());
				detailsVo.setJoinActiveInfo(joinActiveInfo);
				detailsVo.setPreheatState(activityProduct.getPreheatState());
				if (productSpecItemList != null && productSpecItemList.size() > 0) {
					productSpecItemList = getActivityProductSpecItems(activityProduct);
				}
			}

		}

		String json = (String) redisTemplate.opsForValue().get(RedisPrefix.MANAGER_VERSION + detailsVo.getAppmodelId());
		Manager manager = JSONObject.parseObject(json, Manager.class);
		//计算最多可获得积分
		if (manager.getVersion() > 2) {
			ColumnFlag columnFlag = columnFlagService.findByAppmodelId(appmodelId);
			if (columnFlag.getShopFlag().equals(true)) {
				Double price = product.getPrice();
				//多规格计算最大价格
				if (productSpecItemList != null) {
					productSpecItemList.sort(Comparator.comparing(ProductSpecItem::getPrice));
					price = productSpecItemList.get(productSpecItemList.size() - 1).getPrice();
				}
				PrizeRule prizeRule = prizeRuleService.findByAppmodelId(appmodelId);
				if (prizeRule != null) {
					BigDecimal bigDecimal = new BigDecimal(
							price / prizeRule.getTypeThreePay() * prizeRule.getTypeThreeGet());
					detailsVo.setMaxIntegral(bigDecimal.intValue());
				}
			}
		}
		if (productSpecItemList != null) {
			detailsVo.setProductSpecItems(productSpecItemList);
		}
		detailsVo.setCollect(collectService.findWxuserCollect(wxuserId, product.getProductId()));
		return detailsVo;
	}

	@Override
	public List<ProductAgentVo> findProducttoAgent(String product) {
		Condition condition = new Condition(Product.class);
		condition.createCriteria().andEqualTo("shelfState", "0").andEqualTo("deleteState", 0)
				.andEqualTo("appmodelId", product).andGreaterThan("stock", 0);
		List<Product> products = productMapper.selectByCondition(condition);
		List<ProductAgentVo> productAgentVos = new LinkedList<>();
		ProductAgentVo vo = null;
		for (Product obj : products) {
			vo = new ProductAgentVo();
			vo.setMinPrice(obj.getPrice());
			vo.setProductId(obj.getProductId());
			vo.setProductImage(obj.getProductImg());
			vo.setProductName(obj.getProductName());
			vo.setSendProductTime(obj.getCreateTime());
			productAgentVos.add(vo);
		}
		//过滤出带规格的商品
		List<Product> spec = products.stream().filter(obj -> obj.getSpecType().equals(false))
				.collect(Collectors.toList());
		StringBuffer ids = new StringBuffer();
		spec.forEach(obj -> ids.append(obj.getProductId() + ","));
		//查询带规格商品的规格信息
		Condition specItemCondition = new Condition(ProductSpecItem.class);
		specItemCondition.createCriteria()
				.andIn("productId", Arrays.asList(ids.substring(0, ids.length() - 1).split(",")));
		List<ProductSpecItem> specItems = productSpecItemService.findByCondition(specItemCondition);
		//设置带规格商品的最大最小价格
		for (ProductAgentVo productAgentVoagentVo : productAgentVos) {
			List<ProductSpecItem> collect = specItems.stream()
					.filter(obj -> obj.getProductId().equals(productAgentVoagentVo.getProductId()))
					.collect(Collectors.toList());
			if (collect.size() == 0) {
				continue;
			}
			if (collect.size() > 1) {
				collect.sort(Comparator.comparing(ProductSpecItem::getPrice));
				productAgentVoagentVo.setMaxPrice(collect.get(collect.size() - 1).getPrice());
			}
			productAgentVoagentVo.setMinPrice(collect.get(0).getPrice());
		}
		return productAgentVos;
	}

	@Override
	public Result pitchOn(String productIds, String appmodelId) {
		List<Product> products = productMapper.selectByIds(productIds);
		if (products.size() > 0) {
			Condition agentC = new Condition(AgentGrade.class);
			agentC.createCriteria().andEqualTo("appmodelId", appmodelId);
			List<AgentGrade> agentGrades = agentGradeService.findByCondition(agentC);
			List<PitchonProduct> pitchonProducts = new ArrayList<>();
			StringBuffer buffer = new StringBuffer();
			//遍历添加商品的属性
			products.forEach(obj -> {
				//设置必要的属性
				PitchonProduct pitchonProduct = new PitchonProduct();
				pitchonProduct.setAppmodelId(obj.getAppmodelId());
				pitchonProduct.setProductId(obj.getProductId());
				pitchonProduct.setProductImage(obj.getProductImg());
				pitchonProduct.setProductName(obj.getProductName());
				//只设置统一规格的属性,规格商品另外处理
				if (obj.getSpecType().equals(true)) {
					pitchonProduct.setStock(obj.getStock());
					pitchonProduct.setPrice(new BigDecimal(obj.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
					pitchonProduct.setSpecType(true);
					Collections.sort(agentGrades, Comparator.comparing(AgentGrade::getGradeDiscount));
					//设置统一规格的商品代理价
					List<BigDecimal> agenPrice = new ArrayList<>();
					agentGrades.forEach(aget -> {
						BigDecimal v = aget.getGradeDiscount().divide(new BigDecimal(10.0));
						agenPrice.add(v.multiply(new BigDecimal(obj.getPrice())).setScale(2, BigDecimal.ROUND_HALF_UP));
					});
					pitchonProduct.setAgentPrice(agenPrice);
					;
				} else {
					pitchonProduct.setSpecType(false);
					buffer.append(obj.getProductId() + ",");
				}
				pitchonProducts.add(pitchonProduct);
			});

			//处理有规格的商品
			if (buffer.length() > 0) {
				//查出商品规格信息
				Condition condition = new Condition(ProductSpecItem.class);
				condition.createCriteria()
						.andIn("productId", Arrays.asList(buffer.substring(0, buffer.length() - 1).split(",")));
				List<ProductSpecItem> specItems = productSpecItemService.findByCondition(condition);

				Map<String, String> map = new HashMap<>(16);
				List<SpecVo> specVos = new ArrayList<>();
				//去重之后再去找出所有规格值
				specItems.forEach(obj -> {
					List<Integer> specId = new LinkedList<>();
					String[] split = obj.getSpecificationValue().split(",");
					for (String s : split) {
						specId.add(Integer.valueOf(s));
						if (map.get(s + "") == null) {
							map.put(s, s);
						}
					}
					specVos.add(new SpecVo(obj.getProductId(), specId, obj.getProductSpecItemId()));
				});
				List<String> specificationIds = new ArrayList(map.keySet());
				List<Specification> specifications = specificationService.findByIds(String.join(",", specificationIds));
				StringBuffer buf = new StringBuffer();
				//按顺序对应id添加规格值   黑色/xl    白色/xxl/
				specVos.forEach(obi -> {
					obi.getSpecId().forEach(specId -> {
						specifications.forEach(obj -> {
							if (specId.equals(obj.getSpecificationId())) {
								buf.append(obj.getName() + "/");
							}
						});
					});
					obi.setSpecValue(buf.substring(0, buf.length() - 1));
					buf.delete(0, buf.length());
					specItems.forEach(specItem -> {
						if (specItem.getProductId().equals(obi.getProductId())) {
							obi.setStock(specItem.getStock());
							obi.setPrice(new BigDecimal(specItem.getPrice()));
						}
					});
				});
				//封装规格信息到带规格的商品
				pitchonProducts.forEach(obi -> {
					if (obi.getSpecType().equals(false)) {
						List<SpecVo> vos = new LinkedList<>();
						specVos.forEach(obj -> {
							if (obj.getProductId().equals(obi.getProductId())) {
								List<BigDecimal> agentPice = new ArrayList<>();
								agentGrades.forEach(aget -> {
									BigDecimal v = aget.getGradeDiscount().divide(new BigDecimal(10.0));
									agentPice.add(v.multiply(obj.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
								});
								obj.setAgentPrice(agentPice);
								vos.add(obj);
							}
						});
						obi.setSpecVos(vos);
					}
				});
			}
			return ResultGenerator.genSuccessResult(pitchonProducts);
		}
		return ResultGenerator.genFailResult("参数错误");
	}


	/****************************************************************/
	public boolean compare(List<CategoryProductVo> listCategoryVo, Long categoryId) {

		if (!listCategoryVo.isEmpty()) {
			//查看是否有一级分类
			List<Long> categoryIds = listCategoryVo.stream().map(CategoryProductVo::getCategoryId)
					.collect(Collectors.toList());
			if (categoryIds.contains(categoryId)) {
				return true;
			}
			//查看是否有二级分类
			for (CategoryProductVo categoryVo : listCategoryVo) {
				List<CategoryProductVo> subCategorylist = categoryVo.getSubCategoryList();
				if (!subCategorylist.isEmpty()) {
					List<Long> subCategoryIds = subCategorylist.stream().map(CategoryProductVo::getCategoryId)
							.collect(Collectors.toList());
					if (subCategoryIds.contains(categoryId)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
