package com.medusa.basemall.user.controller;

import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.user.entity.FootMark;
import com.medusa.basemall.user.service.FootMarkService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author whh
 */

@RequestMapping("/footmark")
@RestController
@Api(tags = {"所有接口"})
@VersionManager
public class FootMarkController {

	@Autowired
	private FootMarkService footMarkService;


	@RequestMapping(path = "/v1/add", method = RequestMethod.POST)
	@ApiOperation(value = "添加足迹", tags = "添加接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success"), @ApiResponse(code = 99, message = "添加失败"),})
	public Result add(@RequestBody FootMark footMark) {
		return footMarkService.save(footMark);
	}


	@RequestMapping(path = "/v1/list", method = RequestMethod.GET)
	@ApiOperation(value = "查询用户所有足迹", tags = "查询接口")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success", response = FootMark.class, responseContainer = "List"),})
	public Result list(@RequestParam @ApiParam(value = "用户id") Long wxuserId,
			@RequestParam @ApiParam(value = "商家wxAppid") String appmodelId,
			@RequestParam @ApiParam(value = "页数") Integer pageNum,
			@RequestParam @ApiParam(value = "条数") Integer pageSize) {
		return footMarkService.findUserFootMark(wxuserId, appmodelId, pageNum, pageSize);
	}

	@RequestMapping(path = "/v1/delete", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除/清空足迹", tags = "删除接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success"), @ApiResponse(code = 99, message = "删除失败"),})
	public Result delete(@RequestParam @ApiParam(value = "用户id") Long wxuserId,
			@ApiParam(value = "足迹id") @RequestParam String footmarkId,
			@RequestParam @ApiParam(value = " 操作类型:1-删除, 2-清空 ") Integer type) {
		return footMarkService.delete(wxuserId, type, footmarkId);
	}

	@GetMapping(path = "/v1/count")
	@ApiOperation(value = "查询足迹数量", tags = "查询接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success"),})
	public Result count(@RequestParam @ApiParam(value = "用户id") Long wxuserId) {
		int count = footMarkService.count(wxuserId);
		return ResultGenerator.genSuccessResult(count);
	}


}
