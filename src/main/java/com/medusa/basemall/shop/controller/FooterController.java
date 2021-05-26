package com.medusa.basemall.shop.controller;

import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.shop.entity.Footer;
import com.medusa.basemall.shop.service.FooterService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 底部导航
 *
 * @author Created by wx on 2018/06/04.
 */
@Api(tags = "所有接口")
@RequestMapping("/Footer")
@RestController
@VersionManager
public class FooterController {
    @Resource
    private FooterService footerService;

    @ApiOperation(value = "查询底部导航", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100, message = "success", response = Footer.class, responseContainer = "List"),})
    @GetMapping("/v1/findByAppmodelId")
    public Result findByAppmodelId(@ApiParam(value = "模板id") @RequestParam  String appmodelId) {
        List<Footer> footers = footerService.findByAppmoedelId(appmodelId);
        return ResultGenerator.genSuccessResult(footers);
    }

    @ApiOperation(value = "编辑/开启关闭底部导航", tags = "更新接口")
    @PutMapping("/v1/update")
    public Result update(@RequestBody Footer footer) {
        int result = footerService.update(footer);
        if (result > 0) {
            return ResultGenerator.genSuccessResult("更新成功");
        } else {
            return ResultGenerator.genFailResult("更新失败");
        }
    }
}
