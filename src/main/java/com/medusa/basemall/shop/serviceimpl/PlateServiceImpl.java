package com.medusa.basemall.shop.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.product.service.ProductService;
import com.medusa.basemall.product.vo.ProductBackViewVo;
import com.medusa.basemall.shop.dao.PlateMapper;
import com.medusa.basemall.shop.dao.PlateProductMapper;
import com.medusa.basemall.shop.entity.Plate;
import com.medusa.basemall.shop.entity.PlateProduct;
import com.medusa.basemall.shop.service.PlateService;
import com.medusa.basemall.shop.vo.PlateVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author Created by wx on 2018/05/26.
 */

@Service
public class PlateServiceImpl extends AbstractService<Plate> implements PlateService {


	@Resource
	private PlateMapper tPlateMapper;

	@Resource
	private PlateProductMapper plateProductMapper;

	@Resource
	private ProductService productService;

	@Override
	public List<Plate> findByAppmodelId(String appmodelId) {
		List<Plate> plates = tPlateMapper.findByAppmodelId(appmodelId);
		if (plates.size() > 0) {
			for (Plate plate : plates) {
				List<PlateProduct> plateProducts = plateProductMapper.seleteByPlateId(plate.getPlateId());
				if (plateProducts.size() > 0) {
					List<Long> productIds = plateProducts.stream().map(obj -> obj.getProductId())
							.collect(Collectors.toList());
					List<ProductBackViewVo> plateProductIds = productService.findPlateProductIds(productIds);
					Map<Long, ProductBackViewVo> productBackViewVoMap = plateProductIds.stream()
							.collect(Collectors.toMap(k -> k.getProductId(), v -> v));
					List<ProductBackViewVo> products = new LinkedList<>();
					for (PlateProduct plateProduct : plateProducts) {
						ProductBackViewVo productBackViewVo = productBackViewVoMap.get(plateProduct.getProductId());
						if(productBackViewVo != null){
							products.add(productBackViewVo);
						}
					}
					plate.setProducts(products);
				}

			}
		}
		return plates;
	}

	@Override
	public int batchUpdate(PlateVO plateVO) {
		return tPlateMapper.batchUpdate(plateVO);
	}

	@Override
	public int batchDelete(String[] plateId) {
		for (int i = 0; i < plateId.length; i++) {
			plateProductMapper.deleteByPlateId(Integer.valueOf(plateId[i]));
		}
		return tPlateMapper.batchDelete(plateId);
	}

}
