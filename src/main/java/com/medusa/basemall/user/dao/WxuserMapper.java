package com.medusa.basemall.user.dao;

import com.medusa.basemall.core.Mapper;
import com.medusa.basemall.user.entity.Wxuser;
import com.medusa.basemall.user.entity.WxuserBack;
import com.medusa.basemall.user.vo.BackWxuserVo;
import com.medusa.basemall.user.vo.MiniWxuserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WxuserMapper extends Mapper<Wxuser> {

	/**
	 * 根据openID查询用户
	 * @param openId
	 * @param appmodelId
	 * @return
	 */
	Wxuser selectByOpenID(@Param("openId") String openId, @Param("appmodelId") String appmodelId);


	/**
	 * 根据用户id查询用户信息
	 * @param wxuserId
	 * @return
	 */
	MiniWxuserVo selectByWxuserId(Long wxuserId);


	List<BackWxuserVo> searchBack(Map<String, Object> mapo);

	/**
	 * @param appmodelId
	 * @return
	 */
	List<WxuserBack> selectByAppmodelId(String appmodelId);

	/**
	 * @param wxuser
	 * @return
	 */
	int addUser(Wxuser wxuser);

	/**
	 * @param wxuser
	 * @return
	 */
	int updateUserInfo(Wxuser wxuser);

}