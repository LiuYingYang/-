package com.medusa.basemall.shop.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class MiniInfoVO {

	@ApiModelProperty(value = "小程序log")
	private String logo;
	@ApiModelProperty(value = "小程序二维码")
	private String miniCode;
	@ApiModelProperty(value = "版本下标")
	private String versionSubscript;
	@ApiModelProperty(value = "小程序名称")
	private String miniName;

	@ApiModelProperty(value = "用户余额")
	private BigDecimal balance;
	@ApiModelProperty(value = "是否缴纳保障金  false 未缴纳  true 缴纳")
	private Boolean flag;
	@ApiModelProperty(value = "版本 1-基础班,2-标准版,3-营销版")
	private Integer version;
	@ApiModelProperty(value = "到期时间")
	private Date expiryDate;
	@ApiModelProperty(value = "版本到期是否确认")
	private Boolean expiryDateNotify;

}
