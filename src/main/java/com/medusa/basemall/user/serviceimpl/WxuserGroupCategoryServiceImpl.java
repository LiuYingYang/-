package com.medusa.basemall.user.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.user.dao.WxuserGroupCategoryMapper;
import com.medusa.basemall.user.entity.Wxuser;
import com.medusa.basemall.user.entity.WxuserGroup;
import com.medusa.basemall.user.entity.WxuserGroupCategory;
import com.medusa.basemall.user.service.WxuserGroupCategoryService;
import com.medusa.basemall.utils.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by medusa on 2018/06/06.
 * 需要事物时添加  @Transactional
 */

@Service

public class WxuserGroupCategoryServiceImpl extends AbstractService<WxuserGroupCategory> implements WxuserGroupCategoryService {
    @Resource
    private WxuserGroupCategoryMapper tWxuserGroupCategoryMapper;


    @Override
    public Result setWxuserToGroup(Map<String, Object> param) {
        //删除会员原本所在的所有分组
        List<Wxuser> wxusers = (List<Wxuser>) param.get("wxusers");
        if (wxusers != null) {
            tWxuserGroupCategoryMapper.deleteWxuserId(wxusers);
            //批量添加会员分组
            wxusers.forEach(obj -> {
                Map<String, Object> map = new HashMap<>(4);
                map.put("wxuserId", obj.getWxuserId());
                map.put("appmodelId", obj.getAppmodelId());
                map.put("createTime", TimeUtil.getNowTime());
                List<Integer> groupIds = new ArrayList<>();
                List<WxuserGroup> groups = (List<WxuserGroup>) param.get("wxuserGroups");
                groups.forEach(group -> {
                    groupIds.add(group.getWxuserGroupId());
                });
                map.put("groupIds", groupIds);
                if (tWxuserGroupCategoryMapper.setWxuserToGroup(map) == 0) {
                    throw new RuntimeException("分组修改失败");
                }
            });
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("设置失败");
    }
}
