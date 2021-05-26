package com.medusa.basemall.shop.controller;

import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.HandType;
import com.medusa.basemall.constant.QuantitativeRestriction;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.shop.dao.FirstpageClassifyMapper;
import com.medusa.basemall.shop.entity.FirstpageClassify;
import com.medusa.basemall.shop.service.FirstpageClassifyService;
import com.medusa.basemall.shop.vo.ClassifySortVO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

/**
 * 首页分类
 *
 * @author Created by wx on 2018/06/04.
 */
@Api(tags = "所有接口")
@RequestMapping("/FirstpageClassify")
@RestController
@VersionManager
public class FirstpageClassifyController {
	@Resource
	private FirstpageClassifyService firstpageClassifyService;

	@Resource
	private FirstpageClassifyMapper firstpageClassifyMapper;

	@ApiOperation(value = "新增首页分类", tags = "添加接口")
	@PostMapping("/v1/add")
	public Result add(@RequestBody FirstpageClassify firstpageClassify) {
		List<FirstpageClassify> firstpageClassifies = firstpageClassifyService
				.findByAppmodelId(firstpageClassify.getAppmodelId());
		if (firstpageClassifies.size() >= QuantitativeRestriction.FIRSTPAGECLASSIFYUPPERLIMIT) {
			return ResultGenerator.genFailResult("已达上限，无法添加");
		}
		firstpageClassifies.sort(Comparator.comparing(FirstpageClassify::getSort));
		firstpageClassify.setSort(firstpageClassifies.get(firstpageClassifies.size() - 1).getSort() + 1);
		int result = firstpageClassifyService.save(firstpageClassify);
		if (result > 0) {
			return ResultGenerator.genSuccessResult("保存成功");
		} else {
			return ResultGenerator.genFailResult("保存失败");
		}
	}

	@ApiOperation(value = "更新首页分类", tags = "更新接口")
	@PutMapping("/v1/update")
	public Result update(@RequestBody FirstpageClassify firstpageClassify) {
		int result = firstpageClassifyService.update(firstpageClassify);
		if (result > 0) {
			return ResultGenerator.genSuccessResult("更新成功");
		} else {
			return ResultGenerator.genFailResult("更新失败");
		}
	}

	@ApiOperation(value = "根据appmodelId查询首页分类导航", tags = "查询接口")
	@GetMapping("/v1/findByAppmodelId")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success", response = FirstpageClassify.class, responseContainer = "List"),})
	public Result findByAppmodelId(String appmodelId) {
		List<FirstpageClassify> firstpageClassifies = firstpageClassifyService.findByAppmodelId(appmodelId);
		return ResultGenerator.genSuccessResult(firstpageClassifies);
	}

	@ApiOperation(value = "首页分类导航查询(根据Id)", tags = "查询接口")
	@GetMapping("/v1/findByClassifyId")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success", response = FirstpageClassify.class, responseContainer = "FirstpageClassify"),})
	public Result findByClassifyId(Integer classifyId) {
		FirstpageClassify firstpageClassify = firstpageClassifyService.findById(classifyId);
		return ResultGenerator.genSuccessResult(firstpageClassify);
	}

	@ApiOperation(value = "删除首页分类导航(可批量)", tags = "删除接口")
	@DeleteMapping("/v1/batchDelete")
	public Result batchDelete(@ApiParam(value = "分类id字符串") @RequestParam String classifyIds,
			@ApiParam(value = "模板id") @RequestParam String appmodelId) {
		List<FirstpageClassify> firstpageClassifies = firstpageClassifyService.findByAppmodelId(appmodelId);
		String[] classifyId = classifyIds.split(",");
		if (firstpageClassifies.size() - classifyId.length < QuantitativeRestriction.FIRSTPAGECLASSIFYLOWERLIMIT) {
			return ResultGenerator.genFailResult("删除失败,首页分类导航数量不能少于4");
		}
		int reeult = firstpageClassifyService.deleteByIds(classifyIds);
		if (reeult > 0) {
			return ResultGenerator.genSuccessResult("删除成功");
		} else {
			return ResultGenerator.genFailResult("删除失败");
		}
	}

	@ApiOperation(value = "首页分类导航排序", tags = "更新接口")
	@PutMapping("/v1/sort")
	public Result sort(@RequestBody ClassifySortVO classifySortVO) {
		FirstpageClassify firstpageClassify = firstpageClassifyService.findById(classifySortVO.getClassifyId());
		if (classifySortVO.getHandleType().equals(HandType.TOP)) {
			List<FirstpageClassify> firstpageClassifies = firstpageClassifyMapper
					.findByAppmodelIdDesc(classifySortVO.getAppmodelId());
			if (firstpageClassifies.size() > 1) {
				for (FirstpageClassify firstpageClassifyNew : firstpageClassifies) {
					if (firstpageClassify.getSort() > firstpageClassifyNew.getSort()) {
						change(firstpageClassify, firstpageClassifyNew);
					}
				}
				return ResultGenerator.genSuccessResult("置顶成功");
			}
		}
		if (classifySortVO.getHandleType().equals(HandType.FOOT)) {
			List<FirstpageClassify> firstpageClassifies = firstpageClassifyMapper
					.findByAppmodelId(classifySortVO.getAppmodelId());
			if (firstpageClassifies.size() > 1) {
				for (FirstpageClassify firstpageClassifyNew : firstpageClassifies) {
					if (firstpageClassifyNew.getSort() > firstpageClassify.getSort()) {
						change(firstpageClassify, firstpageClassifyNew);
					}
				}
				return ResultGenerator.genSuccessResult("置底成功");
			}
		}
		if (classifySortVO.getHandleType().equals(HandType.UP)) {
			List<FirstpageClassify> firstpageClassifies = firstpageClassifyMapper
					.findByAppmodelIdDesc(classifySortVO.getAppmodelId());
			if (firstpageClassifies.size() > 1) {
				for (FirstpageClassify firstpageClassifyNew : firstpageClassifies) {
					if (firstpageClassify.getSort() > firstpageClassifyNew.getSort()) {
						change(firstpageClassify, firstpageClassifyNew);
						return ResultGenerator.genSuccessResult("上移成功");
					}
				}
			}
		}
		if (classifySortVO.getHandleType().equals(HandType.DOWN)) {
			List<FirstpageClassify> firstpageClassifies = firstpageClassifyMapper
					.findByAppmodelId(classifySortVO.getAppmodelId());
			if (firstpageClassifies.size() > 1) {
				for (FirstpageClassify firstpageClassifyNew : firstpageClassifies) {
					if (firstpageClassifyNew.getSort() > firstpageClassify.getSort()) {
						change(firstpageClassify, firstpageClassifyNew);
						return ResultGenerator.genSuccessResult("下移成功");
					}
				}
			}
		}
		return ResultGenerator.genSuccessResult("操作没有生效");
	}

	private void change(FirstpageClassify firstpageClassify, FirstpageClassify firstpageClassifyNew) {
		Integer sortNew = firstpageClassifyNew.getSort();
		firstpageClassifyNew.setSort(firstpageClassify.getSort());
		firstpageClassifyService.update(firstpageClassifyNew);
		firstpageClassify.setSort(sortNew);
		firstpageClassifyService.update(firstpageClassify);
	}
}
