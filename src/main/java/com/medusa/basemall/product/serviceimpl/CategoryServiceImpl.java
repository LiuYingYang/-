package com.medusa.basemall.product.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.product.dao.CategoryMapper;
import com.medusa.basemall.product.dao.ProductCategoryMapper;
import com.medusa.basemall.product.dao.ProductMapper;
import com.medusa.basemall.product.entity.Category;
import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.product.entity.ProductCategory;
import com.medusa.basemall.product.service.CategoryService;
import com.medusa.basemall.product.vo.CategoryAndProductVo;
import com.medusa.basemall.product.vo.CategoryProductVo;
import com.medusa.basemall.product.vo.CategoryVo;
import com.medusa.basemall.shop.dao.FirstpageClassifyMapper;
import com.medusa.basemall.shop.dao.PosterMapper;
import com.medusa.basemall.utils.IdGenerateUtils;
import com.medusa.basemall.utils.TimeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by psy on 2018/05/24.
 * 需要事物时添加  @Transactional
 */

@Service
@Transactional
public class CategoryServiceImpl extends AbstractService<Category> implements CategoryService {

	@Resource
	private CategoryMapper categoryMapper;

	@Resource
	private ProductCategoryMapper productCategoryMapper;

	@Resource
	private CategoryService categoryService;

	@Resource
	private ProductMapper productMapper;

	@Resource
	PosterMapper posterMapper;

	@Resource
	FirstpageClassifyMapper firstpageClassifyMapper;


	/**
	 * 新增或更新商品分类
	 *
	 * @param categoryVoList 分类数组
	 * @param categoryIds    删除的分类ID数组
	 * @return
	 */
	@Override
	public int saveOrUpdate(List<CategoryVo> categoryVoList, List<Long> categoryIds, String appmodelId) {

		//删除分类，和分类对应的商品
		if (!categoryIds.isEmpty()) {
			StringBuffer sb = new StringBuffer();
			categoryIds.forEach(id -> {
				sb.append(String.valueOf(id) + ",");

				HashMap<String, Object> map = new HashMap<>();
				map.put("categoryId", id);
				posterMapper.updatePoster(map);
				firstpageClassifyMapper.updateFirstpage(map);
			});
			categoryMapper.deleteByIds(sb.toString().substring(0, sb.length() - 1));
			productCategoryMapper.deleteByCategoryId(categoryIds);
		}

		int rs = 0;
		if (!categoryVoList.isEmpty()) {
			for (CategoryVo categoryVo : categoryVoList) {
				List<Category> subCategoryList = categoryVo.getSubCategoryList();
				//有新增的分类
				if (categoryVo.getCategoryId() == -1) {
					long categoryId = IdGenerateUtils.getItemId();
					categoryVo.setCategoryId(categoryId);
					categoryVo.setCreateTime(TimeUtil.getNowTime());
					categoryVo.setFatherId(-1L);
					categoryVo.setAppmodelId(appmodelId);
					rs += categoryMapper.insertSelective(categoryVo);
					//新增子分类
					if (!subCategoryList.isEmpty()) {
						subCategoryList.forEach(subCategory -> {
							long subCategoryId = IdGenerateUtils.getItemId();
							subCategory.setCategoryId(subCategoryId);
							subCategory.setCreateTime(TimeUtil.getNowTime());
							subCategory.setAppmodelId(appmodelId);
							subCategory.setFatherId(categoryId);
							categoryMapper.insertSelective(subCategory);
						});
					}
				} else {
					//更新分类
					rs += categoryMapper.updateByPrimaryKeySelective(categoryVo);
					if (!subCategoryList.isEmpty()) {
						//更新子分类
						subCategoryList.forEach(subCategory -> {
							if (subCategory.getCategoryId() == -1) {
								subCategory.setAppmodelId(appmodelId);
								subCategory.setCategoryId(IdGenerateUtils.getItemId());
								subCategory.setCreateTime(TimeUtil.getNowTime());
								subCategory.setFatherId(categoryVo.getCategoryId());
								categoryMapper.insertSelective(subCategory);
							} else {
								categoryMapper.updateByPrimaryKeySelective(subCategory);
							}
						});
					}
				}
			}
		}

		return rs;
	}

	@Override
	public List<CategoryVo> findFirstAndSecond(String appmodelId) {

		List<CategoryVo> list = categoryMapper.findFirstCategory(appmodelId);

		return list;
	}

	@Override
	public List<CategoryAndProductVo> findCatogoryAndProduct(String appmodelId) {
		List<CategoryAndProductVo> result = new ArrayList<>();
		// 一级分类
		List<CategoryVo> categoryVos = categoryMapper.selectFirstCategory(appmodelId);
		for (CategoryVo categoryVo : categoryVos) {
			Category categoryFirst = categoryMapper.selectByPrimaryKey(categoryVo.getCategoryId());
			CategoryAndProductVo first = new CategoryAndProductVo();
			first.setFirstCategory(categoryFirst);
			result.add(first);
			// 一级分类下的所有商品
			List<ProductCategory> productCategories = productCategoryMapper
					.selectByCategoryId(categoryVo.getCategoryId());
			// 二级分类
			List<Category> categories = categoryVo.getSubCategoryList();
			// 此一级分类下有二级分类
			if (categories.size() > 0) {
				List<ProductCategory> productCategoriesSecond = new ArrayList<>();
				for (Category category : categories) {
					List<ProductCategory> productCategoriesNew = productCategoryMapper
							.selectByCategoryId(category.getCategoryId());
					productCategoriesSecond.addAll(productCategoriesNew);
				}
				// 筛选出只属于一级分类的商品
				productCategoriesSecond = checkOutOnlyFirst(productCategories, productCategoriesSecond);

				for (Category category : categories) {
					Category categorySecond = categoryMapper.selectByPrimaryKey(category.getCategoryId());
					CategoryAndProductVo second = new CategoryAndProductVo();
					second.setSecondCategory(categorySecond);
					result.add(second);

					// 此二级分类下的所有商品
					List<ProductCategory> productCategoriesNew = productCategoryMapper
							.selectByCategoryId(category.getCategoryId());
					productCategoriesNew.addAll(productCategoriesSecond);

					// 此二级分类下有商品
					if (productCategoriesNew.size() > 0) {
						for (ProductCategory productCategory : productCategoriesNew) {
							Product product = productMapper.selectByPrimaryKey(productCategory.getProductId());
							if (product != null && product.getShelfState() == 0 && product.getDeleteState() == false) {
								CategoryAndProductVo productAdd = new CategoryAndProductVo();
								productAdd.setProduct(product);
								result.add(productAdd);
							}
						}
					}
				}
			} else {
				//此一级分类下无二级分类有商品
				if (productCategories.size() > 0) {
					for (ProductCategory productCategory : productCategories) {
						Product product = productMapper.selectByPrimaryKey(productCategory.getProductId());
						if (product != null && product.getShelfState() == 0 && product.getDeleteState() == false) {
							CategoryAndProductVo productAdd = new CategoryAndProductVo();
							productAdd.setProduct(product);
							result.add(productAdd);
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public List<CategoryProductVo> findByFirstCategoryIds(List<Long> categoryIds) {
		List<CategoryProductVo> byFirstCategoryIds = categoryMapper.findByFirstCategoryIds(categoryIds);
		return byFirstCategoryIds;
	}

	@Override
	public int maxCategoryType(String appmodelId) {
		return categoryMapper.maxCategoryType(appmodelId);
	}

	private List<ProductCategory> checkOutOnlyFirst(List<ProductCategory> first, List<ProductCategory> second) {
		List<ProductCategory> result = new ArrayList<>();
		for (ProductCategory productCategory : first) {
			Boolean hasOrNot = true;
			w:
			for (ProductCategory productCategoryNew : second) {
				if (productCategoryNew.getProductId().equals(productCategory.getProductId())) {
					hasOrNot = false;
					break w;
				}
			}
			if (hasOrNot) {
				result.add(productCategory);
			}
		}
		return result;
	}
}
