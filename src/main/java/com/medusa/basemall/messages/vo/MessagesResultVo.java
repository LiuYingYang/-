package com.medusa.basemall.messages.vo;

import com.medusa.basemall.messages.entity.Messages;
import com.medusa.basemall.utils.MongoPageModel;
import io.swagger.annotations.ApiModelProperty;

public class MessagesResultVo {

    @ApiModelProperty(value = "订单未读消息")
    private Integer firstNotRead;

    @ApiModelProperty(value = "营销未读消息")
    private Integer secondNotRead;

    @ApiModelProperty(value = "售后未读消息")
    private Integer thirdNotRead;

    @ApiModelProperty(value = "系统未读消息")
    private Integer fourthNotRead;

    @ApiModelProperty(value = "平台未读消息")
    private Integer fifthNotRead;

    @ApiModelProperty(value = "分页数据")
    private MongoPageModel<Messages> page;

    public Integer getFirstNotRead() {
        return firstNotRead;
    }

    public void setFirstNotRead(Integer firstNotRead) {
        this.firstNotRead = firstNotRead;
    }

    public Integer getSecondNotRead() {
        return secondNotRead;
    }

    public void setSecondNotRead(Integer secondNotRead) {
        this.secondNotRead = secondNotRead;
    }

    public Integer getThirdNotRead() {
        return thirdNotRead;
    }

    public void setThirdNotRead(Integer thirdNotRead) {
        this.thirdNotRead = thirdNotRead;
    }

    public Integer getFourthNotRead() {
        return fourthNotRead;
    }

    public void setFourthNotRead(Integer fourthNotRead) {
        this.fourthNotRead = fourthNotRead;
    }

    public Integer getFifthNotRead() {
        return fifthNotRead;
    }

    public void setFifthNotRead(Integer fifthNotRead) {
        this.fifthNotRead = fifthNotRead;
    }

    public MongoPageModel<Messages> getPage() {
        return page;
    }

    public void setPage(MongoPageModel<Messages> page) {
        this.page = page;
    }
}
