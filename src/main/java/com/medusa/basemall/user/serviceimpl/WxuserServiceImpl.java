package com.medusa.basemall.user.serviceimpl;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.util.crypt.WxMaCryptUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.medusa.basemall.constant.RedisPrefix;
import com.medusa.basemall.core.AbstractService;
import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.ResultGenerator;
import com.medusa.basemall.core.ServiceException;
import com.medusa.basemall.integral.service.PrizeDetailService;
import com.medusa.basemall.shop.entity.ColumnFlag;
import com.medusa.basemall.shop.service.ColumnFlagService;
import com.medusa.basemall.user.dao.MemberGroupCategoryMapper;
import com.medusa.basemall.user.dao.WxuserGroupCategoryMapper;
import com.medusa.basemall.user.dao.WxuserMapper;
import com.medusa.basemall.user.entity.*;
import com.medusa.basemall.user.service.MemberRankRuleService;
import com.medusa.basemall.user.service.MemberRankService;
import com.medusa.basemall.user.service.MemberService;
import com.medusa.basemall.user.service.WxuserService;
import com.medusa.basemall.user.vo.BackWxuserVo;
import com.medusa.basemall.user.vo.MiniWxuserVo;
import com.medusa.basemall.utils.IdGenerateUtils;
import com.medusa.basemall.utils.TimeUtil;
import com.vip.vjtools.vjkit.time.ClockUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.api.WxOpenComponentService;
import me.chanjar.weixin.open.api.WxOpenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * @author medusa
 * @date 2018/05/23
 * 需要事物时添加  @Transactional
 */

@Service
public class WxuserServiceImpl extends AbstractService<Wxuser> implements WxuserService {

	private static Logger LOGGER = LoggerFactory.getLogger(WxuserServiceImpl.class);

	@Resource
	private WxuserMapper tWxuserMapper;
	@Resource
	private MemberService memberService;
	@Resource
	private MemberRankService memberRankService;
	@Resource
	private PrizeDetailService prizeDetailService;
	@Resource
	private MemberGroupCategoryMapper tMemberGroupCategoryMapper;
	@Resource
	private ColumnFlagService columnFlagService;
	@Resource
	private WxuserGroupCategoryMapper tWxuserGroupCategoryMapper;
	@Resource
	private WxOpenService wxOpenService;
	@Resource
	private MemberRankRuleService memberRankRuleService;
	@Resource
	private RedisTemplate redisTemplate;

	@Override
	public Result decodeUserInfo(String encryptedData, String ivStr, Long wxuserId) {
		Wxuser u = tWxuserMapper.selectByPrimaryKey(wxuserId);
		String json = WxMaCryptUtils.decrypt(u.getSessionKey(), encryptedData, ivStr);
		JSONObject jsonObject = JSON.parseObject(json);
		String phoneNumber = jsonObject.getString("phoneNumber");
		return ResultGenerator.genSuccessResult(phoneNumber);
	}


	@Override
	public Result wxlogin(String code, String appmodelId) {
		WxOpenComponentService componentService = wxOpenService.getWxOpenComponentService();
		try {
			WxMaJscode2SessionResult wxMaJscode2SessionResult = componentService
					.miniappJscode2Session(appmodelId.substring(9, appmodelId.length()), code);
			if (wxMaJscode2SessionResult.getOpenid() != null) {
				String nowTime = TimeUtil.getNowTime();
				String openId = wxMaJscode2SessionResult.getOpenid();
				String sessionKey = wxMaJscode2SessionResult.getSessionKey();
				Wxuser wxuser = tWxuserMapper.selectByOpenID(openId,appmodelId);
				if (wxuser == null) {
					wxuser = new Wxuser();
					wxuser.setOpenId(openId);
					wxuser.setWxuserId(IdGenerateUtils.getItemId());
					wxuser.setCreateTime(nowTime);
					wxuser.setAppmodelId(appmodelId);
					wxuser.setSessionKey(sessionKey);
					//为用户创建未开通的会员,只做积分关联不生成其他数据
					Member member = new Member();
					member.setMemberId(IdGenerateUtils.getItemId());
					member.setWxuserId(wxuser.getWxuserId());
					member.setState(0);
					member.setAppmodelId(appmodelId);
					member.setWxuserId(wxuser.getWxuserId());
					member.setMemberId(IdGenerateUtils.getItemId());
					memberService.save(member);
					wxuser.setMemberId(member.getMemberId());
					tWxuserMapper.addUser(wxuser);
				}
				//redis中不存在则代表当天未登陆,判断是否增加积分/成长值
				associatedOperation(wxuser);
				Wxuser newSeesionKey = new Wxuser();
				newSeesionKey.setWxuserId(wxuser.getWxuserId());
				newSeesionKey.setSessionKey(sessionKey);
				newSeesionKey.setLastTime(nowTime);
				tWxuserMapper.updateUserInfo(newSeesionKey);
				return ResultGenerator.genSuccessResult(wxuser.getWxuserId());
			} else {
				throw new ServiceException("获取openId失败");
			}
		} catch (WxErrorException e) {
			throw new ServiceException(e.getError().getJson());
		}
	}

	@Override
	public MiniWxuserVo getUserInfo(Long wxuserId, String appmodelId) {
		MiniWxuserVo miniWxuserVo = tWxuserMapper.selectByWxuserId(wxuserId);
		if (miniWxuserVo != null) {

			String str1 = (String) redisTemplate.opsForValue().get(RedisPrefix.INTEGRAL + miniWxuserVo.getWxuserId());
			JSONObject jsonObject1 = JSONObject.parseObject(str1);
			if (jsonObject1 != null && !jsonObject1.getBoolean("notify")) {
				miniWxuserVo.setIntegralFlag(jsonObject1.getInteger("integral"));
				jsonObject1.put("notify", true);
				redisTemplate.opsForValue()
						.set(RedisPrefix.INTEGRAL + miniWxuserVo.getWxuserId(), jsonObject1.toJSONString(),
								TimeUtil.intradayLastSecond(ClockUtil.currentDate()), TimeUnit.MILLISECONDS);
			}
			String str2 = (String) redisTemplate.opsForValue()
					.get(RedisPrefix.GROWTH_VALUE + miniWxuserVo.getWxuserId());
			JSONObject jsonObject2 = JSONObject.parseObject(str2);
			if (jsonObject2 != null && !jsonObject2.getBoolean("notify")) {
				miniWxuserVo.setIntegralFlag(jsonObject2.getInteger("growthValue"));
				//设置为已通知状态
				jsonObject2.put("notify", true);
				redisTemplate.opsForValue()
						.set(RedisPrefix.GROWTH_VALUE + miniWxuserVo.getWxuserId(), jsonObject2.toJSONString(),
								TimeUtil.intradayLastSecond(ClockUtil.currentDate()), TimeUnit.MILLISECONDS);
			}
		}
		return miniWxuserVo;
	}

	/**
	 * 增加会员积分
	 * 增加会员成长值
	 * @return
	 */
	private Member associatedOperation(Wxuser wxuser) {
		Member member = memberService.findById(wxuser.getMemberId());
		boolean changeFlag = false;
		ColumnFlag columnFlag = columnFlagService.findByAppmodelId(member.getAppmodelId());
		//只存储当天的数据
		long time = TimeUtil.intradayLastSecond(ClockUtil.currentDate());
		Object integral = redisTemplate.opsForValue().get(RedisPrefix.INTEGRAL + member.getWxuserId());
		if (columnFlag.getShopFlag().equals(true) && integral == null) {
			Integer typeTwo = prizeDetailService.addIntegral(3, member.getAppmodelId(), member.getWxuserId());
			member.setIntegralTotal(member.getIntegralTotal() + typeTwo);
			JSONObject str = new JSONObject();
			str.put("integral", typeTwo);
			str.put("notify", false);
			//设置未通知
			redisTemplate.opsForValue()
					.set(RedisPrefix.INTEGRAL + member.getWxuserId(), str.toJSONString(), time, TimeUnit.MILLISECONDS);
			changeFlag = true;
		}
		if (member.getState().equals(0)) {
			return member;
		}
		Object growthValue = redisTemplate.opsForValue().get(RedisPrefix.GROWTH_VALUE + member.getWxuserId());
		if (columnFlag.getMemberFlag().equals(true) && growthValue == null) {
			MemberRankRule memberRankRule = memberRankRuleService.findBy("appmodelId", member.getAppmodelId());
			member.setGrowthValue(member.getGrowthValue() + memberRankRule.getLoginIntegral());
			//设置未通知
			JSONObject str = new JSONObject();
			str.put("growthValue", memberRankRule.getLoginIntegral());
			str.put("notify", false);
			redisTemplate.opsForValue().set(RedisPrefix.GROWTH_VALUE + member.getWxuserId(), str.toJSONString(), time,
					TimeUnit.MILLISECONDS);
			changeFlag = true;
		}
		if (changeFlag) {
			memberService.update(member);
		}
		return member;
	}


	@Override
	public Result updateRemark(String wxuserIds, Integer markLevel, String backRemark, Integer coverType) {
		if (wxuserIds.length() > 0) {
			List<Wxuser> wxusers = tWxuserMapper.selectByIds(wxuserIds);
			if (coverType == 0) {
				//不覆盖原有备注,过滤出非空备注的用户
				List<Wxuser> collect = wxusers.stream()
						.filter(obj -> obj.getBackRemark() == null || "".equals(obj.getBackRemark()))
						.collect(Collectors.toList());
				collect.forEach(obj -> {
					obj.setBackRemark(backRemark);
					tWxuserMapper.updateByPrimaryKeySelective(obj);
				});
			} else {
				wxusers.forEach(obj -> {
					obj.setBackRemark(backRemark);
					tWxuserMapper.updateByPrimaryKey(obj);
				});
			}
			return ResultGenerator.genSuccessResult();
		}
		return ResultGenerator.genFailResult("备注失败");
	}


	@Override
	public List<BackWxuserVo> searchWxuser(String appmodelId, String nickName, String logintime, String dealtime,
			Integer groupId, String orderType, String vip, Integer pageNum, Integer pageSize) {
		Map<String, Object> mapo = new HashMap<String, Object>(4);
		mapo.put("appmodelId", appmodelId);
		mapo.put("vip", vip);
		if (nickName != null && nickName.length() > 0) {
			char[] chars = nickName.toCharArray();
			StringBuilder sb = new StringBuilder("%");
			for (int i = 0; i < chars.length; i++) {
				sb.append(chars[i] + "%");
			}
			mapo.put("nikeName", sb.toString());
		}
		if (logintime != null && !"".equals(logintime)) {
			String[] templogin = logintime.split(",");
			mapo.put("starttime", templogin[0]);
			mapo.put("endtime", templogin[1]);
		}
		if (dealtime != null && !"".equals(dealtime)) {
			String[] orderSucceed = dealtime.split(",");
			mapo.put("orderStart", orderSucceed[0]);
			mapo.put("orderEnd", orderSucceed[1]);
		}
		// 会员功能关闭则不过滤会员用户查询所有
		ColumnFlag columnFlag = columnFlagService.findByAppmodelId(appmodelId);
		if (columnFlag.getMemberFlag().equals(false)) {
			mapo.put("vip", -1);
		}
		mapo.put("groupId", groupId);
		if (orderType != null) {
			if (vip.equals("1")) {
				if ("registerDesc".equals(orderType)) {
					mapo.put("sort", "201");
				}
				if ("registerAsc".equals(orderType)) {
					mapo.put("sort", "202");
				}
			} else {
				if ("registerDesc".equals(orderType)) {
					mapo.put("sort", "101");
				}
				if ("registerAsc".equals(orderType)) {
					mapo.put("sort", "102");
				}
			}
			if ("orderDesc".equals(orderType)) {
				mapo.put("sort", "301");
			}
			if ("orderAsc".equals(orderType)) {
				mapo.put("sort", "302");
			}
		}
		PageHelper.startPage(pageNum, pageSize);
		List<BackWxuserVo> list = tWxuserMapper.searchBack(mapo);
		if (null != list) {
			List<BackWxuserVo> result = filterQualification(groupId, appmodelId, list, vip);
			return result;
		}
		return list;
	}

	@Override
	public int updateUserInfo(Wxuser wxuser) {
		return tWxuserMapper.updateUserInfo(wxuser);
	}

	@Override
	public MiniWxuserVo selectByWxuserId(Long wxuserId) {
		return tWxuserMapper.selectByWxuserId(wxuserId);
	}


	/**
	 * 根据条件进筛选
	 */
	private List<BackWxuserVo> filterQualification(Integer groupId, String appmodelId, List<BackWxuserVo> list,
			String vip) {
		List<BackWxuserVo> result = null;
		//会员查询
		if ("1".equals(vip)) {
			//找出会员所在的分组并添加至会员分组实体数组
			List<MemberGroupCategory> memberGroups = tMemberGroupCategoryMapper.selectMemberGroup(appmodelId);
			list.forEach(user -> {
				//计算注册时间
				long days = LocalDate
						.parse(user.getMemberInfo().getCreateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
						.until(LocalDate.now(), ChronoUnit.DAYS);
				user.setRegisterTime(days + "");
				if (null != memberGroups && memberGroups.size() > 0) {
					memberGroups.forEach(group -> {
						if (null != user.getMemberInfo()) {
							if (user.getMemberInfo().getMemberId().equals(group.getMemberId())) {
								user.getMemberGroups().add(group);
							}
						}
					});
				}
			});
			//获取下一等级的达标成长值
			List<MemberRank> ranks = memberRankService.findByList("appmodelId", appmodelId);
			ranks.sort(Comparator.comparing(MemberRank::getGrowthValue));
			list.forEach(obj -> {
				Integer growthValue = obj.getMemberInfo().getGrowthValue();
				for (int i = 0; i < ranks.size(); i++) {
					if (growthValue < ranks.get(i).getGrowthValue()) {
						obj.setNextGrowthValue(ranks.get(i).getGrowthValue());
						break;
					}
					if (i == ranks.size() - 1) {
						obj.setNextGrowthValue(ranks.get(i).getGrowthValue());
					}
				}
			});

			result = list;
			//会员用户分组筛选
			if (null != list && null != groupId && groupId > 0) {
				List<BackWxuserVo> finalResult = new ArrayList<>();
				list.forEach(obj -> {
					obj.getMemberGroups().forEach(memberGroupCategory -> {
						if (memberGroupCategory.getGroupId().equals(groupId)) {
							finalResult.add(obj);
						}
					});
				});
				result = finalResult;
			}
			//普通用户查询
		} else {
			//找出普通用户所在的分组并添加至用户分组实体数组
			List<WxuserGroupCategory> wxuserGroups = tWxuserGroupCategoryMapper.selectWxuserGroup(appmodelId);
			list.forEach(user -> {
				//计算注册时间
				int days = Period.between(
						LocalDate.parse(user.getCreateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
						LocalDate.now()).getDays();
				user.setRegisterTime(days + "");
				if (null != wxuserGroups && wxuserGroups.size() > 0) {
					wxuserGroups.forEach(group -> {
						if (null != user.getMemberInfo()) {
							if (user.getWxuserId().equals(group.getWxuserId())) {
								user.getWxuserGroups().add(group);
							}
						}
					});
				}
			});

			result = list;
			//普通用户分组筛选
			if (null != list && null != groupId && groupId > 0) {
				List<BackWxuserVo> finalResult = new ArrayList<>();
				list.forEach(obj -> {
					obj.getWxuserGroups().forEach(wxuserGroupCategory -> {
						if (wxuserGroupCategory.getWxuserGroupId().equals(groupId)) {
							finalResult.add(obj);
						}
					});
				});
				result = finalResult;
			}
		}
		return result;
	}


}
