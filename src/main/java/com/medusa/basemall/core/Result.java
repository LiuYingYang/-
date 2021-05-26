package com.medusa.basemall.core;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModelProperty;

/**
 * 统一API响应结果封装
 */
public class Result<T> {

    @ApiModelProperty(value = "业务状态码",example = "99||100")
    private int code;

    @ApiModelProperty("状态消息")
    private String message;

    @ApiModelProperty("封装的数据")
    private T data;

    public Result setCode(ResultCode resultCode) {
        this.code = resultCode.code();
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
