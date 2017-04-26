package org.maxwe.tao.server.common.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.maxwe.tao.server.service.tao.alimama.common.AliResponsePageEntity;
import org.maxwe.tao.server.service.tao.alimama.goods.AliGoodsRequestModel;
import org.maxwe.tao.server.service.tao.alimama.goods.GoodsServices;

import java.util.List;

/**
 * Created by Pengwei Ding on 2017-03-10 18:09.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: @TODO
 */
public class Test {

    public static void main(String[] args) throws Exception {
        AliGoodsRequestModel aliGoodsRequestModel = new AliGoodsRequestModel();
        aliGoodsRequestModel.setQ("http://c.b0yp.com/h.g0bnkg?cv=FeIhr8Dh9z&sm=7471b9");
        List<AliResponsePageEntity> aliResponsePageEntities =
            GoodsServices.searchForGoods(aliGoodsRequestModel);
        System.out.println(aliResponsePageEntities.get(0).getAuctionId());

        //        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
//        HttpGet httpGet = new HttpGet("http://c.b1wt.com/h.4rUoo3?cv=fiqPLIy2WP&sm=51afd4");
//        httpGet.setHeader("Content-type", "application/json");
//        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.151 Safari/535.19");
//        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
//        if (closeableHttpResponse.getStatusLine().getStatusCode() == 200) {
//            String resultJson = EntityUtils.toString(closeableHttpResponse.getEntity());
////            System.out.println(resultJson);
//
//            specialDomainHandl(resultJson);
////            System.out.println(match);
//        }
    }

    public static String specialDomainHandl(String source) {
        String[] split = source.split("\\r\\n");
        for (String text : split) {
            if (text.contains("var url = ")) {
                System.out.println(text);
                String http = text.substring(text.indexOf("http"), text.length() - 2);
                System.out.println(http);
                break;
            }
        }
        return null;
    }
}
