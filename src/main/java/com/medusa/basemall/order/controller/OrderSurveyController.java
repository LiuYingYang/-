package com.medusa.basemall.order.controller;


import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.VersionNumber;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.order.dao.*;
import com.medusa.basemall.order.entity.Buycar;
import com.medusa.basemall.order.entity.CommonlyUsed;
import com.medusa.basemall.order.entity.OrderSurvey;
import com.medusa.basemall.order.service.BuycarService;
import com.medusa.basemall.order.vo.OrderSurveyVo;
import com.medusa.basemall.product.dao.ProductMapper;
import com.medusa.basemall.product.entity.Product;
import com.medusa.basemall.product.vo.ProductHotVo;
import com.medusa.basemall.utils.TimeUtil;
import com.vip.vjtools.vjkit.mapper.BeanMapper;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 经营概况
 *
 * @author Created by wx on 2018/08/15.
 */
@Api(tags = "所有接口")
@RestController
@RequestMapping("/order/survey")
@VersionManager(versoinNumber = VersionNumber.STANDARDVERSION)
public class OrderSurveyController {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private BuycarRepository buycarRepository;

    @Resource
    private BuycarService buycarService;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private OrderRefoundMapper orderRefoundMapper;

    @Resource
    private OrderDetailMapper orderDetailMapper;

    @Resource
    private CommonlyUsedRepository commonlyUsedRepository;

    /**
     * 营销统计
     */
    @ApiOperation(value = "营销统计", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100, message = "success", response = OrderSurvey.class, responseContainer = "List"),
    })
    @GetMapping("/v1/selectCount")
    public Result selectCount(OrderSurveyVo orderSurveyVo) {
        OrderSurvey orderSurvey = new OrderSurvey();
        orderSurvey.setAllOverOrder(orderMapper.selectCountSuccessful(orderSurveyVo));
        orderSurvey.setAllPrice(orderMapper.selectSumSuccessful(orderSurveyVo));
	    String allNumber = buycarService.selectJoinBuycarSum(orderSurveyVo.getStartDate(), orderSurveyVo.getEndDate(),
			    orderSurveyVo.getAppmodelId());
	    orderSurvey.setAllBuyCarNumber(new Integer(allNumber));
        return ResultGenerator.genSuccessResult(orderSurvey);
    }


    /**
     * 实时概况
     */
    @ApiOperation(value = "实时概况", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100, message = "success", response = OrderSurvey.class, responseContainer = "List"),
    })
    @GetMapping("/v1/selectRealTime")
    public Result selectRealTime(@ApiParam(value = "模板id", required = true) @RequestParam String appmodelId) {
        OrderSurvey orderSurvey = new OrderSurvey();
        String nowTime = TimeUtil.getNowTime();
        orderSurvey.setNowTime(nowTime);
        String realTime = nowTime.substring(0, 10);
        OrderSurveyVo orderSurveyVo = new OrderSurveyVo();
        orderSurveyVo.setRealTime(realTime);
        orderSurveyVo.setAppmodelId(appmodelId);

	    orderSurvey.setTodayWaitOrder(orderMapper.selectTodayWaitOrder(orderSurveyVo));
	    orderSurvey.setTodayOverOrder(orderMapper.selectTodayOverOrder(orderSurveyVo));
	    orderSurvey.setTodayPrice(orderMapper.selectSumTodayPrice(orderSurveyVo));
	    orderSurvey.setTodayRefoundOrder(orderRefoundMapper.selectTodayRefoundOrder(orderSurveyVo));

        List<Buycar> buycarList= buycarService.selectAllBuyCarNumber(orderSurveyVo);
        Integer allNumber = buycarList.stream().collect(Collectors.summingInt(Buycar::getQuantity));
        orderSurvey.setTodayBuyCarNumber(allNumber);
        orderSurvey.setBuycarList(buycarList);

        List<Product> urgentProducts = productMapper.selectUrgentProducts(orderSurveyVo.getAppmodelId());
        orderSurvey.setUrgentProductNum(urgentProducts.size());
        orderSurvey.setUrgentProducts(urgentProducts);

        return ResultGenerator.genSuccessResult(orderSurvey);
    }

    /**
     * 购物车分页
     */
    @ApiOperation(value = "今日购物车", tags = "查询接口")
    @GetMapping("/v1/selectRealTimeBuycarList")
    public Result selectRealTimeBuycarList(@ApiParam(value = "模板id") @RequestParam String appmodelId,
                                           @ApiParam(value = "当前页数") @RequestParam Integer pageNum,
                                           @ApiParam(value = "每页数量") @RequestParam Integer pageSize) {
	    Map<String, Object> buycars = buycarService.selectRealTimeBuycarList(pageNum, pageSize, appmodelId);
	    return ResultGenerator.genSuccessResult(buycars);
    }

    /**
     * 热销商品
     */
    @ApiOperation(value = "热销商品", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100,message = "success",response = ProductHotVo.class,responseContainer = "List"),
    })
    @GetMapping("/v1/selectHotProduct")
    public Result selectHotProduct(@ApiParam(value = "模板id")@RequestParam String appmodelId){
        List<Product> products = productMapper.selectHotProduct(appmodelId);
        List<ProductHotVo> productHotVos = new ArrayList<>();
        for (Product product : products) {
	        Double saleNumber = orderDetailMapper.countSaleNumber(product.getProductId(), appmodelId);
	        ProductHotVo productHotVo = BeanMapper.map(product, ProductHotVo.class);
	        productHotVo.setSaleNumber(saleNumber);
            productHotVos.add(productHotVo);
        }
        return ResultGenerator.genSuccessResult(productHotVos);
    }

    @ApiOperation(value = "根据appmodelId常用营销应用", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100,message = "success",response = CommonlyUsed.class,responseContainer = "CommonlyUsed"),
    })
    @GetMapping("/v1/selectCommonlyUsed")
    public Result selectCommonlyUsed(@ApiParam(value = "模板id")@RequestParam String appmodelId){
        CommonlyUsed commonlyUsed = commonlyUsedRepository.findByAppmodelId(appmodelId);
        if (commonlyUsed == null) {
            CommonlyUsed commonlyUsedNew = new CommonlyUsed();
            commonlyUsedNew.setAppmodelId(appmodelId);
            commonlyUsedNew.setCommonlyUseds("");
            commonlyUsedRepository.save(commonlyUsedNew);
            return ResultGenerator.genSuccessResult(commonlyUsedNew);
        }
        return ResultGenerator.genSuccessResult(commonlyUsed);
    }

    @ApiOperation(value = "常用营销应用修改", tags = "更新接口")
    @PutMapping("/v1/updateCommonlyUsed")
    public Result updateCommonlyUsed(@RequestBody CommonlyUsed commonlyUsed){
	    commonlyUsed.setCommonlyUseds(Joiner.on(",").skipNulls()
			    .join(Splitter.on(",").omitEmptyStrings().split(commonlyUsed.getCommonlyUseds())));
	    CommonlyUsed result = commonlyUsedRepository.save(commonlyUsed);
        return ResultGenerator.genSuccessResult("保存成功");
    }
    
}