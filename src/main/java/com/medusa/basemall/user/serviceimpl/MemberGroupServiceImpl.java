package com.medusa.basemall.user.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.user.dao.MemberGroupCategoryMapper;
import com.medusa.basemall.user.dao.MemberGroupMapper;
import com.medusa.basemall.user.entity.MemberGroup;
import com.medusa.basemall.user.service.MemberGroupService;
import com.medusa.basemall.utils.TimeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by medusa on 2018/05/26.
 * 需要事物时添加  @Transactional
 */

@Service

public class MemberGroupServiceImpl extends AbstractService<MemberGroup> implements MemberGroupService {

    @Resource
    private MemberGroupMapper tMemberGroupMapper;

    @Resource
    private MemberGroupCategoryMapper tMemberGroupCategoryMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result createGroup(MemberGroup memberGroup) {
        memberGroup.setCreateTime(TimeUtil.getNowTime());
        return tMemberGroupMapper.insertSelective(memberGroup) > 0 ? ResultGenerator.genSuccessResult(memberGroup) : ResultGenerator.genFailResult("添加失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result deleteGroup(MemberGroup memberGroup) {
        if (tMemberGroupMapper.delete(memberGroup) > 0) {
            //移除分组下得所有会员
            tMemberGroupCategoryMapper.deleteGroup(memberGroup.getGroupId());
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("删除分组失败");
    }

    @Override
    public Result selectByAppmodelId(MemberGroup memberGroup) {
        return ResultGenerator.genSuccessResult(tMemberGroupMapper.select(memberGroup));
    }
}
