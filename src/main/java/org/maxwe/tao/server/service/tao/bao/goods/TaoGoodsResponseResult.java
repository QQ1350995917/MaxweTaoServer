package org.maxwe.tao.server.service.tao.bao.goods;

import com.taobao.api.TaobaoResponse;

import java.util.LinkedList;

/**
 * Created by Pengwei Ding on 2017-01-18 15:23.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TaoGoodsResponseResult extends TaobaoResponse {
    private LinkedList<TaoGoodsResponseEntity> n_tbk_item;

    public TaoGoodsResponseResult() {
        super();
    }

    public LinkedList<TaoGoodsResponseEntity> getN_tbk_item() {
        return n_tbk_item;
    }

    public void setN_tbk_item(LinkedList<TaoGoodsResponseEntity> n_tbk_item) {
        this.n_tbk_item = n_tbk_item;
    }
}
