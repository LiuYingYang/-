package com.medusa.basemall.utils;

import com.vip.vjtools.vjkit.number.RandomUtil;
import com.vip.vjtools.vjkit.time.ClockUtil;
import com.vip.vjtools.vjkit.time.DateFormatUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * 各种id生成策略
 */
public class IdGenerateUtils {

	/**
	 * 生成带有业务前缀的uuid
	 * @return
	 */
	public static String getServicePrefixUuid(String prefix){
		StringBuilder sb = new StringBuilder();
		sb.append(prefix);
		sb.append("-");
		sb.append(UUID.randomUUID().toString());
		return sb.toString();
	}

	/**
	 * id生成
	 */
	public static long getItemId() {
		//取当前时间的长整形值包含毫秒
		long millis = System.currentTimeMillis();
		//long millis = System.nanoTime();
		//加上两位随机数
		Random random = new Random();
		int end3 = random.nextInt(999);
		//如果不足两位前面补0
		String str = millis + String.format("%03d", end3);
		long id = new Long(str);
		return id;
	}

	/**
	 * 订单号id生成  2018082314852725
	 * @return
	 */
	public static String getOrderNum(){
		String s = DateFormatUtil.formatDate("yyMMddHHss", ClockUtil.currentDate());
		int i = RandomUtil.nextInt(10, 99);
		int i1 = RandomUtil.nextInt(1000, 9999);
		return s + i + i1;
	}


	public static void main(String[] args) throws InterruptedException {
		Map<String,String> map = new HashMap<>(20000);
		int n = 0;
		for (int i = 0; i < 20000; i++) {
			Thread.sleep(3);
			System.out.println(i);
			String orderNum = getServicePrefixUuid("mds");
			if (map.get(orderNum) == null || map.get(orderNum).equals("")) {
				map.put(orderNum,i+"");
			}else{
				n++;
				System.out.println("相同次数出现 " +  orderNum +"   "+ n );
			}
		}
	}
	/**
	 * 生成会员卡编号 810207104737
	 */
	public static String getMembershipNumber() {
		//生成前四位
		LocalDate date = LocalDate.now();
		String s1 = new StringBuffer(Integer.toString(date.getYear())).reverse().toString();
		Integer s1Temp = Integer.valueOf(s1);
		s1 = s1Temp < 1000 ? String.valueOf(s1Temp += 1000) : s1;

		//生成中间4位
		LocalTime localTime = LocalTime.now();
		String hour = Integer.toString(date.getMonth().getValue());
		hour = generateNum(hour, 2);
		String minute = Integer.toString(localTime.getMinute());
		minute = generateNum(minute, 2);
		String s2 = hour + minute;

		//生成后四位
		String s3 = String.valueOf(localTime.getNano());
		s3 = generateNum(s3.substring(0, 2), 2);
		s3 = String.valueOf(localTime.getSecond()) + s3;
		s3 = generateNum(s3, 4);
		System.out.println(s1 + "  " + s2 + "  " + s3);
		return s1 + s2 + s3;
	}

	/**
	 * @param num  传入的字符串
	 * @param size 规定大小
	 * @return
	 */
	private static String generateNum(String num, int size) {
		if (num.length() < size) {
			for (int i = num.length(); i < size; i++) {
				num = "0" + num;
			}
		}
		return num;
	}

}
