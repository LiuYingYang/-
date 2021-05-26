package com.medusa.basemall.product.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.product.dao.CategoryMapper;
import com.medusa.basemall.product.dao.ProductCategoryMapper;
import com.medusa.basemall.product.dao.ProductMapper;
import com.medusa.basemall.product.service.CategoryService;
import com.medusa.basemall.product.vo.CategoryAndProductVo;
import com.medusa.basemall.product.vo.CategoryVo;
import com.medusa.basemall.product.vo.PageUtil;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by psy on 2018/05/24.
 */
@Api(tags = "所有接口")
@RestController
@RequestMapping("/Category")
@VersionManager
public class CategoryController {

	@Resource
	private CategoryService categoryService;

	@Resource
	private CategoryMapper categoryMapper;

	@Resource
	private ProductCategoryMapper productCategoryMapper;

	@Resource
	private ProductMapper productMapper;


	@PostMapping("/v1/saveOrUpdate")
	@ApiOperation("新增或更新商品分类")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "categoryList",value = "分类数组"),
			@ApiImplicitParam(name = "categoryIds",value = " 删除的分类ID数组  //新增商品是可不传入"),
			@ApiImplicitParam(name = "appmodelId",value = "商家appmodelId"),
	})
	public Result saveOrUpdate(@RequestBody JSONObject jsonObject) {
		String categoryListJson = jsonObject.getString("categoryList");
		String categoryIdsJson = jsonObject.getString("categoryIds");
		String appmodelId = jsonObject.getString("appmodelId");

		List<CategoryVo> categoryList = JSONArray.parseArray(categoryListJson, CategoryVo.class);
		List<Long> categoryIds = JSONArray.parseArray(categoryIdsJson, Long.class);
		int result = categoryService.saveOrUpdate(categoryList, categoryIds, appmodelId);
		if (result > 0) {
			return ResultGenerator.genSuccessResult();
		} else {
			return ResultGenerator.genFailResult("添加或更新失败");
		}
	}

	@GetMapping("/v1/findFirstAndSecond")
	@ApiOperation(value = "查询商品分类（一级分类和二级分类）")
	public Result findFirstAndSecond(@ApiParam(value = "商家appmodelId") @RequestParam String appmodelId) {
		List<CategoryVo> list = categoryService.findFirstAndSecond(appmodelId);
		return ResultGenerator.genSuccessResult(list);
	}

	/**
	 * 查询商品分类（一级分类和二级分类）以及分类中的商品
	 *
	 * @param
	 * @return
	 */
	@ApiOperation(value = "查询商品分类（一级分类和二级分类）以及分类中的商品", tags = "查询接口")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success", response = CategoryAndProductVo.class, responseContainer = "List"),})
	@GetMapping("/v1/findCatogoryAndProduct")
	public Result findCatogoryAndProduct(@RequestParam String appmodelId, @RequestParam Integer pageNum,
			@RequestParam Integer pageSize) {
		if (pageNum < 0 || pageSize < 0) {
			return ResultGenerator.genFailResult("很遗憾,输入数据有误");
		}
		List<CategoryAndProductVo> result = categoryService.findCatogoryAndProduct(appmodelId);
		if (result != null && result.size() > 0) {
			if (result.size() < (pageNum - 1) * pageSize) {
				return ResultGenerator.genFailResult("很遗憾,没有更多数据了0.0");
			}
			int count = result.size();
			// 开始
			int fromIndex = (pageNum - 1) * pageSize;
			// 结束
			int toIndex = pageNum * pageSize;
			if (toIndex > count) {
				toIndex = count;
			}
			List<CategoryAndProductVo> subList = result.subList(fromIndex, toIndex);
			PageUtil pageUtil = new PageUtil();
			pageUtil.setList(subList);
			pageUtil.setTotal(result.size());
			return ResultGenerator.genSuccessResult(pageUtil);
		} else {
			return ResultGenerator.genSuccessResult("很遗憾,没有您想要的数据呢");
		}
	}
}
