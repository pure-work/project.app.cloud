package com.gozi.core.base.util;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {
	
	public final static String DATATIME = "yyyy-MM-dd HH:mm:ss";
	public static final String Y_M_D_H_M_S_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
	public final static String DATA = "yyyy-MM-dd";
	public static final String YMD = "yyyyMMdd";
	public static final String Y_M = "yyyy-MM";
	public static final String YMD_HMS = "yyyyMMddHHmmss";

	/** 采用Singleton设计模式而具有的唯一实例 */
	private static DateUtil instance = new DateUtil();
	
	/** 格式化器存储器 */
	private Map<String, Object> formats;
	
	
	/**
	 * 通过缺省日期格式得到的工具类实例
	 * 
	 * @return <code>DateUtilities</code>
	 */
	public static DateUtil getInstance() {
		return instance;
	}
	
	/** Reset the supported formats to the default set. */
	public void resetFormats() {
		formats = new HashMap<String, Object>();

		// alternative formats
		formats.put("yyyy-MM-dd HH:mm:ss", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

		// alternative formats
		formats.put("yyyy-MM-dd", new SimpleDateFormat("yyyy-MM-dd"));

		formats.put("yyyy-MM", new SimpleDateFormat("yyyy-MM"));

		// XPDL examples format
		formats.put("MM/dd/yyyy HH:mm:ss a", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a"));

		// alternative formats
		formats.put("yyyy-MM-dd HH:mm:ss a", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a"));

		// ISO formats
		formats.put("yyyy-MM-dd'T'HH:mm:ss'Z'", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"));
		formats.put("yyyy-MM-dd'T'HH:mm:ssZ", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"));
		formats.put("yyyy-MM-dd'T'HH:mm:ssz", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz"));
	}

	
	/**
	 * 解析字符串到日期型
	 * 
	 * @param dateString
	 *            日期字符串
	 * @return 返回日期型对象
	 */
	public Date parse(String dateString) {
		Iterator iter = formats.values().iterator();
		while (iter.hasNext()) {
			try {
				return ((DateFormat) iter.next()).parse(dateString);
			} catch (ParseException e) {
				// do nothing
			}
		}
		return null;
	}
	
	/**
	 * 对日期时间对象进行调整
	 * 
	 * @param date
	 * @param CALENDAR_FIELD
	 * 
	 *            <pre>
	 * 年 Calendar.YEAR
	 * 月 Calendar.MONTH
	 * 日 Calendar.DATE
	 * 时 Calendar.HOUR
	 * 分 Calendar.MINUTE
	 * 秒 Calendar.SECOND
	 *            </pre>
	 * 
	 * @param amount
	 *            调整数量，>0表向后调整（明天，明年），<0表向前调整（昨天，去年）
	 * @return
	 */
	public static Date transpositionDate(Date date, int CALENDAR_FIELD, int amount) {
		if (null == date) {
			return date;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(CALENDAR_FIELD, amount);
		return calendar.getTime();
	}
	
	/**
	 * 解析日期
	 * 
	 * @param date
	 *            日期字符串
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static Date str2date(String date, String pattern) {
		Date resultDate = null;
		try {
			resultDate = new SimpleDateFormat(pattern).parse(date);
		} catch (ParseException e) {
		}
		return resultDate;
	}
	
	/**
	 * 格式化日期字符串 如果日期为空，那么就直接返回""
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static String format(Date date, String pattern) {
		if (StringUtil.isNullOrEmpty(date)) {
			return "";
		} else {
			DateFormat format = new SimpleDateFormat(pattern);
			return format.format(date);
		}
	}

	/**
	 * @return 当前的时间戳 yyyyMMddHHmmss
	 */
	public static String getCurrentTime() {
		return DateUtil.format(new Date(), "yyyyMMddHHmmss");
	}
	
	/**
	 * @return 当前的时间戳 yyyy-MM-dd HH:mm:ss
	 */
	public static String getNowTime() {
		return DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取给定时间的月份第一天时间
	 * @param date yyyy-MM
	 * @return
	 */
	public static Date getMonthFirstDay(String date) throws ParseException {
		if (StringUtils.isBlank(date)) return null;
		SimpleDateFormat ymFormat = new SimpleDateFormat(Y_M);
		Date in = ymFormat.parse(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(in);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取给定时间的月份最后一天时间
	 * @param date yyyy-MM
	 * @return
	 */
	public static Date getMonthLastDay(String date) throws ParseException {
		if (StringUtils.isBlank(date)) return null;
		SimpleDateFormat ymFormat = new SimpleDateFormat(Y_M);
		Date in = ymFormat.parse(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(in);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	
	/**
	 * 根据日期，获取秒的时间戳
	 * @param date
	 * @return
	 */
	public static Integer getTimeInSecond(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return ObjUtil.toInt(c.getTime().getTime() / 1000);
	}
	
	public static int differentDaysByMillisecond(Date date1,Date date2){
	    int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
	    return days;
	}
	/** 获取距离第二天的某小时(24时制)秒数 */
	public static int getSecondToNextDayHours(int nextDayHour24Fix){
		Date date = new Date();
		int daySecond = dayTimeInSecond(date);
		return 86400 - daySecond + nextDayHour24Fix * 3600;
	}
	private static int dayTimeInSecond(Date date){
		int hours = date.getHours();
		int minutes = date.getMinutes();
		int seconds = date.getSeconds();
		return hours * 3600 + minutes * 60 + seconds;
	}
	/** 获取距离第二天凌晨的秒数 */
	public static int getSecondToNextDay(){
		return getSecondToNextDayHours(0);
	}

	public static long differentMillisecond(Date date1,Date date2){
		return date2.getTime() - date1.getTime();
	}
	
	public static void main(String[] args) {
		Date date = DateUtil.str2date("20190611","yyyyMMdd");
		int days = DateUtil.differentDaysByMillisecond(date, new Date());
		System.out.println(days);

		System.out.println(getSecondToNextDayHours(0));
	}
	
}
