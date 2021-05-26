package com.medusa.basemall.user.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.user.dao.MemberRankMapper;
import com.medusa.basemall.user.entity.MemberRank;
import com.medusa.basemall.user.service.MemberRankService;
import com.medusa.basemall.utils.TimeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by medusa on 2018/05/24.
 * 需要事物时添加  @Transactional
 */

@Service

public class MemberRankServiceImpl extends AbstractService<MemberRank> implements MemberRankService {
	@Resource
	private MemberRankMapper tMemberRankMapper;


	@Override
	public Result createRank(MemberRank memberRank) {
		MemberRank Rank = new MemberRank();
		Rank.setAppmodelId(memberRank.getAppmodelId());
		List<MemberRank> memberRanks = tMemberRankMapper.select(Rank);
		if (memberRanks != null) {
			if (memberRanks.size() < 3) {
				return ResultGenerator.genFailResult("非法操作");
			}
			for (MemberRank obj : memberRanks) {
				if (obj.getRankName().equals(memberRank.getRankName())) {
					return ResultGenerator.genFailResult("名称不能相同");

				}
			}
			memberRank.setCreateTime(TimeUtil.getNowTime());
			memberRank.setDeleteState(0);
			memberRanks.add(memberRank);
			Collections.sort(memberRanks, Comparator.comparing(MemberRank::getGrowthValue));
			updateOrInsert(memberRanks);
			return ResultGenerator.genSuccessResult();
		}
		return ResultGenerator.genFailResult("查无此记录");
	}

	private void updateOrInsert(List<MemberRank> memberRanks) {
		int i = 0;
		for (MemberRank rank : memberRanks) {
			i++;
			if (i <= 3) {
				rank.setDeleteState(2);
			} else {
				rank.setDeleteState(2);
			}
			if (rank.getRankId() == null) {
				tMemberRankMapper.insertSelective(rank);
			} else {
				tMemberRankMapper.updateByPrimaryKeySelective(rank);
			}
		}
	}

	@Override
	public Result deleteRank(Integer rankId) {
		MemberRank rank = tMemberRankMapper.selectByPrimaryKey(rankId);
		if (rank.getDeleteState().equals(1)) {
			return ResultGenerator.genFailResult("无法删除默认会员卡");
		}
		return tMemberRankMapper.deleteByPrimaryKey(rankId) > 0 ?
				ResultGenerator.genSuccessResult() :
				ResultGenerator.genFailResult("删除失败");
	}

	@Override
	public Result updateRank(MemberRank memberRank) {
		if (null == memberRank.getRankId()) {
			return ResultGenerator.genFailResult("参数有误");
		}
		MemberRank Rank = new MemberRank();
		Rank.setAppmodelId(memberRank.getAppmodelId());
		List<MemberRank> select = tMemberRankMapper.select(Rank);
		select.forEach(obj -> {
			if (obj.getRankId().equals(memberRank.getRankId())) {
				BeanUtils.copyProperties(memberRank,obj);
			}
		});
		select.sort(Comparator.comparing(MemberRank::getGrowthValue));
		for (int i = 0; i < select.size(); i++) {
			if (i == 0) {
				if (select.get(i).getDiscount() < select.get(i + 1).getDiscount()) {
					return ResultGenerator.genFailResult("折扣取值错误");
				}

			} else if (i == select.size() - 1) {
				if (select.get(i).getDiscount() > select.get(i - 1).getDiscount()) {
					return ResultGenerator.genFailResult("折扣取值错误");
				}
			} else {
				boolean flag1 = select.get(i).getDiscount() > select.get(i - 1).getDiscount();
				boolean flag2 = select.get(i).getDiscount() < select.get(i + 1).getDiscount();
				if (flag1 && flag2) {
					return ResultGenerator.genFailResult("折扣取值错误");
				}
			}
		}
		updateOrInsert(select);
		return ResultGenerator.genSuccessResult();
	}
}
