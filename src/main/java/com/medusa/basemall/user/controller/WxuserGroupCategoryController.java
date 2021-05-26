package com.medusa.basemall.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.aop.annotation.VersionManager;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.user.entity.Wxuser;
import com.medusa.basemall.user.entity.WxuserGroup;
import com.medusa.basemall.user.service.WxuserGroupCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by medusa on 2018/06/06.
 */
@RestController
@RequestMapping("/wxuser/group/category")
@Api(tags = {"所有接口"})
@VersionManager
public class WxuserGroupCategoryController {

    @Resource
    private WxuserGroupCategoryService wxuserGroupCategoryService;

    /**
     * 设置/修改用户分组
     * 用户数组:wxuserIds
     * 用户组数组: wxuserGroups
     * appmodelId
     */
    @PostMapping("/v1/setWxuserToGroup")
    @ApiOperation(value = "设置/修改用户分组", tags = "更新接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wxuserIds", required = true, value = "用户数组", paramType = "WxuserId[]"),
            @ApiImplicitParam(name = "wxuserGroups",  required = true, value = "用户组数组", paramType = "WxuserGroup[]"),
    })
    public Result setWxuserToGroup(@RequestBody JSONObject jsonObject) {
        List<Wxuser> wxusers = JSONObject.parseArray(jsonObject.getString("wxusers"), Wxuser.class);
        List<WxuserGroup> wxuserGroups = JSONObject.parseArray(jsonObject.getString("wxuserGroups"), WxuserGroup.class);
        Map<String, Object> map = new HashMap<>(2);
        map.put("wxusers", wxusers);
        map.put("wxuserGroups", wxuserGroups);
        return wxuserGroupCategoryService.setWxuserToGroup(map);
    }

}
