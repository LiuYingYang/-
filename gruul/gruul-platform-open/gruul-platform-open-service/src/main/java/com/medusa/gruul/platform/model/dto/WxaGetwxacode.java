package com.medusa.gruul.platform.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 获取小程序码dto
 *
 * @author whh
 * @description
 * @data: 2020/1/12
 */
@Data
public class WxaGetwxacode {


    @ApiModelProperty(value = "扫码进入的小程序页面路径，最大长度 128 字节，不能为空,可页面参数,请求头需带上租户id", required = true)
    @NotBlank(message = "路径不能为空")
    private String path;


    @ApiModelProperty("二维码的宽度，单位 px。最小 280px，最大 1280px,非必填,默认430px")
    private Integer width;
}
