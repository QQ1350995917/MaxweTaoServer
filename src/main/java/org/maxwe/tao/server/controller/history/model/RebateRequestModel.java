package org.maxwe.tao.server.controller.history.model;

import org.maxwe.tao.server.common.utils.DateTimeUtils;
import org.maxwe.tao.server.controller.account.model.TokenModel;

/**
 * Created by Pengwei Ding on 2017-03-04 21:31.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 代理返点请求模型
 */
public class RebateRequestModel extends TokenModel {
    private int year;//年份
    private int month;//月份
    private int monthCounter;//月份数量

    public RebateRequestModel() {
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

    public int getMonthCounter() {
        return monthCounter;
    }

    public void setMonthCounter(int monthCounter) {
        this.monthCounter = monthCounter;
    }

    public boolean isRebateParamsOk(){
        if (year < 2017 || year > DateTimeUtils.getCurrentYear()[0]){
            return false;
        }
        if (month < 0 || month > 13){
            return false;
        }
        return super.isTokenParamsOk();
    }
}
