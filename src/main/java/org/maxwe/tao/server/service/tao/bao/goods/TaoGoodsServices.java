package org.maxwe.tao.server.service.tao.bao.goods;

import com.alibaba.fastjson.JSON;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import org.maxwe.tao.server.ApplicationConfigure;
import org.maxwe.tao.server.service.tao.bao.APIConstants;
import org.maxwe.tao.server.service.tao.mami.GoodsEntity;

import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Pengwei Ding on 2017-02-23 16:02.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class TaoGoodsServices {

    /**
     * 根据商品的ID找到商品的详细信息
     * @param ids
     * @return
     * @throws Exception
     */
    public LinkedList<GoodsEntity> findById(String[] ids) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        for (String id : ids) {
            stringBuilder.append(id).append(",");
        }
        stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        String idsString = stringBuilder.toString();
        TaoGoodsItemRequestModel req = new TaoGoodsItemRequestModel();
        req.setNum_iids(idsString);
        TaobaoClient taoBaoClient = new DefaultTaobaoClient(APIConstants.URL_FORMAL, ApplicationConfigure.APP_KEY, ApplicationConfigure.APP_SECRET);
        TaoGoodsItemResponseModel execute = taoBaoClient.execute(req);
        TaoGoodsItemResponseModel taoGoodsItemResponseModel = JSON.parseObject(execute.getBody(), TaoGoodsItemResponseModel.class);
        TaoGoodsResponseItemEntity tbk_item_info_get_response = taoGoodsItemResponseModel.getTbk_item_info_get_response();
        if (tbk_item_info_get_response == null) {
            return null;
        }
        TaoGoodsResponseResult results = tbk_item_info_get_response.getResults();
        if (results == null) {
            return null;
        }
        LinkedList<TaoGoodsResponseEntity> n_tbk_item = results.getN_tbk_item();
        if (n_tbk_item == null || n_tbk_item.size() < 1) {
            return null;
        }
        LinkedList<GoodsEntity> resultsGoodsEntities = new LinkedList<>();
        for (TaoGoodsResponseEntity taoGoodsResponseEntity : n_tbk_item) {
            GoodsEntity goodsEntity = new GoodsEntity();
            goodsEntity.setUser_id(taoGoodsResponseEntity.getSeller_id());
//            goodsEntity.setSeller_nick();
            goodsEntity.setShop_type(taoGoodsResponseEntity.getUser_type() + "");
            goodsEntity.setShop_title(taoGoodsResponseEntity.getNick());
            goodsEntity.setNum_iid(taoGoodsResponseEntity.getNum_iid());
            goodsEntity.setTitle(taoGoodsResponseEntity.getTitle());
//                goodsEntity.setCategory();
            goodsEntity.setReserve_price(taoGoodsResponseEntity.getReserve_price());
            goodsEntity.setZk_final_price(taoGoodsResponseEntity.getZk_final_price());
//                goodsEntity.setCoupon_info();
//                goodsEntity.setCoupon_total_count();
//                goodsEntity.setCoupon_remain_cou();
//                goodsEntity.setCoupon_click_url();
//                goodsEntity.setCoupon_start_time();
//                goodsEntity.setCoupon_end_time();
//                goodsEntity.setCommission_rate();
            goodsEntity.setVolume(taoGoodsResponseEntity.getVolume());
            Object small_images = taoGoodsResponseEntity.getSmall_images();
            if (small_images != null && small_images instanceof Map) {
//                goodsEntity.setSmall_images((String[]) ((LinkedList) (((Map) small_images).get("string"))).toArray());
            }
            goodsEntity.setPict_url(taoGoodsResponseEntity.getPict_url());
            goodsEntity.setItem_url(taoGoodsResponseEntity.getItem_url());
            resultsGoodsEntities.add(goodsEntity);
        }
        return resultsGoodsEntities;
    }


    public LinkedList<GoodsEntity> findByFavouriteGroup(){
        return null;
    }
}
