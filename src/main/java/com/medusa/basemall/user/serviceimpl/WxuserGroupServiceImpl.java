package com.medusa.basemall.user.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.user.dao.WxuserGroupCategoryMapper;
import com.medusa.basemall.user.dao.WxuserGroupMapper;
import com.medusa.basemall.user.entity.WxuserGroup;
import com.medusa.basemall.user.service.WxuserGroupService;
import com.medusa.basemall.utils.TimeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by medusa on 2018/06/06.
 * 需要事物时添加  @Transactional
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class WxuserGroupServiceImpl extends AbstractService<WxuserGroup> implements WxuserGroupService {
    @Resource
    private WxuserGroupMapper tWxuserGroupMapper;

    @Resource
    private WxuserGroupCategoryMapper tWxuserGroupCategoryMapper;

    @Override
    public Result createGroup(WxuserGroup wxuserGroup) {
        wxuserGroup.setCreateTime(TimeUtil.getNowTime());
        return tWxuserGroupMapper.insertSelective(wxuserGroup) > 0 ? ResultGenerator.genSuccessResult(wxuserGroup) : ResultGenerator.genFailResult("添加失败");
    }

    @Override
    public Result deleteGroup(WxuserGroup wxuserGroup) {
        if (tWxuserGroupMapper.delete(wxuserGroup) > 0) {
            //移除分组下得所有会员
            tWxuserGroupCategoryMapper.deleteGroup(wxuserGroup.getWxuserGroupId());
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("删除失败");
    }

    @Override
    public Result selectByAppmodelId(WxuserGroup group) {
        return ResultGenerator.genSuccessResult(tWxuserGroupMapper.select(group));
    }
}
