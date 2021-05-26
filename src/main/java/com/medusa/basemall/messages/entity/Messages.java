package com.medusa.basemall.messages.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @author Created by wx on 2018/08/09.
 */
@Data
public class Messages {

    @Id
    @ApiModelProperty(value = "消息id")
    private String messageId;

    @ApiModelProperty(value = "消息标题")
    private String messageHead;

    @ApiModelProperty(value = "消息内容")
    private String messageBody;

    @ApiModelProperty(value = "消息是否已读")
    private Boolean readOrNot;

    @ApiModelProperty(value = "所属模快(1订单 2营销 3售后 4系统 5平台)")
    private Integer module;

    @ApiModelProperty(value = "所属类型(订单:1付款 2改价 3提醒发货 4订单收获 5订单关闭 " +
            "营销:1拼团 2会员 3代理申请 4代理绑定 " +
            "售后:1申请 2退款超时 3修改退款 " +
            "系统:1续费提醒 2购买 3开通 4审核结果 5提醒更新 6绑定子账号 7禁用子账号 8解禁子账号" +
            "平台:1上新 2活动)")
    private Integer type;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "模板id")
    private String appmodeId;

}
