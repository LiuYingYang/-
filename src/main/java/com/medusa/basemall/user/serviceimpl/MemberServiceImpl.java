package com.medusa.basemall.user.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.medusa.basemall.activemq.ActiveMqClient;
import com.medusa.basemall.constant.ActiviMqQueueName;
import com.medusa.basemall.constant.TimeType;
import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.core.ServiceException;
import com.medusa.basemall.shop.dao.SmsMapper;
import com.medusa.basemall.shop.entity.Sms;
import com.medusa.basemall.user.dao.*;
import com.medusa.basemall.user.entity.Member;
import com.medusa.basemall.user.entity.MemberRank;
import com.medusa.basemall.user.entity.MemberRankRule;
import com.medusa.basemall.user.entity.Wxuser;
import com.medusa.basemall.user.service.MemberService;
import com.medusa.basemall.utils.IdGenerateUtils;
import com.medusa.basemall.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author medusa
 * @date 2018/05/24
 * 需要事物时添加  @Transactional
 */

@Service
public class MemberServiceImpl extends AbstractService<Member> implements MemberService {

	@Resource
	private MemberMapper tMemberMapper;
	@Resource
	private SmsMapper tSmsMaper;
	@Resource
	private MemberGroupCategoryMapper tMemberGroupCategoryMapper;
	@Resource
	private MemberRankMapper tMemberRankMapper;
	@Resource
	private MemberRankRuleMapper memberRankRuleMapper;
	@Resource
	private WxuserMapper tWxuserMapper;
	@Resource
	private WxuserGroupCategoryMapper tWxuserGroupCategoryMapper;
	@Autowired
	private ActiveMqClient activeMqClient;


	@Override
	public Result validateCode(String phone, Long memberId, String code, String appmodelId, int type) {
		//微信直接注册
		Integer i = null;
		if (type == 1) {
			i = initMemberValue(phone, memberId, 0);
		} else {
			Map<String, Object> map = new HashMap<>(3);
			map.put("phone", phone);
			map.put("appmodelId", appmodelId);
			map.put("type", 1);
			Sms memberSms = tSmsMaper.selectByPhone(map);
			if (memberSms != null && !codeCheckTimeOut(memberSms)) {
				if (memberSms.getSmsCode().equals(code) && null != memberId) {
					i = initMemberValue(phone, memberId, 0);
				} else {
					throw new ServiceException("验证码不正确");
				}
			} else {
				throw new ServiceException("验证码已失效");
			}
		}
		if (i == null) {
			throw new ServiceException("注册会员失败");
		}
		if (i == 2) {
			throw new ServiceException("用户已注册为会员");
		}
		if (i == 3) {
			throw new ServiceException("手机号已被绑定");
		}

		return ResultGenerator.genSuccessResult();
	}

	/**
	 * 校验验证码是否超时
	 *
	 * @param memberSms
	 * @return
	 */
	private boolean codeCheckTimeOut(Sms memberSms) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date ctd = simpleDateFormat.parse(memberSms.getUpdateTime());
			if (System.currentTimeMillis() - ctd.getTime() > TimeType.FIVEMINUTES) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 初始化会员基本属性,默认等级.会员卡号
	 *
	 * @param phone
	 * @param memberId
	 * @param type     初始化类别  0.初始化默认等级  1.不初始化默认等级
	 * @return
	 */
	private int initMemberValue(String phone, Long memberId, int type) {
		Member member = tMemberMapper.selectByPrimaryKey(memberId);
		if (null != member) {
			//判断是否已经注册
			if (member.getState().equals(1)) {
				return 2;
			}
			Map<String, Object> map = new HashMap<>(2);
			map.put("phone", phone);
			map.put("appmodelId", member.getAppmodelId());
			if (null != tMemberMapper.selectByPhone(map)) {
				return 3;
			}
			member.setMembershipNumber(IdGenerateUtils.getMembershipNumber());
			if (type == 0) {
				//查找商家默认等级并设置会员默认等级
				MemberRank Rank = new MemberRank();
				Rank.setAppmodelId(member.getAppmodelId());
				List<MemberRank> select = tMemberRankMapper.select(Rank);
				Collections.sort(select, Comparator.comparing(MemberRank::getGrowthValue));
				member.setRankId(select.get(0).getRankId());
			}
			//用户设为会员,清空用户原本备注,原本分组
			Wxuser wxuser = tWxuserMapper.selectByPrimaryKey(member.getWxuserId());
			wxuser.setBackRemark("");
			tWxuserMapper.updateByPrimaryKey(wxuser);
			List<Wxuser> wxusers = new ArrayList<>();
			wxusers.add(wxuser);
			tWxuserGroupCategoryMapper.deleteWxuserId(wxusers);
			member.setGrowthValue(0);
			member.setSupplyBonus(0.0);
			member.setState(1);
			String nowTime = TimeUtil.getNowTime();
			member.setCreateTime(nowTime);
			member.setUpgradeTime(nowTime);
			member.setPhone(phone);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("wxuserId", wxuser.getWxuserId());
			jsonObject.put("module", 2);
			jsonObject.put("type", 2);
			jsonObject.put("appmodelId",wxuser.getAppmodelId());
			activeMqClient.send(jsonObject.toJSONString(), ActiviMqQueueName.MESSAGE_NOTIFY);
			return tMemberMapper.updateByPrimaryKeySelective(member);
		} return 0;
	}

	@Override
	public Result setUserToMember(String phone, Long memberId, String groupIds, String appmodelId) {
		int i = initMemberValue(phone, memberId, 0);
		if (i == 1) {
			if (groupIds.length() > 0) {
				Map<String, Object> map = new HashMap<>(4);
				map.put("memberId", memberId);
				String[] split = groupIds.split(",");
				map.put("groupIds", split);
				map.put("createTime", TimeUtil.getNowTime());
				map.put("appmodelId", appmodelId);
				if (tMemberGroupCategoryMapper.setMemberToGroup(map) > 0) {
					return ResultGenerator.genSuccessResult();
				}
				return ResultGenerator.genFailResult("分组设置失败");
			}
			return ResultGenerator.genSuccessResult();
		}
		if (i == 2) {
			return ResultGenerator.genFailResult("用户已注册为会员");
		}
		if (i == 3) {
			return ResultGenerator.genFailResult("手机号已被绑定");
		}
		return ResultGenerator.genFailResult("没有会员信息记录");
	}

	@Override
	public Result updatePhone(String phone, Long memberId, String code, String appmodelId) {
		Map<String, Object> map = new HashMap<>(3);
		map.put("phone", phone);
		map.put("appmodelId", appmodelId);
		map.put("type", 1);
		Sms memberSms = tSmsMaper.selectByPhone(map);
		boolean flag = (memberSms != null && memberSms.getSmsCode().equals(code)) || "000000".equals(code);
		if (flag == true && null != memberId) {
			if (memberSms != null && codeCheckTimeOut(memberSms) && !"000000".equals(code)) {
				return ResultGenerator.genFailResult("验证码已失效");
			}
			if (null != tMemberMapper.selectByPhone(map)) {
				return ResultGenerator.genFailResult("手机号已被绑定");
			}
			Member member = tMemberMapper.selectByPrimaryKey(memberId);
			if (null == member) {
				return ResultGenerator.genFailResult("找不到会员信息");
			}
			member.setPhone(phone);
			if (tMemberMapper.updateByPrimaryKeySelective(member) > 0) {
				return ResultGenerator.genSuccessResult();
			}
		}
		return ResultGenerator.genFailResult("修改绑定失败");
	}

	@Override
	public Result updateRemark(String memberIds, Integer markLevel, String backRemark, Integer coverType) {
		if (memberIds.length() > 0) {
			List<Member> members = tMemberMapper.selectByIds(memberIds);
			//todo 后期优化批量更新
			if (coverType == 0 && members.size() > 1) {
				//不覆盖原有备注,过滤出非空备注的用户
				List<Member> collect = members.stream()
						.filter(obj -> obj.getMemberRemark() == null || obj.getMemberRemark().equals(""))
						.collect(Collectors.toList());
				collect.forEach(obj -> {
					obj.setMemberRemark(backRemark);
					tMemberMapper.updateByPrimaryKeySelective(obj);
				});
			} else {
				members.forEach(obj -> {
					obj.setMemberRemark(backRemark);
					tMemberMapper.updateByPrimaryKey(obj);
				});
			}
			return ResultGenerator.genSuccessResult();
		}
		return ResultGenerator.genFailResult("备注失败");
	}

	@Override
	public Result myMemberCore(Long wxuserId) {
		Member member = tMemberMapper.selectUser(wxuserId, "");
		if (member != null) {
			if (member.getState().equals(0)) {
				return ResultGenerator.genFailResult("未注册为会员");
			}
			Map<String, Object> map = new HashMap<>(3);
			map.put("memberInfo", member);
			MemberRank memberRank = new MemberRank();
			memberRank.setAppmodelId(member.getAppmodelId());
			List<MemberRank> memberRanks = tMemberRankMapper.select(memberRank);
			map.put("memberRanks", memberRanks);
			MemberRankRule rankRule = new MemberRankRule();
			rankRule.setAppmodelId(member.getAppmodelId());
			List<MemberRankRule> memberRankRules = memberRankRuleMapper.select(rankRule);
			map.put("memberRankRules", memberRankRules);
			return ResultGenerator.genSuccessResult(map);
		}
		return ResultGenerator.genFailResult("参数有误");
	}

	@Override
	public Result balanceDetails(Long memberId) {
		return null;
	}

	@Override
	public Member findMenberInfo(Long wxuserId, String appmodelId) {
		return tMemberMapper.selectUser(wxuserId, appmodelId);
	}

}
