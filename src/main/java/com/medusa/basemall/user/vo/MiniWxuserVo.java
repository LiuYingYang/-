package com.medusa.basemall.user.vo;

import com.medusa.basemall.user.entity.Member;
import com.medusa.basemall.user.entity.Wxuser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author whh
 */
@Data
public class MiniWxuserVo extends Wxuser {

	/**
	 * 会员信息
	 */
	@ApiModelProperty(value = "会员信息")
	private Member memberInfo;

	@ApiModelProperty(value = "增加积分")
	private Integer integralFlag = 0 ;

	@ApiModelProperty(value = "增加成长值")
	private Integer growthValue = 0;

}
