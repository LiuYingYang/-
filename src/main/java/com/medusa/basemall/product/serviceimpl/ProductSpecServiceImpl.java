package com.medusa.basemall.product.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.product.dao.ProductSpecMapper;
import com.medusa.basemall.product.entity.ProductSpec;
import com.medusa.basemall.product.service.ProductSpecService;
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
@Transactional
public class ProductSpecServiceImpl extends AbstractService<ProductSpec> implements ProductSpecService {
    @Resource
    private ProductSpecMapper productSpecMapper;

    /**
     * 新增商品选中的规格
     *
     * @param productSpecList
     * @param productId
     * @param appmodelId
     * @return
     */
    @Override
    public int saveProductSpec(List<ProductSpec> productSpecList, Long productId, String appmodelId) {
        AtomicInteger rs = new AtomicInteger();
        if (!productSpecList.isEmpty()) {
            productSpecList.forEach(productSpec -> {
                productSpec.setAppmodelId(appmodelId);
                productSpec.setProductId(productId);
                productSpec.setProductSpecId(IdGenerateUtils.getItemId());
                rs.addAndGet(productSpecMapper.insertSelective(productSpec));
            });
        }
        return rs.get();
    }

    /**
     * 更新商品选中的规格
     *
     * @param productSpecList
     * @param productId
     * @param appmodelId
     * @return
     */
    @Override
    public int updateProductSpec(List<ProductSpec> productSpecList, long productId, String appmodelId) {

        int rs = 0;
        ProductSpec productSpec = new ProductSpec();
        productSpec.setProductId(productId);
        productSpecMapper.delete(productSpec);
        rs += saveProductSpec(productSpecList, productId, appmodelId);
        return rs;
    }

    @Override
    public List<ProductSpec> findByProductId(Long productId) {

        ProductSpec productSpec = new ProductSpec();
        productSpec.setProductId(productId);

        return productSpecMapper.select(productSpec);
    }

}
