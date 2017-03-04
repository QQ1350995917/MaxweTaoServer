package org.maxwe.tao.server.controller.history.model;

import org.maxwe.tao.server.service.history.DealMonthEntity;

import java.util.List;

/**
 * Created by Pengwei Ding on 2017-03-04 21:47.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 成交量相应模型
 */
public class DealResponseModel extends DealRequestModel {
    private long total;
    private long totalPages;
    private List<DealMonthEntity> dealMonths;

    public DealResponseModel() {
        super();
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public List<DealMonthEntity> getDealMonths() {
        return dealMonths;
    }

    public void setDealMonths(List<DealMonthEntity> dealMonths) {
        this.dealMonths = dealMonths;
    }
}
