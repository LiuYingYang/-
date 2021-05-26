package com.medusa.basemall.integral.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Created by wx on 2018/06/06.
 */
@Table(name = "t_prize_rule")
public class PrizeRule {

    @Id
    @Column(name = "prize_rule_id")
    @ApiModelProperty(value = "积分获得规则表编号")
    private Integer prizeRuleId;

    @Column(name = "type_one")
    @ApiModelProperty(value = "分享获取积分值")
    private Integer typeOne;

    @Column(name = "type_two")
    @ApiModelProperty(value = "登录获取积分值")
    private Integer typeTwo;

    @Column(name = "type_three_pay")
    @ApiModelProperty(value = "购买满值")
    private Integer typeThreePay;

    @Column(name = "type_three_get")
    @ApiModelProperty(value = "购买满获取积分值")
    private Integer typeThreeGet;

    @ApiModelProperty(value = "有效期")
    private Integer indate;

    @Column(name = "appmodel_id")
    @ApiModelProperty(value = "模板id")
    private String appmodelId;

    @ApiModelProperty(value = "说明")
    private String info;

    /**
     * @return prize_rule_id
     */
    public Integer getPrizeRuleId() {
        return prizeRuleId;
    }

    /**
     * @param prizeRuleId
     */
    public void setPrizeRuleId(Integer prizeRuleId) {
        this.prizeRuleId = prizeRuleId;
    }

    /**
     * 获取分享获取积分值
     *
     * @return type_one - 分享获取积分值
     */
    public Integer getTypeOne() {
        return typeOne;
    }

    /**
     * 设置分享获取积分值
     *
     * @param typeOne 分享获取积分值
     */
    public void setTypeOne(Integer typeOne) {
        this.typeOne = typeOne;
    }

    /**
     * 获取登录获取积分值
     *
     * @return type_two - 登录获取积分值
     */
    public Integer getTypeTwo() {
        return typeTwo;
    }

    /**
     * 设置登录获取积分值
     *
     * @param typeTwo 登录获取积分值
     */
    public void setTypeTwo(Integer typeTwo) {
        this.typeTwo = typeTwo;
    }

    /**
     * 获取购买满值
     *
     * @return type_three_pay - 购买满值
     */
    public Integer getTypeThreePay() {
        return typeThreePay;
    }

    /**
     * 设置购买满值
     *
     * @param typeThreePay 购买满值
     */
    public void setTypeThreePay(Integer typeThreePay) {
        this.typeThreePay = typeThreePay;
    }

    /**
     * 获取购买满获取积分值
     *
     * @return type_three_get - 购买满获取积分值
     */
    public Integer getTypeThreeGet() {
        return typeThreeGet;
    }

    /**
     * 设置购买满获取积分值
     *
     * @param typeThreeGet 购买满获取积分值
     */
    public void setTypeThreeGet(Integer typeThreeGet) {
        this.typeThreeGet = typeThreeGet;
    }

    /**
     * 获取有效期
     *
     * @return indate - 有效期
     */
    public Integer getIndate() {
        return indate;
    }

    /**
     * 设置有效期
     *
     * @param indate 有效期
     */
    public void setIndate(Integer indate) {
        this.indate = indate;
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
     * 获取说明
     *
     * @return info - 说明
     */
    public String getInfo() {
        return info;
    }

    /**
     * 设置说明
     *
     * @param info 说明
     */
    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "PrizeRule{" +
                "prizeRuleId=" + prizeRuleId +
                ", typeOne=" + typeOne +
                ", typeTwo=" + typeTwo +
                ", typeThreePay=" + typeThreePay +
                ", typeThreeGet=" + typeThreeGet +
                ", indate=" + indate +
                ", appmodelId='" + appmodelId + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}