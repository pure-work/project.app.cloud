package com.gozi.core.base.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils extends DateUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_MONTH = "yyyy年MM月";
    public static final String DATETIME_FORMAT =
            "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_SHORT_FORMAT =
            "yyyy-MM-dd HH:mm";
    public static final String DATETIME_FULL_FORMAT =
            "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String[] PATTERNS = {
            DATE_FORMAT,
            DATETIME_SHORT_FORMAT,
            DATETIME_FORMAT,
            DATETIME_FULL_FORMAT,
            DATE_FORMAT_MONTH
    };

    public static Date parse(String text) throws ParseException {
        if (StringUtils.isEmpty(text)) {
            return null;
        }

        return DateUtils.parseDate(text, PATTERNS);
    }

    public static Date parse(String text, Date defaultValue) {
        if (StringUtils.isEmpty(text)) {
            return defaultValue;
        }

        try {
            return DateUtils.parseDate(text, PATTERNS);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static String format(Date date) {
        return DateFormatUtils.format(
                date, DateTimeUtils.DATETIME_FULL_FORMAT);
    }

    public static String format(Date date, String format) {
        return DateFormatUtils.format(date, format);
    }

    public static int getField(Date date, int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(field);
    }


    /**
     * 判断时间是否在时间段内
     *
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        return nowTime.getTime() >= beginTime.getTime() && nowTime.getTime() <= endTime.getTime();
    }
}
