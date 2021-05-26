package com.medusa.basemall.promotion.controller;

import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.constant.VersionNumber;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.integral.vo.FindProductVo;
import com.medusa.basemall.product.service.ProductSpecItemService;
import com.medusa.basemall.product.vo.ProductSimpleVo;
import com.medusa.basemall.promotion.dao.ActivityProductStockMapper;
import com.medusa.basemall.promotion.dao.GroupMemberMapper;
import com.medusa.basemall.promotion.entity.*;
import com.medusa.basemall.promotion.service.ActivityGroupService;
import com.medusa.basemall.promotion.service.ActivityProductService;
import com.medusa.basemall.promotion.service.GroupMemberService;
import com.medusa.basemall.promotion.service.GroupService;
import com.medusa.basemall.promotion.vo.ActivityGroupVo;
import io.swagger.annotations.*;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 团购活动相关
 *
 * @author Created by psy on 2018/05/30.
 */
@Api(tags = "所有接口")
@RestController
@RequestMapping("/ActivityGroup")
@VersionManager(versoinNumber = VersionNumber.MARKETINGVERSION)
public class ActivityGroupController {
    @Resource
    private ActivityGroupService activityGroupService;

    @Resource
    private ActivityProductService activityProductService;

    @Resource
    private GroupService groupService;

    @Resource
    private GroupMemberService groupMemberService;

    @Resource
    private ProductSpecItemService productSpecItemService;

    @Resource
    private ActivityProductStockMapper activityProductStockMapper;

    @Resource
    private GroupMemberMapper groupMemberMapper;


    @ApiOperation(value = "查询可供团购活动选择的商品", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100, message = "success", response = ProductSimpleVo.class, responseContainer = "List"),})
    @GetMapping("/v1/findProductForGroup")
    public Result findProductForGroup(FindProductVo findProductVo) {
        return activityGroupService.findProductForGroup(findProductVo);
    }

    @ApiOperation(value = "创建团购活动", tags = "添加接口")
    @PostMapping("/v1/save")
    public Result save(@RequestBody ActivityGroupVo activityGroupVo) {

        int result = activityGroupService.saveActivityGroup(activityGroupVo);

        if (result > 0) {
            return ResultGenerator.genSuccessResult(result);
        } else {
            return ResultGenerator.genFailResult("创建失败，请检查选择的商品是否与其他活动时间冲突");
        }
    }

    @ApiOperation(value = "查询团购活动", tags = "查询接口")
    @ApiResponses({
            @ApiResponse(code = 100, message = "success", response = ActivityGroupVo.class, responseContainer = "List"),})
    @GetMapping("/v1/findByAppmodelId")
    public Result findByAppmodelId(String appmodelId) {

        List<ActivityGroupVo> list = activityGroupService.findByAppmodelId(appmodelId);

        return ResultGenerator.genSuccessResult(list);
    }

    @ApiOperation(value = "批量删除团购活动", tags = "更新接口")
    @Delete("/v1/batchDelete")
    public Result batchDelete(@RequestParam String activityGroupIds,@RequestParam String appmodelId) {
        String[] activityGroupId = activityGroupIds.split(",");
        int result = activityGroupService.batchDelete(activityGroupId,appmodelId);
        if (result > 0) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }

    @ApiOperation(value = "更新团购活动", tags = "更新接口")
    @PutMapping("/v1/update")
    public Result update(@RequestBody ActivityGroupVo activityGroupVo) {

        int result = activityGroupService.updateActivityGroup(activityGroupVo);

        if (result > 0) {
            return ResultGenerator.genSuccessResult(result);
        } else {
            return ResultGenerator.genFailResult("更新失败，请检查选择的商品是否与其他活动时间冲突");
        }
    }

    @ApiOperation(value = "查询能否开团", tags = "查询接口")
    @GetMapping("/v1/openGroup")
    public Result openGroup(@ApiParam(value = "规格id") Long productSpecItemId,
                            @ApiParam(value = "活动商品id") Long activityProductId,
                            @ApiParam(value = "购买商品数量") Integer quantity) {
        // 查找活动商品
        ActivityProduct activityProduct = activityProductService.findById(activityProductId);
        // 判断活动商品是否有规格
        if (productSpecItemId != null && !"".equals(productSpecItemId)) {
            // 查找活动商品对应的状态为进行中的团活动
            ActivityGroup activityGroup = activityGroupService.selectByActivityGroupIdEnd(activityProduct.getActivityId());
            if (activityGroup != null) {
                // 查找商品对应的规格
                ActivityProductStock activityProductStockNew = new ActivityProductStock();
                activityProductStockNew.setActivityProductId(activityProductId);
                activityProductStockNew.setProductSpecItemId(productSpecItemId);
                ActivityProductStock activityProductStock = activityProductStockMapper.selectActivityProductStock(activityProductStockNew);
                if (activityProductStock.getActivityStock() - quantity > 0) {
                    return ResultGenerator.genSuccessResult("可以开团");
                } else {
                    return ResultGenerator.genFailResult("无法开团原因：库存不足");
                }
            } else {
                return ResultGenerator.genFailResult("无法开团原因：活动已结束");
            }
        } else {
            ActivityGroup activityGroup = activityGroupService.selectByActivityGroupIdEnd(activityProduct.getActivityId());
            if (activityGroup != null) {
                if (activityProduct.getActivityStock() - quantity > 0) {
                    return ResultGenerator.genSuccessResult("可以开团");
                } else {
                    return ResultGenerator.genFailResult("无法开团原因：库存不足");
                }
            } else {
                return ResultGenerator.genFailResult("无法开团原因：活动已结束");
            }
        }
    }

    @ApiOperation(value = "查询能否参团", tags = "查询接口")
    @GetMapping("/v1/joinGroup")
    public Result joinGroup(Integer groupId, Long wxuserId) {
        // 查看是否已经是这个团的团成员
        Map<String, Object> map = new HashMap<>();
        map.put("wxuserId", wxuserId);
        map.put("groupId", groupId);
        GroupMember groupMember = groupMemberMapper.findByWxuserIdAndGroupId(map);
        if (groupMember != null) {
            return ResultGenerator.genFailResult("已经团员，无法再次参团");
        }
        // 查找团成员的个数
        List<GroupMember> groupMembers = groupMemberService.findByGroupId(groupId);
        // 查找对应的团状态为未成团的团
        Group group = groupService.findByGroupId(groupId);
        if (group != null && group.getLimitNum() > groupMembers.size()) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("无法参团");
        }
    }

    @ApiOperation(value = "查询可以加入的团", tags = "查询接口")
    @GetMapping("/v1/groupToJoin")
    public Result groupToJoin(@RequestParam Group group) {
        List<Group> groups = groupService.selectGroup(group);
        return ResultGenerator.genSuccessResult(groups);
    }
}
