package org.maxwe.tao.server.controller.history.model;

import org.maxwe.tao.server.common.response.ResponseModel;
import org.maxwe.tao.server.service.history.HistoryEntity;

import java.util.List;

/**
 * Created by Pengwei Ding on 2017-03-10 11:32.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 历史信息列表响应模型
 */
public class HistoryListResponseModel extends ResponseModel<HistoryListRequestModel> {
    private int total;
    private List<HistoryEntity> histories;

    public HistoryListResponseModel() {
        super();
    }

    public HistoryListResponseModel(HistoryListRequestModel requestModel) {
        super(requestModel);
    }

    public HistoryListResponseModel(HistoryListRequestModel requestModel, int total) {
        super(requestModel);
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<HistoryEntity> getHistories() {
        return histories;
    }

    public void setHistories(List<HistoryEntity> histories) {
        this.histories = histories;
    }
}
