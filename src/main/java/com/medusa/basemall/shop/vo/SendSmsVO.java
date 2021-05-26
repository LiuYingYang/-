package com.medusa.basemall.shop.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SendSmsVO {
	@ApiModelProperty(value = "手机号")
	String phone;
	@ApiModelProperty(value = "1.会员注册获取验证码 2.代理商绑定获取验证码3.代理商申请获取验证码,4,商家同意退款验证,5.余额解冻验证")
	Integer type;
	@ApiModelProperty(value = "商家wxappmodeId")
	String appmodelId;
}
