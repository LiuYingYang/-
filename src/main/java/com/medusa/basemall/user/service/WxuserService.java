package com.medusa.basemall.user.service;

import com.medusa.basemall.core.Result;
import com.medusa.basemall.core.Service;
import com.medusa.basemall.user.entity.Wxuser;
import com.medusa.basemall.user.vo.BackWxuserVo;
import com.medusa.basemall.user.vo.MiniWxuserVo;

import java.util.List;

/**
 * Created by medusa on 2018/05/23.
 */
public interface WxuserService extends Service<Wxuser> {

    Result decodeUserInfo(String encryptedData, String iv, Long wxuserId);

    Result wxlogin(String code, String appmodelId);

    Result updateRemark(String wxuserIds, Integer markLevel, String backRemark, Integer coverType);



    List<BackWxuserVo> searchWxuser(String appmodelId, String nickName, String logintime, String dealtime, Integer groupId, String order, String vip,
		    Integer pageNum, Integer pageSize);

    int updateUserInfo(Wxuser wxuser);

    /**
     * 根据用户id查询用户信息
     * @param wxuserId
     * @return
     */
    MiniWxuserVo selectByWxuserId(Long wxuserId);

	MiniWxuserVo getUserInfo(Long wxuserId, String appmodelId);
}
