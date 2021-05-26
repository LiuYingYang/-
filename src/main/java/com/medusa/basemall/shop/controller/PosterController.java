package com.medusa.basemall.shop.controller;

import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.HandType;
import com.medusa.basemall.constant.QuantitativeRestriction;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.shop.dao.PosterMapper;
import com.medusa.basemall.shop.entity.Poster;
import com.medusa.basemall.shop.service.PosterService;
import com.medusa.basemall.shop.vo.PosterSortVO;
import com.medusa.basemall.shop.vo.PosterVO;
import com.medusa.basemall.utils.TimeUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 轮播图
 *
 * @author Created by wx on 2018/05/25.
 */
@Api(tags = "所有接口")
@RequestMapping("/Poster")
@RestController
@VersionManager
public class PosterController {

	@Autowired
    private PosterService posterService;

    @Resource
    private PosterMapper posterMapper;

    @ApiOperation(value = "新增轮播图", tags = "添加接口")
    @PostMapping("/v1/save")
    public Result saveOrUpdate(@RequestBody Poster poster) {
        poster.setCreateTime(TimeUtil.getNowTime());
        List<Poster> posters = posterMapper.findByAppmodelId(poster.getAppmodelId());
        if (posters.size() >= QuantitativeRestriction.PLATEUPPERLIMIT) {
            return ResultGenerator.genFailResult("已达上限，无法添加");
        }
        if (posters.size() > 0) {
            for (Poster posterNew : posters) {
                posterNew.setSort(posterNew.getSort() + 1);
                posterService.update(posterNew);
            }
        }
        poster.setSort(1);
        int result = posterService.save(poster);
        if (result > 0) {
            return ResultGenerator.genSuccessResult("添加成功");
        } else {
            return ResultGenerator.genFailResult("添加失败");
        }
    }

    @ApiOperation(value = "更新轮播图", tags = "更新接口")
    @PutMapping("/v1/update")
    public Result update(@RequestBody Poster poster) {
        int result = posterMapper.updateByPrimaryKey(poster);
        if (result > 0) {
            return ResultGenerator.genSuccessResult("更新成功");
        } else {
            return ResultGenerator.genFailResult("更新失败");
        }
    }

    @ApiOperation(value = "根据id查询轮播图", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100, message = "success", response = Poster.class, responseContainer = "Poster"),
    })
    @GetMapping("/v1/findById")
    public Result findById(@ApiParam(value = "轮播图id") @RequestParam Integer posterId) {
        Poster poster = posterService.findById(posterId);
        if (poster != null) {
            return ResultGenerator.genSuccessResult(poster);
        } else {
            return ResultGenerator.genFailResult("查询失败");
        }
    }

    @ApiOperation(value = "根据appmodelId查询轮播图", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100, message = "success", response = PosterVO.class, responseContainer = "List"),
    })
    @GetMapping("/v1/findByAppmodelId")
    public Result findByAppmodelId(@ApiParam(value = "模板id") @RequestParam String appmodelId) {
        List<PosterVO> list = posterService.findByAppmodelId(appmodelId);
        return ResultGenerator.genSuccessResult(list);
    }


    @ApiOperation(value = "删除轮播图(可批量)", tags = "删除接口")
    @DeleteMapping("/v1/batchDelete")
    public Result batchDelete(@ApiParam(value = "轮播图id字符串", required = true) @RequestParam String posterIds) {
        String[] posterId = posterIds.split(",");
        int result = posterService.batchDelete(posterId);
        if (result > 0) {
            return ResultGenerator.genSuccessResult("删除成功");
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }


    @ApiOperation(value = "轮播图排序操作", tags = "更新接口")
    @PutMapping("/v1/sort")
    public Result sort(@RequestBody PosterSortVO posterSortVO) {
        Poster poster = posterService.findById(posterSortVO.getPosterId());
        if (posterSortVO.getHandleType().equals(HandType.TOP)) {
            List<Poster> posters = posterService.findByAppmodelIdDesc(posterSortVO.getAppmodelId());
            if (posters.size() > 1) {
                for (Poster posterNew : posters) {
                    if (poster.getSort() > posterNew.getSort()) {
                        change(poster, posterNew);
                    }
                }
                return ResultGenerator.genSuccessResult("置顶成功");
            }
        }
        if (posterSortVO.getHandleType().equals(HandType.FOOT)) {
            List<PosterVO> posters = posterService.findByAppmodelId(posterSortVO.getAppmodelId());
            if (posters.size() > 1) {
                for (Poster posterNew : posters) {
                    if (posterNew.getSort() > poster.getSort()) {
                        change(poster, posterNew);
                    }
                }
                return ResultGenerator.genSuccessResult("置底成功");
            }
        }
        if (posterSortVO.getHandleType().equals(HandType.UP)) {
            List<Poster> posters = posterService.findByAppmodelIdDesc(posterSortVO.getAppmodelId());
            if (posters.size() > 1) {
                for (Poster posterNew : posters) {
                    if (poster.getSort() > posterNew.getSort()) {
                        change(poster, posterNew);
                        return ResultGenerator.genSuccessResult("上移成功");
                    }
                }
            }
        }
        if (posterSortVO.getHandleType().equals(HandType.DOWN)) {
            List<PosterVO> posters = posterService.findByAppmodelId(posterSortVO.getAppmodelId());
            if (posters.size() > 1) {
                for (Poster posterNew : posters) {
                    if (posterNew.getSort() > poster.getSort()) {
                        change(poster, posterNew);
                        return ResultGenerator.genSuccessResult("下移成功");
                    }
                }
            }
        }
        return ResultGenerator.genSuccessResult("操作没有生效");
    }

    private void change(Poster poster, Poster posterNew) {
        Integer sortNew = posterNew.getSort();
        posterNew.setSort(poster.getSort());
        posterService.update(posterNew);
        poster.setSort(sortNew);
        posterService.update(poster);
    }
}
