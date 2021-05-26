package com.medusa.basemall.user.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.user.dao.MemberActiveRechargeMapper;
import com.medusa.basemall.user.entity.MemberActiveRecharge;
import com.medusa.basemall.user.service.MemberActiveRechargeService;
import com.medusa.basemall.utils.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * @author medusa
 * @date 2018/05/30
 * 需要事物时添加  @Transactional
 */

@Service
public class MemberActiveRechargeServiceImpl extends AbstractService<MemberActiveRecharge> implements MemberActiveRechargeService {
    @Resource
    private MemberActiveRechargeMapper tMemberActiveRechargeMapper;


    @Override
    public Result createRecharge(MemberActiveRecharge memberActiveRecharge) {
        //根据达标价格和赠送价格查出商家所有充值活动
        if (equalRecharge(memberActiveRecharge)) {
            return ResultGenerator.genFailResult("相同充值活动已存在");
        }
        memberActiveRecharge.setCreateTime(TimeUtil.getNowTime());
        if (tMemberActiveRechargeMapper.insertSelective(memberActiveRecharge) > 0) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("充值活动添加失败");
    }

    //判断是否存在相同价格
    private boolean equalRecharge(MemberActiveRecharge memberActiveRecharge) {
	    MemberActiveRecharge recharge = new MemberActiveRecharge();
        recharge.setAppmodelId(memberActiveRecharge.getAppmodelId());
        recharge.setSendPrice(memberActiveRecharge.getSendPrice());
        recharge.setMaxPrice(memberActiveRecharge.getMaxPrice());
        if (tMemberActiveRechargeMapper.selectOne(recharge) != null) {
            return true;
        }
        return false;
    }

    @Override
    public Result updateRecharge(MemberActiveRecharge memberActiveRecharge) {
        if (equalRecharge(memberActiveRecharge)) {
            return ResultGenerator.genFailResult("相同充值活动已存在");
        }
        if (tMemberActiveRechargeMapper.updateByPrimaryKeySelective(memberActiveRecharge)>0) {
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("充值活动更新失败");
    }

    @Override
    public Result selectAppmodelId(String appmodelId) {
	    MemberActiveRecharge recharge = new MemberActiveRecharge();
        recharge.setAppmodelId(appmodelId);
        return ResultGenerator.genSuccessResult(tMemberActiveRechargeMapper.select(recharge));
    }

    @Override
    public Result OpenOrCloseActive(String appmodelId, int type) {
        Map<String,Object> map = new HashMap<>(2);
        map.put("appmodelId",appmodelId);
        map.put("type",type);
        if (tMemberActiveRechargeMapper.OpenOrCloseActive(map) >0) {
            return ResultGenerator.genSuccessResult();
        }
        return  ResultGenerator.genFailResult("操作成功");
    }
}
