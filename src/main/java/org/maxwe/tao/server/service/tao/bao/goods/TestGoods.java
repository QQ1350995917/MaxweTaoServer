package org.maxwe.tao.server.service.tao.bao.goods;

import com.alibaba.fastjson.JSON;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import org.maxwe.tao.server.ApplicationConfigure;
import org.maxwe.tao.server.service.tao.bao.APIConstants;

/**
 * Created by Pengwei Ding on 2017-01-18 14:54.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TestGoods {

    public static void main(String[] args) throws Exception {
        TaoGoodsRequestModel goodsRequestModel = new TaoGoodsRequestModel();
//        goodsRequestModel.setMethodName("taobao.tbk.item.get");
//        goodsRequestModel.setFields("num_iid");
//        goodsRequestModel.setQ("女装");
//        goodsRequestModel.setCat("16");
//        goodsRequestModel.setItemloc("杭州");
//        goodsRequestModel.setSort("tk_rate_des");
//        goodsRequestModel.setIs_tmall(false);
//        goodsRequestModel.setIs_overseas(false);
//        goodsRequestModel.setStart_price(0000);
//        goodsRequestModel.setEnd_price(9999);
//        goodsRequestModel.setStart_tk_rate(0000);
//        goodsRequestModel.setEnd_tk_rate(9999);
//        goodsRequestModel.setPlatform(2);
//        goodsRequestModel.setPage_no(1);
//        goodsRequestModel.setPage_size(20);
        TaobaoClient taoBaoClient = new DefaultTaobaoClient(APIConstants.URL_FORMAL, ApplicationConfigure.APP_KEY, ApplicationConfigure.APP_SECRET);
        TaoGoodsResponseModel execute = taoBaoClient.execute(goodsRequestModel);
        System.out.println(execute.getBody());
        TaoGoodsResponseModel taoGoodsResponseModel = JSON.parseObject(execute.getBody(), TaoGoodsResponseModel.class);
        for (TaoGoodsResponseEntity taoGoodsResponseEntity : taoGoodsResponseModel.getTbk_item_get_response().getResults().getN_tbk_item()) {

        }

    }
}
