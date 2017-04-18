package org.maxwe.tao.client.auction;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Created by Pengwei Ding on 2017-04-17 11:44.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: 给商品加上自己的推广信息
 */
public class AuctionClient {
    public static String auctionGoods(AuctionRequestModel auctionRequestModel) throws Exception {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(auctionRequestModel.getURL());
        httpGet.setHeader("Cookie", auctionRequestModel.getCookie());
        httpGet.setHeader("Content-type", "application/json");
        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
        String resultJson = EntityUtils.toString(closeableHttpResponse.getEntity());
        return resultJson;
    }
}
