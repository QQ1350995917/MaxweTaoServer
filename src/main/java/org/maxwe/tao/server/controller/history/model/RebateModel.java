package org.maxwe.tao.server.controller.history.model;

import java.io.Serializable;

/**
 * Created by Pengwei Ding on 2017-03-05 11:37.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 返点数据模型
 */
public class RebateModel implements Serializable {
    private int year;
    private int month;
    private int rebate;

    public RebateModel() {
        super();
    }

    public RebateModel(int year,int month,int rebate) {
        super();
        this.year = year;
        this.month = month;
        this.rebate = rebate;
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

