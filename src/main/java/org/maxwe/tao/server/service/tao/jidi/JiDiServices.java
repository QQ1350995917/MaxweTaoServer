package org.maxwe.tao.server.service.tao.jidi;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.maxwe.tao.server.service.tao.mami.GoodsEntity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pengwei Ding on 2017-02-20 22:49.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class JiDiServices implements IJiDiGoodsServices {
    private static JiDiServices instance;

    private JiDiServices() {

    }

    public static synchronized JiDiServices getInstance() {
        if (instance == null) {
            instance = new JiDiServices();
            instance.init();
        }
        return instance;
    }

    private static final String APPKEY = "db7818534504f08a4b5a8c584a6af3ca";
    private static final String allGoodsUrl = "http://api.tkjidi.com/getGoodsLink?appkey=" + APPKEY + "&type=www_lingquan&page=";
    private static final String goodsTop100Url = "http://api.tkjidi.com/getGoodsLink?appkey=" + APPKEY + "&type=top100&page=";
    private static final String goodsDaPaiUrl = "http://api.tkjidi.com/getGoodsLink?appkey=" + APPKEY + "&type=dapai&page=";
    private static final String goodsMeiRiBiMaiUrl = "http://api.tkjidi.com/getGoodsLink?appkey=" + APPKEY + "&type=bipai&page=";

    private static final LinkedList<GoodsEntity> allGoods = new LinkedList<>();
    private static final LinkedList<GoodsEntity> goodsTop100 = new LinkedList<>();
    private static final LinkedList<GoodsEntity> goodsDaPai = new LinkedList<>();
    private static final LinkedList<GoodsEntity> goodsMeiRiBiPai = new LinkedList<>();

    @Override
    public synchronized void init() {
        allGoods.clear();
        goodsTop100.clear();
        goodsDaPai.clear();
        goodsMeiRiBiPai.clear();

        int pageIndex = 0;
        boolean isGoing = true;

        while (isGoing) {
            pageIndex++;
            try {
                LinkedList<GoodsEntity> goodsEntityFormServer = getGoodsEntityFormServer(allGoodsUrl, pageIndex);
                if (goodsEntityFormServer.size() < 1) {
                    isGoing = false;
                } else {
                    allGoods.addAll(goodsEntityFormServer);
                }
            } catch (Exception e) {
                isGoing = false;
                e.printStackTrace();
            }
        }

        pageIndex = 0;
        isGoing = true;

        while (isGoing) {
            pageIndex++;
            try {
                LinkedList<GoodsEntity> goodsEntityFormServer = getGoodsEntityFormServer(goodsTop100Url, pageIndex);
                if (goodsEntityFormServer.size() < 1) {
                    isGoing = false;
                } else {
                    goodsTop100.addAll(goodsEntityFormServer);
                }
            } catch (Exception e) {
                isGoing = false;
                e.printStackTrace();
            }
        }


        pageIndex = 0;
        isGoing = true;

        while (isGoing) {
            pageIndex++;
            try {
                LinkedList<GoodsEntity> goodsEntityFormServer = getGoodsEntityFormServer(goodsDaPaiUrl, pageIndex);
                if (goodsEntityFormServer.size() < 1) {
                    isGoing = false;
                } else {
                    goodsDaPai.addAll(goodsEntityFormServer);
                }
            } catch (Exception e) {
                isGoing = false;
                e.printStackTrace();
            }
        }

        pageIndex = 0;
        isGoing = true;

        while (isGoing) {
            pageIndex++;
            try {
                LinkedList<GoodsEntity> goodsEntityFormServer = getGoodsEntityFormServer(goodsMeiRiBiMaiUrl, pageIndex);
                if (goodsEntityFormServer.size() < 1) {
                    isGoing = false;
                } else {
                    goodsMeiRiBiPai.addAll(goodsEntityFormServer);
                }
            } catch (Exception e) {
                isGoing = false;
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized LinkedList<GoodsEntity> getGoods(int pageIndex, int pageSize) {
        LinkedList<GoodsEntity> result = new LinkedList<>();
        int startIndex = pageIndex * pageSize;
        int endIndex = (pageIndex + 1) * pageSize;
        for (int index = startIndex; index < endIndex && index < allGoods.size(); index++) {
            result.add(allGoods.get(index));
        }
        return result;
    }

    @Override
    public synchronized LinkedList<GoodsEntity> getGoodsTop100(int pageIndex, int pageSize) {
        LinkedList<GoodsEntity> result = new LinkedList<>();
        int startIndex = pageIndex * pageSize;
        int endIndex = (pageIndex + 1) * pageSize;
        for (int index = startIndex; index < endIndex && index < goodsTop100.size(); index++) {
            result.add(goodsTop100.get(index));
        }
        return result;
    }

    @Override
    public synchronized LinkedList<GoodsEntity> getGoodsDaPai(int pageIndex, int pageSize) {
        LinkedList<GoodsEntity> result = new LinkedList<>();
        int startIndex = pageIndex * pageSize;
        int endIndex = (pageIndex + 1) * pageSize;
        for (int index = startIndex; index < endIndex && index < goodsDaPai.size(); index++) {
            result.add(goodsDaPai.get(index));
        }
        return result;
    }

    @Override
    public synchronized LinkedList<GoodsEntity> getGoodsMeiRiBiPai(int pageIndex, int pageSize) {
        LinkedList<GoodsEntity> result = new LinkedList<>();
        int startIndex = pageIndex * pageSize;
        int endIndex = (pageIndex + 1) * pageSize;
        for (int index = startIndex; index < endIndex && index < goodsMeiRiBiPai.size(); index++) {
            result.add(goodsMeiRiBiPai.get(index));
        }
        return result;
    }


    private LinkedList<GoodsEntity> getGoodsEntityFormServer(String url, int pageIndex) throws Exception {
        LinkedList<GoodsEntity> result = new LinkedList<>();
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url + pageIndex);
        HttpResponse httpResponse = defaultHttpClient.execute(httpget);
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String strResult = EntityUtils.toString(httpResponse.getEntity());
            JiDiGoodsModel jiDiGoodsModel = JSONObject.parseObject(strResult, JiDiGoodsModel.class);
            List<JiDiGoodsEntity> data = jiDiGoodsModel.getData();
            for (JiDiGoodsEntity jiDiGoodsEntity : data) {
                GoodsEntity goodsEntity = new GoodsEntity();
                goodsEntity.setTitle(jiDiGoodsEntity.getGoods_name());
                goodsEntity.setNum_iid(Long.parseLong(jiDiGoodsEntity.getGoods_id()));
                goodsEntity.setCategory(jiDiGoodsEntity.getCate_name());
                goodsEntity.setPict_url(jiDiGoodsEntity.getPic());
                goodsEntity.setReserve_price(jiDiGoodsEntity.getPrice());
                goodsEntity.setZk_final_price(jiDiGoodsEntity.getPrice_after_coupons());
                goodsEntity.setCoupon_info(jiDiGoodsEntity.getPrice_coupons());
                goodsEntity.setVolume(Long.parseLong(jiDiGoodsEntity.getSales()));
                goodsEntity.setCommission_rate(jiDiGoodsEntity.getRate());
                goodsEntity.setCoupon_click_url(jiDiGoodsEntity.getQuan_link());
                goodsEntity.setCoupon_total_count(Long.parseLong(jiDiGoodsEntity.getQuan_zhong()));
                goodsEntity.setCoupon_remain_cou(Long.parseLong(jiDiGoodsEntity.getQuan_shengyu()));
                goodsEntity.setCoupon_end_time(jiDiGoodsEntity.getQuan_expired_time());
//                private String cate_id;// 1,/*分类id*/
//                private String commission_type;// 3,/*佣金类型*/
//                private String commission_type_name;// 鹊桥,/*佣金类型名称*/
//                private String quan_note;// 单笔满69元可用，每人限领1 张,/*领券的备注*/
//                private String quan_guid_content;// 儿童纯棉中厚款，贴身舒适，打底家居套装，必备保暖套装！/*领的导购文案*/
//                private String quan_qq_img;// http://images.***.*******.jpg/*QQ群发时候用的图片*/
                result.add(goodsEntity);
            }
        } else {
            return result;
        }
        return result;
    }
}
