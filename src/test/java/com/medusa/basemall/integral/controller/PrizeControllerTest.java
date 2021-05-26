package com.medusa.basemall.integral.controller;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.integral.entity.Prize;
import com.medusa.basemall.integral.service.PrizeService;
import com.medusa.basemall.promotion.entity.Coupon;
import com.medusa.basemall.promotion.service.CouponService;
import com.medusa.basemall.utiles.Constant;
import com.medusa.basemall.utiles.MockMvcUtil;
import com.vip.vjtools.vjkit.collection.ListUtil;
import com.vip.vjtools.vjkit.number.RandomUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import tk.mybatis.mapper.entity.Condition;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Transactional
public class PrizeControllerTest extends BasemallApplicationTests {

	@Autowired
	PrizeService prizeService;

	private static final Integer INIT_PRODUCT_NUMBER = 5;

	@Before
	public void before() {
		for (int i = 0; i < INIT_PRODUCT_NUMBER; i++) {
			addProductTest();
		}
	}

	@Autowired
	CouponService couponService;

	private Prize createIntegrateProduct() {
		Prize prize = new Prize();
		prize.setPrizeType(1);
		prize.setPrizeName("优惠卷" + RandomUtil.randomAsciiFixLength(5));
		prize.setPrizeName("商品" + RandomUtil.randomAsciiFixLength(5));
		prize.setSendPlace("发货地址" + RandomUtil.randomAsciiFixLength(5));
		prize.setSendDate("发货日期" + RandomUtil.randomAsciiFixLength(5));
		prize.setPrizeImg(Constant.imgUrl);
		prize.setRemark("描述" + RandomUtil.randomAsciiFixLength(30));
		prize.setPrice(new BigDecimal(RandomUtil.nextDouble(1000)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		prize.setConvertPrice(RandomUtil.nextInt(1000, 3000));
		prize.setPrizeStock(RandomUtil.nextInt(100, 1000));
		prize.setState(RandomUtil.nextInt(1, 3));
		prize.setAppmodelId(Constant.appmodelIdy);
		return prize;
	}

	/**
	 * 添加积分商品测试
	 */
	@Test
	@Ignore
	public void addProductTest() {
		Prize p = createIntegrateProduct();
		MockMvcUtil.sendRequest("/prize/addProduct/v1", JSONObject.toJSONString(p), null, "post");
		Prize prize = prizeService.findBy("remark", p.getRemark());
		Assert.assertNotNull("积分商品未添加成功", prize);
	}

	/**
	 *
	 * @param number
	 * @param prizeType  积分商品类型  奖品类型：1.商品2.优惠卷
	 * @return
	 */
	private List<Prize> getIntegrateProduct(int number, int prizeType) {
		Condition condition = new Condition(Prize.class);
		condition.createCriteria().andEqualTo("appmodelId", Constant.appmodelIdy).andEqualTo("prizeType", prizeType);
		condition.orderBy("createTime").desc();
		List<Prize> prizes = prizeService.findByCondition(condition);
		ListUtil.shuffle(prizes);
		return prizes.stream().limit(number).collect(Collectors.toList());
	}

	/**
	 * 更新积分商品测试
	 */
	@Test
	public void updateProductTest() {
		List<Prize> integrateProduct = getIntegrateProduct(1, 1);
		Prize prize = integrateProduct.get(0);
		prize.setPrizeName(RandomUtil.randomAsciiFixLength(20));
		MockMvcUtil.sendRequest("/prize/updateProduct/v1", JSONObject.toJSONString(prize), null, "put");
		Prize p = prizeService.findById(prize.getPrizeId());
		Assert.assertEquals("积分商品未正确更新", prize.getPrizeName(), p.getPrizeName());
	}

	/**
	 * 批量删除积分商品测试
	 */
	@Test
	public void batchDeleteTest() {
		List<Prize> integrateProduct = getIntegrateProduct(3, 1);
		String prizeIds = integrateProduct.stream().map(obj -> obj.getPrizeId().toString())
				.collect(Collectors.joining(","));
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("prizeIds", prizeIds);
		JSONObject post = MockMvcUtil.sendRequest("/prize/batchDelete/v1", "", map, "delete");
		List<Prize> byIds = prizeService.findByIds(prizeIds);
		for (Prize prize : byIds) {
			Assert.assertEquals("未正确删除积分商品", true, prize.getDeleteState());
		}
	}

	/**
	 * 批量修改上下架状态
	 * 1.上架  2.仓库中 3.已售完
	 */
	@Test
	public void batchUpdateStateTest() {
		List<Prize> integrateProduct = getIntegrateProduct(3, 1);
		String prizeIds = integrateProduct.stream().map(obj -> obj.getPrizeId().toString())
				.collect(Collectors.joining(","));
		Map<String, Object> object = new HashMap<>();
		object.put("prizeIds", prizeIds);
		Integer state = RandomUtil.nextInt(1, 3);
		object.put("state", state);
		JSONObject post = MockMvcUtil
				.sendRequest("/prize/batchUpdateState/v1", JSONObject.toJSONString(object), null, "put");
		List<Prize> prizes = prizeService.findByIds(prizeIds);
		prizes.forEach(obj -> Assert.assertEquals("积分商品未正确上下架", state, obj.getState()));

	}

	/**
	 * 添加积分商城优惠券
	 */
	@Test
	public void addCouponTest() {
		List<Coupon> coupons = new ArrayList<>();
		Coupon coupon = new Coupon();
		coupon.setSourceType(2);
		coupon.setStockQuantity(50);

		coupon.setCouponType(3);
		coupon.setMinPrice(10.0);
		coupon.setLiveTime(0);

		coupon.setConvertPrice(50);
		coupon.setAppmodelId(Constant.appmodelId);

		Coupon couponTwo = new Coupon();
		couponTwo.setSourceType(2);
		couponTwo.setStockQuantity(50);

		couponTwo.setCouponType(3);
		couponTwo.setMinPrice(10.0);
		couponTwo.setLiveTime(0);

		couponTwo.setConvertPrice(50);
		couponTwo.setAppmodelId(Constant.appmodelId);

		coupons.add(coupon);
		coupons.add(couponTwo);

		JSONObject post = MockMvcUtil.sendRequest("/prize/addCoupon", JSONObject.toJSONString(coupons), null, "post");
	}

	/**
	 * 更新积分商城优惠券
	 */
	@Test
	public void updateCouponTest() {
		Coupon coupon = couponService.findById(59);
		coupon.setStockQuantity(100);
		JSONObject post = MockMvcUtil.sendRequest("/prize/updateCoupon", JSONObject.toJSONString(coupon), null, "post");
	}

	/**
	 * 查询积分商品
	 */
	@Test
	public void selectPrizeProductTest() {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("appmodelId",Constant.appmodelIdy);
		params.add("state",RandomUtil.nextInt(1,3)+"");
		params.add("pageNum","1");
		params.add("pageSize","20");
		params.add("prizeType","2");
		JSONObject jsonObject = MockMvcUtil
				.sendRequest("/prize/selectPrizeProduct/v1", "", params, "get");
		Assert.assertEquals("积分商品查询失败" + jsonObject.getString("message"), new Integer(100), jsonObject.getInteger("code"));
	}
}
