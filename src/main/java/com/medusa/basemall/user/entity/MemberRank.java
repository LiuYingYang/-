package com.medusa.basemall.user.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author whh
 */
@Table(name = "t_member_rank")
public class MemberRank implements Serializable {
	private static final long serialVersionUID = -3102489506611472230L;
	/**
	 * 等级表id
	 */
	@Id
	@Column(name = "rank_id")
	@ApiModelProperty(value = "等级表id", dataType = "Integer")
	private Integer rankId;

	/**
	 * 等级名称
	 */
	@Column(name = "rank_name")
	@ApiModelProperty(value = "等级名称"  )
	private String rankName;

	/**
	 * 折扣
	 */
	@ApiModelProperty(value = "折扣" )
	private Double discount;

	/**
	 * 会员卡图片
	 */
	@Column(name = "back_ground_pic_url")
	@ApiModelProperty(value = "会员卡图片"  )
	private String backGroundPicUrl;

	/**
	 * 会员卡标题
	 */
	@ApiModelProperty(value = "会员卡标题"  )
	private String title;

	/**
	 * 到期后减扣得成长值
	 */
	@Column(name = "deduct_growth")
	@ApiModelProperty(value = "到期后减扣得成长值", dataType = "Integer")
	private Integer deductGrowth;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	@ApiModelProperty(value = "创建时间"  )
	private String createTime;

	/**
	 * 删除状态 0-可用 1-逻辑删除  2-不可删除
	 */
	@Column(name = "delete_state")
	@ApiModelProperty(value = "删除状态:0-可用 1-逻辑删除  2-不可删除", dataType = "Integer")
	private Integer deleteState;

	/**
	 * 模板id
	 */
	@Column(name = "appmodel_id")
	@ApiModelProperty(value = "模板id"  )
	private String appmodelId;

	/**
	 * 达标成长值
	 */
	@Column(name = "growth_value")
	@ApiModelProperty(value = "达标成长值", dataType = "Integer")
	private Integer growthValue;

	/**
	 * 获取等级表id
	 *
	 * @return rank_id - 等级表id
	 */
	public Integer getRankId() {
		return rankId;
	}

	/**
	 * 设置等级表id
	 *
	 * @param rankId 等级表id
	 */
	public void setRankId(Integer rankId) {
		this.rankId = rankId;
	}

	/**
	 * 获取等级名称
	 *
	 * @return rank_name - 等级名称
	 */
	public String getRankName() {
		return rankName;
	}

	/**
	 * 设置等级名称
	 *
	 * @param rankName 等级名称
	 */
	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

	/**
	 * 获取折扣
	 *
	 * @return discount - 折扣
	 */
	public Double getDiscount() {
		return discount;
	}

	/**
	 * 设置折扣
	 *
	 * @param discount 折扣
	 */
	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	/**
	 * 获取会员卡图片
	 *
	 * @return back_ground_pic_url - 会员卡图片
	 */
	public String getBackGroundPicUrl() {
		return backGroundPicUrl;
	}

	/**
	 * 设置会员卡图片
	 *
	 * @param backGroundPicUrl 会员卡图片
	 */
	public void setBackGroundPicUrl(String backGroundPicUrl) {
		this.backGroundPicUrl = backGroundPicUrl;
	}

	/**
	 * 获取会员卡标题
	 *
	 * @return title - 会员卡标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置会员卡标题
	 *
	 * @param title 会员卡标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取到期后减扣得成长值
	 *
	 * @return deduct_growth - 到期后减扣得成长值
	 */
	public Integer getDeductGrowth() {
		return deductGrowth;
	}

	/**
	 * 设置到期后减扣得成长值
	 *
	 * @param deductGrowth 到期后减扣得成长值
	 */
	public void setDeductGrowth(Integer deductGrowth) {
		this.deductGrowth = deductGrowth;
	}

	/**
	 * 获取创建时间
	 *
	 * @return create_time - 创建时间
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * 设置创建时间
	 *
	 * @param createTime 创建时间
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取删除状态 0-可用 1-逻辑删除  2-不可删除
	 *
	 * @return delete_state - 删除状态 0-可用 1-逻辑删除  2-不可删除
	 */
	public Integer getDeleteState() {
		return deleteState;
	}

	/**
	 * 设置删除状态 0-可用 1-逻辑删除  2-不可删除
	 *
	 * @param deleteState 删除状态 0-可用 1-逻辑删除  2-不可删除
	 */
	public void setDeleteState(Integer deleteState) {
		this.deleteState = deleteState;
	}

	/**
	 * 获取模板id
	 *
	 * @return appmodel_id - 模板id
	 */
	public String getAppmodelId() {
		return appmodelId;
	}

	/**
	 * 设置模板id
	 *
	 * @param appmodelId 模板id
	 */
	public void setAppmodelId(String appmodelId) {
		this.appmodelId = appmodelId;
	}

	/**
	 * 获取达标成长值
	 *
	 * @return growth_value - 达标成长值
	 */
	public Integer getGrowthValue() {
		return growthValue;
	}

	/**
	 * 设置达标成长值
	 *
	 * @param growthValue 达标成长值
	 */
	public void setGrowthValue(Integer growthValue) {
		this.growthValue = growthValue;
	}
}