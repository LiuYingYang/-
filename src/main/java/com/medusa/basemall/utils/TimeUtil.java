package com.medusa.basemall.utils;

import com.medusa.basemall.core.ServiceException;
import com.vip.vjtools.vjkit.time.ClockUtil;
import com.vip.vjtools.vjkit.time.DateFormatUtil;
import com.vip.vjtools.vjkit.time.DateUtil;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TimeUtil {

	public static final SimpleDateFormat DATETIMEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


	/**
	 * 字符串转换时间戳
	 *
	 * @param str
	 * @return
	 */
	public static Long str2Timestamp(String str) {
		try {
			return DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.parse(str).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 字符串转换DATE
	 *
	 * @param str
	 * @return
	 */
	public static Date str2Date(String str) {
		try {
			return DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 换时间戳转字符串
	 *
	 * @param timestamp
	 * @return
	 */
	public static String timestampToStr2(Long timestamp) {
		Timestamp t = new Timestamp(timestamp);
		Date date = new Date(t.getTime());
		return DATETIMEFORMAT.format(date);
	}

	/**
	 * 获取最后指定时间离当天结束的时间戳
	 * @return
	 */
	public static Long intradayLastSecond(Date date) {
		return DateUtil.endOfDate(date).getTime() - date.getTime();
	}

	/**
	 * 获取当前时间距离结束时间还需要多少时间戳
	 * @return
	 */
	public static Long endTiemSubtractStart(Date endDate) {
		return endDate.getTime() - ClockUtil.currentTimeMillis();
	}

	/**
	 * 获取当前时间距离结束时间还需要多少时间戳
	 * @return
	 */
	public static Long endTiemSubtractStart(String endDate) {
		try {
			long time = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.parse(endDate).getTime();
			return time - ClockUtil.currentTimeMillis();
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("时间转换有误");
		}
	}

	/**
	 * 指定时间是否在当前时间+指定秒范围之内
	 * @param date
	 * @param second
	 * @return
	 */
	public static Boolean dateIfScopeIn(String date, Integer second) {
		try {
			Date parse = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.parse(date);
			Date currentDate = ClockUtil.currentDate();
			Date startTime = DateUtil.subSeconds(currentDate, second / 2);
			Date endtime = DateUtil.addSeconds(currentDate, second / 2);
			return DateUtil.isBetween(parse, startTime, endtime);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("时间有误");
		}
	}


	/**
	 * 获取当前日期时间
	 *
	 * @return
	 */
	public static String getNowTime() {
		return DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.format(ClockUtil.currentDate());
	}

	/**
	 * 获取当前日期
	 *
	 * @return
	 */
	public static String getNowDate() {
		return DateFormatUtil.ISO_ON_DATE_FORMAT.format(ClockUtil.currentDate());
	}

	/**
	 * 获取从今天起n个月前后的时间 （2017/11/15-2017/08/15）
	 *
	 * @return
	 */
	public static String getMonth(int n) {
		Calendar calendar = Calendar.getInstance();
		// 得到前3个月
		calendar.add(Calendar.MONTH, n);
		Date formNow3Month = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(formNow3Month);
	}

	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<>();
		map.put(null, "123");
		System.out.println(map.get(null));
	}

	/**
	 * 判断开始时间是否小于结束时间
	 *
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean startdateGreaterthanEnddate(String startDate, String endDate) {
		try {
			long start = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.parse(startDate).getTime();
			long end = DateFormatUtil.DEFAULT_ON_SECOND_FORMAT.parse(endDate).getTime();
			if (start >= end) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			throw  new ServiceException("时间日期异常");
		}
		return false;
	}
}
