package com.medusa.basemall.user.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Table(name = "t_member_rank_rule")
public class MemberRankRule {
    /**
     * 规则表id
     */
    @Id
    @Column(name = "member_rule_id")
    @ApiModelProperty(value = "规则表id",required = true)
    private Integer memberRuleId;

    /**
     * 登录可获得成长值
     */
    @Column(name = "login_integral")
    @ApiModelProperty(value = "登录可获得成长值")
    private Integer loginIntegral;

    /**
     * 分享可获得成长值
     */
    @Column(name = "share_integral")
    @ApiModelProperty(value = "分享可获得成长值")
    private Integer shareIntegral;

    /**
     * 消费可获得成长值
     */
    @Column(name = "consume_integral")
    @ApiModelProperty(value = "消费可获得成长值")
    private Integer consumeIntegral;

    /**
     * 等级有效期   -1 默认永久
     */
    @ApiModelProperty(value = "等级有效期 -1 默认永久")
    private Integer validity;

    /**
     * 等级介绍
     */
    @ApiModelProperty(value = "等级介绍"  )
    private String introduce;

    /**
     * 升降级说明
     */
    @Column(name = "explain_info")
    @ApiModelProperty(value = "升降级说明"  )
    private String explainInfo;

    @Column(name = "appmodel_id")
    @ApiModelProperty(value = "商家唯一id"  )
    private String appmodelId;

    //是否使用升降级规格
    @Column(name = "explain_state")
    @ApiModelProperty(value = "是否使用升降级规格")
    private Integer explainState;

    @Transient
    @ApiModelProperty(value = "等级数组",dataType = "List<MemberRank>")
    private List<MemberRank> ranks;

    public List<MemberRank> getRanks() {
        return ranks;
    }

    public void setRanks(List<MemberRank> ranks) {
        this.ranks = ranks;
    }

    public Integer getExplainState() {
        return explainState;
    }

    public void setExplainState(Integer explainState) {
        this.explainState = explainState;
    }

    public String getExplainInfo() {
        return explainInfo;
    }

    public void setExplainInfo(String explainInfo) {
        this.explainInfo = explainInfo;
    }

    /**
     * 获取规则表id
     *
     * @return member_rule_id - 规则表id
     */
    public Integer getMemberRuleId() {
        return memberRuleId;
    }

    /**
     * 设置规则表id
     *
     * @param memberRuleId 规则表id
     */
    public void setMemberRuleId(Integer memberRuleId) {
        this.memberRuleId = memberRuleId;
    }

    /**
     * 获取登录可获得成长值
     *
     * @return login_integral - 登录可获得成长值
     */
    public Integer getLoginIntegral() {
        return loginIntegral;
    }

    /**
     * 设置登录可获得成长值
     *
     * @param loginIntegral 登录可获得成长值
     */
    public void setLoginIntegral(Integer loginIntegral) {
        this.loginIntegral = loginIntegral;
    }

    /**
     * 获取分享可获得成长值
     *
     * @return share_integral - 分享可获得成长值
     */
    public Integer getShareIntegral() {
        return shareIntegral;
    }

    /**
     * 设置分享可获得成长值
     *
     * @param shareIntegral 分享可获得成长值
     */
    public void setShareIntegral(Integer shareIntegral) {
        this.shareIntegral = shareIntegral;
    }

    /**
     * 获取消费可获得成长值
     *
     * @return consume_integral - 消费可获得成长值
     */
    public Integer getConsumeIntegral() {
        return consumeIntegral;
    }

    /**
     * 设置消费可获得成长值
     *
     * @param consumeIntegral 消费可获得成长值
     */
    public void setConsumeIntegral(Integer consumeIntegral) {
        this.consumeIntegral = consumeIntegral;
    }

    /**
     * 获取等级有效期   -1 默认永久
     *
     * @return validity - 等级有效期   -1 默认永久
     */
    public Integer getValidity() {
        return validity;
    }

    /**
     * 设置等级有效期   -1 默认永久
     *
     * @param validity 等级有效期   -1 默认永久
     */
    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    /**
     * 获取等级介绍
     *
     * @return introduce - 等级介绍
     */
    public String getIntroduce() {
        return introduce;
    }

    /**
     * 设置等级介绍
     *
     * @param introduce 等级介绍
     */
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    /**
     * @return appmodel_id
     */
    public String getAppmodelId() {
        return appmodelId;
    }

    /**
     * @param appmodelId
     */
    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }
}