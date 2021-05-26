package com.medusa.basemall.integral.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.ActiviMqQueueName;
import com.medusa.basemall.constant.TimeType;
import com.medusa.basemall.constant.VersionNumber;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.core.ServiceException;
import com.medusa.basemall.integral.entity.PrizeOrder;
import com.medusa.basemall.integral.service.PrizeOrderService;
import com.medusa.basemall.integral.vo.PrizeOrderListVO;
import com.medusa.basemall.integral.vo.PrizeOrderVo;
import com.medusa.basemall.integral.vo.SavePrizeOrderVO;
import com.medusa.basemall.jobhandler.ActiveDelaySendJobHandler;
import com.medusa.basemall.utils.TimeUtil;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


/**
 * 积分商品订单
 *
 * @author Created by wx on 2018/06/06.
 */
@Api(tags = "所有接口")
@RestController
@RequestMapping("/prize/order")
@VersionManager(versoinNumber = VersionNumber.STANDARDVERSION)
public class PrizeOrderController {
	@Autowired
	private PrizeOrderService prizeOrderService;
	@Resource
	private ActiveDelaySendJobHandler activeDelaySendJobHandler;

	@ApiOperation(value = "添加积分订单", tags = "添加接口")
	@PostMapping("/v1/saveOrder")
	public Result saveOrder(
			@ApiParam(value = "积分订单对象", required = true) @RequestBody SavePrizeOrderVO savePrizeOrderVO) {
		if (savePrizeOrderVO.getOrderType() == 1) {
			return prizeOrderService.saveProductOrder(savePrizeOrderVO);
		} else {
			return prizeOrderService.saveCouponOrder(savePrizeOrderVO);
		}
	}

	/**
	 * 支付回调函数
	 *
	 * @param request
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/Notify")
	@ResponseBody
	@VersionManager(enable = false)
	public String notify(HttpServletRequest request) throws Exception {
		String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
		return prizeOrderService.notify(xmlResult);
	}


	@ApiOperation(value = "查询积分订单(可根据买家昵称查询)", tags = "查询接口")
	@GetMapping("/v1/findOrderByAppmodelId")
	public Result<List<PrizeOrderListVO>> findOrderByAppmodelId(PrizeOrderVo prizeOrderVo) {
		PageHelper.startPage(prizeOrderVo.getPageNum(), prizeOrderVo.getPageSize());
		List<PrizeOrderListVO> prizeOrders = prizeOrderService.findByAppmodelId(prizeOrderVo);
		return ResultGenerator.genSuccessResult(new PageInfo(prizeOrders));
	}

	@ApiOperation(value = "批量备注订单", tags = "更新接口")
	@PutMapping("/v1/batchUpdateRemark")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "String", name = "prizeOrderIds", value = "积分商品订单id字符串", required = true),
			@ApiImplicitParam(paramType = "String", name = "backRemark", value = "备注内容", required = true)})
	public Result batchUpdateRemark(@ApiParam(hidden = true) @RequestBody JSONObject object) {
		String prizeOrderIds = object.getString("prizeOrderIds");
		String backRemark = object.getString("backRemark");
		String[] prizeOrderId = prizeOrderIds.split(",");
		for (int i = 0; i < prizeOrderId.length; i++) {
			PrizeOrder prizeOrder = prizeOrderService.findById(Long.valueOf(prizeOrderId[i]));
			prizeOrder.setBackRemark(backRemark);
			prizeOrderService.update(prizeOrder);
		}
		return ResultGenerator.genSuccessResult();
	}

	@ApiOperation(value = "批量发货", tags = "更新接口")
	@PutMapping("/v1/batchUpdateState")
	public Result batchUpdateState(
			@ApiParam(value = "积分商品订单对象数组", required = true) @RequestBody JSONObject jsonObject) {
		List<PrizeOrder> prizeOrderGet = jsonObject.getJSONArray("prizeOrderGet").toJavaList(PrizeOrder.class);
		if (prizeOrderGet != null && prizeOrderGet.size() > 0) {
			for (PrizeOrder prizeOrder : prizeOrderGet) {
				prizeOrder.setOrderState(2);
				prizeOrder.setSendTime(TimeUtil.getNowTime());
				prizeOrder.setUpdateTime(TimeUtil.getNowTime());
				prizeOrderService.update(prizeOrder);
				activeDelaySendJobHandler
						.savaTask(prizeOrder.getPrizeId().toString(), ActiviMqQueueName.PRIZE_ORDER, TimeType.TWENTYDAY,
								prizeOrder.getAppmodelId());
			}
		} else {
			throw new ServiceException("数据不能为空");
		}
		return ResultGenerator.genSuccessResult();
	}

	@ApiOperation(value = "修改地址/手机号/物流等信息", tags = "更新接口")
	@PutMapping("/v1/update/other")
	public Result updateaddress(@RequestBody PrizeOrder prizeOrder) {
		PrizeOrder p = new PrizeOrder();
		p.setPrizeOrderId(prizeOrder.getPrizeOrderId());
		if (prizeOrder.getAddress() != null) {
			p.setAddress(prizeOrder.getAddress());
		}
		if (prizeOrder.getTelPhone() != null) {
			p.setTelPhone(prizeOrder.getTelPhone());
		}
		if (prizeOrder.getName() != null) {
			p.setName(prizeOrder.getName());
		}
		if (prizeOrder.getWlName() != null && prizeOrder.getWlName().length() > 0) {
			p.setWlName(prizeOrder.getWlName());
			p.setWlCode(prizeOrder.getWlCode());
			p.setWlNum(prizeOrder.getWlNum());
		}
		prizeOrderService.update(p);
		return ResultGenerator.genSuccessResult();
	}


}
