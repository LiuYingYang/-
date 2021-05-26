package com.medusa.basemall.order.controller;

import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.order.entity.BalanceDetail;
import com.medusa.basemall.order.service.BalanceDetaiService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author whh
 */
@RestController
@RequestMapping("/BalanceDetail")
@Api(tags = "所有接口")
@VersionManager
public class BalanceDetailController {

    @Resource
    private BalanceDetaiService detaiService;

    @GetMapping("/v1/list")
    @ApiOperation(value = "查询余额详情", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100,message = "success",response = BalanceDetail.class,responseContainer = "List"),
            @ApiResponse(code = 99,message = "查询失败")
    })
    public Result list(@RequestParam @ApiParam(value = "会员id",required = true ) Long memberId,
		    @RequestParam @ApiParam(value = "页数",required = true ) Integer pageNum,
		    @RequestParam @ApiParam(value = "条数",required = true ) Integer pageSize,
		    @RequestParam(required = false,defaultValue = "")  @ApiParam(value = "开始时间") String startTime,
		    @RequestParam(required = false,defaultValue = "") @ApiParam(value = "结束时间") String endTime) {
        return detaiService.selectAll(memberId,pageNum,pageSize,startTime,endTime);
    }


    @DeleteMapping("/v1/delete")
    @ApiOperation(value = "删除余额详情", tags = "删除接口")
    public Result delete(@RequestParam @ApiParam(value = "余额详情id",required = true )String balanceDetailId) {
        return detaiService.delete(balanceDetailId);
    }

}
