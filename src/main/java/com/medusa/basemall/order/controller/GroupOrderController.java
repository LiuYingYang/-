package com.medusa.basemall.order.controller;


import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.ActivityType;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.order.dao.OrderMapper;
import com.medusa.basemall.order.entity.Order;
import com.medusa.basemall.order.entity.OrderExtend;
import com.medusa.basemall.order.vo.GroupVo;
import com.medusa.basemall.promotion.dao.ActivityProductMapper;
import com.medusa.basemall.promotion.dao.GroupMapper;
import com.medusa.basemall.promotion.dao.GroupMemberMapper;
import com.medusa.basemall.promotion.entity.ActivityProduct;
import com.medusa.basemall.promotion.entity.GroupMember;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "所有接口")
@RestController
@RequestMapping("/GroupOrder")
@VersionManager
public class GroupOrderController {

	@Resource
	private GroupMemberMapper groupMemberMapper;

	@Resource
	private OrderMapper orderMapper;

	@Resource
	private GroupMapper groupMapper;

	@Resource
	private ActivityProductMapper activityProductMapper;

	@ApiOperation(value = "我的拼团", tags = "查询接口")
	@ApiResponses({@ApiResponse(code = 100, message = "success", response = Order.class, responseContainer = "List"),})
	@GetMapping("/v1/selectMineGroupOrder")
	public Result selectMineGroupOrder(Long wxuserId, Integer groupState) {
		Map<String, Object> map = new HashMap<>();
		map.put("wxuserId", wxuserId);
		map.put("groupState", groupState);
		List<OrderExtend> orderExtends = orderMapper.selectByWxuserIdAndGroupState(map);
		return ResultGenerator.genSuccessResult(orderExtends);
	}

	@ApiOperation(value = "团详情", tags = "查询接口")
	@ApiResponses({
			@ApiResponse(code = 100, message = "success", response = GroupVo.class, responseContainer = "GroupVo"),})
	@GetMapping("/v1/selectGroupDetials")
	public Result selectGroupDetials(Integer groupMemberId, Long productId) {
		GroupMember groupMember = groupMemberMapper.selectByPrimaryKey(groupMemberId);
		GroupVo groupVo = groupMapper.selectByGroupId(groupMember.getGroupId());
		Map<String, Object> map = new HashMap<>();
		map.put("productId", productId);
		map.put("activityType", ActivityType.GROUP);
		map.put("activityId", groupVo.getActivityGroup().getActivityGroupId());
		ActivityProduct activityProduct = activityProductMapper.selectGroupActivityProduct(map);
		groupVo.setActivityProduct(activityProduct);
		return ResultGenerator.genSuccessResult(groupVo);
	}
}
