package com.medusa.basemall.shop.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.product.service.ProductService;
import com.medusa.basemall.shop.dao.PosterMapper;
import com.medusa.basemall.shop.entity.Poster;
import com.medusa.basemall.shop.service.PosterService;
import com.medusa.basemall.shop.vo.PosterVO;
import com.vip.vjtools.vjkit.mapper.BeanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Created by wx on 2018/05/25.
 */

@Service

public class PosterServiceImpl extends AbstractService<Poster> implements PosterService {

	@Resource
	private PosterMapper tPosterMapper;
	@Resource
	private ProductService productService;

	@Override
	public List<PosterVO> findByAppmodelId(String appmodelId) {
		List<Poster> posters = tPosterMapper.findByAppmodelId(appmodelId);
		List<PosterVO> posterVOS = BeanMapper.mapList(posters, PosterVO.class);
		//筛选出有链接商品的轮播图(把商品封装进去)
		List<Poster> haveProduct = posters.stream().filter(obj -> null != obj.getProductId())
				.collect(Collectors.toList());
		if (haveProduct != null && haveProduct.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (Poster poster : haveProduct) {
				sb.append(poster.getPosterId() + ",");
			}
			List<Product> productList = productService.findByIds(sb.substring(0, sb.length() - 1));
			for (PosterVO poster : posterVOS) {
				for (Product product : productList) {
					if(poster.getProductId().equals(product.getProductId())){
						poster.setProductInfo(product);
					}
				}
			}
		}

		return posterVOS;
	}

	@Override
	public List<Poster> findByAppmodelIdDesc(String appmodelId) {
		return tPosterMapper.findByAppmodelIdDesc(appmodelId);
	}

	@Override
	public int batchDelete(String[] posterId) {
		return tPosterMapper.batchDelete(posterId);
	}
}
