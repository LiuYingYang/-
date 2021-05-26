package com.medusa.basemall.integral.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.PrizeState;
import com.medusa.basemall.constant.VersionNumber;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.integral.entity.Prize;
import com.medusa.basemall.integral.service.PrizeService;
import com.medusa.basemall.integral.vo.PrizeParamVO;
import com.medusa.basemall.integral.vo.PrizeVo;
import com.medusa.basemall.promotion.entity.Coupon;
import com.medusa.basemall.promotion.service.CouponService;
import com.medusa.basemall.utils.TimeUtil;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 积分商品
 *
 * @author Created by wx on 2018/06/06.
 */
@Api(tags = "所有接口")
@RestController
@RequestMapping("/prize")
@VersionManager(versoinNumber = VersionNumber.STANDARDVERSION)
public class PrizeController {

	@Resource
	private PrizeService prizeService;
	@Resource
	private CouponService couponService;

	@ApiOperation(value = "添加积分商品", tags = "添加接口")
	@PostMapping("/v1/addProduct")
	public Result addProduct(@ApiParam(value = "积分商品对象", required = true) @RequestBody Prize prize) {
		prize.setDeleteState(false);
		prize.setSalesVolume(0);
		prize.setAllIntegral(0);
		prize.setCreateTime(TimeUtil.getNowTime());
		int result = prizeService.save(prize);
		if (result > 0) {
			return ResultGenerator.genSuccessResult();
		} else {
			return ResultGenerator.genFailResult("保存失败");
		}
	}

	@ApiOperation(value = "更新积分商品", tags = "更新接口")
	@PutMapping("/v1/updateProduct")
	public Result updateProduct(@ApiParam(value = "积分商城商品对象", required = true) @RequestBody Prize prize) {
		if (prize.getPrizeStock() > 0 && prize.getState().equals(PrizeState.SELLOUT)) {
			prize.setState(PrizeState.SHELVES);
		}
		int result = prizeService.update(prize);
		if (result > 0) {
			return ResultGenerator.genSuccessResult();
		} else {
			return ResultGenerator.genFailResult("修改失败");
		}
	}

	@ApiOperation(value = "添加积分商城优惠券", tags = "添加接口")
	@PostMapping("/v1/addCoupon")
	public Result addCoupon(@ApiParam(value = "优惠券数组对象", required = true) @RequestBody JSONObject object) {
		List<Coupon> coupons = object.getJSONArray("coupons").toJavaList(Coupon.class);
		for (Coupon coupon : coupons) {
			coupon.setObtainQuantity(0);
			coupon.setUsedQuantity(0);
			coupon.setDeleteState(false);
			int result = couponService.save(coupon);
			if (result > 0) {
				Prize prize = new Prize();
				prize.setDeleteState(false);
				prize.setPrizeType(2);
				prize.setState(1);
				prize.setCouponId(coupon.getCouponId());
				prize.setAppmodelId(coupon.getAppmodelId());
				prize.setCreateTime(TimeUtil.getNowTime());
				prize.setPrizeStock(coupon.getStockQuantity());
				prize.setAllIntegral(0);
				prize.setSalesVolume(0);
				//优惠券类型（1---满减，2---满折，3---无门槛现金券，4---无门槛折扣）
				setPrizeName(coupon, prize);
				prizeService.save(prize);
			}
		}
		return ResultGenerator.genSuccessResult();
	}

	@ApiOperation(value = "更新积分商城优惠券", tags = "更新接口")
	@PutMapping("/v1/updateCoupon")
	public Result updateCoupon(@ApiParam(value = "积分商城优惠券对象", required = true) @RequestBody Coupon coupon) {
		Prize prize = prizeService.findByCouponId(coupon.getCouponId());
		prize.setPrizeStock(coupon.getStockQuantity());
		if (coupon.getStockQuantity() > 0) {
			if (prize.getState().equals(PrizeState.SELLOUT)) {
				prize.setState(PrizeState.SHELVES);
			}
		}
		if (coupon.getCouponType() != null && coupon.getCouponType()>0) {
			//优惠券类型（1---满减，2---满折，3---无门槛现金券，4---无门槛折扣）
			setPrizeName(coupon, prize);
		}
		prizeService.update(prize);
		int result = couponService.update(coupon);
		if (result > 0) {
			return ResultGenerator.genSuccessResult();
		} else {
			return ResultGenerator.genFailResult("修改失败");
		}
	}

	private void setPrizeName(@ApiParam(value = "积分商城优惠券对象", required = true) @RequestBody Coupon coupon, Prize prize) {
		switch (coupon.getCouponType()) {
			case 1:
				prize.setPrizeName("满减券");
				break;
			case 2:
				prize.setPrizeName("折扣券");
				break;
			case 3:
				prize.setPrizeName("无门槛现金券");
				break;
			case 4:
				prize.setPrizeName("无门槛折扣券");
				break;
			default:
				break;
		}
	}

	@ApiOperation(value = "更新积分商品/优惠券部分数据", tags = "更新接口")
	@PutMapping("/v1/update")
	public Result updateCouponOrproduct(@RequestBody PrizeParamVO prizeParamVO) {
		int i = prizeService.updateCouponOrproduct(prizeParamVO);
		if (i == 0) {
			return ResultGenerator.genFailResult("更新失败");
		}
		return ResultGenerator.genSuccessResult();
	}

	@ApiOperation(value = "查询积分商品", tags = "查询接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success", response = Prize.class, responseContainer = "List"),})
	@GetMapping("/v1/selectPrizeProduct")
	public Result selectPrizeProduct(PrizeVo prizeVo) {
		PageHelper.startPage(prizeVo.getPageNum(), prizeVo.getPageSize());
		List<Prize> prizes = prizeService.findByState(prizeVo);
		PageInfo pageInfo = new PageInfo(prizes);
		return ResultGenerator.genSuccessResult(pageInfo);
	}

	@ApiOperation(value = "查询积分商品详情", tags = "查询接口")
	@GetMapping("/v1/selectPrizeProductDetail")
	public Result selectPrizeProductDetail(@RequestParam Integer prizeId, @RequestParam String appmodelId) {
		Prize prize = prizeService.findByAppmodelIdAndPrizeId(appmodelId, prizeId);
		return ResultGenerator.genSuccessResult(prize);
	}


	@ApiOperation(value = "批量删除积分商品", tags = "更新接口")
	@DeleteMapping("/v1/batchDelete")
	public Result delete(@ApiParam(value = "积分商品id 逗号分隔", required = true) @RequestParam String prizeIds) {
		String[] prizeId = prizeIds.split(",");
		for (int i = 0; i < prizeId.length; i++) {
			Prize prize = prizeService.findById(Integer.valueOf(prizeId[i]));
			if (prize.getPrizeType() == 2) {
				Coupon coupon = couponService.findById(prize.getCouponId());
				coupon.setDeleteState(true);
				couponService.update(coupon);
			}
			prize.setDeleteState(true);
			prizeService.update(prize);
		}
		return ResultGenerator.genSuccessResult();
	}

	@ApiOperation(value = "批量上下架", tags = "更新接口")
	@PutMapping("/v1/batchUpdateState")
	public Result updateState(@RequestBody PrizeParamVO prizeParamVO) {
		String[] prizeId = prizeParamVO.getPrizeIds().split(",");
		for (int i = 0; i < prizeId.length; i++) {
			Prize prize = prizeService.findById(Integer.valueOf(prizeId[i]));
			prize.setState(prizeParamVO.getState());
			prizeService.update(prize);
		}
		return ResultGenerator.genSuccessResult();
	}
}
