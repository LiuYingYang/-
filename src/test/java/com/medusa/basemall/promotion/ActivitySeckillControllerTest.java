package com.medusa.basemall.promotion;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.BasemallApplicationTests;
import com.medusa.basemall.integral.vo.FindProductVo;
import com.medusa.basemall.product.entity.ProductSpecItem;
import com.medusa.basemall.promotion.entity.ActivityProductStock;
import com.medusa.basemall.promotion.service.ActivitySeckillService;
import com.medusa.basemall.promotion.vo.ActivitySeckillVo;
import com.medusa.basemall.promotion.vo.JoinActityProductVO;
import com.medusa.basemall.promotion.vo.OptionalProductItems;
import com.medusa.basemall.utiles.Constant;
import com.vip.vjtools.vjkit.number.RandomUtil;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Transactional
public class ActivitySeckillControllerTest extends BasemallApplicationTests {

	@Autowired
	private ActivitySeckillService activitySeckillService;

	/**
	 * 基础查询可供秒杀选择的商品测试
	 */
	@Test
	@Ignore
	public void findProductForSeckillTest() {
		List<OptionalProductItems> product = getProduct("2018-09-20 15:24:25", "2018-11-15 00:00:00");
		Assert.notNull(product, "查询商品不能为空");
	}

	private List<OptionalProductItems> getProduct(String startTime, String entTime) {
		FindProductVo findProductVo = new FindProductVo();
		findProductVo.setAppmodelId(Constant.appmodelId);
		findProductVo.setPageNum(1);
		findProductVo.setPageSize(10);
		findProductVo.setStartDate(startTime);
		findProductVo.setEndDate(entTime);
		return activitySeckillService.findProductForSeckill(findProductVo);
	}

	/**
	 * 基础创建秒杀活动测试
	 */
	@Test
	public void save() {
		String startTime = "2018-11-15 10:27:00";
		String entTime = "2018-11-15 10:50:00";
		ActivitySeckillVo seckillVo = new ActivitySeckillVo();
		seckillVo.setStartDate(startTime);
		seckillVo.setEndDate(entTime);
		seckillVo.setNowState(1);
		seckillVo.setPreheatTime("");
		seckillVo.setOverlayState(false);
		seckillVo.setAppmodelId(Constant.appmodelId);
		seckillVo.setActivityRemark("测试");
		seckillVo.setActivitySeckillName("活动名称");

		List<OptionalProductItems> product = getProduct(startTime, entTime);
		List<JoinActityProductVO> joinActityProductVO = new ArrayList<>();
		for (int i = 0; i < product.size(); i++) {
			OptionalProductItems productWxVo = product.get(i);
			JoinActityProductVO joinActityProductVO1 = new JoinActityProductVO();
			joinActityProductVO1.setActivityDiscount(8.8);
			joinActityProductVO1.setActivityStock(productWxVo.getStock() / 2);
			joinActityProductVO1.setMaxQuantity(2);
			joinActityProductVO1.setProductId(productWxVo.getProductId());
			BigDecimal divide = new BigDecimal(joinActityProductVO1.getActivityDiscount()).divide(new BigDecimal(10))
					.setScale(2, BigDecimal.ROUND_HALF_UP);
			if (productWxVo.getSpecType().equals(false)) {
				List<ProductSpecItem> productSpecItemList = productWxVo.getProductSpecItemList();
				List<ActivityProductStock> activityProductStocks = new ArrayList<>(productSpecItemList.size());
				for (ProductSpecItem specItem : productSpecItemList) {
					ActivityProductStock productStock = new ActivityProductStock();
					productStock.setProductSpecItemId(specItem.getProductSpecItemId());
					productStock.setActivityStock(specItem.getStock() / 2);
					BigDecimal activityPrice = new BigDecimal(specItem.getPrice()).multiply(divide)
							.setScale(2, BigDecimal.ROUND_HALF_UP);
					productStock.setActivityPrice(activityPrice.doubleValue());
					activityProductStocks.add(productStock);
				}
				joinActityProductVO1.setActivityProductStocks(activityProductStocks);
			} else {
				BigDecimal activityPrice = new BigDecimal(productWxVo.getPrice()).multiply(divide)
						.setScale(2, BigDecimal.ROUND_HALF_UP);
				joinActityProductVO1.setActivityPrice(activityPrice.doubleValue());
			}
			if (i % 2 == 0) {
				joinActityProductVO1.setSort(-1);
			}else{
				joinActityProductVO1.setSort(RandomUtil.nextInt(0, 1000));
			}
			joinActityProductVO1.setHomeViewStat(RandomUtil.nextInt(1, 2));
			joinActityProductVO.add(joinActityProductVO1);
		}
		seckillVo.setJoinActityProductVO(joinActityProductVO);
		System.out.println(JSONObject.toJSONString(seckillVo));
		activitySeckillService.saveActivitySeckill(seckillVo);

	}

}
