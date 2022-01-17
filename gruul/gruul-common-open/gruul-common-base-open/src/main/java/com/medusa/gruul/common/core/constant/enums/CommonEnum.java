package com.medusa.gruul.common.core.constant.enums;


import lombok.Getter;
import lombok.Setter;

/**
 * <p>Descrition: 返回状态 枚举类</P>
 * @author zhaozheng
 */
public enum CommonEnum {

    SUCCESS_RESPONSE(200, "成功"),
    FAILED_RESPONSE(500, "失败");
    @Setter
    @Getter
    private Integer code;
    @Setter
    @Getter
    private String msg;


    CommonEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
