package org.maxwe.tao.server.controller.history.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.maxwe.tao.server.controller.account.model.TokenModel;

/**
 * Created by Pengwei Ding on 2017-03-10 11:32.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 历史信息列表请求模型
 */
public class HistoryListRequestModel extends TokenModel {
    private int pageIndex; //
    private int pageSize; //

    public HistoryListRequestModel() {
        super();
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

    @JSONField(serialize = false)
    public boolean isListRequestParamsOk(){
        if (pageIndex < 0){
            return false;
        }
        if (pageSize < 1){
            return false;
        }
        return super.isTokenParamsOk();
    }
}
