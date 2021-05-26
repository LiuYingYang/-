package com.medusa.basemall.product.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.product.dao.ProductSpecItemMapper;
import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.product.entity.ProductSpecItem;
import com.medusa.basemall.product.service.ProductService;
import com.medusa.basemall.product.service.ProductSpecItemService;
import com.medusa.basemall.product.vo.SpecItemUpdateVo;
import com.medusa.basemall.promotion.entity.ActivityProduct;
import com.medusa.basemall.promotion.entity.ActivityProductStock;
import com.medusa.basemall.promotion.service.ActivityProductService;
import com.medusa.basemall.promotion.service.ActivityProductStockService;
import com.medusa.basemall.utils.IdGenerateUtils;
import com.vip.vjtools.vjkit.mapper.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


/**
 * Created by medusa on 2018/05/23.
 * 需要事物时添加  @Transactional
 */

@Service
@Transactional
public class ProductSpecItemServiceImpl extends AbstractService<ProductSpecItem> implements ProductSpecItemService {

	@Autowired
	private ProductSpecItemMapper productSpecItemMapper;
	@Autowired
	private ActivityProductStockService activityProductStockService;
	@Autowired
	private ActivityProductService activityProductService;
	@Autowired
	private ProductService productService;

	@Override
	public int saveProductSpecItem(List<ProductSpecItem> productSpecItemList, Long productId, String appmodelId) {
		AtomicInteger rs = new AtomicInteger();
		if (!productSpecItemList.isEmpty()) {
			productSpecItemList.forEach(productSpecItem -> {
				productSpecItem.setAppmodelId(appmodelId);
				if (null == productSpecItem.getProductSpecItemId() || productSpecItem.getProductSpecItemId() < 1) {
					productSpecItem.setProductSpecItemId(IdGenerateUtils.getItemId());
				}
				productSpecItem.setProductId(productId);
				rs.addAndGet(productSpecItemMapper.insertSelective(productSpecItem));
			});
		}
		return rs.get();
	}


	@Override
	public int updateProductSpecItem(List<ProductSpecItem> productSpecItemList, long productId, String appmodelId) {
		int rs = 0;
		//先找出不删除只做修改的
		List<ProductSpecItem> specItems = findByProductId(productId);
		List<ProductSpecItem> notdelete = new ArrayList<>();
		if (specItems.size() >= 0) {
			for (ProductSpecItem specItem : specItems) {
				for (ProductSpecItem productSpecItem : productSpecItemList) {
					if (specItem.getSpecificationValue().equals(productSpecItem.getSpecificationValue())) {
						notdelete.add(productSpecItem);
					}
				}
			}
		}
		Map<String, String> old = specItems.stream().collect(
				Collectors.toMap(ProductSpecItem::getSpecificationValue, ProductSpecItem::getSpecificationName));
		//找出新增的
		List<ProductSpecItem> newSpecItem = new ArrayList<>();
		for (ProductSpecItem specItem : productSpecItemList) {
			String newItem = old.get(specItem.getSpecificationValue());
			if (newItem == null) {
				newSpecItem.add(specItem);
			}
		}

		//删除所有
		ProductSpecItem productSpecItem = new ProductSpecItem();
		productSpecItem.setProductId(productId);
		rs += productSpecItemMapper.delete(productSpecItem);

		//添加规格项
		newSpecItem.addAll(notdelete);
		saveProductSpecItem(newSpecItem, productId, appmodelId);

		//更新商品总库存
		int sum = newSpecItem.stream().mapToInt(ProductSpecItem::getStock).sum();
		Product product = productService.findById(productId);
		if (product.getStock() < sum) {
			product.setStock(sum);
			productService.update(product);
		}
		if (specItems.size() > 0) {
			//查询活动商品多规格库存中是否有单前商品的某个规格
			//不为空 则是存在活动商品规格库存
			List<ActivityProductStock> activityProductStocks = activityProductStockService
					.findByProductSpecItemId(specItems.get(0).getProductSpecItemId());
			if (activityProductStocks != null && activityProductStocks.size() > 0) {
				Long activityProductId = activityProductStocks.get(0).getActivityProductId();
				Map<Long, String> newItem = newSpecItem.stream().collect(Collectors
						.toMap(ProductSpecItem::getProductSpecItemId, ProductSpecItem::getSpecificationValue));
				for (ActivityProductStock productStock : activityProductStocks) {
					String value = newItem.get(productStock.getProductSpecItemId());
					if (value == null) {
						//删除已删除的某个规格项id
						activityProductStockService.deleteById(productStock.getActivityProductStockId());
					}
				}
				Map<Long, Long> odlActivityStock = activityProductStocks.stream().collect(Collectors
						.toMap(ActivityProductStock::getProductSpecItemId, ActivityProductStock::getActivityProductId));
				//添加新增的规格规格项,但库存为0
				List<ActivityProductStock> newActivityProductStocks = new ArrayList<>();
				for (ProductSpecItem specItem : newSpecItem) {
					Long temp = odlActivityStock.get(specItem.getProductSpecItemId());
					//不存在则新增
					if (temp == null || temp < 1) {
						ActivityProductStock productStock = new ActivityProductStock();
						productStock.setActivityStock(0);
						productStock.setProductSpecItemId(specItem.getProductSpecItemId());
						productStock.setActivityProductId(activityProductId);
						newActivityProductStocks.add(productStock);
					}
				}
				if (newActivityProductStocks.size() > 0) {
					activityProductStockService.save(newActivityProductStocks);
				}
				//更新活动商品总库存
				int sumStock = activityProductStockService
						.findByList("activityProductId",activityProductId).stream()
						.mapToInt(ActivityProductStock::getActivityStock).sum();
				ActivityProduct activityProduct = activityProductService
						.findById(activityProductId);
				if (activityProduct.getActivityStock() < sumStock) {
					activityProduct.setActivityStock(sumStock);
					activityProductService.update(activityProduct);
				}
			}
		}


		return rs;
	}

	@Override
	public List<ProductSpecItem> findByProductId(Long productId) {
		ProductSpecItem productSpecItem = new ProductSpecItem();
		productSpecItem.setProductId(productId);
		return productSpecItemMapper.select(productSpecItem);
	}

	@Override
	public Integer updateSpecStockOrPrice(List<SpecItemUpdateVo> specItemUpdateVos) {
		if (specItemUpdateVos.size() > 0) {
			List<ProductSpecItem> specItems = BeanMapper.mapList(specItemUpdateVos, ProductSpecItem.class);
			for (ProductSpecItem specItem : specItems) {
				productSpecItemMapper.updateByPrimaryKeySelective(specItem);
			}
			specItems.sort(Comparator.comparing(ProductSpecItem::getPrice));
			//把多个规格的最小价格的商品设置为商品的price
			ProductSpecItem productSpecItem = specItems.get(0);
			Product product = new Product();
			product.setProductId(productSpecItem.getProductId());
			product.setPrice(productSpecItem.getPrice());
			productService.update(product);
			return 1;
		}
		return 0;
	}

	@Override
	public Integer updateSpecProductStockReturn(Map<Long, Integer> map) {
		return productSpecItemMapper.updateSpecProductStockReturn(map);
	}

}
