package com.medusa.basemall.user.service;

import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.Service;
import com.medusa.basemall.user.entity.Member;

/**
 * Created by medusa on 2018/05/24.
 */
public interface MemberService extends Service<Member> {

    /**
     * 校验验证码并注册会员
     * 更新会员信息基础信息
     *
     * @param phone
     * @param memberId
     * @param code
     * @param appmodelId
     * @param type
     * @return
     */
    Result validateCode(String phone, Long memberId, String code, String appmodelId, int type);

    /**
     * 普通用户设为会员
     * 手机号码:phone
     * 用户未激活的会员id:memberId
     * 分组id:groupIds
     * 模板id:appmodelId
     * (修改注册状态为1,补全其他信息)
     */
    Result setUserToMember(String phone, Long wxuserId, String groupIds, String appmodelId);

    Result updatePhone(String phone, Long memberId, String code, String appmodelId);

    Result updateRemark(String memberIds, Integer markLevel, String backRemark, Integer coverType);

    Result myMemberCore(Long wxuserId);

    Result balanceDetails(Long memberId);

	/**
	 * 查找会员信息,包括等级信息
	 * @param wxuserId
	 * @param appmodelId
	 * @return
	 */
	Member findMenberInfo(Long wxuserId, String appmodelId);
}
