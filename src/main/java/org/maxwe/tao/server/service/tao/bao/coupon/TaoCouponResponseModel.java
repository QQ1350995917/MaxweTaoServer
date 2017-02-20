package org.maxwe.tao.server.service.tao.bao.coupon;

import com.taobao.api.TaobaoResponse;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2017-02-17 13:47.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TaoCouponResponseModel extends TaobaoResponse {
    private LinkedList<TaoCouponResponseEntity> results;
    private long total_results;

    public TaoCouponResponseModel() {
        super();
    }

    public LinkedList<TaoCouponResponseEntity> getResults() {
        return results;
    }

    public void setResults(LinkedList<TaoCouponResponseEntity> results) {
        this.results = results;
    }

    public long getTotal_results() {
        return total_results;
    }

    public void setTotal_results(long total_results) {
        this.total_results = total_results;
    }
}
