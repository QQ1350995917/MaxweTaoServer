package org.maxwe.product;

/**
 * Created by Pengwei Ding on 2016-08-05 22:07.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class Test {
    public static void main(String[] args) throws Exception {
        String[] strings = {" "};
        String sql = "";
        for (String string : strings) {
            sql = sql + "'" + string + "',";
        }
        if (sql.length() > 0) {
            String substring = sql.substring(0, sql.length() - 1);
            System.out.println(substring);
        }
        System.out.println("over");
    }
}
