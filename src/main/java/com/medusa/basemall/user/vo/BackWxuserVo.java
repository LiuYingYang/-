package com.medusa.basemall.user.vo;

import com.medusa.basemall.user.entity.Member;
import com.medusa.basemall.user.entity.MemberGroupCategory;
import com.medusa.basemall.user.entity.Wxuser;
import com.medusa.basemall.user.entity.WxuserGroupCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class BackWxuserVo extends Wxuser {

	@ApiModelProperty(value = "下一等级的成长值", dataType = "Member")
	private Integer nextGrowthValue;

	@ApiModelProperty(value = "会员信息", dataType = "Member")
	private Member memberInfo;

	@ApiModelProperty(value = "注册时间"  )
	private String registerTime;

	@ApiModelProperty(value = "总交易额" )
	private Double totlePrice;

	@ApiModelProperty(value = "总的订单数", dataType = "Integer")
	private Integer totleOrder;

	@ApiModelProperty(value = "最后支付时间"  )
	private String lastPayTime;

	@ApiModelProperty(value = "会员分组"  )
	private List<MemberGroupCategory> memberGroups = new ArrayList<>();

	@ApiModelProperty(value = "用户分组"  )
	private List<WxuserGroupCategory> wxuserGroups = new ArrayList<>();



}
