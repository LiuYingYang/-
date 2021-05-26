package com.medusa.basemall.user.vo;

import com.medusa.basemall.user.entity.Member;
import com.medusa.basemall.user.entity.MemberGroupCategory;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;


public class MemberVo implements Serializable {

    @ApiModelProperty("会员对象数组")
    private List<Member> members;
    @ApiModelProperty("会员分组对象")
    private List<MemberGroupCategory> memberGroupCategories;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("用户id")
    private Long wxuserId;
    @ApiModelProperty("会员id")
    private Long memberId;
    @ApiModelProperty("验证码")
    private String code;
    @ApiModelProperty(value =  "商家唯一id",required = true)
    private String appmodelId;
    @ApiModelProperty(value = "多分组id,用逗号粪狗", example = "1,2,34,5,6")
    private String groupIds;
    @ApiModelProperty("页数")
    private int pageNum;
    @ApiModelProperty("页面大小")
    private int pageSize;
     @ApiModelProperty("注册类型 1-微信注册  2-用户注册")
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getWxuserId() {
        return wxuserId;
    }

    public void setWxuserId(Long wxuserId) {
        this.wxuserId = wxuserId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAppmodelId() {
        return appmodelId;
    }

    public void setAppmodelId(String appmodelId) {
        this.appmodelId = appmodelId;
    }

    public String getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String groupIds) {
        this.groupIds = groupIds;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public List<MemberGroupCategory> getMemberGroupCategories() {
        return memberGroupCategories;
    }

    public void setMemberGroupCategories(List<MemberGroupCategory> memberGroupCategories) {
        this.memberGroupCategories = memberGroupCategories;
    }
}
