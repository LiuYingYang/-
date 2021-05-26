package com.medusa.basemall.activemq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.constant.ActiviMqQueueName;
import com.medusa.basemall.order.entity.Buycar;
import com.medusa.basemall.order.service.BuycarService;
import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.product.service.ProductService;
import com.medusa.basemall.user.entity.Collect;
import com.medusa.basemall.user.entity.FootMark;
import com.medusa.basemall.user.service.CollectService;
import com.medusa.basemall.user.service.FootMarkService;
import com.vip.vjtools.vjkit.mapper.BeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author whh
 */
@Component
public class ProductListerner {

	@Resource
	private ProductService productService;
	@Resource
	private BuycarService buycarService;
	@Resource
	private CollectService collectService;
	@Resource
	private FootMarkService footMarkService;

	private static Logger logger = LoggerFactory.getLogger(ProductListerner.class);

	@JmsListener(destination = ActiviMqQueueName.PRODUCT_SHELFSTATE_CHANGE)
	public void productStockChange(String jsondata) {
		JSONObject jsonObject = JSON.parseObject(jsondata);
		Integer shelfState = jsonObject.getInteger("shelfState");
		List<Long> productIds = Arrays.asList(jsonObject.getString("productIds").split(",")).stream()
				.map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
		List<Product> products = productService.findByIds(jsonObject.getString("productIds"));
		Map<Long, Product> productMap = products.stream().collect(Collectors.toMap(Product::getProductId, obj -> obj));
		//如果是上架
		if (shelfState == 0) {
			for (Product product : products) {
				if (product.getStock() == 0) {
					product.setShelfState(2);
					productService.update(product);
				}
			}
		}
		Map<Long, Integer> productShelfState = products.stream()
				.collect(Collectors.toMap(k -> k.getProductId(), v -> v.getShelfState()));
		List<Object> modifyProduct = new ArrayList<>();
		//处理购物车
		List<Buycar> buycars = buycarService.findByProductId(productIds);
		if (buycars != null && buycars.size() > 0) {
			buycars.forEach(buycar -> {

				buycar.setShelfState(productShelfState.get(buycar.getProductId()));
				Product product = productMap.get(buycar.getProductId());
				buycar.setCountPrice(buycar.getQuantity() * product.getPrice());
				buycar.getProductSpecItemInfo().setPrice(product.getPrice());
				modifyProduct.add(buycar);
			});
			updateState(1, modifyProduct);
		}
		//处理收藏
		List<Collect> collects = collectService.findByProductId(productIds);
		if (collects != null && collects.size() > 0) {
			collects.forEach(collect -> {
				collect.setState(productShelfState.get(collect.getProductId()));
				collect.setMinPrice(productMap.get(collect.getProductId()).getPrice());
				modifyProduct.add(collect);
			});
			updateState(2, modifyProduct);
		}
		//处理足迹
		List<FootMark> footMarks = footMarkService.findByProductId(productIds);
		if (footMarks != null && footMarks.size() > 0) {
			footMarks.forEach(footMark -> {
				footMark.setState(productShelfState.get(footMark.getProductId()));
				footMark.setMinPrice(productMap.get(footMark.getProductId()).getPrice());
				modifyProduct.add(footMark);
			});
			updateState(3, modifyProduct);
		}
	}

	/**
	 *
	 * @param type  1 购物车 2收藏 3足迹
	 * @param list
	 */
	private void updateState(Integer type, List<Object> list) {
		if (list != null) {
			switch (type) {
				case 1:
					List<Buycar> buycars = BeanMapper.mapList(list, Buycar.class);
					buycarService.updateBurCars(buycars);
					break;
				case 2:
					List<Collect> collects = BeanMapper.mapList(list, Collect.class);
					collectService.updateCollects(collects);
					break;
				case 3:
					List<FootMark> footMarks = BeanMapper.mapList(list, FootMark.class);
					footMarkService.updateFootMarks(footMarks);
					break;
				default:
					break;
			}
			list.clear();
		}
	}

}
