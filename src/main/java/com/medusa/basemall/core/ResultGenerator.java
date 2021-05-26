package com.medusa.basemall.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 响应结果生成工具
 */
public class ResultGenerator {

	private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";
	private static final Logger logger = LoggerFactory.getLogger(ResultGenerator.class);

	public static Result genSuccessResult() {
		return new Result().setCode(ResultCode.SUCCESS).setMessage(DEFAULT_SUCCESS_MESSAGE);
	}

	public static Result genSuccessResult(Object data) {
		return new Result().setCode(ResultCode.SUCCESS).setMessage(DEFAULT_SUCCESS_MESSAGE).setData(data);
	}

	public static Result genFailResult(String message) {
		return new Result().setCode(ResultCode.FAIL).setMessage(message);
	}

	public static Result genFailResult(String message, Object data) {
		return new Result().setCode(ResultCode.FAIL).setMessage(message).setData(data);

	}

	public static void responseResult(HttpServletResponse response, Result result) {
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "application/json;charset=UTF-8");
		response.setStatus(200);
		try {
			response.getWriter().write(JSON.toJSONString(result, SerializerFeature.WriteMapNullValue));
		} catch (IOException ex) {
			logger.error(ex.getMessage());
		}
	}
}
