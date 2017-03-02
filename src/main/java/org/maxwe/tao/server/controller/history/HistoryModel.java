package org.maxwe.tao.server.controller.history;

import org.maxwe.tao.server.controller.account.model.TokenModel;
import org.maxwe.tao.server.service.history.HistoryEntity;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2017-01-09 18:38.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description:
 */
public class HistoryModel extends TokenModel {

    private int total; // 响应字段
    private int pageIndex; // 请求字段
    private int pageSize; // 请求字段

    private LinkedList<HistoryEntity> historyEntities; // 响应字段

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public LinkedList<HistoryEntity> getHistoryEntities() {
        return historyEntities;
    }

    public void setHistoryEntities(LinkedList<HistoryEntity> historyEntities) {
        this.historyEntities = historyEntities;
    }

    @Override
    public String toString() {
        return "HistoryModel{" +
                "historyEntities=" + historyEntities +
                ", pageSize=" + pageSize +
                ", pageIndex=" + pageIndex +
                ", total=" + total +
                '}';
    }

    public boolean isParamsOk() {
        return super.isTokenParamsOk() && this.getPageIndex() >= 0 && this.getPageSize() >= 1;
    }
}
