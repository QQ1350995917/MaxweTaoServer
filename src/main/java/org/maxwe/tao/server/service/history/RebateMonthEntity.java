package org.maxwe.tao.server.service.history;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-03-04 21:40.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 月返点
 */
public class RebateMonthEntity implements Serializable {
    private int year;//返点年份
    private int month;//返点月份
    private int rebate;//返点数量

    public RebateMonthEntity() {
        super();
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getRebate() {
        return rebate;
    }

    public void setRebate(int rebate) {
        this.rebate = rebate;
    }
}
