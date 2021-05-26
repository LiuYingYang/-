package com.medusa.basemall.product.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medusa.basemall.agent.vo.PitchonProduct;
import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.order.vo.ProductAgentVo;
import com.medusa.basemall.product.service.ProductCategoryService;
import com.medusa.basemall.product.service.ProductService;
import com.medusa.basemall.product.vo.*;
import com.medusa.basemall.promotion.vo.ActivitySeckillVo;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author psy
 * @date 2018/05/24
 */
@RestController
@RequestMapping("/Product")
@VersionManager
public class ProductController {

	@Resource
	private ProductService productService;

	@Resource
	private ProductCategoryService productCategoryService;

	/**
	 * 添加商品
	 *
	 * @param productEditVo
	 * @return
	 */
	@PostMapping("/v1/save")
	public Result save(@RequestBody ProductEditVo productEditVo) {
		int result = productService.saveProduct(productEditVo);
		if (result > 0) {
			return ResultGenerator.genSuccessResult(result);
		} else {
			return ResultGenerator.genFailResult("添加失败");
		}
	}

	/**
	 * 更新商品时查询商品
	 *
	 * @param productId
	 * @return
	 */
	@GetMapping("/v1/findProductForUpdate")
	public Result findProductForUpdate(@RequestParam Long productId,@RequestParam String appmodelId) {
		ProductEditVo productEditVo = productService.findProductForUpdate(productId,appmodelId);
		return ResultGenerator.genSuccessResult(productEditVo);
	}

	/**
	 * 更新商品信息
	 *
	 * @param productEditVo
	 * @return
	 */
	@PostMapping("/v1/update")
	public Result update(@RequestBody ProductEditVo productEditVo) {
		int result = productService.updateProduct(productEditVo);
		if (result > 0) {
			return ResultGenerator.genSuccessResult(result);
		} else {
			return ResultGenerator.genFailResult("更新失败");
		}
	}

	@PutMapping("/v1/updateOthers")
	@ApiImplicitParams({@ApiImplicitParam(name = "productId", value = "商品id"),
			@ApiImplicitParam(name = "stock", value = "库存"), @ApiImplicitParam(name = "price", value = "价格"),})
	@ApiOperation(value = "更新统一规格商品总价格和总库存的更新", tags = "更新接口")
	public Result updateOthers(@RequestBody JSONObject jsonObject) {
		Long productId = jsonObject.getLong("productId");
		Integer stock = jsonObject.getInteger("stock");
		Double price = jsonObject.getDouble("price");
		int result = productService.updateStockOrPrice(productId, stock, price);
		if (result == 1) {
			return ResultGenerator.genSuccessResult(result);
		} else if (result == 2) {
			return ResultGenerator.genFailResult("不能同时更新价格和库存");
		} else {
			return ResultGenerator.genFailResult("更新失败");
		}
	}

	@PutMapping("/v1/batchUpdateShelfState")
	@ApiOperation(value = "单个或多个商品上下架", tags = "更新接口")
	public Result batchUpdateShelfState(@RequestBody JSONObject jsonObject) {
		List<DeleteProduct> deleteProduct = JSON.parseArray(jsonObject.getString("deleteProduct"), DeleteProduct.class);
		int result = productService.batchUpdateShelfState(deleteProduct);
		if (result > 0) {
			return ResultGenerator.genSuccessResult(result);
		} else {
			return ResultGenerator.genFailResult("上下架失败");
		}
	}

	/**
	 * 批量删除
	 * productIds
	 * @return
	 */
	@PutMapping("/v1/batchDelete")
	@ApiOperation(value = "单个或多个商品 删除(逻辑删除)", tags = "更新接口")
	@ApiImplicitParams({@ApiImplicitParam(name = "deleteProduct"),})
	public Result batchDelete(@RequestBody @ApiParam(hidden = true) JSONObject jsonObject) {
		List<DeleteProduct> deleteProduct = JSON.parseArray(jsonObject.getString("deleteProduct"), DeleteProduct.class);
		int result = productService.batchDelete(deleteProduct);
		if (result > 0) {
			return ResultGenerator.genSuccessResult(result);
		} else {
			return ResultGenerator.genFailResult("删除失败");
		}
	}
	/**
	 * 批量设置分类
	 * <p>
	 * productIds
	 * categoryIdList
	 * @return
	 */
	@PostMapping("/v1/batchSetCategory")
	public Result batchSetCategory(@RequestBody JSONObject jsonObject) {
		String appmodelId = jsonObject.getString("appmodelId");
		List<Long> productIds = JSONArray.parseArray(jsonObject.getString("productIds"), Long.class);
		List<Long> categoryIds = JSONArray.parseArray(jsonObject.getString("categoryIds"), Long.class);
		if (categoryIds.size() == 0) {
			return ResultGenerator.genFailResult("商品必须要有一个分类");
		}
		int result = productCategoryService.batchSetCategory(productIds, categoryIds, appmodelId);
		if (result > 0) {
			return ResultGenerator.genSuccessResult(result);
		} else {
			return ResultGenerator.genFailResult("设置失败");
		}
	}


	@GetMapping("/v1/findProductForBack")
	@ApiOperation(value = "后台查询商品", tags = "查询接口")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success", response = ProductBackViewVo.class, responseContainer = "List"),
			@ApiResponse(code = 99, message = "查询失败"),})
	public Result findProductForBack(@ApiParam ProductFindRequestVo productFindRequestVo) {
		PageInfo pageInfo = productService.findProductForBack(productFindRequestVo);
		return ResultGenerator.genSuccessResult(pageInfo);
	}

	/**************小程序************/
	/**
	 * 小程序端查询所有商品
	 *
	 * @param productFindRequestVo
	 * @return
	 */
	@GetMapping("/v1/findProductForWX")
	@ApiOperation(value = "小程序端查询所有商品", tags = "查询接口")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success", responseContainer = "List", response = ProductWxViewVo.class)})
	public Result findProductForWX(ProductFindRequestVo productFindRequestVo) {
		PageInfo pageInfo = productService.findProductForWX(productFindRequestVo);
		return ResultGenerator.genSuccessResult(pageInfo);
	}

	@ApiOperation(value = "首页查询秒杀活动", tags = "查询接口")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success", response = ActivitySeckillVo.class, responseContainer = "List"),})
	@GetMapping("/v1/home/page")
	public Result<List<HomeSeckillVO>> homePageAppmodelId(@RequestParam  String appmodelId) {
		List<HomeSeckillVO> list = productService.homePageAppmodelId(appmodelId);
		return ResultGenerator.genSuccessResult(list);
	}

	@ApiOperation(value = "小程序查询秒杀活动(二级页面)", tags = "查询接口")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success", response = ActivitySeckillVo.class, responseContainer = "List"),})
	@GetMapping("/v1/seckill/product")
	public Result<List<HomeSeckillVO>> findSeckillProduct(@RequestParam  String appmodelId) {
		List<HomeSeckillVO> list = productService.findSeckillProduct(appmodelId);
		return ResultGenerator.genSuccessResult(list);
	}

	@GetMapping("/v1/findSpecByProductId")
	@ApiOperation(value = "小程序端根据商品ID查询商品规格", tags = "查询接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success", response = ProductSpecVo.class)})
	public Result<ProductSpecVo> findSpecByProductId(@RequestParam Long productId, @RequestParam String appmodelId) {
		ProductSpecVo productSpecVo = productService.findSpecByProductId(productId,appmodelId);
		return ResultGenerator.genSuccessResult(productSpecVo);
	}

	/**
	 * 小程序端根据商品ID查询商品详情
	 * @return
	 */
	@ApiOperation(value = "小程序端根据商品ID查询商品详情(包括活动信息)", tags = "查询接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success", response = ProductWxViewDetailsVo.class)})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "activeInfo", value = "活动信息, 如果是活动商品需要带上joinActiveInfo中的activeInfo"),
			@ApiImplicitParam(name = "", value = "商品id"),})
	@GetMapping("/v1/findDetailByProductId")
	public Result findDetailByProductId(@RequestParam Long productId,@RequestParam String appmodelId,@RequestParam Long wxuserId) {
		ProductWxViewDetailsVo productEditVo = productService.findDetailByProductId(productId,appmodelId, wxuserId);
		return ResultGenerator.genSuccessResult(productEditVo);
	}


	/**
	 * 根据appmodelId查询 商家可选取为代理商品的商品
	 *
	 * @return
	 */
	@GetMapping("/v1/findProducttoAgent")
	@ApiOperation(value = "查询商家可选取为代理商品的商品", tags = "查询接口")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success", response = ProductAgentVo.class, responseContainer = "List"),
			@ApiResponse(code = 99, message = "查询失败")})
	public Result findProducttoAgent(@RequestParam @ApiParam(value = "商家wxAppId") String appmodelId,
			@RequestParam @ApiParam(value = "页数") Integer pageNum,
			@RequestParam @ApiParam(value = "条数") Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize, "create_time desc");
		List<ProductAgentVo> list = productService.findProducttoAgent(appmodelId);
		PageInfo pageInfo = new PageInfo(list);
		return ResultGenerator.genSuccessResult(pageInfo);
	}

	/**
	 *查询选中的商品
	 * @return
	 */
	@GetMapping("/v1/pitchon")
	@ApiOperation(value = "查询选中的代理商品/v1", tags = "查询接口")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success", response = PitchonProduct.class, responseContainer = "List"),
			@ApiResponse(code = 99, message = "查询失败")})
	public Result pitchOn(@RequestParam @ApiParam(value = "商家wxAppId") String appmodelId,
			@RequestParam @ApiParam(value = "商品ids 字符串逗号分隔") String productIds) {
		return productService.pitchOn(productIds, appmodelId);
	}

}
