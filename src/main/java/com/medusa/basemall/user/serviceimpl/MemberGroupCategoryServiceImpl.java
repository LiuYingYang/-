package com.medusa.basemall.user.serviceimpl;

import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.user.dao.MemberGroupCategoryMapper;
import com.medusa.basemall.user.entity.MemberGroupCategory;
import com.medusa.basemall.user.service.MemberGroupCategoryService;
import com.medusa.basemall.user.vo.MemberVo;
import com.medusa.basemall.utils.TimeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author medusa
 * @date 2018/05/26
 * 需要事物时添加  @Transactional
 */

@Service

public class MemberGroupCategoryServiceImpl extends AbstractService<MemberGroupCategory>
		implements MemberGroupCategoryService {
	@Resource
	private MemberGroupCategoryMapper tMemberGroupCategoryMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result setMemberToGroup(MemberVo params) {
		//删除会员原本所在的所有分组
		if (params.getMembers() != null && params.getMemberGroupCategories() != null) {
			tMemberGroupCategoryMapper.deleteMemberId(params.getMembers());
			//批量添加会员分组
			params.getMembers().forEach(obj -> {
				Map<String, Object> map = new HashMap<>(4);
				map.put("memberId", obj.getMemberId());
				map.put("appmodelId", obj.getAppmodelId());
				map.put("createTime", TimeUtil.getNowTime());
				List<Integer> groupIds = new ArrayList<>();
				params.getMemberGroupCategories().forEach(group -> {
					groupIds.add(group.getGroupId());
				});
				map.put("groupIds", groupIds);
				if (tMemberGroupCategoryMapper.setMemberToGroup(map) == 0) {
					throw new RuntimeException("分组修改失败");
				}
			});
			return ResultGenerator.genSuccessResult();
		}
		if(params.getMembers() != null && params.getMemberGroupCategories() == null){
			tMemberGroupCategoryMapper.deleteMemberId(params.getMembers());
			return  ResultGenerator.genSuccessResult();
		}
		return ResultGenerator.genFailResult("设置失败");
	}
}
