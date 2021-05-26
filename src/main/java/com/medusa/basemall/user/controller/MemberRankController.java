package com.medusa.basemall.user.controller;

import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.VersionNumber;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.user.entity.MemberRank;
import com.medusa.basemall.user.service.MemberRankRuleService;
import com.medusa.basemall.user.service.MemberRankService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 *
 * @author medusa
 * @date 2018/05/24
 */
@RestController
@RequestMapping("/member/rank")
@Api(tags = {"所有接口"})
@VersionManager(versoinNumber = VersionNumber.STANDARDVERSION)
public class MemberRankController {

	@Resource
	private MemberRankService memberRankService;

	@Resource
	private MemberRankRuleService memberRankRuleService;

	@PostMapping("/v1/createRank")
	@ApiOperation(value = "新增会员卡等级", tags = "添加接口")
	public Result createRank(@RequestBody MemberRank memberRank) {
		return memberRankService.createRank(memberRank);
	}

	@DeleteMapping("/v1/deleteRank")
	@ApiOperation(value = "删除会员卡", tags = "删除接口", notes = "默认会员卡不可删除只能修改")
	public Result deleteRank(@RequestParam @ApiParam(value = "等级id", required = true, example = "19") Integer rankId) {
		return memberRankService.deleteRank(rankId);
	}

	@PutMapping("/v1/updateRank")
	@ApiOperation(value = "修改会员卡等级信息", tags = "更新接口")
	public Result updateRank(@RequestBody MemberRank memberRank) {
		return memberRankService.updateRank(memberRank);
	}

	@GetMapping("/v1/list")
	@ApiOperation(value = "查询所有会员卡", tags = "查询接口")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success", response = MemberRank.class, responseContainer = "List"),})
	public Result list(@RequestParam String appmodelId) {
		List<MemberRank> list = memberRankService.findByList("appmodelId", appmodelId);
		list = list.stream().filter(obj -> obj.getDeleteState().equals(0) || obj.getDeleteState().equals(2)).collect(Collectors.toList());
		Collections.sort(list, Comparator.comparing(MemberRank::getGrowthValue));
		return ResultGenerator.genSuccessResult(list);
	}

}
