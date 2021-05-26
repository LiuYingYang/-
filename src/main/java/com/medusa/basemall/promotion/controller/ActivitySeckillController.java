package com.medusa.basemall.promotion.controller;

import com.github.pagehelper.PageInfo;
import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.VersionNumber;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.integral.vo.FindProductVo;
import com.medusa.basemall.promotion.service.ActivitySeckillService;
import com.medusa.basemall.promotion.vo.ActivitySeckillDetailVo;
import com.medusa.basemall.promotion.vo.ActivitySeckillVo;
import com.medusa.basemall.promotion.vo.OptionalProductItems;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 秒杀活动相关
 *
 * @author Created by psy on 2018/05/30.
 */
@Api(tags = "所有接口")
@RestController
@RequestMapping("/ActivitySeckill")
@VersionManager(versoinNumber = VersionNumber.MARKETINGVERSION)
public class ActivitySeckillController {

	@Resource
	private ActivitySeckillService activitySeckillService;


	@ApiOperation(value = "查询可供秒杀活动选择的商品", tags = "查询接口")
	@GetMapping("/v1/findProductForSeckill")
	public Result<List<OptionalProductItems>> findProductForSeckill(FindProductVo findProductVo) {
		List<OptionalProductItems> productForSeckill = activitySeckillService.findProductForSeckill(findProductVo);
		return ResultGenerator.genSuccessResult(new PageInfo(productForSeckill));
	}

	@ApiOperation(value = "创建秒杀活动", tags = "添加接口")
	@PostMapping("/v1/save")
	public Result save(@RequestBody ActivitySeckillVo activitySeckillVo) {
		int result = activitySeckillService.saveActivitySeckill(activitySeckillVo);
		if (result > 0) {
			return ResultGenerator.genSuccessResult();
		} else {
			return ResultGenerator.genFailResult("创建失败");
		}
	}

	@ApiOperation(value = "查询秒杀活动", tags = "查询接口")
	@GetMapping("/v1/findByAppmodelId")
	public Result<PageInfo<List<ActivitySeckillVo>>> findByAppmodelId(String appmodelId,Integer pageNum,Integer pageSize) {
		List<ActivitySeckillVo> list = activitySeckillService.findByAppmodelId(appmodelId,pageNum,pageSize);
		Result<PageInfo<List<ActivitySeckillVo>>> result = ResultGenerator.genSuccessResult(new PageInfo(list));
		return result;
	}

	@ApiOperation(value = "查询秒杀活动详情", tags = "查询接口")
	@GetMapping("/v1/seckill/detail")
	public Result<ActivitySeckillDetailVo> seckillDetail(@RequestParam Integer activitySeckillId,
			@RequestParam String appmodelId) {
		ActivitySeckillDetailVo findProductVo = activitySeckillService.findseckillDetail(activitySeckillId, appmodelId);
		return ResultGenerator.genSuccessResult(findProductVo);
	}

	@ApiOperation(value = "批量删除秒杀活动", tags = "更新接口")
	@DeleteMapping("/v1/batchDelete")
	public Result batchDelete(@RequestParam String activitySeckillIds, @RequestParam String appmodelId) {
		int result = activitySeckillService.batchDelete(activitySeckillIds, appmodelId);
		if (result > 0) {
			return ResultGenerator.genSuccessResult();
		} else {
			return ResultGenerator.genFailResult("删除失败");
		}
	}

	@ApiOperation(value = "更新秒杀活动", tags = "更新接口")
	@PutMapping("/v1/update")
	public Result update(@RequestBody ActivitySeckillVo activitySeckillVo) {
		int result = activitySeckillService.updateActivitySeckill(activitySeckillVo);
		if (result > 0) {
			return ResultGenerator.genSuccessResult(result);
		} else {
			return ResultGenerator.genFailResult("更新失败，请检查选择的商品是否与其他活动时间冲突");
		}
	}


}
