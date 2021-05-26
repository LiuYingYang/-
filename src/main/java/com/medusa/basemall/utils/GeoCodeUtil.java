package com.medusa.basemall.utils;

import com.github.kevinsawicki.http.HttpRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * 高德地图工具类
 * 依赖开源项目 com.github.kevinsawicki(http-request)
 * @author whh
 */
public class GeoCodeUtil {

	/**
	 * 高德地图秘钥
	 */
	private static final String KEY = "3d0918c9dc956b167c7d6af57bed4460";

	/**
	 * 获取地理/逆地理编码 API
	 */
	private static String ADDRESSENCODEDURL = "https://restapi.amap.com/v3/geocode/geo";

	/**
	 * 距离测量API
	 */
	private static String RANGEMEASUREMENTURL = "https://restapi.amap.com/v3/distance";

	/**
	 * 联级请求
	 */
	private static String DISTRICTURL = "http://restapi.amap.com/v3/config/district";


	private static Map<String, Object> params = new HashMap<>();

	static {
		params.put("key", KEY);
	}

	/**
	 * 获取地理/逆地理编码
	 * @url https://lbs.amap.com/api/webservice/guide/api/georegeo
	 * @param address 结构化地址信息
	 * @param batch  是否批量查询控制
	 * @return
	 */
	public static String getAddressEncoded(String address, boolean batch) {
		params.put("address", address);
		params.put("batch", batch);
		HttpRequest httpRequest = HttpRequest.get(ADDRESSENCODEDURL, params, true);
		params.remove(address);
		params.remove(batch);
		return httpRequest.body();
	}

	/**
	 *  距离测量
	 * @url https://lbs.amap.com/api/webservice/guide/api/direction
	 * @param origins 出发点
	 * @param destination 目的地
	 * @param type  路径计算的方式和方法  默认1
	 * @return
	 */
	public static String getRangeMeasurement(String origins, String destination, Integer type) {
		params.put("origins", origins);
		params.put("destination", destination);
		if (type != null) {
			params.put("type", type);
		}
		String body = HttpRequest.get(RANGEMEASUREMENTURL, params, true).body();
		params.remove("origins");
		params.remove("destination");
		return body;
	}

	public static String getDistrict() {
		params.put("subdistrict", 3);
		return HttpRequest.get(DISTRICTURL, params, true).body();
	}

	public static void main(String[] args) {
		System.out.println(getDistrict());
	}

}
