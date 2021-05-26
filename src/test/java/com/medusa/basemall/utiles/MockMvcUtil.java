package com.medusa.basemall.utiles;

import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SuppressWarnings("ALL")
public class MockMvcUtil {

	public static MockMvc mockMvc;

	/**
	 * @param url         请求地址
	 * @param content     传入对象为json字符串
	 * @param params      get请求参数
	 * @param requestType 请求类型 post/get
	 */
	public static JSONObject sendRequest(String url, String content, MultiValueMap<String, String> params,
			String requestType) {
		MvcResult result;
		if (params == null) {
			params = new LinkedMultiValueMap<>();
		}
		if (content == null) {
			content = "";
		}
		String contentAsString = "";
		try {
			System.out.println(
					Color.ANSI_GREEN + "==============Start=================>>>url请求地址: " + url + Color.ANSI_RESET);
			MvcResult request = request(url, content, params, requestType);
			if (request.getResolvedException() != null) {
				Assert.assertNull(request.getResolvedException().getMessage(),request.getResolvedException());
			}
			if (request.getResolvedException() != null) {
				String message = request.getResolvedException().getMessage();
				Assert.assertNotEquals("请求方法错误", "Request method 'POST' not supported", message);
				Assert.assertNotEquals("请求方法错误", "Request method 'GET' not supported", message);
				Assert.assertNotEquals("请求方法错误", "Request method 'PUT' not supported", message);
				Assert.assertNotEquals("请求方法错误", "Request method 'DELETE' not supported", message);

			}
			assert request.getResponse().getStatus() == 200 : "找不到该url地址";
			contentAsString = request.getResponse().getContentAsString();
			System.out.println(
					Color.ANSI_RED + "==============End=====================>>>url请求地址: " + url + Color.ANSI_RESET);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.parseObject(contentAsString);
		//resultHandle(jsonObject, url, 100, "SUCCESS");
		return jsonObject;
	}

	private static MvcResult request(String url, String content, MultiValueMap<String, String> params,
			String requestType) throws Exception {
		MvcResult result = null;
		if ("post".equals(requestType.toLowerCase())) {
			result = post(url, content, params);
		}
		if ("get".equals(requestType.toLowerCase())) {
			result = get(url, content, params);
		}
		if ("put".equals(requestType.toLowerCase())) {
			result = put(url, content, params);
		}
		if ("delete".equals(requestType.toLowerCase())) {
			result = delete(url, content, params);
		}
		return result;
	}


	private static MvcResult post(String url, String content, MultiValueMap<String, String> params) throws Exception {
		return mockMvc.perform(
				MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(content)
						.params(params).header("Genre", 1)
						.header("Token", "Li3MpGWtl537AUBHlCQcwEPNHbT7qx/2mWmL8W/TI/M=")).andReturn();
	}

	private static MvcResult get(String url, String content, MultiValueMap<String, String> params) throws Exception {
		return mockMvc.perform(
				MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(content)
						.params(params).header("Genre", 1)
						.header("Token", "Li3MpGWtl537AUBHlCQcwEPNHbT7qx/2mWmL8W/TI/M=")).andReturn();
	}

	private static MvcResult delete(String url, String content, MultiValueMap<String, String> params) throws Exception {
		return mockMvc.perform(
				MockMvcRequestBuilders.delete(url).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(content)
						.params(params).header("Genre", 1)
						.header("Token", "Li3MpGWtl537AUBHlCQcwEPNHbT7qx/2mWmL8W/TI/M=")).andReturn();
	}

	private static MvcResult put(String url, String content, MultiValueMap<String, String> params) throws Exception {
		return mockMvc.perform(
				MockMvcRequestBuilders.put(url).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(content)
						.params(params).header("Genre", 1)
						.header("Token", "Li3MpGWtl537AUBHlCQcwEPNHbT7qx/2mWmL8W/TI/M=")).andReturn();
	}

}
