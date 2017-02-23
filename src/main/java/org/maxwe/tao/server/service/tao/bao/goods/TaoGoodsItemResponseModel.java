package org.maxwe.tao.server.service.tao.bao.goods;

import com.taobao.api.TaobaoResponse;

/**
 * Created by Pengwei Ding on 2017-02-23 16:07.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TaoGoodsItemResponseModel extends TaobaoResponse {
        private TaoGoodsResponseItemEntity tbk_item_info_get_response;

    public TaoGoodsItemResponseModel() {
        super();
    }


    public TaoGoodsResponseItemEntity getTbk_item_info_get_response() {
        return tbk_item_info_get_response;
    }

    public void setTbk_item_info_get_response(TaoGoodsResponseItemEntity tbk_item_info_get_response) {
        this.tbk_item_info_get_response = tbk_item_info_get_response;
    }
}
