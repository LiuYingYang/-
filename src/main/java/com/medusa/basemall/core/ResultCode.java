package com.medusa.basemall.core;

/**
 * 响应码枚举，参考HTTP状态码的语义
 */
public enum ResultCode {
    //成功
    SUCCESS(100),
    //失败
    FAIL(99),
    //未认证（签名错误）
    UNAUTHORIZED(99),
    //接口不存在
    NOT_FOUND(99),
    //服务器内部错误
    INTERNAL_SERVER_ERROR(99);

    private final int code;

    ResultCode(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }

}
