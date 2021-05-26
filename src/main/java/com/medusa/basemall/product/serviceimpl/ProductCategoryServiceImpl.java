package com.medusa.basemall.product.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.product.dao.ProductCategoryMapper;
import com.medusa.basemall.product.entity.ProductCategory;
import com.medusa.basemall.product.service.CategoryService;
import com.medusa.basemall.product.service.ProductCategoryService;
import com.medusa.basemall.product.vo.CategoryProductVo;
import com.medusa.basemall.utils.IdGenerateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 *
 * @author psy
 * @date 2018/05/24
 * 需要事物时添加  @Transactional
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductCategoryServiceImpl extends AbstractService<ProductCategory> implements ProductCategoryService {
	@Resource
	private ProductCategoryMapper productCategoryMapper;
	@Resource
	private CategoryService categoryService;

	/**
	 * 添加商品分类
	 *
	 * @param categoryIds
	 * @param productId
	 * @param appmodelId
	 * @return
	 */
	@Override
	public int saveProductCategory(List<Long> categoryIds, Long productId, String appmodelId) {

		AtomicInteger rs = new AtomicInteger();

		if (!categoryIds.isEmpty()) {
			categoryIds.forEach(categoryId -> {
				ProductCategory productCategory = new ProductCategory();
				productCategory.setProductCategoryId(IdGenerateUtils.getItemId());
				productCategory.setAppmodelId(appmodelId);
				productCategory.setCategoryId(categoryId);
				productCategory.setProductId(productId);
				rs.addAndGet(productCategoryMapper.insertSelective(productCategory));
			});
		}

		return rs.get();
	}


	/**
	 * 更新商品分类
	 *
	 * @param categoryIds
	 * @param productId
	 * @param appmodelId
	 * @return
	 */
	@Override
	public int updateProductCategory(List<Long> categoryIds, long productId, String appmodelId) {

		int rs = 0;
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductId(productId);
		rs += productCategoryMapper.delete(productCategory);
		rs += saveProductCategory(categoryIds, productId, appmodelId);

		return rs;
	}

	@Override
	public List<ProductCategory> findByProductId(Long productId) {

		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductId(productId);
		return productCategoryMapper.select(productCategory);
	}

	/**
	 * 批量设置分类
	 *
	 * @param productIds
	 * @param categoryIds
	 * @param appmodelId
	 * @return
	 */
	@Override
	public int batchSetCategory(List<Long> productIds, List<Long> categoryIds, String appmodelId) {

		AtomicInteger rs = new AtomicInteger();
		if (!productIds.isEmpty()) {
			int i = productCategoryMapper.deleteByProductIds(productIds);
			if (!categoryIds.isEmpty()) {
				for (Long productId : productIds) {
					for (Long categoryId : categoryIds) {
						ProductCategory productCategory = new ProductCategory();
						productCategory.setProductCategoryId(IdGenerateUtils.getItemId());
						productCategory.setProductId(productId);
						productCategory.setAppmodelId(appmodelId);
						productCategory.setCategoryId(categoryId);
						rs.addAndGet(productCategoryMapper.insertSelective(productCategory));
					}
				}
			}
		}

		return rs.get();
	}

	/**
	 * 查找商品分类
	 * @return
	 */
	@Override
	public List<CategoryProductVo> findByCategoryById(List<Long> productIds, String appmodelId) {
		if (productIds.size() == 0) {
			return null;
		}
		int maxCategoryType = categoryService.maxCategoryType(appmodelId);
		//查询父类
		List<CategoryProductVo> productCategoryList = selectByProdctsParentCategoryId(productIds, appmodelId);
		selectSonCategory(productCategoryList, 1, maxCategoryType, appmodelId);
		return productCategoryList;
	}

	private void selectSonCategory(List<CategoryProductVo> productCategoryList, int categoryType, int maxCategoryType,
			String appmodelId) {
		//查询父类下商品拥有的子类
		categoryType += 1;
		for (CategoryProductVo categoryProductVo : productCategoryList) {
			for (int i = categoryType; i <= maxCategoryType; i++) {
				Long productId = categoryProductVo.getProductId();
				List<CategoryProductVo> categoryProductVos = selectByProdctSonCategoryId(productId, appmodelId,
						categoryType,categoryProductVo.getCategoryId());
				categoryProductVo.setSubCategoryList(categoryProductVos);
				if (categoryProductVos != null && categoryProductVos.size() > 0) {
					selectSonCategory(categoryProductVos, i, maxCategoryType, appmodelId);
				}
			}
		}
	}

	private List<CategoryProductVo> selectByProdctSonCategoryId(Long productId, String appmodelId, int categoryType,
			Long fatherId) {
		List<Long> productIds = new ArrayList<>(1);
		productIds.add(productId);
		return productCategoryMapper.selectByProdctParentCategoryId(productIds, appmodelId, categoryType,fatherId);
	}

	private List<CategoryProductVo> selectByProdctsParentCategoryId(List<Long> productIds, String appmodelId) {
		return productCategoryMapper.selectByProdctParentCategoryId(productIds, appmodelId, 1, null);
	}

}
