package com.medusa.basemall.agent.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Table(name = "t_agent")
public class Agent {
	/**
	 * 代理人id
	 */
	@Id
	@Column(name = "agent_id")
	@ApiModelProperty(value = "代理人id")
	private Integer agentId;

	/**
	 * 用户id
	 */
	@Column(name = "wxuser_id")
	@ApiModelProperty(value = "用户id")
	private Long wxuserId;

	/**
	 * 代理人姓名
	 */
	@Column(name = "agent_name")
	@ApiModelProperty(value = "代理人姓名")
	private String agentName;

	/**
	 * 代理人手机号
	 */
	@Column(name = "agent_phone")
	@ApiModelProperty(value = "代理人手机号")
	private String agentPhone;

	/**
	 * 代理等级
	 */
	@Column(name = "agent_grade_id")
	@ApiModelProperty(value = "代理等级")
	private Integer agentGradeId;

	/**
	 * 所在区域
	 */
	@Column(name = "agent_domain")
	@ApiModelProperty(value = "所在区域")
	private String agentDomain;

	/**
	 * 审核通过时间
	 */
	@Column(name = "audit_date")
	@ApiModelProperty(value = "审核通过时间")
	private String auditDate;

	/**
	 * 代理人状态   1通过审核，2取消分销商资格(禁用)
	 */
	@Column(name = "agent_status")
	@ApiModelProperty(value = "代理人状态  1通过审核，2取消分销商资格(禁用)")
	private Integer agentStatus;

	/**
	 * 模板id
	 */
	@Column(name = "appmodel_id")
	@ApiModelProperty(value = "模板id")
	private String appmodelId;

	@Column(name = "agent_balance")
	@ApiModelProperty(value = "代理消费额度")
	private Double agentBalance;

	@Column(name = "prohibit_time")
	@ApiModelProperty(value = "禁用时间")
	private String prohibitTime;

	@Column(name = "nike_name")
	@ApiModelProperty(value = "微信昵称")
	private String nikeName;

	public String getNikeName() {
		return nikeName;
	}

	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}

	public Double getAgentBalance() {
		return agentBalance;
	}

	public void setAgentBalance(Double agentBalance) {
		this.agentBalance = agentBalance;
	}

	public String getProhibitTime() {
		return prohibitTime;
	}

	public void setProhibitTime(String prohibitTime) {
		this.prohibitTime = prohibitTime;
	}

	/**
	 * 获取代理人id
	 *
	 * @return agent_id - 代理人id
	 */
	public Integer getAgentId() {
		return agentId;
	}

	/**
	 * 设置代理人id
	 *
	 * @param agentId 代理人id
	 */
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	/**
	 * 获取用户id
	 *
	 * @return wxuser_id - 用户id
	 */
	public Long getWxuserId() {
		return wxuserId;
	}

	/**
	 * 设置用户id
	 *
	 * @param wxuserId 用户id
	 */
	public void setWxuserId(Long wxuserId) {
		this.wxuserId = wxuserId;
	}

	/**
	 * 获取代理人姓名
	 *
	 * @return agent_name - 代理人姓名
	 */
	public String getAgentName() {
		return agentName;
	}

	/**
	 * 设置代理人姓名
	 *
	 * @param agentName 代理人姓名
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	/**
	 * 获取代理人手机号
	 *
	 * @return agent_phone - 代理人手机号
	 */
	public String getAgentPhone() {
		return agentPhone;
	}

	/**
	 * 设置代理人手机号
	 *
	 * @param agentPhone 代理人手机号
	 */
	public void setAgentPhone(String agentPhone) {
		this.agentPhone = agentPhone;
	}

	/**
	 * 获取代理等级
	 *
	 * @return agent_grade - 代理等级
	 */
	public Integer getAgentGradeId() {
		return agentGradeId;
	}

	/**
	 * 设置代理等级
	 *
	 * @param agentGradeId 代理等级
	 */
	public void setAgentGradeId(Integer agentGradeId) {
		this.agentGradeId = agentGradeId;
	}

	/**
	 * 获取所在区域
	 *
	 * @return agent_domain - 所在区域
	 */
	public String getAgentDomain() {
		return agentDomain;
	}

	/**
	 * 设置所在区域
	 *
	 * @param agentDomain 所在区域
	 */
	public void setAgentDomain(String agentDomain) {
		this.agentDomain = agentDomain;
	}

	/**
	 * 获取审核通过时间
	 *
	 * @return audit_date - 审核通过时间
	 */
	public String getAuditDate() {
		return auditDate;
	}

	/**
	 * 设置审核通过时间
	 *
	 * @param auditDate 审核通过时间
	 */
	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}

	/**
	 * 获取代理人状态   1通过审核，2取消分销商资格(禁用)
	 *
	 * @return agent_status - 代理人状态   1通过审核，2取消分销商资格(禁用)
	 */
	public Integer getAgentStatus() {
		return agentStatus;
	}

	/**
	 * 设置代理人状态   1通过审核，2取消分销商资格(禁用)
	 *
	 * @param agentStatus 代理人状态   1通过审核，2取消分销商资格(禁用)
	 */
	public void setAgentStatus(Integer agentStatus) {
		this.agentStatus = agentStatus;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Agent agent = (Agent) o;
		return Objects.equals(agentId, agent.agentId) && Objects.equals(wxuserId, agent.wxuserId) && Objects
				.equals(agentName, agent.agentName) && Objects.equals(agentPhone, agent.agentPhone) && Objects
				.equals(agentGradeId, agent.agentGradeId) && Objects.equals(agentDomain, agent.agentDomain) && Objects
				.equals(auditDate, agent.auditDate) && Objects.equals(agentStatus, agent.agentStatus) && Objects
				.equals(appmodelId, agent.appmodelId) && Objects.equals(agentBalance, agent.agentBalance) && Objects
				.equals(prohibitTime, agent.prohibitTime) && Objects.equals(nikeName, agent.nikeName);
	}

	@Override
	public int hashCode() {

		return Objects.hash(agentId, wxuserId, agentName, agentPhone, agentGradeId, agentDomain, auditDate, agentStatus,
				appmodelId, agentBalance, prohibitTime, nikeName);
	}

	@Override
	public String toString() {
		return "Agent{" + "agentId=" + agentId + ", wxuserId=" + wxuserId + ", agentName='" + agentName + '\''
				+ ", agentPhone='" + agentPhone + '\'' + ", agentGradeId=" + agentGradeId + ", agentDomain='"
				+ agentDomain + '\'' + ", auditDate='" + auditDate + '\'' + ", agentStatus=" + agentStatus
				+ ", appmodelId='" + appmodelId + '\'' + ", agentBalance=" + agentBalance + ", prohibitTime='"
				+ prohibitTime + '\'' + ", nikeName='" + nikeName + '\'' + '}';
	}
}