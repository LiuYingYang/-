package com.medusa.basemall.shop.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.shop.dao.NoticeMapper;
import com.medusa.basemall.shop.entity.Notice;
import com.medusa.basemall.shop.service.NoticeService;
import com.medusa.basemall.utils.TimeUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 公告
 *
 * @author Created by wx on 2018/05/24.
 */
@Api(tags = "所有接口")
@RequestMapping("/Notice")
@RestController
@VersionManager
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @Resource
    private NoticeMapper noticeMapper;

    @ApiOperation(value = "保存或更新店铺公告", tags = "添加接口")
    @PostMapping("/v1/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody Notice notice) {
        if (notice.getNoticeId() == null) {
            notice.setCreateTime(TimeUtil.getNowTime());
            noticeService.save(notice);
        } else {
            notice.setCreateTime(TimeUtil.getNowTime());
            noticeService.update(notice);
        }
        return ResultGenerator.genSuccessResult();
    }


    @ApiOperation(value = "根据appmodelId查询店铺公告", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100, message = "success", response = Notice.class, responseContainer = "List"),})
    @GetMapping("/v1/findByAppmodelId")
    public Result findByAppmodelId(@RequestParam String appmodelId,
                                   @RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> list = noticeMapper.selectByAppmodelId(appmodelId);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }


    @ApiOperation(value = "根据id查询店铺公告", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100, message = "success", response = Notice.class, responseContainer = "NoticeVO"),})
    @GetMapping("/v1/findById")
    public Result findById(Integer noticeId) {
        Notice notice = noticeService.findById(noticeId);
        return ResultGenerator.genSuccessResult(notice);
    }


    @ApiOperation(value = "删除店铺公告(可批量)", tags = "删除接口")
    @DeleteMapping("/v1/batchDelete")
    public Result batchDelete(@ApiParam(value = "店铺公告id字符串") @RequestParam String noticeIds) {
        String[] noticeId = noticeIds.split(",");
        int result = noticeService.batchDelete(noticeId);
        if (result > 0) {
            return ResultGenerator.genSuccessResult("删除成功");
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }
}
