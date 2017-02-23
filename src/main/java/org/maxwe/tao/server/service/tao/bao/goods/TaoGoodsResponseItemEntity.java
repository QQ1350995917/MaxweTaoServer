package org.maxwe.tao.server.service.tao.bao.goods;

/**
 * Created by Pengwei Ding on 2017-02-19 13:56.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TaoGoodsResponseItemEntity {
    private TaoGoodsResponseResult results;
    private long total_results;
    private String request_id;

    public TaoGoodsResponseItemEntity() {
        super();
    }

    public TaoGoodsResponseResult getResults() {
        return results;
    }

    public void setResults(TaoGoodsResponseResult results) {
        this.results = results;
    }

    public long getTotal_results() {
        return total_results;
    }

    public void setTotal_results(long total_results) {
        this.total_results = total_results;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }
}
