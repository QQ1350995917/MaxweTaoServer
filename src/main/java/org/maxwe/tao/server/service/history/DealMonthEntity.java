package org.maxwe.tao.server.service.history;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2017-03-04 21:51.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 成交量
 */
public class DealMonthEntity implements Serializable {
    private int year;
    private int month;
    private LinkedList<HistoryEntity> histories;

    public DealMonthEntity() {
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

    public LinkedList<HistoryEntity> getHistories() {
        return histories;
    }

    public void setHistories(LinkedList<HistoryEntity> histories) {
        this.histories = histories;
    }
}
