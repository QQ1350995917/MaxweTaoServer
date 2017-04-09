package org.maxwe.tao.server.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Pengwei Ding on 2016-08-11 11:18.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 时间格式化工具
 */
public class DateTimeUtils {

    public static void test() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String month = String.format("%02d", Calendar.getInstance().get(Calendar.MONTH));
        String date = String.format("%02d", Calendar.getInstance().get(Calendar.DATE));
        String hour = String.format("%02d", Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        String minute = String.format("%02d", Calendar.getInstance().get(Calendar.MINUTE));
        int second = Calendar.getInstance().get(Calendar.SECOND);
        System.out.println(year + "/" + month + "/" + date + " " + hour + ":" + minute + ":" + second);
    }

    /**
     * 把年月日格式的日期转化为秒级数据
     *
     * @param ymdString 年月日字符串，使用中划线分割，如：2016-08-11
     * @return
     * @throws Exception
     */
    public static long parseYMDToLong(String ymdString) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long time = simpleDateFormat.parse(ymdString).getTime();
        return time;
    }

    /**
     * 全时间值转化为秒级数据
     *
     * @param ymdString
     * @return
     * @throws Exception
     */
    public static long parseFullTimeToLong(String ymdString) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = simpleDateFormat.parse(ymdString).getTime();
        return time;
    }

    public static String parseLongToFullTime(long timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timestamp);
        String format = simpleDateFormat.format(date);
        return format;
    }

    public static long getFixedTimestamp() throws Exception {
        long fixTime = parseFullTimeToLong("2015-12-31 23:59:59");
        return fixTime;
    }

    public static long getCurrentTimestamp() {
        return new Date().getTime();
    }

    /**
     * 获取当前的年份月份和天数
     *
     * @return
     */
    public static int[] getCurrentYearMonthDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DATE);
        return new int[]{year, month, date};
    }

    /**
     * 获取某年某月的起始和结束时间点
     *
     * @param year  年份
     * @param month 月份
     * @return
     * @throws Exception
     */
    public static long[] getMonthTimestampLine(int year, int month) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        int maximum = calendar.getActualMaximum(Calendar.DATE);
        int minimum = calendar.getActualMinimum(Calendar.DATE);

        calendar.set(Calendar.DATE, minimum);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long startLine = calendar.getTimeInMillis();


        calendar.set(Calendar.DATE, maximum);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        long endLine = calendar.getTimeInMillis();

        return new long[]{startLine, endLine};
    }

    /**
     * 获取某年某月的结束点和之前偏移月份的起始时间点
     *
     * @param year  年份
     * @param month 起始月份
     * @param offset 偏移月份
     * @return
     * @throws Exception
     */
    public static long[] getMonthTimestampLine(int year, int month,int offset) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        int maximum = calendar.getActualMaximum(Calendar.DATE);
        calendar.set(Calendar.DATE, maximum);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        long endLine = calendar.getTimeInMillis();

        calendar.set(Calendar.MONTH, month - 1 - offset);
        int minimum = calendar.getActualMinimum(Calendar.DATE);
        calendar.set(Calendar.DATE, minimum);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long startLine = calendar.getTimeInMillis();

        return new long[]{startLine, endLine};
    }
}
