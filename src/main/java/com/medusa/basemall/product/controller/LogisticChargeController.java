package com.medusa.basemall.product.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.product.entity.LogisticCharge;
import com.medusa.basemall.product.service.LogisticChargeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by psy on 2018/05/24.
 */
@RestController
@RequestMapping("/logistic/charge")
@VersionManager
public class LogisticChargeController {

    @Resource
    private LogisticChargeService logisticChargeService;

    @PostMapping("/v1/add")
    public Result add(@RequestBody LogisticCharge logisticCharge) {
        logisticChargeService.save(logisticCharge);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/v1/delete")
    public Result delete(@RequestParam Integer id) {
        logisticChargeService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/v1/update")
    public Result update(@RequestBody LogisticCharge logisticCharge) {
        logisticChargeService.update(logisticCharge);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/v1/detail")
    public Result detail(@RequestParam Integer id) {
        LogisticCharge logisticCharge = logisticChargeService.findById(id);
        return ResultGenerator.genSuccessResult(logisticCharge);
    }

    @PostMapping("/v1/list")
    public Result list(@RequestParam( ) Integer page, @RequestParam( ) Integer size) {
        PageHelper.startPage(page, size);
        List<LogisticCharge> list = logisticChargeService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }


}
