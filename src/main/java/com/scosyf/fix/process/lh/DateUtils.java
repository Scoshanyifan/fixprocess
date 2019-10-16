package com.scosyf.fix.process.lh;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
 * 时间格式化工具包
 * 统一格式为：yyyy-MM-dd HH:mm:ss
 */
public class DateUtils {

    /**
     * 常用日期格式
     */
    public static final String[] COMMON_DATE_FORMATS = new String[] { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH", "yyyy-MM-dd"};

    public static String toString(Date date) {
        String dateString = "";
        if (date == null) {
            return dateString;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateString = sdf.format(date);
        } catch (Exception localException) {
        }
        return dateString;
    }

    public static String toString(Date date, String format) {
        String dateString = "";
        if ((date == null) || (format == null) || (format == "")) {
            return dateString;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            dateString = sdf.format(date);
        } catch (Exception localException) {
        }
        return dateString;
    }

    public static Date toDate(String dateString) {
        if ((dateString == null) || (dateString == "")) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date toDate(String dateString, String format) {
        if ((dateString == null) || (dateString == "") || (format == null)
                || (format == "")) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date();
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date toDate(String dateString, String[] formats) {
        return toDate(dateString, formats, null);
    }

    public static Date toDate(String dateString, String[] formats, Date defaultDate) {
        if (StringUtils.isEmpty(dateString)) {
            return null;
        }

        Date date = null;
        for (String format : formats) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            try {
                date = sdf.parse(dateString);
                if (date != null) {
                    return date;
                }
            } catch (ParseException e) {
            }
        }
        return date == null ? defaultDate : date;
    }
    
    public static Date getYYMDay() {
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.DATE, -1);
    	// 将时分秒毫秒域清零
    	cal.set(Calendar.HOUR_OF_DAY, 0);
    	cal.set(Calendar.MINUTE, 0);
    	cal.set(Calendar.SECOND, 0);
    	cal.set(Calendar.MILLISECOND, 0);
    	return cal.getTime();
    }

    /**
     * 返回指定天的开始时间
     * @param date
     * @return
     */
    public static Date startOfDay(Date date){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date.getTime());
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND,0) ;
        return c.getTime() ;
    }

    /**
     * 返回指定天的结束时间
     * @param date
     * @return
     */
    public static Date endOfDay(Date date){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date.getTime());
        c.set(Calendar.HOUR_OF_DAY,23);
        c.set(Calendar.MINUTE,59);
        c.set(Calendar.SECOND,59);
        c.set(Calendar.MILLISECOND,999) ;
        return c.getTime() ;
    }
}
