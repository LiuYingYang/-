package com.medusa.basemall.promotion.controller;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.VersionNumber;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.integral.vo.FindProductVo;
import com.medusa.basemall.product.vo.ProductSimpleVo;
import com.medusa.basemall.promotion.dao.ActivityProductMapper;
import com.medusa.basemall.promotion.dao.CouponMapper;
import com.medusa.basemall.promotion.entity.ActivityCoupon;
import com.medusa.basemall.promotion.entity.Coupon;
import com.medusa.basemall.promotion.entity.CouponWxuser;
import com.medusa.basemall.promotion.service.ActivityCouponService;
import com.medusa.basemall.promotion.service.CouponService;
import com.medusa.basemall.promotion.service.CouponWxuserService;
import com.medusa.basemall.promotion.vo.ActivityCouponVo;
import com.medusa.basemall.promotion.vo.CouponWxuserVo;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 优惠券活动相关
 *
 * @author Created by psy on 2018/05/30.
 */
@Api(tags = "所有接口")
@RestController
@RequestMapping("/ActivityCoupon")
@VersionManager(versoinNumber = VersionNumber.BASICVERSION)
public class ActivityCouponController {

    @Resource
    private ActivityCouponService activityCouponService;

    @Resource
    private CouponWxuserService couponWxuserService;

    @Resource
    private CouponMapper couponMapper;

    @Resource
    private CouponService couponService;

    @Resource
    private ActivityProductMapper activityProductMapper;


    @ApiOperation(value = "查询可供优惠券活动选择的商品", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100, message = "success", response = ProductSimpleVo.class, responseContainer = "List"),})
    @GetMapping("/v1/findProductForCoupon")
    public Result findProductForCoupon(FindProductVo findProductVo) {
        return activityCouponService.findProductForCoupon(findProductVo);
    }

    @ApiOperation(value = "创建优惠券活动", tags = "添加接口")
    @PostMapping("/v1/save")
    public Result save(
            @ApiParam(value = "优惠券活动对象(带优惠券,商品(根据是否对全部商品有效而定))", required = true)
            @RequestBody JSONObject jsonObject) {
        ActivityCouponVo activityCouponVo = JSONObject
                .parseObject(jsonObject.getString("activityCouponVo"), ActivityCouponVo.class);
        if (activityCouponVo.getCouponList().size() < 1 || activityCouponVo.getCouponList() == null) {
            return ResultGenerator.genFailResult("活动必须有优惠券,无法创建");
        }
        for (Coupon coupon : activityCouponVo.getCouponList()) {
            if (coupon.getLimitQuantity() > coupon.getStockQuantity()) {
                return ResultGenerator.genFailResult("优惠券设置有误");
            }
        }
        return activityCouponService.saveActivityCoupon(activityCouponVo);
    }

    @ApiOperation(value = "查询优惠券活动", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100, message = "success", response = ActivityCouponVo.class, responseContainer = "List"),})
    @GetMapping("/v1/findByAppmodelId")
    public Result findByAppmodelId(@ApiParam(value = "模板id", required = true) String appmodelId) {
        List<ActivityCouponVo> list = activityCouponService.findByAppmodelId(appmodelId);
        return ResultGenerator.genSuccessResult(list);
    }

    @ApiOperation(value = "批量删除优惠券活动", tags = "更新接口")
    @PutMapping("/v1/batchDelete")
    public Result batchDelete(@ApiParam(value = "activityCouponIdsGet(优惠券活动id字符串)") @RequestBody JSONObject jsonObject) {

	    int result = activityCouponService
			    .batchDelete(jsonObject.getString("activityCouponIdsGet"), jsonObject.getString("appmodelId"));
        if (result > 0) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }

    @ApiOperation(value = "更新优惠券活动", tags = "更新接口")
    @PutMapping("/v1/update")
    public Result update(@ApiParam(value = "优惠券活动对象(带优惠券,商品(根据是否对全部商品有效而定))", required = true)
                         @RequestBody JSONObject jsonObject) {
        ActivityCouponVo activityCouponVo = JSONObject
                .parseObject(jsonObject.getString("activityCouponVo"), ActivityCouponVo.class);
        return activityCouponService.updateActivityCoupon(activityCouponVo);
    }

    @ApiOperation(value = "用户领取优惠券", tags = "添加接口")
    @PostMapping("/v1/wxuserObtainCoupon")
    public Result wxuserObtainCoupon(
            @ApiParam(value = "用户优惠券对象", required = true) @RequestBody CouponWxuser couponWxuser) {
        ActivityCoupon activityCoupon = activityCouponService.findById(couponWxuser.getActivityCouponId());
        if (activityCoupon.getNowState() == 0) {
            return ResultGenerator.genFailResult("优惠券已失效无法领取");
        }
        return couponWxuserService.wxuserObtainCoupon(couponWxuser);
    }

    @ApiOperation(value = "查询用户领取的优惠券", tags = "查询接口")
    @ApiResponses({@ApiResponse(code = 100, message = "success", response = CouponWxuser.class, responseContainer = "List"),})
    @GetMapping("/v1/findWxuserCoupon")
    public Result findWxuserCoupon(CouponWxuserVo couponWxuserVo) {
        List<CouponWxuser> couponWxusers = couponWxuserService.findWxuserCoupons(couponWxuserVo);
        return ResultGenerator.genSuccessResult(couponWxusers);
    }

    @ApiOperation(value = "查询用户可用优惠券", tags = "查询接口")
    @ApiResponses({@ApiResponse(code = 100, message = "success", response = Coupon.class, responseContainer = "List"),})
    @GetMapping("/v1/findWxuserCouponCanUse")
    public Result findWxuserCouponCanUse(CouponWxuserVo couponWxuserVo) {
	    List<CouponWxuser> wxuserCouponCanUse = couponWxuserService.findWxuserCouponCanUse(couponWxuserVo);
        return ResultGenerator.genSuccessResult(wxuserCouponCanUse);
    }

    @ApiOperation(value = "小程序首页优惠券显示", tags = "查询接口")
    @ApiResponses({@ApiResponse(code = 100, message = "success", response = Coupon.class, responseContainer = "List"),})
    @GetMapping("/v1/findCouponByAppmodelId")
    public Result findCouponByAppmodelId(@ApiParam(value = "模板id") String appmodelId,
                                         @ApiParam(value = "用户id") Long wxuserId) {
        List<Coupon> list = couponService.findByAppmodelId(appmodelId, wxuserId);
        return ResultGenerator.genSuccessResult(list);
    }

    @ApiOperation(value = "小程序商品详情页或购物车优惠券显示", tags = "查询接口")
    @ApiResponses({@ApiResponse(code = 100, message = "success", response = Coupon.class, responseContainer = "List"),})
    @GetMapping("/v1/findCouponByProductId")
    public Result findCouponByProductId(@ApiParam(value = "商品id字符串", required = true) String productIds,
                                        @ApiParam(value = "模板id", required = true) String appmodelId,
                                        @ApiParam(value = "用户id") Long wxuserId) {
        List<Coupon> list = couponService.findCouponByProductIds(productIds, appmodelId, wxuserId);
        return ResultGenerator.genSuccessResult(list);
    }

    @ApiOperation(value = "活动添加优惠券", tags = "添加接口")
    @PostMapping("/v1/addCoupon")
    public Result addCoupon(Coupon coupon) {
        coupon.setUsedQuantity(0);
        coupon.setObtainQuantity(0);
        coupon.setDeleteState(false);
        coupon.setSourceType(1);
        int result = couponMapper.insertSelective(coupon);
        if (result > 0) {
            return ResultGenerator.genSuccessResult("添加成功");
        } else {
            return ResultGenerator.genFailResult("添加失败");
        }
    }

    @ApiOperation(value = "活动删除优惠券", tags = "删除接口")
    @DeleteMapping("/v1/deleteCoupon")
    public Result deleteCoupon(@RequestParam Integer couponId) {
        Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
        List<Coupon> coupons = couponMapper.selectByActivityCouponId(coupon.getActivityCouponId());
        if (coupons.size() > 1) {
            int result = couponMapper.deleteByPrimaryKey(coupon);
            if (result > 0) {
                return ResultGenerator.genSuccessResult("删除成功");
            } else {
                return ResultGenerator.genFailResult("删除失败");
            }
        } else {
            return ResultGenerator.genFailResult("活动必须存在优惠券,无法删除");
        }
    }
}
