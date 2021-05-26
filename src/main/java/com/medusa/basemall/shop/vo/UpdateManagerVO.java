package com.medusa.basemall.shop.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateManagerVO {

	@ApiModelProperty(value = "小程序appid")
	private String appid;
	@ApiModelProperty(value = "证书路径")
	private String certificatePath;
	@ApiModelProperty(value = "商户号")
	private String mchId;
	@ApiModelProperty(value = "秘钥")
	private String mchKey;
}
