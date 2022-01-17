package com.medusa.gruul.common.core.util;

import com.alibaba.fastjson.JSON;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.Math.PI;

/**
 * 经纬度工具
 *
 * @author whh
 */
public class DistanceUtil {

    /**
     *
     */
    private static double EARTH_RADIUS = 6378137.00;

    private static double rad(double d) {
        return d * PI / 180.00;
    }

    /**
     * 根据两个位置的经纬度，来计算两地的距离（单位米）
     * 参数为String类型
     *
     * @param lat1Str 用户经度
     * @param lng1Str 用户纬度
     * @param lat2Str 商家经度
     * @param lng2Str 商家纬度
     * @return
     */
    public static String getDistance(String lat1Str, String lng1Str, String lat2Str, String lng2Str) {
        Double lat1 = Double.parseDouble(lat1Str);
        Double lng1 = Double.parseDouble(lng1Str);
        Double lat2 = Double.parseDouble(lat2Str);
        Double lng2 = Double.parseDouble(lng2Str);

        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double difference = radLat1 - radLat2;
        double mdifference = rad(lng1) - rad(lng2);
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(mdifference / 2), 2)));

        distance = distance * EARTH_RADIUS;
        distance = Math.round(distance * 10000) / 10000;
        String distanceStr = distance + "";
        distanceStr = distanceStr.
                substring(0, distanceStr.indexOf("."));
        return distanceStr;
    }


    /**
     * 通过经纬度获取距离信息<br></>
     * 取WGS84标准参考椭球中的地球长半径(单位:m)
     *
     * @param lng1
     * @param lat1
     * @param lng2
     * @param lat2
     * @return double s
     */
    public static double distanceByLngLat(double lng1, double lat1, double lng2, double lat2) {

        if (lng1 == 0 || lng2 == 0 || lat1 == 0 || lat2 == 0) {

            return 0;
        } else {
            double radLat1 = lat1 * PI / 180.0;

            double radLat2 = lat2 * PI / 180.0;

            double a = radLat1 - radLat2;

            double b = lng1 * PI / 180.0 - lng2 * PI / 180.0;

            double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)

                    * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
            // 取WGS84标准参考椭球中的地球长半径(单位:m)
            s = s * 6378137.0;

            s = Math.round(s * 10000) / 10000;

            if (s < 10) {
                s = 0;
            }
            return s;

        }

    }


    /**
     * 根据经纬度和半径计算经纬度范围
     *
     * @param raidus 单位米
     * @return minLat, minLng, maxLat, maxLng
     */
    public static double[] getAround(String lat, String lon, int raidus) {

        Double latitude = Double.valueOf(lat);
        Double longitude = Double.valueOf(lon);

        Double degree = (24901 * 1609) / 360.0;
        double raidusMile = raidus;

        Double dpmLat = 1 / degree;
        Double radiusLat = dpmLat * raidusMile;
        Double minLat = latitude - radiusLat;
        Double maxLat = latitude + radiusLat;

        Double mpdLng = degree * Math.cos(latitude * (PI / 180));
        Double dpmLng = 1 / mpdLng;
        Double radiusLng = dpmLng * raidusMile;
        Double minLng = longitude - radiusLng;
        Double maxLng = longitude + radiusLng;
        return new double[]{minLat, minLng, maxLat, maxLng};
    }


}
