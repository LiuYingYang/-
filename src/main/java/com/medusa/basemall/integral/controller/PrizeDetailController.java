package com.medusa.basemall.integral.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.VersionNumber;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.integral.entity.PrizeDetail;
import com.medusa.basemall.integral.service.PrizeDetailService;
import com.medusa.basemall.integral.vo.PrizeDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 积分明细
 *
 * @author Created by wx on 2018/06/06.
 */
@Api(tags = "所有接口")
@RestController
@RequestMapping("/prize/detail")
@VersionManager(versoinNumber = VersionNumber.STANDARDVERSION)
public class PrizeDetailController {

	@Resource
	private PrizeDetailService prizeDetailService;

	@ApiOperation(value = "查看积分明细", tags = "查询接口")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success", response = PrizeDetail.class, responseContainer = "List"),})
	@GetMapping("/v1/list")
	public Result seletePrizeDetail(PrizeDetailVo prizeDetailVo) {
		PageHelper.startPage(prizeDetailVo.getPageNum(), prizeDetailVo.getPageSize());
		List<PrizeDetail> prizeDetails = prizeDetailService.seletePrizeDetailByWxuserId(prizeDetailVo);
		PageInfo pageInfo = new PageInfo(prizeDetails);
		return ResultGenerator.genSuccessResult(pageInfo);
	}

	@ApiOperation(value = "删除积分明细记录", tags = "更新接口")
	@GetMapping("/v1/delete")
	public Result deleteDetail(@RequestParam Integer integralDetailId) {
		if (prizeDetailService.deleteById(integralDetailId) > 0) {
			return ResultGenerator.genSuccessResult();
		}
		return ResultGenerator.genFailResult("删除失败");
	}

	@ApiOperation(value = "分享增加积分", tags = "更新接口")
	@GetMapping("/v1/add/share")
	public Result loginOrshare(@RequestParam String appmodelId, @RequestParam Long wxuserId) {
		prizeDetailService.addIntegral(4, appmodelId, wxuserId);
		return ResultGenerator.genSuccessResult();
	}


}
