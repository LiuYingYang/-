package com.medusa.basemall.shop.controller;

import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.VersionNumber;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.integral.dao.PrizeDetailMapper;
import com.medusa.basemall.integral.entity.PrizeDetail;
import com.medusa.basemall.product.vo.ColumnFlagVo;
import com.medusa.basemall.shop.entity.ColumnFlag;
import com.medusa.basemall.shop.service.ColumnFlagService;
import com.medusa.basemall.utils.TimeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 栏目开关
 *
 * @author Created by wx on 2018/06/04.
 */
@Api(tags = "所有接口")
@RequestMapping("/ColumnFlag")
@RestController
@VersionManager
public class ColumnFlagController {

    @Resource
    private ColumnFlagService columnFlagService;

    @Resource
    private PrizeDetailMapper prizeDetailMapper;


    @ApiOperation(value = "查询栏目开关设置", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100, message = "success", response = ColumnFlag.class, responseContainer = "ColumnFlag"),})
    @GetMapping("/v1/findByAppmodelId")
    public Result findByAppmodelId(String appmodelId) {
        ColumnFlag columnFlag = columnFlagService.findByAppmodelId(appmodelId);
        return ResultGenerator.genSuccessResult(columnFlag);
    }

    @ApiOperation(value = "首页分类导航开关", tags = "更新接口")
    @PutMapping("/v1/home/page/flag")
    @VersionManager(versoinNumber = VersionNumber.BASICVERSION)
    public Result homePageFlag(@RequestBody ColumnFlagVo columnFlagVo) {
        columnFlagService.homePageFlag(columnFlagVo);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "搜索栏", tags = "更新接口")
    @PutMapping("/v1/serarchWord/flag")
    @VersionManager(versoinNumber = VersionNumber.BASICVERSION)
    public Result serarchFlag(@RequestBody ColumnFlagVo columnFlagVo) {
        columnFlagService.serarchFlag(columnFlagVo);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "店铺故事", tags = "更新接口")
    @PutMapping("/v1/shopinfo/flag")
    @VersionManager(versoinNumber = VersionNumber.BASICVERSION)
    public Result shopinfoFlag(@RequestBody ColumnFlagVo columnFlagVo) {
        columnFlagService.shopinfoFlag(columnFlagVo);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "店铺公告", tags = "更新接口")
    @PutMapping("/v1/notice/flag")
    @VersionManager(versoinNumber = VersionNumber.BASICVERSION)
    public Result noticeFlag(@RequestBody ColumnFlagVo columnFlagVo) {
        columnFlagService.noticeFlag(columnFlagVo);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "发现页", tags = "更新接口")
    @PutMapping("/v1/article/flag")
    @VersionManager(versoinNumber = VersionNumber.MARKETINGVERSION)
    public Result articleFlag(@RequestBody ColumnFlagVo columnFlagVo) {
        columnFlagService.articleFlag(columnFlagVo);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "会员", tags = "更新接口")
    @PutMapping("/v1/member/flag")
    @VersionManager(versoinNumber = VersionNumber.STANDARDVERSION)
    public Result memberFlag(@RequestBody ColumnFlagVo columnFlagVo) {
        columnFlagService.memberFlag(columnFlagVo);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "会员储值", tags = "更新接口")
    @PutMapping("/v1/member/recharge/flag")
    @VersionManager(versoinNumber = VersionNumber.STANDARDVERSION)
    public Result memberRechargeFlag(@RequestBody ColumnFlagVo columnFlagVo) {
        columnFlagService.memberRechargeFlag(columnFlagVo);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "积分商城", tags = "更新接口")
    @PutMapping("/v1/integral/shop/flag")
    @VersionManager(versoinNumber = VersionNumber.STANDARDVERSION)
    public Result integralShopFlag(@RequestBody ColumnFlagVo columnFlagVo) {
        columnFlagService.integralShopFlag(columnFlagVo);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "招收代理", tags = "更新接口")
    @PutMapping("/v1/proxy/flag")
    @VersionManager(versoinNumber = VersionNumber.MARKETINGVERSION)
    public Result proxyFlag(@RequestBody ColumnFlagVo columnFlagVo) {
        columnFlagService.proxyFlag(columnFlagVo);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "底部打标", tags = "更新接口")
    @PutMapping("/v1/foot/lable/flag")
    @VersionManager(versoinNumber = VersionNumber.MARKETINGVERSION)
    public Result footFlag(@RequestBody ColumnFlagVo columnFlagVo) {
        columnFlagService.footFlag(columnFlagVo);
        return ResultGenerator.genSuccessResult();
    }

    @ApiOperation(value = "打开/关闭开关(只传id和要更新的接口)", tags = "更新接口")
    @PutMapping("/v1/updateFlag")
    @Deprecated
    public Result updateFlag(@RequestBody ColumnFlag columnFlag) {
        if (columnFlag.getShopFlag() != null && columnFlag.getShopFlag() == true) {
            List<PrizeDetail> prizeDetails = prizeDetailMapper.selectByAppmodelId(columnFlag.getAppmodelId());
            if (prizeDetails.size() > 0) {
                // 说明积分商城为重新开启
                String now = TimeUtil.getNowTime();
                Map<String, Object> map = new HashMap<>(2);
                map.put("createTime", now);
                map.put("appmodelId", columnFlag.getAppmodelId());
                prizeDetailMapper.updateCreatTime(map);
            }
        }
        //columnFlagService.integralShopFlag(columnFlagVo);
        return ResultGenerator.genSuccessResult();
    }
}



