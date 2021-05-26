package com.medusa.basemall.user.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author whh
 */
@Table(name = "t_member")
@Data
public class Member implements Serializable {

	private static final long serialVersionUID = -6214228252359811649L;
	/**
	 * 会员id
	 */
	@Id
	@Column(name = "member_id")
	@ApiModelProperty(value = "会员id"  )
	private Long memberId;

	/**
	 * 会员卡编号
	 */
	@Column(name = "membership_number")
	@ApiModelProperty(value = "会员卡编号"  )
	private String membershipNumber;

	/**
	 * 会员卡二维码
	 */
	@ApiModelProperty(value = "会员卡二维码"  )
	@Column(name = "code_type")
	private String codeType;

	/**
	 * 用户id
	 */
	@Column(name = "wxuser_id")
	@ApiModelProperty(value = "用户id"  )
	private Long wxuserId;

	/**
	 * 会员卡余额
	 */
	@Column(name = "supply_bonus")
	@ApiModelProperty(value = "会员卡余额" )
	private Double supplyBonus;

	/**
	 * 成长值
	 */
	@Column(name = "growth_value")
	@ApiModelProperty(value = "成长值", dataType = "Integer")
	private Integer growthValue;

	/**
	 * 积分值
	 */
	@ApiModelProperty(value = "积分值", dataType = "Integer")
	private Integer integral;

	/**
	 * 总积分值
	 */
	@Column(name = "integral_total")
	@ApiModelProperty(value = "总积分值", dataType = "Integer")
	private Integer integralTotal;

	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号"  )
	private String phone;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	@ApiModelProperty(value = "创建时间"  )
	private String createTime;

	/**
	 * 最后升级时间
	 */
	@Column(name = "upgrade_time")
	@ApiModelProperty(value = "最后升级时间"  )
	private String upgradeTime;

	/**
	 * 注册状态0未注册,1已注册
	 */
	@ApiModelProperty(value = "注册状态   0未注册,1已注册", dataType = "Integer")
	private Integer state;

	/**
	 * 等级表Id
	 */
	@Column(name = "rank_id")
	@ApiModelProperty(value = "等级表Id", dataType = "Integer")
	private Integer rankId;

	@ApiModelProperty(value = "等级信息", dataType = "MemberRank")
	@Transient
	private MemberRank rankInfo;

	/**
	 * 模板id
	 */
	@Column(name = "appmodel_id")
	@ApiModelProperty(value = "模板id"  )
	private String appmodelId;


	@Column(name = "member_remark")
	@ApiModelProperty(value = "会员备注"  )
	private String memberRemark;

}