package com.medusa.basemall.order.entity;

import com.medusa.basemall.promotion.entity.Group;
import com.medusa.basemall.promotion.entity.GroupMember;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Transient;
import java.util.List;

/**
 * @author whh
 */
@Data
public class OrderExtend extends Order {

    @Transient
    @ApiModelProperty(value = "订单商品详情")
    private List<OrderDetail> orderDetails;
    @Transient
    private Integer groupLimit;
    @Transient
    private Group group;
    @Transient
    private GroupMember groupMember;
    @Transient
    @ApiModelProperty(value = "用户昵称")
    private String nickName;
    @Transient
    @ApiModelProperty(value = "用户头像")
    private String avatarUrl;
    @Transient
    @ApiModelProperty(value = "会员id")
    private Long memberId;
	@Transient
	@ApiModelProperty(value = "优惠券优惠")
	private String couponDiscount;


}
