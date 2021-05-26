package com.medusa.basemall.product;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.agent.entity.AgentGrade;
import com.medusa.basemall.agent.service.AgentGradeService;
import com.medusa.basemall.agent.vo.PitchonProduct;
import com.medusa.basemall.agent.vo.SpecVo;
import com.medusa.basemall.order.entity.Buycar;
import com.medusa.basemall.order.service.BuycarService;
import com.medusa.basemall.product.entity.*;
import com.medusa.basemall.product.service.*;
import com.medusa.basemall.product.vo.*;
import com.medusa.basemall.user.entity.Collect;
import com.medusa.basemall.user.entity.FootMark;
import com.medusa.basemall.user.service.CollectService;
import com.medusa.basemall.user.service.FootMarkService;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import com.vip.vjtools.vjkit.collection.ListUtil;
import com.vip.vjtools.vjkit.number.RandomUtil;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@Transactional
public class ProductContorllerTest extends BasemallApplicationTests {

	@Resource
	private BuycarService buycarService;
	@Resource
	private CollectService collectService;
	@Resource
	private ProductService productService;
	@Resource
	private FootMarkService footMarkService;
	@Resource
	private AgentGradeService agentGradeService;
	@Resource
	private LogisticModelService logisticModelService;
	@Resource
	private LogisticCancelService logisticCancelService;
	@Resource
	LogisticDistrobutionService logisticDistrobutionService;
	@Resource
	private SpecificationService specificationService;
	@Resource
	private ParameterService parameterService;
	@Resource
	private CategoryService categoryService;
	@Resource
	private ProductCategoryService productCategoryService;

	private static final Integer INIT_PRODUCT_NUMBER = 5;

	@Before
	public void before() {
		for (int i = 0; i < INIT_PRODUCT_NUMBER; i++) {
			createProduct();
		}
	}

	/**
	 * 商品上下架(单个或多个)
	 * 下架相关状态
	 *     1-shelfState = 1     上下状态(默认上架，0--上架，1--下架（仓库中），2--已售完)
	 *     2-购物车、收藏、足迹  状态需要与商品状态相同
	 *     3-查询仓库中的商品时含有该商品
	 *     4-查询出售中的商品是不包含该商品
	 *     5-如果是活动商品,活动商品中需要删除该商品再次上架之后不在属于活动商品
	 * 再次上架相关状态
	 *     与下架相关状态前4条相反
	 */
	@Test
	public void batchUpdateShelfStateTest() throws InterruptedException {
		List<DeleteProduct> deleteProducts = new ArrayList<>();
		for (Product product : getProduct(3, null)) {
			DeleteProduct deleteProduct = new DeleteProduct();
			deleteProduct.setProductId(product.getProductId());
			deleteProduct.setShelfState(product.getShelfState());
			deleteProducts.add(deleteProduct);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("deleteProduct", deleteProducts);
		MockMvcUtil.sendRequest("/Product/batchUpdateShelfState/v1", JSON.toJSONString(map), null, "put");
		//断言商品实时是否正确修改
		List<Long> productIdList = deleteProducts.stream().map(DeleteProduct::getProductId)
				.collect(Collectors.toList());
		String productIds = StringUtils.join(productIdList, ",");
		List<Product> products = productService.findByIds(productIds);

		for (DeleteProduct product : deleteProducts) {
			products.forEach(obj -> {
				if (obj.getProductId().equals(product.getProductId())) {
					assertEquals("商品上下架状态未正确修改", obj.getShelfState(), product.getShelfState());
				}
			});
		}
		List<Buycar> buycars = buycarService.findByProductId(productIdList);
		buycars.forEach(buycar -> {
			deleteProducts.forEach(dProduct -> {
				if (buycar.getProductId().equals(dProduct.getProductId())) {
					assertEquals("商品上下架-购物车中状态未正确修改", buycar.getShelfState(), dProduct.getShelfState());
				}
			});
		});
		List<Collect> collects = collectService.findByProductId(productIdList);
		collects.forEach(collect -> {
			deleteProducts.forEach(dProduct -> {
				if (collect.getProductId().equals(dProduct.getProductId())) {
					assertEquals("商品上下架-收藏中状态未正确修改", collect.getState(), dProduct.getShelfState());
				}
			});
		});
		List<FootMark> footMarks = footMarkService.findByProductId(productIdList);
		footMarks.forEach(footMark -> {
			deleteProducts.forEach(dProduct -> {
				if (footMark.getProductId().equals(dProduct.getProductId())) {
					assertEquals("商品上下架-足迹中状态未正确修改", footMark.getState(), dProduct.getShelfState());
				}
			});
		});
	}

	/**
	 *  查询代理商品
	 */
	@Test
	public void findProducttoAgent() {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("appmodelId", Constant.appmodelIdy);
		params.add("pageNum", "1");
		params.add("pageSize", "10");
		MockMvcUtil.sendRequest("/Product/findProducttoAgent/v1", "", params, "get");
	}

	/**
	 * 查询选中的商品
	 *      -选中的商品不能为空
	 *      -图片以及商品名称不能为空
	 *      -多规格商品规格项不能为空
	 */
	@Test
	public void pitchon() {
		Condition condition = new Condition(Product.class);
		condition.createCriteria().andEqualTo("appmodelId", Constant.appmodelIdy).andEqualTo("shelfState", 0);
		List<Product> list = productService.findByCondition(condition);
		ListUtil.shuffle(list);
		List<Long> productIdlist = list.stream().map(Product::getProductId).collect(Collectors.toList());
		int i = 0;
		StringBuilder productIds = new StringBuilder();
		for (Long id : productIdlist) {
			if (i == 3) {
				break;
			}
			i++;
			productIds.append(id + ",");
		}
		MultiValueMap multiValueMap = new LinkedMultiValueMap();
		multiValueMap.add("productIds", productIds.toString().substring(0, productIds.length() - 1));
		//multiValueMap.add("productIds", "1536133191360407");
		multiValueMap.add("appmodelId", Constant.appmodelIdy);
		JSONObject post = MockMvcUtil.sendRequest("/Product/pitchon/v1", "", multiValueMap, "get");
		List<PitchonProduct> data = JSON.parseArray(post.getString("data"), PitchonProduct.class);
		List<AgentGrade> agentGrades = agentGradeService.findByAppmodelId(Constant.appmodelIdy);
		data.forEach(obj -> {
			assertThat("商品id为空", obj.getProductId(), notNullValue());
			assertThat("商品名为空", obj.getProductName(), notNullValue());
			//代理价格已计算完成, 并且计算正确
			if (obj.getSpecType().equals(false)) {
				assertThat("多规格商品规格项为空", obj.getSpecVos(), notNullValue());
				for (SpecVo specVo : obj.getSpecVos()) {
					assertThat("多规格商品规格项id为空", specVo.getSpecItemId(), notNullValue());
					assertThat("多规格商品代理价格为空", specVo.getAgentPrice(), notNullValue());

				}
			}
			if (obj.getSpecType().equals(true)) {
				assertThat("代理价格为空", obj.getAgentPrice(), notNullValue());
				agentGrades.forEach(grade -> {
					BigDecimal multiply = obj.getPrice().multiply(grade.getGradeDiscount().divide(new BigDecimal(10)))
							.setScale(2, BigDecimal.ROUND_HALF_UP);
					assertThat("代理价格计算出错", multiply,
							anyOf(is(obj.getAgentPrice().get(0)), is(obj.getAgentPrice().get(1))));
				});
			}
		});
	}

	/**
	 * 创建商品
	 *      -根据商品标题和买点描述查询出该商品
	 *      -与创建时添加的内容相同.
	 */
	private ProductEditVo createProductEditVo() {
		ProductEditVo productEditVo = new ProductEditVo();
		productEditVo.setAppmodelId(Constant.appmodelIdy);
		int i = RandomUtil.nextInt(1, 2);
		//物流和商家配送必须要有一个
		if (i == 2) {
			List<LogisticModel> logisticModels = logisticModelService.findModelByAppmodelId(Constant.appmodelIdy);
			ListUtil.shuffle(logisticModels);
			productEditVo.setLogisticModelId(logisticModels.get(0).getLogisticModelId());
		}
		if (i == 1) {
			LogisticDistrobution distrobution = logisticDistrobutionService.findDistrobution(Constant.appmodelIdy);
			if (distrobution.getTurnState().equals(false)) {
				List<LogisticModel> logisticModels = logisticModelService.findModelByAppmodelId(Constant.appmodelIdy);
				ListUtil.shuffle(logisticModels);
				productEditVo.setLogisticModelId(logisticModels.get(0).getLogisticModelId());
			}else{
				productEditVo.setLogisticModelId(0);
			}
		}
		productEditVo.setDeliveryFees(0.0);
		productEditVo.setSalesVolume(0);
		productEditVo.setTextImg("<p>" + RandomUtil.randomAsciiFixLength(RandomUtil.nextInt(10)) + "</p>");
		productEditVo.setProductImg(Constant.imgUrl);
		productEditVo.setShelfState(RandomUtil.nextInt(1));
		productEditVo.setSendPlace(RandomUtil.randomAsciiFixLength(RandomUtil.nextInt(1, 10)));
		productEditVo.setSendDate(RandomUtil.randomAsciiFixLength(RandomUtil.nextInt(1, 10)));
		productEditVo.setRemark(RandomUtil.randomAsciiFixLength(RandomUtil.nextInt(1, 10)));
		productEditVo.setProductName(RandomUtil.randomAsciiFixLength(RandomUtil.nextInt(1, 50)));
		productEditVo.setDeleteState(false);
		productEditVo.setPrice(new BigDecimal(RandomUtil.nextDouble(1, 1000)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		double marketPrice = RandomUtil
				.nextDouble(productEditVo.getPrice(), productEditVo.getPrice() + RandomUtil.nextDouble(1000));
		productEditVo.setMarketPrice(BigDecimal.valueOf(marketPrice).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		productEditVo.setStock(RandomUtil.nextInt(1, 3000));

		List<String> serviceAssuranceList = new ArrayList<>();
		serviceAssuranceList.add("{'name':'全场包邮','text':'所有商品均无条件包邮','state':" + RandomUtils.nextBoolean() + "'}");
		serviceAssuranceList.add("{'name':'7天退换','text':'商家承诺7天无理由退换货','state':" + RandomUtils.nextBoolean() + "'}");
		serviceAssuranceList
				.add("{'name':'48小时发货','text':'商家承诺订单在48小时内发货','state':" + RandomUtils.nextBoolean() + "'}");
		serviceAssuranceList
				.add("{'name':'假一赔十','text':'若收到商品是假冒品牌，可获得十倍赔偿','state':" + RandomUtils.nextBoolean() + "'}");
		serviceAssuranceList.add("{'name':'正品保证','text':'商家承诺商品正品质量','state':" + RandomUtils.nextBoolean() + "'}");
		productEditVo.setServiceAssuranceList(serviceAssuranceList);
		//多规格商品添加规格
		productEditVo.setSpecType(RandomUtils.nextBoolean());
		if (productEditVo.getSpecType() == false) {
			List<SpecificationVo> specificationVos = specificationService.findByAppmodelId(Constant.appmodelIdy);
			ListUtil.shuffle(specificationVos);
			int temp = 0;
			//设置规格分类
			List<ProductSpecClass> specClasses = new ArrayList<>();
			for (SpecificationVo specificationVo : specificationVos) {
				if (temp == 2) {
					break;
				}
				temp++;
				ProductSpecClass productSpecClass = new ProductSpecClass();
				productSpecClass.setSpecificationClassId(specificationVo.getSpecificationClassId());
				specClasses.add(productSpecClass);
			}
			//设置规格分类
			productEditVo.setProductSpecClassList(specClasses);
			//设置规格值
			List<ProductSpecItem> specItems = new ArrayList<>();
			List<ProductSpec> productSpecList = new ArrayList<>();
			Integer stock = 0;
			for (SpecificationVo specificationVo : specificationVos) {
				for (ProductSpecClass productSpecClass : productEditVo.getProductSpecClassList()) {
					if (specificationVo.getSpecificationClassId().equals(productSpecClass.getSpecificationClassId())) {

						List<Specification> specificationList = specificationVo.getSpecificationList();
						for (Specification specification : specificationList) {
							ProductSpec productSpec = new ProductSpec();
							productSpec.setSpecificationClassId(specification.getSpecificationClassId());
							productSpec.setSpecificationId(specification.getSpecificationId());
							productSpecList.add(productSpec);

							ProductSpecItem specItem = new ProductSpecItem();
							specItem.setPrice(RandomUtil.nextDouble(2000));
							specItem.setStock(RandomUtil.nextInt(1000));
							stock += specItem.getStock();
							if (specItem.getSpecificationName() != null && !specItem.getSpecificationName()
									.equals("")) {
								StringBuilder name = new StringBuilder(specItem.getSpecificationName());
								StringBuilder value = new StringBuilder(specItem.getSpecificationValue());
								specItem.setSpecificationName(name.append(specification.getName() + ",").toString());
								specItem.setSpecificationValue(
										value.append(specification.getSpecificationId() + ",").toString());
							} else {
								specItem.setSpecificationName("");
								specItem.setSpecificationValue("");
								specItem.setSpecificationName(specification.getName() + ",");
								specItem.setSpecificationValue(specification.getSpecificationId() + ",");
							}
							specItems.add(specItem);
						}
					}
				}
			}
			//商品规格下的规格值
			productEditVo.setProductSpecList(productSpecList);
			//设置规格项
			productEditVo.setProductSpecItemList(specItems);
			productEditVo.setStock(stock);
		}
		//设置商品轮播图
		List<String> rimgurlList = new ArrayList<>();
		if (RandomUtils.nextBoolean()) {
			rimgurlList.add(Constant.imgUrl);
			rimgurlList.add(Constant.imgUrl);
			rimgurlList.add(Constant.imgUrl);
		}
		productEditVo.setRimgurlList(rimgurlList);
		//设置商品属性模板
		if (RandomUtils.nextBoolean()) {
			List<ParameterVo> parameterVos = parameterService.findByAppmodelId(Constant.appmodelIdy);
			ListUtil.shuffle(parameterVos);
			List<Parameter> paramValueList = new ArrayList<>();
			for (Parameter parameter : parameterVos.get(0).getParameterList()) {
				paramValueList.add(parameter);
			}
			productEditVo.setParamValueList(paramValueList);
		}
		List<Long> CategoryIds = getCategoryIds();
		productEditVo.setCategoryIds(CategoryIds.stream().distinct().collect(Collectors.toList()));
		return productEditVo;
	}

	/**
	 * 获取分类
	 * @return
	 */
	private List<Long> getCategoryIds() {
		List<CategoryVo> firstAndSecond = categoryService.findFirstAndSecond(Constant.appmodelIdy);
		ListUtil.shuffle(firstAndSecond);
		List<Long> CategoryIds = new ArrayList<>();
		for (CategoryVo categoryVo : firstAndSecond) {
			if (RandomUtils.nextBoolean()) {
				CategoryIds.add(categoryVo.getCategoryId());
				int temp = 0;
				for (Category category : categoryVo.getSubCategoryList()) {
					if (temp == 2) {break;}
					temp++;
					CategoryIds.add(category.getCategoryId());
				}
			}
		}
		if (CategoryIds.size() == 0) {
			CategoryIds.add(firstAndSecond.get(0).getCategoryId());
			CategoryIds.add(firstAndSecond.get(0).getSubCategoryList().get(0).getCategoryId());
		}
		return CategoryIds;
	}


	@Test
	@Ignore
	public void createProduct() {
		/*int len = 1;
		List<ProductEditVo> productEditVo = new ArrayList<>();
		for (int i = 0; i < len; i++) {
			productEditVo.add(createProductEditVo());
		}
		for (ProductEditVo editVo : productEditVo) {
		}*/
		ProductEditVo productEditVo = createProductEditVo();
		JSONObject post = MockMvcUtil.sendRequest("/Product/save/v1", JSON.toJSONString(productEditVo), null, "post");
		Product product = productService.findBy("productName", productEditVo.getProductName());
		assertThat("添加失败,数据库中无此数据", product, notNullValue());
	}


	/**
	 *
	 * @param number  获取多个商品
	 * @param type  null-混合 1-统一规格 2-多规格
	 * @return
	 */
	private List<Product> getProduct(int number, Integer type) {

		List<Product> list = new ArrayList<>();
		while (list.size() == 0) {
			Integer shelfState = RandomUtil.nextInt(0, 2);
			Condition condition = new Condition(Product.class);
			condition.createCriteria().andEqualTo("appmodelId", Constant.appmodelIdy)
					.andEqualTo("shelfState", shelfState).andEqualTo("deleteState", false);
			condition.orderBy("createTime").desc();
			list = productService.findByCondition(condition);
		}
		List<Product> products = list.stream().limit(INIT_PRODUCT_NUMBER).collect(Collectors.toList());
		if (type != null) {
			if (type.equals(1)) {
				products = products.stream().filter(obj -> obj.getSpecType().equals(true)).collect(Collectors.toList());
			} else {
				products = products.stream().filter(obj -> obj.getSpecType().equals(false))
						.collect(Collectors.toList());
			}
		}
		ListUtil.shuffle(products);
		return products.stream().limit(number).collect(Collectors.toList());
	}

	/**
	 * 删除单个或多个商品
	 *      -商品delete状态变为1
	 *      -购物车、收藏、足迹变为下架商品
	 *      -活动商品,从活动中删除该商品
	 */
	@Test
	public void batchDelete() {
		List<DeleteProduct> deleteProducts = new ArrayList<>();
		for (Product product : getProduct(3, null)) {
			DeleteProduct deleteProduct = new DeleteProduct();
			deleteProduct.setProductId(product.getProductId());
			deleteProduct.setShelfState(product.getShelfState());
			deleteProducts.add(deleteProduct);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("deleteProduct", deleteProducts);
		JSONObject post = MockMvcUtil.sendRequest("/Product/batchDelete/v1", JSON.toJSONString(map), null, "put");
		//断言商品实时是否正确修改
		List<Long> productIdList = deleteProducts.stream().map(DeleteProduct::getProductId)
				.collect(Collectors.toList());
		String productIds = StringUtils.join(productIdList, ",");
		List<Product> products = productService.findByIds(productIds);
		for (DeleteProduct product : deleteProducts) {
			products.forEach(obj -> {
				if (obj.getProductId().equals(product.getProductId())) {
					assertEquals("商品delete状态未正确修改为1", obj.getDeleteState(), true);
				}
			});
		}
		List<Buycar> buycars = buycarService.findByProductId(productIdList);
		buycars.forEach(buycar -> {
			deleteProducts.forEach(dProduct -> {
				if (buycar.getProductId().equals(dProduct.getProductId())) {
					assertEquals("商品下架-购物车中状态未修改为下架状态", buycar.getShelfState(), dProduct.getShelfState());
				}
			});
		});
		List<Collect> collects = collectService.findByProductId(productIdList);
		collects.forEach(collect -> {
			deleteProducts.forEach(dProduct -> {
				if (collect.getProductId().equals(dProduct.getProductId())) {
					assertEquals("商品下架-收藏中状态未修改为下架状态", collect.getState(), new Integer(1));
				}
			});
		});

		List<FootMark> footMarks = footMarkService.findByProductId(productIdList);
		footMarks.forEach(footMark -> {
			deleteProducts.forEach(dProduct -> {
				if (footMark.getProductId().equals(dProduct.getProductId())) {
					assertEquals("商品下架-足迹中状态状态未修改为下架状态", footMark.getState(), new Integer(1));
				}
			});
		});
	}

	/**
	 * 更新商品时查询商品
	 */
	@Test
	public void findProductForUpdate() {
		Product product = getProduct(1, null).get(0);
		getProductForUpdate(product);
	}

	private ProductEditVo getProductForUpdate(Product product) {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("productId", product.getProductId().toString());
		JSONObject jsonObject = MockMvcUtil.sendRequest("/Product/findProductForUpdate/v1", "", map, "get");
		ProductEditVo data = JSON.parseObject(jsonObject.getString("data"), ProductEditVo.class);
		return data;
	}

	/**
	 *更新统一规格商品总价格和总库存的更新
	 *      -总库存为0
	 *          -商品变为已售罄商品  状态改为2  购物车、收藏、足迹 需要跟商品相同的状态
	 *      -总库存从0变为>1
	 *          -商品变为上架商品 状态改为0  购物车、收藏、足迹 需要跟商品相同的状态
	 *      -同时修改价格和商品 code 99
	 */
	@Test
	public void updateOthers() {
		List<Product> productList = getProduct(1, 1);
		Product product = new Product();
		product.setProductId(productList.get(0).getProductId());
		//修改库存 为 0
		updateOthers1(product);
		//修改库存 从0变为>1
		updateOthers2(product);
		//修改库存和价格
		updateOthers3(product);
		//修改价格
		updateOthers4(product);

	}

	private void updateOthers4(Product product) {
		product.setStock(null);
		product.setPrice(33.2);
		MockMvcUtil.sendRequest("/Product/updateOthers/v1", JSON.toJSONString(product), null, "put");
		Product p = productService.findById(product.getProductId());
		assertEquals("统一商品修改价格-未修改正确", new Double(33.2), p.getPrice());
		List<Long> productIdList = new ArrayList<>();
		productIdList.add(product.getProductId());
		List<Buycar> buycars = buycarService.findByProductId(productIdList);
		buycars.forEach(buycar -> {
			assertEquals("商品库存修改为0-购物车中状态未正确修改", p.getShelfState(), buycar.getShelfState());
		});
		List<Collect> collects = collectService.findByProductId(productIdList);
		collects.forEach(collect -> {
			assertEquals("商品库存修改为0-收藏中状态未正确修改", p.getShelfState(), collect.getState());
		});
		List<FootMark> footMarks = footMarkService.findByProductId(productIdList);
		footMarks.forEach(footMark -> {
			assertEquals("商品库存修改为0-足迹中状态未正确修改", p.getShelfState(), footMark.getState());
		});
	}

	private void updateOthers1(Product product) {
		//todo 需要重新修改
		product.setStock(0);
		MockMvcUtil.sendRequest("/Product/updateOthers/v1", JSON.toJSONString(product), null, "put");
		Product p = productService.findById(product.getProductId());
		assertEquals("商品未修改为已售罄状态", p.getShelfState(), new Integer(2));
		List<Long> productIdList = new ArrayList<>();
		productIdList.add(product.getProductId());
		List<Buycar> buycars = buycarService.findByProductId(productIdList);
		buycars.forEach(buycar -> {
			assertEquals("商品库存修改为0-购物车中状态未正确修改", p.getShelfState(), buycar.getShelfState());
		});
		List<Collect> collects = collectService.findByProductId(productIdList);
		collects.forEach(collect -> {
			assertEquals("商品库存修改为0-收藏中状态未正确修改", p.getShelfState(), collect.getState());
		});
		List<FootMark> footMarks = footMarkService.findByProductId(productIdList);
		footMarks.forEach(footMark -> {
			assertEquals("商品库存修改为0-足迹中状态未正确修改", p.getShelfState(), footMark.getState());
		});
	}

	private void updateOthers2(Product product) {
		product.setStock(10);
		List<Long> productIdList = new ArrayList<>();
		productIdList.add(product.getProductId());
		MockMvcUtil.sendRequest("/Product/updateOthers/v1", JSON.toJSONString(product), null, "put");
		Product p2 = productService.findById(product.getProductId());
		assertEquals("商品未修改为上架状态", new Integer(0), p2.getShelfState());
		List<Buycar> buycars1 = buycarService.findByProductId(productIdList);
		buycars1.forEach(buycar -> {
			assertEquals("商品库存修改从0到>1-购物车中状态未正确修改", p2.getShelfState(), buycar.getShelfState());
		});
		List<Collect> collects1 = collectService.findByProductId(productIdList);
		collects1.forEach(collect -> {
			assertEquals("商品库存修改从0到-收藏中状态未正确修改", p2.getShelfState(), collect.getState());
		});
		List<FootMark> footMarks1 = footMarkService.findByProductId(productIdList);
		footMarks1.forEach(footMark -> {
			assertEquals("商品库存修改从0到-足迹中状态未正确修改", p2.getShelfState(), footMark.getState());
		});
	}

	private void updateOthers3(Product product) {
		product.setStock(10);
		product.setPrice(33.2D);
		JSONObject jsonObject = MockMvcUtil
				.sendRequest("/Product/updateOthers/v1", JSON.toJSONString(product), null, "put");
		assertEquals("库存和价格同时被修改", new Integer(99), jsonObject.getInteger("code"));
	}


	/**
	 * 批量设置商品分类
	 *      -
	 */
	@Test
	public void batchSetCategory() {
		List<Product> product = getProduct(3, null);
		Map<String, Object> object = new HashMap<>();
		List<Long> productIds = product.stream().map(Product::getProductId).collect(Collectors.toList());
		object.put("productIds", productIds);
		List<Long> categoryIds = getCategoryIds();
		object.put("categoryIds", categoryIds);
		object.put("appmodelId", Constant.appmodelId);
		MockMvcUtil.sendRequest("/Product/batchSetCategory/v1", JSON.toJSONString(object), null, "post");
		Map<Long, Long> c = categoryIds.stream().collect(Collectors.toMap(k -> k, v -> v));
		for (Long productId : productIds) {
			List<ProductCategory> list = productCategoryService.findByList("productId", productId);
			for (ProductCategory category : list) {
				assertNotNull("分类设置未生效", c.get(category.getCategoryId()));
			}
		}
	}


	/**
	 * 后台查询商品
	 *      -按商品状态查询  shelfState 0 -上架  1-下架  2售罄   结果数据保持一致
	 * @throws Exception
	 */
	@Test
	public void findProductForBack() {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("appmodelId", Constant.appmodelIdy);
		map.add("pageNum", "1");
		map.add("pageSize", "20");
		Integer shelfState = RandomUtil.nextInt(0, 2);
		map.add("shelfState", shelfState.toString());
		JSONObject jsonObject = MockMvcUtil.sendRequest("/Product/findProductForBack/v1", "", map, "get");
		assertEquals("查询失败 原因:" + jsonObject.getString("message"), new Integer(100), jsonObject.getInteger("code"));
		List<ProductBackViewVo> productBackViewVos = JSON
				.parseArray(jsonObject.getJSONObject("data").getString("list"), ProductBackViewVo.class);
		for (ProductBackViewVo productBackViewVo : productBackViewVos) {
			assertEquals("按商品上下售罄状态查询未正确", shelfState, productBackViewVo.getShelfState());
		}
	}

	/**
	 * 小程序端查询商品
	 *
	 */
	@Test
	@Ignore
	public void findProductForWX() {
		ProductFindRequestVo productFindRequestVo = new ProductFindRequestVo();
		productFindRequestVo.setSearchString("");
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("appmodelId", Constant.appmodelIdy);
		map.add("pageNum", "1");
		map.add("pageSize", "20");
		//升序状态 0--综合，1--销量，2--新品，3--价格"
		map.add("sortType", RandomUtil.nextInt(0, 3) + "");
		JSONObject jsonObject = MockMvcUtil
				.sendRequest("/Product/findProductForWX/v1", JSON.toJSONString(productFindRequestVo), map, "get");
		List<ProductWxViewVo> productWxViewVos = JSON
				.parseArray(jsonObject.getJSONObject("data").getString("list"), ProductWxViewVo.class);
	}

	/**
	 * 小程序端根据商品ID查询商品规格
	 */
	@Test
	public void findSpecByProductId() {
		List<Product> products = getProduct(1, 2);
		Product product = products.get(0);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("productId", product.getProductId() + "");
		map.add("appmodelId", Constant.appmodelIdy);
		JSONObject jsonObject = MockMvcUtil.sendRequest("/Product/findSpecByProductId/v1", "", map, "get");
	}

	/**
	 * 小程序端根据商品ID查询商品详情
	 */
	@Test
	@Ignore
	public void findDetailByProductId() {
		List<Product> product = getProduct(1, null);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("productId", product.get(0).getProductId() + "");
		map.add("activeInfo", "");
		JSONObject post = MockMvcUtil
				.sendRequest("/Product/findDetailByProductId/v1", JSON.toJSONString(null), map, "post");

	}

	@Test
	public void update() {
		//获取一个已创建的商品id
		Product product = getProduct(1, null).get(0);
		//获取要修改的商品
		ProductEditVo productForUpdate = getProductForUpdate(product);
		//随机创建商品
		ProductEditVo productEditVo = createProductEditVo();
		//把要修改的商品属性改为随机创建商品的属性(productId 不变)
		BeanUtils.copyProperties(productEditVo, productForUpdate);
		productForUpdate.setProductId(product.getProductId());
		MockMvcUtil.sendRequest("/Product/update/v1", JSON.toJSONString(productForUpdate), null, "post");
		//获取修改后的商品
		ProductEditVo updateNew = getProductForUpdate(product);
		assertEquals("商品id不正确",productForUpdate.getProductId(),updateNew.getProductId());
		assertEquals("商品名称不正确",productEditVo.getProductName(),updateNew.getProductName());
		assertEquals("商品详情不正确",productEditVo.getTextImg(),updateNew.getTextImg());
	}


}
