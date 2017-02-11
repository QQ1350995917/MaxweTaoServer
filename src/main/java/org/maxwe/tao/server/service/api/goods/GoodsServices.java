package org.maxwe.tao.server.service.api.goods;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import org.maxwe.tao.server.controller.goods.GoodsRequestModel;
import org.maxwe.tao.server.service.api.APIConstants;

/**
 * Created by Pengwei Ding on 2017-01-18 14:54.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class GoodsServices {

    public static void requestCategory() throws Exception {
        CategoryRequestModel categoryRequestModel = new CategoryRequestModel();
        categoryRequestModel.setMethodName("taobao.itemcats.get");
        categoryRequestModel.setFields("cid,parent_cid,name,is_parent");
        categoryRequestModel.setParent_cid(0);
        TaobaoClient taoBaoClient = APIConstants.getTaoBaoClient();
        GoodsResponseModel execute = taoBaoClient.execute(categoryRequestModel);
        System.out.println(execute.getBody());
    }

    public static void requestGoods(GoodsFilterEntity goodsFilterEntity) throws Exception {
        GoodsRequestModel goodsRequestModel = new GoodsRequestModel();
        goodsRequestModel.setMethodName("taobao.tbk.item.get");
        goodsRequestModel.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick");
        goodsRequestModel.setFields("nick");
//        goodsRequestModel.setQ("女装");
//        goodsRequestModel.setCat("16");
//        goodsRequestModel.setItemloc("杭州");
//        goodsRequestModel.setSort("tk_rate_des");
        goodsRequestModel.setIs_tmall(false);
        goodsRequestModel.setIs_overseas(false);
        goodsRequestModel.setStart_price(0);
        goodsRequestModel.setEnd_price(1000);
        goodsRequestModel.setStart_tk_rate(0000);
        goodsRequestModel.setEnd_tk_rate(9999);
        goodsRequestModel.setPlatform(2);
        goodsRequestModel.setPage_no(1);
        goodsRequestModel.setPage_size(20);
        TaobaoClient taoBaoClient = new DefaultTaobaoClient(APIConstants.URL_FORMAL, "23595494", "6608da9c96be14e186ff485020892334");
        GoodsResponseModel execute = taoBaoClient.execute(goodsRequestModel);
        System.out.println(execute.getBody());
    }

    public static void main(String[] args) throws Exception {
        requestGoods(null);
//        requestCategory();
    }
}
