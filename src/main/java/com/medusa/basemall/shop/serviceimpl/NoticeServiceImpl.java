package com.medusa.basemall.shop.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.product.service.ProductService;
import com.medusa.basemall.shop.dao.FirstpageClassifyMapper;
import com.medusa.basemall.shop.dao.NoticeMapper;
import com.medusa.basemall.shop.dao.PosterMapper;
import com.medusa.basemall.shop.entity.Notice;
import com.medusa.basemall.shop.service.NoticeService;
import com.medusa.basemall.shop.vo.NoticeVO;
import com.vip.vjtools.vjkit.mapper.BeanMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Created by wx on 2018/05/24.
 */

@Service
public class NoticeServiceImpl extends AbstractService<Notice> implements NoticeService {
	@Resource
	private NoticeMapper tNoticeMapper;
	@Resource
	private ProductService productService;
	@Resource
	PosterMapper posterMapper;
	@Resource
	FirstpageClassifyMapper firstpageClassifyMapper;

	@Override
	public List<NoticeVO> selectByAppmodelId(String appmodelId) {
		List<Notice> notices = tNoticeMapper.selectByAppmodelId(appmodelId);
		List<NoticeVO> noticeVOS = BeanMapper.mapList(notices, NoticeVO.class);
		//筛选出有链接商品的轮播图(把商品封装进去)
		List<NoticeVO> haveProduct = noticeVOS.stream().filter(obj -> null != obj.getProductId() && !"".equals(obj.getProductId()))
				.collect(Collectors.toList());
		if (haveProduct != null && haveProduct.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (NoticeVO noticeVO : haveProduct) {
				sb.append(noticeVO.getProductId() + ",");
			}
			List<Product> productList = productService.findByIds(sb.substring(0, sb.length() - 1));
			for (NoticeVO noticeVO : noticeVOS) {
				for (Product product : productList) {
					if (noticeVO.getProductId().equals(product.getProductId())) {
						noticeVO.setProductInfo(product);
					}
				}
			}
		}
		return noticeVOS;
	}

	@Override
	public int batchDelete(String[] noticeId) {
		for (String noticeid : noticeId) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("noticeId", noticeid);
			posterMapper.updatePoster(map);
			firstpageClassifyMapper.updateFirstpage(map);
		}
		return tNoticeMapper.batchDelete(noticeId);
	}

	@Override
	public void cleanProduct(List<Long> productId) {
		tNoticeMapper.cleanProduct(productId);
	}
}
