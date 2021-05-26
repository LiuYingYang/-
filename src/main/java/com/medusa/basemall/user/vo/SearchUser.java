package com.medusa.basemall.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class SearchUser {

	@ApiModelProperty(value = "页数")
	Integer pageNum;
	@ApiModelProperty(value = "条数")
	Integer pageSize;
	@ApiModelProperty(value = "分组id")
	Integer groupId;
	@ApiModelProperty(value = "商家wxAppId")
	String appmodelId;
	@ApiModelProperty(value = "昵称")
	String nickName;
	@ApiModelProperty(value = "注册时间:用逗号分隔 2018-2-10,2018-2-22")
	String logintime;
	@ApiModelProperty(value = "成交时间:用逗号分隔  2018-2-10,2018-2-22")
	String dealtime;
	@ApiModelProperty(value = "排序方式:orderDesc(订单降序)/orderAsc(订单升序)/registerDesc(注册时间降序)/registerAsc(注册时间升序)")
	String order;

}
