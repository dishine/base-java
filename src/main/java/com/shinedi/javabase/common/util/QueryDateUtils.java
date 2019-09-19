package com.shinedi.javabase.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 16/7/1.
 */
public class QueryDateUtils {

    static Log log = LogFactory.getLog(QueryDateUtils.class);

    /**
     * 获取当前时间的开始一天和后一天
     * @return  Map<String, Date>
     * startOfToday   表示开始的一天日期
     * endOfToday   表示后的一天日期
     */
    public static Map<String, Date> getCurrentDatePeriod() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Map<String, Date> dateMap = new HashMap<>();
        dateMap.put("startOfToday", calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        dateMap.put("endOfToday", calendar.getTime());
        return dateMap;
    }

    /**
     * 获取当前时间的开始一天和前一天
     * @return  Map<String, Date>
     * startOfToday   表示开始的一天日期
     * endOfToday   表示后的一天日期
     */
    public static Map<String, Date> getFrontDatePeriod() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Map<String, Date> dateMap = new HashMap<>();
        dateMap.put("endOfToday", calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        dateMap.put("startOfToday", calendar.getTime());
        return dateMap;


    }

    /**
     *  获取当前时间指定当月一天的 日期
     * @param i  代表天数
     * @return  Date 类型
     */
    public static Date getFrontDate(int i){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, i);
        return calendar.getTime();
    }

    /**
     * 获取当前时间的开始一天和后一天
     * @param date
     * @return  Map<String, Date>
     *     startOfToday   表示开始的一天日期
     *     endOfToday   表示后的一天日期
     */
    public static Map<String, Date> getCurrentDatePeriod(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Map<String, Date> dateMap = new HashMap<>();
        dateMap.put("startOfToday", calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        dateMap.put("endOfToday", calendar.getTime());
        return dateMap;
    }

    /**
     * 改变当前时间的小时
     * @param date
     * @param i 正数为添，负数为减
     * @return
     */
    public static Date changeHour(Date date, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, i);
        return calendar.getTime();

    }

    /**
     * 日期修改分钟
     * @param date
     * @param min
     * @return
     */
    public static Date changeMins(Date date, int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, min);
        return calendar.getTime();

    }

    /**
     * 时间添加毫秒
     * @param date  时间
     * @param millisecond 毫秒数
     * @return  Date类型
     */
    public static Date getPreSecPeriod(Date date, int millisecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MILLISECOND, millisecond);
        return calendar.getTime();
    }

    /**
     * 改变当前时间的小时整点分钟为00
     * @param date
     * @param hour 正数为添，负数为减
     * @return
     */
    public static Date getHoursPeriod(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        return calendar.getTime();
    }

    /**
     * 将HH:mm类型时间转化为当天时间
     * @param intime 必须为 HH:mm
     * @return Date类型
     *  将08:10的时间转换为当前日期的(2016-08-01 08:10)
     */
    public static Date dateMakerHHmm(String intime) {
        String[] hourminute = intime.split(":");
        int hour = Integer.parseInt(hourminute[0]);
        int minute = Integer.parseInt(hourminute[1]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        return date;
    }
    /**
     * 将HH:mm类型时间转化为当天时间
     * @param intime 必须为 HH:mm
     * @return Date类型
     *  将08:10的时间转换为当前日期的(2016-08-01 08:10)
     */
    public static Date dateMakerHHmm(String intime, int min) {//将08:10的时间转换为当前日期的(2016-08-01 08:10)
        String[] hourminute = intime.split(":");
        int hour = Integer.parseInt(hourminute[0]);
        int minute = Integer.parseInt(hourminute[1]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.MINUTE, -min);
        Date date = calendar.getTime();
        return date;
    }

    /**
     * 将yyyy-MM-dd的时间添加时分
     * @param startDate  格式为yyyy-MM-dd类型的 时间
     * @param intime   HH:mm 类型的 需要拼接的时间字符串
     * @return Date
     */
    public static Date dateMaker(Date startDate, String intime) {
        String[] hourminute = intime.split(":");
        int hour = Integer.parseInt(hourminute[0]);
        int minute = Integer.parseInt(hourminute[1]);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        return date;
    }

    /**
     * 转换成yyyy-MM-dd格式的字符串
     * @param dateDate
     * @return
     */
    public static String dateToStrLong(Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将2016-08-01的时间转换为当前日期的(2016-08-01 08:10)
     * @param intime 2016-08-01 格式类型的时间
     * @return Date
     */
    public static Date yearMonthDayMaker(String intime) {
        Date calDate = null;
        try {
            String[] date = intime.split("-");
            int year = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int day = Integer.parseInt(date[2]);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month - 1);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            calDate = calendar.getTime();
        } catch (Exception e) {
            log.error("日期格式错误", e);
        }
        return calDate;


    }

    /**
     * 将时间类型转化为string类型
     * @param date date类型的时间
     * @return  yyyy-MM-dd HH:mm:ss string类型
     */
    public static String dateSecondFormater(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    /**
     * 将时间类型转化为string类型
     * @param date date类型的时间
     * @return  yyyy-MM-dd HH:mm string类型
     */
    public static String dateFormat(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(date);
    }

    /**
     * 将时间类型转化为string类型
     * @param date date类型的时间
     * @return  yyyy-MM-dd
     */
    public static String dateDayFromater(Date date) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);


    }

    /**
     * 将时间类型转化为string类型
     * @param date date类型的时间
     * @return  yyyy-MM
     */
    public static String dateMonthFormater(Date date) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        return simpleDateFormat.format(date);


    }


    /**
     * 判断某一时间是否在一个区间内
     *
     * @param sourceTime
     *            时间区间,半闭合,如[10:00-20:00)
     * @param curTime
     *            需要判断的时间 如10:00
     * @return
     * @throws IllegalArgumentException
     */
    public static boolean isInTime(String sourceTime, String curTime) {
        if (sourceTime == null || !sourceTime.contains("-") || !sourceTime.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }
        if (curTime == null || !curTime.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + curTime);
        }
        String[] args = sourceTime.split("-");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        try {
            long now = sdf.parse(curTime).getTime();
            long start = sdf.parse(args[0]).getTime();
            long end = sdf.parse(args[1]).getTime();
            if (args[1].equals("00:00")) {
                args[1] = "24:00";
            }
            if (end < start) {
                if (now >= end && now < start) {
                    return false;
                } else {
                    return true;
                }
            }
            else {
                if (now >= start && now < end) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }

    }
    /**
     * 比较HH:MM格式字符串的大小
     * 后面时间大则返回true
     * @param frontTime
     * @param endTime
     * @return
     */
    public static boolean compareHm(String frontTime, String endTime){
        boolean result = false;
        String[] beginArr = frontTime.split(":");
        String[] endArr = endTime.split(":");
        int beginH = Integer.parseInt(beginArr[0]);
        int beginM = Integer.parseInt(beginArr[1]);

        // 截取结束时间时分
        int endH = Integer.parseInt(endArr[0]);
        int endM = Integer.parseInt(endArr[1]);

        if(endH > beginH){
            result = true;
        }else if(endH == beginH && endM >= beginM){
            result = true;
        }else{
            result = false;
        }
        return result;
    }

    public static Date getHoursPeriod(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
