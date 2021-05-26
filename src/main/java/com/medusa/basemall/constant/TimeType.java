package com.medusa.basemall.constant;

/**
 * 时间类型
 *
 * @author whh
 */
public interface TimeType {

    /**
     * 1 分钟
     */
    Long TEMPTIME = 1 * 60 * 1000L;

    /**
     * 5 分钟
     */
    Long FIVEMINUTES = 5 * 60 * 1000L;

	/**
	 * 10 分钟
	 */
	Long TENMINUTES = 10 * 60 * 1000L;


	/**
     * 30分钟
     */
    Long HALFHOUR = 30 * 60 * 1000L;

	/**
	 * 24小时  (1天)
	 */
	Long ONEDAY = 1 * 24 * 60 * 60 * 1000L;

    /**
     * 48小时  (2天)
     */
    Long NEXTDAY = 2 * 24 * 60 * 60 * 1000L;

    /**
     * 5天
     */
    Long FIVEDAY = 5 * 24 * 60 * 60 * 1000L;

    /**
     * 7天
     */
    Long SEVENDAY = 7 * 24 * 60 * 60 * 1000L;


    /**
     * 1小时
     */
    Long ONEHOUR = 60 * 60 * 1000L;

	/**
	 * 19天
	 */
	Long NINETEENDAYS = 15 * 24 * 60 * 60 * 1000L;
    /**
     * 20天
     */
    Long TWENTYDAY = 20 * 24 * 60 * 60 * 1000L;
}
