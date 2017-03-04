package org.maxwe.tao.server.controller.history.model;

import org.maxwe.tao.server.service.history.RebateMonthEntity;

import java.util.List;

/**
 * Created by Pengwei Ding on 2017-03-04 21:32.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 代理返点请求模型
 */
public class RebateResponseModel extends RebateRequestModel {
    private long total;
    private long totalPages;
    private List<RebateMonthEntity> rebates;

    public RebateResponseModel() {
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

    public List<RebateMonthEntity> getRebates() {
        return rebates;
    }

    public void setRebates(List<RebateMonthEntity> rebates) {
        this.rebates = rebates;
    }
}
