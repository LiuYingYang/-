package com.medusa.basemall.integral.controller;


import com.github.pagehelper.PageInfo;
import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.VersionNumber;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.integral.dao.PrizeMapper;
import com.medusa.basemall.integral.dao.PrizeOrderMapper;
import com.medusa.basemall.integral.entity.Prize;
import com.medusa.basemall.integral.entity.PrizeOrder;
import com.medusa.basemall.integral.entity.PrizeSurvey;
import com.medusa.basemall.integral.service.PrizeOrderService;
import com.medusa.basemall.integral.vo.PrizeSurveyVo;
import com.medusa.basemall.utils.TimeUtil;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 积分商城经营概况
 *
 * @author Created by wx on 2018/06/06.
 */
@Api(tags = "所有接口")
@RestController
@RequestMapping("/prize/survey")
@VersionManager(versoinNumber = VersionNumber.STANDARDVERSION)
public class PrizeSurveyController {

    @Resource
    PrizeOrderMapper prizeOrderMapper;

    @Resource
    PrizeMapper prizeMapper;

    @Resource
    PrizeOrderService prizeOrderService;

    @ApiOperation(value = "热销商品", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100,message = "success",response = Prize.class,responseContainer = "List"),
    })
    @GetMapping("/v1/selectHotProduct")
    public Result selectHotProduct(@ApiParam(value = "模板id")@RequestParam String appmodelId) {
        List<Prize> prizes = prizeMapper.selectHotProduct(appmodelId);
        return ResultGenerator.genSuccessResult(prizes);
    }

    @ApiOperation(value = "交易记录", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100,message = "success",response = PrizeOrder.class,responseContainer = "List"),
    })
    @GetMapping("/v1/selectPrizeOrder")
    public Result selectPrizeOrder(@ApiParam(value = "当前页数", required = true)@RequestParam Integer pageNum,
                                    @ApiParam(value = "每页数量", required = true)@RequestParam Integer pageSize,
                                    @ApiParam(value = "模板id", required = true)@RequestParam String appmodelId) {
        PageInfo pageInfo = prizeOrderService.selectPrizeOrder(pageNum, pageSize, appmodelId);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @ApiOperation(value = "营销统计", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100,message = "success",response = PrizeSurvey.class,responseContainer = "List"),
    })
    @GetMapping("/v1/selectCount")
    public Result selectCount(PrizeSurveyVo prizeSurveyVo) {
        PrizeSurvey prizeSurvey = new PrizeSurvey();
        Integer allOverOrder = prizeOrderMapper.selectCountAllOverOrder(prizeSurveyVo);
        prizeSurvey.setAllOverOrder(allOverOrder);
        Integer allOverProductOrder = prizeOrderMapper.selectCountAllOverProductOrder(prizeSurveyVo);
        prizeSurvey.setAllOverProductOrder(allOverProductOrder);
        Integer allOverCouponOrder = prizeOrderMapper.selectCountAllOverCouponOrder(prizeSurveyVo);
        prizeSurvey.setAllOverCouponOrder(allOverCouponOrder);
        Integer allIntegral = prizeOrderMapper.selectSumAllIntegral(prizeSurveyVo);
        prizeSurvey.setAllIntegral(allIntegral);
        return ResultGenerator.genSuccessResult(prizeSurvey);
    }

    /**
     * 实时概况
     */
    @ApiOperation(value = "实时概况", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100,message = "success",response = PrizeSurvey.class,responseContainer = "List"),
    })
    @GetMapping("/v1/selectRealTime")
    public Result selectRealTime(@ApiParam(value = "模板id", required = true)@RequestParam String appmodelId) {
        PrizeSurvey prizeSurvey = new PrizeSurvey();
        String nowTime = TimeUtil.getNowTime();
        String realTime = nowTime.substring(0, 10);
        prizeSurvey.setNowTime(nowTime);
        PrizeSurveyVo prizeSurveyVo = new PrizeSurveyVo();
        prizeSurveyVo.setRealTime(realTime);
        prizeSurveyVo.setAppmodelId(appmodelId);
        Integer todayOverOrder = prizeOrderMapper.selectCountTodayOverOrder(prizeSurveyVo);
        prizeSurvey.setTodayOverOrder(todayOverOrder);
        Integer todayWaitOrder = prizeOrderMapper.selectCountTodayWaitOrder(prizeSurveyVo);
        prizeSurvey.setTodayWaitOrder(todayWaitOrder);
        Integer todayCouponOrder = prizeOrderMapper.selectCountTodayCouponOrder(prizeSurveyVo);
        prizeSurvey.setTodayCouponOrder(todayCouponOrder);
        Integer todayIntegral = prizeOrderMapper.selectSumTodayIntegral(prizeSurveyVo);
        prizeSurvey.setTodayIntegral(todayIntegral);
        List<Prize> urgentPrizes = prizeMapper.selectUrgentPrizes(prizeSurveyVo.getAppmodelId());
        prizeSurvey.setUrgentPrizes(urgentPrizes);
        prizeSurvey.setUrgentPrizeNum(urgentPrizes.size());
        return ResultGenerator.genSuccessResult(prizeSurvey);
    }
}
