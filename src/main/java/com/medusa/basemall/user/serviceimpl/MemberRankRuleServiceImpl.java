package com.medusa.basemall.user.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.user.dao.MemberRankMapper;
import com.medusa.basemall.user.dao.MemberRankRuleMapper;
import com.medusa.basemall.user.entity.MemberRank;
import com.medusa.basemall.user.entity.MemberRankRule;
import com.medusa.basemall.user.service.MemberRankRuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 *
 * @author medusa
 * @date 2018/05/29
 * 需要事物时添加  @Transactional
 */

@Service
public class MemberRankRuleServiceImpl extends AbstractService<MemberRankRule> implements MemberRankRuleService {
	@Resource
	private MemberRankRuleMapper tMemberRankRuleMapper;

	@Resource
	private MemberRankMapper tMemberRankMapper;


	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result createRankRule(MemberRankRule memberRankRule) {
		MemberRankRule rankRule = new MemberRankRule();
		rankRule.setAppmodelId(memberRankRule.getAppmodelId());
		if (tMemberRankRuleMapper.selectOne(rankRule) != null) {
			return ResultGenerator.genFailResult("只能创建一条会员卡规则");
		}
		return tMemberRankRuleMapper.insertSelective(memberRankRule) > 0 ?
				ResultGenerator.genSuccessResult() :
				ResultGenerator.genFailResult("添加失败");
	}

	@Override
	public Result updateMemberRankRule(MemberRankRule memberRankRule) {
		if (null != memberRankRule.getMemberRuleId()) {
			tMemberRankRuleMapper.updateByPrimaryKeySelective(memberRankRule);
			if (null != memberRankRule.getRanks() && memberRankRule.getRanks().size() > 0) {
				List<MemberRank> ranks = memberRankRule.getRanks();
				ranks.forEach(obj -> {
					tMemberRankMapper.updateByPrimaryKeySelective(obj);
				});
			}
			return ResultGenerator.genSuccessResult();
		}
		return ResultGenerator.genFailResult("参数有误");
	}

	@Override
	public MemberRankRule detail(String appmodelId) {
		MemberRankRule rankRule = new MemberRankRule();
		rankRule.setAppmodelId(appmodelId);
		MemberRankRule newRankRule = tMemberRankRuleMapper.selectOne(rankRule);
		if (newRankRule != null) {
			MemberRank memberRank = new MemberRank();
			memberRank.setAppmodelId(appmodelId);
			List<MemberRank> select = tMemberRankMapper.select(memberRank);
			newRankRule.setRanks(select);
		}else{
			newRankRule = new MemberRankRule();
		}
		return newRankRule;
	}


}
