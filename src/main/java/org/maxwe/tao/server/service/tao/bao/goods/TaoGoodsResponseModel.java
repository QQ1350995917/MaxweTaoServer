package org.maxwe.tao.server.service.tao.bao.goods;

import com.taobao.api.TaobaoResponse;

/**
 * Created by Pengwei Ding on 2017-01-18 15:23.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TaoGoodsResponseModel extends TaobaoResponse {
    private TaoGoodsResponseItemEntity tbk_item_get_response;

    public TaoGoodsResponseModel() {
        super();
    }

    public TaoGoodsResponseItemEntity getTbk_item_get_response() {
        return tbk_item_get_response;
    }

    public void setTbk_item_get_response(TaoGoodsResponseItemEntity tbk_item_get_response) {
        this.tbk_item_get_response = tbk_item_get_response;
    }
}
