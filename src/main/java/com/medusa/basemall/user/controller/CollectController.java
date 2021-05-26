package com.medusa.basemall.user.controller;


import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.user.entity.Collect;
import com.medusa.basemall.user.service.CollectService;
import com.medusa.basemall.user.vo.CollectList;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author whh
 */
@RequestMapping("collect/")
@RestController
@Api(tags = {"所有接口"})
@VersionManager
public class CollectController {

    @Autowired
    private CollectService collectService;

    @PostMapping(path = "/v1/add")
    @ApiOperation(value = "添加收藏",tags = "添加接口")
    public Result add(@RequestBody  Collect collect) {
        return collectService.add(collect);
    }

    @GetMapping(path = "/v1/list")
    @ApiOperation(value = "查询用户所有收藏",tags = "查询接口")
    @ApiResponses({
        @ApiResponse(code = 100,message = "success",response=Collect.class,responseContainer = "List"),
    })
    public Result list(CollectList collectList) {
        return collectService.list(collectList.getWxuserId(), collectList.getAppmodelId(), collectList.getPageNum(), collectList.getPageSize());
    }

	@GetMapping(path = "/v1/count")
	@ApiOperation(value = "查询用户收藏数量",tags = "查询接口")
	@ApiResponses({
			@ApiResponse(code = 100,message = "success"),
	})
	public Result count(@RequestParam @ApiParam(value = "用户id") Long wxuserId) {
		int count = collectService.count(wxuserId);
		return ResultGenerator.genSuccessResult(count);
	}


    @PostMapping(path = "/v1/delete")
    @ApiOperation(value = "删除收藏",tags = "删除接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "collectId", required = true, value = "收藏id", paramType = "strign"  ),
    })
    public Result delete(@RequestBody @ApiParam(hidden = true)JSONObject jsonObject) {
        String collectId = jsonObject.getString("collectId");
        return collectService.delete(collectId);
    }

    @PostMapping(path = "/v1/detailed")
    @ApiOperation(value = "查询用户单个收藏1",tags = "查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "collectId", required = true, value = "收藏id", paramType = "strign", dataType = "strign")
    })
    public Result detailed(@RequestBody @ApiParam(hidden = true)JSONObject jsonObject) {
        String collectId = jsonObject.getString("collectId");
	    Collect detailed = collectService.detailed(collectId);
	    return ResultGenerator.genSuccessResult(detailed);
    }

    @PostMapping(path = "/v1/findWxuserCollect")
    @ApiOperation(value = "查询用户单个收藏2",tags = "查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wxuserId", required = true, value = "用户id", paramType = "long"  ),
            @ApiImplicitParam(name = "productId", required = true, value = "商品id", paramType = "long"  ),
    })
    public Result findWxuserCollect(@RequestBody @ApiParam(hidden = true)JSONObject jsonObject) {
        Long wxuserId = jsonObject.getLong("wxuserId");
        Long productId = jsonObject.getLong("productId");
	    Collect wxuserCollect = collectService.findWxuserCollect(wxuserId, productId);
	    return ResultGenerator.genSuccessResult(wxuserCollect);
    }

}
