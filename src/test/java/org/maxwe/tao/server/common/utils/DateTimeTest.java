package org.maxwe.tao.server.common.utils;

/**
 * Created by Pengwei Ding on 2016-08-11 11:25.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 */
public class DateTimeTest {

    public static void main(String[] args) throws Exception {
        long startLine = DateTimeUtils.getMonthTimestampLine(2017, 3, 1)[0];
        long endLine = DateTimeUtils.getMonthTimestampLine(2017, 3, 1)[1];

        System.out.println(DateTimeUtils.parseLongToFullTime(startLine));
        System.out.println(DateTimeUtils.parseLongToFullTime(endLine));

    }
}
