package com.medusa.basemall.product.controller;

import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.product.service.SpecificationService;
import com.medusa.basemall.product.vo.SpecificationVo;
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
@RequestMapping("/Specification")
@VersionManager
public class SpecificationController {

    @Resource
    private SpecificationService specificationService;

    /**
     * 添加或更新规格分类
     *
     * @param specificationVo
     * @return
     */
    @PostMapping("/v1/saveOrUpdateSpecificationClass")
    public Result saveOrUpdateSpecificationClass(@RequestBody @Validated  SpecificationVo specificationVo) {
        int result = specificationService.saveOrUpdateSpecificationClass(specificationVo);
        if (result > 0) {
            return ResultGenerator.genSuccessResult(result);
        } else {
            return ResultGenerator.genFailResult("添加或更新失败");
        }
    }

    /**
     * 查询所有规格
     *
     * @param specificationVo(appmodelId)
     * @return
     */
    @PostMapping("/v1/findSpecificationClassByAppmodelId")
    public Result findSpecificationClassByAppmodelId(@RequestBody SpecificationVo specificationVo) {
        List<SpecificationVo> list = specificationService.findByAppmodelId(specificationVo.getAppmodelId());
        return ResultGenerator.genSuccessResult(list);
    }

    /**
     * 删除规格分类
     *
     * @param specificationVo
     * @return
     */
    @PostMapping("/v1/deleteSpecificationClassById")
    public Result deleteSpecificationClassById(@RequestBody SpecificationVo specificationVo) {

        int result = specificationService.deleteSpecificationClassById(specificationVo.getSpecificationClassId());

        if (result > 1) {
            return ResultGenerator.genSuccessResult(result);
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }
}
