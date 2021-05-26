package com.medusa.basemall.shop.controller;

import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.QuantitativeRestriction;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.shop.entity.SearchWord;
import com.medusa.basemall.shop.service.SearchWordService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 搜索词
 *
 * @author Created by wx on 2018/05/25.
 */
@Api(tags = "所有接口")
@RequestMapping("/SearchWord")
@RestController
@VersionManager
public class SearchWordController {
    @Autowired
    private SearchWordService searchWordService;

    @ApiOperation(value = "保存或更新搜索词", tags = "添加接口")
    @PostMapping("/v1/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody SearchWord searchWord) {
        if (searchWord.getSearchWordId() == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("appmodelId", searchWord.getAppmodelId());
            map.put("wordType", searchWord.getWordtype());
            List<SearchWord> searchWords = searchWordService.findByType(map);
            if (searchWord.getWordtype()) {
                if (searchWords.size() > 0) {
                    return ResultGenerator.genFailResult("已存在一个默认搜索词,无法再添加");
                } else {
                    searchWordService.save(searchWord);
                    return ResultGenerator.genSuccessResult("保存成功");
                }
            } else {
                if (searchWords.size() >= QuantitativeRestriction.SEARCHWORD) {
                    return ResultGenerator.genFailResult("已存在10个普通搜索词,无法再添加");
                } else {
                    searchWordService.save(searchWord);
                    return ResultGenerator.genSuccessResult("保存成功");
                }

            }
        } else {
            searchWordService.update(searchWord);
            return ResultGenerator.genSuccessResult("保存成功");

        }
    }

    @ApiOperation(value = "根据appmodelId查询搜索词", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100, message = "success", response = SearchWord.class, responseContainer = "List"),
    })
    @GetMapping("/v1/findByAppmodelId")
    public Result findByAppmodelId(@ApiParam(value = "模板id", required = true) String appmodelId) {
        List<SearchWord> list = searchWordService.findByAppmodelId(appmodelId);
        return ResultGenerator.genSuccessResult(list);
    }

    /**
     * 批量删除搜索词
     */
    @ApiOperation(value = "删除搜索词(可批量)", tags = "删除接口")
    @DeleteMapping("/v1/batchDelete")
    public Result batchDelete(@ApiParam(value = "搜索词id字符串") @RequestParam String searchWordIds) {
        String[] searchWordId = searchWordIds.split(",");
        int result = searchWordService.batchDelete(searchWordId);
        if (result > 0) {
            return ResultGenerator.genSuccessResult("删除成功");
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }
}
