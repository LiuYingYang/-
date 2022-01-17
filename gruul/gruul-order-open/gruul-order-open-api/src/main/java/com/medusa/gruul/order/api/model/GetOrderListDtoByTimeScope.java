package com.medusa.gruul.order.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lcysike
 */
@Data
@NoArgsConstructor
@ApiModel("查询对象")
public class GetOrderListDtoByTimeScope {
    @ApiModelProperty(name = "开始时间", dataType = "yyyy-MM-dd HH:mm:ss")
    private String start;
    @ApiModelProperty(name = "结束时间 ", dataType = "yyyy-MM-dd HH:mm:ss")
    private String end;

    public GetOrderListDtoByTimeScope(String start, String end) {
        this.start = start;
        this.end = end;
    }
}