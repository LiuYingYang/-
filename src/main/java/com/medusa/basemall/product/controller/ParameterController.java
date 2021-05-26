package com.medusa.basemall.product.controller;

import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.product.entity.ParameterClass;
import com.medusa.basemall.product.service.ParameterService;
import com.medusa.basemall.product.vo.ParameterVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by psy on 2018/05/24.
 */
@RestController
@RequestMapping("/Parameter")
@VersionManager
public class ParameterController {

    @Resource
    private ParameterService parameterService;

    /**
     * 新增或更新商品属性
     *
     * @param parameterVo
     * @return
     */
    @PostMapping("/v1/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody @Validated ParameterVo parameterVo) {

        int result = parameterService.saveOrUpdate(parameterVo);

        if (result > 1) {
            return ResultGenerator.genSuccessResult(result);
        } else {
            return ResultGenerator.genFailResult("添加或更新失败");
        }
    }

    /**
     * 查询商品属性
     *
     * @param parameterClass(appmodelId)
     * @return
     */
    @PostMapping("/v1/findByAppmodelId")
    public Result findByAppmodelId(@RequestBody ParameterClass parameterClass) {

        List<ParameterVo> list = parameterService.findByAppmodelId(parameterClass.getAppmodelId());

        return ResultGenerator.genSuccessResult(list);
    }


    /**
     * 删除商品属性模板
     *
     * @param parameterClass（paramClassId）
     * @return
     */
    @PostMapping("/v1/deleteById")
    public Result deleteById(@RequestBody ParameterClass parameterClass) {

        int result = parameterService.deleteByParamClassId(parameterClass.getParamClassId());

        if (result > 1) {
            return ResultGenerator.genSuccessResult(result);
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }

}
