package com.medusa.basemall.product.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.core.ServiceException;
import com.medusa.basemall.product.dao.ProductSpecClassMapper;
import com.medusa.basemall.product.entity.ProductSpecClass;
import com.medusa.basemall.product.service.ProductSpecClassService;
import com.medusa.basemall.utils.IdGenerateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by psy on 2018/05/24.
 * 需要事物时添加  @Transactional
 */

@Service
@Transactional(rollbackFor = ServiceException.class)
public class ProductSpecClassServiceImpl extends AbstractService<ProductSpecClass> implements ProductSpecClassService {

	@Resource
	private ProductSpecClassMapper productSpecClassMapper;

	/**
	 * 新增商品选中的规格分类
	 *
	 * @param productSpecClassList
	 * @param productId
	 * @param appmodelId
	 * @return
	 */
	@Override
	public int saveProductSpecClass(List<ProductSpecClass> productSpecClassList, Long productId, String appmodelId) {

		AtomicInteger rs = new AtomicInteger();
		if (!productSpecClassList.isEmpty()) {
			productSpecClassList.forEach(productSpecClass -> {
				productSpecClass.setAppmodelId(appmodelId);
				productSpecClass.setProductId(productId);
				productSpecClass.setProductSpecClassId(IdGenerateUtils.getItemId());
				rs.addAndGet(productSpecClassMapper.insertSelective(productSpecClass));
			});
		}

		return rs.get();
	}


    /**
     * 更新商品选中的规格分类
     *
     * @param productSpecClassList
     * @param productId
     * @param appmodelId
     * @return
     */
    @Override
    public int updateProductSpecClass(List<ProductSpecClass> productSpecClassList, long productId, String appmodelId) {

        int rs = 0;
        ProductSpecClass productSpecClass = new ProductSpecClass();
        productSpecClass.setProductId(productId);
        rs += productSpecClassMapper.delete(productSpecClass);
        rs += saveProductSpecClass(productSpecClassList, productId, appmodelId);

		return rs;
	}

	@Override
	public List<ProductSpecClass> findByProductId(Long productId, String appmodelId) {
		ProductSpecClass productSpecClass = new ProductSpecClass();
		productSpecClass.setProductId(productId);
		productSpecClass.setAppmodelId(appmodelId);
		return productSpecClassMapper.select(productSpecClass);
	}

}
