package com.medusa.basemall.shop.vo;

import com.medusa.basemall.shop.entity.TransactionRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VersionBuyVO extends TransactionRecord {

	@ApiModelProperty(value = "购买的版本 1-标准版,2营销版,3旗舰版")
	private Integer buyVersion;
	@ApiModelProperty(value = "原本版本")
	private Integer originalVersion;
	@ApiModelProperty(value = "原本版本剩余天数")
	private Integer originalVersionNum;
	@ApiModelProperty(value = "服务内容")
	private String serviceContext;


}
