package com.medusa.basemall.agent.vo;

import io.swagger.annotations.ApiModelProperty;

public class AgentVo {


    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long wxuserid;

    /**
     * 申请表id
     */
	@ApiModelProperty(value = "申请表id")
    private Integer regId;

    /**
     * 申请人手机号
     */
	@ApiModelProperty(value = "申请人手机号")
    private String regPhone;

    /**
     * 申请人姓名
     */
	@ApiModelProperty(value = "申请人姓名")
    private String regName;

    /**
     * 注册时间
     */
	@ApiModelProperty(value = "注册时间")
    private String regTime;

    /**
     * 验证码
     */
	@ApiModelProperty(value = "验证码")
    private String regCode;


	@ApiModelProperty(value = "")
    private String bingCode;

    /**
     * 发送模板消息fromid
     */
	@ApiModelProperty(value = "")
    private String fromId;

    /**
     * 申请类别    1-前端  2-后端
     */
	@ApiModelProperty(value = "")
    private Integer type;

    /**
     * 条数
     */
	@ApiModelProperty(value = "")
    private Integer pageSize;

    /**
     * 页数
     */
	@ApiModelProperty(value = "")
    private Integer pageNum;

    /**
     * 审核状态， 0-审核中   1-拒绝申请	2.同意申请
     */
	@ApiModelProperty(value = "")
    private Integer regState;

    /**
     * 备注
     *
     */
	@ApiModelProperty(value = "")
    private String regRemark;

    /**
     * 拒绝原因
     */
	@ApiModelProperty(value = "")
    private String refuseRemark;

    /**
     * 申请等级
     */
	@ApiModelProperty(value = "")
    private Integer agentGrade;

    /**
     * 所在区域
     */
	@ApiModelProperty(value = "")
    private String agentDomain;

    /**
     * 模板id
     */
	@ApiModelProperty(value = "")
    private String appmodelId;

	@ApiModelProperty(value = "")
    private String agentProductIds;

	@ApiModelProperty(value = "")

    private String  productName;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAgentProductIds() {
        return agentProductIds;
    }

    public void setAgentProductIds(String agentProductIds) {
        this.agentProductIds = agentProductIds;
    }

    public String getBingCode() {
        return bingCode;
    }

    public void setBingCode(String bingCode) {
        this.bingCode = bingCode;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public Long getWxuserid() {
        return wxuserid;
    }

    public void setWxuserid(Long wxuserid) {
        this.wxuserid = wxuserid;
    }

    public Integer getRegId() {
        return regId;
    }

    public void setRegId(Integer regId) {
        this.regId = regId;
    }

    public String getRegPhone() {
        return regPhone;
    }

    public void setRegPhone(String regPhone) {
        this.regPhone = regPhone;
    }

    public String getRegName() {
        return regName;
    }

    public void setRegName(String regName) {
        this.regName = regName;
    }

    public String getRegCode() {
        return regCode;
    }

    public void setRegCode(String regCode) {
        this.regCode = regCode;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getRegState() {
        return regState;
    }

    public void setRegState(Integer regState) {
        this.regState = regState;
    }

    public String getRegRemark() {
        return regRemark;
    }

    public void setRegRemark(String regRemark) {
        this.regRemark = regRemark;
    }

    public String getRefuseRemark() {
        return refuseRemark;
    }

    public void setRefuseRemark(String refuseRemark) {
        this.refuseRemark = refuseRemark;
    }

    public Integer getAgentGrade() {
        return agentGrade;
    }

    public void setAgentGrade(Integer agentGrade) {
        this.agentGrade = agentGrade;
    }

    public String getAgentDomain() {
        return agentDomain;
    }

    public void setAgentDomain(String agentDomain) {
        this.agentDomain = agentDomain;
    }

    public String getAppmodelId() {
        return appmodelId;
    }

    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }
}
