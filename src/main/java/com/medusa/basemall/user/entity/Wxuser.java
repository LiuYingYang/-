package com.medusa.basemall.user.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author whh
 */
@Table(name = "t_wxuser")
@Data
public class Wxuser implements Serializable {

	private static final long serialVersionUID = -6615111648935428306L;
	/**
	 * 用户id
	 */
	@Id
	@Column(name = "wxuser_id")
	@ApiModelProperty(value = "用户id")
	private Long wxuserId;
	/**
	 * 微信用户openId
	 */
	@Column(name = "open_id")
	@ApiModelProperty(value = "微信用户openId")
	private String openId;
	/**
	 * 用户名称
	 */
	@Column(name = "nike_name")
	@ApiModelProperty(value = "用户名称")
	private String nikeName;
	/**
	 * 头像url
	 */
	@Column(name = "avatar_url")
	@ApiModelProperty(value = "头像url")
	private String avatarUrl;
	/**
	 * 授权时间
	 */
	@Column(name = "create_time")
	@ApiModelProperty(value = "授权时间")
	private String createTime;
	/**
	 * 最后登录时间
	 */
	@Column(name = "last_time")
	@ApiModelProperty(value = "最后登录时间")
	private String lastTime;

	/**
	 * 是否授权
	 */
	@Column(name = "authorize_type")
	@ApiModelProperty(value = "是否授权")
	private String authorizeType;

	/**
	 * 删除标示
	 */
	@Column(name = "delete_state")
	@ApiModelProperty(value = "删除标示")
	private Integer deleteState;
	/**
	 * 模板id
	 */
	@Column(name = "appmodel_id")
	@ApiModelProperty(value = "appmodel_id")
	private String appmodelId;
	/**
	 * 后台备注
	 */
	@Column(name = "back_remark")
	@ApiModelProperty(value = "后台备注")
	private String backRemark;

	@Column(name = "mark_level")
	@ApiModelProperty(value = "mark_level")
	private Integer markLevel;
	/**
	 * 用户的sessionkey
	 */
	@Column(name = "session_key")
	@ApiModelProperty(value = "用户的sessionkey")
	private String sessionKey;

	@Column(name = "member_id")
	@ApiModelProperty(value = "会员id")
	private Long memberId;


}